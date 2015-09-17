package com.catb.bo;

import java.util.List;

import com.catb.model.NewsCatalog;

public interface NewsCatalogBO {
	
	public List<NewsCatalog> getNewsCatalog(String displayLocation, Integer parent);
	public void addNewsCatalog(NewsCatalog newsCatalog);
	public NewsCatalog getNewsCatalogById(Integer newsCatalogId);
	public void updateNewsCatalog(NewsCatalog newsCatalog);
	public void deleteNewsCatalogs(Integer[] ids);
	public List<NewsCatalog> getNewsCatalogs(String displayLocation, Integer parentId, Integer childLevel, Boolean display);
	public List<NewsCatalog> getNewsCatalogs(String displayLocation, Integer parentId, Integer childLevel, Boolean display, Integer size);
	public NewsCatalog getNewsCatalogByUrl(String newsCatalogUrl);
}
