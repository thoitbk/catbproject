package catb.vanthu.viewmodel;

public class UpdateDepartmentViewModel {
	
	private Integer id;
	private String code;
	private String name;
	private String phoneNumber;
	private String email;
	private Boolean inProvince;
	private Integer bureau;
	
	public UpdateDepartmentViewModel() {
		
	}

	public UpdateDepartmentViewModel(Integer id, String code, String name,
			String phoneNumber, String email, Boolean inProvince, Integer bureau) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.inProvince = inProvince;
		this.bureau = bureau;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getInProvince() {
		return inProvince;
	}

	public void setInProvince(Boolean inProvince) {
		this.inProvince = inProvince;
	}

	public Integer getBureau() {
		return bureau;
	}

	public void setBureau(Integer bureau) {
		this.bureau = bureau;
	}
}
