package catb.vanthu.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.ComingDocumentDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.ComingDocument;
import catb.vanthu.model.ComingDocumentFile;
import catb.vanthu.model.Department;
import catb.vanthu.model.Document;
import catb.vanthu.model.DocumentType;
import catb.vanthu.valueobject.ComplexSearchComingDocumentsVO;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;

@Repository
public class ComingDocumentDAOImpl implements ComingDocumentDAO {
	
	static Logger logger = Logger.getLogger(ComingDocumentDAOImpl.class.getCanonicalName());
	
	@SuppressWarnings("unchecked")
	public Boolean checkSignExistInYear(String sign, Integer year) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM ComingDocument d WHERE d.sign = :sign AND YEAR(receiveDate) = :year";
			Query query = session.createQuery(queryStr);
			query.setParameter("sign", sign);
			query.setParameter("year", year);
			
			List<Document> documents = (List<Document>) query.list();
			
			session.getTransaction().commit();
			
			return documents != null && documents.size() > 0;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return true;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void saveComingDocument(ComingDocument comingDocument,
			Integer documentTypeId, List<Integer> senderIds) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if (documentTypeId != null) {
				DocumentType documentType = (DocumentType) session.get(DocumentType.class, documentTypeId);
				comingDocument.setDocumentType(documentType);
			}
			
			if (senderIds != null && senderIds.size() > 0) {
				String queryStr = "FROM Department WHERE id IN :senderIds";
				Query query = session.createQuery(queryStr);
				query.setParameterList("senderIds", senderIds);
				List<Department> senders = (List<Department>) query.list();
				Set<Department> departments = new HashSet<Department>(senders);
				comingDocument.setSentDepartments(departments);
				for (Department d : departments) {
					d.getComingDocuments().add(comingDocument);
				}
			}
			
			session.save(comingDocument);
			
			Set<ComingDocumentFile> comingDocumentFiles = comingDocument.getComingDocumentFiles();
			
