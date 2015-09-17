package com.catb.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import com.catb.bo.RoleBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.Role;
import com.catb.web.validator.CreateRoleValidator;
import com.catb.web.validator.UpdateRoleValidator;
import com.catb.web.viewmodel.RoleViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class RoleController {
	
	static Logger logger = Logger.getLogger(RoleController.class);
	
	@Autowired
	private RoleBO roleBO;
	
	@Autowired
	private CreateRoleValidator createRoleValidator;
	
	@Autowired
	private UpdateRoleValidator updateRoleValidator;
	
	@RequiresPermissions(value = {"role:manage"})
	@RequestMapping(value = "/cm/role/add", method = RequestMethod.GET)
	public ModelAndView showCreateRole(ModelMap model) {
		RoleViewModel roleViewModel = new RoleViewModel();
		model.addAttribute("roleViewModel", roleViewModel);
		
		List<Role> roles = roleBO.getRoles();
		model.addAttribute("roles", roles);
		
		return new ModelAndView("cm/role/add");
	}
	
	@RequiresPermissions(value = {"role:manage"})
	@RequestMapping(value = "/cm/role/add", method = RequestMethod.POST)
	public ModelAndView processCreateRole(
								@Valid @ModelAttribute("roleViewModel") RoleViewModel roleViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		createRoleValidator.validate(roleViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			List<Role> roles = roleBO.getRoles();
			model.addAttribute("roles", roles);
			
			return new ModelAndView("cm/role/add");
		} else {
			Role role = new Role(roleViewModel.getName(), roleViewModel.getDescription());
			
			roleBO.addRole(role);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("role.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/role/add"));
		}
	}
	
	@RequiresPermissions(value = {"role:manage"})
	@RequestMapping(value = "/cm/role/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateRole(@PathVariable("id") Integer id, ModelMap model) {
		RoleViewModel roleViewModel = new RoleViewModel();
		Role role = roleBO.getRoleById(id);
		if (role != null) {
			roleViewModel.setName(role.getName());
			roleViewModel.setDescription(role.getDescription());
		}
		model.addAttribute("roleViewModel", roleViewModel);
		
		List<Role> roles = roleBO.getRoles();
		model.addAttribute("roles", roles);
		
		return new ModelAndView("cm/role/update");
	}
	
	@RequiresPermissions(value = {"role:manage"})
	@RequestMapping(value = "/cm/role/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateRole(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("roleViewModel") RoleViewModel roleViewModel,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		updateRoleValidator.setRoleId(id);
		updateRoleValidator.validate(roleViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			List<Role> roles = roleBO.getRoles();
			model.addAttribute("roles", roles);
			
			return new ModelAndView("cm/role/update");
		} else {
			Role role = new Role(id, roleViewModel.getName(), roleViewModel.getDescription());
			roleBO.updateRole(role);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("role.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/role/add"));
		}
	}
	
	@RequiresPermissions(value = {"role:manage"})
	@RequestMapping(value = "/cm/role/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteRole(@RequestParam("ids") Integer[] ids, HttpSession session) {
		roleBO.deleteRoles(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("role.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
