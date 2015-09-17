package catb.vanthu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.UserDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.Department;
import catb.vanthu.model.User;
import catb.vanthu.valueobject.SearchUserResult;
import catb.vanthu.valueobject.SearchUserVO;

@Repository
public class UserDAOImpl implements UserDAO {
	
	static Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
	
	public void saveUser(User user) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.save(user);
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User findUserByUsername(String username) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM User WHERE username = :username";
			Query query = session.createQuery(queryStr);
			query.setParameter("username", username);
			
			List<User> users = (List<User>) query.list();
			
			session.getTransaction().commit();
			
			if (users != null && users.size() > 0) {
				User user = users.get(0);
				Hibernate.initialize(user.getDepartment());
				
				return user;
			}
			
			return null;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateUser(User user) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "UPDATE User " +
							  "SET name = :name, email = :email, phoneNumber = :phoneNumber " +
							  "WHERE id = :id";
			Query query = session.createQuery(queryStr);
			query.setParameter("name", user.getName());
			query.setParameter("email", user.getEmail());
			query.setParameter("phoneNumber", user.getPhoneNumber());
			query.setParameter("id", user.getId());
			
			query.executeUpdate();
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateUserPassword(Integer id, String password) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "UPDATE User " +
							  "SET password = :password " +
							  "WHERE id = :id";
			Query query = session.createQuery(queryStr);
			query.setParameter("password", password);
			query.setParameter("id", id);
			
			query.executeUpdate();
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM User";
			Query query = session.createQuery(queryStr);
			List<User> users = (List<User>) query.list();
			
			for (User u : users) {
				Hibernate.initialize(u.getDepartment());
			}
			
			session.getTransaction().commit();
			
			return users;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(int page, int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM User";
			Query query = session.createQuery(queryStr);
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
			
			List<User> users = (List<User>) query.list();
			
			for (User u : users) {
				Hibernate.initialize(u.getDepartment());
			}
			
			session.getTransaction().commit();
			
			return users;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserById(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM User WHERE id = :id";
			Query query = session.createQuery(queryStr);
			query.setParameter("id", id);
			
			List<User> users = (List<User>) query.list();
			
			User user = users == null || users.size() == 0 ? null : users.get(0);
			if (user != null) {
				Hibernate.initialize(user.getDepartment());
			}
			
			session.getTransaction().commit();
			
			return user;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateUser(User user, Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			User u = (User) session.get(User.class, user.getId());
			if (u != null) {
				u.setUsername(user.getUsername());
				u.setName(user.getName());
				u.setPosition(user.getPosition());
				u.setEmail(user.getEmail());
				u.setPhoneNumber(user.getPhoneNumber());
				u.setRole(user.getRole());
				
				Department department = (Department) session.get(Department.class, departmentId);
				if (department != null) {
					u.setDepartment(department);
				}
				
				session.update(u);
			}
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/*Session session = null;
	try {
		
	} catch (Exception ex) {
		logger.error("Exception : ", ex);
		if (session != null && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
		}
		return null;
	} finally {
		if (session != null) {
			session.close();
		}
	}*/
	
	@Override
	public void deleteUser(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			User user = (User) session.get(User.class, id);
			if (user != null) {
				session.delete(user);
			}
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void saveUser(User user, Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Department department = (Department) session.get(Department.class, departmentId);
			if (department != null) {
				user.setDepartment(department);
			}
			
			session.save(user);
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SearchUserResult getUsers(SearchUserVO searchUserVO, int page, int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(User.class);
			
			if (searchUserVO.getUsername() != null) {
				criteria.add(Restrictions.like("username", "%" + searchUserVO.getUsername() + "%"));
			}
			if (searchUserVO.getName() != null) {
				criteria.add(Restrictions.like("name", "%" + searchUserVO.getName() + "%"));
			}
			if (searchUserVO.getPosition() != null) {
				criteria.add(Restrictions.like("position", "%" + searchUserVO.getPosition() + "%"));
			}
			if (searchUserVO.getEmail() != null) {
				criteria.add(Restrictions.like("email", "%" + searchUserVO.getEmail() + "%"));
			}
			if (searchUserVO.getPhoneNumber() != null) {
				criteria.add(Restrictions.like("phoneNumber", "%" + searchUserVO.getPhoneNumber() + "%"));
			}
			if (searchUserVO.getDepartment() != null) {
				Department department = (Department) session.get(Department.class, searchUserVO.getDepartment());
				if (department != null) {
					criteria.add(Restrictions.eq("department", department));
				}
			}
			
			Integer resultSize = criteria.list().size();
			
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			
			List<User> users = criteria.list();
			for (User u : users) {
				Hibernate.initialize(u.getDepartment());
			}
			
			SearchUserResult searchUserResult = new SearchUserResult(users, resultSize);
			
			session.getTransaction().commit();
			
			return searchUserResult;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
