package com.catb.bo;

import java.util.List;

import com.catb.model.Department;

public interface DepartmentBO {
	
	public void addDepartment(Department department);
	public List<Department> getDepartments();
	public Department getDepartmentById(Integer id);
	public void updateDepartment(Department department);
	public void deleteDepartments(Integer[] ids);
}
