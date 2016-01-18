<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title>Lịch công tác</title>
	<link href="/resources/images/cm_logo.ico" rel="shortcut icon">
	<style type="text/css">
		body {
		    background: transparent linear-gradient(180deg, #FDE0A5 100%, #FFF 100%);
		    font-family: Arial;
		}
		#banner {
		    width: 100%;
		    height: 150px;
		}
		.content_bold {
			font-size: 14px;
			font-weight: bold;
			color: green;
		}
		.content {
			font-size: 14px;
		}
		th, td { padding: 5px; }

		table { border-collapse: separate; border-spacing: 5px; }
		table { border-collapse: collapse; border-spacing: 0; }

		th, td { vertical-align: top; }

		table { margin: 0 auto; }
	</style>
</head>
<body>
	<div id="banner">
		<img alt="" src="/resources/images/b.png" height="100%" width="100%">
	</div>
	<p></p>
	<div style="text-align: center; font-weight: bold; color: red; text-transform: uppercase;">Lịch công tác phòng ${departmentCode}</div>
	<p></p>
	<div>
		<table border="1" style="width: 100%">
			<tr>
				<td style="width: 20%; text-align: center;"><span class="content_bold">Thời gian</span></td>
				<td style="width: 30%; text-align: center;"><span class="content_bold">Lãnh đạo trực</span></td>
				<td style="width: 50%; text-align: center;"><span class="content_bold">Cán bộ chiến sỹ trực</span></td>
			</tr>
			<tr>
				<td style="width: 20%;"><span class="content">Hôm nay - ${scheduleInfos[offset].date}</span></td>
				<td style="width: 30%;"><span class="content">${scheduleInfos[offset].leader}</span></td>
				<td style="width: 50%;"><span class="content">${scheduleInfos[offset].staffs}</span></td>
			</tr>
		</table>
	</div>
	<p></p>
	<div style="text-align: center; font-weight: bold; color: red;">Lịch công tác tuần</div>
	<div style="height: 700px; overflow: auto;">
		<table border="1" style="width: 100%">
			<tr>
				<td style="width: 20%; text-align: center;"><span class="content_bold">Thời gian</span></td>
				<td style="width: 30%; text-align: center;"><span class="content_bold">Lãnh đạo trực</span></td>
				<td style="width: 50%; text-align: center;"><span class="content_bold">Cán bộ chiến sỹ trực</span></td>
			</tr>
		</table>
		<marquee direction="up" scrolldelay="100" scrollamount="1" width="100%" style="max-height: 400px;">
		<table border="1" style="width: 100%;">
			<c:forEach begin="0" end="6" step="1" var="i">
				<tr>
					<td style="width: 20%;"><span class="content">${scheduleInfos[i].date}</span></td>
					<td style="width: 30%;"><span class="content">${scheduleInfos[i].leader}</span></td>
					<td style="width: 50%;"><span class="content">${scheduleInfos[i].staffs}</span></td>
				</tr>
			</c:forEach>
		</table>
		</marquee>
	</div>
</body>
</html>