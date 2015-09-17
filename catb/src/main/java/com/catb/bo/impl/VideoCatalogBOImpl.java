package com.catb.bo.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.VideoCatalogBO;
import com.catb.dao.VideoCatalogDAO;
import com.catb.dao.VideoDAO;
import com.catb.model.Video;
import com.catb.model.VideoCatalog;
import com.catb.web.viewmodel.VideoGallery;

@Service
public class VideoCatalogBOImpl implements VideoCatalogBO {
	
	@Autowired
	private VideoCatalogDAO videoCatalogDAO;
	
	@Autowired
	private VideoDAO videoDAO;
	
	@Transactional
	public void addVideoCatalog(VideoCatalog videoCatalog) {
		videoCatalogDAO.addVideoCatalog(videoCatalog);
	}
	
	@Transactional
	public List<VideoCatalog> getVideoCatalogs() {
		return videoCatalogDAO.getVideoCatalogs();
	}
	
	@Transactional
	public VideoCatalog getVideoCatalogById(Integer id) {
		return videoCatalogDAO.getVideoCatalogById(id);
	}
	
	@Transactional
	public void updateVideoCatalog(VideoCatalog videoCatalog) {
		videoCatalogDAO.updateVideoCatalog(videoCatalog);
	}
	
	@Transactional
	public void deleteVideoCatalogs(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				videoCatalogDAO.deleteVideoCatalog(id);
			}
		}
	}
	
	@Transactional
	public List<VideoCatalog> fetchVideoCatalogs() {
		List<VideoCatalog> videoCatalogs = videoCatalogDAO.getVideoCatalogs();
		if (videoCatalogs != null && videoCatalogs.size() > 0) {
			for (VideoCatalog videoCatalog : videoCatalogs) {
				Hibernate.initialize(videoCatalog.getVideos());
			}
		}
		
		return videoCatalogs;
	}
	
	@Transactional
	public List<VideoGallery> fetchVideoCatalogsHavingVideo() {
		List<VideoCatalog> videoCatalogs = videoCatalogDAO.getVideoCatalogs();
		List<VideoGallery> videoGalleries = new LinkedList<VideoGallery>();
		if (videoCatalogs != null && videoCatalogs.size() > 0) {
			for (VideoCatalog videoCatalog : videoCatalogs) {
				List<Video> videos = videoDAO.getVideosByCatalogId(videoCatalog.getId());
				if (videos != null && videos.size() > 0) {
					videoGalleries.add(new VideoGallery(videoCatalog, videos));
				}
			}
		}
		
		return videoGalleries;
	}
}
