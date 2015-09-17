package com.catb.web.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.catb.model.CommonInfo;

public class Util {

	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		
		return ipAddress;
	}
	
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
	
	public static String getCurrentDateString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
	
	public static String getRandomString() {
		return String.valueOf(new Date().getTime());
	}
	
	public static Integer getPageSize(HttpServletRequest request) {
		CommonInfo commonInfo = (CommonInfo) request.getServletContext().getAttribute("COMMONINFO");
		return commonInfo != null && commonInfo.getPageSize() != null ? commonInfo.getPageSize() : 20;
	}
	
	public static Integer getNewsListSize(HttpServletRequest request) {
		CommonInfo commonInfo = (CommonInfo) request.getServletContext().getAttribute("COMMONINFO");
		return commonInfo != null && commonInfo.getNewsInSameCatalog() != null ? commonInfo.getNewsInSameCatalog() : 20;
	}
	
	public static Integer getNewsAmountInSameSubject(HttpServletRequest request) {
		CommonInfo commonInfo = (CommonInfo) request.getServletContext().getAttribute("COMMONINFO");
		return commonInfo != null && commonInfo.getSameSubjects() != null ? commonInfo.getSameSubjects() : 10;
	}
	
	public static void createFolder(String folder) {
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
	}
}
