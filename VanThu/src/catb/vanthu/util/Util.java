package catb.vanthu.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Util {
	
	public static int evaluatePageNumAfterDelete(int beforePageNum, int totalItems, int pageSize, int deletedItems) {
		int totalPages = (int) Math.ceil(((double) totalItems) / pageSize);
		int remainderPage = totalItems % pageSize != 0 ? totalItems % pageSize : pageSize;
		if (beforePageNum == totalPages && remainderPage == deletedItems) {
			return beforePageNum - 1 > 0 ? beforePageNum - 1 : 1;
		}
		
		return beforePageNum;
	}
	
	public static String modifyQueryString(HttpServletRequest request, Map<String, String> modifiedParams) {
		Enumeration<String> params = request.getParameterNames();
		StringBuffer buffer = new StringBuffer();
		Set<String> keys = modifiedParams.keySet();
		while (params.hasMoreElements()) {
			String p = params.nextElement();
			if (keys.contains(p)) {
				buffer.append(p).append("=").append(modifiedParams.get(p)).append("&");
			} else {
				buffer.append(p).append("=").append(request.getParameter(p)).append("&");
			}
		}
		String s = buffer.toString();
		return s.substring(0, s.length() - 1);
	}
	
	public static HttpSession getSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true);
	}
}