			for (ComingDocumentFile file : comingDocumentFiles) {
				session.save(file);
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

	@SuppressWarnings("unchecked")
	public List<ComingDocument> getComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, Integer page,
			Integer pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchComingDocumentsCriteria(session, simpleSearchDocumentVO);
			
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("receiveDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<ComingDocument> documents = (List<ComingDocument>) criteria.list();
			
			for (ComingDocument d : documents) {
				Hibernate.initialize(d.getComingDocumentFiles());
			}
			
			session.getTransaction().commit();
			
			return documents;
		} catch (Exception ex) {
			ex.printStackTrace();
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
	
	@SuppressWarnings("deprecation")
	private Criteria buildSimpleSearchComingDocumentsCriteria(Session session, SimpleSearchDocumentVO simpleSearchDocumentVO) {
		Criteria criteria = session.createCriteria(ComingDocument.class, "document");
		String documentInfo = simpleSearchDocumentVO.getDocumentInfo();
		if (documentInfo != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.like("sign", "%" + documentInfo + "%"))
					.add(Restrictions.like("abs", "%" + documentInfo + "%"))
					.add(Restrictions.sqlRestriction("DATE_FORMAT(publish_date, '%d/%m/%Y') LIKE ?", "%" + documentInfo + "%", Hibernate.STRING))
					.add(Restrictions.sqlRestriction("DATE_FORMAT(receive_date, '%d/%m/%Y') LIKE ?", "%" + documentInfo + "%", Hibernate.STRING)));
		}
		if (simpleSearchDocumentVO.getMonth() != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.sqlRestriction("MONTH(publish_date) = ?", simpleSearchDocumentVO.getMonth(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("MONTH(receive_date) = ?", simpleSearchDocumentVO.getMonth(), Hibernate.INTEGER)));
		}
		if (simpleSearchDocumentVO.getYear() != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.sqlRestriction("YEAR(publish_date) = ?", simpleSearchDocumentVO.getYear(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("YEAR(receive_date) = ?", simpleSearchDocumentVO.getYear(), Hibernate.INTEGER)));
		}
		
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public Integer countComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchComingDocumentsCriteria(session, simpleSearchDocumentVO);
			
			List<ComingDocument> documents = (List<ComingDocument>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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

	public ComingDocument getComingDocumentById(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			ComingDocument d = (ComingDocument) session.get(ComingDocument.class, id);
			if (d != null) {
				Hibernate.initialize(d.getSentDepartments());
				Hibernate.initialize(d.getComingDocumentFiles());
				Hibernate.initialize(d.getDocumentType());
			}
			
			session.getTransaction().commit();
			
			return d;
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

	public void deleteComingDocument(Integer id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			ComingDocument document = (ComingDocument) session.get(ComingDocument.class, id);
			if (document != null) {
				session.delete(document);
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

	@SuppressWarnings("unchecked")
	public Boolean checkSignExistInYear(String sign, Integer year,
			String currentSign) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM ComingDocument d WHERE d.sign != :currentSign AND d.sign = :sign AND YEAR(receiveDate) = :year";
			Query query = session.createQuery(queryStr);
			query.setParameter("currentSign", currentSign);
			query.setParameter("sign", sign);
			query.setParameter("year", year);
			
			List<ComingDocument> documents = (List<ComingDocument>) query.list();
			
			session.getTransaction().commit();
			
			return documents != null && documents.size() > 0;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return true;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateComingDocument(ComingDocument comingDocument,
			Integer documentTypeId, List<Integer> senderIds) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			ComingDocument d = (ComingDocument) session.get(ComingDocument.class, comingDocument.getId());
			d.setSign(comingDocument.getSign());
			if (documentTypeId == null) {
				d.setDocumentType(null);
			} else {
				DocumentType documentType = (DocumentType) session.get(DocumentType.class, documentTypeId);
				d.setDocumentType(documentType);
			}
			d.setPublishDate(comingDocument.getPublishDate());
			d.setConfidentialLevel(comingDocument.getConfidentialLevel());
			d.setUrgentLevel(comingDocument.getUrgentLevel());
			d.setAbs(comingDocument.getAbs());
			
			if (senderIds != null && senderIds.size() > 0) {
				String queryStr = "FROM Department WHERE id IN :senderIds";
				Query query = session.createQuery(queryStr);
				query.setParameterList("senderIds", senderIds);
				List<Department> senders = (List<Department>) query.list();
				Set<Department> departments = new HashSet<Department>(senders);
				
				Set<Department> aDepartments = d.getSentDepartments();
				for (Department aDepartment : aDepartments) {
					aDepartment.getDocuments().remove(d);
				}
				
				d.getSentDepartments().clear();
				d.getSentDepartments().addAll(departments);
				for (Department department : departments) {
					department.getComingDocuments().add(d);
				}
			}
			
			Set<ComingDocumentFile> files = d.getComingDocumentFiles();
			for (ComingDocumentFile file : files) {
				session.delete(file);
			}
			
			d.getComingDocumentFiles().clear();
			for (ComingDocumentFile file : comingDocument.getComingDocumentFiles()) {
				session.save(file);
				d.getComingDocumentFiles().add(file);
			}
			
			session.update(d);
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			ex.printStackTrace();
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ComingDocument> getComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO,
			Integer page, Integer pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchComingDocumentsCriteria(session, complexSearchComingDocumentsVO);
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("receiveDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<ComingDocument> documents = (List<ComingDocument>) criteria.list();
			
			for (ComingDocument d : documents) {
				Hibernate.initialize(d.getComingDocumentFiles());
			}
			
			session.getTransaction().commit();
			
			return documents;
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
	
	private Criteria buildComplexSearchComingDocumentsCriteria(Session session, ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO) {
		Criteria criteria = session.createCriteria(ComingDocument.class, "document");
		if (complexSearchComingDocumentsVO.getSign() != null) {
			criteria.add(Restrictions.like("sign", "%" + complexSearchComingDocumentsVO.getSign() + "%"));
		}
		if (complexSearchComingDocumentsVO.getType() != null) {
			criteria = criteria.createAlias("document.documentType", "documentType", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("documentType.id", complexSearchComingDocumentsVO.getType()));
		}
		if (complexSearchComingDocumentsVO.getConfidentialLevel() != null) {
			criteria.add(Restrictions.eq("confidentialLevel", complexSearchComingDocumentsVO.getConfidentialLevel()));
		}
		if (complexSearchComingDocumentsVO.getUrgentLevel() != null) {
			criteria.add(Restrictions.eq("urgentLevel", complexSearchComingDocumentsVO.getUrgentLevel()));
		}
		if (complexSearchComingDocumentsVO.getSender() != null) {
			criteria = criteria.createAlias("document.sentDepartments", "senders", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("senders.id", complexSearchComingDocumentsVO.getSender()));
		}
		if (complexSearchComingDocumentsVO.getFromPublishDate() != null) {
			criteria.add(Restrictions.ge("publishDate", complexSearchComingDocumentsVO.getFromPublishDate()));
		}
		if (complexSearchComingDocumentsVO.getToPublishDate() != null) {
			criteria.add(Restrictions.le("publishDate", complexSearchComingDocumentsVO.getToPublishDate()));
		}
		if (complexSearchComingDocumentsVO.getFromReceiveDate() != null) {
			criteria.add(Restrictions.ge("receiveDate", complexSearchComingDocumentsVO.getFromReceiveDate()));
		}
		if (complexSearchComingDocumentsVO.getToReceiveDate() != null) {
			criteria.add(Restrictions.le("receiveDate", complexSearchComingDocumentsVO.getToReceiveDate()));
		}
		if (complexSearchComingDocumentsVO.getAbs() != null) {
			criteria.add(Restrictions.like("abs", "%" + complexSearchComingDocumentsVO.getAbs() + "%"));
		}
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public Integer countComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchComingDocumentsCriteria(session, complexSearchComingDocumentsVO);
			
			List<ComingDocument> documents = (List<ComingDocument>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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
	public List<ComingDocument> getComingDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchComingDocumentsCriteria(session, simpleSearchDocumentVO);
			
			criteria.addOrder(Order.asc("receiveDate"));
			criteria.addOrder(Order.asc("publishDate"));
			
			List<ComingDocument> documents = (List<ComingDocument>) criteria.list();
			
			for (ComingDocument document : documents) {
				Hibernate.initialize(document.getSentDepartments());
			}
			
			session.getTransaction().commit();
			
			return documents;
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
	public List<ComingDocument> getComingDocuments(
			ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchComingDocumentsCriteria(session, complexSearchComingDocumentsVO);
			criteria.addOrder(Order.desc("receiveDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<ComingDocument> documents = (List<ComingDocument>) criteria.list();
			
			for (ComingDocument d : documents) {
				Hibernate.initialize(d.getComingDocumentFiles());
			}
			
			session.getTransaction().commit();
			
			return documents;
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
