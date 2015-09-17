package com.catb.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.bo.UserBO;
import com.catb.model.User;
import com.catb.web.viewmodel.EditUserViewModel;

@Component
public class EditUserValidator implements Validator {
	
	@Autowired
	private UserBO userBO;
	
	private Integer id;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean supports(Class<?> clazz) {
		return EditUserViewModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		EditUserViewModel model = (EditUserViewModel) target;
		String username = model.getUsername();
		
		if (!errors.hasErrors()) {
			if (id != null && username != null) {
				User user = userBO.getUserByUsername(username);
				if (!id.equals(user.getId())) {
					errors.reject("user.already.exist");
				}
			}
		}
	}
}
