package catb.vanthu.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import catb.vanthu.bo.DepartmentBO;
import catb.vanthu.model.Department;
import catb.vanthu.util.Constants;
import catb.vanthu.viewmodel.UpdateDepartmentViewModel;

public class UpdateDepartmentValidator implements Validator {
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateDepartmentViewModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "code.required");
		
		UpdateDepartmentViewModel model = (UpdateDepartmentViewModel) target;
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
		
		if (model.getBureau() == -1) {
			errors.rejectValue("bureau", "required.bureau");
		}
		
		if (!errors.hasErrors()) {
			Department department = departmentBO.getDepartmentByCode(model.getCode());
			Department d = departmentBO.findDepartmentById(model.getId());
			if (department != null && d != null && !model.getCode().equalsIgnoreCase(d.getCode())) {
				errors.reject("existed.code");
			}
		}
	}
}
