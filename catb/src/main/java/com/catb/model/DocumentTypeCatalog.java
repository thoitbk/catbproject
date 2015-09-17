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
@Table(name = "document_type_catalog")
public class DocumentTypeCatalog implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@Column(name = "display")
	private Boolean display;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentTypeCatalog")
	private Set<Document> documents = new HashSet<Document>();

	public DocumentTypeCatalog() {
		
	}
	
	public DocumentTypeCatalog(Integer id, String code, String name,
			Integer sqNumber, Boolean display, String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.sqNumber = sqNumber;
		this.display = display;
		this.description = description;
	}

	public DocumentTypeCatalog(String code, String name, Integer sqNumber,
			Boolean display, String description) {
		this.code = code;
		this.name = name;
		this.sqNumber = sqNumber;
		this.display = display;
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

	public Integer getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(Integer sqNumber) {
		this.sqNumber = sqNumber;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
}
