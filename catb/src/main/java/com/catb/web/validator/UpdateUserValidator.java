package com.catb.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.bo.UserBO;
import com.catb.model.User;
import com.catb.web.viewmodel.UpdateUserViewModel;

@Component
public class UpdateUserValidator implements Validator {
	
	@Autowired
	private UserBO userBO;
	
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public boolean supports(Class<?> clazz) {
		return UpdateUserValidator.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		UpdateUserViewModel model = (UpdateUserViewModel) target;
		if (model.getUsername() != null && !"".equals(model.getUsername().trim()) && !errors.hasErrors()) {
			User user = userBO.getUserByUsername(model.getUsername().trim());
			if (user != null && user.getId() != userId) {
				errors.reject("username.already.inuse");
			}
		}
	}
}
