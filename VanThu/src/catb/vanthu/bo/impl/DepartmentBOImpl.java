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
	
	@Override
	public List<Department> getDepartmentsInBureau() {
		return departmentDAO.getDepartments(true);
	}

	@Override
	public List<Department> getDepartments(int page, int pageSize) {
		return departmentDAO.getDepartments(page, pageSize);
	}

	@Override
	public int countDepartments() {
		return departmentDAO.getDepartments().size();
	}

	@Override
	public Department getDepartmentByCode(String code) {
		return departmentDAO.getDepartmentByCode(code);
	}

	@Override
	public void saveDepartment(Department department) {
		departmentDAO.saveDepartment(department);
	}

	@Override
	public Department findDepartmentById(Integer id) {
		return departmentDAO.findDepartmentById(id);
	}

	@Override
	public void updateDepartment(Department department) {
		departmentDAO.updateDepartment(department);
	}

	@Override
	public void deleteDepartment(Integer departmentId) {
		departmentDAO.deleteDepartment(departmentId);
	}

	@Override
	public List<Department> getDepartments() {
		return departmentDAO.getDepartments();
	}

	@Override
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

	@Override
	public List<Department> getDepartments(List<Integer> departmentIds) {
		return departmentDAO.getDepartments(departmentIds);
	}

	@Override
	public void saveDepartment(Department department, Integer bureauId) {
		departmentDAO.saveDepartment(department, bureauId);
	}

	@Override
	public void updateDepartment(Department department, Integer bureauId) {
		departmentDAO.updateDepartment(department, bureauId);
	}

	@Override
	public List<Department> getDepartmentsNotInBureau() {
		return departmentDAO.getDepartments(false);
	}
}
