package com.catb.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.CommentBO;
import com.catb.dao.CommentDAO;
import com.catb.dao.QACatalogDAO;
import com.catb.model.Comment;
import com.catb.model.QACatalog;

@Service
public class CommentBOImpl implements CommentBO {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private QACatalogDAO qaCatalogDAO;
	
	@Transactional
	public void addComment(Comment comment, Integer catalogId) {
		if (catalogId != null) {
			QACatalog qaCatalog = qaCatalogDAO.getQACatalogById(catalogId);
			if (qaCatalog != null) {
				comment.setQaCatalog(qaCatalog);
			}
		}
		
		commentDAO.addComment(comment);
	}
	
	@Transactional
	public List<Comment> getComments(Integer catalogId, String title, Integer display,
			Integer page, Integer pageSize) {
		return commentDAO.getComments(catalogId, title, display, page, pageSize);
	}
	
	@Transactional
	public Long countComments(Integer catalogId, String title, Integer display) {
		return commentDAO.countComments(catalogId, title, display);
	}
	
	@Transactional
	public Comment getCommentById(Integer id) {
		return commentDAO.getCommentById(id);
	}
	
	@Transactional
	public Comment fetchCommentById(Integer id) {
		Comment comment = commentDAO.getCommentById(id);
		if (comment != null && comment.getQaCatalog() != null) {
			Hibernate.initialize(comment.getQaCatalog());
		}
		
		return comment;
	}
	
	@Transactional
	public void updateComment(Comment comment, Integer qaCatalogId) {
		Comment c = commentDAO.getCommentById(comment.getId());
		if (c != null) {
			c.setTitle(comment.getTitle());
			c.setName(comment.getName());
			c.setContent(comment.getContent());
			c.setAnswerer(comment.getAnswerer());
			c.setStatus(comment.getStatus());
			c.setReplyContent(comment.getReplyContent());
			if (qaCatalogId != null) {
				QACatalog qaCatalog = qaCatalogDAO.getQACatalogById(qaCatalogId);
				c.setQaCatalog(qaCatalog);
			}
			
			commentDAO.updateComment(c);
		}
	}
	
	@Transactional
	public void deleteComments(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				commentDAO.deleteComment(id);
			}
		}
	}
	
	@Transactional
	public List<Comment> getCommentsInSameCatalog(Integer catalogId,
			Integer currentCommentId, Integer amount) {
		return commentDAO.getCommentsInSameCatalog(catalogId, currentCommentId, amount);
	}
}
