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

import com.catb.bo.FieldBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.Field;
import com.catb.web.viewmodel.FieldViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class FieldController {
	
	@Autowired
	private FieldBO fieldBO;
	
	@RequiresPermissions(value = {"field:manage"})
	@RequestMapping(value = "/cm/field/add", method = RequestMethod.GET)
	public ModelAndView showCreateField(ModelMap model) {
		FieldViewModel fieldViewModel = new FieldViewModel();
		model.addAttribute("fieldViewModel", fieldViewModel);
		
		List<Field> fields = fieldBO.getFields();
		model.addAttribute("fields", fields);
		
		return new ModelAndView("cm/field/add");
	}
	
	@RequiresPermissions(value = {"field:manage"})
	@RequestMapping(value = "/cm/field/add", method = RequestMethod.POST)
	public ModelAndView processCreateField(
								@Valid @ModelAttribute("fieldViewModel") FieldViewModel fieldViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			List<Field> fields = fieldBO.getFields();
			model.addAttribute("fields", fields);
			
			return new ModelAndView("cm/field/add");
		} else {
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (fieldViewModel.getSqNumber() != null && !"".equals(fieldViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(fieldViewModel.getSqNumber().trim());
			}
			Field field = new Field(fieldViewModel.getCode(), 
									fieldViewModel.getName(), sqNumber, 
									fieldViewModel.getDisplay(), fieldViewModel.getDescription());
			
			fieldBO.addField(field);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("field.created.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/field/add"));
		}
	}
	
	@RequiresPermissions(value = {"field:manage"})
	@RequestMapping(value = "/cm/field/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateField(@PathVariable("id") Integer id, ModelMap model) {
		FieldViewModel fieldViewModel = new FieldViewModel();
		Field field = fieldBO.getFieldById(id);
		if (field != null) {
			fieldViewModel.setCode(field.getCode());
			fieldViewModel.setName(field.getName());
			String sqNumber = field.getSqNumber() != null && !Constants.MAX_SQ_NUMBER.equals(field.getSqNumber()) ? String.valueOf(field.getSqNumber()) : "";
			fieldViewModel.setSqNumber(sqNumber);
			fieldViewModel.setDisplay(field.getDisplay());
			fieldViewModel.setDescription(field.getDescription());
		}
		model.addAttribute("fieldViewModel", fieldViewModel);
		
		List<Field> fields = fieldBO.getFields();
		model.addAttribute("fields", fields);
		
		return new ModelAndView("cm/field/update");
	}
	
	@RequiresPermissions(value = {"field:manage"})
	@RequestMapping(value = "/cm/field/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateField(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("fieldViewModel") FieldViewModel fieldViewModel,
			BindingResult bindingResult, 
			ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<Field> fields = fieldBO.getFields();
			model.addAttribute("fields", fields);
			
			return new ModelAndView("cm/field/update");
		} else {
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (fieldViewModel.getSqNumber() != null && !"".equals(fieldViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(fieldViewModel.getSqNumber().trim());
			}
			Field field = new Field(id, fieldViewModel.getCode(), 
									fieldViewModel.getName(), sqNumber, 
									fieldViewModel.getDisplay(), fieldViewModel.getDescription());
			fieldBO.updateField(field);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("field.updated.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/field/add"));
		}
	}
	
	@RequiresPermissions(value = {"field:manage"})
	@RequestMapping(value = "/cm/field/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteFields(@RequestParam("ids") Integer[] ids, HttpSession session) {
		fieldBO.deleteFields(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("field.deleted.success"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
