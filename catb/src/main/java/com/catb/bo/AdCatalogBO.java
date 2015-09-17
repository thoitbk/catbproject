package com.catb.bo;

import java.util.List;

import com.catb.model.AdCatalog;

public interface AdCatalogBO {
	
	public List<AdCatalog> getAdCatalogs();
	public void addAdCatalog(AdCatalog adCatalog);
	public void updateAdCatalog(AdCatalog adCatalog);
	public AdCatalog getAdCatalogById(Integer id);
	public void deleteAdCatalogs(Integer[] ids);
	public List<AdCatalog> getDisplayedAdCatalogs(Integer adNum);
}
