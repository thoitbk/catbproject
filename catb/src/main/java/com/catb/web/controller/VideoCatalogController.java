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

import com.catb.bo.VideoCatalogBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.VideoCatalog;
import com.catb.web.viewmodel.Status;
import com.catb.web.viewmodel.VideoCatalogViewModel;

@Controller
public class VideoCatalogController {
	
	@Autowired
	private VideoCatalogBO videoCatalogBO;
	
	@RequiresPermissions(value = {"videoCatalog:manage"})
	@RequestMapping(value = "/cm/videoCatalog/add", method = RequestMethod.GET)
	public ModelAndView showCreateVideoCatalog(ModelMap model) {
		VideoCatalogViewModel videoCatalogViewModel = new VideoCatalogViewModel();
		model.addAttribute("videoCatalogViewModel", videoCatalogViewModel);
		
		List<VideoCatalog> videoCatalogs = videoCatalogBO.getVideoCatalogs();
		model.addAttribute("videoCatalogs", videoCatalogs);
		
		return new ModelAndView("cm/videoCatalog/add");
	}
	
	@RequiresPermissions(value = {"videoCatalog:manage"})
	@RequestMapping(value = "/cm/videoCatalog/add", method = RequestMethod.POST)
	public ModelAndView processCreateVideoCatalog(
								@Valid @ModelAttribute("videoCatalogViewModel") VideoCatalogViewModel videoCatalogViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("videoCatalogs", videoCatalogBO.getVideoCatalogs());
			return new ModelAndView("cm/videoCatalog/add");
		} else {
			VideoCatalog videoCatalog = new VideoCatalog(videoCatalogViewModel.getName(), videoCatalogViewModel.getDescription());
			
			videoCatalogBO.addVideoCatalog(videoCatalog);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("videoCatalog.created.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/videoCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"videoCatalog:manage"})
	@RequestMapping(value = "/cm/videoCatalog/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateVideoCatalog(@PathVariable("id") Integer id, ModelMap model) {
		VideoCatalogViewModel videoCatalogViewModel = new VideoCatalogViewModel();
		VideoCatalog videoCatalog = videoCatalogBO.getVideoCatalogById(id);
		if (videoCatalog != null) {
			videoCatalogViewModel.setName(videoCatalog.getName());
			videoCatalogViewModel.setDescription(videoCatalog.getDescription());
		}
		
		model.addAttribute("videoCatalogViewModel", videoCatalogViewModel);
		model.addAttribute("videoCatalogs", videoCatalogBO.getVideoCatalogs());
		
		return new ModelAndView("cm/videoCatalog/update");
	}
	
	@RequiresPermissions(value = {"videoCatalog:manage"})
	@RequestMapping(value = "/cm/videoCatalog/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateVideoCatalog(@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("videoCatalogViewModel") VideoCatalogViewModel videoCatalogViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("videoCatalogs", videoCatalogBO.getVideoCatalogs());
			
			return new ModelAndView("cm/videoCatalog/add");
		} else {
			VideoCatalog videoCatalog = new VideoCatalog(videoCatalogViewModel.getName(), videoCatalogViewModel.getDescription());
			videoCatalog.setId(id);
			
			videoCatalogBO.updateVideoCatalog(videoCatalog);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("videoCatalog.updated.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/videoCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"videoCatalog:manage"})
	@RequestMapping(value = "/cm/videoCatalog/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteVideoCatalogs(@RequestParam("ids") Integer[] ids, HttpSession session) {
		videoCatalogBO.deleteVideoCatalogs(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("videoCatalog.deleted.success"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
