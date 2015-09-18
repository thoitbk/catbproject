package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.catb.dao.NewsCatalogDAO;
import com.catb.model.NewsCatalog;

@Repository
public class NewsCatalogDAOImpl implements NewsCatalogDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<NewsCatalog> getNewsCatalog(String displayLocation, Integer parent) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NewsCatalog.class, "newsCatalog");
		
		if (displayLocation != null && !"".equals(displayLocation.trim())) {
			criteria.add(Restrictions.eq("displayLocation", displayLocation.trim()));
		}
		if (parent != null && parent >= 0) {
			criteria.add(Restrictions.eq("parentId", parent));
		}
		
		criteria.addOrder(Order.asc("sqNumber"));
		criteria.addOrder(Order.asc("id"));
		
		return (List<NewsCatalog>) criteria.list();
	}

	public void addNewsCatalog(NewsCatalog newsCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(newsCatalog);
	}

	public NewsCatalog getNewsCatalogById(Integer newsCatalogId) {
		Session session = sessionFactory.getCurrentSession();
		return (NewsCatalog) session.get(NewsCatalog.class, newsCatalogId);
	}

	public void updateNewsCatalog(NewsCatalog newsCatalog) {
		Session session = sessionFactory.getCurrentSession();
		session.update(newsCatalog);
	}

	public void deleteNewsCatalog(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String delete = "DELETE FROM NewsCatalog " + 
						"WHERE id = :id";
		Query query = session.createQuery(delete);
		query.setParameter("id", id);
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<NewsCatalog> getNewsCatalogs(String displayLocation,
			Integer parentId, Integer childLevel, Boolean display) {
		Criteria criteria = buildCriteria(displayLocation, parentId, childLevel, display);
		
		return (List<NewsCatalog>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NewsCatalog> getNewsCatalogs(String displayLocation,
			Integer parentId, Integer childLevel, Boolean display, Integer size) {
		Criteria criteria = buildCriteria(displayLocation, parentId, childLevel, display);
		criteria.setMaxResults(size);
		
		criteria.setCacheable(true);
		criteria.setCacheRegion("query.newsCatalogByLocation");
		
		return (List<NewsCatalog>) criteria.list();
	}
	
	private Criteria buildCriteria(String displayLocation,
			Integer parentId, Integer childLevel, Boolean display) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NewsCatalog.class, "newsCatalog");
		
		if (displayLocation != null && !"".equals(displayLocation.trim())) {
			criteria.add(Restrictions.eq("displayLocation", displayLocation.trim()));
		}
		if (parentId != null && parentId >= 0) {
			criteria.add(Restrictions.eq("parentId", parentId));
		}
		if (childLevel != null && childLevel >= 0) {
			criteria.add(Restrictions.eq("childLevel", childLevel));
		}
		if (display != null) {
			criteria.add(Restrictions.eq("display", display));
		}
		
		criteria.addOrder(Order.asc("sqNumber"));
		criteria.addOrder(Order.asc("id"));
		
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<NewsCatalog> getNewsCatalogs(Boolean display, Boolean specialSite) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT c FROM NewsCatalog c " + 
						"WHERE c.display = :display AND c.specialSite = :specialSite " + 
						"ORDER BY sqNumber ASC, id ASC";
		
		Query query = session.createQuery(select);
		query.setParameter("display", display);
		query.setParameter("specialSite", specialSite);
		
		query.setCacheable(true);
		query.setCacheRegion("query.specialSites");
		
		return (List<NewsCatalog>) query.list();
	}

	@SuppressWarnings("unchecked")
	public NewsCatalog getNewsCatalogByUrl(String newsCatalogUrl) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT c FROM NewsCatalog c WHERE c.url = :url";
		Query query = session.createQuery(select);
		query.setParameter("url", newsCatalogUrl);
		
		List<NewsCatalog> newsCatalogs = (List<NewsCatalog>) query.list();
		return newsCatalogs != null && newsCatalogs.size() > 0 ? newsCatalogs.get(0) : null;
	}
}
