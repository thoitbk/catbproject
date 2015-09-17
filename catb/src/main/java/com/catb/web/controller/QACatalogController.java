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

import com.catb.bo.QACatalogBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.QACatalog;
import com.catb.web.viewmodel.QACatalogViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class QACatalogController {
	
	@Autowired
	private QACatalogBO qaCatalogBO;
	
	@RequiresPermissions(value = {"qaCatalog:manage"})
	@RequestMapping(value = "/cm/qaCatalog/add", method = RequestMethod.GET)
	public ModelAndView showCreateQACatalog(ModelMap model) {
		QACatalogViewModel qaCatalogViewModel = new QACatalogViewModel();
		model.addAttribute("qaCatalogViewModel", qaCatalogViewModel);
		
		List<QACatalog> qaCatalogs = qaCatalogBO.getQACatalogs();
		model.addAttribute("qaCatalogs", qaCatalogs);
		
		return new ModelAndView("cm/qaCatalog/add");
	}
	
	@RequiresPermissions(value = {"qaCatalog:manage"})
	@RequestMapping(value = "/cm/qaCatalog/add", method = RequestMethod.POST)
	public ModelAndView processCreateQACatalog(
								@Valid @ModelAttribute("qaCatalogViewModel") QACatalogViewModel qaCatalogViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			List<QACatalog> qaCatalogs = qaCatalogBO.getQACatalogs();
			model.addAttribute("qaCatalogs", qaCatalogs);
			
			return new ModelAndView("cm/qaCatalog/add");
		} else {
			QACatalog qaCatalog = new QACatalog(qaCatalogViewModel.getName(), qaCatalogViewModel.getDescription());
			
			qaCatalogBO.addQACatalog(qaCatalog);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("qaCatalog.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/qaCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"qaCatalog:manage"})
	@RequestMapping(value = "/cm/qaCatalog/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateQACatalog(@PathVariable("id") Integer id, ModelMap model) {
		QACatalogViewModel qaCatalogViewModel = new QACatalogViewModel();
		QACatalog qaCatalog = qaCatalogBO.getQACatalogById(id);
		if (qaCatalog != null) {
			qaCatalogViewModel.setName(qaCatalog.getName());
			qaCatalogViewModel.setDescription(qaCatalog.getDescription());
		}
		model.addAttribute("qaCatalogViewModel", qaCatalogViewModel);
		
		List<QACatalog> qaCatalogs = qaCatalogBO.getQACatalogs();
		model.addAttribute("qaCatalogs", qaCatalogs);
		
		return new ModelAndView("cm/qaCatalog/update");
	}
	
	@RequiresPermissions(value = {"qaCatalog:manage"})
	@RequestMapping(value = "/cm/qaCatalog/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateQACatalog(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("qaCatalogViewModel") QACatalogViewModel qaCatalogViewModel,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<QACatalog> qaCatalogs = qaCatalogBO.getQACatalogs();
			model.addAttribute("qaCatalogs", qaCatalogs);
			
			return new ModelAndView("cm/qaCatalog/update");
		} else {
			QACatalog qaCatalog = new QACatalog(id, qaCatalogViewModel.getName(), qaCatalogViewModel.getDescription());
			
			qaCatalogBO.updateQACatalog(qaCatalog);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("qaCatalog.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/qaCatalog/add"));
		}
	}
	
	@RequiresPermissions(value = {"qaCatalog:manage"})
	@RequestMapping(value = "/cm/qaCatalog/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteQACatalog(@RequestParam("ids") Integer[] ids, HttpSession session) {
		qaCatalogBO.deleteQACatalogs(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("qaCatalog.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
