package com.catb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.PermissionDAO;
import com.catb.model.Permission;

@Repository
public class PermissionDAOImpl implements PermissionDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermissions() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM Permission";
		
		return session.createQuery(select).list();
	}

	public void addPermission(Permission permission) {
		Session session = sessionFactory.getCurrentSession();
		session.save(permission);
	}

	public void updatePermission(Permission permission) {
		Session session = sessionFactory.getCurrentSession();
		session.update(permission);
	}

	public void deletePermission(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String delete = "DELETE FROM Permission " + 
						"WHERE id = :id";
		Query query = session.createQuery(delete);
		query.setParameter("id", id);
		
		query.executeUpdate();
	}

	public Permission getPermissionById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Permission) session.get(Permission.class, id);
	}

	@SuppressWarnings("unchecked")
	public Permission getPermissionByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM Permission " + 
						"WHERE name = :name";
		Query query = session.createQuery(select);
		query.setParameter("name", name);
		
		List<Permission> permissions = (List<Permission>) query.list();
		
		return permissions != null && permissions.size() > 0 ? permissions.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public Permission getPermissionByPerStr(String perStr) {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM Permission " + 
						"WHERE perStr = :perStr";
		Query query = session.createQuery(select);
		query.setParameter("perStr", perStr);
		
		List<Permission> permissions = (List<Permission>) query.list();
		
		return permissions != null && permissions.size() > 0 ? permissions.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermissionsByRoleId(Integer roleId) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT p " +
						"FROM Permission p INNER JOIN FETCH p.roles r " + 
						"WHERE r.id = :roleId";
		Query query = session.createQuery(select);
		query.setParameter("roleId", roleId);
		
		return (List<Permission>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermissionsByIds(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			Session session = sessionFactory.getCurrentSession();
			String select = "SELECT p " + 
							"FROM Permission p " +
							"WHERE p.id IN (:ids)";
			Query query = session.createQuery(select);
			query.setParameterList("ids", ids);
			
			return (List<Permission>) query.list();
		}
		
		return new ArrayList<Permission>();
	}
}
