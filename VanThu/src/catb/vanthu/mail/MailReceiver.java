package catb.vanthu.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import catb.vanthu.util.Constants;
import catb.vanthu.valueobject.MailAttachment;
import catb.vanthu.valueobject.MailDetails;
import catb.vanthu.valueobject.MailList;
import catb.vanthu.valueobject.MailViewModel;

public class MailReceiver {

	static Logger logger = Logger
			.getLogger(MailSender.class.getCanonicalName());

	private static Store store = null;
	private static Folder folder = null;

	private static void initMailBox() {
		if (store == null || folder == null || !store.isConnected() || !folder.isOpen()) {
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");

			try {
				Session session = Session.getDefaultInstance(properties, null);
				store = session.getStore("imap");
				store.connect(Constants.SMTP_HOST, Constants.EMAIL_USERNAME, Constants.EMAIL_PASSWORD);

				folder = store.getFolder("Inbox");
				folder.open(Folder.READ_WRITE);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Exception", ex);
				try {
					folder.close(true);
					store.close();
				} catch (Exception e) {
					ex.printStackTrace();
					logger.error("Exception", e);
				}
			}
		}
	}

	public static void closeMailBox() {
		try {
			if (folder != null && folder.isOpen()) {
				folder.close(true);
			}
			if (store != null && store.isConnected()) {
				store.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception", ex);
		}
	}
	
	public static MailList showInbox(int page, int pageSize) {

		List<MailViewModel> mails = new LinkedList<MailViewModel>();
		Integer inboxSize = 0;
		
		try {
			initMailBox();
			UIDFolder f = (UIDFolder) folder;
			inboxSize = folder.getMessages().length;

			if (inboxSize == 0)
				return new MailList(mails, 0);
			if (page < 0)
				page = 1;

			int lowerBound = (page - 1) * pageSize >= inboxSize ? 1
					: (page - 1) * pageSize + 1;
			int upperBound = lowerBound + pageSize >= inboxSize ? inboxSize
					: lowerBound + pageSize;

			Message[] messages = folder.getMessages(lowerBound, upperBound);
			for (Message m : messages) {
				Long uid = f.getUID(m);
				String subject = m.getSubject();
				String from = ((InternetAddress) m.getFrom()[0]).getAddress();
				Date sentDate = m.getSentDate();
				Integer size = m.getSize() / 1000;
				Boolean read = m.isSet(Flags.Flag.SEEN);
				mails.add(new MailViewModel(uid, subject, from, sentDate, size,
						read));
			}

			Collections.sort(mails);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception", ex);
		}

		return new MailList(mails, inboxSize);
	}

	public static MailDetails showDetails(Long uid) {
		initMailBox();
		UIDFolder f = (UIDFolder) folder;
		try {
			Message message = f.getMessageByUID(uid);
			if (message != null) {
				Address fromAdd = message.getFrom()[0];
				String from = String.format("%s", fromAdd.toString());
				Address[] toAdds = message.getAllRecipients();
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < toAdds.length; i++) {
					builder.append(String.format("\"%s\"", toAdds[i].toString()));
					if (i < toAdds.length - 1) {
						builder.append(", ");
					}
				}
				String to = builder.toString();
				Date sentDate = message.getSentDate();
				String subject = message.getSubject();
				String mailContent = getText(message);
				List<MailAttachment> attachments = getAttachments(message);
				return new MailDetails(from, to, sentDate, subject,
						mailContent, attachments);
			}
			return null;
		} catch (Exception ex) {
			logger.error("Exception: ", ex);
			ex.printStackTrace();

			return null;
		}
	}

	public static void getAttachment(Long uid, Integer part, OutputStream os) {
		initMailBox();
		UIDFolder f = (UIDFolder) folder;

		try {
			Message m = f.getMessageByUID(uid);
			if (m != null) {
				Multipart multipart = (Multipart) m.getContent();
				BodyPart bodyPart = multipart.getBodyPart(part);
				InputStream is = bodyPart.getInputStream();
				byte bytes[] = new byte[1024 * 1024];
				int readBytes = -1;
				while ((readBytes = is.read(bytes)) != -1) {
					os.write(bytes, 0, readBytes);
				}
				is.close();
				os.close();
			}
		} catch (Exception ex) {
			logger.error("Exception: ", ex);
			ex.printStackTrace();
		}
	}

	private static List<MailAttachment> getAttachments(Message m) throws IOException,
			MessagingException {
		List<MailAttachment> attachments = new LinkedList<MailAttachment>();
		Object obj = m.getContent();
		if (obj instanceof Multipart) {
			Multipart multipart = (Multipart) obj;
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())
						&& StringUtils.isNotBlank(bodyPart.getFileName())) {
					attachments.add(new MailAttachment(i, MimeUtility
							.decodeText(bodyPart.getFileName()), bodyPart
							.getContentType(),
							(int) ((bodyPart.getSize() / 1024) * 0.73) + 1));
				}
			}
		}

		return attachments;
	}

	public static List<MailAttachment> getAttachments(Long uid) {
		try {
			initMailBox();
			UIDFolder f = (UIDFolder) folder;

			return getAttachments(f.getMessageByUID(uid));
		} catch (Exception ex) {
			logger.error("Exception: ", ex);
			ex.printStackTrace();
			
			return null;
		}
	}

	private static String getText(Part p) throws MessagingException,
			IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}

	public static void main(String[] args) {
		showDetails(65L);
	}
}
