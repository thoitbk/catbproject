<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách văn bản đến tìm được</title>

<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
</style>

</head>
<body>
<div align="center"><h3>DANH SÁCH VĂN BẢN ĐẾN TÌM THẤY</h3></div>
<br />
<table width="100%">
	<tr>
		<th style="width:5%" align="center">STT</th>
		<th style="width:5%" align="center">Số đến</th>
		<th style="width:10%" align="center">Số ký hiệu</th>
		<th style="width:50%" align="center">Trích yếu</th>
		<th style="width:20%" align="center">Nơi gửi</th>
		<th style="width:10%" align="center">Ngày ban hành</th>
	</tr>
	<c:forEach items="${documents}" var="document" varStatus="s">
		<tr>
			<td align="center">${s.count}</td>
			<td align="center">${document.number}</td>
			<td>${document.sign}</td>
			<td>${document.abs}</td>
			<td>
				<c:set var="departments" value="${document.sentDepartments}" scope="page"></c:set>
				<c:forEach items="${departments}" var="department" varStatus="s">
					${department.code}
					<c:if test="${fn:length(departments) > s.count}">
						<c:out value=","></c:out>
					</c:if>
				</c:forEach>
			</td>
			<td>
				<fmt:formatDate var="publishDate" value="${document.publishDate}" pattern="dd/MM/yyyy" />
				${publishDate}
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>