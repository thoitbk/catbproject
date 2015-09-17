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

import com.catb.bo.PermissionBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.Permission;
import com.catb.web.validator.CreatePermissionValidator;
import com.catb.web.validator.UpdatePermissionValidator;
import com.catb.web.viewmodel.PermissionViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class PermissionController {
	
	@Autowired
	private PermissionBO permissionBO;
	
	@Autowired
	private CreatePermissionValidator createPermissionValidator;
	
	@Autowired
	private UpdatePermissionValidator updatePermissionValidator;
	
	@RequiresPermissions(value = {"permission:manage"})
	@RequestMapping(value = "/cm/permission/add", method = RequestMethod.GET)
	public ModelAndView showCreatePermission(ModelMap model) {
		PermissionViewModel permissionViewModel = new PermissionViewModel();
		model.addAttribute("permissionViewModel", permissionViewModel);
		
		List<Permission> permissions = permissionBO.getPermissions();
		model.addAttribute("permissions", permissions);
		
		return new ModelAndView("cm/permission/add");
	}
	
	@RequiresPermissions(value = {"permission:manage"})
	@RequestMapping(value = "/cm/permission/add", method = RequestMethod.POST)
	public ModelAndView processCreatePermission(
								@Valid @ModelAttribute("permissionViewModel") PermissionViewModel permissionViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		createPermissionValidator.validate(permissionViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			List<Permission> permissions = permissionBO.getPermissions();
			model.addAttribute("permissions", permissions);
			
			return new ModelAndView("cm/permission/add");
		} else {
			Permission permission = new Permission(
												permissionViewModel.getName(), 
												permissionViewModel.getPerStr(), 
												permissionViewModel.getDescription());
			permissionBO.addPermission(permission);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("permission.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/permission/add"));
		}
	}
	
	@RequiresPermissions(value = {"permission:manage"})
	@RequestMapping(value = "/cm/permission/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdatePermission(@PathVariable("id") Integer id, ModelMap model) {
		PermissionViewModel permissionViewModel = new PermissionViewModel();
		Permission permission = permissionBO.getPermissionById(id);
		if (permission != null) {
			permissionViewModel.setName(permission.getName());
			permissionViewModel.setPerStr(permission.getPerStr());
			permissionViewModel.setDescription(permission.getDescription());
		}
		model.addAttribute("permissionViewModel", permissionViewModel);
		
		List<Permission> permissions = permissionBO.getPermissions();
		model.addAttribute("permissions", permissions);
		
		return new ModelAndView("cm/permission/update");
	}
	
	@RequiresPermissions(value = {"permission:manage"})
	@RequestMapping(value = "/cm/permission/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdatePermission(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("permissionViewModel") PermissionViewModel permissionViewModel,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		updatePermissionValidator.setPermissionId(id);
		updatePermissionValidator.validate(permissionViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			List<Permission> permissions = permissionBO.getPermissions();
			model.addAttribute("permissions", permissions);
			
			return new ModelAndView("cm/permission/update");
		} else {
			Permission permission = new Permission(id, 
											permissionViewModel.getName(), 
											permissionViewModel.getPerStr(), 
											permissionViewModel.getDescription());
			permissionBO.updatePermission(permission);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("permission.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/permission/add"));
		}
	}
	
	@RequiresPermissions(value = {"permission:manage"})
	@RequestMapping(value = "/cm/permission/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deletePermission(@RequestParam("ids") Integer[] ids, HttpSession session) {
		permissionBO.deletePermissions(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("permission.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
