package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UpdateUserViewModel {
	
	@NotBlank
	@Size(min = 0, max = 100)
	private String username;
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String fullName;
	
	@Size(min = 0, max = 100)
	private String password;
	
	private Integer gender;
	
	@Size(min = 0, max = 500)
	private String address;
	
	@Size(min = 0, max = 50)
	private String homePhoneNumber;
	
	@Size(min = 0, max = 50)
	private String mobileNumber;
	
	@Size(min = 0, max = 50)
	private String officePhoneNumber;
	
	@Email
	@Size(min = 0, max = 100)
	private String email;
	
	private Integer position;
	
	private Integer department;
	
	@Size(min = 0, max = 500)
	private String description;
	
	public UpdateUserViewModel() {
		
	}

	public UpdateUserViewModel(String username, String fullName, String password,
			Integer gender, String address, String homePhoneNumber,
			String mobileNumber, String officePhoneNumber, String email,
			Integer position, Integer department, String description) {
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.gender = gender;
		this.address = address;
		this.homePhoneNumber = homePhoneNumber;
		this.mobileNumber = mobileNumber;
		this.officePhoneNumber = officePhoneNumber;
		this.email = email;
		this.position = position;
		this.department = department;
		this.description = description;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOfficePhoneNumber() {
		return officePhoneNumber;
	}

	public void setOfficePhoneNumber(String officePhoneNumber) {
		this.officePhoneNumber = officePhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
