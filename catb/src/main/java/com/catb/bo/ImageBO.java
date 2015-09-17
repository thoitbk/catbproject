package com.catb.bo;

import java.util.List;

import com.catb.model.Image;

public interface ImageBO {
	
	public List<Image> getImages(Integer imageCatalogId, Integer page, Integer pageSize);
	public Long countImages(Integer imageCatalogId);
	public void addImage(Image image, Integer imageCatalogId);
	public Image fetchImageById(Integer id);
	public void updateImage(Image image, Integer imageCatalogId);
	public void deleteImages(Integer[] ids);
	public List<Image> getImages(Integer amount);
	public List<Image> getImagesByCatalogId(Integer catalogId);
}
