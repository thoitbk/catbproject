package com.catb.web.bootstrap;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.catb.bo.AdCatalogBO;
import com.catb.bo.DepartmentBO;
import com.catb.bo.LinkCatalogBO;
import com.catb.common.Constants;
import com.catb.dao.statics.ResReader;
import com.catb.web.component.Menu;
import com.catb.web.component.MenuLoader;
import com.catb.web.util.Util;

@Component
public class ComponentsLoader {
	
	static Logger logger = Logger.getLogger(ComponentsLoader.class);
	
	@Autowired
	private MenuLoader menuLoader;
	
	@Autowired
	private LinkCatalogBO linkCatalogBO;
	
	@Autowired
	private AdCatalogBO adCatalogBO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	private ServletContext context;

	public void setContext(ServletContext context) {
		this.context = context;
	}
	
	public void load() {
		if (context == null) {
			logger.error("context cannot be null");
			throw new Error("Context is null");
		} else {
			logger.info("Loading components...");
			
			logger.info("Loading resources from conf.properties...");
			Constants.load();
			
			logger.info("Reading xml resources from res...");
			context.setAttribute("CM_MENU", ResReader.readCMMenuConfig(context.getRealPath(Constants.CM_MENU_CONFIG_FILE)));
			context.setAttribute("COMMONINFO", ResReader.readCommonInfo(context.getRealPath(Constants.COMMONINFO_CONFIG_FILE)));
			context.setAttribute("DISPLAY_LOCATION", ResReader.readDisplayLocation(context.getRealPath(Constants.DISPLAY_LOCATION_CONFIG_FILE)));
			context.setAttribute("NEWS_STATUSES", ResReader.readNewsStatuses(context.getRealPath(Constants.NEWS_STATUSES_CONFIG_FILE)));
			
			logger.info("Save context path to context variable...");
			context.setAttribute("ct", context.getContextPath());
			context.setAttribute("news_ct", context.getContextPath() + Constants.NEWS_PREFIX);
			
			logger.info("Loading menu hierarchy...");
			String prefix = context.getContextPath() + Constants.NEWS_PREFIX + "/";
			menuLoader.setPrefix(prefix);
			List<Menu> menuTree = new LinkedList<Menu>();
			menuTree.add(new Menu(Constants.HOMEPAGE, "/home", 0, null));
			menuTree.addAll(menuLoader.loadMenuTree());
			context.setAttribute("MENU_HIERARCHY", menuTree);
			
			logger.info("Loading link list");
			context.setAttribute("LINK_LIST", linkCatalogBO.getLinkCatalogs());
			logger.info("Loading advertisements");
			context.setAttribute("ADVERTISEMENTS_LIST", adCatalogBO.getDisplayedAdCatalogs(Constants.MAX_ADS_NUM));
			logger.info("Loading departments");
			context.setAttribute("DEPARTMENTS_LIST", departmentBO.getDepartments());
			
			logger.info("Creating required directories...");
			Util.createFolder(Constants.ADMINISTRATIVE_PROCEDURE_LOCATION);
			Util.createFolder(Constants.DOCUMENT_LOCATION);
			Util.createFolder(Constants.NEWS_IMAGE_LOCATION);
			Util.createFolder(Constants.IMAGE_LOCATION);
			Util.createFolder(Constants.VIDEO_LOCATION);
			Util.createFolder(Constants.CONTACT_LOCATION);
		}
	}
}
