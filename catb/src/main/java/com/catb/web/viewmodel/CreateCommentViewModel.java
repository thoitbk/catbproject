package com.catb.web.viewmodel;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class CreateCommentViewModel {
	
	@NotBlank
	@Size(min = 0, max = 1000)
	private String title;
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String name;
	
	@Size(min = 0, max = 500)
	private String address;
	
	@Size(min = 0, max = 200)
	@Pattern(regexp = "^$|^(?:[0-9] ?){6,14}[0-9]$")
	private String phoneNumber;
	
	@Size(min = 0, max = 200)
	@Email
	private String email;
	
	private Integer qaCatalogId;
	
	@NotBlank
	private String content;
	
	@NotBlank
	private String captcha;
	
	public CreateCommentViewModel() {
		
	}

	public CreateCommentViewModel(String title, String name, String address,
			String phoneNumber, String email, Integer qaCatalogId,
			String content, String captcha) {
		this.title = title;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.qaCatalogId = qaCatalogId;
		this.content = content;
		this.captcha = captcha;
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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
