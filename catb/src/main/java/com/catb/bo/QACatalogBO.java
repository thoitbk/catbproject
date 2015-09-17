package com.catb.bo;

import java.util.List;

import com.catb.model.QACatalog;

public interface QACatalogBO {
	
	public void addQACatalog(QACatalog qaCatalog);
	public List<QACatalog> getQACatalogs();
	public QACatalog getQACatalogById(Integer id);
	public void updateQACatalog(QACatalog qaCatalog);
	public void deleteQACatalogs(Integer[] ids);
}
