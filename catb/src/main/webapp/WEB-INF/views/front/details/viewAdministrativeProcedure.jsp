<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${ct}/resources/css/frontTable.css" rel="stylesheet" type="text/css" />

<div class="titleAdministrative">
	HƯỚNG DẪN THỦ TỤC HÀNH CHÍNH
</div>

<table class="simple_table" border="1" style="width: 100%;" cellpadding="10">
	<tbody>
		<tr>
			<td width="20%"><b>Mã thủ tục</b></td>
			<td width="80%">${administrativeProcedure.code}</td>
		</tr>
		<tr>
			<td><b>Tên thủ tục</b></td>
			<td>${administrativeProcedure.name}</td>
		</tr>
		<tr>
			<td><b>Cơ quan ban hành</b></td>
			<td>
				${administrativeProcedure.department.name}
			</td>
		</tr>
		<tr>
			<td><b>Lĩnh vực</b></td>
			<td>${administrativeProcedure.field.name}</td>
		</tr>
		<tr>
			<td><b>Ngày ban hành</b></td>
			<td>
				<fmt:formatDate var="publishedDate" value="${administrativeProcedure.publishedDate}" pattern="dd/MM/yyyy" />
				${publishedDate}
			</td>
		</tr>
		<tr>
			<td><b>Thời gian có hiệu lực</b></td>
			<td>${administrativeProcedure.validDuration}</td>
		</tr>
		<tr>
			<td><b>Ghi chú</b></td>
			<td><span style="white-space: pre-wrap;">${administrativeProcedure.description}</span></td>
		</tr>
		<tr>
			<td><b>Nội dung</b></td>
			<td>${administrativeProcedure.content}</td>
		</tr>
		<tr>
			<td><b>File đính kèm</b></td>
			<td>
				<ul class="doc_list">
					<c:forEach items="${administrativeProcedure.administrativeProcedureFiles}" var="file">
						<li><a href="${ct}/thu-tuc-hanh-chinh/download/${file.id}/${file.name}">${file.name}</a></li>
					</c:forEach>
				</ul>
			</td>
		</tr>
	</tbody>
</table>