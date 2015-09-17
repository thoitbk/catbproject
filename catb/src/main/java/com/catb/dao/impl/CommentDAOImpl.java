package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.catb.dao.CommentDAO;
import com.catb.model.Comment;

@Repository
public class CommentDAOImpl implements CommentDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addComment(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		session.save(comment);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getComments(Integer catalogId, String title, Integer display,
			Integer page, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, catalogId, title, display);
		
		criteria.setFirstResult((page - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		
		criteria.addOrder(Order.desc("id"));
		
		return criteria.list();
	}

	public Long countComments(Integer catalogId, String title, Integer display) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, catalogId, title, display);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private Criteria buildCriteria(Session session, Integer catalogId, String title, Integer display) {
		Criteria criteria = session.createCriteria(Comment.class, "comment");
		criteria.createAlias("comment.qaCatalog", "qaCatalog");
		
		if (catalogId != null) {
			criteria.add(Restrictions.eq("qaCatalog.id", catalogId));
		}
		if (title != null && !"".equals(title.trim())) {
			criteria.add(Restrictions.like("comment.title", "%" + title.trim() + "%"));
		}
		if (display != null) {
			criteria.add(Restrictions.eq("comment.status", display));
		}
		
		return criteria;
	}

	public Comment getCommentById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Comment) session.get(Comment.class, id);
	}

	public void updateComment(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		session.update(comment);
	}

	public void deleteComment(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		if (comment != null) {
			session.delete(comment);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsInSameCatalog(Integer catalogId,
			Integer currentCommentId, Integer amount) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT c " + 
						"FROM Comment c INNER JOIN c.qaCatalog q " + 
						"WHERE q.id = :qaCatalogId AND c.id != :currentCommentId " + 
						"ORDER BY c.id DESC";
		Query query = session.createQuery(select);
		query.setParameter("qaCatalogId", catalogId);
		query.setParameter("currentCommentId", currentCommentId);
		query.setMaxResults(amount);
		
		return query.list();
	}
}
