package com.catb.web.controller;

import java.util.Date;
import java.util.LinkedHashMap;
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

import com.catb.bo.CriminalDenouncementBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.CriminalDenouncement;
import com.catb.model.CriminalDenouncementStatus;
import com.catb.service.mail.MailSender;
import com.catb.vo.MailContent;
import com.catb.web.tag.PageInfo;
import com.catb.web.util.Util;
import com.catb.web.validator.CreateCriminalDenouncementValidator;
import com.catb.web.viewmodel.CreateCriminalDenouncementViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class CriminalDenouncementController {
	
	@Autowired
	private CriminalDenouncementBO criminalDenouncementBO;
	
	@Autowired
	private CreateCriminalDenouncementValidator createCriminalDenouncementValidator;
	
	@RequestMapping(value = "/to-giac-toi-pham", method = RequestMethod.GET)
	public ModelAndView showCreateCriminalDenouncement(ModelMap model) {
		CreateCriminalDenouncementViewModel createCriminalDenouncementViewModel = new CreateCriminalDenouncementViewModel();
		model.addAttribute("createCriminalDenouncementViewModel", createCriminalDenouncementViewModel);
		
		return new ModelAndView("denounceCriminal");
	}
	
	@RequestMapping(value = "/to-giac-toi-pham", method = RequestMethod.POST)
	public ModelAndView processCreateCriminalDenouncement(
			@Valid @ModelAttribute("createCriminalDenouncementViewModel") CreateCriminalDenouncementViewModel createCriminalDenouncementViewModel, 
			BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		String validCode = (String) request.getSession().getAttribute(Constants.CD_CAPTCHA_KEY);
		createCriminalDenouncementValidator.setValidCode(validCode);
		createCriminalDenouncementValidator.validate(createCriminalDenouncementViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return new ModelAndView("denounceCriminal");
		} else {
			CriminalDenouncement criminalDenouncement = new CriminalDenouncement();
			criminalDenouncement.setAddress(createCriminalDenouncementViewModel.getAddress());
			criminalDenouncement.setContent(createCriminalDenouncementViewModel.getContent());
			criminalDenouncement.setEmail(createCriminalDenouncementViewModel.getEmail());
			criminalDenouncement.setName(createCriminalDenouncementViewModel.getName());
			criminalDenouncement.setPhoneNumber(createCriminalDenouncementViewModel.getPhoneNumber());
			criminalDenouncement.setSentDate(new Date());
			criminalDenouncement.setStatus(CriminalDenouncementStatus.NOT_SEEN.getStatus());
			criminalDenouncement.setTitle(createCriminalDenouncementViewModel.getTitle());
			
			criminalDenouncementBO.addCriminalDenouncement(criminalDenouncement);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("criminalDenouncement.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/to-giac-toi-pham"));
		}
	}
	
	@RequiresPermissions(value = {"criminalDenouncement:manage"})
	@RequestMapping(value = "/cm/denouncement/show", method = RequestMethod.GET)
	public ModelAndView showCriminalDenouncements(
			@RequestParam(value = "t", required = false) String title,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer page, 
			ModelMap model, HttpServletRequest request) {
		Integer pageSize = Util.getPageSize(request);
		List<CriminalDenouncement> criminialDenouncements = criminalDenouncementBO.getCriminalDenouncements(title, page, pageSize);
		model.addAttribute("criminalDenouncements", criminialDenouncements);
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (title != null && !"".equals(title)) {
			params.put("t", title);
		}
		model.addAttribute("params", params);
		
		Long total = criminalDenouncementBO.countCriminalDenouncements(title);
		model.addAttribute("pageInfo", new PageInfo(total, page, pageSize));
		
		return new ModelAndView("cm/criminalDenouncement/show");
	}
	
	@RequiresPermissions(value = {"criminalDenouncement:manage"})
	@RequestMapping(value = "/cm/denouncement/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewDenouncement(@PathVariable("id") Integer id, ModelMap model) {
		CriminalDenouncement criminalDenouncement = criminalDenouncementBO.getCriminalDenouncement(id);
		model.addAttribute("criminalDenouncement", criminalDenouncement);
		
		Integer NOT_SEEN = CriminalDenouncementStatus.NOT_SEEN.getStatus();
		if (criminalDenouncement.getStatus() == null || NOT_SEEN.equals(criminalDenouncement.getStatus())) {
			CriminalDenouncement c = new CriminalDenouncement();
			c.setId(id);
			c.setStatus(CriminalDenouncementStatus.SEEN.getStatus());
			criminalDenouncementBO.updateCriminalDenouncementStatus(c);
		}
		
		return new ModelAndView("cm/criminalDenouncement/view");
	}
	
	@RequiresPermissions(value = {"criminalDenouncement:manage"})
	@RequestMapping(value = "/cm/denouncement/reply/{id}", method = RequestMethod.GET)
	public ModelAndView replyDenouncement(@PathVariable("id") Integer id, ModelMap model) {
		CriminalDenouncement criminalDenouncement = criminalDenouncementBO.getCriminalDenouncement(id);
		model.addAttribute("criminalDenouncement", criminalDenouncement);
		model.addAttribute("id", id);
		
		return new ModelAndView("cm/criminalDenouncement/reply");
	}
	
	@RequiresPermissions(value = {"criminalDenouncement:manage"})
	@RequestMapping(value = "/cm/denouncement/reply/{id}", method = RequestMethod.POST)
	public ModelAndView processReplyDenouncement(@PathVariable("id") Integer id, 
			@RequestParam(value = "replyContent", required = false) String replyContent, 
			ModelMap model, HttpServletRequest request) {
		if (replyContent != null && !"".equals(replyContent.trim())) {
			CriminalDenouncement c = criminalDenouncementBO.getCriminalDenouncement(id);
			if (c != null && c.getEmail() != null) {
				try {
					MailContent mailContent = new MailContent();
					mailContent.setTitle(c.getTitle());
					mailContent.setContent(c.getContent());
					mailContent.setReplyContent(replyContent);
					
					MailSender.send(c.getEmail(), mailContent);
					
					CriminalDenouncement criminalDenouncement = new CriminalDenouncement();
					criminalDenouncement.setId(id);
					criminalDenouncement.setReplyContent(replyContent);
					criminalDenouncement.setStatus(CriminalDenouncementStatus.ANSWERED_VIA_EMAIL.getStatus());
					
					criminalDenouncementBO.updateCriminalDenouncement(criminalDenouncement);
					
					request.getSession().setAttribute("msg", PropertiesUtil.getProperty("denouncement.replied.successfully"));
				} catch (Exception ex) {
					ex.printStackTrace();
					request.getSession().setAttribute("msg", PropertiesUtil.getProperty("denouncement.replied.unsuccessfully"));
				}
			}
		} else {
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("denouncement.not.replied"));
		}
		
		String queryString = request.getQueryString() != null && !"".equals(request.getQueryString()) ? "?" + request.getQueryString() : "";
		
		return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/denouncement/show" + queryString));
	}
	
	@RequiresPermissions(value = {"criminalDenouncement:manage"})
	@RequestMapping(value = "/cm/denouncement/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteDenouncements(@RequestParam("ids") Integer[] ids, HttpSession session) {
		criminalDenouncementBO.deleteCriminalDenouncements(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("denouncement.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
}
