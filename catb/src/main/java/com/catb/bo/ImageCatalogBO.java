package com.catb.bo;

import java.util.List;

import com.catb.model.ImageCatalog;
import com.catb.web.viewmodel.ImageGallery;

public interface ImageCatalogBO {
	
	public void addImageCatalog(ImageCatalog imageCatalog);
	public List<ImageCatalog> getImageCatalogs();
	public ImageCatalog getImageCatalogById(Integer id);
	public void updateImageCatalog(ImageCatalog imageCatalog);
	public void deleteImageCatalogs(Integer[] ids);
	public List<ImageCatalog> fetchImageCatalogs();
	public List<ImageGallery> fetchImageCatalogsHavingImage();
}
