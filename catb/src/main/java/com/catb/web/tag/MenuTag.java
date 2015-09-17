package com.catb.web.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.catb.web.component.Menu;

public class MenuTag extends TagSupport {

	private static final long serialVersionUID = 196712990465584729L;
	
	private static final String BEGIN_UL = "<ul>";
	private static final String END_UL = "</ul>";
	private static final String BEGIN_LI = "<li>";
	private static final String END_LI = "</li>";
	
	private List<Menu> menuHierarchy;
	private HttpServletRequest request;
	private String menuId;
	private String selectedClass;
	
	public void setMenuHierarchy(List<Menu> menuHierarchy) {
		this.menuHierarchy = menuHierarchy;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public void setSelectedClass(String selectedClass) {
		this.selectedClass = selectedClass;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print(buildMenu());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new JspException(ex);
		}
		return EVAL_PAGE;
	}
	
	private String buildMenu() {
		StringBuffer buffer = new StringBuffer();
		if (menuId != null && !"".equals(menuId.trim())) {
			buffer.append("<ul id='" + menuId.trim() + "'>").append("\n");
		} else {
			buffer.append(BEGIN_UL).append("\n");
		}
		
		for (Menu menu : menuHierarchy) {
			traverseMenuHierarchy(menu, buffer);
		}
		
		buffer.append(END_UL).append("\n");
		
		return buffer.toString();
	}
	
	private void traverseMenuHierarchy(Menu menu, StringBuffer buffer) {
		String a = "<a href='" + menu.getUrl() + "'>" + menu.getTitle() + "</a>";
		String beginLi = BEGIN_LI;
		if (request != null && selectedClass != null && !"".equals(selectedClass.trim()) && menu.getLevel() == 0) {
			String requestUrl = request.getRequestURL().toString();
			if (requestUrl.contains(menu.getUrl())) {
				beginLi = "<li class='" + selectedClass + "'>";
			}
		}
		
		buffer.append(beginLi).append(a).append("\n");
		List<Menu> childMenus = menu.getChildMenus();
		if (childMenus == null || childMenus.size() == 0) {
			buffer.append(END_LI).append("\n");
			return;
		} else {
			buffer.append(BEGIN_UL).append("\n");
			for (Menu m : childMenus) {
				traverseMenuHierarchy(m, buffer);
			}
			buffer.append(END_UL).append("\n");
			buffer.append(END_LI).append("\n");
		}
	}
}
