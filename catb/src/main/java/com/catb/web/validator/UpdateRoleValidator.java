package com.catb.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.bo.RoleBO;
import com.catb.model.Role;
import com.catb.web.viewmodel.RoleViewModel;

@Component
public class UpdateRoleValidator implements Validator {
	
	@Autowired
	private RoleBO roleBO;
	
	private Integer roleId;
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean supports(Class<?> clazz) {
		return UpdateRoleValidator.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		RoleViewModel model = (RoleViewModel) target;
		if (model.getName() != null && !"".equals(model.getName().trim()) && !errors.hasErrors()) {
			Role role = roleBO.getRoleByName(model.getName().trim());
			if (role != null && role.getId() != roleId) {
				errors.reject("roleName.already.inuse");
			}
		}
	}
}
