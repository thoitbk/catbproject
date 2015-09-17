package catb.vanthu.bo;

import java.util.List;

import catb.vanthu.model.DocumentType;

public interface DocumentTypeBO {
	
	public List<DocumentType> getDocumentTypes();
	public DocumentType getDocumentTypeByName(String name);
	public void saveDocumentType(DocumentType documentType);
	public void updateDocumentType(Integer id, String name);
	public void deleteDocumentType(Integer id);
}
