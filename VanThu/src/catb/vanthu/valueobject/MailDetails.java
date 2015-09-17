package catb.vanthu.valueobject;

import java.util.Date;
import java.util.List;

public class MailDetails {
	
	private String from;
	private String to;
	private Date sentDate;
	private String subject;
	private String content;
	private List<MailAttachment> attachments;
	
	public MailDetails() {
		
	}

	public MailDetails(String from, String to, Date sentDate,
			String subject, String content, List<MailAttachment> attachments) {
		this.from = from;
		this.to = to;
		this.sentDate = sentDate;
		this.subject = subject;
		this.content = content;
		this.attachments = attachments;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<MailAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<MailAttachment> attachments) {
		this.attachments = attachments;
	}
}
