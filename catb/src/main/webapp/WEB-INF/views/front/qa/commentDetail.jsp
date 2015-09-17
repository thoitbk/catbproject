<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
#Tin_Chi_Tiet p {
	margin: 0px !important;
}
</style>

<div id="Tin_Chi_Tiet">
	<div class="DanhMuc">
		<strong>HỎI ĐÁP TRỰC TUYẾN</strong>
	</div>
	<div class="comment_content">
		<span class="question_title">${comment.title}</span>
		<span style="white-space: pre-wrap;">${comment.content}</span>
		<p class="asked_date">
			<fmt:formatDate var="commentedDate" value="${comment.commentedDate}" pattern="dd/MM/yyyy" />
			Gửi bởi: <c:out value="${comment.name}"></c:out> (<c:out value="${commentedDate}"></c:out>)
		</p>
		<div style="height: 20px;">
		</div>
		<span class="answer_title">CÂU TRẢ LỜI</span>
		<span class="comment_detail">
			${comment.replyContent}
		</span>
		<p class="answeredBy">
			<c:if test="${empty comment.answerer}">
				Trả lời bởi: Công an tỉnh Thái Bình
			</c:if>
			<c:if test="${not empty comment.answerer}">
				Trả lời bởi: <c:out value="${comment.answerer}"></c:out>
			</c:if>
		</p>
		<div style="height: 30px;">
		</div>
		
		<div id="">
			<div class="sameQuestions">Câu hỏi cùng lĩnh vực</div>
			<div>
				<ul style="padding: 0; list-style-type: none;">
					<c:forEach items="${sameComments}" var="sameComment">
						<li class="related_comment">
							<fmt:formatDate var="commentedDate" value="${sameComment.commentedDate}" pattern="dd/MM/yyyy" />
							<a href="${ct}/hoi-dap/${sameComment.id}/${f:toFriendlyUrl(sameComment.title)}" class="related_question_link"><span>${sameComment.title}<i>(${commentedDate})</i></span></a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>