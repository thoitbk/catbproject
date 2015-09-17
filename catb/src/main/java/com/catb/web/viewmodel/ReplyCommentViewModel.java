package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ReplyCommentViewModel {
	
	@NotBlank
	@Size(min = 0, max = 1000)
	private String title;
	
	@Size(min = 0, max = 200)
	private String name;
	
	private Integer qaCatalogId;
	
	@NotBlank
	private String content;
	
	private Boolean show;
	
	@Size(min = 0, max = 200)
	private String answerer;
	
	private String replyContent;
	
	public ReplyCommentViewModel() {
		
	}

	public ReplyCommentViewModel(String title, String name,
			Integer qaCatalogId, String content, Boolean show, String answerer,
			String replyContent) {
		this.title = title;
		this.name = name;
		this.qaCatalogId = qaCatalogId;
		this.content = content;
		this.show = show;
		this.answerer = answerer;
		this.replyContent = replyContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQaCatalogId() {
		return qaCatalogId;
	}

	public void setQaCatalogId(Integer qaCatalogId) {
		this.qaCatalogId = qaCatalogId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public String getAnswerer() {
		return answerer;
	}

	public void setAnswerer(String answerer) {
		this.answerer = answerer;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
}
