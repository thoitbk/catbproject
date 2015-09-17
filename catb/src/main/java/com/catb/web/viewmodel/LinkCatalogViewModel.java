package com.catb.web.viewmodel;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class LinkCatalogViewModel {
	
	@NotBlank
	@Size(min = 0, max = 500)
	private String title;
	
	@NotBlank
	@Size(min = 0, max = 500)
	@URL
	private String linkSite;
	
	@Pattern(regexp = "(^$|[0-9]{0,9})")
	private String sqNumber;
	
	private Boolean openBlank;

	public LinkCatalogViewModel() {
		
	}

	public LinkCatalogViewModel(String title, String linkSite, String sqNumber,
			Boolean openBlank) {
		this.title = title;
		this.linkSite = linkSite;
		this.sqNumber = sqNumber;
		this.openBlank = openBlank;
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

	public String getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(String sqNumber) {
		this.sqNumber = sqNumber;
	}

	public Boolean getOpenBlank() {
		return openBlank;
	}

	public void setOpenBlank(Boolean openBlank) {
		this.openBlank = openBlank;
	}
}
