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
						CHI TIẾT CÂU HỎI VÀ TRẢ LỜI
					</div>
				</div>
				<table style="font-size: 13px; width: 90%; border-width: 1px;" border="1">
					<tr>
						<td style="width: 15%" class="label"><strong>Tiêu đề câu hỏi</strong></td>
						<td><c:out value="${comment.title}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Người hỏi</strong></td>
						<td><c:out value="${comment.name}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Địa chỉ</strong></td>
						<td><c:out value="${comment.address}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Số điện thoại</strong></td>
						<td><c:out value="${comment.phoneNumber}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Email</strong></td>
						<td><c:out value="${comment.email}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Ngày đặt câu hỏi</strong></td>
						<td>
							<fmt:formatDate var="commentedDate" value="${comment.commentedDate}" pattern="dd/MM/yyyy" />
							<c:out value="${commentedDate}"></c:out>
						</td>
					</tr>
					<tr>
						<td class="label"><strong>Nội dung câu hỏi</strong></td>
						<td>
							<span style="white-space: pre-wrap;"><c:out value="${comment.content}" escapeXml="false"></c:out></span>
						</td>
					</tr>
					<tr>
						<td class="label"><strong>Trạng thái</strong></td>
						<td>
							<c:if test="${comment.status == 0}">Đang chờ trả lời</c:if>
							<c:if test="${comment.status == 1}">Đã trả lời & chưa hiển thị</c:if>
							<c:if test="${comment.status == 2}">Đã trả lời & hiển thị</c:if>
						</td>
					</tr>
					<c:if test="${comment.status != 0}">
						<tr>
							<td class="label"><strong>Người trả lời</strong></td>
							<td>
								<span><c:out value="${comment.answerer}" escapeXml="false"></c:out></span>
							</td>
						</tr>
						<tr>
							<td class="label"><strong>Trả lời</strong></td>
							<td>
								<span><c:out value="${comment.replyContent}" escapeXml="false"></c:out></span>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
	</div>
</body>
</html>