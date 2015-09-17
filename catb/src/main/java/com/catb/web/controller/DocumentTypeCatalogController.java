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

import com.catb.bo.DocumentTypeCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.DocumentTypeCatalog;
import com.catb.web.viewmodel.DocumentTypeCatalogViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class DocumentTypeCatalogController {
	
	@Autowired
	private DocumentTypeCatalogBO documentTypeCatalogBO;
	
	@RequiresPermissions(value = {"documentType:manage"})
	@RequestMapping(value = "/cm/documentType/add", method = RequestMethod.GET)
	public ModelAndView showCreateDocumentTypeCatalog(ModelMap model) {
		DocumentTypeCatalogViewModel documentTypeCatalogViewModel = new DocumentTypeCatalogViewModel();
		model.addAttribute("documentTypeCatalogViewModel", documentTypeCatalogViewModel);
		
		List<DocumentTypeCatalog> documentTypeCatalogs = documentTypeCatalogBO.getDocumentTypeCatalogs();
		model.addAttribute("documentTypeCatalogs", documentTypeCatalogs);
		
		return new ModelAndView("cm/documentType/add");
	}
	
	@RequiresPermissions(value = {"documentType:manage"})
	@RequestMapping(value = "/cm/documentType/add", method = RequestMethod.POST)
	public ModelAndView processCreateDocumentTypeCatalog(
								@Valid @ModelAttribute("documentTypeCatalogViewModel") DocumentTypeCatalogViewModel documentTypeCatalogViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			List<DocumentTypeCatalog> documentTypeCatalogs = documentTypeCatalogBO.getDocumentTypeCatalogs();
			model.addAttribute("documentTypeCatalogs", documentTypeCatalogs);
			return new ModelAndView("cm/documentType/add");
		} else {
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (documentTypeCatalogViewModel.getSqNumber() != null && !"".equals(documentTypeCatalogViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(documentTypeCatalogViewModel.getSqNumber().trim());
			}
			DocumentTypeCatalog documentTypeCatalog = new DocumentTypeCatalog(
														documentTypeCatalogViewModel.getCode(), 
														documentTypeCatalogViewModel.getName(), 
														sqNumber,
														documentTypeCatalogViewModel.getDisplay(), 
														documentTypeCatalogViewModel.getDescription());
			documentTypeCatalogBO.addDocumentTypeCatalog(documentTypeCatalog);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("documentType.created.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/documentType/add"));
		}
	}
	
	@RequiresPermissions(value = {"documentType:manage"})
	@RequestMapping(value = "/cm/documentType/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateDocumentTypeCatalog(@PathVariable("id") Integer id, ModelMap model) {
		DocumentTypeCatalogViewModel documentTypeCatalogViewModel = new DocumentTypeCatalogViewModel();
		DocumentTypeCatalog documentTypeCatalog = documentTypeCatalogBO.getDocumentTypeCatalogById(id);
		if (documentTypeCatalog != null) {
			documentTypeCatalogViewModel.setCode(documentTypeCatalog.getCode());
			documentTypeCatalogViewModel.setName(documentTypeCatalog.getName());
			documentTypeCatalogViewModel.setDisplay(documentTypeCatalog.getDisplay());
			String sqNumber = documentTypeCatalog.getSqNumber() != null && !Constants.MAX_SQ_NUMBER.equals(documentTypeCatalog.getSqNumber()) ? String.valueOf(documentTypeCatalog.getSqNumber()) : "";
			documentTypeCatalogViewModel.setSqNumber(sqNumber);
			documentTypeCatalogViewModel.setDescription(documentTypeCatalog.getDescription());
		}
		model.addAttribute("documentTypeCatalogViewModel", documentTypeCatalogViewModel);
		
		List<DocumentTypeCatalog> documentTypeCatalogs = documentTypeCatalogBO.getDocumentTypeCatalogs();
		model.addAttribute("documentTypeCatalogs", documentTypeCatalogs);
		
		return new ModelAndView("cm/documentType/update");
	}
	
	@RequiresPermissions(value = {"documentType:manage"})
	@RequestMapping(value = "/cm/documentType/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateDocumentTypeCatalog(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("documentTypeCatalogViewModel") DocumentTypeCatalogViewModel documentTypeCatalogViewModel,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			List<DocumentTypeCatalog> documentTypeCatalogs = documentTypeCatalogBO.getDocumentTypeCatalogs();
			model.addAttribute("documentTypeCatalogs", documentTypeCatalogs);
			return new ModelAndView("cm/documentType/update");
		} else {
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (documentTypeCatalogViewModel.getSqNumber() != null && !"".equals(documentTypeCatalogViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(documentTypeCatalogViewModel.getSqNumber().trim());
			}
			DocumentTypeCatalog documentTypeCatalog = new DocumentTypeCatalog(id, 
												documentTypeCatalogViewModel.getCode(), 
												documentTypeCatalogViewModel.getName(), 
												sqNumber, documentTypeCatalogViewModel.getDisplay(), 
												documentTypeCatalogViewModel.getDescription());
			
			documentTypeCatalogBO.updateDocumentTypeCatalog(documentTypeCatalog);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("documentType.updated.success"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/documentType/add"));
		}
	}
	
	@RequiresPermissions(value = {"documentType:manage"})
	@RequestMapping(value = "/cm/documentType/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteDocumentTypeCatalogs(@RequestParam("ids") Integer[] ids, HttpSession session) {
		documentTypeCatalogBO.deleteDocumentTypeCatalogs(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("documentType.deleted.success"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
