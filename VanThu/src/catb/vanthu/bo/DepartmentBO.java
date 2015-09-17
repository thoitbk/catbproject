package catb.vanthu.bo;

import java.util.List;

import catb.vanthu.model.Department;

public interface DepartmentBO {
	
	public List<Department> getDepartmentsInBureau();
	public List<Department> getDepartmentsNotInBureau();
	public List<Department> getDepartments(int page, int pageSize);
	public int countDepartments();
	public Department getDepartmentByCode(String code);
	public void saveDepartment(Department department);
	public void saveDepartment(Department department, Integer bureauId);
	public Department findDepartmentById(Integer id);
	public void updateDepartment(Department department);
	public void deleteDepartment(Integer departmentId);
	public List<Department> getDepartments();
	public List<String> getEmailsOfDepartments(List<Integer> departmentIds);
	public List<Department> getDepartments(List<Integer> departmentIds);
	public void updateDepartment(Department department, Integer bureauId);
}
