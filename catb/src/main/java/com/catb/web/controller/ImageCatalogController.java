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

import com.catb.bo.ImageCatalogBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.ImageCatalog;
import com.catb.web.viewmodel.ImageCatalogViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class ImageCatalogController {
	
	@Autowired
	private ImageCatalogBO imageCatalogBO;
	
	@RequiresPermissions(value = {"imageCatalog:manage"})
	@RequestMapping(value = "/cm/imageCatalog/add", method = RequestMethod.GET)
	public ModelAndView showCreateImageCatalog(ModelMap model) {
		ImageCatalogViewModel imageCatalogViewModel = new ImageCatalogViewModel();
		model.addAttribute("imageCatalogViewModel", imageCatalogViewModel);
		
		List<ImageCatalog> imageCatalogs = imageCatalogBO.getImageCatalogs();
		model.addAttribute("imageCatalogs", imageCatalogs);
		
		return new ModelAndView("cm/imageCatalog/add");
	}
	
	@RequiresPermissions(value = {"imageCatalog:manage"})
	@RequestMapping(value = "/cm/imageCatalog/add", method = RequestMethod.POST)
	public ModelAndView processCreateImageCatalog(
								@Valid @ModelAttribute("imageCatalogViewModel") ImageCatalogViewModel imageCatalogViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("imageCatalogs", imageCatalogBO.getImageCatalogs());
			return new ModelAndView("cm/imageCatalog/add");
		} else {
			ImageCatalog imageCatalog = new ImageCatalog(imageCatalogViewModel.getName(), imageCatalogViewModel.getDescription());
			
			imageCatalogBO.addImageCatalog(imageCatalog);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("imageCatalog.created.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/imageCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"imageCatalog:manage"})
	@RequestMapping(value = "/cm/imageCatalog/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateImageCatalog(@PathVariable("id") Integer id, ModelMap model) {
		ImageCatalogViewModel imageCatalogViewModel = new ImageCatalogViewModel();
		ImageCatalog imageCatalog = imageCatalogBO.getImageCatalogById(id);
		if (imageCatalog != null) {
			imageCatalogViewModel.setName(imageCatalog.getName());
			imageCatalogViewModel.setDescription(imageCatalog.getDescription());
		}
		
		model.addAttribute("imageCatalogViewModel", imageCatalogViewModel);
		model.addAttribute("imageCatalogs", imageCatalogBO.getImageCatalogs());
		
		return new ModelAndView("cm/imageCatalog/update");
	}
	
	@RequiresPermissions(value = {"imageCatalog:manage"})
	@RequestMapping(value = "/cm/imageCatalog/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateImageCatalog(@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("imageCatalogViewModel") ImageCatalogViewModel imageCatalogViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("imageCatalogs", imageCatalogBO.getImageCatalogs());
			
			return new ModelAndView("cm/imageCatalog/add");
		} else {
			ImageCatalog imageCatalog = new ImageCatalog(imageCatalogViewModel.getName(), imageCatalogViewModel.getDescription());
			imageCatalog.setId(id);
			
			imageCatalogBO.updateImageCatalog(imageCatalog);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("imageCatalog.updated.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/imageCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"imageCatalog:manage"})
	@RequestMapping(value = "/cm/imageCatalog/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteImageCatalog(@RequestParam("ids") Integer[] ids, HttpSession session) {
		imageCatalogBO.deleteImageCatalogs(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("imageCatalog.deleted.success"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
