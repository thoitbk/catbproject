package com.catb.bo;

import java.util.List;

import com.catb.model.Video;

public interface VideoBO {
	
	public List<Video> getVideos(Integer videoCatalogId, Integer page, Integer pageSize);
	public Long countVideos(Integer videoCatalogId);
	public void addVideo(Video video, Integer videoCatalogId);
	public Video fetchVideoById(Integer id);
	public void updateVideo(Video video, Integer videoCatalogId);
	public void deleteVideos(Integer[] ids);
	public List<Video> getVideos(Integer amount);
	public List<Video> getVideosByCatalogId(Integer catalogId);
	public List<Video> getVideos(Integer page, Integer pageSize);
	public Long countVideos();
}
