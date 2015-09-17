package catb.vanthu.mail;

import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import catb.vanthu.util.Constants;
import catb.vanthu.valueobject.MailContentVO;

public class MailSender {
	
	static Logger logger = Logger.getLogger(MailSender.class.getCanonicalName());
	
	public static void send(MailContentVO mailContentVO) {

		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", Constants.SMTP_HOST);
		properties.put("mail.smtp.port", Constants.SMTP_PORT);

		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Constants.EMAIL_USERNAME, Constants.EMAIL_PASSWORD);
					}
				});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Constants.EMAIL_ADDRESS_FROM, "Văn thư", "UTF-8"));
			
			InternetAddress[] internetAddresses = new InternetAddress[mailContentVO.getDestinationAddresses().size()];
			for (int i = 0; i < mailContentVO.getDestinationAddresses().size(); i++) {
				internetAddresses[i] = new InternetAddress(mailContentVO.getDestinationAddresses().get(i));
			}
			message.setRecipients(Message.RecipientType.TO, internetAddresses);

			message.setSubject(mailContentVO.getAbs(), "UTF-8");

			Multipart multipart = new MimeMultipart();
			MimeBodyPart m = new MimeBodyPart();
			m.setContent(buildMailContent(mailContentVO), "text/html; charset=utf-8");
			multipart.addBodyPart(m);
			
			for (String file : mailContentVO.getFiles()) {
			    MimeBodyPart messageBodyPart = new MimeBodyPart();
			    DataSource source = new FileDataSource(file);
			    messageBodyPart.setDataHandler(new DataHandler(source));
			    messageBodyPart.setFileName(MimeUtility.encodeText(source.getName(), "UTF-8", "B"));
			    multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (Exception ex) {
			logger.error("Exception", ex);
		}
	}
	
	private static String buildMailContent(MailContentVO mailContentVO) {
		StringBuilder builder = new StringBuilder();
		builder.append("<p>").append("<b>Số ký hiệu: </b>").append(mailContentVO.getSign()).append("<p>");
		builder.append("<b>Trích yếu: </b>").append(mailContentVO.getAbs()).append("<p>");
		StringBuilder sendBuilder = new StringBuilder();
		for (int i = 0; i < mailContentVO.getSenders().size(); i++) {
			sendBuilder.append(mailContentVO.getSenders().get(i));
			if (i < mailContentVO.getSenders().size() - 1) {
				sendBuilder.append(", ");
			}
		}
		builder.append("<b>Nơi gửi: </b>").append(sendBuilder.toString()).append("<p>");
		builder.append("<b>Người ký: </b>").append(mailContentVO.getSigner()).append("<p>");
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		builder.append("<b>Ngày ban hành: </b>").append(f.format(mailContentVO.getPublishDate())).append("<p>");
		
		return builder.toString();
	}
}
