package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.LinkCatalogDAO;
import com.catb.model.LinkCatalog;

@Repository
public class LinkCatalogDAOImpl implements LinkCatalogDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addLinkCatalog(LinkCatalog linkCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(linkCatalog);
	}

	public void updateLinkCatalog(LinkCatalog linkCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.update(linkCatalog);
	}

	public void deleteLinkCatalog(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String delete = "DELETE FROM LinkCatalog WHERE id = :id";
		Query query = session.createQuery(delete);
		query.setParameter("id", id);
		
		query.executeUpdate();
	}

	public LinkCatalog getLinkCatalogById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (LinkCatalog) session.get(LinkCatalog.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<LinkCatalog> getLinkCatalogs() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM LinkCatalog ORDER BY sqNumber ASC, id DESC";
		Query query = session.createQuery(select);
		
		return (List<LinkCatalog>) query.list();
	}
}
