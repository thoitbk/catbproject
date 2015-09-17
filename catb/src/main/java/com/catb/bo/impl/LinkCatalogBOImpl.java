package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.LinkCatalogBO;
import com.catb.dao.LinkCatalogDAO;
import com.catb.model.LinkCatalog;

@Service
public class LinkCatalogBOImpl implements LinkCatalogBO {
	
	@Autowired
	private LinkCatalogDAO linkCatalogDAO;
	
	@Transactional
	public void addLinkCatalog(LinkCatalog linkCatalog) {
		linkCatalogDAO.addLinkCatalog(linkCatalog);
	}
	
	@Transactional
	public void updateLinkCatalog(LinkCatalog linkCatalog) {
		linkCatalogDAO.updateLinkCatalog(linkCatalog);
	}
	
	@Transactional
	public void deleteLinkCatalogs(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				linkCatalogDAO.deleteLinkCatalog(id);
			}
		}
	}
	
	@Transactional
	public LinkCatalog getLinkCatalogById(Integer id) {
		return linkCatalogDAO.getLinkCatalogById(id);
	}
	
	@Transactional
	public List<LinkCatalog> getLinkCatalogs() {
		return linkCatalogDAO.getLinkCatalogs();
	}
}
