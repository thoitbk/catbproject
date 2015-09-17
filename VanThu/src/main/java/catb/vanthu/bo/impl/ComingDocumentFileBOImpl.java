package catb.vanthu.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.ComingDocumentFileBO;
import catb.vanthu.dao.ComingDocumentFileDAO;
import catb.vanthu.model.ComingDocumentFile;

@Service
public class ComingDocumentFileBOImpl implements ComingDocumentFileBO {
	
	@Autowired
	private ComingDocumentFileDAO comingDocumentFileDAO;
	
	@Override
	public ComingDocumentFile getComingDocumentFileById(Integer id) {
		return comingDocumentFileDAO.getComingDocumentFileById(id);
	}
}
