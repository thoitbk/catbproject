package com.catb.dao;

import java.util.List;

import com.catb.model.ImageCatalog;

public interface ImageCatalogDAO {
	
	public void addImageCatalog(ImageCatalog imageCatalog);
	public List<ImageCatalog> getImageCatalogs();
	public ImageCatalog getImageCatalogById(Integer id);
	public void updateImageCatalog(ImageCatalog imageCatalog);
	public void deleteImageCatalog(Integer id);
}
