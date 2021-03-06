package com.catb.web.viewmodel;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePasswordViewModel {
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
	@NotBlank
	private String confirmNewPassword;
	
	public ChangePasswordViewModel() {
		
	}

	public ChangePasswordViewModel(String oldPassword, String newPassword,
			String confirmNewPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
