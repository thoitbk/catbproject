package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class PermissionViewModel {
	
	@NotBlank
	@Size(min = 0, max = 100)
	private String name;
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String perStr;
	
	@Size(min = 0, max = 500)
	private String description;
	
	public PermissionViewModel() {
		
	}

	public PermissionViewModel(String name, String perStr, String description) {
		this.name = name;
		this.perStr = perStr;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPerStr() {
		return perStr;
	}

	public void setPerStr(String perStr) {
		this.perStr = perStr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
