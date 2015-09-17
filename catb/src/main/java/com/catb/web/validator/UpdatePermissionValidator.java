package com.catb.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.bo.PermissionBO;
import com.catb.model.Permission;
import com.catb.web.viewmodel.PermissionViewModel;

@Component
public class UpdatePermissionValidator implements Validator {
	
	@Autowired
	private PermissionBO permissionBO;
	
	private Integer permissionId;
	
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public boolean supports(Class<?> clazz) {
		return UpdatePermissionValidator.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		PermissionViewModel model = (PermissionViewModel) target;
		if (!errors.hasErrors()) {
			if (model.getName() != null && !"".equals(model.getName().trim())) {
				Permission permission = permissionBO.getPermissionByName(model.getName().trim());
				if (permission != null && permission.getId() != permissionId) {
					errors.reject("permissionName.already.inuse");
				}
			}
			if (model.getPerStr() != null && !"".equals(model.getPerStr().trim())) {
				Permission permission = permissionBO.getPermissionByPerStr(model.getPerStr().trim());
				if (permission != null && permission.getId() != permissionId) {
					errors.reject("permissionString.already.inuse");
				}
			}
		}
	}
}
