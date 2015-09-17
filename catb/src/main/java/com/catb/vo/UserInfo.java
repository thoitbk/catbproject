package com.catb.vo;

public class UserInfo {
	
	private Integer id;
	private String username;
	private String fullName;
	private Boolean gender;
	
	public UserInfo() {
		
	}

	public UserInfo(Integer id, String username, String fullName, Boolean gender) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}
}
