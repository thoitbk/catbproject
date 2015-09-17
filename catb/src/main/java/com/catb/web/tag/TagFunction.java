package com.catb.web.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.catb.web.util.DiacriticsRemover;

public class TagFunction {
	
	@SuppressWarnings("unchecked")
	public static String[] getActiveMenuId(HttpServletRequest request) {
		String url = (String) request.getAttribute("javax.servlet.forward.request_uri");
		List<String[]> menuList = (List<String[]>) request.getServletContext().getAttribute("CM_MENU");
		
		String[] clickedMenuId = new String[2];
		if (url != null && menuList != null) {
			for (String[] menu : menuList) {
				if (menu != null && menu.length == 3 && menu[0] != null && url.contains(menu[0])) {
					clickedMenuId[0] = menu[1];
					clickedMenuId[1] = menu[2];
					break;
				}
			}
		}
		
		return clickedMenuId;
	}
	
	public static String getMenuClass(String s, String t, String r) {
		if (s != null && t != null && s.trim().equalsIgnoreCase(t.trim())) {
			return r;
		}
		
		return "";
	}
	
	public static String toFriendlyUrl(String s) {
		return DiacriticsRemover.toFriendlyUrl(s);
	}
}
