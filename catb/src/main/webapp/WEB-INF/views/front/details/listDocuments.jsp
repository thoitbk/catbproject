<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${ct}/resources/css/frontTable.css" rel="stylesheet" type="text/css" />

<div class="titleAdministrative">
	VĂN BẢN PHÁP QUY
</div>

<table class="responstable">
	<tr class="header">
		<th width="5%">STT</th>
		<th width="60%">Trích yếu</th>
		<th width="15%">Ngày ban hành</th>
		<th>Đơn vị ban hành</th>
	</tr>
	<c:forEach items="${documents}" var="document" varStatus="s">
		<tr>
			<td>
				<c:out value="${s.index + 1}" />
			</td>
			<td>
				<a class="procedure_link" href="${ct}/van-ban-phap-quy/${document.id}/${f:toFriendlyUrl(document.summary)}">${document.summary}</a>
			</td>
			<td>
				<fmt:formatDate var="publishedDate" value="${document.publishedDate}" pattern="dd/MM/yyyy" />
				<c:out value="${publishedDate}"></c:out>
			</td>
			<td>${document.department.name}</td>
		</tr>
	</c:forEach>
</table>