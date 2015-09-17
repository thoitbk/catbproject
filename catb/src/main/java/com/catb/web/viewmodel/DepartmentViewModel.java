package com.catb.web.viewmodel;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class DepartmentViewModel {
	
	@NotBlank
	@Size(min = 0, max = 200)
	private String code;
	
	@NotBlank
	@Size(min = 0, max = 500)
	private String name;
	
	@Size(min = 0, max = 100)
	private String phone;
	
	@Size(min = 0, max = 100)
	private String fax;
	
	@Size(min = 0, max = 500)
	private String description;

	public DepartmentViewModel() {
		
	}

	public DepartmentViewModel(String code, String name, String phone,
			String fax, String description) {
		this.code = code;
		this.name = name;
		this.phone = phone;
		this.fax = fax;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
