package catb.vanthu.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.DocumentTypeBO;
import catb.vanthu.dao.DocumentTypeDAO;
import catb.vanthu.model.DocumentType;

@Service
public class DocumentTypeBOImpl implements DocumentTypeBO {
	
	@Autowired
	private DocumentTypeDAO documentTypeDAO;
	
	@Override
	public List<DocumentType> getDocumentTypes() {
		return documentTypeDAO.getDocumentTypes();
	}

	@Override
	public DocumentType getDocumentTypeByName(String name) {
		return documentTypeDAO.getDocumentTypeByName(name);
	}

	@Override
	public void saveDocumentType(DocumentType documentType) {
		documentTypeDAO.saveDocumentType(documentType);
	}

	@Override
	public void updateDocumentType(Integer id, String name) {
		documentTypeDAO.updateDocumentType(id, name);
	}

	@Override
	public void deleteDocumentType(Integer id) {
		documentTypeDAO.deleteDocumentType(id);
	}
}
