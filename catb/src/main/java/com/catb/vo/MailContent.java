package com.catb.vo;

public class MailContent {
	
	private String title;
	private String content;
	private String name;
	private String sentDate;
	private String replyContent;
	
	public MailContent() {
		
	}

	public MailContent(String title, String content, String name,
			String sentDate, String replyContent) {
		this.title = title;
		this.content = content;
		this.name = name;
		this.sentDate = sentDate;
		this.replyContent = replyContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
}
