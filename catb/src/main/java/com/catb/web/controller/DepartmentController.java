package com.catb.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.catb.bo.DepartmentBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.Department;
import com.catb.web.viewmodel.DepartmentViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class DepartmentController {
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@RequiresPermissions(value = {"department:manage"})
	@RequestMapping(value = "/cm/department/add", method = RequestMethod.GET)
	public ModelAndView showCreateDepartment(ModelMap model) {
		DepartmentViewModel departmentViewModel = new DepartmentViewModel();
		model.addAttribute("departmentViewModel", departmentViewModel);
		
		List<Department> departments = departmentBO.getDepartments();
		model.addAttribute("departments", departments);
		
		return new ModelAndView("cm/department/add");
	}
	
	@RequiresPermissions(value = {"department:manage"})
	@RequestMapping(value = "/cm/department/add", method = RequestMethod.POST)
	public ModelAndView processCreateDepartment(
			@Valid @ModelAttribute("departmentViewModel") DepartmentViewModel departmentViewModel,
			BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			List<Department> departments = departmentBO.getDepartments();
			model.addAttribute("departments", departments);
			
			return new ModelAndView("cm/department/add");
		} else {
			Department department = new Department(
					departmentViewModel.getCode(), 
					departmentViewModel.getName(), 
					departmentViewModel.getPhone(), 
					departmentViewModel.getFax(), 
					departmentViewModel.getDescription());
			
			departmentBO.addDepartment(department);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("department.created.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/department/add"));
		}
	}
	
	@RequiresPermissions(value = {"department:manage"})
	@RequestMapping(value = "/cm/department/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateDepartment(@PathVariable("id") Integer id, ModelMap model) {
		DepartmentViewModel departmentViewModel = new DepartmentViewModel();
		Department department = departmentBO.getDepartmentById(id);
		if (department != null) {
			departmentViewModel.setCode(department.getCode());
			departmentViewModel.setName(department.getName());
			departmentViewModel.setPhone(department.getPhone());
			departmentViewModel.setFax(department.getFax());
			departmentViewModel.setDescription(department.getDescription());
		}
		model.addAttribute("departmentViewModel", departmentViewModel);
		
		List<Department> departments = departmentBO.getDepartments();
		model.addAttribute("departments", departments);
		
		return new ModelAndView("cm/department/update");
	}
	
	@RequiresPermissions(value = {"department:manage"})
	@RequestMapping(value = "/cm/department/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateDepartment(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("departmentViewModel") DepartmentViewModel departmentViewModel,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<Department> departments = departmentBO.getDepartments();
			model.addAttribute("departments", departments);
			
			return new ModelAndView("cm/department/update");
		} else {
			Department department = new Department(id, 
					departmentViewModel.getCode(), 
					departmentViewModel.getName(), 
					departmentViewModel.getPhone(), 
					departmentViewModel.getFax(), 
					departmentViewModel.getDescription());
			
			departmentBO.updateDepartment(department);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("department.updated.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/department/add"));
		}
	}
	
	@RequiresPermissions(value = {"department:manage"})
	@RequestMapping(value = "/cm/department/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteDepartment(@RequestParam("ids") Integer[] ids, HttpSession session) {
		departmentBO.deleteDepartments(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("department.deleted.success"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
