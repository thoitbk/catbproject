package catb.vanthu.tags;

import javax.servlet.http.HttpSession;

import catb.vanthu.auth.AuthUtil;
import catb.vanthu.model.DepartmentDocument;
import catb.vanthu.model.Document;
import catb.vanthu.model.UserInfo;

public class TagFunctions {
	
	public static UserInfo getUserInfo(HttpSession session) {
		return AuthUtil.getUserInfo(session);
	}
	
	public static Boolean isRead(Document document, Integer departmentId) {
		for (DepartmentDocument d : document.getDepartmentDocuments()) {
			if (d.getDepartment().getId() != null && d.getDepartment().getId().equals(departmentId)) {
				return d.getIsRead();
			}
		}
		return true;
	}
}
