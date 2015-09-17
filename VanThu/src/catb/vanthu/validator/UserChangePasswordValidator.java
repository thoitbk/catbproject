package catb.vanthu.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import catb.vanthu.auth.AuthUtil;
import catb.vanthu.bo.UserBO;
import catb.vanthu.model.User;
import catb.vanthu.viewmodel.UserChangePasswordViewModel;

public class UserChangePasswordValidator implements Validator {
	
	@Autowired
	private UserBO userBO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserChangePasswordViewModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "oldPassword.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "newPassword.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmNewPassword", "confirmNewPassword.required");
		
		UserChangePasswordViewModel model = (UserChangePasswordViewModel) target;
		if (model.getNewPassword() != null && model.getNewPassword().length() > 0 &&
				model.getConfirmNewPassword() != null && model.getConfirmNewPassword().length() > 0 &&
				!model.getNewPassword().equals(model.getConfirmNewPassword())) {
			errors.reject("password.mismatch");
		}
		
		if (!errors.hasErrors()) {
			String username = AuthUtil.getUsername();
			User user = userBO.findUserByUsername(username);
			if (user == null || (user != null && !model.getOldPassword().equals(user.getPassword()))) {
				errors.reject("password.incorrect");
			}
		}
	}
}
