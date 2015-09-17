package catb.vanthu.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class DepartmentDocumentId implements Serializable {
	
	@ManyToOne
	private Document document;
	
	@ManyToOne
	private Department department;

	public DepartmentDocumentId() {
		
	}

	public DepartmentDocumentId(Document document, Department department) {
		this.document = document;
		this.department = department;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		
		DepartmentDocumentId that = (DepartmentDocumentId) obj;
		if (document != null ? !document.equals(that.document) : that.document != null) return false;
		if (department != null ? !department.equals(that.department) : that.department != null) return false;
		
		return true;	
	}
	
	public int hashCode() {
		int result;
		result = document != null ? document.hashCode() : 0;
		result = 31 * result + (department != null ? department.hashCode() : 0);
		
		return result;
	}
}
