package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.DocumentTypeCatalogDAO;
import com.catb.model.DocumentTypeCatalog;

@Repository
public class DocumentTypeCatalogDAOImpl implements DocumentTypeCatalogDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(documentTypeCatalog);
	}

	@SuppressWarnings("unchecked")
	public List<DocumentTypeCatalog> getDocumentTypeCatalogs() {
		Session session = sessionFactory.getCurrentSession();
		String query = "FROM DocumentTypeCatalog";
		
		return session.createQuery(query).list();
	}

	public DocumentTypeCatalog getDocumentTypeCatalogById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		return (DocumentTypeCatalog) session.get(DocumentTypeCatalog.class, id);
	}

	public void updateDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog) {
		Session session = sessionFactory.getCurrentSession();
		String update = "UPDATE DocumentTypeCatalog " + 
			    		"SET code = :code, name = :name, sqNumber = :sqNumber, display = :display, description = :description " + 
			    		"WHERE id = :id";
		Query query = session.createQuery(update);
		query.setParameter("code", documentTypeCatalog.getCode());
		query.setParameter("name", documentTypeCatalog.getName());
		query.setParameter("sqNumber", documentTypeCatalog.getSqNumber());
		query.setParameter("display", documentTypeCatalog.getDisplay());
		query.setParameter("description", documentTypeCatalog.getDescription());
		query.setParameter("id", documentTypeCatalog.getId());
		
		query.executeUpdate();
	}

	public void deleteDocumentTypeCatalog(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		DocumentTypeCatalog documentTypeCatalog = (DocumentTypeCatalog) session.get(DocumentTypeCatalog.class, id);
		if (documentTypeCatalog != null) {
			session.delete(documentTypeCatalog);
		}
	}
}
