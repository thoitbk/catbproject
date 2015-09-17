package catb.vanthu.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import catb.vanthu.bo.UserBO;
import catb.vanthu.model.User;
import catb.vanthu.util.Constants;
import catb.vanthu.viewmodel.UpdateUserViewModel;

public class UpdateUserValidator implements Validator {
	
	@Autowired
	private UserBO userBO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateUserViewModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name");
		
		UpdateUserViewModel model = (UpdateUserViewModel) target;
		if (model.getEmail() != null && model.getEmail().length() > 0) {
			Pattern emailPattern = Pattern.compile(Constants.EMAIL_PATTERN);
			Matcher emailMatcher = emailPattern.matcher(model.getEmail());
			if (!emailMatcher.matches()) {
				errors.rejectValue("email", "malformed.email");
			}
		}
		
		if (model.getPhoneNumber() != null && model.getPhoneNumber().length() > 0) {
			Pattern phoneNumberPattern = Pattern.compile(Constants.PHONE_NUMBER_PATTERN);
			Matcher phoneNumberMatcher = phoneNumberPattern.matcher(model.getPhoneNumber());
			if (!phoneNumberMatcher.matches()) {
				errors.rejectValue("phoneNumber", "malformed.phoneNumber");
			}
		}
		
		if (model.getDepartment() == -1) {
			errors.rejectValue("department", "required.department");
		}
		
		if (!errors.hasErrors()) {
			User user = userBO.findUserByUsername(model.getUsername());
			User u = userBO.findUserById(model.getId());
			if (user != null && u != null && !model.getUsername().equalsIgnoreCase(u.getUsername())) {
				errors.reject("username.existed");
			}
		}
	}
}
