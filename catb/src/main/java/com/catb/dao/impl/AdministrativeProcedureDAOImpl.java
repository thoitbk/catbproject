package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.AdministrativeProcedureDAO;
import com.catb.model.AdministrativeProcedure;
import com.catb.model.AdministrativeProcedureFile;

@Repository
public class AdministrativeProcedureDAOImpl implements AdministrativeProcedureDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<AdministrativeProcedure> getAdministrativeProcedures() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM AdministrativeProcedure a ORDER BY a.sqNumber ASC, a.id DESC";
		Query query = session.createQuery(select);
		
		return (List<AdministrativeProcedure>) query.list();
	}

	public void addAdministrativeProcedure(AdministrativeProcedure administrativeProcedure) {
		Session session = sessionFactory.getCurrentSession();
		session.save(administrativeProcedure);
	}

	public void addAdministrativeProcedureFile(AdministrativeProcedureFile administrativeProcedureFile) {
		Session session = sessionFactory.getCurrentSession();
		session.save(administrativeProcedureFile);
	}

	public void updateAdministrativeProcedure(AdministrativeProcedure administrativeProcedure) {
		Session session = sessionFactory.getCurrentSession();
		session.update(administrativeProcedure);
	}

	public void updateAdministrativeProcedureFile(AdministrativeProcedureFile administrativeProcedureFile) {
		Session session = sessionFactory.getCurrentSession();
		session.update(administrativeProcedureFile);
	}

	public AdministrativeProcedure getAdministrativeProcedureById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (AdministrativeProcedure) session.get(AdministrativeProcedure.class, id);
	}

	public void deleteAdministrativeProcedure(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		AdministrativeProcedure administrativeProcedure = (AdministrativeProcedure) session.get(AdministrativeProcedure.class, id);
		if (administrativeProcedure != null) {
			session.delete(administrativeProcedure);
		}
	}

	@SuppressWarnings("unchecked")
	public List<AdministrativeProcedure> listAdministrativeProcedures() {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT a FROM AdministrativeProcedure a LEFT JOIN FETCH a.department ORDER BY a.sqNumber ASC, a.id DESC";
		Query query = session.createQuery(select);
		
		return (List<AdministrativeProcedure>) query.list();
	}

	public AdministrativeProcedureFile getAdministrativeProcedureFile(Integer fileId) {
		Session session = sessionFactory.getCurrentSession();
		return (AdministrativeProcedureFile) session.get(AdministrativeProcedureFile.class, fileId);
	}
}
