package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.VideoCatalogDAO;
import com.catb.model.VideoCatalog;

@Repository
public class VideoCatalogDAOImpl implements VideoCatalogDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addVideoCatalog(VideoCatalog videoCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(videoCatalog);
	}

	@SuppressWarnings("unchecked")
	public List<VideoCatalog> getVideoCatalogs() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM VideoCatalog";
		
		return session.createQuery(select).list();
	}

	public VideoCatalog getVideoCatalogById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (VideoCatalog) session.get(VideoCatalog.class, id);
	}

	public void updateVideoCatalog(VideoCatalog videoCatalog) {
		Session session = sessionFactory.getCurrentSession();
		String update = "UPDATE VideoCatalog " + 
						"SET name = :name, description = :description " + 
						"WHERE id = :id";
		
		Query query = session.createQuery(update);
		query.setParameter("name", videoCatalog.getName());
		query.setParameter("description", videoCatalog.getDescription());
		query.setParameter("id", videoCatalog.getId());
		
		query.executeUpdate();
	}

	public void deleteVideoCatalog(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		VideoCatalog videoCatalog = (VideoCatalog) session.get(VideoCatalog.class, id);
		if (videoCatalog != null) {
			session.delete(videoCatalog);
		}
	}
}
