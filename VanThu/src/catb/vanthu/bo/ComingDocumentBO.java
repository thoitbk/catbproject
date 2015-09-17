package catb.vanthu.bo;

import java.util.List;

import catb.vanthu.model.ComingDocument;
import catb.vanthu.valueobject.ComplexSearchComingDocumentsVO;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;

public interface ComingDocumentBO {
	
	public Boolean checkSignExistInYear(String sign, Integer year);
	public void saveComingDocument(ComingDocument comingDocument, Integer documentTypeId, List<Integer> senderIds);
	public List<ComingDocument> getComingDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, Integer page, Integer pageSize);
	public Integer countComingDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO);
	public ComingDocument getComingDocumentById(Integer id);
	public void deleteComingDocument(Integer id);
	public Boolean checkSignExistInYear(String sign, Integer year, String currentSign);
	public void updateComingDocument(ComingDocument comingDocument, Integer documentTypeId, List<Integer> senders);
	public List<ComingDocument> getComingDocuments(ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO, Integer page, Integer pageSize);
	public Integer countComingDocuments(ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO);
	public List<ComingDocument> getComingDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO);
	public List<ComingDocument> getComingDocuments(ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO);
}
