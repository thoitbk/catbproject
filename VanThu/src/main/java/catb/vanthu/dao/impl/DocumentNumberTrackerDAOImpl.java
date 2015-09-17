package catb.vanthu.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.DocumentNumberTrackerDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.DocumentNumberTracker;

@Repository
public class DocumentNumberTrackerDAOImpl implements DocumentNumberTrackerDAO {
	
	static Logger logger = Logger.getLogger(DocumentNumberTrackerDAOImpl.class.getCanonicalName());
	
	@Override
	public Integer getDocumentNumber(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			DocumentNumberTracker d = (DocumentNumberTracker) session.get(DocumentNumberTracker.class, id);
			Integer n = 0;
			
			if (d != null) {
				n = d.getDocumentNumber();
			}
			
			session.getTransaction().commit();
			
			return n;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			
			return 0;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateDocumentNumber(Integer n, Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "UPDATE DocumentNumberTracker SET documentNumber = :documentNumber WHERE id = :id";
			Query query = session.createQuery(queryStr);
			query.setParameter("documentNumber", n);
			query.setParameter("id", id);
			
			query.executeUpdate();
			
			session.getTransaction().commit();
			
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
