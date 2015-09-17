package com.catb.web.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class LinkTag extends TagSupport {

	private static final long serialVersionUID = 3781024435476740927L;
	
	private String link;
	private Map<String, String> params;
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print(link + generateStringQuery());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new JspException(ex);
		}
		return EVAL_PAGE;
	}
	
	private String generateStringQuery() {
		StringBuilder stringBuilder = null;
		if (params != null && params.size() > 0) {
			stringBuilder = new StringBuilder("?");
			for (String k : params.keySet()) {
				if (k != null && !"".equals(k.trim())) {
					stringBuilder.append(k.trim()).append("=").append(params.get(k)).append("&");
				}
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			
			return stringBuilder.toString();
		}
		
		return "";
	}
}
