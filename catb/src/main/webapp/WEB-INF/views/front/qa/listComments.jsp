<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="exTag" uri="/WEB-INF/tag/extags.tld" %>

<link href="${ct}/resources/css/form.css" rel="stylesheet" type="text/css" />
<link href="${ct}/resources/css/frontTable.css" rel="stylesheet" type="text/css" />

<div class="titleAdministrative">
	HỎI ĐÁP TRỰC TUYẾN
</div>

<table>
	<tr>
		<td width="20%">
			<img alt="" src="${ct}/resources/images/qa.jpg" style="width: 150px;">
		</td>
		<td width="80%">
			<form action="${ct}/hoi-dap" method="get">
				<table style="width: 100%">
					<tr>
						<td width="20%">
							<span class="lblBlack">Danh mục</span>
						</td>
						<td width="70%">
							<select name="cId" id="qaCatalogId" class="combobox" style="width: 100%;">
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
							<div style="height: 10px;"></div>
							<input type="submit" value="Tìm kiếm" class="button" />
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
	<tr>
		<td>
			<a href="${ct}/dat-cau-hoi"><img alt="" src="${ct}/resources/images/gui_cau_hoi.gif"></a>
		</td>
	</tr>
</table>

<table class="responstable">
	<tr class="header">
		<th width="5%">STT</th>
		<th width="65%">Câu hỏi</th>
		<th width="15%">Người hỏi</th>
		<th width="15%">Ngày hỏi</th>
	</tr>
	<c:forEach items="${comments}" var="comment" varStatus="s">
		<tr>
			<td width="5%">
				<c:out value="${(pageInfo.currentPage - 1) * pageInfo.pageSize + s.index + 1}" />
			</td>
			<td width="50%">
				<a class="procedure_link" href="${ct}/hoi-dap/${comment.id}/${f:toFriendlyUrl(comment.title)}">${comment.title}</a>
			</td>
			<td width="15%">
				<c:out value="${comment.name}"></c:out>
			</td>
			<td width="15%">
				<fmt:formatDate var="commentedDate" value="${comment.commentedDate}" pattern="dd/MM/yyyy" />
				<c:out value="${commentedDate}"></c:out>
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="4" style="text-align: right; background-color: #FFF; padding: 0.7em;">
			<exTag:paging pageInfo="${pageInfo}" link="${ct}/hoi-dap" cssClass="page_link" params="${params}" />
		</td>
	</tr>
</table>