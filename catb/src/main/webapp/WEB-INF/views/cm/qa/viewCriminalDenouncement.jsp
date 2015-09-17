<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title>${comment.title}</title>
	<!-- css -->
	<link href="/resources/css/Admin.css" rel="stylesheet" type="text/css">
	<!-- icon -->
	<link href="/resources/images/cm_logo.ico" rel="shortcut icon">
	<style type="text/css">
		body {
			font-family: sans-serif,Arial,Verdana,"Trebuchet MS";
		}
		.label {
			color: navy;
		}
		
		th, td { padding: 5px; }

		table { border-collapse: separate; border-spacing: 5px; }
		table { border-collapse: collapse; border-spacing: 0; }

		th, td { vertical-align: top; }

		table { margin: 0 auto; }
	</style>
</head>
<body>
	<div id="wrapper">
		<div id="pagecontents" class="main">
			<div id="contentMain">
				<div class="TieuDe">     
					<div class="TieuDe_ND">
						CHI TIẾT TỐ GIÁC TỘI PHẠM
					</div>
				</div>
				<table style="font-size: 13px; width: 90%; border-width: 1px;" border="1">
					<tr>
						<td style="width: 15%" class="label"><strong>Tiêu đề</strong></td>
						<td><c:out value="${criminalDenouncement.title}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Người tố giác</strong></td>
						<td><c:out value="${criminalDenouncement.name}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Địa chỉ</strong></td>
						<td><c:out value="${criminalDenouncement.address}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Số điện thoại</strong></td>
						<td><c:out value="${criminalDenouncement.phoneNumber}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Email</strong></td>
						<td><c:out value="${criminalDenouncement.email}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Ngày tố giác</strong></td>
						<td>
							<fmt:formatDate var="sentDate" value="${criminalDenouncement.sentDate}" pattern="dd/MM/yyyy" />
							<c:out value="${sentDate}"></c:out>
						</td>
					</tr>
					<tr>
						<td class="label"><strong>Nội dung tố giác</strong></td>
						<td>
							<span style="white-space: pre-wrap;"><c:out value="${criminalDenouncement.content}" escapeXml="false"></c:out></span>
						</td>
					</tr>
					<c:if test="${criminalDenouncement.status == 2}">
						<tr>
							<td class="label"><strong>Phản hồi</strong></td>
							<td>
								<span><c:out value="${criminalDenouncement.replyContent}" escapeXml="false"></c:out></span>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
	</div>
</body>
</html>