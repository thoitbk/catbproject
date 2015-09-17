package com.catb.web.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.catb.bo.CommentBO;
import com.catb.bo.QACatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.model.Comment;
import com.catb.model.CommentStatus;
import com.catb.model.QACatalog;
import com.catb.web.tag.PageInfo;
import com.catb.web.util.Util;
import com.catb.web.validator.CreateCommentValidator;
import com.catb.web.viewmodel.CreateCommentViewModel;
import com.catb.web.viewmodel.ReplyCommentViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class CommentController {
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private QACatalogBO qaCatalogBO;
	
	@Autowired
	private CreateCommentValidator createCommentValidator;
	
	@ModelAttribute("qaCatalogMap")
	public Map<Integer, String> populateQACatalogs() {
		List<QACatalog> qaCatalogs = qaCatalogBO.getQACatalogs();
		Map<Integer, String> qaCatalogMap = new LinkedHashMap<Integer, String>();
		for (QACatalog qaCatalog : qaCatalogs) {
			qaCatalogMap.put(qaCatalog.getId(), qaCatalog.getName());
		}
		
		return qaCatalogMap;
	}
	
	@RequestMapping(value = "/dat-cau-hoi", method = RequestMethod.GET)
	public ModelAndView showCreateComment(ModelMap model) {
		CreateCommentViewModel createCommentViewModel = new CreateCommentViewModel();
		model.addAttribute("createCommentViewModel", createCommentViewModel);
		
		return new ModelAndView("askQuestion");
	}
	
	@RequestMapping(value = "/dat-cau-hoi", method = RequestMethod.POST)
	public ModelAndView processCreateComment(
								@Valid @ModelAttribute("createCommentViewModel") CreateCommentViewModel createCommentViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		String validCode = (String) request.getSession().getAttribute(Constants.QA_CAPTCHA_KEY);
		createCommentValidator.setValidCode(validCode);
		createCommentValidator.validate(createCommentViewModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return new ModelAndView("askQuestion");
		} else {
			Comment comment = new Comment();
			comment.setAddress(createCommentViewModel.getAddress());
			comment.setCommentedDate(new Date());
			comment.setContent(createCommentViewModel.getContent());
			comment.setEmail(createCommentViewModel.getEmail());
			comment.setName(createCommentViewModel.getName());
			comment.setPhoneNumber(createCommentViewModel.getPhoneNumber());
			comment.setTitle(createCommentViewModel.getTitle());
			comment.setStatus(CommentStatus.PENDING_FOR_ANSWER.getStatus());
			
			commentBO.addComment(comment, createCommentViewModel.getQaCatalogId());
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("comment.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/dat-cau-hoi"));
		}
	}
	
	@RequiresPermissions(value = {"comment:manage"})
	@RequestMapping(value = "/cm/comment/show", method = RequestMethod.GET)
	public ModelAndView showComments(
			@RequestParam(value = "cId", required = false) Integer catalogId,
			@RequestParam(value = "t", required = false) String title,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer page, 
			ModelMap model, HttpServletRequest request) {
		Integer pageSize = Util.getPageSize(request);
		List<Comment> comments = commentBO.getComments(catalogId, title, null, page, pageSize);
		model.addAttribute("comments", comments);
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (catalogId != null && catalogId >= 0) {
			params.put("cId", String.valueOf(catalogId));
		}
		if (title != null && !"".equals(title)) {
			params.put("t", title);
		}
		model.addAttribute("params", params);
		
		Long total = commentBO.countComments(catalogId, title, null);
		model.addAttribute("pageInfo", new PageInfo(total, page, pageSize));
		
		return new ModelAndView("cm/comment/show");
	}
	
	@RequiresPermissions(value = {"comment:manage"})
	@RequestMapping(value = "/cm/comment/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewComment(@PathVariable("id") Integer id, ModelMap model) {
		Comment comment = commentBO.getCommentById(id);
		model.addAttribute("comment", comment);
		
		return new ModelAndView("cm/comment/view");
	}
	
	@RequiresPermissions(value = {"comment:manage"})
	@RequestMapping(value = "/cm/comment/answer/{id}", method = RequestMethod.GET)
	public ModelAndView showAnswerComment(@PathVariable("id") Integer id, ModelMap model) {
		Comment comment = commentBO.fetchCommentById(id);
		ReplyCommentViewModel replyCommentViewModel = new ReplyCommentViewModel();
		if (comment != null) {
			replyCommentViewModel.setTitle(comment.getTitle());
			Integer status = comment.getStatus();
			if (status != null && status.equals(CommentStatus.ANSWERED_AND_SHOWED.getStatus())) {
				replyCommentViewModel.setShow(true);
			}
			replyCommentViewModel.setReplyContent(comment.getReplyContent());
			if (comment.getQaCatalog() != null) {
				replyCommentViewModel.setQaCatalogId(comment.getQaCatalog().getId());
			}
			replyCommentViewModel.setName(comment.getName());
			replyCommentViewModel.setContent(comment.getContent());
			replyCommentViewModel.setAnswerer(comment.getAnswerer());
		}
		
		model.addAttribute("replyCommentViewModel", replyCommentViewModel);
		
		return new ModelAndView("cm/comment/answer");
	}
	
	@RequiresPermissions(value = {"comment:manage"})
	@RequestMapping(value = "/cm/comment/answer/{id}", method = RequestMethod.POST)
	public ModelAndView processAnswerComment(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("replyCommentViewModel") ReplyCommentViewModel replyCommentViewModel,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("cm/comment/answer");
		} else {
			Comment comment = new Comment();
			comment.setId(id);
			comment.setTitle(replyCommentViewModel.getTitle());
			comment.setName(replyCommentViewModel.getName());
			comment.setContent(replyCommentViewModel.getContent());
			comment.setAnswerer(replyCommentViewModel.getAnswerer());
			
			Integer status = CommentStatus.PENDING_FOR_ANSWER.getStatus();
			String replyContent = replyCommentViewModel.getReplyContent();
			if (replyContent != null && !"".equals(replyContent.trim())) {
				status = replyCommentViewModel.getShow() ? CommentStatus.ANSWERED_AND_SHOWED.getStatus() : CommentStatus.ANSWERED_NOT_SHOWED.getStatus();
				comment.setReplyContent(replyContent.trim());
			}
			comment.setStatus(status);
			
			commentBO.updateComment(comment, replyCommentViewModel.getQaCatalogId());
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("comment.answered.successfully"));
			String queryString = request.getQueryString() != null && !"".equals(request.getQueryString()) ? "?" + request.getQueryString() : "";
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/comment/show" + queryString));
		}
	}
	
	@RequiresPermissions(value = {"comment:manage"})
	@RequestMapping(value = "/cm/comment/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteComment(@RequestParam("ids") Integer[] ids, HttpSession session) {
		commentBO.deleteComments(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("comment.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequestMapping(value = "/hoi-dap", method = RequestMethod.GET)
	public ModelAndView listComments(
			@RequestParam(value = "cId", required = false) Integer catalogId,
			@RequestParam(value = "t", required = false) String title,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer page, 
			ModelMap model, HttpServletRequest request) {
		Integer pageSize = Constants.QUESTION_PAGE_SIZE;
		List<Comment> comments = commentBO.getComments(catalogId, title, CommentStatus.ANSWERED_AND_SHOWED.getStatus(), page, pageSize);
		model.addAttribute("comments", comments);
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (catalogId != null && catalogId >= 0) {
			params.put("cId", String.valueOf(catalogId));
		}
		if (title != null && !"".equals(title)) {
			params.put("t", title);
		}
		model.addAttribute("params", params);
		
		Long total = commentBO.countComments(catalogId, title, CommentStatus.ANSWERED_AND_SHOWED.getStatus());
		model.addAttribute("pageInfo", new PageInfo(total, page, pageSize));
		
		return new ModelAndView("listComments");
	}
	
	@RequestMapping(value = {"/hoi-dap/{id}", "/hoi-dap/{id}/{s}"}, method = RequestMethod.GET)
	public ModelAndView viewCommentDetail(@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		Comment comment = commentBO.fetchCommentById(id);
		if (comment != null) {
			model.addAttribute("comment", comment);
			if (comment.getQaCatalog() != null) {
				Integer catalogId = comment.getQaCatalog().getId();
				List<Comment> sameComments = commentBO.getCommentsInSameCatalog(catalogId, id, Constants.COMMENT_IN_SAME_CATALOG);
				model.addAttribute("sameComments", sameComments);
			}
			
			return new ModelAndView("commentDetail");
		} else {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/notFound"));
		}
	}
}
