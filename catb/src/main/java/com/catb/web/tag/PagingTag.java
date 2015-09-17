package com.catb.web.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.catb.common.Constants;

public class PagingTag extends TagSupport {
	
	private static final long serialVersionUID = -7133140064585701534L;
	
	// Mandatory fields
	private PageInfo pageInfo;
	private String link;
	
	// Optional fields
	private String cssClass;
	private String paramName = "p";
	private int numPageLink = Constants.NUMBER_OF_PAGE_LINK;
	private String selectedLinkCssClass = "selected";
	private Map<String, String> params;
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setSelectedLinkCssClass(String selectedLinkCssClass) {
		this.selectedLinkCssClass = selectedLinkCssClass;
	}

	public void setNumPageLink(int numPageLink) {
		this.numPageLink = numPageLink;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print(renderAllPageLinks());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new JspException(ex);
		}
		return SKIP_BODY;
	}
	
	private String renderPageLink(int pageNum, String linkText, boolean isSelected) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<a href='").append(link).append("?").append(paramName).append("=").append(pageNum);
		if (params != null && !params.isEmpty()) {
			for (String k : params.keySet()) {
				builder.append("&").append(k).append("=").append(params.get(k));
			}
		}
		builder.append("'");
		if (isSelected) {
			builder.append(" class='").append(selectedLinkCssClass).append("'");
		} else if (cssClass != null) {
			builder.append(" class='").append(cssClass).append("'");
		}
		builder.append(">").append(linkText).append("</a>");
		
		return builder.toString();
	}
	
	private String renderAllPageLinks() {
		int totalPages = (int) Math.ceil(((double) pageInfo.getTotalItems()) / pageInfo.getPageSize());
		int currentPage = pageInfo.getCurrentPage();
		int lowHalf = (int) Math.ceil(((double) numPageLink) / 2);
		int highHalf = numPageLink - lowHalf - 1;
		int floorPage = currentPage - lowHalf >= 1 ? currentPage - lowHalf : 1;
		int ceilPage = 1;
		
		if (currentPage <= lowHalf) {
			int t = totalPages <= numPageLink ? totalPages : numPageLink;
			ceilPage = t;
		} else {
			if (currentPage + highHalf <= totalPages) {
				ceilPage = currentPage + highHalf;
			} else {
				ceilPage = totalPages;
				floorPage = ceilPage - numPageLink >= 1 ? ceilPage - numPageLink : 1;
			}
			ceilPage = currentPage + highHalf <= totalPages ? currentPage + highHalf : totalPages;
		}
		
		StringBuilder builder = new StringBuilder();
		
		if (pageInfo.getCurrentPage() > 1) {
			builder.append(renderPageLink(1, " << ", false));
			builder.append(renderPageLink(pageInfo.getCurrentPage() - 1, " < ", false));
		}
		
		for (int i = floorPage; i <= ceilPage; i++) {
			builder.append(renderPageLink(i, String.valueOf(i), i == pageInfo.getCurrentPage()));
		}
		
		if (pageInfo.getCurrentPage() < totalPages) {
			builder.append(renderPageLink(pageInfo.getCurrentPage() + 1, " > ", false));
			builder.append(renderPageLink(totalPages, " >> ", false));
		}
		
		return builder.toString();
	}
}
