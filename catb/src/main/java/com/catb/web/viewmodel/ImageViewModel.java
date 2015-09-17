package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

public class ImageViewModel {
	
	@Size(min = 0, max = 300)
	private String caption;
	
	private Boolean display;
	
	private Integer imageCatalogId;

	public ImageViewModel() {
		
	}

	public ImageViewModel(String caption, Boolean display,
			Integer imageCatalogId) {
		this.caption = caption;
		this.display = display;
		this.imageCatalogId = imageCatalogId;
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

	public Integer getImageCatalogId() {
		return imageCatalogId;
	}

	public void setImageCatalogId(Integer imageCatalogId) {
		this.imageCatalogId = imageCatalogId;
	}
}
