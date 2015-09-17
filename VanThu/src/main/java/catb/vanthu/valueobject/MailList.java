package catb.vanthu.valueobject;

import java.util.List;

public class MailList {
	
	private List<MailViewModel> mails;
	private Integer inboxSize;
	
	public MailList() {
		
	}

	public MailList(List<MailViewModel> mails, Integer inboxSize) {
		this.mails = mails;
		this.inboxSize = inboxSize;
	}

	public List<MailViewModel> getMails() {
		return mails;
	}

	public void setMails(List<MailViewModel> mails) {
		this.mails = mails;
	}

	public Integer getInboxSize() {
		return inboxSize;
	}

	public void setInboxSize(Integer inboxSize) {
		this.inboxSize = inboxSize;
	}
}
