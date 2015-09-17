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
	
	@Override
	public Boolean checkSignExistInYear(String sign, Integer year) {
		return comingDocumentDAO.checkSignExistInYear(sign, year);
	}

	@Override
	public void saveComingDocument(ComingDocument comingDocument,
			Integer documentTypeId, List<Integer> senderIds) {
		comingDocumentDAO.saveComingDocument(comingDocument, documentTypeId, senderIds);
	}

	@Override
	public List<ComingDocument> getComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, Integer page,
			Integer pageSize) {
		return comingDocumentDAO.getComingDocuments(simpleSearchDocumentVO, page, pageSize);
	}

	@Override
	public Integer countComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		return comingDocumentDAO.countComingDocuments(simpleSearchDocumentVO);
	}

	@Override
	public ComingDocument getComingDocumentById(Integer id) {
		return comingDocumentDAO.getComingDocumentById(id);
	}

	@Override
	public void deleteComingDocument(Integer id) {
		comingDocumentDAO.deleteComingDocument(id);
	}

	@Override
	public Boolean checkSignExistInYear(String sign, Integer year,
			String currentSign) {
		return comingDocumentDAO.checkSignExistInYear(sign, year, currentSign);
	}

	@Override
	public void updateComingDocument(ComingDocument comingDocument,
			Integer documentTypeId, List<Integer> senders) {
		comingDocumentDAO.updateComingDocument(comingDocument, documentTypeId, senders);
	}

	@Override
	public List<ComingDocument> getComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO,
			Integer page, Integer pageSize) {
		return comingDocumentDAO.getComingDocuments(complexSearchComingDocumentsVO, page, pageSize);
	}

	@Override
	public Integer countComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO) {
		return comingDocumentDAO.countComingDocuments(complexSearchComingDocumentsVO);
	}

	@Override
	public List<ComingDocument> getComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		return comingDocumentDAO.getComingDocuments(simpleSearchDocumentVO);
	}

	@Override
	public List<ComingDocument> getComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO) {
		return comingDocumentDAO.getComingDocuments(complexSearchComingDocumentsVO);
	}
}
