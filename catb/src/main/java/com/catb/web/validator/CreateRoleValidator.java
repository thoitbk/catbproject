package com.catb.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.bo.RoleBO;
import com.catb.model.Role;
import com.catb.web.viewmodel.RoleViewModel;

@Component
public class CreateRoleValidator implements Validator {
	
	@Autowired
	private RoleBO roleBO;
	
	public boolean supports(Class<?> clazz) {
		return CreateRoleValidator.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		RoleViewModel model = (RoleViewModel) target;
		if (model.getName() != null && !"".equals(model.getName().trim()) && !errors.hasErrors()) {
			Role role = roleBO.getRoleByName(model.getName().trim());
			if (role != null) {
				errors.reject("roleName.already.existed");
			}
		}
	}
}
