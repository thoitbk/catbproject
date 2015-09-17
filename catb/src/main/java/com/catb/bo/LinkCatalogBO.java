package com.catb.bo;

import java.util.List;

import com.catb.model.LinkCatalog;

public interface LinkCatalogBO {
	
	public void addLinkCatalog(LinkCatalog linkCatalog);
	public void updateLinkCatalog(LinkCatalog linkCatalog);
	public void deleteLinkCatalogs(Integer[] ids);
	public LinkCatalog getLinkCatalogById(Integer id);
	public List<LinkCatalog> getLinkCatalogs();
}
