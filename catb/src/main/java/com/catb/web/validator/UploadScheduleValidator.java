package com.catb.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catb.web.viewmodel.ScheduleFileViewModel;

@Component
public class UploadScheduleValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return ScheduleFileViewModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ScheduleFileViewModel model = (ScheduleFileViewModel) target;
		String fileName = model.getFile().getOriginalFilename();
		boolean isExcelFile = fileName.endsWith(".xls") || fileName.endsWith(".xlsx");
		if (!isExcelFile) {
			errors.reject("filetype.wrong");
		}
	}
}
