package com.catb.web.controller;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import com.catb.bo.NewsCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.NewsCatalog;
import com.catb.web.component.Menu;
import com.catb.web.component.MenuLoader;
import com.catb.web.viewmodel.NewsCatalogViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class NewsCatalogController {
	
	@Autowired
	private NewsCatalogBO newsCatalogBO;
	
	@Autowired
	private MenuLoader menuLoader;
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("displayLocations")
	public Map<String, String> populateDisplayLocations(HttpServletRequest request) {
		Map<String, String> displayLocations = (Map<String, String>) request.getServletContext().getAttribute("DISPLAY_LOCATION"); 
		return displayLocations;
	}
	
	@ModelAttribute("newsCatalogsMap")
	public Map<Integer, String> getNewsCatalogMap(
			@RequestParam(value = "location", required = false, defaultValue = "") String displayLocation) {
		return getNewsCatalogsMapByLocation(displayLocation);
	}
	
	private Map<Integer, String> getNewsCatalogsMapByLocation(String displayLocation) {
		List<NewsCatalog> bindingNewsCatalogs = newsCatalogBO.getNewsCatalog(displayLocation, null);
		Map<Integer, String> newsCatalogsMap = new LinkedHashMap<Integer, String>();
		if (bindingNewsCatalogs != null && bindingNewsCatalogs.size() > 0) {
			for (NewsCatalog newsCatalog : bindingNewsCatalogs) {
				newsCatalogsMap.put(newsCatalog.getId(), String.format("%s (Level %d)", newsCatalog.getName(), newsCatalog.getChildLevel()));
			}
		}
		
		return newsCatalogsMap;
	}
	
	@RequiresPermissions(value = {"newsCatalog:manage"})
	@RequestMapping(value = "/cm/newsCatalogsByLocation", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer, String> showNewsCatalogsByLocation(
			@RequestParam(value = "location", required = false, defaultValue = "") String location) {
		return getNewsCatalogsMapByLocation(location);
	}
	
	@RequiresPermissions(value = {"newsCatalog:manage"})
	@RequestMapping(value = "/cm/newsCatalog/add", method = RequestMethod.GET)
	public ModelAndView showCreateNewsCatalog(
			@RequestParam(value = "location", required = false, defaultValue = "") String displayLocation, 
			@RequestParam(value = "parent", required = false, defaultValue = "-1") Integer parentId, 
			ModelMap model, HttpServletRequest request) {
		
		NewsCatalogViewModel newsCatalogViewModel = new NewsCatalogViewModel();
		if (displayLocation != null && !"".equals(displayLocation.trim())) {
			newsCatalogViewModel.setDisplayLocation(displayLocation);
		}
		if (parentId != null && parentId >= 0) {
			newsCatalogViewModel.setParentId(parentId);
		}
		model.addAttribute("newsCatalogViewModel", newsCatalogViewModel);
		
		List<NewsCatalog> newsCatalogs = newsCatalogBO.getNewsCatalog(displayLocation, parentId);
		model.addAttribute("newsCatalogs", newsCatalogs);
		
		return new ModelAndView("cm/newsCatalog/add");
	}
	
	@RequiresPermissions(value = {"newsCatalog:manage"})
	@RequestMapping(value = "/cm/newsCatalog/add", method = RequestMethod.POST)
	public ModelAndView processCreateNewsCatalog(
								@Valid @ModelAttribute("newsCatalogViewModel") NewsCatalogViewModel newsCatalogViewModel, 
								BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<NewsCatalog> newsCatalogs = newsCatalogBO.getNewsCatalog(
															newsCatalogViewModel.getDisplayLocation(), 
															newsCatalogViewModel.getParentId());
			model.addAttribute("newsCatalogs", newsCatalogs);
			
			return new ModelAndView("cm/newsCatalog/add");
		} else {
			NewsCatalog newsCatalog = new NewsCatalog();
			
			if (newsCatalogViewModel.getDisplayLocation() != null && 
					populateDisplayLocations(request).containsKey(newsCatalogViewModel.getDisplayLocation())) {
				newsCatalog.setDisplayLocation(newsCatalogViewModel.getDisplayLocation());
			}
			
			Integer childLevel = 0;
			if (newsCatalogViewModel.getParentId() != null) {
				if (newsCatalogViewModel.getParentId() >= 0) {
					newsCatalog.setParentId(newsCatalogViewModel.getParentId());
					NewsCatalog parentNewsCatalog = newsCatalogBO.getNewsCatalogById(newsCatalogViewModel.getParentId());
					if (parentNewsCatalog != null && parentNewsCatalog.getChildLevel() != null) {
						childLevel = parentNewsCatalog.getChildLevel() + 1;
					}
				}
			}
			newsCatalog.setChildLevel(childLevel);
			
			newsCatalog.setName(newsCatalogViewModel.getName());
			newsCatalog.setUrl(newsCatalogViewModel.getUrl());
			if (newsCatalogViewModel.getSqNumber() != null && !"".equals(newsCatalogViewModel.getSqNumber().trim())) {
				newsCatalog.setSqNumber(Integer.parseInt(newsCatalogViewModel.getSqNumber().trim()));
			} else {
				newsCatalog.setSqNumber(Constants.MAX_SQ_NUMBER);
			}
			newsCatalog.setDisplay(newsCatalogViewModel.getDisplay());
			newsCatalog.setSpecialSite(newsCatalogViewModel.getSpecialSite());
			
			newsCatalogBO.addNewsCatalog(newsCatalog);
			
			List<Menu> menuTree = new LinkedList<Menu>();
			menuTree.add(new Menu(Constants.HOMEPAGE, "/home", 0, null));
			menuTree.addAll(menuLoader.loadMenuTree());
			request.getServletContext().setAttribute("MENU_HIERARCHY", menuTree);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("newsCatalog.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/newsCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"newsCatalog:manage"})
	@RequestMapping(value = "/cm/newsCatalog/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateNewsCatalog(@PathVariable("id") Integer id, ModelMap model) {
		NewsCatalog newsCatalog = newsCatalogBO.getNewsCatalogById(id);
		NewsCatalogViewModel newsCatalogViewModel = null;
		if (newsCatalog != null) {
			String sqNumber = newsCatalog.getSqNumber() != null && !Constants.MAX_SQ_NUMBER.equals(newsCatalog.getSqNumber()) ? String.valueOf(newsCatalog.getSqNumber()) : "";
			newsCatalogViewModel = new NewsCatalogViewModel(
											newsCatalog.getDisplayLocation(), 
											newsCatalog.getParentId(), 
											newsCatalog.getName(), 
											newsCatalog.getUrl(), 
											sqNumber, 
											newsCatalog.getDisplay(), 
											newsCatalog.getSpecialSite());
		} else {
			newsCatalogViewModel = new NewsCatalogViewModel();
		}
		model.addAttribute("newsCatalogViewModel", newsCatalogViewModel);
		
		List<NewsCatalog> newsCatalogs = newsCatalogBO.getNewsCatalog(newsCatalog.getDisplayLocation(), newsCatalog.getParentId());
		model.addAttribute("newsCatalogs", newsCatalogs);
		
		return new ModelAndView("cm/newsCatalog/update");
	}
	
	@RequiresPermissions(value = {"newsCatalog:manage"})
	@RequestMapping(value = "/cm/newsCatalog/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateNewsCatalog(
								@PathVariable("id") Integer id, 
								@Valid @ModelAttribute("newsCatalogViewModel") NewsCatalogViewModel newsCatalogViewModel, 
								BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<NewsCatalog> newsCatalogs = newsCatalogBO.getNewsCatalog(newsCatalogViewModel.getDisplayLocation(), newsCatalogViewModel.getParentId());
			model.addAttribute("newsCatalogs", newsCatalogs);
			
			return new ModelAndView("cm/newsCatalog/update");
		} else {
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (newsCatalogViewModel.getSqNumber() != null && !"".equals(newsCatalogViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(newsCatalogViewModel.getSqNumber().trim());
			}
			
			NewsCatalog newsCatalog = new NewsCatalog(id, 
									newsCatalogViewModel.getName(), newsCatalogViewModel.getUrl(), 
									sqNumber, newsCatalogViewModel.getDisplay(), newsCatalogViewModel.getSpecialSite(), 
									newsCatalogViewModel.getDisplayLocation(), newsCatalogViewModel.getParentId(), null);
			newsCatalogBO.updateNewsCatalog(newsCatalog);
			
			List<Menu> menuTree = new LinkedList<Menu>();
			menuTree.add(new Menu(Constants.HOMEPAGE, "/home", 0, null));
			menuTree.addAll(menuLoader.loadMenuTree());
			request.getServletContext().setAttribute("MENU_HIERARCHY", menuTree);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("newsCatalog.updated.successfully"));
			
			String queryString = String.format("?location=%s&parent=%d", newsCatalogViewModel.getDisplayLocation(), newsCatalogViewModel.getParentId());
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/newsCatalog/add" + queryString));
		}
	}
	
	@RequiresPermissions(value = {"newsCatalog:manage"})
	@RequestMapping(value = "/cm/newsCatalog/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteNewsCatalog(@RequestParam("ids") Integer[] ids, HttpSession session) {
		newsCatalogBO.deleteNewsCatalogs(ids);
		
		List<Menu> menuTree = new LinkedList<Menu>();
		menuTree.add(new Menu(Constants.HOMEPAGE, "/home", 0, null));
		menuTree.addAll(menuLoader.loadMenuTree());
		session.getServletContext().setAttribute("MENU_HIERARCHY", menuTree);
		
		session.setAttribute("msg", PropertiesUtil.getProperty("newsCatalog.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
