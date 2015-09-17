package catb.vanthu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import catb.vanthu.auth.AuthUtil;
import catb.vanthu.bo.UserBO;
import catb.vanthu.model.User;
import catb.vanthu.model.UserInfo;
import catb.vanthu.validator.UserChangeInfoValidator;
import catb.vanthu.validator.UserChangePasswordValidator;
import catb.vanthu.viewmodel.UserChangeInfoViewModel;
import catb.vanthu.viewmodel.UserChangePasswordViewModel;

@Controller
public class AccountController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private UserChangeInfoValidator userChangeInfoValidator;
	
	@Autowired
	private UserChangePasswordValidator userChangePasswordValidator;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU', 'NORMAL_USER')")
	@RequestMapping(value = "/user/changeInfo", method = RequestMethod.GET)
	public String initUserChangeInfoForm(ModelMap model, HttpSession session) {
		UserInfo userInfo = AuthUtil.getUserInfo(session);
		User user = userBO.findUserByUsername(userInfo.getUsername());
		UserChangeInfoViewModel userChangeInfoViewModel = new UserChangeInfoViewModel(user.getUsername(), 
				user.getName(), user.getPosition(), user.getEmail(), user.getPhoneNumber());
		model.addAttribute("userChangeInfoViewModel", userChangeInfoViewModel);
		return "user/changeInfo";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU', 'NORMAL_USER')")
	@RequestMapping(value = "/user/changeInfo", method = RequestMethod.POST)
	public ModelAndView processUserChangeInfoSubmit(
			@ModelAttribute("userChangeInfoViewModel") UserChangeInfoViewModel userChangeInfoViewModel,
			BindingResult bindingResult, SessionStatus sessionStatus, HttpSession session) {
		userChangeInfoValidator.validate(userChangeInfoViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return new ModelAndView("user/changeInfo");
		} else {
			UserInfo userInfo = AuthUtil.getUserInfo(session);
			userBO.updateUser(new User(userInfo.getId(), null, null, 
					userChangeInfoViewModel.getName(), null, 
					userChangeInfoViewModel.getEmail(), 
					userChangeInfoViewModel.getPhoneNumber()));
			AuthUtil.updateUserInfo(session);
			sessionStatus.setComplete();
			return new ModelAndView(new RedirectView("/user/changeInfo.html"));
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU', 'NORMAL_USER')")
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
	public String initUserChangePasswordForm(ModelMap model) {
		UserChangePasswordViewModel userChangePasswordViewModel = new UserChangePasswordViewModel();
		model.addAttribute("userChangePasswordViewModel", userChangePasswordViewModel);
		return "user/changePassword";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU', 'NORMAL_USER')")
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
	public ModelAndView processUserChangePasswordSubmit(
			@ModelAttribute("userChangePasswordViewModel") UserChangePasswordViewModel userChangePasswordViewModel,
			BindingResult result, SessionStatus status, HttpSession session) {
		userChangePasswordValidator.validate(userChangePasswordViewModel, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("user/changePassword");
		} else {
			UserInfo userInfo = AuthUtil.getUserInfo(session);
			userBO.updateUserPassword(userInfo.getId(), userChangePasswordViewModel.getNewPassword());
			AuthUtil.updateUserInfo(session);
			status.setComplete();
			
			return new ModelAndView(new RedirectView("/user/changePassword.html"));
		}
	}
}
