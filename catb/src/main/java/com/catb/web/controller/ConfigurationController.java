package com.catb.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.dao.statics.ResWriter;
import com.catb.model.CommonInfo;
import com.catb.web.viewmodel.CommonInfoViewModel;

@Controller
public class ConfigurationController {
	
	@RequiresPermissions(value = {"configuration:manage"})
	@RequestMapping(value = "/cm/configurations", method = RequestMethod.GET)
	public ModelAndView showConfiguration(ModelMap model, HttpServletRequest request) {
		CommonInfo commonInfo = (CommonInfo) request.getServletContext().getAttribute("COMMONINFO");
		CommonInfoViewModel commonInfoViewModel = new CommonInfoViewModel();
		
		commonInfoViewModel.setWebTitle(commonInfo.getWebTitle());
		commonInfoViewModel.setMarqueeTitle(commonInfo.getMarqueeTitle());
		commonInfoViewModel.setRecentNews(commonInfo.getRecentNews().toString());
		commonInfoViewModel.setQuestionAnswer(commonInfo.getQuestionAnswer().toString());
		commonInfoViewModel.setTcCatalogs(commonInfo.getTcCatalogs().toString());
		commonInfoViewModel.setSameSubjects(commonInfo.getSameSubjects().toString());
		commonInfoViewModel.setHeadlines(commonInfo.getHeadlines().toString());
		commonInfoViewModel.setHeadlineCaption(commonInfo.getHeadlineCaption());
		commonInfoViewModel.setImageCaption(commonInfo.getImageCaption());
		commonInfoViewModel.setVideoCaption(commonInfo.getVideoCaption());
		commonInfoViewModel.setAudioCaption(commonInfo.getAudioCaption());
		commonInfoViewModel.setDetailsCaption(commonInfo.getDetailsCaption());
		commonInfoViewModel.setAdministrativeProcedures(commonInfo.getAdministrativeProcedures());
		commonInfoViewModel.setAdministrativeProceduresInstruction(commonInfo.getAdministrativeProceduresInstruction());
		commonInfoViewModel.setViews(commonInfo.getViews());
		commonInfoViewModel.setIntroduction(commonInfo.getIntroduction());
		commonInfoViewModel.setOrganizationalStructure(commonInfo.getOrganizationalStructure());
		commonInfoViewModel.setMostViewed(commonInfo.getMostViewed().toString());
		commonInfoViewModel.setAdAmount(commonInfo.getAdAmount().toString());
		commonInfoViewModel.setNewsInSameCatalog(commonInfo.getNewsInSameCatalog().toString());
		commonInfoViewModel.setNewsInSearchResult(commonInfo.getNewsInSearchResult().toString());
		commonInfoViewModel.setSameSubjectTitle(commonInfo.getSameSubjectTitle());
		commonInfoViewModel.setToday(commonInfo.getToday());
		commonInfoViewModel.setPostedDate(commonInfo.getPostedDate());
		commonInfoViewModel.setAuthor(commonInfo.getAuthor());
		commonInfoViewModel.setPrint(commonInfo.getPrint());
		commonInfoViewModel.setHomePage(commonInfo.getHomePage());
		commonInfoViewModel.setDocument(commonInfo.getDocument());
		commonInfoViewModel.setLegalDocument(commonInfo.getLegalDocument());
		commonInfoViewModel.setGoTop(commonInfo.getGoTop());
		commonInfoViewModel.setDuty(commonInfo.getDuty());
		commonInfoViewModel.setAchievement(commonInfo.getAchievement());
		commonInfoViewModel.setPageSize(commonInfo.getPageSize().toString());
		commonInfoViewModel.setRightTopSize(commonInfo.getRightTopSize().toString());
		commonInfoViewModel.setRightCenterSize(commonInfo.getRightCenterSize().toString());
		
		model.addAttribute("commonInfoViewModel", commonInfoViewModel);
		
		return new ModelAndView("cm/configurations");
	}
	
