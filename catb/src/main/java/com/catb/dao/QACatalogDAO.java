package com.catb.dao;

import java.util.List;

import com.catb.model.QACatalog;

public interface QACatalogDAO {
	
	public void addQACatalog(QACatalog qaCatalog);
	public List<QACatalog> getQACatalogs();
	public QACatalog getQACatalogById(Integer id);
	public void updateQACatalog(QACatalog qaCatalog);
	public void deleteQACatalog(Integer id);
}
