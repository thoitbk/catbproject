package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.AdCatalogDAO;
import com.catb.model.AdCatalog;

@Repository
public class AdCatalogDAOImpl implements AdCatalogDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<AdCatalog> getAdCatalogs() {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT a FROM AdCatalog a ORDER BY a.sqNumber ASC, a.id DESC";
		Query query = session.createQuery(select);
		
		return query.list();
	}

	public void addAdCatalog(AdCatalog adCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(adCatalog);
	}

	public void updateAdCatalog(AdCatalog adCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.update(adCatalog);
	}

	public AdCatalog getAdCatalogById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (AdCatalog) session.get(AdCatalog.class, id);
	}

	public void deleteAdCatalog(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		AdCatalog adCatalog = (AdCatalog) session.get(AdCatalog.class, id);
		if (adCatalog != null) {
			session.delete(adCatalog);
		}
	}

	@SuppressWarnings("unchecked")
	public List<AdCatalog> getDisplayedAdCatalogs(Integer adNum) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT a FROM AdCatalog a WHERE a.display = :display ORDER BY a.sqNumber ASC, a.id DESC";
		Query query = session.createQuery(select);
		query.setParameter("display", true);
		query.setMaxResults(adNum);
		
		return query.list();
	}
}
