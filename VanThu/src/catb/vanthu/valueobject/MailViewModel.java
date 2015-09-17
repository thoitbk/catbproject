package catb.vanthu.valueobject;

import java.util.Date;

public class MailViewModel implements Comparable<MailViewModel> {
	
	private Long uid;
	private String subject;
	private String from;
	private Date sentDate;
	private Integer size;
	private Boolean read;
	
	public MailViewModel() {
		
	}

	public MailViewModel(Long uid, String subject, String from, Date sentDate,
			Integer size, Boolean read) {
		this.uid = uid;
		this.subject = subject;
		this.from = from;
		this.sentDate = sentDate;
		this.size = size;
		this.read = read;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public int compareTo(MailViewModel o) {
		if (o == null || this.sentDate == null || o.sentDate == null) return 0;
		Date s = o.sentDate;
		return s.compareTo(this.sentDate);
	}
}
