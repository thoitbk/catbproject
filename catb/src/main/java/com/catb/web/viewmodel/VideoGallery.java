package com.catb.web.viewmodel;

import java.util.List;

import com.catb.model.Video;
import com.catb.model.VideoCatalog;

public class VideoGallery {
	
	private VideoCatalog videoCatalog;
	private List<Video> videos;
	
	public VideoGallery() {
		
	}

	public VideoGallery(VideoCatalog videoCatalog, List<Video> videos) {
		this.videoCatalog = videoCatalog;
		this.videos = videos;
	}

	public VideoCatalog getVideoCatalog() {
		return videoCatalog;
	}

	public void setVideoCatalog(VideoCatalog videoCatalog) {
		this.videoCatalog = videoCatalog;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
}
