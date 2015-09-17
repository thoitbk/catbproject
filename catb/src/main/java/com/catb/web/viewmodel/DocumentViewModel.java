package com.catb.web.viewmodel;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class DocumentViewModel {
	
	@NotBlank
	@Size(min = 0, max = 100)
	private String code;
	
	@NotBlank
	@Size(min = 0, max = 5000)
	private String summary;
	
	private Date publishDate;
	private Date validDate;
	
	@Size(min = 0, max = 200)
	private String leader;
	
	@Size(min = 0, max = 1000)
	private String description;
	
	@Pattern(regexp = "(^$|[0-9]{0,9})")
	private String sqNumber;
	
	private String content;
	private Integer departmentId;
	private Integer documentTypeCatalogId;
	
	public DocumentViewModel() {
		
	}

	public DocumentViewModel(String code, String summary, Date publishDate,
			Date validDate, String leader, String description, String sqNumber,
			String content, Integer departmentId, Integer documentTypeCatalogId) {
		this.code = code;
		this.summary = summary;
		this.publishDate = publishDate;
		this.validDate = validDate;
		this.leader = leader;
		this.description = description;
		this.sqNumber = sqNumber;
		this.content = content;
		this.departmentId = departmentId;
		this.documentTypeCatalogId = documentTypeCatalogId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getDocumentTypeCatalogId() {
		return documentTypeCatalogId;
	}

	public void setDocumentTypeCatalogId(Integer documentTypeCatalogId) {
		this.documentTypeCatalogId = documentTypeCatalogId;
	}
}
