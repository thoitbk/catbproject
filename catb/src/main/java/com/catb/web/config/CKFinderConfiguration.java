package com.catb.web.config;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import com.ckfinder.connector.configuration.Configuration;

public class CKFinderConfiguration extends Configuration {

	public CKFinderConfiguration(ServletConfig servletConfig) {
		super(servletConfig);
	}
	
    @Override
	protected Configuration createConfigurationInstance() {
		return new CKFinderConfiguration(this.servletConf);
	}

	@Override
	public boolean checkAuthentication(HttpServletRequest request) {
		return SecurityUtils.getSubject().isAuthenticated();
	}
}
