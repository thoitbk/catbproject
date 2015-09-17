package com.catb.web.viewmodel;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class AdViewModel {
	
	@Size(min = 0, max = 500)
	private String title;
	
	@NotBlank
	@Size(min = 0, max = 500)
	private String link;
	
	@Size(min = 0, max = 500)
	private String image;
	
	private Boolean display;
	
	@Pattern(regexp = "(^$|[0-9]{0,9})")
	private String sqNumber;
	
	private Boolean openBlank;
	
	public AdViewModel() {
		
	}

	public AdViewModel(String title, String link, String image,
			Boolean display, String sqNumber, Boolean openBlank) {
		this.title = title;
		this.link = link;
		this.image = image;
		this.display = display;
		this.sqNumber = sqNumber;
		this.openBlank = openBlank;
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
