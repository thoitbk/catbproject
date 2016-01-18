package com.catb.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.DepartmentBO;
import com.catb.dao.DepartmentDAO;
import com.catb.dao.UserDAO;
import com.catb.model.Department;
import com.catb.model.User;

@Service
public class DepartmentBOImpl implements DepartmentBO {
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Transactional
	public void addDepartment(Department department) {
		departmentDAO.addDepartment(department);
	}
	
	@Transactional
	public List<Department> getDepartments() {
		return departmentDAO.getDepartments();
	}
	
	@Transactional
	public Department getDepartmentById(Integer id) {
		return departmentDAO.getDepartmentById(id);
	}
	
	@Transactional
	public void updateDepartment(Department department) {
		departmentDAO.updateDepartment(department);
	}
	
	@Transactional
	public void deleteDepartments(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				departmentDAO.deleteDepartment(id);
			}
		}
	}
	
	@Transactional
	public Department getDepartmentByUserId(Integer userId) {
		User user = userDAO.getUserById(userId);
		if (user != null) {
			Hibernate.initialize(user.getDepartment());
			return user.getDepartment();
		}
		
		return null;
	}
}
