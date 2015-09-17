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
@Table(name = "ad_catalog")
public class AdCatalog implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "link")
	private String link;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "display")
	private Boolean display;
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@Column(name = "open_blank")
	private Boolean openBlank;

	public AdCatalog() {
		
	}

	public AdCatalog(String title, String link, String image, Boolean display,
			Integer sqNumber, Boolean openBlank) {
		this.title = title;
		this.link = link;
		this.image = image;
		this.display = display;
		this.sqNumber = sqNumber;
		this.openBlank = openBlank;
	}

	public AdCatalog(Integer id, String title, String link, String image,
			Boolean display, Integer sqNumber, Boolean openBlank) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.image = image;
		this.display = display;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
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
