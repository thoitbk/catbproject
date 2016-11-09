package catb.vanthu.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.ComingDocumentBO;
import catb.vanthu.dao.ComingDocumentDAO;
import catb.vanthu.model.ComingDocument;
import catb.vanthu.valueobject.ComplexSearchComingDocumentsVO;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;

@Service
public class ComingDocumentBOImpl implements ComingDocumentBO {
	
	@Autowired
	private ComingDocumentDAO comingDocumentDAO;
	
	public Boolean checkSignExistInYear(String sign, Integer year) {
		return comingDocumentDAO.checkSignExistInYear(sign, year);
	}

	public void saveComingDocument(ComingDocument comingDocument,
			Integer documentTypeId, List<Integer> senderIds) {
		comingDocumentDAO.saveComingDocument(comingDocument, documentTypeId, senderIds);
	}

	public List<ComingDocument> getComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, Integer page,
			Integer pageSize) {
		return comingDocumentDAO.getComingDocuments(simpleSearchDocumentVO, page, pageSize);
	}

	public Integer countComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		return comingDocumentDAO.countComingDocuments(simpleSearchDocumentVO);
	}

	public ComingDocument getComingDocumentById(Integer id) {
		return comingDocumentDAO.getComingDocumentById(id);
	}

	public void deleteComingDocument(Integer id) {
		comingDocumentDAO.deleteComingDocument(id);
	}

	public Boolean checkSignExistInYear(String sign, Integer year,
			String currentSign) {
		return comingDocumentDAO.checkSignExistInYear(sign, year, currentSign);
	}

	public void updateComingDocument(ComingDocument comingDocument,
			Integer documentTypeId, List<Integer> senders) {
		comingDocumentDAO.updateComingDocument(comingDocument, documentTypeId, senders);
	}

	public List<ComingDocument> getComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO,
			Integer page, Integer pageSize) {
		return comingDocumentDAO.getComingDocuments(complexSearchComingDocumentsVO, page, pageSize);
	}

	public Integer countComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO) {
		return comingDocumentDAO.countComingDocuments(complexSearchComingDocumentsVO);
	}

	public List<ComingDocument> getComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		return comingDocumentDAO.getComingDocuments(simpleSearchDocumentVO);
	}

	public List<ComingDocument> getComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO) {
		return comingDocumentDAO.getComingDocuments(complexSearchComingDocumentsVO);
	}
}
