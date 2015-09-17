<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Xem chi tiết email</title>

<link href="/css/common.css" rel="stylesheet" type="text/css" />
<link href="/css/MVP.css" rel="stylesheet" type="text/css" />
<link href="/css/gridview.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<div class="gridHeader960" style="width: 100%;" align="center">XEM CHI TIẾT EMAIL</div>
	<div class="content-grid">
		<table class="gridView" cellspacing="1">
			<tr>
				<td style="width:20%">Người gửi: </td>
				<td style="width:80%"><c:out value="${mailDetails.from}" escapeXml="true"></c:out></td>
			</tr>
			<tr>
				<td style="width:20%">Người nhận: </td>
				<td style="width:80%"><c:out value="${mailDetails.to}" escapeXml="true"></c:out></td>
			</tr>
			<tr>
				<td style="width:20%">Ngày gửi: </td>
				<td style="width:80%">
					<fmt:formatDate var="sentDate" value="${mailDetails.sentDate}" pattern="dd/MM/yyyy HH:mm" />
					${sentDate}
				</td>
			</tr>
			<tr>
				<td style="width:20%">Tiêu đề: </td>
				<td style="width:80%"><c:out value="${mailDetails.subject}" escapeXml="true"></c:out></td>
			</tr>
			<tr>
				<td style="width:20%">Nội dung: </td>
				<td style="width:80%">${mailDetails.content}</td>
			</tr>
		</table>
		<c:if test="${!empty mailDetails.attachments}">
			<div class="gridHeader960" style="width: 100%;" align="center">FILE ĐÍNH KÈM</div>
			<table class="gridView" cellspacing="1">
				<tr>
					<td style="width:50%" align="center">Tên file</td>
					<td style="width:30%" align="center">Loại file</td>
					<td style="width:20%" align="center">Kích thước</td>
				</tr>
				<c:forEach items="${mailDetails.attachments}" var="attachment">
					<tr>
						<td style="width:50%"><a href="/mail/download.html?uid=${uid}&part=${attachment.part}&fileName=${attachment.fileName}&ct=${attachment.contentType}">${attachment.fileName}</a></td>
						<td style="width:30%" align="center">${attachment.contentType}</td>
						<td style="width:20%" align="center">${attachment.size} KB</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>