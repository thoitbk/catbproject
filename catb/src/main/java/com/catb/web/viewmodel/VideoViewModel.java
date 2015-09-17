package com.catb.web.viewmodel;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VideoViewModel {
	
	@Size(min = 0, max = 1000)
	private String caption;
	
	private Boolean display;
	
	@Pattern(regexp = "(^$|[0-9]{0,4})")
	private String sqNumber;
	
	private Integer videoCatalogId;
	
	public VideoViewModel() {
		
	}

	public VideoViewModel(String caption, Boolean display, String sqNumber,
			Integer videoCatalogId) {
		this.caption = caption;
		this.display = display;
		this.sqNumber = sqNumber;
		this.videoCatalogId = videoCatalogId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
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

	public Integer getVideoCatalogId() {
		return videoCatalogId;
	}

	public void setVideoCatalogId(Integer videoCatalogId) {
		this.videoCatalogId = videoCatalogId;
	}
}
