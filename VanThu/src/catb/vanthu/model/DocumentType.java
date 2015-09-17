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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "document_type")
public class DocumentType implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "type_name", length = 300)
	private String typeName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
	private Set<Document> documents = new HashSet<Document>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
	private Set<ComingDocument> comingDocuments = new HashSet<ComingDocument>();
	
	public DocumentType() {
		
	}

	public DocumentType(String typeName) {
		this.typeName = typeName;
	}

	public DocumentType(String typeName, Set<Document> documents) {
		this.typeName = typeName;
		this.documents = documents;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Set<ComingDocument> getComingDocuments() {
		return comingDocuments;
	}

	public void setComingDocuments(Set<ComingDocument> comingDocuments) {
		this.comingDocuments = comingDocuments;
	}
}
