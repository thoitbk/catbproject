package com.catb.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "salt")
	private String salt;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "gender")
	private Boolean gender;
	
	@Column(name = "home_phone_number")
	private String homePhoneNumber;
	
	@Column(name = "office_phone_number")
	private String officePhoneNumber;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "address")
	private String address;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id")
	private Position position;
	
	@Column(name = "email")
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;
	
	@Column(name = "description")
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
	private Set<Role> roles = new HashSet<Role>();
	
	public User() {
		
	}

	public User(String username, String password, String fullName,
			Boolean gender, String homePhoneNumber, String officePhoneNumber,
			String mobileNumber, String address, String email,
			String description) {
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.gender = gender;
		this.homePhoneNumber = homePhoneNumber;
		this.officePhoneNumber = officePhoneNumber;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.email = email;
		this.description = description;
	}

	public User(Integer id, String username, String password, String salt,
			String fullName, Boolean gender, String homePhoneNumber,
			String officePhoneNumber, String mobileNumber, String address,
			Position position, String email, Department department,
			String description, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.fullName = fullName;
		this.gender = gender;
		this.homePhoneNumber = homePhoneNumber;
		this.officePhoneNumber = officePhoneNumber;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.position = position;
		this.email = email;
		this.department = department;
		this.description = description;
		this.roles = roles;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getOfficePhoneNumber() {
		return officePhoneNumber;
	}

	public void setOfficePhoneNumber(String officePhoneNumber) {
		this.officePhoneNumber = officePhoneNumber;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
