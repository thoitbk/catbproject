package catb.vanthu.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.DocumentNumberTrackerBO;
import catb.vanthu.dao.DocumentNumberTrackerDAO;

@Service
public class DocumentNumberTrackerBOImpl implements DocumentNumberTrackerBO {
	
	@Autowired
	private DocumentNumberTrackerDAO documentNumberTrackerDAO;
	
	@Override
	public Integer getDocumentNumber() {
		return documentNumberTrackerDAO.getDocumentNumber(0);
	}

	@Override
	public void updateDocumentNumber(Integer n) {
		documentNumberTrackerDAO.updateDocumentNumber(n, 0);
	}

	@Override
	public Integer getComingDocumentNumber() {
		return documentNumberTrackerDAO.getDocumentNumber(1);
	}

	@Override
	public void updateComingDocumentNumber(Integer n) {
		documentNumberTrackerDAO.updateDocumentNumber(n, 1);
	}
}
