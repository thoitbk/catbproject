package com.catb.bo.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.ImageCatalogBO;
import com.catb.dao.ImageCatalogDAO;
import com.catb.dao.ImageDAO;
import com.catb.model.Image;
import com.catb.model.ImageCatalog;
import com.catb.web.viewmodel.ImageGallery;

@Service
public class ImageCatalogBOImpl implements ImageCatalogBO {
	
	@Autowired
	private ImageCatalogDAO imageCatalogDAO;
	
	@Autowired
	private ImageDAO imageDAO;
	
	@Transactional
	public void addImageCatalog(ImageCatalog imageCatalog) {
		imageCatalogDAO.addImageCatalog(imageCatalog);
	}
	
	@Transactional
	public List<ImageCatalog> getImageCatalogs() {
		return imageCatalogDAO.getImageCatalogs();
	}
	
	@Transactional
	public ImageCatalog getImageCatalogById(Integer id) {
		return imageCatalogDAO.getImageCatalogById(id);
	}
	
	@Transactional
	public void updateImageCatalog(ImageCatalog imageCatalog) {
		imageCatalogDAO.updateImageCatalog(imageCatalog);
	}
	
	@Transactional
	public void deleteImageCatalogs(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				imageCatalogDAO.deleteImageCatalog(id);
			}
		}
	}
	
	@Transactional
	public List<ImageCatalog> fetchImageCatalogs() {
		List<ImageCatalog> imageCatalogs = imageCatalogDAO.getImageCatalogs();
		if (imageCatalogs != null && imageCatalogs.size() > 0) {
			for (ImageCatalog imageCatalog : imageCatalogs) {
				Hibernate.initialize(imageCatalog.getImages());
			}
		}
		
		return imageCatalogs;
	}
	
	@Transactional
	public List<ImageGallery> fetchImageCatalogsHavingImage() {
		List<ImageCatalog> imageCatalogs = imageCatalogDAO.getImageCatalogs();
		List<ImageGallery> imageGalleries = new LinkedList<ImageGallery>();
		if (imageCatalogs != null && imageCatalogs.size() > 0) {
			for (ImageCatalog imageCatalog : imageCatalogs) {
				List<Image> images = imageDAO.getImagesByCatalogId(imageCatalog.getId());
				if (images != null && images.size() > 0) {
					imageGalleries.add(new ImageGallery(imageCatalog, images));
				}
			}
		}
		
		return imageGalleries;
	}
}
