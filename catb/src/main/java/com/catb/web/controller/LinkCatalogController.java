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

import com.catb.bo.LinkCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.LinkCatalog;
import com.catb.web.viewmodel.LinkCatalogViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class LinkCatalogController {
	
	@Autowired
	private LinkCatalogBO linkCatalogBO;
	
	@RequiresPermissions(value = {"link:manage"})
	@RequestMapping(value = "/cm/linkCatalog/add", method = RequestMethod.GET)
	public ModelAndView showCreateLinkCatalog(ModelMap model) {
		LinkCatalogViewModel linkCatalogViewModel = new LinkCatalogViewModel();
		model.addAttribute("linkCatalogViewModel", linkCatalogViewModel);
		
		List<LinkCatalog> linkCatalogs = linkCatalogBO.getLinkCatalogs();
		model.addAttribute("linkCatalogs", linkCatalogs);
		
		return new ModelAndView("cm/linkCatalog/add");
	}
	
	@RequiresPermissions(value = {"link:manage"})
	@RequestMapping(value = "/cm/linkCatalog/add", method = RequestMethod.POST)
	public ModelAndView processCreateLinkCatalog(
								@Valid @ModelAttribute("linkCatalogViewModel") LinkCatalogViewModel linkCatalogViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			List<LinkCatalog> linkCatalogs = linkCatalogBO.getLinkCatalogs();
			model.addAttribute("linkCatalogs", linkCatalogs);
			
			return new ModelAndView("cm/linkCatalog/add");
		} else {
			LinkCatalog linkCatalog = new LinkCatalog();
			linkCatalog.setTitle(linkCatalogViewModel.getTitle());
			linkCatalog.setLinkSite(linkCatalogViewModel.getLinkSite());
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (linkCatalogViewModel.getSqNumber() != null && !"".equals(linkCatalogViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(linkCatalogViewModel.getSqNumber().trim());
			}
			linkCatalog.setSqNumber(sqNumber);
			linkCatalog.setOpenBlank(linkCatalogViewModel.getOpenBlank());
			
			linkCatalogBO.addLinkCatalog(linkCatalog);
			request.getServletContext().setAttribute("LINK_LIST", linkCatalogBO.getLinkCatalogs());
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("link.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/linkCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"link:manage"})
	@RequestMapping(value = "/cm/linkCatalog/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteLinkCatalog(@RequestParam("ids") Integer[] ids, HttpSession session) {
		linkCatalogBO.deleteLinkCatalogs(ids);
		session.getServletContext().setAttribute("LINK_LIST", linkCatalogBO.getLinkCatalogs());
		session.setAttribute("msg", PropertiesUtil.getProperty("link.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequiresPermissions(value = {"link:manage"})
	@RequestMapping(value = "/cm/linkCatalog/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateLinkCatalog(@PathVariable("id") Integer id, ModelMap model) {
		LinkCatalogViewModel linkCatalogViewModel = new LinkCatalogViewModel();
		LinkCatalog linkCatalog = linkCatalogBO.getLinkCatalogById(id);
		if (linkCatalog != null) {
			linkCatalogViewModel.setTitle(linkCatalog.getTitle());
			linkCatalogViewModel.setLinkSite(linkCatalog.getLinkSite());
			if (linkCatalog.getSqNumber() != null && !Constants.MAX_SQ_NUMBER.equals(linkCatalog.getSqNumber())) {
				linkCatalogViewModel.setSqNumber(String.valueOf(linkCatalog.getSqNumber()));
			}
			linkCatalogViewModel.setOpenBlank(linkCatalog.getOpenBlank());
		}
		model.addAttribute("linkCatalogViewModel", linkCatalogViewModel);
		
		List<LinkCatalog> linkCatalogs = linkCatalogBO.getLinkCatalogs();
		model.addAttribute("linkCatalogs", linkCatalogs);
		
		return new ModelAndView("cm/linkCatalog/update");
	}
	
	@RequiresPermissions(value = {"link:manage"})
	@RequestMapping(value = "/cm/linkCatalog/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateLinkCatalog(@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("linkCatalogViewModel") LinkCatalogViewModel linkCatalogViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<LinkCatalog> linkCatalogs = linkCatalogBO.getLinkCatalogs();
			model.addAttribute("linkCatalogs", linkCatalogs);
			
			return new ModelAndView("cm/linkCatalog/update");
		} else {
			LinkCatalog linkCatalog = new LinkCatalog();
			linkCatalog.setId(id);
			linkCatalog.setTitle(linkCatalogViewModel.getTitle());
			linkCatalog.setLinkSite(linkCatalogViewModel.getLinkSite());
			linkCatalog.setOpenBlank(linkCatalogViewModel.getOpenBlank());
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (linkCatalogViewModel.getSqNumber() != null && !"".equals(linkCatalogViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(linkCatalogViewModel.getSqNumber().trim());
			}
			linkCatalog.setSqNumber(sqNumber);
			
			linkCatalogBO.updateLinkCatalog(linkCatalog);
			request.getServletContext().setAttribute("LINK_LIST", linkCatalogBO.getLinkCatalogs());
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("link.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/linkCatalog/add"));
		}
	}
}
