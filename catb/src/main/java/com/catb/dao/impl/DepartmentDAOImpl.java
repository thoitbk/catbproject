package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.DepartmentDAO;
import com.catb.model.Department;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addDepartment(Department department) {
		Session session = sessionFactory.getCurrentSession();
		session.save(department);
	}

	@SuppressWarnings("unchecked")
	public List<Department> getDepartments() {
		Session session = sessionFactory.getCurrentSession();
		String query = "FROM Department";
		
		return session.createQuery(query).list();
	}

	public Department getDepartmentById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Department department = (Department) session.get(Department.class, id);
		
		return department;
	}

	public void updateDepartment(Department department) {
		Session session = sessionFactory.getCurrentSession();
		String update = "UPDATE Department " + 
					    "SET code = :code, name = :name, phone = :phone, fax = :fax, description = :description " + 
					    "WHERE id = :id";
		Query query = session.createQuery(update);
		query.setParameter("code", department.getCode());
		query.setParameter("name", department.getName());
		query.setParameter("phone", department.getPhone());
		query.setParameter("fax", department.getFax());
		query.setParameter("description", department.getDescription());
		query.setParameter("id", department.getId());
		
		query.executeUpdate();
	}

	public void deleteDepartment(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String delete = "DELETE FROM Department " + 
						"WHERE id = :id";
		Query query = session.createQuery(delete);
		query.setParameter("id", id);
		
		query.executeUpdate();
	}

}
