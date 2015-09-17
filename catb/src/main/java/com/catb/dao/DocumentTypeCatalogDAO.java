package com.catb.dao;

import java.util.List;

import com.catb.model.DocumentTypeCatalog;

public interface DocumentTypeCatalogDAO {
	
	public void addDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog);
	public List<DocumentTypeCatalog> getDocumentTypeCatalogs();
	public DocumentTypeCatalog getDocumentTypeCatalogById(Integer id);
	public void updateDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog);
	public void deleteDocumentTypeCatalog(Integer id);
}
