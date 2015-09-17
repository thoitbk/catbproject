package com.catb.dao;

import java.util.List;

import com.catb.model.Image;

public interface ImageDAO {
	
	public List<Image> getImages(Integer imageCatalogId, Integer page, Integer pageSize);
	public Long countImages(Integer imageCatalogId);
	public void addImage(Image image);
	public Image getImageById(Integer id);
	public void updateImage(Image image);
	public void deleteImage(Integer id);
	public List<Image> getImages(Integer amount);
	public List<Image> getImagesByCatalogId(Integer catalogId);
}
