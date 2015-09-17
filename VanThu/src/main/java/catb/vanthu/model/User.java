package catb.vanthu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column(name = "password", length = 100)
	private String password;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "position", length = 400)
	private String position;
	
	@Column(name = "email", length = 200)
	private String email;
	
	@Column(name = "phone_number", length = 100)
	private String phoneNumber;
	
	@Column(name = "role")
	private Integer role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	public User() {
		
	}
	
	public User(Integer id, String username, String password, String name,
			String position, String email, String phoneNumber, Integer role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public User(Integer id, String username, String password, String name,
			String position, String email, String phoneNumber, Integer role,
			Department department) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.department = department;
	}

	public User(String username, String password, String name, String position,
			String email, String phoneNumber, Integer role) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public User(String username, String password, String name, String position,
			String email, String phoneNumber, Integer role, Department department) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.department = department;
	}

	public User(Integer id, String username, String password, String name,
			String position, String email, String phoneNumber) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
