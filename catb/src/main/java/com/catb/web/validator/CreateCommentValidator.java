package com.catb.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.web.viewmodel.CreateCommentViewModel;

@Component
public class CreateCommentValidator implements Validator {
	
	private String validCode;
	
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public boolean supports(Class<?> clazz) {
		return CreateCommentValidator.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		CreateCommentViewModel model = (CreateCommentViewModel) target;
		if (!errors.hasErrors()) {
			if (model.getCaptcha() != null && !"".equals(model.getCaptcha().trim()) && validCode != null) {
				if (!validCode.equalsIgnoreCase(model.getCaptcha().trim())) {
					errors.reject("captcha.notmatch");
				}
			}
		}
	}
}
