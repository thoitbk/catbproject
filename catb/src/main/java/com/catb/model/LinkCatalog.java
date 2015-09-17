package com.catb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "link_catalog")
public class LinkCatalog implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "link_site")
	private String linkSite;
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@Column(name = "open_blank")
	private Boolean openBlank;

	public LinkCatalog() {
		
	}

	public LinkCatalog(Integer id, String title, String linkSite,
			Integer sqNumber, Boolean openBlank) {
		this.id = id;
		this.title = title;
		this.linkSite = linkSite;
		this.sqNumber = sqNumber;
		this.openBlank = openBlank;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLinkSite() {
		return linkSite;
	}

	public void setLinkSite(String linkSite) {
		this.linkSite = linkSite;
	}

	public Integer getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(Integer sqNumber) {
		this.sqNumber = sqNumber;
	}

	public Boolean getOpenBlank() {
		return openBlank;
	}

	public void setOpenBlank(Boolean openBlank) {
		this.openBlank = openBlank;
	}
}
