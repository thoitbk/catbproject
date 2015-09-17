package catb.vanthu.model;

// Object to be saved in session
public class UserInfo {
	
	private Integer id;
	private String username;
	private String name;
	private String position;
	private String email;
	private String phoneNumber;
	private String role;
	private Integer departmentId;
	private String departmentName;
	
	public UserInfo() {
		
	}

	public UserInfo(Integer id, String username, String name, String position,
			String email, String phoneNumber, String role, Integer departmentId,
			String departmentName) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
