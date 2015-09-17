package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class RoleViewModel {
	
	@NotBlank
	@Size(min = 0, max = 100)
	private String name;
	
	@Size(min = 0, max = 500)
	private String description;

	public RoleViewModel() {
		
	}

	public RoleViewModel(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
