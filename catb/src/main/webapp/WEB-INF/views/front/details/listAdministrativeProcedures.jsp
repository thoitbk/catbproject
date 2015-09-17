<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld"%>

<link href="${ct}/resources/css/frontTable.css" rel="stylesheet" type="text/css" />

<div class="titleAdministrative">
	HƯỚNG DẪN THỦ TỤC HÀNH CHÍNH
</div>

<table class="responstable">
	<tr class="header">
		<th width="5%">STT</th>
		<th width="65%">Tiêu đề</th>
		<th width="30%">Đơn vị thực hiện</th>
	</tr>
	<c:forEach items="${administrativeProcedures}" var="administrativeProcedure" varStatus="s">
		<tr>
			<td width="5%">
				<c:out value="${s.index + 1}" />
			</td>
			<td width="65%">
				<a class="procedure_link" href="${ct}/thu-tuc-hanh-chinh/${administrativeProcedure.id}/${f:toFriendlyUrl(administrativeProcedure.name)}">${administrativeProcedure.name}</a>
			</td>
			<td width="30%"><c:out value="${administrativeProcedure.department.name}"></c:out></td>
		</tr>
	</c:forEach>
</table>