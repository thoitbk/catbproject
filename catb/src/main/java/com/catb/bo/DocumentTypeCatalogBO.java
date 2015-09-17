package com.catb.bo;

import java.util.List;

import com.catb.model.DocumentTypeCatalog;

public interface DocumentTypeCatalogBO {
	
	public void addDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog);
	public List<DocumentTypeCatalog> getDocumentTypeCatalogs();
	public DocumentTypeCatalog getDocumentTypeCatalogById(Integer id);
	public void updateDocumentTypeCatalog(DocumentTypeCatalog documentTypeCatalog);
	public void deleteDocumentTypeCatalogs(Integer[] ids);
}
