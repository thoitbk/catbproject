package com.catb.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.DocumentBO;
import com.catb.dao.DepartmentDAO;
import com.catb.dao.DocumentDAO;
import com.catb.dao.DocumentTypeCatalogDAO;
import com.catb.model.Department;
import com.catb.model.Document;
import com.catb.model.DocumentFile;
import com.catb.model.DocumentTypeCatalog;

@Service
public class DocumentBOImpl implements DocumentBO {
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Autowired
	private DocumentTypeCatalogDAO documentTypeCatalogDAO;
	
	@Transactional
	public List<Document> getDocuments() {
		return documentDAO.getDocuments();
	}
	
	@Transactional
	public void addDocument(Document document, Integer departmentId,
			Integer documentTypeId, List<DocumentFile> files) {
		Department department = null;
		if (departmentId != null) {
			department = departmentDAO.getDepartmentById(departmentId);
		}
		document.setDepartment(department);
		DocumentTypeCatalog documentTypeCatalog = null;
		if (documentTypeId != null) {
			documentTypeCatalog = documentTypeCatalogDAO.getDocumentTypeCatalogById(documentTypeId);
		}
		document.setDocumentTypeCatalog(documentTypeCatalog);
		
		documentDAO.addDocument(document);
		
		if (files != null && files.size() > 0) {
			for (DocumentFile file : files) {
				file.setDocument(document);
				documentDAO.addDocumentFile(file);
			}
		} 
	}
	
	@Transactional
	public Document fetchDocumentById(Integer id) {
		Document document = documentDAO.getDocumentById(id);
		if (document != null) {
			Hibernate.initialize(document.getDepartment());
			Hibernate.initialize(document.getDocumentTypeCatalog());
			Hibernate.initialize(document.getDocumentFiles());
		}
		
		return document;
	}
	
	@Transactional
	public void updateDocument(Document document, Integer departmentId,
			Integer documentTypeId, List<DocumentFile> files) {
		Document d = documentDAO.getDocumentById(document.getId());
		if (d != null) {
			d.setCode(document.getCode());
			d.setContent(document.getContent());
			d.setDescription(document.getDescription());
			d.setLeader(document.getLeader());
			d.setPublishedDate(document.getPublishedDate());
			d.setSqNumber(document.getSqNumber());
			d.setSummary(document.getSummary());
			d.setValidDate(document.getValidDate());
			Department department = null;
			if (departmentId != null && departmentId >= 0) {
				department = departmentDAO.getDepartmentById(departmentId);
			}
			d.setDepartment(department);
			DocumentTypeCatalog documentTypeCatalog = null;
			if (documentTypeId != null && documentTypeId >= 0) {
				documentTypeCatalog = documentTypeCatalogDAO.getDocumentTypeCatalogById(documentTypeId);
			}
			d.setDocumentTypeCatalog(documentTypeCatalog);
			
			if (files != null && files.size() > 0) {
				d.getDocumentFiles().clear();
				d.getDocumentFiles().addAll(files);
				for (DocumentFile file : files) {
					file.setDocument(d);
					documentDAO.addDocumentFile(file);
				}
			}
			
			documentDAO.updateDocument(d);
		}
	}
	
	@Transactional
	public void deleteDocuments(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				documentDAO.deleteDocument(id);
			}
		}
	}
	
	@Transactional
	public DocumentFile getDocumentFileById(Integer id) {
		return documentDAO.getDocumentFileById(id);
	}
	
	@Transactional
	public List<Document> listDocuments() {
		return documentDAO.listDocuments();
	}
}
