package catb.vanthu.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import catb.vanthu.bo.UserBO;
import catb.vanthu.model.User;
import catb.vanthu.model.UserInfo;

@Component
public class AuthUtil {
	
	private static UserBO userBO;
	
	@Autowired
	public void setUserBO(UserBO userBO) {
		AuthUtil.userBO = userBO;
	}
	
	private static Map<Integer, String> roleMap = new HashMap<Integer, String>();
	
	static {
		roleMap.put(0, "ADMIN");
		roleMap.put(1, "VAN_THU");
		roleMap.put(2, "NORMAL_USER");
	}
	
	public static String getRoleName(int r) {
		return roleMap.get(r);
	}
	
	public static UserInfo getUserInfo(HttpSession session) {
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			AuthenticationUserDetails user = (AuthenticationUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = user.getUsername();
			User u = userBO.findUserByUsername(username);
			userInfo = new UserInfo(u.getId(), u.getUsername(), u.getName(),
					u.getPosition(), u.getEmail(), u.getPhoneNumber(), 
					AuthUtil.getRoleName(u.getRole()), u.getDepartment().getId(), u.getDepartment().getName());
			session.setAttribute("userInfo", userInfo);
		}
		
		return userInfo;
	}
	
	public static void updateUserInfo(HttpSession session) {
		session.removeAttribute("userInfo");
		getUserInfo(session);
	}
	
	public static String getUsername() {
		AuthenticationUserDetails user = (AuthenticationUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user != null ? user.getUsername() : null;
	}
}
