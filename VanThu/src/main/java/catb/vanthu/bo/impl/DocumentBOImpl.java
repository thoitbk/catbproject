package catb.vanthu.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.DocumentBO;
import catb.vanthu.dao.DocumentDAO;
import catb.vanthu.model.Document;
import catb.vanthu.valueobject.ComplexSearchDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchInboundDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchOutboundDocumentsVO;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;

@Service
public class DocumentBOImpl implements DocumentBO {
	
	@Autowired
	private DocumentDAO documentDAO;
	
	public void saveDocument(Document document, Integer documentTypeId,
			List<Integer> senderIds, List<Integer> receiverIds) {
		documentDAO.saveDocument(document, documentTypeId, senderIds, receiverIds);
	}

	public Document getDocumentBySign(String sign) {
		return documentDAO.getDocumentBySign(sign);
	}

	public List<Document> getDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, int page,
			int pageSize) {
		return documentDAO.getDocuments(simpleSearchDocumentVO, page, pageSize);
	}

	public Integer countDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO) {
		return documentDAO.countDocuments(simpleSearchDocumentVO);
	}

	public Document getDocumentById(Integer documentId) {
		return documentDAO.getDocumentById(documentId);
	}

	public List<Document> getDocuments(
			ComplexSearchDocumentsVO complexSearchDocumentsVO, int page,
			int pageSize) {
		return documentDAO.getDocuments(complexSearchDocumentsVO, page, pageSize);
	}

	public Integer countDocuments(ComplexSearchDocumentsVO complexSearchDocumentsVO) {
		return documentDAO.countDocuments(complexSearchDocumentsVO);
	}

	public List<Document> getOutboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO,
			Integer departmentId, int page, int pageSize) {
		return documentDAO.getOutboundDocuments(simpleSearchDocumentVO, departmentId, page, pageSize);
	}

	public Integer countOutboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId) {
		return documentDAO.countOutboundDocuments(simpleSearchDocumentVO, departmentId);
	}

	public Boolean checkUserHavePermissionToOutboundFile(Integer departmentId,
			Integer fileId) {
		return documentDAO.checkUserHavePermissionToOutboundFile(departmentId, fileId);
	}

	public Boolean checkUserHavePermissionToOutboundDocument(
			Integer departmentId, Integer documentId) {
		return documentDAO.checkUserHavePermissionToOutboundDocument(departmentId, documentId);
	}

	public List<Document> getOutboundDocuments(
			ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO,
			Integer departmentId, int page, int pageSize) {
		return documentDAO.getOutboundDocuments(complexSearchOutboundDocumentsVO, departmentId, page, pageSize);
	}

	public Integer countOutboundDocuments(
			ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO,
			Integer departmentId) {
		return documentDAO.countOutboundDocuments(complexSearchOutboundDocumentsVO, departmentId);
	}

	public List<Document> getInboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO,
			Integer departmentId, int page, int pageSize) {
		return documentDAO.getInboundDocuments(simpleSearchDocumentVO, departmentId, page, pageSize);
	}

	public Integer countInboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId) {
		return documentDAO.countInboundDocuments(simpleSearchDocumentVO, departmentId);
	}

	public Boolean checkUserHavePermissionToInboundFile(Integer departmentId,
			Integer fileId) {
		return documentDAO.checkUserHavePermissionToInboundFile(departmentId, fileId);
	}

	public Boolean checkUserHavePermissionToInboundDocument(
			Integer departmentId, Integer documentId) {
		return documentDAO.checkUserHavePermissionToInboundDocument(departmentId, documentId);
	}

	public void updateReadStatusOfDocument(Integer departmentId,
			Integer documentId, Date receiveTime, Boolean isRead) {
		documentDAO.updateReadStatusOfDocument(departmentId, documentId, receiveTime, isRead);
	}

	public List<Document> getInboundDocuments(
			ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO,
			Integer departmentId, int page, int pageSize) {
		return documentDAO.getInboundDocuments(complexSearchInboundDocumentsVO, departmentId, page, pageSize);
	}

	public Integer countInboundDocuments(
			ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO,
			Integer departmentId) {
		return documentDAO.countInboundDocuments(complexSearchInboundDocumentsVO, departmentId);
	}

	public List<Document> getUnreadDocuments(Integer departmentId) {
		return documentDAO.getUnreadDocuments(departmentId);
	}

	public void updateSendStatusOfDocument(Integer documentId,
			Boolean isMailSent) {
		documentDAO.updateSendStatusOfDocument(documentId, isMailSent);
	}

	public Boolean checkSignExistInYear(String sign, Integer year) {
		return documentDAO.checkSignExistInYear(sign, year);
	}

	public Boolean checkNumberExistInYear(Integer number, Integer year) {
		return documentDAO.checkNumberExistInYear(number, year);
	}

	public void deleteDocument(Integer documentId) {
		documentDAO.deleteDocument(documentId);
	}

	public Boolean checkSignExistInYear(String sign, Integer year,
			String currentSign) {
		return documentDAO.checkSignExistInYear(sign, year, currentSign);
	}

	public Boolean checkNumberExistInYear(Integer number, Integer year,
			Integer currentNumber) {
		return documentDAO.checkNumberExistInYear(number, year, currentNumber);
	}

	public void updateDocument(Document document, Integer documentTypeId,
			List<Integer> senderIds, List<Integer> receiverIds) {
		documentDAO.updateDocument(document, documentTypeId, senderIds, receiverIds);
	}

	public List<Document> getDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		return documentDAO.getDocuments(simpleSearchDocumentVO);
	}

	public List<Document> getDocuments(
			ComplexSearchDocumentsVO complexSearchDocumentsVO) {
		return documentDAO.getDocuments(complexSearchDocumentsVO);
	}

	public List<Document> getOutboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentsVO, Integer departmentId) {
		return documentDAO.getOutboundDocuments(simpleSearchDocumentsVO, departmentId);
	}

	public List<Document> getOutboundDocuments(
			ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO,
			Integer departmentId) {
		return documentDAO.getOutboundDocuments(complexSearchOutboundDocumentsVO, departmentId);
	}

	public List<Document> getInboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentsVO, Integer departmentId) {
		return documentDAO.getInboundDocuments(simpleSearchDocumentsVO, departmentId);
	}

	public List<Document> getInboundDocuments(
			ComplexSearchInboundDocumentsVO complexSearchInboundDocumentVO,
			Integer departmentId) {
		return documentDAO.getInboundDocuments(complexSearchInboundDocumentVO, departmentId);
	}
}
