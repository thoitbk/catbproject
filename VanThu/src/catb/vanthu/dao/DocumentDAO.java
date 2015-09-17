package catb.vanthu.dao;

import java.util.Date;
import java.util.List;

import catb.vanthu.model.Document;
import catb.vanthu.valueobject.ComplexSearchDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchInboundDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchOutboundDocumentsVO;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;

public interface DocumentDAO {
	
	public void saveDocument(Document document, Integer documentTypeId, List<Integer> senderIds, List<Integer> receiverIds);
	public Document getDocumentBySign(String sign);
	public List<Document> getDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, int page, int pageSize);
	public Integer countDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO);
	public Document getDocumentById(Integer documentId);
	public List<Document> getDocuments(ComplexSearchDocumentsVO complexSearchDocumentsVO, int page, int pageSize);
	public Integer countDocuments(ComplexSearchDocumentsVO complexSearchDocumentsVO);
	public List<Document> getOutboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId, int page, int pageSize);
	public Integer countOutboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId);
	public Boolean checkUserHavePermissionToOutboundFile(Integer departmentId, Integer fileId);
	public Boolean checkUserHavePermissionToOutboundDocument(Integer departmentId, Integer documentId);
	public List<Document> getOutboundDocuments(ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO, Integer departmentId, int page, int pageSize);
	public Integer countOutboundDocuments(ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO, Integer departmentId);
	public List<Document> getInboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId, int page, int pageSize);
	public Integer countInboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId);
	public Boolean checkUserHavePermissionToInboundFile(Integer departmentId, Integer fileId);
	public Boolean checkUserHavePermissionToInboundDocument(Integer departmentId, Integer documentId);
	public void updateReadStatusOfDocument(Integer departmentId, Integer documentId, Date receiveTime, Boolean isRead);
	public List<Document> getInboundDocuments(ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO, Integer departmentId, int page, int pageSize);
	public Integer countInboundDocuments(ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO, Integer departmentId);
	public List<Document> getUnreadDocuments(Integer departmentId);
	public void updateSendStatusOfDocument(Integer documentId, Boolean isMailSent);
	public Boolean checkSignExistInYear(String sign, Integer year);
	public Boolean checkNumberExistInYear(Integer number, Integer year);
	public Boolean checkSignExistInYear(String sign, Integer year, String currentSign);
	public Boolean checkNumberExistInYear(Integer number, Integer year, Integer currentNumber);
	public void deleteDocument(Integer documentId);
	public void updateDocument(Document document, Integer documentTypeId, List<Integer> senderIds, List<Integer> receiverIds);
	public List<Document> getDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO);
	public List<Document> getDocuments(ComplexSearchDocumentsVO complexSearchDocumentsVO);
	public List<Document> getOutboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentsVO, Integer departmentId);
	public List<Document> getOutboundDocuments(ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO, Integer departmentId);
	public List<Document> getInboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentsVO, Integer departmentId);
	public List<Document> getInboundDocuments(ComplexSearchInboundDocumentsVO complexSearchInboundDocumentVO, Integer departmentId);
}
