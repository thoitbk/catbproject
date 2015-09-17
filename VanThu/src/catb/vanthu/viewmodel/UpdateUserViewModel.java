package catb.vanthu.viewmodel;

public class UpdateUserViewModel {
	
	private Integer id;
	private String username;
	private String name;
	private String position;
	private String email;
	private String phoneNumber;
	private Integer role;
	private Integer department;
	
	public UpdateUserViewModel() {
		
	}

	public UpdateUserViewModel(Integer id, String username, String name,
			String position, String email, String phoneNumber, Integer role,
			Integer department) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}
}
