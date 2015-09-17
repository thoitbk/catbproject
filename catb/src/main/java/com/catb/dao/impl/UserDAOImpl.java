package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.UserDAO;
import com.catb.model.Permission;
import com.catb.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		Session session = sessionFactory.getCurrentSession();
		String query = "FROM User";
		List<User> users = session.createQuery(query).list();
		
		return users;
	}

	public void addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM User WHERE username = :username";
		Query query = session.createQuery(select);
		query.setParameter("username", username);
		List<User> users = (List<User>) query.list();
		
		return users != null && users.size() > 0 ? users.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermissionsByRole(String roleName) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT p FROM Permission AS p INNER JOIN FETCH p.roles AS r WHERE r.name = :roleName";
		Query query = session.createQuery(select);
		query.setParameter("roleName", roleName);
		List<Permission> permissions = (List<Permission>) query.list();
		
		return permissions;
	}

	public User getUserById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (User) session.get(User.class, id);
	}

	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	public void deleteUser(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String delete = "DELETE FROM User " + 
						"WHERE id = :id";
		Query query = session.createQuery(delete);
		query.setParameter("id", id);
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersByRoleId(Integer roleId) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT u " +
						"FROM User u INNER JOIN u.roles r " + 
						"WHERE r.id = :id";
		Query query = session.createQuery(select);
		query.setParameter("id", roleId);
		
		return (List<User>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersDontHaveRoleId(Integer roleId) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT u " + 
						"FROM User u " + 
						"WHERE u.id NOT IN ( " + 
										"SELECT u1.id " + 
										"FROM User u1 INNER JOIN u1.roles r " + 
										"WHERE r.id = :roleId)";
		Query query = session.createQuery(select);
		query.setParameter("roleId", roleId);
		
		return (List<User>) query.list();
	}

	@SuppressWarnings("unchecked")
	public User fetchUserByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT u FROM User u INNER JOIN FETCH u.roles r WHERE u.username = :username";
		Query query = session.createQuery(select);
		query.setParameter("username", username);
		List<User> users = (List<User>) query.list();
		
		return users != null && users.size() > 0 ? users.get(0) : null;
	}
}
