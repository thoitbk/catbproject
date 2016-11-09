package catb.vanthu.dao.impl;

import java.util.Calendar;
import java.util.Date;
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

import catb.vanthu.dao.DocumentDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.Department;
import catb.vanthu.model.DepartmentDocument;
import catb.vanthu.model.DepartmentDocumentId;
import catb.vanthu.model.Document;
import catb.vanthu.model.DocumentFile;
import catb.vanthu.model.DocumentType;
import catb.vanthu.valueobject.ComplexSearchDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchInboundDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchOutboundDocumentsVO;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;

@Repository
public class DocumentDAOImpl implements DocumentDAO {
	
	static Logger logger = Logger.getLogger(DocumentDAOImpl.class.getName());
	
	@SuppressWarnings("unchecked")
	public void saveDocument(Document document, Integer documentTypeId,
			List<Integer> senderIds, List<Integer> receiverIds) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if (documentTypeId != null) {
				DocumentType documentType = (DocumentType) session.get(DocumentType.class, documentTypeId);
				document.setDocumentType(documentType);
			}
			
			if (senderIds != null && senderIds.size() > 0) {
				String queryStr = "FROM Department WHERE id IN :senderIds";
				Query query = session.createQuery(queryStr);
				query.setParameterList("senderIds", senderIds);
				List<Department> senders = (List<Department>) query.list();
				Set<Department> departments = new HashSet<Department>(senders);
				document.setDepartments(departments);
				for (Department d : departments) {
					d.getDocuments().add(document);
				}
			}
			
			session.save(document);
			
			if (receiverIds != null && receiverIds.size() > 0) {
				String queryStr = "FROM Department WHERE id IN :receiverIds";
				Query query = session.createQuery(queryStr);
				query.setParameterList("receiverIds", receiverIds);
				List<Department> receivers = (List<Department>) query.list();
				if (receivers != null && receivers.size() > 0) {
					for (Department department : receivers) {
						DepartmentDocumentId pk = new DepartmentDocumentId(document, department);
						DepartmentDocument departmentDocument = new DepartmentDocument(pk, new Date(), null, false);
						document.getDepartmentDocuments().add(departmentDocument);
						department.getDepartmentDocuments().add(departmentDocument);
						session.save(departmentDocument);
					}
				}
			}
			
			for (DocumentFile file : document.getDocumentFiles()) {
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
	public Document getDocumentBySign(String sign) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM Document WHERE sign = :sign";
			Query query = session.createQuery(queryStr);
			query.setParameter("sign", sign);
			
			List<Document> documents = (List<Document>) query.list();
			
			session.getTransaction().commit();
			
			return documents == null || documents.size() == 0 ? null : documents.get(0);
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
	public List<Document> getDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, int page,
			int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchDocumentsCriteria(session, simpleSearchDocumentVO);
			
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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
	
	@SuppressWarnings("deprecation")
	private Criteria buildSimpleSearchDocumentsCriteria(Session session, SimpleSearchDocumentVO simpleSearchDocumentVO) {
		Criteria criteria = session.createCriteria(Document.class, "document");
		String documentInfo = simpleSearchDocumentVO.getDocumentInfo();
		if (documentInfo != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.like("sign", "%" + documentInfo + "%"))
					.add(Restrictions.like("signer", "%" + documentInfo + "%"))
					.add(Restrictions.like("abs", "%" + documentInfo + "%"))
					.add(Restrictions.sqlRestriction("DATE_FORMAT(publish_date, '%d/%m/%Y') LIKE ?", "%" + documentInfo + "%", Hibernate.STRING))
					.add(Restrictions.sqlRestriction("DATE_FORMAT(send_date, '%d/%m/%Y') LIKE ?", "%" + documentInfo + "%", Hibernate.STRING)));
		}
		if (simpleSearchDocumentVO.getMonth() != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.sqlRestriction("MONTH(publish_date) = ?", simpleSearchDocumentVO.getMonth(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("MONTH(send_date) = ?", simpleSearchDocumentVO.getMonth(), Hibernate.INTEGER)));
		}
		if (simpleSearchDocumentVO.getYear() != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.sqlRestriction("YEAR(publish_date) = ?", simpleSearchDocumentVO.getYear(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("YEAR(send_date) = ?", simpleSearchDocumentVO.getYear(), Hibernate.INTEGER)));
		}
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public Integer countDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchDocumentsCriteria(session, simpleSearchDocumentVO);
			
