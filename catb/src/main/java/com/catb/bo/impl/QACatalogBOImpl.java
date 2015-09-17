package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.QACatalogBO;
import com.catb.dao.QACatalogDAO;
import com.catb.model.QACatalog;

@Service
public class QACatalogBOImpl implements QACatalogBO {
	
	@Autowired
	private QACatalogDAO qaCatalogDAO;
	
	@Transactional
	public void addQACatalog(QACatalog qaCatalog) {
		qaCatalogDAO.addQACatalog(qaCatalog);
	}
	
	@Transactional
	public List<QACatalog> getQACatalogs() {
		return qaCatalogDAO.getQACatalogs();
	}
	
	@Transactional
	public QACatalog getQACatalogById(Integer id) {
		return qaCatalogDAO.getQACatalogById(id);
	}
	
	@Transactional
	public void updateQACatalog(QACatalog qaCatalog) {
		qaCatalogDAO.updateQACatalog(qaCatalog);
	}
	
	@Transactional
	public void deleteQACatalogs(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				qaCatalogDAO.deleteQACatalog(id);
			}
		}
	}
}
