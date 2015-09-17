package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.DocumentTypeCatalogBO;
import com.catb.dao.DocumentTypeCatalogDAO;
import com.catb.model.DocumentTypeCatalog;

@Service
public class DocumentTypeCatalogBOImpl implements DocumentTypeCatalogBO {
	
	@Autowired
	private DocumentTypeCatalogDAO documentTypeCatalogDAO;
	
	@Transactional
	public void addDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog) {
		documentTypeCatalogDAO.addDocumentTypeCatalog(documentTypeCatalog);
	}
	
	@Transactional
	public List<DocumentTypeCatalog> getDocumentTypeCatalogs() {
		return documentTypeCatalogDAO.getDocumentTypeCatalogs();
	}
	
	@Transactional
	public DocumentTypeCatalog getDocumentTypeCatalogById(Integer id) {
		return documentTypeCatalogDAO.getDocumentTypeCatalogById(id);
	}
	
	@Transactional
	public void updateDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog) {
		documentTypeCatalogDAO.updateDocumentTypeCatalog(documentTypeCatalog);
	}
	
	@Transactional
	public void deleteDocumentTypeCatalogs(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				documentTypeCatalogDAO.deleteDocumentTypeCatalog(id);
			}
		}
	}
}
