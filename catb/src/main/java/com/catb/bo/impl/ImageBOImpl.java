package com.catb.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.ImageBO;
import com.catb.dao.ImageCatalogDAO;
import com.catb.dao.ImageDAO;
import com.catb.model.Image;
import com.catb.model.ImageCatalog;

@Service
public class ImageBOImpl implements ImageBO {
	
	@Autowired
	private ImageDAO imageDAO;
	
	@Autowired
	private ImageCatalogDAO imageCatalogDAO;
	
	@Transactional
	public List<Image> getImages(Integer imageCatalogId, Integer page, Integer pageSize) {
		return imageDAO.getImages(imageCatalogId, page, pageSize);
	}
	
	@Transactional
	public Long countImages(Integer imageCatalogId) {
		return imageDAO.countImages(imageCatalogId);
	}
	
	@Transactional
	public void addImage(Image image, Integer imageCatalogId) {
		if (imageCatalogId != null && imageCatalogId >= 0) {
			ImageCatalog imageCatalog = imageCatalogDAO.getImageCatalogById(imageCatalogId);
			if (imageCatalog != null) {
				image.setImageCatalog(imageCatalog);
				imageCatalog.getImages().add(image);
			}
		}
		
		imageDAO.addImage(image);
	}
	
	@Transactional
	public Image fetchImageById(Integer id) {
		Image image = null;
		if (id != null && id >= 0) {
			image = imageDAO.getImageById(id);
			if (image != null) {
				Hibernate.initialize(image.getImageCatalog());
			}
		}
		
		return image;
	}
	
	@Transactional
	public void updateImage(Image image, Integer imageCatalogId) {
		Image img = imageDAO.getImageById(image.getId());
		if (img != null) {
			img.setCaption(image.getCaption());
			img.setDisplay(image.getDisplay());
			img.setFile(image.getFile());
			if (imageCatalogId != null) {
				ImageCatalog imageCatalog = imageCatalogDAO.getImageCatalogById(imageCatalogId);
				if (imageCatalog != null) {
					img.setImageCatalog(imageCatalog);
					imageCatalog.getImages().add(img);
				}
			}
			
			imageDAO.updateImage(img);
		}
	}
	
	@Transactional
	public void deleteImages(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				imageDAO.deleteImage(id);
			}
		}
	}
	
	@Transactional
	public List<Image> getImages(Integer amount) {
		return imageDAO.getImages(amount);
	}
	
	@Transactional
	public List<Image> getImagesByCatalogId(Integer catalogId) {
		return imageDAO.getImagesByCatalogId(catalogId);
	}
}
