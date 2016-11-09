package catb.vanthu.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.DocumentNumberTrackerBO;
import catb.vanthu.dao.DocumentNumberTrackerDAO;

@Service
public class DocumentNumberTrackerBOImpl implements DocumentNumberTrackerBO {
	
	@Autowired
	private DocumentNumberTrackerDAO documentNumberTrackerDAO;
	
	public Integer getDocumentNumber() {
		return documentNumberTrackerDAO.getDocumentNumber(0);
	}

	public void updateDocumentNumber(Integer n) {
		documentNumberTrackerDAO.updateDocumentNumber(n, 0);
	}

	public Integer getComingDocumentNumber() {
		return documentNumberTrackerDAO.getDocumentNumber(1);
	}

	public void updateComingDocumentNumber(Integer n) {
		documentNumberTrackerDAO.updateDocumentNumber(n, 1);
	}
}
