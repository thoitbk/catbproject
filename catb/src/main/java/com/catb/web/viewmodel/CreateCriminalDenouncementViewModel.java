package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class CreateCriminalDenouncementViewModel {
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String name;
	
	@Size(min = 0, max = 500)
	private String address;
	
	@Size(min = 0, max = 200)
	private String phoneNumber;
	
	@Email
	@Size(min = 0, max = 100)
	private String email;
	
	@NotBlank
	@Size(min = 0, max = 1000)
	private String title;
	
	@NotBlank
	private String content;
	
	@NotBlank
	private String captcha;
	
	public CreateCriminalDenouncementViewModel() {
		
	}
	
	public CreateCriminalDenouncementViewModel(String name, String address,
			String phoneNumber, String email, String title, String content,
			String captcha) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.title = title;
		this.content = content;
		this.captcha = captcha;
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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
