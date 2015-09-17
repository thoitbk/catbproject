<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title>Content Management</title>
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
						CHI TIẾT TIN
					</div>
				</div>
				<table style="font-size: 13px;">
					<tr>
						<td style="width: 15%" class="label"><strong>Danh mục tin</strong></td>
						<td><c:out value="${news.newsCatalog.name}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Tiêu đề tin</strong></td>
						<td><c:out value="${news.title}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Ngày đăng tin</strong></td>
						<td>
							<fmt:formatDate var="postedDate" value="${news.postedDate}" pattern="dd/MM/yyyy" />
							<c:out value="${postedDate}"></c:out>
						</td>
					</tr>
					<tr>
						<td class="label"><strong>Tác giả</strong></td>
						<td><c:out value="${news.author}"></c:out></td>
					</tr>
					<tr>
						<td class="label"><strong>Tóm tắt</strong></td>
						<td><c:out value="${news.summary}"></c:out></td>
					</tr>
					<tr>
						<td colspan="2" class="label"><strong>Nội dung tin</strong></td>
					</tr>
					<tr>
						<td colspan="2">
							<span><c:out value="${news.newsContent.content}" escapeXml="false"></c:out></span>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>