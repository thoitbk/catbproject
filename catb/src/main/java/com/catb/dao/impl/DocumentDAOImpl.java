package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.DocumentDAO;
import com.catb.model.Document;
import com.catb.model.DocumentFile;

@Repository
public class DocumentDAOImpl implements DocumentDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Document> getDocuments() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM Document d ORDER BY d.sqNumber ASC, d.id DESC";
		Query query = session.createQuery(select);
		
		return (List<Document>) query.list();
	}

	public void addDocument(Document document) {
		Session session = sessionFactory.getCurrentSession();
		session.save(document);
	}

	public void addDocumentFile(DocumentFile documentFile) {
		Session session = sessionFactory.getCurrentSession();
		session.save(documentFile);
	}

	public void updateDocument(Document document) {
		Session session = sessionFactory.getCurrentSession();
		session.update(document);
	}

	public void updateDocumentFile(DocumentFile documentFile) {
		Session session = sessionFactory.getCurrentSession();
		session.update(documentFile);
	}

	public Document getDocumentById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Document) session.get(Document.class, id);
	}

	public void deleteDocument(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Document document = (Document) session.get(Document.class, id);
		if (document != null) {
			session.delete(document);
		}
	}

	public DocumentFile getDocumentFileById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (DocumentFile) session.get(DocumentFile.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Document> listDocuments() {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT d FROM Document d LEFT JOIN FETCH d.department ORDER BY d.sqNumber ASC, d.id DESC";
		Query query = session.createQuery(select);
		
		return (List<Document>) query.list();
	}
}
