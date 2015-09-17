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

import catb.vanthu.bo.DepartmentBO;
import catb.vanthu.bo.UserBO;
import catb.vanthu.model.Department;
import catb.vanthu.model.User;
import catb.vanthu.tags.PageInfo;
import catb.vanthu.util.Constants;
import catb.vanthu.util.PropertiesUtil;
import catb.vanthu.util.Util;
import catb.vanthu.validator.CreateUserValidator;
import catb.vanthu.validator.UpdateUserValidator;
import catb.vanthu.valueobject.SearchUserResult;
import catb.vanthu.valueobject.SearchUserVO;
import catb.vanthu.viewmodel.CreateUserViewModel;
import catb.vanthu.viewmodel.UpdateUserViewModel;

@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Autowired
	private CreateUserValidator createUserValidator;
	
	@Autowired
	private UpdateUserValidator updateUserValidator;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/listUsers", method = RequestMethod.GET)
	public String listUsers(
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p, ModelMap model) {
		List<User> users = userBO.getUsers(p, Constants.PAGE_SIZE);
		model.addAttribute("users", users);
		model.addAttribute("pageInfo", new PageInfo(userBO.countUsers(), p, Constants.PAGE_SIZE));
		return "user/listUsers";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/updateUser/{id}", method = RequestMethod.GET)
	public String initUpdateUserForm(@PathVariable("id") Integer id, ModelMap model) {
		UpdateUserViewModel updateUserViewModel = null;
		User user = userBO.findUserById(id);
		if (user != null) {
			updateUserViewModel = new UpdateUserViewModel(user.getId(), 
					user.getUsername(), user.getName(), user.getPosition(), 
					user.getEmail(), user.getPhoneNumber(), user.getRole(), 
					user.getDepartment().getId());
		} else {
			updateUserViewModel = new UpdateUserViewModel();
		}
		
		model.addAttribute("updateUserViewModel", updateUserViewModel);
		return "user/updateUser";
	}
	
	@ModelAttribute("departments")
	public Map<Integer, String> populateDepartments() {
		Map<Integer, String> departmentsMap = new HashMap<Integer, String>();
		List<Department> departments = departmentBO.getDepartmentsInBureau();
		for (Department d : departments) {
			departmentsMap.put(d.getId(), d.getCode());
		}
		
		return departmentsMap;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/updateUser/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateUser(
			@ModelAttribute("updateUserViewModel") UpdateUserViewModel updateUserViewModel, 
			BindingResult result, SessionStatus status, HttpSession session, HttpServletRequest request) {
		updateUserValidator.validate(updateUserViewModel, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("user/updateUser");
		} else {
			
			User user = new User(updateUserViewModel.getId(), 
					updateUserViewModel.getUsername(), 
					null, updateUserViewModel.getName(), 
					updateUserViewModel.getPosition(), 
					updateUserViewModel.getEmail(), 
					updateUserViewModel.getPhoneNumber(), 
					updateUserViewModel.getRole());
			
			userBO.updateUser(user, updateUserViewModel.getDepartment());
			
			status.setComplete();
			
			session.setAttribute("msg", PropertiesUtil.getProperty("user.update.success"));
			
			String queryStr = request.getQueryString();
			if (queryStr == null || "".equals(queryStr)) {
				return new ModelAndView(new RedirectView("/user/listUsers.html"));
			} else {
				return new ModelAndView(new RedirectView("/user/searchUsers.html?" + queryStr));
			}
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/deleteUser/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable("id") Integer id,
			@RequestParam(value = "susername", required = false, defaultValue = "") String username,
			@RequestParam(value = "sname", required = false, defaultValue = "") String name,
			@RequestParam(value = "sposition", required = false, defaultValue = "") String position,
			@RequestParam(value = "semail", required = false, defaultValue = "") String email,
			@RequestParam(value = "sphoneNumber", required = false, defaultValue = "") String phoneNumber,
			@RequestParam(value = "sdepartment", required = false, defaultValue = "-1") Integer department,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			HttpServletRequest request, HttpSession session) {
		
		SearchUserVO searchUserVO = new SearchUserVO();
		
		if (username != null && !"".equals(username)) {
			searchUserVO.setUsername(username);
		}
		if (name != null && !"".equals(name)) {
			searchUserVO.setName(name);
		}
		if (position != null && !"".equals(position)) {
			searchUserVO.setPosition(position);
		}
		if (email != null && !"".equals(email)) {
			searchUserVO.setEmail(email);
		}
		if (phoneNumber != null && !"".equals(phoneNumber)) {
			searchUserVO.setPhoneNumber(phoneNumber);
		}
		if (department != null && !department.equals(-1)) {
			searchUserVO.setDepartment(department);
		}
		
		SearchUserResult result = userBO.getUsers(searchUserVO, p, Constants.PAGE_SIZE);
		int totalItems = result.getSize();
		
		userBO.deleteUser(id);
		int newPage = 1;
		newPage = Util.evaluatePageNumAfterDelete(p, totalItems, Constants.PAGE_SIZE, 1);
		
		session.setAttribute("msg", PropertiesUtil.getProperty("user.delete.success"));
		
		if (newPage == p) {
			return new ModelAndView(new RedirectView("/user/listUsers.html" + (request.getQueryString() != null ? "?" + request.getQueryString() : "")));
		} else {
			Map<String, String> modifiedParams = new HashMap<String, String>();
			modifiedParams.put("p", String.valueOf(newPage));
			String q = Util.modifyQueryString(request, modifiedParams);
			return new ModelAndView(new RedirectView("/user/listUsers.html" + (q != null ? "?" + q : "")));
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/createUser", method = RequestMethod.GET)
	public String initCreateUserForm(ModelMap model) {
		CreateUserViewModel createUserViewModel = new CreateUserViewModel();
		createUserViewModel.setRole(2);
		createUserViewModel.setDepartment(-1);
		model.addAttribute("createUserViewModel", createUserViewModel);
		
		return "user/createUser";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/createUser", method = RequestMethod.POST)
	public ModelAndView processCreateUser(
			@ModelAttribute("createUserViewModel") CreateUserViewModel createUserViewModel,
			BindingResult result, SessionStatus status, HttpSession session) {
		createUserValidator.validate(createUserViewModel, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("user/createUser");
		} else {
			User user = new User(createUserViewModel.getUsername(), 
					createUserViewModel.getUsername(), createUserViewModel.getName(), 
					createUserViewModel.getPosition(), 
					createUserViewModel.getEmail(), 
					createUserViewModel.getPhoneNumber(), 
					createUserViewModel.getRole());
			userBO.saveUser(user, createUserViewModel.getDepartment());
			status.setComplete();
			
			session.setAttribute("msg", PropertiesUtil.getProperty("user.create.success"));
			
			return new ModelAndView(new RedirectView("/user/listUsers.html"));
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/searchUsers", method = RequestMethod.GET)
	public String searchUsers(
			@RequestParam(value = "susername", required = false, defaultValue = "") String username,
			@RequestParam(value = "sname", required = false, defaultValue = "") String name,
			@RequestParam(value = "sposition", required = false, defaultValue = "") String position,
			@RequestParam(value = "semail", required = false, defaultValue = "") String email,
			@RequestParam(value = "sphoneNumber", required = false, defaultValue = "") String phoneNumber,
			@RequestParam(value = "sdepartment", required = false, defaultValue = "-1") Integer department,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			ModelMap model, HttpServletRequest request) {
		
		SearchUserVO searchUserVO = new SearchUserVO();
		Map<String, String> params = new HashMap<String, String>();
		
		if (username != null && !"".equals(username)) {
			searchUserVO.setUsername(username);
			params.put("susername", username);
		}
		if (name != null && !"".equals(name)) {
			searchUserVO.setName(name);
			params.put("sname", name);
		}
		if (position != null && !"".equals(position)) {
			searchUserVO.setPosition(position);
			params.put("sposition", position);
		}
		if (email != null && !"".equals(email)) {
			searchUserVO.setEmail(email);
			params.put("semail", email);
		}
		if (phoneNumber != null && !"".equals(phoneNumber)) {
			searchUserVO.setPhoneNumber(phoneNumber);
			params.put("sphoneNumber", phoneNumber);
		}
		if (department != null && !department.equals(-1)) {
			searchUserVO.setDepartment(department);
			params.put("sdepartment", String.valueOf(department));
		}
		
		SearchUserResult result = userBO.getUsers(searchUserVO, p, Constants.PAGE_SIZE);
		
		model.addAttribute("users", result.getUsers());
		model.addAttribute("pageInfo", new PageInfo(result.getSize(), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "user/listUsers";
	}
}
