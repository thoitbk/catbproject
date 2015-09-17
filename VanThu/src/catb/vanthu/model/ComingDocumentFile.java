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
@Table(name = "coming_document_file")
public class ComingDocumentFile implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "mime")
	private String mime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coming_document_id")
	private ComingDocument comingDocument;
	
	public ComingDocumentFile() {
		
	}

	public ComingDocumentFile(Integer id, String name, String path, String mime) {
		this.id = id;
		this.name = name;
		this.path = path;
		this.mime = mime;
	}

	public ComingDocumentFile(String name, String path, String mime,
			ComingDocument comingDocument) {
		this.name = name;
		this.path = path;
		this.mime = mime;
		this.comingDocument = comingDocument;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public ComingDocument getComingDocument() {
		return comingDocument;
	}

	public void setComingDocument(ComingDocument comingDocument) {
		this.comingDocument = comingDocument;
	}
}
