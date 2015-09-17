package catb.vanthu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.DocumentTypeDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.DocumentType;

@Repository
public class DocumentTypeDAOImpl implements DocumentTypeDAO {
	
	static Logger logger = Logger.getLogger(DocumentTypeDAOImpl.class.getName());
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentType> getDocumentTypes() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			String queryStr = "FROM DocumentType";
			Query query = session.createQuery(queryStr);

			List<DocumentType> documentTypes = (List<DocumentType>) query.list();

			session.getTransaction().commit();

			return documentTypes;
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

	@SuppressWarnings("unchecked")
	@Override
	public DocumentType getDocumentTypeByName(String name) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			String queryStr = "FROM DocumentType WHERE typeName = :typeName";
			Query query = session.createQuery(queryStr);
			query.setParameter("typeName", name);

			List<DocumentType> documentTypes = (List<DocumentType>) query.list();

			session.getTransaction().commit();
			
			return documentTypes == null || documentTypes.size() == 0 ? null : documentTypes.get(0);
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

	@Override
	public void saveDocumentType(DocumentType documentType) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.save(documentType);
			
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

	@Override
	public void updateDocumentType(Integer id, String name) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			DocumentType documentType = (DocumentType) session.get(DocumentType.class, id);
			if (documentType != null) {
				documentType.setTypeName(name);
			}
			
			session.update(documentType);
			
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

	@Override
	public void deleteDocumentType(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			DocumentType documentType = (DocumentType) session.get(DocumentType.class, id);
			if (documentType != null) {
				session.delete(documentType);
			}
			
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
