package catb.vanthu.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.DocumentFileDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.DocumentFile;

@Repository
public class DocumentFileDAOImpl implements DocumentFileDAO {
	
	static Logger logger = Logger.getLogger(DocumentFileDAOImpl.class.getName());
	
	public DocumentFile getDocumentFileById(Integer fileId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			DocumentFile documentFile = (DocumentFile) session.get(DocumentFile.class, fileId);
			
			session.getTransaction().commit();
			
			return documentFile;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
