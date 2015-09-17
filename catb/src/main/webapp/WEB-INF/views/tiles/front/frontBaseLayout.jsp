<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<head>
	<title>${COMMONINFO.webTitle}</title>
	<!-- css -->
	<link href="${ct}/resources/css/news.css" rel="stylesheet" type="text/css" />
	<link href="${ct}/resources/css/nav.css" rel="stylesheet" type="text/css" />
	<!-- javascript -->
	<script type="text/javascript" src="${ct}/resources/js/menus.js" ></script>
	<script type="text/javascript" src="${ct}/resources/js/box.js"></script>
	<script type="text/javascript" src="${ct}/resources/js/jquery-1.9.1.min.js"></script>
	<%-- <script type="text/javascript" src="${ct}/resources/js/jquery-ui-1.8.18.custom.min.js"></script> --%>
	<script src="${ct}/resources/js/jquery-ui-tab.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ct}/resources/js/news.js"></script>
	
	<link href="${ct}/resources/images/ca_logo.png" rel="shortcut icon" />
</head>
<body>
	<div id="wrapper">
		<div id="top">
			<jsp:include page="top.jsp"></jsp:include>
		</div>
		<div id="pagecontents">
			<div class="contentLeft">
				<tiles:insertAttribute name="left_high" />
				<tiles:insertAttribute name="left_low" />
			</div>
			<div class="contentRight">
				<jsp:include page="right.jsp"></jsp:include>
			</div>
		</div>
		<div id="mainfooter">
			<jsp:include page="bottom.jsp"></jsp:include>
		</div>
		<a href="#" class="back-to-top">Về đầu trang</a>
	</div>
</body>
</html>