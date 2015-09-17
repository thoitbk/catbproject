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

import com.catb.bo.PositionBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.Position;
import com.catb.web.viewmodel.PositionViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class PositionController {
	
	@Autowired
	private PositionBO positionBO;
	
	@RequiresPermissions(value = {"position:manage"})
	@RequestMapping(value = "/cm/position/add", method = RequestMethod.GET)
	public ModelAndView showCreatePosition(ModelMap model) {
		PositionViewModel positionViewModel = new PositionViewModel();
		model.addAttribute("positionViewModel", positionViewModel);
		
		List<Position> positions = positionBO.getPositions();
		model.addAttribute("positions", positions);
		
		return new ModelAndView("cm/position/add");
	}
	
	@RequiresPermissions(value = {"position:manage"})
	@RequestMapping(value = "/cm/position/add", method = RequestMethod.POST)
	public ModelAndView processCreatePosition(
								@Valid @ModelAttribute("positionViewModel") PositionViewModel positionViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			List<Position> positions = positionBO.getPositions();
			model.addAttribute("positions", positions);
			return new ModelAndView("cm/position/add");
		} else {
			Position position = new Position(
								positionViewModel.getName(), 
								positionViewModel.getCode(), 
								positionViewModel.getDescription());
			
			positionBO.addPosition(position);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("position.created.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/position/add"));
		}
	}
	
	@RequiresPermissions(value = {"position:manage"})
	@RequestMapping(value = "/cm/position/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdatePosition(@PathVariable("id") Integer id, ModelMap model) {
		PositionViewModel positionViewModel = new PositionViewModel();
		Position position = positionBO.getPositionById(id);
		if (position != null) {
			positionViewModel.setName(position.getName());
			positionViewModel.setCode(position.getCode());
			positionViewModel.setDescription(position.getDescription());
		}
		model.addAttribute("positionViewModel", positionViewModel);
		
		List<Position> positions = positionBO.getPositions();
		model.addAttribute("positions", positions);
		
		return new ModelAndView("cm/position/update");
	}
	
	@RequiresPermissions(value = {"position:manage"})
	@RequestMapping(value = "/cm/position/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdatePosition(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("positionViewModel") PositionViewModel positionViewModel,
			BindingResult bindingResult, 
			ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<Position> positions = positionBO.getPositions();
			model.addAttribute("positions", positions);
			return new ModelAndView("cm/position/update");
		} else {
			Position position = new Position(
					positionViewModel.getName(), 
					positionViewModel.getCode(), 
					positionViewModel.getDescription());
			position.setId(id);
			
			positionBO.updatePosition(position);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("position.updated.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/position/add"));
		}
	}
	
	@RequiresPermissions(value = {"position:manage"})
	@RequestMapping(value = "/cm/position/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deletePosition(@RequestParam("ids") Integer[] ids, HttpSession session) {
		positionBO.deletePositions(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("position.deleted.success"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
