package com.catb.dao;

import java.util.List;

import com.catb.model.LinkCatalog;

public interface LinkCatalogDAO {
	
	public void addLinkCatalog(LinkCatalog linkCatalog);
	public void updateLinkCatalog(LinkCatalog linkCatalog);
	public void deleteLinkCatalog(Integer id);
	public LinkCatalog getLinkCatalogById(Integer id);
	public List<LinkCatalog> getLinkCatalogs();
}
