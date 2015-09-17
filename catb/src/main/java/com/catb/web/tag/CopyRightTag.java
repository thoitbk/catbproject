package com.catb.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CopyRightTag extends TagSupport {

	private static final long serialVersionUID = 2095500907454578746L;
	
	private static final String COPYRIGHT = "Thiết kế và lập trình bởi <strong><a href='http://thoitbk.blogspot.com/' target='_blank' style='text-decoration: none; color: red;'>Trần Anh Thơ</a></strong> - Cán bộ Phòng Tham mưu Công an tỉnh Thái Bình";
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print(COPYRIGHT);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new JspException(ex);
		}
		
		return EVAL_PAGE;
	}
}
