<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thông tin văn bản</title>

<link href="/css/common.css" rel="stylesheet" type="text/css" />
<link href="/css/MVP.css" rel="stylesheet" type="text/css" />
<link href="/css/gridview.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<div class="gridHeader960" style="width: 100%;" align="center">THÔNG TIN VĂN BẢN ĐẾN</div>
	<div class="content-grid">
		<table class="gridView" cellspacing="1">
			<tr>
				<td style="width:20%">Số đến</td>
				<td style="width:30%" align="center">${document.number}</td>
				<td style="width:20%">Số ký hiệu</td>
				<td style="width:30%" align="center">${document.sign}</td>
			</tr>
			<tr>
				<td style="width:20%">Loại văn bản</td>
				<td colspan="3" style="width:30%" align="center">${document.documentType.typeName}</td>
			</tr>
			<tr>
				<td style="width:20%">Độ mật</td>
				<td style="width:30%" align="center">
					<c:forEach items="${confidentialLevels}" var="c">
						<c:if test="${c.key == document.confidentialLevel}">${c.value}</c:if>
					</c:forEach>
				</td>
				<td style="width:20%">Độ khẩn</td>
				<td style="width:30%" align="center">
					<c:forEach items="${urgentLevels}" var="u">
						<c:if test="${u.key == document.urgentLevel}">${u.value}</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="width:20%">Ngày ban hành</td>
				<td style="width:30%" align="center">
					<fmt:formatDate var="publishDate" value="${document.publishDate}" pattern="dd/MM/yyyy" />
					${publishDate}
				</td>
				<td style="width:20%">Ngày nhận</td>
				<td style="width:30%" align="center">
					<fmt:formatDate var="receiveDate" value="${document.receiveDate}" pattern="dd/MM/yyyy" />
					${receiveDate}
				</td>
			</tr>
			<tr>
				<td style="width:20%">Đơn vị ban hành</td>
				<td colspan="3">
					<c:set var="len" value="${fn:length(document.sentDepartments)}"></c:set>
					<c:forEach items="${document.sentDepartments}" var="d" varStatus="s">
						<c:if test="${s.count == len}">
							${d.code}
						</c:if>
						<c:if test="${s.count != len}">
							${d.code}, 
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="width:20%" valign="middle">Trích yếu</td>
				<td colspan="3">${document.abs}</td>
			</tr>
			<tr>
				<td style="width:20%" valign="middle">File văn bản</td>
				<td colspan="3">
					<c:forEach items="${document.comingDocumentFiles}" var="file">
						<a href="/comingDocument/downloadDocument.html?f=${file.id}&d=${document.id}">${file.name}</a><br />
					</c:forEach>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>