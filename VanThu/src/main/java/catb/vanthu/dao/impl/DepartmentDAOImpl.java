package catb.vanthu.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.DepartmentDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.Bureau;
import catb.vanthu.model.Department;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {
	
	static Logger logger = Logger.getLogger(DepartmentDAOImpl.class.getName());
	
	public void saveDepartment(Department department) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.save(department);
			
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

	public void deleteDepartment(Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "DELETE Department WHERE id = :id";
			Query query = session.createQuery(queryStr);
			query.setParameter("id", departmentId);
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

	public void updateDepartment(Department department) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "UPDATE Department " +
					"SET code = :code, name = :name, phoneNumber = :phoneNumber, email = :email, inProvince = :inProvince " +
					"WHERE id = :id";
			Query query = session.createQuery(queryStr);
			query.setParameter("code", department.getCode());
			query.setParameter("name", department.getName());
			query.setParameter("phoneNumber", department.getPhoneNumber());
			query.setParameter("email", department.getEmail());
			query.setParameter("inProvince", department.getInProvince());
			query.setParameter("id", department.getId());
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
	public List<Department> getDepartments(Boolean isInBureau) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM Department WHERE inProvince = :inProvince";
			Query query = session.createQuery(queryStr);
			query.setParameter("inProvince", isInBureau);
			
			List<Department> departments = (List<Department>) query.list();
			
			session.getTransaction().commit();
			
			return departments;
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
	public List<Department> getDepartments(int page, int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM Department d ORDER BY d.inProvince DESC";
			Query query = session.createQuery(queryStr);
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
			
			List<Department> departments = (List<Department>) query.list();
			
			session.getTransaction().commit();
			
			return departments;
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
	public List<Department> getDepartments() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM Department";
			Query query = session.createQuery(queryStr);
			
			List<Department> departments = (List<Department>) query.list();
			
			session.getTransaction().commit();
			
			return departments;
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
	public Department getDepartmentByCode(String code) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM Department WHERE code = :code";
			Query query = session.createQuery(queryStr);
			query.setParameter("code", code);
			
			List<Department> departments = (List<Department>) query.list();
			
			session.getTransaction().commit();
			
			return departments == null || departments.size() == 0 ? null : departments.get(0);
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

	public Department findDepartmentById(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Department department = (Department) session.get(Department.class, id);
			Hibernate.initialize(department.getBureau());
			
			session.getTransaction().commit();
			
			return department;
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
	public List<Department> getDepartments(List<Integer> departmentIds) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			if (departmentIds.size() != 0) {
				session.beginTransaction();
				
				String queryStr = "SELECT d FROM Department d WHERE d.id IN (:departmentIds)";
				Query query = session.createQuery(queryStr);
				query.setParameterList("departmentIds", departmentIds);
				
				List<Department> departments = (List<Department>) query.list();
				
				for (Department d : departments) {
					Hibernate.initialize(d.getUsers());
				}
				
				session.getTransaction().commit();
				
				return departments;
			}
			return new ArrayList<Department>();
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

	public void saveDepartment(Department department, Integer bureauId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Bureau bureau = (Bureau) session.get(Bureau.class, bureauId);
			department.setBureau(bureau);
			
			session.save(department);
			
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

	public void updateDepartment(Department department, Integer bureauId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Department d = (Department) session.get(Department.class, department.getId());
			d.setCode(department.getCode());
			d.setName(department.getName());
			d.setPhoneNumber(department.getPhoneNumber());
			d.setEmail(department.getEmail());
			d.setInProvince(department.getInProvince());
			
			Bureau bureau = (Bureau) session.get(Bureau.class, bureauId);
			d.setBureau(bureau);
			
			session.update(d);
			
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
}
