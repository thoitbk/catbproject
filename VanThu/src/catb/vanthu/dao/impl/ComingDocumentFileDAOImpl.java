package catb.vanthu.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.ComingDocumentFileDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.ComingDocumentFile;

@Repository
public class ComingDocumentFileDAOImpl implements ComingDocumentFileDAO {
	
	static Logger logger = Logger.getLogger(ComingDocumentFileDAOImpl.class.getCanonicalName());
	
	@Override
	public ComingDocumentFile getComingDocumentFileById(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			ComingDocumentFile documentFile = (ComingDocumentFile) session.get(ComingDocumentFile.class, id);
			
			session.getTransaction().commit();
			
			return documentFile;
		} catch (Exception ex) {
			logger.error("Exception: ", ex);
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
