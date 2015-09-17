package com.catb.dao;

import java.util.List;

import com.catb.model.Department;

public interface DepartmentDAO {
	
	public void addDepartment(Department department);
	public List<Department> getDepartments();
	public Department getDepartmentById(Integer id);
	public void updateDepartment(Department department);
	public void deleteDepartment(Integer id);
}
