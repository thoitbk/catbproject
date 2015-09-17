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

@SuppressWarnings("serial")
@Entity
@Table(name = "video")
public class Video implements Serializable {
	
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
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "video_catalog_id")
	private VideoCatalog videoCatalog;

	public Video() {
		
	}
	
	public Video(Integer id, String caption, String file, Boolean display,
			Integer sqNumber, VideoCatalog videoCatalog) {
		this.id = id;
		this.caption = caption;
		this.file = file;
		this.display = display;
		this.sqNumber = sqNumber;
		this.videoCatalog = videoCatalog;
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

	public Integer getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(Integer sqNumber) {
		this.sqNumber = sqNumber;
	}

	public VideoCatalog getVideoCatalog() {
		return videoCatalog;
	}

	public void setVideoCatalog(VideoCatalog videoCatalog) {
		this.videoCatalog = videoCatalog;
	}
}
