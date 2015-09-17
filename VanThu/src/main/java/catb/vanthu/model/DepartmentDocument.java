package catb.vanthu.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "department_document_receive")
@AssociationOverrides({
		@AssociationOverride(name = "pk.department", joinColumns = @JoinColumn(name = "department_id")),
		@AssociationOverride(name = "pk.document", joinColumns = @JoinColumn(name = "document_id")) })
public class DepartmentDocument implements Serializable {
	
	@EmbeddedId
	private DepartmentDocumentId pk;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "send_time")
	private Date sendTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "receive_time")
	private Date receiveTime;
	
	@Column(name = "is_read")
	private Boolean isRead;

	public DepartmentDocument() {
		
	}
	
	public DepartmentDocument(DepartmentDocumentId pk, Date sendTime,
			Date receiveTime, Boolean isRead) {
		this.pk = pk;
		this.sendTime = sendTime;
		this.receiveTime = receiveTime;
		this.isRead = isRead;
	}

	public DepartmentDocument(Date sendTime, Date receiveTime, Boolean isRead) {
		this.sendTime = sendTime;
		this.receiveTime = receiveTime;
		this.isRead = isRead;
	}
	
	public DepartmentDocumentId getPk() {
		return pk;
	}

	public void setPk(DepartmentDocumentId pk) {
		this.pk = pk;
	}
	
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@Transient
	public Department getDepartment() {
		return getPk().getDepartment();
	}
	
	public void setDepartment(Department department) {
		getPk().setDepartment(department);
	}
	
	@Transient
	public Document getDocument() {
		return getPk().getDocument();
	}
	
	public void setDocument(Document document) {
		getPk().setDocument(document);
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		
		DepartmentDocument that = (DepartmentDocument) obj;
		
		if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;
		
		return true;
	}
	
	public int hashCode() {
		return 31 * (pk != null ? pk.hashCode() : 0);
	}
}
