package com.catb.web.exhandler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.catb.vo.UserInfo;
import com.catb.web.util.Util;
import com.catb.web.viewmodel.Status;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = {UnauthorizedException.class})
	public void handleUnauthorizedException(HttpServletRequest request, HttpServletResponse response) {
		String log = String.format("Unauthorized access to %s from %s at %s", 
											request.getRequestURL().toString(), 
											getCurrentUsername(request),
											Util.getIpAddress(request));
		logger.info(log);
		
		try {
			if (Util.isAjaxRequest(request)) {
				Status status = new Status(Status.UNAUTHORIZED, "Unauthorized request");
				ObjectMapper objectMapper = new ObjectMapper();
				String s = objectMapper.writeValueAsString(status);
				
				response.setContentType("application/json");
				PrintWriter printWriter = response.getWriter();
				printWriter.print(s);
			} else {
				response.sendRedirect(request.getContextPath() + "/cm/unauthorized");
			}
		} catch (Exception ex) {
			logger.error("Response from unauthorized handler failed due to error of serializing to json or responding", ex);
		}
	}
	
	@ExceptionHandler(value = {UnauthenticatedException.class})
	public void handleUnauthenticatedException(HttpServletRequest request, HttpServletResponse response) {
		String log = String.format("Unauthenticated access to %s from %s at %s", 
															request.getRequestURL().toString(), 
															getCurrentUsername(request), 
															Util.getIpAddress(request));
		logger.info(log);
		
		try {
			if (Util.isAjaxRequest(request)) {
				Status status = new Status(Status.UNAUTHENTICATED, "Unauthenticated request");
				ObjectMapper objectMapper = new ObjectMapper();
				String s = objectMapper.writeValueAsString(status);
				
				response.setContentType("application/json");
				PrintWriter printWriter = response.getWriter();
				printWriter.print(s);
			} else {
				response.sendRedirect(request.getContextPath() + "/cm/login");
			}
		} catch (Exception ex) {
			logger.error("Response from unauthenticated handler failed due to error of serializing to json or responding", ex);
		}
	}
	
	@ExceptionHandler(value = {TypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
	public void handleInvalidParameterException(HttpServletRequest request, HttpServletResponse response) {
		String log = String.format("Attempt to access to invalid url of %s from %s at %s", 
									request.getRequestURL().toString(), 
									getCurrentUsername(request),
									Util.getIpAddress(request));
		logger.info(log);
		
		try {
			if (Util.isAjaxRequest(request)) {
				Status status = new Status(Status.NOTEXISTED_RESOURCE, "Nonexisted resource");
				ObjectMapper objectMapper = new ObjectMapper();
				String s = objectMapper.writeValueAsString(status);
				
				response.setContentType("application/json");
				PrintWriter printWriter = response.getWriter();
				printWriter.print(s);
			} else {
				Subject subject = SecurityUtils.getSubject();
				if (subject.isAuthenticated()) {
					response.sendRedirect(request.getContextPath() + "/cm/notExistedResource");
				} else {
					response.sendRedirect(request.getContextPath() + "/notExistedResource");
				}
			}
		} catch (Exception ex) {
			logger.error("Response from nonexisted-resource handler failed due to error of serializing to json or responding", ex);
		}
	}
	
	@ExceptionHandler(value = {Exception.class})
	public void handleGeneralException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		logger.error("Internal exception: ", ex);
		
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			try {
				if (Util.isAjaxRequest(request)) {
					Status status = new Status(Status.INTERNAL_ERROR, "Intenal error");
					ObjectMapper objectMapper = new ObjectMapper();
					String s = objectMapper.writeValueAsString(status);
					
					response.setContentType("application/json");
					PrintWriter printWriter = response.getWriter();
					printWriter.print(s);
				} else {
					response.sendRedirect("/cm/internalError");
				}
			} catch (Exception e) {
				logger.error("Response from general exception handler failed due to error of serializing to json or responding", ex);
			}
		} else {
			try {
				response.sendRedirect("/notFound");
			} catch (Exception e) {
				logger.error("Exception occurred during processing external request", ex);
			}
		}
	}
	
	private String getCurrentUsername(HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		return userInfo != null ? userInfo.getUsername() : "Unknown person";
	}
}
