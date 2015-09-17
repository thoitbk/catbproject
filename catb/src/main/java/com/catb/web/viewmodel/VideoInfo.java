package com.catb.web.viewmodel;

public class VideoInfo {
	
	private Integer id;
	private String caption;
	private String file;
	
	public VideoInfo() {
		
	}

	public VideoInfo(Integer id, String caption, String file) {
		this.id = id;
		this.caption = caption;
		this.file = file;
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
}
