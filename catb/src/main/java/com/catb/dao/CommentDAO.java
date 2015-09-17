package com.catb.dao;

import java.util.List;

import com.catb.model.Comment;

public interface CommentDAO {
	
	public void addComment(Comment comment);
	public List<Comment> getComments(Integer catalogId, String title, Integer display, Integer page, Integer pageSize);
	public Long countComments(Integer catalogId, String title, Integer display);
	public Comment getCommentById(Integer id);
	public void updateComment(Comment comment);
	public void deleteComment(Integer id);
	public List<Comment> getCommentsInSameCatalog(Integer catalogId, Integer currentCommentId, Integer amount);
}
