package catb.vanthu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "document")
public class Document implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "sign", unique = false, nullable = false)
	private String sign;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private DocumentType documentType;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "publish_date", nullable = true)
	private Date publishDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "send_date", nullable = true)
	private Date sendDate;
	
	@Column(name = "confidential_level", nullable = true)
	private Integer confidentialLevel;
	
	@Column(name = "urgent_level", nullable = true)
	private Integer urgentLevel;
	
	@Column(name = "signer", length = 100)
	private String signer;
	
	@Column(name = "abstract", length = 10000)
	private String abs;
	
	@Column(name = "is_mail_sent")
	private Boolean isMailSent;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
	private Set<DocumentFile> documentFiles = new HashSet<DocumentFile>();
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "documents")
	private Set<Department> departments = new HashSet<Department>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.document")
	private Set<DepartmentDocument> departmentDocuments = new HashSet<DepartmentDocument>();
	
	public Document() {
		
	}
	
	public Document(String sign, DocumentType documentType, Date publishDate,
			Date sendDate, Integer confidentialLevel, Integer urgentLevel,
			String signer, String abs, Boolean isMailSent) {
		this.sign = sign;
		this.documentType = documentType;
		this.publishDate = publishDate;
		this.sendDate = sendDate;
		this.confidentialLevel = confidentialLevel;
		this.urgentLevel = urgentLevel;
		this.signer = signer;
		this.abs = abs;
		this.isMailSent = isMailSent;
	}

	public Document(String sign, DocumentType documentType, Date publishDate,
			Date sendDate, Integer confidentialLevel, Integer urgentLevel,
			String signer, String abs, Set<DocumentFile> documentFiles,
			Set<Department> departments,
			Set<DepartmentDocument> departmentDocuments) {
		this.sign = sign;
		this.documentType = documentType;
		this.publishDate = publishDate;
		this.sendDate = sendDate;
		this.confidentialLevel = confidentialLevel;
		this.urgentLevel = urgentLevel;
		this.signer = signer;
		this.abs = abs;
		this.documentFiles = documentFiles;
		this.departments = departments;
		this.departmentDocuments = departmentDocuments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getConfidentialLevel() {
		return confidentialLevel;
	}

	public void setConfidentialLevel(Integer confidentialLevel) {
		this.confidentialLevel = confidentialLevel;
	}

	public Integer getUrgentLevel() {
		return urgentLevel;
	}

	public void setUrgentLevel(Integer urgentLevel) {
		this.urgentLevel = urgentLevel;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getAbs() {
		return abs;
	}

	public Boolean getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(Boolean isMailSent) {
		this.isMailSent = isMailSent;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}
	
	public Set<DocumentFile> getDocumentFiles() {
		return documentFiles;
	}

	public void setDocumentFiles(Set<DocumentFile> documentFiles) {
		this.documentFiles = documentFiles;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		
		Document that = (Document) obj;
		
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		
		return true;
	}
	
	public int hashCode() {
		return 31 * (id != null ? id.hashCode() : 0);
	}

	public Set<DepartmentDocument> getDepartmentDocuments() {
		return departmentDocuments;
	}

	public void setDepartmentDocuments(Set<DepartmentDocument> departmentDocuments) {
		this.departmentDocuments = departmentDocuments;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
}
