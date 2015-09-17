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
@Table(name = "coming_document")
public class ComingDocument implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "number", unique = false, nullable = true)
	private Integer number;
	
	@Column(name = "sign", unique = false, nullable = false)
	private String sign;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private DocumentType documentType;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "publish_date", nullable = true)
	private Date publishDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "receive_date", nullable = true)
	private Date receiveDate;
	
	@Column(name = "confidential_level", nullable = true)
	private Integer confidentialLevel;
	
	@Column(name = "urgent_level", nullable = true)
	private Integer urgentLevel;
	
	@Column(name = "abstract", length = 10000)
	private String abs;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comingDocument")
	private Set<ComingDocumentFile> comingDocumentFiles = new HashSet<ComingDocumentFile>();
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "comingDocuments")
	private Set<Department> sentDepartments = new HashSet<Department>();
	
	public ComingDocument() {
		
	}

	public ComingDocument(Integer number, String sign,
			DocumentType documentType, Date publishDate, Date receiveDate,
			Integer confidentialLevel, Integer urgentLevel, String abs) {
		this.number = number;
		this.sign = sign;
		this.documentType = documentType;
		this.publishDate = publishDate;
		this.receiveDate = receiveDate;
		this.confidentialLevel = confidentialLevel;
		this.urgentLevel = urgentLevel;
		this.abs = abs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public Set<ComingDocumentFile> getComingDocumentFiles() {
		return comingDocumentFiles;
	}

	public void setComingDocumentFiles(Set<ComingDocumentFile> comingDocumentFiles) {
		this.comingDocumentFiles = comingDocumentFiles;
	}

	public Set<Department> getSentDepartments() {
		return sentDepartments;
	}

	public void setSentDepartments(Set<Department> sentDepartments) {
		this.sentDepartments = sentDepartments;
	}
	

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		
		ComingDocument that = (ComingDocument) obj;
		
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		
		return true;
	}
	
	public int hashCode() {
		return 31 * (id != null ? id.hashCode() : 0);
	}
}
