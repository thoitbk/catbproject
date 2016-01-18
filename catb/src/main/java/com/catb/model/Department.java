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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "department")
public class Department implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "fax")
	private String fax;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	private Set<AdministrativeProcedure> administrativeProcedures = new HashSet<AdministrativeProcedure>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	private Set<Document> documents = new HashSet<Document>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	private Set<User> users = new HashSet<User>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	private Set<Schedule> schedules = new HashSet<Schedule>();
	
	public Department() {
		
	}

	public Department(Integer id, String code, String name, String phone,
			String fax, String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.phone = phone;
		this.fax = fax;
		this.description = description;
	}

	public Department(String code, String name, String phone, String fax,
			String description) {
		this.code = code;
		this.name = name;
		this.phone = phone;
		this.fax = fax;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<AdministrativeProcedure> getAdministrativeProcedures() {
		return administrativeProcedures;
	}

	public void setAdministrativeProcedures(
			Set<AdministrativeProcedure> administrativeProcedures) {
		this.administrativeProcedures = administrativeProcedures;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}
}
