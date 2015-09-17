package com.catb.dao;

import java.util.List;

import com.catb.model.Document;
import com.catb.model.DocumentFile;

public interface DocumentDAO {
	
	public List<Document> getDocuments();
	public void addDocument(Document document);
	public void addDocumentFile(DocumentFile documentFile);
	public void updateDocument(Document document);
	public void updateDocumentFile(DocumentFile documentFile);
	public Document getDocumentById(Integer id);
	public void deleteDocument(Integer id);
	public DocumentFile getDocumentFileById(Integer id);
	public List<Document> listDocuments();
}
