package com.catb.web.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.catb.bo.ImageBO;
import com.catb.bo.NewsBO;
import com.catb.bo.NewsCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.CommonInfo;
import com.catb.model.Image;
import com.catb.model.NewsCatalog;
import com.catb.vo.RightCenterNews;
import com.catb.web.component.MenuLoader;

@Controller
public class ComponentController {
	
	@Autowired
	private NewsCatalogBO newsCatalogBO;
	
	@Autowired
	private NewsBO newsBO;
	
	@Autowired
	private ImageBO imageBO;
	
	@RequestMapping(value = "/rightBar", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getRightBar(ModelMap model, HttpServletRequest request) {
		CommonInfo commonInfo = (CommonInfo) request.getServletContext().getAttribute("COMMONINFO");
		Integer rightTopSize = 10;
		Integer rightCenterSize = 10;
		if (commonInfo != null && commonInfo.getRightTopSize() != null) {
			rightTopSize = commonInfo.getRightTopSize();
		}
		if (commonInfo != null && commonInfo.getRightCenterSize() != null) {
			rightCenterSize = commonInfo.getRightCenterSize();
		}
		
		List<NewsCatalog> rightTopNewsCatalogs = newsCatalogBO.getNewsCatalogs(
				MenuLoader.DisplayLocation.RIGHT_TOP.getPosition(), null, 0, true, rightTopSize);
		
//		NewsCatalog comment = new NewsCatalog(PropertiesUtil.getProperty("comment.name"), Constants.COMMENT_URL);
//		NewsCatalog criminalDenouncement = new NewsCatalog(PropertiesUtil.getProperty("criminalDenouncement.name"), Constants.CRIMINAL_DENOUNCEMENT_URL);
//		NewsCatalog administrativeProcedures = new NewsCatalog(PropertiesUtil.getProperty("administrativeProcedures.name"), Constants.ADMINISTRATIVE_PROCEDURE_URL);
		NewsCatalog document = new NewsCatalog(PropertiesUtil.getProperty("document.name"), Constants.DOCUMENT_URL);
		NewsCatalog contacts = new NewsCatalog("Danh bạ điện thoại", "danh-ba/danh-ba.xls");
//		List<NewsCatalog> columns = new LinkedList<NewsCatalog>(Arrays.asList(comment, criminalDenouncement, administrativeProcedures, document));
		List<NewsCatalog> columns = new LinkedList<NewsCatalog>(Arrays.asList(contacts, document));
		List<Image> images = imageBO.getImages(Constants.IMAGE_AMOUNT);
		
		model.addAttribute("rightTopNewsCatalogs", rightTopNewsCatalogs);
		model.addAttribute("columns", columns);
		RightCenterNews rightCenterNews = newsBO.getRightCenterNews(rightCenterSize);
		model.addAttribute("rightCenterNews", rightCenterNews);
		model.addAttribute("images", images);
		
		return new ModelAndView("rightBar");
	}
}
