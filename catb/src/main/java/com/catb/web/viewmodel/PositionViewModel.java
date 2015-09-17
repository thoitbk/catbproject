package com.catb.web.viewmodel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class PositionViewModel {
	
	@NotBlank
	@Size(min = 0, max = 500)
	private String name;
	
	@NotBlank
	@Size(min = 0, max = 100)
	private String code;
	
	@Size(min = 0, max = 500)
	private String description;
	
	public PositionViewModel() {
		
	}

	public PositionViewModel(String name, String code, String description) {
		this.name = name;
		this.code = code;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
