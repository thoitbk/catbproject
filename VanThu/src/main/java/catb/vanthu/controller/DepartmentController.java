package catb.vanthu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import catb.vanthu.bo.BureauBO;
import catb.vanthu.bo.DepartmentBO;
import catb.vanthu.model.Bureau;
import catb.vanthu.model.Department;
import catb.vanthu.tags.PageInfo;
import catb.vanthu.util.Constants;
import catb.vanthu.util.PropertiesUtil;
import catb.vanthu.util.Util;
import catb.vanthu.validator.CreateDepartmentValidator;
import catb.vanthu.validator.UpdateDepartmentValidator;
import catb.vanthu.viewmodel.CreateDepartmentViewModel;
import catb.vanthu.viewmodel.UpdateDepartmentViewModel;

@Controller
public class DepartmentController {
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Autowired
	private BureauBO bureauBO;
	
	@Autowired
	private CreateDepartmentValidator createDepartmentValidator;
	
	@Autowired
	private UpdateDepartmentValidator updateDepartmentValidator;
	
	@ModelAttribute("bureaus")
	public Map<Integer, String> populateBureaus() {
		Map<Integer, String> bureaus = new HashMap<Integer, String>();
		List<Bureau> list = bureauBO.getBureaus();
		
		for (Bureau bureau : list) {
			bureaus.put(bureau.getId(), bureau.getCode());
		}
		
		return bureaus;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/department/listDepartments", method = RequestMethod.GET)
	public String listDepartments(
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p, ModelMap model) {
		List<Department> departments = departmentBO.getDepartments(p, Constants.PAGE_SIZE);
		model.addAttribute("departments", departments);
		model.addAttribute("pageInfo", new PageInfo(departmentBO.countDepartments(), p, Constants.PAGE_SIZE));
		return "department/listDepartments";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/department/createDepartment", method = RequestMethod.GET)
	public String initCreateDepartmentForm(ModelMap model) {
		CreateDepartmentViewModel createDepartmentViewModel = new CreateDepartmentViewModel();
		createDepartmentViewModel.setInProvince(true);
		model.addAttribute("createDepartmentViewModel", createDepartmentViewModel);
		
		return "department/createDepartment";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/department/createDepartment", method = RequestMethod.POST)
	public ModelAndView processCreateDepartment(
			@ModelAttribute("createDepartmentViewModel") CreateDepartmentViewModel createDepartmentViewModel,
			BindingResult result, SessionStatus status, HttpSession session) {
		createDepartmentValidator.validate(createDepartmentViewModel, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("department/createDepartment");
		} else {
			Department department = new Department(createDepartmentViewModel.getCode(), 
					createDepartmentViewModel.getName(), 
					createDepartmentViewModel.getPhoneNumber(), 
					createDepartmentViewModel.getEmail(), 
					createDepartmentViewModel.getInProvince());
			
			departmentBO.saveDepartment(department, createDepartmentViewModel.getBureau());
			
			status.setComplete();
			session.setAttribute("msg", PropertiesUtil.getProperty("department.create.success"));
			
			return new ModelAndView(new RedirectView("/department/listDepartments.html"));
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/department/updateDepartment/{id}", method = RequestMethod.GET)
	public String initUpdateDepartmentForm(@PathVariable("id") Integer id, ModelMap model) {
		UpdateDepartmentViewModel updateDepartmentViewModel = null;
		Department department = departmentBO.findDepartmentById(id);
		if (department != null) {
			updateDepartmentViewModel = new UpdateDepartmentViewModel(
					department.getId(), department.getCode(),
					department.getName(), department.getPhoneNumber(),
					department.getEmail(), department.getInProvince(), department.getBureau().getId());
		} else {
			updateDepartmentViewModel = new UpdateDepartmentViewModel();
		}
		
		model.addAttribute("updateDepartmentViewModel", updateDepartmentViewModel);
		
		return "department/updateDepartment";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/department/updateDepartment/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateDepartment(
			@ModelAttribute("updateDepartmentViewModel") UpdateDepartmentViewModel updateDepartmentViewModel,
			BindingResult result, SessionStatus status, HttpSession session, HttpServletRequest request) {
		updateDepartmentValidator.validate(updateDepartmentViewModel, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("department/updateDepartment");
		} else {
			Department department = new Department(updateDepartmentViewModel.getId(), 
					updateDepartmentViewModel.getCode(), updateDepartmentViewModel.getName(), 
					updateDepartmentViewModel.getPhoneNumber(), updateDepartmentViewModel.getEmail(), 
					updateDepartmentViewModel.getInProvince());
			departmentBO.updateDepartment(department, updateDepartmentViewModel.getBureau());
			
			status.setComplete();
			session.setAttribute("msg", PropertiesUtil.getProperty("department.update.success"));
			String queryStr = request.getQueryString();
			if (queryStr == null || "".equals(queryStr)) {
				return new ModelAndView(new RedirectView("/department/listDepartments.html"));
			} else {
				return new ModelAndView(new RedirectView("/department/listDepartments.html?" + queryStr));
			}
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/department/deleteDepartment/{id}", method = RequestMethod.GET)
	public ModelAndView deleteDepartment(@PathVariable("id") Integer id,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			HttpServletRequest request, HttpSession session) {
		
		int totalItems = departmentBO.countDepartments();
		departmentBO.deleteDepartment(id);
		int newPage = 1;
		newPage = Util.evaluatePageNumAfterDelete(p, totalItems, Constants.PAGE_SIZE, 1);
		
		session.setAttribute("msg", PropertiesUtil.getProperty("department.delete.success"));
		if (newPage == p) {
			return new ModelAndView(new RedirectView("/department/listDepartments.html" + (request.getQueryString() != null ? "?" + request.getQueryString() : "")));
		} else {
			Map<String, String> modifiedParams = new HashMap<String, String>();
			modifiedParams.put("p", String.valueOf(newPage));
			String q = Util.modifyQueryString(request, modifiedParams);
			return new ModelAndView(new RedirectView("/department/listDepartments.html" + (q != null ? "?" + q : "")));
		}
	}
}
