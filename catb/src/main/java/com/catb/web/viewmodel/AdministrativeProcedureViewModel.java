package com.catb.web.viewmodel;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class AdministrativeProcedureViewModel {
	
	@NotBlank
	@Size(min = 0, max = 100)
	private String code;
	
	@NotBlank
	@Size(min = 0, max = 1000)
	private String name;
	
	@Past
	private Date publishedDate;
	
	@Size(min = 0, max = 5000)
	private String description;
	
	@Size(min = 0, max = 100)
	private String validDuration;
	
	@Pattern(regexp = "(^$|[0-9]{0,9})")
	private String sqNumber;
	
	private String content;
	
	private Integer departmentId;
	
	private Integer fieldId;

	public AdministrativeProcedureViewModel() {
		
	}

	public AdministrativeProcedureViewModel(String code, String name,
			Date publishedDate, String description, String validDuration,
			String sqNumber, String content, Integer departmentId,
			Integer fieldId) {
		this.code = code;
		this.name = name;
		this.publishedDate = publishedDate;
		this.description = description;
		this.validDuration = validDuration;
		this.sqNumber = sqNumber;
		this.content = content;
		this.departmentId = departmentId;
		this.fieldId = fieldId;
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

	public String getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(String sqNumber) {
		this.sqNumber = sqNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
}
