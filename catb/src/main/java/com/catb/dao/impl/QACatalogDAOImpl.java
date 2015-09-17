package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.QACatalogDAO;
import com.catb.model.QACatalog;

@Repository
public class QACatalogDAOImpl implements QACatalogDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addQACatalog(QACatalog qaCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(qaCatalog);
	}

	@SuppressWarnings("unchecked")
	public List<QACatalog> getQACatalogs() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM QACatalog";
		
		return session.createQuery(select).list();
	}

	public QACatalog getQACatalogById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (QACatalog) session.get(QACatalog.class, id);
	}

	public void updateQACatalog(QACatalog qaCatalog) {
		Session session = sessionFactory.getCurrentSession();
		String update = "UPDATE QACatalog " + 
					    "SET name = :name, description = :description " + 
					    "WHERE id = :id";
		Query query = session.createQuery(update);
		query.setParameter("name", qaCatalog.getName());
		query.setParameter("description", qaCatalog.getDescription());
		query.setParameter("id", qaCatalog.getId());
		
		query.executeUpdate();
	}

	public void deleteQACatalog(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		QACatalog qaCatalog = (QACatalog) session.get(QACatalog.class, id);
		if (qaCatalog != null) {
			session.delete(qaCatalog);
		}
	}
}
