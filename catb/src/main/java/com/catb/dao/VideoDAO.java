package com.catb.dao;

import java.util.List;

import com.catb.model.Video;

public interface VideoDAO {
	
	public List<Video> getVideos(Integer videoCatalogId, Integer page, Integer pageSize);
	public Long countVideos(Integer videoCatalogId);
	public void addVideo(Video video);
	public Video getVideoById(Integer id);
	public void updateVideo(Video video);
	public void deleteVideo(Integer id);
	public List<Video> getVideos(Integer amount);
	public List<Video> getVideosByCatalogId(Integer catalogId);
	public List<Video> getVideos(Integer page, Integer pageSize);
	public Long countVideos();
}
