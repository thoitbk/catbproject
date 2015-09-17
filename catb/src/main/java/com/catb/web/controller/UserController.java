package com.catb.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authc.credential.PasswordService;
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
import com.catb.bo.PositionBO;
import com.catb.bo.UserBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.Department;
import com.catb.model.Position;
import com.catb.model.User;
import com.catb.vo.UserInfo;
import com.catb.web.validator.ChangePasswordValidator;
import com.catb.web.validator.CreateUserValidator;
import com.catb.web.validator.EditUserValidator;
import com.catb.web.validator.UpdateUserValidator;
import com.catb.web.viewmodel.ChangePasswordViewModel;
import com.catb.web.viewmodel.EditUserViewModel;
import com.catb.web.viewmodel.Status;
import com.catb.web.viewmodel.UpdateUserViewModel;
import com.catb.web.viewmodel.UserViewModel;

@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PositionBO positionBO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private CreateUserValidator createUserValidator;
	
	@Autowired
	private EditUserValidator editUserValidator;
	
	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	
	@Autowired
	private UpdateUserValidator updateUserValidator;
	
	@ModelAttribute("positions")
	public Map<Integer, String> populatePosition() {
		Map<Integer, String> positions = new HashMap<Integer, String>();
		List<Position> positionList = positionBO.getPositions();
		for (Position position : positionList) {
			positions.put(position.getId(), position.getName());
		}
		
		return positions;
	}
	
	@ModelAttribute("departments")
	public Map<Integer, String> populateDepartment() {
		Map<Integer, String> departments = new HashMap<Integer, String>();
		List<Department> departmentList = departmentBO.getDepartments();
		for (Department department : departmentList) {
			departments.put(department.getId(), department.getName());
		}
		
		return departments;
	}
	
	@RequiresPermissions(value = {"user:manage"})
	@RequestMapping(value = "/cm/user/add", method = RequestMethod.GET)
	public ModelAndView showCreateUser(ModelMap model) {
		UserViewModel userViewModel = new UserViewModel();
		model.addAttribute("userViewModel", userViewModel);
		
		List<User> users = userBO.getUsers();
		model.addAttribute("users", users);
		
		return new ModelAndView("cm/user/add");
	}
	
	@RequiresPermissions(value = {"user:manage"})
	@RequestMapping(value = "/cm/user/add", method = RequestMethod.POST)
	public ModelAndView processCreateUser(
			@Valid @ModelAttribute("userViewModel") UserViewModel userViewModel,
			BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		createUserValidator.validate(userViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			List<User> users = userBO.getUsers();
			model.addAttribute("users", users);
			
			return new ModelAndView("cm/user/add");
		} else {
			User user = new User();
			user.setUsername(userViewModel.getUsername());
			user.setPassword(passwordService.encryptPassword(userViewModel.getPassword()));
			user.setFullName(userViewModel.getFullName());
			Boolean gender = null;
			if (userViewModel.getGender().equals(0)) {
				gender = true;
			} else if (userViewModel.getGender().equals(1)) {
				gender = false;
			}
			user.setGender(gender);
			user.setHomePhoneNumber(userViewModel.getHomePhoneNumber());
			user.setMobileNumber(userViewModel.getMobileNumber());
			user.setOfficePhoneNumber(userViewModel.getOfficePhoneNumber());
			user.setAddress(userViewModel.getAddress());
			user.setEmail(userViewModel.getEmail());
			user.setDescription(userViewModel.getDescription());
			Integer positionId = userViewModel.getPosition() < 0 ? null : userViewModel.getPosition();
			Integer departmentId = userViewModel.getDepartment() < 0 ? null : userViewModel.getDepartment();
			
			userBO.addUser(user, positionId, departmentId);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("user.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/user/add"));
		}
	}
	
	@RequiresPermissions(value = {"user:manage"})
	@RequestMapping(value = "/cm/user/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateUser(@PathVariable("id") Integer id, ModelMap model) {
		UpdateUserViewModel updateUserViewModel = new UpdateUserViewModel();
		User user = userBO.getUserById(id);
		if (user != null) {
			updateUserViewModel.setUsername(user.getUsername());
			updateUserViewModel.setFullName(user.getFullName());
			Boolean gender = user.getGender();
			updateUserViewModel.setGender(-1);
			if (gender != null) {
				updateUserViewModel.setGender(gender ? 0 : 1);
			}
			updateUserViewModel.setAddress(user.getAddress());
			updateUserViewModel.setHomePhoneNumber(user.getHomePhoneNumber());
			updateUserViewModel.setMobileNumber(user.getMobileNumber());
			updateUserViewModel.setOfficePhoneNumber(user.getOfficePhoneNumber());
			updateUserViewModel.setEmail(user.getEmail());
			if (user.getPosition() != null && user.getPosition().getId() != null) {
				updateUserViewModel.setPosition(user.getPosition().getId());
			} else {
				updateUserViewModel.setPosition(-1);
			}
			if (user.getDepartment() != null && user.getDepartment().getId() != null) {
				updateUserViewModel.setDepartment(user.getDepartment().getId());
			} else {
				updateUserViewModel.setDepartment(-1);
			}
			
			updateUserViewModel.setDescription(user.getDescription());
		}
		model.addAttribute("updateUserViewModel", updateUserViewModel);
		
		List<User> users = userBO.getUsers();
		model.addAttribute("users", users);
		
		return new ModelAndView("cm/user/update");
	}
	
	@RequiresPermissions(value = {"user:manage"})
	@RequestMapping(value = "/cm/user/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateUser(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("updateUserViewModel") UpdateUserViewModel updateUserViewModel,
			BindingResult bindingResult, 
			ModelMap model, HttpServletRequest request) {
		
		updateUserValidator.setUserId(id);
		updateUserValidator.validate(updateUserViewModel, bindingResult);

		if (bindingResult.hasErrors()) {
			List<User> users = userBO.getUsers();
			model.addAttribute("users", users);
			
			return new ModelAndView("cm/user/update");
		} else {
			User user = new User();
			user.setId(id);
			user.setUsername(updateUserViewModel.getUsername());
			user.setFullName(updateUserViewModel.getFullName());
			if (updateUserViewModel.getPassword() != null && !"".equals(updateUserViewModel.getPassword().trim())) {
				user.setPassword(passwordService.encryptPassword(updateUserViewModel.getPassword()));
			}
			Boolean gender = null;
			if (updateUserViewModel.getGender().equals(0)) {
				gender = true;
			} else if (updateUserViewModel.getGender().equals(1)) {
				gender = false;
			}
			user.setGender(gender);
			user.setHomePhoneNumber(updateUserViewModel.getHomePhoneNumber());
			user.setMobileNumber(updateUserViewModel.getMobileNumber());
			user.setOfficePhoneNumber(updateUserViewModel.getOfficePhoneNumber());
			user.setAddress(updateUserViewModel.getAddress());
			user.setEmail(updateUserViewModel.getEmail());
			user.setDescription(updateUserViewModel.getDescription());
			Integer positionId = updateUserViewModel.getPosition() < 0 ? null : updateUserViewModel.getPosition();
			Integer departmentId = updateUserViewModel.getDepartment() < 0 ? null : updateUserViewModel.getDepartment();
			
			userBO.updateUser(user, positionId, departmentId);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("user.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/user/add"));
		}
	}
	
	@RequiresPermissions(value = {"user:manage"})
	@RequestMapping(value = "/cm/user/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteUser(@RequestParam("ids") Integer[] ids, HttpSession session) {
		userBO.deleteUsers(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("user.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequiresPermissions(value = {"user:editSelf"})
	@RequestMapping(value = "/cm/user/edit", method = RequestMethod.GET)
	public ModelAndView showEditUser(ModelMap model, HttpServletRequest request) {
		EditUserViewModel editUserViewModel = new EditUserViewModel();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		if (userInfo != null) {
			User user = userBO.getUserById(userInfo.getId());
			if (user != null) {
				editUserViewModel.setUsername(user.getUsername());
				editUserViewModel.setFullName(user.getFullName());
				editUserViewModel.setHomeNumber(user.getHomePhoneNumber());
				editUserViewModel.setMobileNumber(user.getMobileNumber());
				editUserViewModel.setOfficeNumber(user.getOfficePhoneNumber());
				editUserViewModel.setAddress(user.getAddress());
				editUserViewModel.setEmail(user.getEmail());
			}
		}
		
		model.addAttribute("editUserViewModel", editUserViewModel);
		
		return new ModelAndView("cm/user/edit");
	}
	
	@RequiresPermissions(value = {"user:editSelf"})
	@RequestMapping(value = "/cm/user/edit", method = RequestMethod.POST)
	public ModelAndView processEditUser(
			@Valid @ModelAttribute("editUserViewModel") EditUserViewModel editUserViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		if (userInfo != null) {
			editUserValidator.setId(userInfo.getId());
			editUserValidator.validate(editUserViewModel, bindingResult);
			
			if (bindingResult.hasErrors()) {
				return new ModelAndView("cm/user/edit");
			} else {
				User user = new User();
				user.setId(userInfo.getId());
				user.setUsername(editUserViewModel.getUsername());
				user.setFullName(editUserViewModel.getFullName());
				user.setHomePhoneNumber(editUserViewModel.getHomeNumber());
				user.setMobileNumber(editUserViewModel.getMobileNumber());
				user.setOfficePhoneNumber(editUserViewModel.getOfficeNumber());
				user.setAddress(editUserViewModel.getAddress());
				user.setEmail(editUserViewModel.getAddress());
				
				userBO.updateUser(user);
				
				request.getSession().setAttribute("msg", PropertiesUtil.getProperty("user.edited.successfully"));
				
				return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/user/edit"));
			}
		}
		
		return null;
	}
	
	@RequiresPermissions(value = {"user:editSelf"})
	@RequestMapping(value = "/cm/user/changePassword", method = RequestMethod.GET)
	public ModelAndView changeUserPassword(ModelMap model, HttpServletRequest request) {
		ChangePasswordViewModel changePasswordViewModel = new ChangePasswordViewModel();
		model.addAttribute("changePasswordViewModel", changePasswordViewModel);
		
		return new ModelAndView("cm/user/changePassword");
	}
	
	@RequiresPermissions(value = {"user:editSelf"})
	@RequestMapping(value = "/cm/user/changePassword", method = RequestMethod.POST)
	public ModelAndView processChangePassword(
			@Valid @ModelAttribute("changePasswordViewModel") ChangePasswordViewModel changePasswordViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		if (userInfo != null) {
			changePasswordValidator.setId(userInfo.getId());
			changePasswordValidator.validate(changePasswordViewModel, bindingResult);
			
			if (bindingResult.hasErrors()) {
				return new ModelAndView("cm/user/changePassword");
			} else {
				User user = new User();
				user.setId(userInfo.getId());
				user.setPassword(passwordService.encryptPassword(changePasswordViewModel.getNewPassword()));
				
				userBO.updateUserPassword(user);
				
				request.getSession().setAttribute("msg", PropertiesUtil.getProperty("password.changed.successfully"));
				
				return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/user/changePassword"));
			}
		}
		
		return null;
	}
}
