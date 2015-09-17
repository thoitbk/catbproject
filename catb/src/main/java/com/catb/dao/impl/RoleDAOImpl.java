package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.RoleDAO;
import com.catb.model.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		Session session = sessionFactory.getCurrentSession();
		String query = "FROM Role";
		
		return session.createQuery(query).list();
	}

	public void addRole(Role role) {
		Session session = sessionFactory.getCurrentSession();
		session.save(role);
	}

	public Role getRoleById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Role) session.get(Role.class, id);
	}

	@SuppressWarnings("unchecked")
	public Role getRoleByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM Role r WHERE r.name = :name";
		Query query = session.createQuery(select);
		query.setParameter("name", name);
		
		List<Role> roles = (List<Role>) query.list();
		return roles != null && roles.size() > 0 ? roles.get(0) : null;
	}

	public void updateRole(Role role) {
		Session session = sessionFactory.getCurrentSession();
		session.update(role);
	}

	public void deleteRole(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String delete = "DELETE FROM Role " + 
						"WHERE id = :id";
		Query query = session.createQuery(delete);
		query.setParameter("id", id);
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Role fetchRoleById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT r " + 
						"FROM Role r INNER JOIN FETCH r.permissions p " + 
						"WHERE r.id = :id";
		Query query = session.createQuery(select);
		query.setParameter("id", id);
		
		List<Role> roles = (List<Role>) query.list();
		
		return roles != null && roles.size() > 0 ? roles.get(0) : null;
	}
}
