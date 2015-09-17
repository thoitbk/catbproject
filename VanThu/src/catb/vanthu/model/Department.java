package catb.vanthu.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "department")
public class Department implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "code", length = 200)
	private String code;

	@Column(name = "name", length = 500)
	private String name;

	@Column(name = "phone_number", length = 100)
	private String phoneNumber;

	@Column(name = "email", length = 100)
	private String email;
	
	@Column(name = "in_province")
	private Boolean inProvince;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bureau_id")
	private Bureau bureau;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	private Set<User> users = new HashSet<User>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "department_document_send", 
			joinColumns = { @JoinColumn(name = "department_id", nullable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "document_id", nullable = false) })
	private Set<Document> documents = new HashSet<Document>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.department")
	private Set<DepartmentDocument> departmentDocuments = new HashSet<DepartmentDocument>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "department_coming_document_send", 
			joinColumns = { @JoinColumn(name = "department_id", nullable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "coming_document_id", nullable = false) })
	private Set<ComingDocument> comingDocuments = new HashSet<ComingDocument>();
	
	public Department() {

	}
	
	public Department(Integer id, String code, String name, String phoneNumber,
			String email, Boolean inProvince) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.inProvince = inProvince;
	}

	public Department(String code, String name, String phoneNumber, String email) {
		this.code = code;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public Department(String code, String name, String phoneNumber,
			String email, Boolean inProvince) {
		this.code = code;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.inProvince = inProvince;
	}

	public Department(String code, String name, String phoneNumber,
			String email, Boolean inProvince, Bureau bureau) {
		super();
		this.code = code;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.inProvince = inProvince;
		this.bureau = bureau;
	}

	public Department(String code, String name, String phoneNumber,
			String email, Set<User> users, Set<Document> documents) {
		this.code = code;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.users = users;
		this.documents = documents;
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

	public Bureau getBureau() {
		return bureau;
	}

	public void setBureau(Bureau bureau) {
		this.bureau = bureau;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	public Set<DepartmentDocument> getDepartmentDocuments() {
		return departmentDocuments;
	}

	public void setDepartmentDocuments(Set<DepartmentDocument> departmentDocuments) {
		this.departmentDocuments = departmentDocuments;
	}

	public Set<ComingDocument> getComingDocuments() {
		return comingDocuments;
	}

	public void setComingDocuments(Set<ComingDocument> comingDocuments) {
		this.comingDocuments = comingDocuments;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		
		Department that = (Department) obj;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		
		return true;
	}
	
	public int hashCode() {
		return 31 * (id != null ? id.hashCode() : 0);
	}
}
