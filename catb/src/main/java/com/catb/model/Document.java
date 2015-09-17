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

@SuppressWarnings("serial")
@Entity
@Table(name = "document")
public class Document implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "summary")
	private String summary;
	
	@Column(name = "published_date")
	private Date publishedDate;
	
	@Column(name = "valid_date")
	private Date validDate;
	
	@Column(name = "leader")
	private String leader;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_type_catalog_id")
	private DocumentTypeCatalog documentTypeCatalog;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "document", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private Set<DocumentFile> documentFiles = new HashSet<DocumentFile>();
	
	public Document() {
		
	}

	public Document(Integer id, String code, String summary,
			Date publishedDate, Date validDate, String leader,
			String description, Integer sqNumber, String content,
			Department department, DocumentTypeCatalog documentTypeCatalog) {
		this.id = id;
		this.code = code;
		this.summary = summary;
		this.publishedDate = publishedDate;
		this.validDate = validDate;
		this.leader = leader;
		this.description = description;
		this.sqNumber = sqNumber;
		this.content = content;
		this.department = department;
		this.documentTypeCatalog = documentTypeCatalog;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
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

	public DocumentTypeCatalog getDocumentTypeCatalog() {
		return documentTypeCatalog;
	}

	public void setDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog) {
		this.documentTypeCatalog = documentTypeCatalog;
	}

	public Set<DocumentFile> getDocumentFiles() {
		return documentFiles;
	}

	public void setDocumentFiles(Set<DocumentFile> documentFiles) {
		this.documentFiles = documentFiles;
	}
}
