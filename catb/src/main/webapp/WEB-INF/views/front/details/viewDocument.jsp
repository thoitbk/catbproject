<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${ct}/resources/css/frontTable.css" rel="stylesheet" type="text/css" />

<div class="titleAdministrative">
	VĂN BẢN PHÁP QUY
</div>

<table class="simple_table" border="1" style="width: 100%;" cellpadding="10">
	<tbody>
		<tr>
			<td width="20%"><b>Số ký hiệu</b></td>
			<td width="80%">${document.code}</td>
		</tr>
		<tr>
			<td><b>Trích yếu</b></td>
			<td>${document.summary}</td>
		</tr>
		<tr>
			<td><b>Loại văn bản</b></td>
			<td>
				${document.documentTypeCatalog.name}
			</td>
		</tr>
		<tr>
			<td><b>Cơ quan ban hành</b></td>
			<td>
				${document.department.name}
			</td>
		</tr>
		<tr>
			<td><b>Người ký</b></td>
			<td>
				${document.leader}
			</td>
		</tr>
		<tr>
			<td><b>Ngày ban hành</b></td>
			<td>
				<fmt:formatDate var="publishedDate" value="${document.publishedDate}" pattern="dd/MM/yyyy" />
				${publishedDate}
			</td>
		</tr>
		<tr>
			<td><b>Có hiệu lực đến</b></td>
			<td>
				<fmt:formatDate var="validDate" value="${document.validDate}" pattern="dd/MM/yyyy" />
				${validDate}
			</td>
		</tr>
		<tr>
			<td><b>Ghi chú</b></td>
			<td><span style="white-space: pre-wrap;">${document.description}</span></td>
		</tr>
		<tr>
			<td><b>Nội dung</b></td>
			<td>${document.content}</td>
		</tr>
		<tr>
			<td><b>File đính kèm</b></td>
			<td>
				<ul class="doc_list">
					<c:forEach items="${document.documentFiles}" var="file">
						<li><a href="${ct}/van-ban-phap-quy/download/${file.id}/${file.name}">${file.name}</a></li>
					</c:forEach>
				</ul>
			</td>
		</tr>
	</tbody>
</table>