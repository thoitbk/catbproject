package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.AdCatalogBO;
import com.catb.dao.AdCatalogDAO;
import com.catb.model.AdCatalog;

@Service
public class AdCatalogBOImpl implements AdCatalogBO {
	
	@Autowired
	private AdCatalogDAO adCatalogDAO;
	
	@Transactional
	public List<AdCatalog> getAdCatalogs() {
		return adCatalogDAO.getAdCatalogs();
	}
	
	@Transactional
	public void addAdCatalog(AdCatalog adCatalog) {
		adCatalogDAO.addAdCatalog(adCatalog);
	}
	
	@Transactional
	public void updateAdCatalog(AdCatalog adCatalog) {
		AdCatalog a = adCatalogDAO.getAdCatalogById(adCatalog.getId());
		if (a != null) {
			a.setDisplay(adCatalog.getDisplay());
			a.setImage(adCatalog.getImage());
			a.setLink(adCatalog.getLink());
			a.setOpenBlank(adCatalog.getOpenBlank());
			a.setSqNumber(adCatalog.getSqNumber());
			a.setTitle(adCatalog.getTitle());
			
			adCatalogDAO.updateAdCatalog(a);
		}
	}
	
	@Transactional
	public AdCatalog getAdCatalogById(Integer id) {
		return adCatalogDAO.getAdCatalogById(id);
	}
	
	@Transactional
	public void deleteAdCatalogs(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				adCatalogDAO.deleteAdCatalog(id);
			}
		}
	}
	
	@Transactional
	public List<AdCatalog> getDisplayedAdCatalogs(Integer adNum) {
		return adCatalogDAO.getDisplayedAdCatalogs(adNum);
	}
}
