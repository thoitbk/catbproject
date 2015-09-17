package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.catb.dao.CriminalDenouncementDAO;
import com.catb.model.CriminalDenouncement;

@Repository
public class CriminalDenouncementDAOImpl implements CriminalDenouncementDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addCriminalDenouncement(CriminalDenouncement criminalDenouncement) {
		Session session = sessionFactory.getCurrentSession();
		session.save(criminalDenouncement);
	}

	@SuppressWarnings("unchecked")
	public List<CriminalDenouncement> getCriminalDenouncements(String title, Integer page, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, title);
		
		criteria.setFirstResult((page - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		
		criteria.addOrder(Order.desc("id"));
		
		return criteria.list();
	}
	
	private Criteria buildCriteria(Session session, String title) {
		Criteria criteria = session.createCriteria(CriminalDenouncement.class, "criminalDenouncement");
		
		if (title != null && !"".equals(title.trim())) {
			criteria.add(Restrictions.like("criminalDenouncement.title", "%" + title.trim() + "%"));
		}
		
		return criteria;
	}

	public Long countCriminalDenouncements(String title) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, title);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	public CriminalDenouncement getCriminalDenouncement(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (CriminalDenouncement) session.get(CriminalDenouncement.class, id);
	}

	public void updateCriminalDenouncement(CriminalDenouncement criminalDenouncement) {
		Session session = sessionFactory.getCurrentSession();
		session.update(criminalDenouncement);
	}

	public void deleteCriminalDenouncement(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		CriminalDenouncement criminalDenouncement = (CriminalDenouncement) session.get(CriminalDenouncement.class, id);
		if (criminalDenouncement != null) {
			session.delete(criminalDenouncement);
		}
	}
}
