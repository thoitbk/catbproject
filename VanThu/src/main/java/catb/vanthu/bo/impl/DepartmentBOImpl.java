package catb.vanthu.bo.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.DepartmentBO;
import catb.vanthu.dao.DepartmentDAO;
import catb.vanthu.model.Department;
import catb.vanthu.model.User;

@Service
public class DepartmentBOImpl implements DepartmentBO {
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	public List<Department> getDepartmentsInBureau() {
		return departmentDAO.getDepartments(true);
	}

	public List<Department> getDepartments(int page, int pageSize) {
		return departmentDAO.getDepartments(page, pageSize);
	}

	public int countDepartments() {
		return departmentDAO.getDepartments().size();
	}

	public Department getDepartmentByCode(String code) {
		return departmentDAO.getDepartmentByCode(code);
	}

	public void saveDepartment(Department department) {
		departmentDAO.saveDepartment(department);
	}

	public Department findDepartmentById(Integer id) {
		return departmentDAO.findDepartmentById(id);
	}

	public void updateDepartment(Department department) {
		departmentDAO.updateDepartment(department);
	}

	public void deleteDepartment(Integer departmentId) {
		departmentDAO.deleteDepartment(departmentId);
	}

	public List<Department> getDepartments() {
		return departmentDAO.getDepartments();
	}

	public List<String> getEmailsOfDepartments(List<Integer> departmentIds) {
		Set<String> emails = new HashSet<String>();
		List<Department> departments = departmentDAO.getDepartments(departmentIds);
		for (Department d : departments) {
			if (d.getEmail() != null && !"".equals(d.getEmail())) {
				emails.add(d.getEmail());
			}
			for (User u : d.getUsers()) {
				if (u.getEmail() != null && !"".equals(u.getEmail())) {
					emails.add(u.getEmail());
				}
			}
		}
		
		return new LinkedList<String>(emails);
	}

	public List<Department> getDepartments(List<Integer> departmentIds) {
		return departmentDAO.getDepartments(departmentIds);
	}

	public void saveDepartment(Department department, Integer bureauId) {
		departmentDAO.saveDepartment(department, bureauId);
	}

	public void updateDepartment(Department department, Integer bureauId) {
		departmentDAO.updateDepartment(department, bureauId);
	}

	public List<Department> getDepartmentsNotInBureau() {
		return departmentDAO.getDepartments(false);
	}
}
