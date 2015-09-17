package com.catb.web.validator;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.bo.UserBO;
import com.catb.model.User;
import com.catb.web.viewmodel.ChangePasswordViewModel;

@Component
public class ChangePasswordValidator implements Validator {
	
	private Integer id;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PasswordService passwordService;
	
	public void setId(Integer id) {
		this.id = id;
	}

	public boolean supports(Class<?> clazz) {
		return ChangePasswordViewModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ChangePasswordViewModel model = (ChangePasswordViewModel) target;
		if (!errors.hasErrors()) {
			if (!model.getNewPassword().equals(model.getConfirmNewPassword())) {
				errors.reject("passwords.mismatch");
			}
			if (!errors.hasErrors()) {
				if (id != null) {
					User user = userBO.getUserById(id);
					String originalHash = user.getPassword();
					boolean isMatch = passwordService.passwordsMatch(model.getOldPassword(), originalHash);
					if (!isMatch) {
						errors.reject("oldPassword.incorrect");
					}
				}
			}
		}
	}

}
