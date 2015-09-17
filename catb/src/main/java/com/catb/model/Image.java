package com.catb.model;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "image")
public class Image implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "caption")
	private String caption;
	
	@Column(name = "file")
	private String file;
	
	@Column(name = "display")
	private Boolean display;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_catalog_id")
	private ImageCatalog imageCatalog;

	public Image() {
		
	}

	public Image(Integer id, String caption, String file, Boolean display,
			ImageCatalog imageCatalog) {
		this.id = id;
		this.caption = caption;
		this.file = file;
		this.display = display;
		this.imageCatalog = imageCatalog;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public ImageCatalog getImageCatalog() {
		return imageCatalog;
	}

	public void setImageCatalog(ImageCatalog imageCatalog) {
		this.imageCatalog = imageCatalog;
	}
}
