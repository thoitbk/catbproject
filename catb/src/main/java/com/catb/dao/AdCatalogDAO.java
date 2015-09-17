package com.catb.dao;

import java.util.List;

import com.catb.model.AdCatalog;


public interface AdCatalogDAO {
	
	public List<AdCatalog> getAdCatalogs();
	public void addAdCatalog(AdCatalog adCatalog);
	public void updateAdCatalog(AdCatalog adCatalog);
	public AdCatalog getAdCatalogById(Integer id);
	public void deleteAdCatalog(Integer id);
	public List<AdCatalog> getDisplayedAdCatalogs(Integer adNum);
}
