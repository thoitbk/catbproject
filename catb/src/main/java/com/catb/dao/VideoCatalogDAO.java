package com.catb.dao;

import java.util.List;

import com.catb.model.VideoCatalog;

public interface VideoCatalogDAO {
	
	public void addVideoCatalog(VideoCatalog videoCatalog);
	public List<VideoCatalog> getVideoCatalogs();
	public VideoCatalog getVideoCatalogById(Integer id);
	public void updateVideoCatalog(VideoCatalog videoCatalog);
	public void deleteVideoCatalog(Integer id);
}
