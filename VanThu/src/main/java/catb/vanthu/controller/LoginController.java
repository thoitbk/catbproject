package catb.vanthu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import catb.vanthu.auth.AuthUtil;
import catb.vanthu.auth.AuthenticationUserDetails;

@Controller
public class LoginController {
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU', 'NORMAL_USER')")
	@RequestMapping(value = "/loginsuccess", method = RequestMethod.GET)
	public RedirectView LoginSuccess(HttpSession session) {
		AuthenticationUserDetails user = (AuthenticationUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user == null) {
			return new RedirectView("login.html");
		} else {
			// save user to session
			AuthUtil.getUserInfo(session);
			
			return new RedirectView("home.html");
		}
	}
	
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginError(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpSession session) {
		session.removeAttribute("userInfo");
		return "login";
	}
}
