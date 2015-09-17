<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="exTag" uri="/WEB-INF/tag/extags.tld" %>

<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ CÂU HỎI
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form action="${ct}/cm/comment/show" method="get">
		<table style="width: 80%" align="center">
			<tr>
				<td width="20%">
					<span class="lblBlack">Danh mục</span>
				</td>
				<td width="80%">
					<select name="cId" id="qaCatalogId" class="combobox" style="width: 100%">
						<option value="">------ Chọn danh mục câu hỏi ------</option>
						<c:forEach items="${qaCatalogMap}" var="qaCatalog">
							<c:if test="${qaCatalog.key == param.cId}">
								<option value="${qaCatalog.key}" selected="selected">${qaCatalog.value}</option>
							</c:if>
							<c:if test="${qaCatalog.key != param.cId}">
								<option value="${qaCatalog.key}">${qaCatalog.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td valign="middle">
					<span class="lblBlack">Tiêu đề</span>
				</td>
				<td>
					<input type="text" name="t" id="title" maxlength="1000" value="${param.t}" class="textbox" style="width: 100%" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Tìm kiếm" class="button" />
				</td>
			</tr>
		</table>
	</form>

	<table class="responstable">
		<tr class="header">
			<th width="5%">
				<input type="checkbox" name="selectAll" id="selectAll">
			</th>
			<th width="70%">Tiêu đề</th>
			<th width="20%">Trạng thái</th>
			<th>Trả lời</th>
		</tr>
		<c:forEach items="${comments}" var="comment">
			<tr>
				<td width="5%">
					<input type="checkbox" name="commentId" id="commentId" value="${comment.id}" class="checkbox" />
				</td>
				<td width="70%"><a href="${ct}/cm/comment/view/${comment.id}" class="news_title"><c:out value="${comment.title}"></c:out></a></td>
				<td width="20%">
					<c:if test="${comment.status == 0}">Đang chờ trả lời</c:if>
					<c:if test="${comment.status == 1}">Đã trả lời & chưa hiển thị</c:if>
					<c:if test="${comment.status == 2}">Đã trả lời & hiển thị</c:if>
				</td>
				<td>
					<a href="${ct}/cm/comment/answer/${comment.id}">
						<img src="${ct}/resources/images/answer.png" alt="Trả lời" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" style="text-align: right; background-color: #FFF; padding: 0.7em;">
				<exTag:paging pageInfo="${pageInfo}" link="${ct}/cm/comment/show" cssClass="page_link" params="${params}" />
			</td>
		</tr>
	    <tr>
	    	<td colspan="4" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/comment/delete" id="delComment"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>