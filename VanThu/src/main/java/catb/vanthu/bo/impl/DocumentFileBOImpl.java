package catb.vanthu.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.DocumentFileBO;
import catb.vanthu.dao.DocumentFileDAO;
import catb.vanthu.model.DocumentFile;

@Service
public class DocumentFileBOImpl implements DocumentFileBO {
	
	@Autowired
	private DocumentFileDAO documentFileDAO;
	
	public DocumentFile getDocumentFileById(Integer fileId) {
		return documentFileDAO.getDocumentFileById(fileId);
	}
}
