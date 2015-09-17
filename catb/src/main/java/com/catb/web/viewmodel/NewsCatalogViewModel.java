package com.catb.web.viewmodel;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class NewsCatalogViewModel {
	
	private String displayLocation;
	
	private Integer parentId;
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String name;
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String url;
	
	@Pattern(regexp = "(^$|[0-9]{0,4})")
	private String sqNumber;
	
	private Boolean display;
	
	private Boolean specialSite;
	
	public NewsCatalogViewModel() {
		
	}

	public NewsCatalogViewModel(String displayLocation, Integer parentId,
			String name, String url, String sqNumber, Boolean display,
			Boolean specialSite) {
		this.displayLocation = displayLocation;
		this.parentId = parentId;
		this.name = name;
		this.url = url;
		this.sqNumber = sqNumber;
		this.display = display;
		this.specialSite = specialSite;
	}

	public String getDisplayLocation() {
		return displayLocation;
	}

	public void setDisplayLocation(String displayLocation) {
		this.displayLocation = displayLocation;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(String sqNumber) {
		this.sqNumber = sqNumber;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Boolean getSpecialSite() {
		return specialSite;
	}

	public void setSpecialSite(Boolean specialSite) {
		this.specialSite = specialSite;
	}
}
