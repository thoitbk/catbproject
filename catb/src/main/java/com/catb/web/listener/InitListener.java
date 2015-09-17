package com.catb.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.catb.web.bootstrap.ComponentsLoader;

public class InitListener implements ServletContextListener {
	
	static Logger logger = Logger.getLogger(InitListener.class);
	
	@Autowired
	private ComponentsLoader componentLoader;
	
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("------------------------- Begin stopping web application -------------------------");
		// Destroy cache manager instance
//		DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
//		EhCacheManager cacheManager = (EhCacheManager) securityManager.getCacheManager();
//		cacheManager.destroy();
		logger.info("------------------------- Stop web application successfully -------------------------");
	}

	public void contextInitialized(ServletContextEvent event) {
		PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("log4j.properties"));
		logger.info("\n------------------------- Begin starting web application -------------------------");
		
		WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext())
								.getAutowireCapableBeanFactory().autowireBean(this);
		componentLoader.setContext(event.getServletContext());
		componentLoader.load();
		
		logger.info("\n------------------------- Complete starting web application successfully -------------------------");
	}
}