	@RequiresPermissions(value = {"configuration:manage"})
	@RequestMapping(value = "/cm/configurations", method = RequestMethod.POST)
	public ModelAndView processUpdateConfiguration(
			@Valid @ModelAttribute("commonInfoViewModel") CommonInfoViewModel commonInfoViewModel,
			BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("cm/configurations");
		} else {
			CommonInfo commonInfo = (CommonInfo) request.getServletContext().getAttribute("COMMONINFO");
			
			commonInfo.setWebTitle(commonInfoViewModel.getWebTitle());
			commonInfo.setMarqueeTitle(commonInfoViewModel.getMarqueeTitle());
			commonInfo.setRecentNews(Integer.parseInt(commonInfoViewModel.getRecentNews()));
			commonInfo.setQuestionAnswer(Integer.parseInt(commonInfoViewModel.getQuestionAnswer()));
			commonInfo.setTcCatalogs(Integer.parseInt(commonInfoViewModel.getTcCatalogs()));
			commonInfo.setSameSubjects(Integer.parseInt(commonInfoViewModel.getSameSubjects()));
			commonInfo.setHeadlines(Integer.parseInt(commonInfoViewModel.getHeadlines()));
			commonInfo.setHeadlineCaption(commonInfoViewModel.getHeadlineCaption());
			commonInfo.setImageCaption(commonInfoViewModel.getImageCaption());
			commonInfo.setVideoCaption(commonInfoViewModel.getVideoCaption());
			commonInfo.setAudioCaption(commonInfoViewModel.getAudioCaption());
			commonInfo.setDetailsCaption(commonInfoViewModel.getDetailsCaption());
			commonInfo.setAdministrativeProcedures(commonInfoViewModel.getAdministrativeProcedures());
			commonInfo.setAdministrativeProceduresInstruction(commonInfoViewModel.getAdministrativeProceduresInstruction());
			commonInfo.setViews(commonInfoViewModel.getViews());
			commonInfo.setIntroduction(commonInfoViewModel.getIntroduction());
			commonInfo.setOrganizationalStructure(commonInfoViewModel.getOrganizationalStructure());
			commonInfo.setMostViewed(Integer.parseInt(commonInfoViewModel.getMostViewed()));
			commonInfo.setAdAmount(Integer.parseInt(commonInfoViewModel.getAdAmount()));
			commonInfo.setNewsInSameCatalog(Integer.parseInt(commonInfoViewModel.getNewsInSameCatalog()));
			commonInfo.setNewsInSearchResult(Integer.parseInt(commonInfoViewModel.getNewsInSearchResult()));
			commonInfo.setSameSubjectTitle(commonInfoViewModel.getSameSubjectTitle());
			commonInfo.setToday(commonInfoViewModel.getToday());
			commonInfo.setPostedDate(commonInfoViewModel.getPostedDate());
			commonInfo.setAuthor(commonInfoViewModel.getAuthor());
			commonInfo.setPrint(commonInfoViewModel.getPrint());
			commonInfo.setHomePage(commonInfoViewModel.getHomePage());
			commonInfo.setDocument(commonInfoViewModel.getDocument());
			commonInfo.setLegalDocument(commonInfoViewModel.getLegalDocument());
			commonInfo.setGoTop(commonInfoViewModel.getGoTop());
			commonInfo.setDuty(commonInfoViewModel.getDuty());
			commonInfo.setAchievement(commonInfoViewModel.getAchievement());
			commonInfo.setPageSize(Integer.parseInt(commonInfoViewModel.getPageSize()));
			commonInfo.setRightTopSize(Integer.parseInt(commonInfoViewModel.getRightTopSize()));
			commonInfo.setRightCenterSize(Integer.parseInt(commonInfoViewModel.getRightCenterSize()));
			
			ResWriter.writeCommonInfo(commonInfo, request.getServletContext().getRealPath(Constants.COMMONINFO_CONFIG_FILE));
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("configurations.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/configurations"));
		}
	}
}
