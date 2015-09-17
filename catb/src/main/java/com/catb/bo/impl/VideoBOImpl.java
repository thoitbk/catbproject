package com.catb.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.VideoBO;
import com.catb.dao.VideoCatalogDAO;
import com.catb.dao.VideoDAO;
import com.catb.model.Video;
import com.catb.model.VideoCatalog;

@Service
public class VideoBOImpl implements VideoBO {
	
	@Autowired
	private VideoDAO videoDAO;
	
	@Autowired
	private VideoCatalogDAO videoCatalogDAO;
	
	@Transactional
	public List<Video> getVideos(Integer videoCatalogId, Integer page, Integer pageSize) {
		return videoDAO.getVideos(videoCatalogId, page, pageSize);
	}
	
	@Transactional
	public Long countVideos(Integer videoCatalogId) {
		return videoDAO.countVideos(videoCatalogId);
	}
	
	@Transactional
	public void addVideo(Video video, Integer videoCatalogId) {
		if (videoCatalogId != null && videoCatalogId >= 0) {
			VideoCatalog videoCatalog = videoCatalogDAO.getVideoCatalogById(videoCatalogId);
			if (videoCatalog != null) {
				video.setVideoCatalog(videoCatalog);
				videoCatalog.getVideos().add(video);
			}
		}
		
		videoDAO.addVideo(video);
	}
	
	@Transactional
	public Video fetchVideoById(Integer id) {
		Video video = null;
		if (id != null && id >= 0) {
			video = videoDAO.getVideoById(id);
			if (video != null) {
				Hibernate.initialize(video.getVideoCatalog());
			}
		}
		
		return video;
	}
	
	@Transactional
	public void updateVideo(Video video, Integer videoCatalogId) {
		Video v = videoDAO.getVideoById(video.getId());
		if (v != null) {
			v.setCaption(video.getCaption());
			v.setDisplay(video.getDisplay());
			v.setFile(video.getFile());
			v.setSqNumber(video.getSqNumber());
			if (videoCatalogId != null) {
				VideoCatalog videoCatalog = videoCatalogDAO.getVideoCatalogById(videoCatalogId);
				if (videoCatalog != null) {
					v.setVideoCatalog(videoCatalog);
					videoCatalog.getVideos().add(v);
				}
			}
			
			videoDAO.updateVideo(v);
		}
	}
	
	@Transactional
	public void deleteVideos(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				videoDAO.deleteVideo(id);
			}
		}
	}
	
	@Transactional
	public List<Video> getVideos(Integer amount) {
		return videoDAO.getVideos(amount);
	}
	
	@Transactional
	public List<Video> getVideosByCatalogId(Integer catalogId) {
		return videoDAO.getVideosByCatalogId(catalogId);
	}
	
	@Transactional
	public List<Video> getVideos(Integer page, Integer pageSize) {
		return videoDAO.getVideos(page, pageSize);
	}
	
	@Transactional
	public Long countVideos() {
		return videoDAO.countVideos();
	}
}
