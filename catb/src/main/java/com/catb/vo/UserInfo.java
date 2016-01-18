package com.catb.vo;

public class UserInfo {
	
	private Integer id;
	private String username;
	private String fullName;
	private Boolean gender;
	private Integer departmentId;
	private String departmentCode;
	private String departmentName;
	
	public UserInfo() {
		
	}

	public UserInfo(Integer id, String username, String fullName, Boolean gender) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.gender = gender;
	}

	public UserInfo(Integer id, String username, String fullName,
			Boolean gender, Integer departmentId) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.gender = gender;
		this.departmentId = departmentId;
	}

	public UserInfo(Integer id, String username, String fullName,
			Boolean gender, Integer departmentId, String departmentCode,
			String departmentName) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.gender = gender;
		this.departmentId = departmentId;
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
