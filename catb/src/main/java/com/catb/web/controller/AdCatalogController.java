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

import com.catb.bo.AdCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.AdCatalog;
import com.catb.web.viewmodel.AdViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class AdCatalogController {
	
	@Autowired
	private AdCatalogBO adCatalogBO;
	
	@RequiresPermissions(value = {"ad:manage"})
	@RequestMapping(value = "/cm/adv/add", method = RequestMethod.GET)
	public ModelAndView showCreateAdvertisement(ModelMap model) {
		AdViewModel adViewModel = new AdViewModel();
		model.addAttribute("adViewModel", adViewModel);
		
		List<AdCatalog> adCatalogs = adCatalogBO.getAdCatalogs();
		model.addAttribute("adCatalogs", adCatalogs);
		
		return new ModelAndView("cm/adv/add");
	}
	
	@RequiresPermissions(value = {"ad:manage"})
	@RequestMapping(value = "/cm/adv/add", method = RequestMethod.POST)
	public ModelAndView processCreateAdvertisement(
								@Valid @ModelAttribute("adViewModel") AdViewModel adViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			List<AdCatalog> adCatalogs = adCatalogBO.getAdCatalogs();
			model.addAttribute("adCatalogs", adCatalogs);
			
			return new ModelAndView("cm/adv/add");
		} else {
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (adViewModel.getSqNumber() != null && !"".equals(adViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(adViewModel.getSqNumber().trim());
			}
			AdCatalog adCatalog = new AdCatalog(adViewModel.getTitle(), adViewModel.getLink(), 
												adViewModel.getImage(), adViewModel.getDisplay(), 
												sqNumber, adViewModel.getOpenBlank());
			adCatalogBO.addAdCatalog(adCatalog);
			request.getServletContext().setAttribute("ADVERTISEMENTS_LIST", adCatalogBO.getDisplayedAdCatalogs(Constants.MAX_ADS_NUM));
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("advertisement.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/adv/add"));
		}
	}
	
	@RequiresPermissions(value = {"ad:manage"})
	@RequestMapping(value = "/cm/adv/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateAdvertisement(@PathVariable("id") Integer id, ModelMap model) {
		AdViewModel adViewModel = new AdViewModel();
		AdCatalog adCatalog = adCatalogBO.getAdCatalogById(id);
		if (adCatalog != null) {
			adViewModel.setDisplay(adCatalog.getDisplay());
			adViewModel.setImage(adCatalog.getImage());
			adViewModel.setLink(adCatalog.getLink());
			adViewModel.setOpenBlank(adCatalog.getOpenBlank());
			Integer sqNumber = adCatalog.getSqNumber();
			if (sqNumber != null && !Constants.MAX_SQ_NUMBER.equals(sqNumber)) {
				adViewModel.setSqNumber(String.valueOf(sqNumber));
			}
			adViewModel.setTitle(adCatalog.getTitle());
		}
		model.addAttribute("adViewModel", adViewModel);
		
		List<AdCatalog> adCatalogs = adCatalogBO.getAdCatalogs();
		model.addAttribute("adCatalogs", adCatalogs);
		
		return new ModelAndView("cm/adv/update");
	}
	
	@RequiresPermissions(value = {"ad:manage"})
	@RequestMapping(value = "/cm/adv/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateAdvertisement(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("adViewModel") AdViewModel adViewModel,
			BindingResult bindingResult, 
			ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<AdCatalog> adCatalogs = adCatalogBO.getAdCatalogs();
			model.addAttribute("adCatalogs", adCatalogs);
			
			return new ModelAndView("cm/adv/update");
		} else {
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (adViewModel.getSqNumber() != null && !"".equals(adViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(adViewModel.getSqNumber().trim());
			}
			
			AdCatalog adCatalog = new AdCatalog(id, adViewModel.getTitle(), 
					adViewModel.getLink(), adViewModel.getImage(), adViewModel.getDisplay(), 
					sqNumber, adViewModel.getOpenBlank());
			
			adCatalogBO.updateAdCatalog(adCatalog);
			request.getServletContext().setAttribute("ADVERTISEMENTS_LIST", adCatalogBO.getDisplayedAdCatalogs(Constants.MAX_ADS_NUM));
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("advertisement.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/adv/add"));
		}
	}
	
	@RequiresPermissions(value = {"ad:manage"})
	@RequestMapping(value = "/cm/adv/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteAdvertisements(@RequestParam("ids") Integer[] ids, HttpSession session) {
		adCatalogBO.deleteAdCatalogs(ids);
		session.getServletContext().setAttribute("ADVERTISEMENTS_LIST", adCatalogBO.getDisplayedAdCatalogs(Constants.MAX_ADS_NUM));
		session.setAttribute("msg", PropertiesUtil.getProperty("advertisement.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
