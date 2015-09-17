package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.NewsCatalogBO;
import com.catb.dao.NewsCatalogDAO;
import com.catb.model.NewsCatalog;

@Service
public class NewsCatalogBOImpl implements NewsCatalogBO {
	
	@Autowired
	private NewsCatalogDAO newsCatalogDAO;
	
	@Transactional
	public List<NewsCatalog> getNewsCatalog(String displayLocation, Integer parent) {
		return newsCatalogDAO.getNewsCatalog(displayLocation, parent);
	}
	
	@Transactional
	public void addNewsCatalog(NewsCatalog newsCatalog) {
		newsCatalogDAO.addNewsCatalog(newsCatalog);
	}
	
	@Transactional
	public NewsCatalog getNewsCatalogById(Integer newsCatalogId) {
		return newsCatalogDAO.getNewsCatalogById(newsCatalogId);
	}
	
	@Transactional
	public void updateNewsCatalog(NewsCatalog newsCatalog) {
		if (newsCatalog.getId() != null) {
			NewsCatalog c = newsCatalogDAO.getNewsCatalogById(newsCatalog.getId());
			if (c != null) {
				c.setName(newsCatalog.getName());
				c.setUrl(newsCatalog.getUrl());
				c.setSqNumber(newsCatalog.getSqNumber());
				c.setDisplay(newsCatalog.getDisplay());
				c.setSpecialSite(newsCatalog.getSpecialSite());
				c.setDisplayLocation(newsCatalog.getDisplayLocation());
				c.setParentId(newsCatalog.getParentId());
				
				NewsCatalog t = newsCatalogDAO.getNewsCatalogById(newsCatalog.getParentId());
				if (t != null && t.getChildLevel() != null) {
					Integer newLevel = t.getChildLevel() + 1;
					c.setChildLevel(newLevel);
				}
				
				newsCatalogDAO.updateNewsCatalog(c);
			}
		}
	}
	
	@Transactional
	public void deleteNewsCatalogs(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				newsCatalogDAO.deleteNewsCatalog(id);
			}
		}
	}
	
	@Transactional
	public List<NewsCatalog> getNewsCatalogs(String displayLocation,
			Integer parentId, Integer childLevel, Boolean display) {
		return newsCatalogDAO.getNewsCatalogs(displayLocation, parentId, childLevel, display);
	}
	
	@Transactional
	public List<NewsCatalog> getNewsCatalogs(String displayLocation,
			Integer parentId, Integer childLevel, Boolean display, Integer size) {
		return newsCatalogDAO.getNewsCatalogs(displayLocation, parentId, childLevel, display, size);
	}
	
	@Transactional
	public NewsCatalog getNewsCatalogByUrl(String newsCatalogUrl) {
		return newsCatalogDAO.getNewsCatalogByUrl(newsCatalogUrl);
	}
}
