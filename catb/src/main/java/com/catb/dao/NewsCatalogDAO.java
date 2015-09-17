package com.catb.dao;

import java.util.List;

import com.catb.model.NewsCatalog;

public interface NewsCatalogDAO {
	
	public List<NewsCatalog> getNewsCatalog(String displayLocation, Integer parent);
	public void addNewsCatalog(NewsCatalog newsCatalog);
	public NewsCatalog getNewsCatalogById(Integer newsCatalogId);
	public void updateNewsCatalog(NewsCatalog newsCatalog);
	public void deleteNewsCatalog(Integer id);
	public List<NewsCatalog> getNewsCatalogs(String displayLocation, Integer parentId, Integer childLevel, Boolean display);
	public List<NewsCatalog> getNewsCatalogs(Boolean display, Boolean specialSite);
	public List<NewsCatalog> getNewsCatalogs(String displayLocation, Integer parentId, Integer childLevel, Boolean display, Integer size);
	public NewsCatalog getNewsCatalogByUrl(String newsCatalogUrl);
}
