package com.catb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "commented_date")
	private Date commentedDate;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "answerer")
	private String answerer;
	
	@Column(name = "reply_content")
	private String replyContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qa_catalog_id")
	private QACatalog qaCatalog;

	public Comment() {
		
	}

	public Comment(Integer id, String name, String address, String phoneNumber,
			String email, String title, String content, Date commentedDate,
			Integer status, String answerer, String replyContent) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.title = title;
		this.content = content;
		this.commentedDate = commentedDate;
		this.status = status;
		this.answerer = answerer;
		this.replyContent = replyContent;
	}

	public Comment(Integer id, String name, String address, String phoneNumber,
			String email, String title, String content, Date commentedDate,
			Integer status, String answerer, String replyContent,
			QACatalog qaCatalog) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.title = title;
		this.content = content;
		this.commentedDate = commentedDate;
		this.status = status;
		this.answerer = answerer;
		this.replyContent = replyContent;
		this.qaCatalog = qaCatalog;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getCommentedDate() {
		return commentedDate;
	}

	public void setCommentedDate(Date commentedDate) {
		this.commentedDate = commentedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public QACatalog getQaCatalog() {
		return qaCatalog;
	}

	public void setQaCatalog(QACatalog qaCatalog) {
		this.qaCatalog = qaCatalog;
	}
}
