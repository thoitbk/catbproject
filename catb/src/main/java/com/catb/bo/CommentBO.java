package com.catb.bo;

import java.util.List;

import com.catb.model.Comment;

public interface CommentBO {
	
	public void addComment(Comment comment, Integer catalogId);
	public List<Comment> getComments(Integer catalogId, String title, Integer display, Integer page, Integer pageSize);
	public Long countComments(Integer catalogId, String title, Integer display);
	public Comment getCommentById(Integer id);
	public Comment fetchCommentById(Integer id);
	public void updateComment(Comment comment, Integer qaCatalogId);
	public void deleteComments(Integer[] ids);
	public List<Comment> getCommentsInSameCatalog(Integer catalogId, Integer currentCommentId, Integer amount);
}
