package com.catb.web.viewmodel;

import java.util.List;

import com.catb.model.Image;
import com.catb.model.ImageCatalog;

public class ImageGallery {
	
	private ImageCatalog imageCatalog;
	private List<Image> images;
	
	public ImageGallery() {
		
	}

	public ImageGallery(ImageCatalog imageCatalog, List<Image> images) {
		this.imageCatalog = imageCatalog;
		this.images = images;
	}

	public ImageCatalog getImageCatalog() {
		return imageCatalog;
	}

	public void setImageCatalog(ImageCatalog imageCatalog) {
		this.imageCatalog = imageCatalog;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
}
