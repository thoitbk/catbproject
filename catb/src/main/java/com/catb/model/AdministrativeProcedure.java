package com.catb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "administrative_procedure")
public class AdministrativeProcedure implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "published_date")
	private Date publishedDate;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "valid_duration")
	private String validDuration;
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id")
	private Field field;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "administrativeProcedure", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private Set<AdministrativeProcedureFile> administrativeProcedureFiles = new HashSet<AdministrativeProcedureFile>();

	public AdministrativeProcedure() {
		
	}

	public AdministrativeProcedure(Integer id, String code, String name,
			Date publishedDate, String description, String validDuration,
			Integer sqNumber, String content, Department department, Field field) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.publishedDate = publishedDate;
		this.description = description;
		this.validDuration = validDuration;
		this.sqNumber = sqNumber;
		this.content = content;
		this.department = department;
		this.field = field;
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

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValidDuration() {
		return validDuration;
	}

	public void setValidDuration(String validDuration) {
		this.validDuration = validDuration;
	}

	public Integer getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(Integer sqNumber) {
		this.sqNumber = sqNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Set<AdministrativeProcedureFile> getAdministrativeProcedureFiles() {
		return administrativeProcedureFiles;
	}

	public void setAdministrativeProcedureFiles(
			Set<AdministrativeProcedureFile> administrativeProcedureFiles) {
		this.administrativeProcedureFiles = administrativeProcedureFiles;
	}
}
