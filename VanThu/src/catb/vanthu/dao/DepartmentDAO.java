package catb.vanthu.dao;

import java.util.List;

import catb.vanthu.model.Department;


public interface DepartmentDAO {
	
	public void saveDepartment(Department department);
	public void saveDepartment(Department department, Integer bureauId);
	public void deleteDepartment(Integer departmentId);
	public void updateDepartment(Department department);
	public void updateDepartment(Department department, Integer bureauId);
	public List<Department> getDepartments(Boolean isInBureau);
	public List<Department> getDepartments(int page, int pageSize);
	public List<Department> getDepartments();
	public Department getDepartmentByCode(String code);
	public Department findDepartmentById(Integer id);
	public List<Department> getDepartments(List<Integer> departmentIds);
}