			List<Document> documents = (List<Document>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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

	public Document getDocumentById(Integer documentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Document d = (Document) session.get(Document.class, documentId);
			if (d != null) {
				Hibernate.initialize(d.getDepartmentDocuments());
				Hibernate.initialize(d.getDepartments());
				Hibernate.initialize(d.getDocumentFiles());
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

	@SuppressWarnings("unchecked")
	public List<Document> getDocuments(
			ComplexSearchDocumentsVO complexSearchDocumentsVO, int page,
			int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchDocumentsCriteria(session, complexSearchDocumentsVO);
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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
	
	private Criteria buildComplexSearchDocumentsCriteria(Session session, ComplexSearchDocumentsVO complexSearchDocumentsVO) {
		Criteria criteria = session.createCriteria(Document.class, "document");
		if (complexSearchDocumentsVO.getSign() != null) {
			criteria.add(Restrictions.like("sign", "%" + complexSearchDocumentsVO.getSign() + "%"));
		}
		if (complexSearchDocumentsVO.getType() != null) {
			criteria = criteria.createAlias("document.documentType", "documentType", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("documentType.id", complexSearchDocumentsVO.getType()));
		}
		if (complexSearchDocumentsVO.getConfidentialLevel() != null) {
			criteria.add(Restrictions.eq("confidentialLevel", complexSearchDocumentsVO.getConfidentialLevel()));
		}
		if (complexSearchDocumentsVO.getUrgentLevel() != null) {
			criteria.add(Restrictions.eq("urgentLevel", complexSearchDocumentsVO.getUrgentLevel()));
		}
		if (complexSearchDocumentsVO.getSender() != null) {
			criteria = criteria.createAlias("document.departments", "senders", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("senders.id", complexSearchDocumentsVO.getSender()));
		}
		if (complexSearchDocumentsVO.getReceiver() != null) {
			criteria = criteria.createAlias("document.departmentDocuments", "receivers", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("receivers.pk.department.id", complexSearchDocumentsVO.getReceiver()));
		}
		if (complexSearchDocumentsVO.getSigner() != null) {
			criteria.add(Restrictions.like("signer", "%" + complexSearchDocumentsVO.getSigner() + "%"));
		}
		if (complexSearchDocumentsVO.getFromPublishDate() != null) {
			criteria.add(Restrictions.ge("publishDate", complexSearchDocumentsVO.getFromPublishDate()));
		}
		if (complexSearchDocumentsVO.getToPublishDate() != null) {
			criteria.add(Restrictions.le("publishDate", complexSearchDocumentsVO.getToPublishDate()));
		}
		if (complexSearchDocumentsVO.getFromSendDate() != null) {
			criteria.add(Restrictions.ge("sendDate", complexSearchDocumentsVO.getFromSendDate()));
		}
		if (complexSearchDocumentsVO.getToSendDate() != null) {
			criteria.add(Restrictions.le("sendDate", complexSearchDocumentsVO.getToSendDate()));
		}
		if (complexSearchDocumentsVO.getAbs() != null) {
			criteria.add(Restrictions.like("abs", "%" + complexSearchDocumentsVO.getAbs() + "%"));
		}
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public Integer countDocuments(ComplexSearchDocumentsVO complexSearchDocumentsVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchDocumentsCriteria(session, complexSearchDocumentsVO);
			
			List<Document> documents = (List<Document>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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
	
	@SuppressWarnings("unchecked")
	public List<Document> getOutboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO,
			Integer departmentId, int page, int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchDocumentsCriteria(session, simpleSearchDocumentVO);
			criteria = criteria.createAlias("document.departments", "senders", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("senders.id", departmentId));
			
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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
	public Integer countOutboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchDocumentsCriteria(session, simpleSearchDocumentVO);
			criteria = criteria.createAlias("document.departments", "senders", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("senders.id", departmentId));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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

	@SuppressWarnings("unchecked")
	public Boolean checkUserHavePermissionToOutboundFile(Integer departmentId,
			Integer fileId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d INNER JOIN d.documentFiles f INNER JOIN d.departments de WHERE f.id = :fileId AND de.id = :departmentId";
			Query query = session.createQuery(queryStr);
			query.setParameter("fileId", fileId);
			query.setParameter("departmentId", departmentId);
			
			List<Document> documents = (List<Document>) query.list();
			
			session.getTransaction().commit();
			
			return documents != null && documents.size() > 0;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Boolean checkUserHavePermissionToOutboundDocument(
			Integer departmentId, Integer documentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d INNER JOIN d.departments de WHERE d.id = :documentId AND de.id = :departmentId";
			Query query = session.createQuery(queryStr);
			query.setParameter("documentId", documentId);
			query.setParameter("departmentId", departmentId);
			
			List<Document> documents = (List<Document>) query.list();
			
			session.getTransaction().commit();
			
			return documents != null && documents.size() > 0;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> getOutboundDocuments(
			ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO,
			Integer departmentId, int page, int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchOutboundDocumentsCriteria(session, complexSearchOutboundDocumentsVO, departmentId);
			
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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
	public Integer countOutboundDocuments(
			ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO,
			Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchOutboundDocumentsCriteria(session, complexSearchOutboundDocumentsVO, departmentId);
			
			List<Document> documents = (List<Document>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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
	
	private Criteria buildComplexSearchOutboundDocumentsCriteria(Session session, ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO, Integer departmentId) {
		Criteria criteria = session.createCriteria(Document.class, "document");
		
		criteria.createAlias("document.departments", "senders", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("senders.id", departmentId));
		
		if (complexSearchOutboundDocumentsVO.getSign() != null) {
			criteria.add(Restrictions.like("sign", "%" + complexSearchOutboundDocumentsVO.getSign() + "%"));
		}
		if (complexSearchOutboundDocumentsVO.getType() != null) {
			criteria = criteria.createAlias("document.documentType", "documentType", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("documentType.id", complexSearchOutboundDocumentsVO.getType()));
		}
		if (complexSearchOutboundDocumentsVO.getConfidentialLevel() != null) {
			criteria.add(Restrictions.eq("confidentialLevel", complexSearchOutboundDocumentsVO.getConfidentialLevel()));
		}
		if (complexSearchOutboundDocumentsVO.getUrgentLevel() != null) {
			criteria.add(Restrictions.eq("urgentLevel", complexSearchOutboundDocumentsVO.getUrgentLevel()));
		}
		if (complexSearchOutboundDocumentsVO.getReceiver() != null) {
			criteria = criteria.createAlias("document.departmentDocuments", "receivers", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("receivers.pk.department.id", complexSearchOutboundDocumentsVO.getReceiver()));
		}
		if (complexSearchOutboundDocumentsVO.getSigner() != null) {
			criteria.add(Restrictions.like("signer", "%" + complexSearchOutboundDocumentsVO.getSigner() + "%"));
		}
		if (complexSearchOutboundDocumentsVO.getFromPublishDate() != null) {
			criteria.add(Restrictions.ge("publishDate", complexSearchOutboundDocumentsVO.getFromPublishDate()));
		}
		if (complexSearchOutboundDocumentsVO.getToPublishDate() != null) {
			criteria.add(Restrictions.le("publishDate", complexSearchOutboundDocumentsVO.getToPublishDate()));
		}
		if (complexSearchOutboundDocumentsVO.getFromSendDate() != null) {
			criteria.add(Restrictions.ge("sendDate", complexSearchOutboundDocumentsVO.getFromSendDate()));
		}
		if (complexSearchOutboundDocumentsVO.getToSendDate() != null) {
			criteria.add(Restrictions.le("sendDate", complexSearchOutboundDocumentsVO.getToSendDate()));
		}
		if (complexSearchOutboundDocumentsVO.getAbs() != null) {
			criteria.add(Restrictions.like("abs", "%" + complexSearchOutboundDocumentsVO.getAbs() + "%"));
		}
		
		return criteria;
	}
	
	@SuppressWarnings("deprecation")
	private Criteria buildSimpleSearchInboundDocumentsCriteria(Session session, SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId) {
		Criteria criteria = session.createCriteria(Document.class, "document");
		
		criteria = criteria.createAlias("document.departmentDocuments", "receivers", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("receivers.pk.department.id", departmentId));
		
		String documentInfo = simpleSearchDocumentVO.getDocumentInfo();
		if (documentInfo != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.like("sign", "%" + documentInfo + "%"))
					.add(Restrictions.like("signer", "%" + documentInfo + "%"))
					.add(Restrictions.like("abs", "%" + documentInfo + "%"))
					.add(Restrictions.sqlRestriction("DATE_FORMAT(publish_date, '%d/%m/%Y') LIKE ?", "%" + documentInfo + "%", Hibernate.STRING))
					.add(Restrictions.sqlRestriction("DATE_FORMAT(send_date, '%d/%m/%Y') LIKE ?", "%" + documentInfo + "%", Hibernate.STRING))
					.add(Restrictions.sqlRestriction("DATE_FORMAT(receive_time, '%d/%m/%Y') LIKE ?", "%" + documentInfo + "%", Hibernate.STRING)));
		}
		if (simpleSearchDocumentVO.getMonth() != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.sqlRestriction("MONTH(publish_date) = ?", simpleSearchDocumentVO.getMonth(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("MONTH(send_date) = ?", simpleSearchDocumentVO.getMonth(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("MONTH(receive_time) = ?", simpleSearchDocumentVO.getMonth(), Hibernate.INTEGER)));
		}
		if (simpleSearchDocumentVO.getYear() != null) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.sqlRestriction("YEAR(publish_date) = ?", simpleSearchDocumentVO.getYear(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("YEAR(send_date) = ?", simpleSearchDocumentVO.getYear(), Hibernate.INTEGER))
					.add(Restrictions.sqlRestriction("YEAR(receive_time) = ?", simpleSearchDocumentVO.getYear(), Hibernate.INTEGER)));
		}
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> getInboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId, int page, int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchInboundDocumentsCriteria(session, simpleSearchDocumentVO, departmentId);
			
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
				Hibernate.initialize(d.getDepartmentDocuments());
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
	public Integer countInboundDocuments(SimpleSearchDocumentVO simpleSearchDocumentVO, Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchInboundDocumentsCriteria(session, simpleSearchDocumentVO, departmentId);
			
			List<Document> documents = (List<Document>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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

	@SuppressWarnings("unchecked")
	public Boolean checkUserHavePermissionToInboundFile(Integer departmentId, Integer fileId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d INNER JOIN d.documentFiles f INNER JOIN d.departmentDocuments de WHERE f.id = :fileId AND de.pk.department.id = :departmentId";
			Query query = session.createQuery(queryStr);
			query.setParameter("fileId", fileId);
			query.setParameter("departmentId", departmentId);
			
			List<Document> documents = (List<Document>) query.list();
			
			session.getTransaction().commit();
			
			return documents != null && documents.size() > 0;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Boolean checkUserHavePermissionToInboundDocument(
			Integer departmentId, Integer documentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d INNER JOIN d.departmentDocuments de WHERE d.id = :documentId AND de.pk.department.id = :departmentId";
			Query query = session.createQuery(queryStr);
			query.setParameter("documentId", documentId);
			query.setParameter("departmentId", departmentId);
			
			List<Document> documents = (List<Document>) query.list();
			
			session.getTransaction().commit();
			
			return documents != null && documents.size() > 0;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void updateReadStatusOfDocument(Integer departmentId, Integer documentId, Date receiveTime, Boolean isRead) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "UPDATE DepartmentDocument SET receiveTime = :receiveTime, isRead = :isRead WHERE pk.document.id = :documentId AND pk.department.id = :departmentId";
			Query query = session.createQuery(queryStr);
			query.setParameter("receiveTime", receiveTime);
			query.setParameter("isRead", isRead);
			query.setParameter("documentId", documentId);
			query.setParameter("departmentId", departmentId);
			
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

	@SuppressWarnings("unchecked")
	public List<Document> getInboundDocuments(
			ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO,
			Integer departmentId, int page, int pageSize) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchInboundDocumentsCriteria(session, complexSearchInboundDocumentsVO, departmentId);
			
			criteria.setFirstResult((page - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
				Hibernate.initialize(d.getDepartmentDocuments());
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
	public Integer countInboundDocuments(
			ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO,
			Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchInboundDocumentsCriteria(session, complexSearchInboundDocumentsVO, departmentId);
			
			List<Document> documents = (List<Document>) criteria.list();
			
			session.getTransaction().commit();
			
			return documents.size();
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
	
	private Criteria buildComplexSearchInboundDocumentsCriteria(Session session, ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO, Integer departmentId) {
		Criteria criteria = session.createCriteria(Document.class, "document");
		
		criteria = criteria.createAlias("document.departmentDocuments", "receivers", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("receivers.pk.department.id", departmentId));
		
		if (complexSearchInboundDocumentsVO.getSign() != null) {
			criteria.add(Restrictions.like("sign", "%" + complexSearchInboundDocumentsVO.getSign() + "%"));
		}
		if (complexSearchInboundDocumentsVO.getType() != null) {
			criteria = criteria.createAlias("document.documentType", "documentType", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("documentType.id", complexSearchInboundDocumentsVO.getType()));
		}
		if (complexSearchInboundDocumentsVO.getConfidentialLevel() != null) {
			criteria.add(Restrictions.eq("confidentialLevel", complexSearchInboundDocumentsVO.getConfidentialLevel()));
		}
		if (complexSearchInboundDocumentsVO.getUrgentLevel() != null) {
			criteria.add(Restrictions.eq("urgentLevel", complexSearchInboundDocumentsVO.getUrgentLevel()));
		}
		if (complexSearchInboundDocumentsVO.getSender() != null) {
			criteria.createAlias("document.departments", "senders", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("senders.id", complexSearchInboundDocumentsVO.getSender()));
		}
		if (complexSearchInboundDocumentsVO.getSigner() != null) {
			criteria.add(Restrictions.like("signer", "%" + complexSearchInboundDocumentsVO.getSigner() + "%"));
		}
		if (complexSearchInboundDocumentsVO.getFromPublishDate() != null) {
			criteria.add(Restrictions.ge("publishDate", complexSearchInboundDocumentsVO.getFromPublishDate()));
		}
		if (complexSearchInboundDocumentsVO.getToPublishDate() != null) {
			criteria.add(Restrictions.le("publishDate", complexSearchInboundDocumentsVO.getToPublishDate()));
		}
		if (complexSearchInboundDocumentsVO.getFromReceiveDate() != null) {
			criteria.add(Restrictions.ge("receivers.receiveTime", complexSearchInboundDocumentsVO.getFromReceiveDate()));
		}
		if (complexSearchInboundDocumentsVO.getToReceiveDate() != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(complexSearchInboundDocumentsVO.getToReceiveDate());
			c.add(Calendar.DATE, 1);
			criteria.add(Restrictions.lt("receivers.receiveTime", c.getTime()));
		}
		if (complexSearchInboundDocumentsVO.getAbs() != null) {
			criteria.add(Restrictions.like("abs", "%" + complexSearchInboundDocumentsVO.getAbs() + "%"));
		}
		
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Document> getUnreadDocuments(Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d INNER JOIN d.departmentDocuments de WHERE de.pk.department.id = :departmentId AND de.isRead = :isRead";
			Query query = session.createQuery(queryStr);
			query.setParameter("departmentId", departmentId);
			query.setParameter("isRead", false);
			
			List<Document> documents = (List<Document>) query.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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

	public void updateSendStatusOfDocument(Integer documentId,
			Boolean isMailSent) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "UPDATE Document SET isMailSent = :isMailSent WHERE id = :documentId";
			Query query = session.createQuery(queryStr);
			query.setParameter("isMailSent", isMailSent);
			query.setParameter("documentId", documentId);
			
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

	@SuppressWarnings("unchecked")
	public Boolean checkSignExistInYear(String sign, Integer year) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d WHERE d.sign = :sign AND YEAR(sendDate) = :year";
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
	public Boolean checkNumberExistInYear(Integer number, Integer year) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d WHERE d.sign LIKE '%" + number + "/%'" + " AND YEAR(sendDate) = :year";
			Query query = session.createQuery(queryStr);
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

	public void deleteDocument(Integer documentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Document document = (Document) session.get(Document.class, documentId);
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
			
			String queryStr = "SELECT d FROM Document d WHERE d.sign != :currentSign AND d.sign = :sign AND YEAR(sendDate) = :year";
			Query query = session.createQuery(queryStr);
			query.setParameter("currentSign", currentSign);
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
	public Boolean checkNumberExistInYear(Integer number, Integer year,
			Integer currentNumber) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "SELECT d FROM Document d WHERE d.sign NOT LIKE '%" + currentNumber + "/%' AND d.sign LIKE '%" + number + "/%'" + " AND YEAR(sendDate) = :year";
			Query query = session.createQuery(queryStr);
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
	public void updateDocument(Document document, Integer documentTypeId,
			List<Integer> senderIds, List<Integer> receiverIds) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Document d = (Document) session.get(Document.class, document.getId());
			d.setSign(document.getSign());
			if (documentTypeId == null) {
				d.setDocumentType(null);
			} else {
				DocumentType documentType = (DocumentType) session.get(DocumentType.class, documentTypeId);
				d.setDocumentType(documentType);
			}
			d.setPublishDate(document.getPublishDate());
			d.setConfidentialLevel(document.getConfidentialLevel());
			d.setUrgentLevel(document.getUrgentLevel());
			d.setSigner(document.getSigner());
			d.setAbs(document.getAbs());
			
			if (senderIds != null && senderIds.size() > 0) {
				String queryStr = "FROM Department WHERE id IN :senderIds";
				Query query = session.createQuery(queryStr);
				query.setParameterList("senderIds", senderIds);
				List<Department> senders = (List<Department>) query.list();
				Set<Department> departments = new HashSet<Department>(senders);
				
				Set<Department> aDepartments = d.getDepartments();
				for (Department aDepartment : aDepartments) {
					aDepartment.getDocuments().remove(d);
				}
				
				d.getDepartments().clear();
				d.getDepartments().addAll(departments);
				for (Department department : departments) {
					department.getDocuments().add(d);
				}
			}
			
			if (receiverIds != null && receiverIds.size() > 0) {
				String queryStr = "FROM Department WHERE id IN :receiverIds";
				Query query = session.createQuery(queryStr);
				query.setParameterList("receiverIds", receiverIds);
				List<Department> receivers = (List<Department>) query.list();
				if (receivers != null && receivers.size() > 0) {
					Set<DepartmentDocument> departmentDocuments = d.getDepartmentDocuments();
					Set<DepartmentDocument> newDepartmentDocuments = new HashSet<DepartmentDocument>();
					
					for (Department department : receivers) {
						DepartmentDocumentId pk = new DepartmentDocumentId(d, department);
						Date sendTime = new Date();
						Date receiveTime = null;
						Boolean isRead = false;
						for (DepartmentDocument d1 : departmentDocuments) {
							if (d1.getPk().getDepartment().equals(department)) {
								sendTime = d1.getSendTime();
								receiveTime = d1.getReceiveTime();
								isRead = d1.getIsRead();
								break;
							}
						}
						
						DepartmentDocument departmentDocument = new DepartmentDocument(pk, sendTime, receiveTime, isRead);
						newDepartmentDocuments.add(departmentDocument);
						
					}
					
					for (DepartmentDocument d1 : d.getDepartmentDocuments()) {
						session.delete(d1);
					}
					
					d.getDepartmentDocuments().clear();
					d.getDepartmentDocuments().addAll(newDepartmentDocuments);
					for (DepartmentDocument d2 : newDepartmentDocuments) {
						session.save(d2);
					}
				}
			}
			
			Set<DocumentFile> files = d.getDocumentFiles();
			for (DocumentFile file : files) {
				session.delete(file);
			}
			
			d.getDocumentFiles().clear();
			for (DocumentFile file : document.getDocumentFiles()) {
				session.save(file);
				d.getDocumentFiles().add(file);
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
	public List<Document> getDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchDocumentsCriteria(session, simpleSearchDocumentVO);
			
			criteria.addOrder(Order.asc("sendDate"));
			criteria.addOrder(Order.asc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
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
	public List<Document> getDocuments(
			ComplexSearchDocumentsVO complexSearchDocumentsVO) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchDocumentsCriteria(session, complexSearchDocumentsVO);
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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
	public List<Document> getOutboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentsVO, Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchDocumentsCriteria(session, simpleSearchDocumentsVO);
			criteria = criteria.createAlias("document.departments", "senders", Criteria.INNER_JOIN);
			criteria.add(Restrictions.eq("senders.id", departmentId));
			
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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
	public List<Document> getOutboundDocuments(
			ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO,
			Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchOutboundDocumentsCriteria(session, complexSearchOutboundDocumentsVO, departmentId);
			
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
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
	public List<Document> getInboundDocuments(
			SimpleSearchDocumentVO simpleSearchDocumentsVO, Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildSimpleSearchInboundDocumentsCriteria(session, simpleSearchDocumentsVO, departmentId);
			
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
				Hibernate.initialize(d.getDepartmentDocuments());
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
	public List<Document> getInboundDocuments(
			ComplexSearchInboundDocumentsVO complexSearchInboundDocumentVO,
			Integer departmentId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = buildComplexSearchInboundDocumentsCriteria(session, complexSearchInboundDocumentVO, departmentId);
			
			criteria.addOrder(Order.desc("sendDate"));
			criteria.addOrder(Order.desc("publishDate"));
			
			List<Document> documents = (List<Document>) criteria.list();
			
			for (Document d : documents) {
				Hibernate.initialize(d.getDocumentFiles());
				Hibernate.initialize(d.getDepartmentDocuments());
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
