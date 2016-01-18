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
						LỊCH CÔNG TÁC - ${userInfo.departmentCode}
					</div>
				</div>
				<table style="font-size: 13px;  width: 100%;" border="1">
					<tr>
						<td class="label" align="center" style="width: 20%; font-weight: bold;">Ngày</td>
						<td class="label" align="center" style="width: 30%; font-weight: bold;">Lãnh đạo trực</td>
						<td class="label" align="center" style="width: 50%; font-weight: bold;">Cán bộ trực</td>
					</tr>
					<c:forEach items="${scheduleInfos}" var="scheduleInfo">
						<tr>
							<td><c:out value="${scheduleInfo.date}"></c:out></td>
							<td><c:out value="${scheduleInfo.leader}"></c:out></td>
							<td><c:out value="${scheduleInfo.staffs}"></c:out></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>