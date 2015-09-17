package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class EditUserViewModel {
	
	@NotBlank
	@Size(min = 0, max = 100)
	private String username;
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String fullName;
	
	@Size(min = 0, max = 50)
	private String homeNumber;
	
	@Size(min = 0, max = 50)
	private String officeNumber;
	
	@Size(min = 0, max = 50)
	private String mobileNumber;
	
	@Size(min = 0, max = 500)
	private String address;
	
	@Email
	@Size(min = 0, max = 100)
	private String email;

	public EditUserViewModel() {
		
	}

	public EditUserViewModel(String username, String fullName,
			String homeNumber, String officeNumber, String mobileNumber,
			String address, String email) {
		this.username = username;
		this.fullName = fullName;
		this.homeNumber = homeNumber;
		this.officeNumber = officeNumber;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
