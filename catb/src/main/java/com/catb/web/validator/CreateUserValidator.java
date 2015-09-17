package com.catb.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.bo.UserBO;
import com.catb.model.User;
import com.catb.web.viewmodel.UserViewModel;

@Component
public class CreateUserValidator implements Validator {
	
	@Autowired
	private UserBO userBO;
	
	public boolean supports(Class<?> clazz) {
		return CreateUserValidator.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		UserViewModel model = (UserViewModel) target;
		if (model.getUsername() != null && !"".equals(model.getUsername().trim()) && !errors.hasErrors()) {
			User user = userBO.getUserByUsername(model.getUsername().trim());
			if (user != null) {
				errors.reject("username.already.existed");
			}
		}
	}
}
