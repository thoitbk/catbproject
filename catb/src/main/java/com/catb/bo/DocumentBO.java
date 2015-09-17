package com.catb.bo;

import java.util.List;

import com.catb.model.Document;
import com.catb.model.DocumentFile;


public interface DocumentBO {
	public List<Document> getDocuments();
	public void addDocument(
			Document document,
			Integer departmentId, Integer documentTypeId,
			List<DocumentFile> files);
	public Document fetchDocumentById(Integer id);
	public void updateDocument(
			Document document,
			Integer departmentId, Integer documentTypeId,
			List<DocumentFile> files);
	public void deleteDocuments(Integer[] ids);
	public DocumentFile getDocumentFileById(Integer id);
	public List<Document> listDocuments();
}
