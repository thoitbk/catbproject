package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.ImageCatalogDAO;
import com.catb.model.ImageCatalog;

@Repository
public class ImageCatalogDAOImpl implements ImageCatalogDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addImageCatalog(ImageCatalog imageCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(imageCatalog);
	}

	@SuppressWarnings("unchecked")
	public List<ImageCatalog> getImageCatalogs() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM ImageCatalog";
		
		return session.createQuery(select).list();
	}

	public ImageCatalog getImageCatalogById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (ImageCatalog) session.get(ImageCatalog.class, id);
	}

	public void updateImageCatalog(ImageCatalog imageCatalog) {
		Session session = sessionFactory.getCurrentSession();
		String update = "UPDATE ImageCatalog " + 
						"SET name = :name, description = :description " + 
						"WHERE id = :id";
		Query query = session.createQuery(update);
		query.setParameter("name", imageCatalog.getName());
		query.setParameter("description", imageCatalog.getDescription());
		query.setParameter("id", imageCatalog.getId());
		
		query.executeUpdate();
	}

	public void deleteImageCatalog(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		ImageCatalog imageCatalog = (ImageCatalog) session.get(ImageCatalog.class, id);
		if (imageCatalog != null) {
			session.delete(imageCatalog);
		}
	}
}
