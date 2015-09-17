package com.catb.web.viewmodel;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class DocumentTypeCatalogViewModel {
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String code;
	
	@NotBlank
	@Size(min = 0, max = 500)
	private String name;
	
	@Pattern(regexp = "(^$|[0-9]{0,9})")
	private String sqNumber;
	
	private Boolean display;
	
	@Size(min = 0, max = 500)
	private String description;

	public DocumentTypeCatalogViewModel() {
		
	}
	
	public DocumentTypeCatalogViewModel(String code, String name,
			String sqNumber, Boolean display, String description) {
		this.code = code;
		this.name = name;
		this.sqNumber = sqNumber;
		this.display = display;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
