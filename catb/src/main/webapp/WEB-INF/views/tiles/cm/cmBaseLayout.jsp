<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<head id="Head1">
	<title>Content Management</title>
	<!-- css -->
	<link href="${ct}/resources/css/Admin.css" rel="stylesheet" type="text/css" />
	<link href="${ct}/resources/css/accordion.css" rel="stylesheet" type="text/css" />
	<link href="${ct}/resources/css/notification.css" rel="stylesheet" type="text/css" />
	<link href="${ct}/resources/css/anim.css" rel="stylesheet" type="text/css" />
	<!-- javascript -->
	<script src="${ct}/resources/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script src="${ct}/resources/js/accordion.js" type="text/javascript"></script>
	<script src="${ct}/resources/js/cm/cm.js" type="text/javascript"></script>
	<!-- icon -->
	<link href="${ct}/resources/images/cm_logo.ico" rel="shortcut icon" />
</head>
<body>
	<div id="wrapper">
		<div id="top">
			<div ID="Banner" style="margin: 0px auto; width: 100%; height: 133px;">
				<img alt="" src="${ct}/resources/images/b.png" width="100%" height="100%" />
			</div>
		</div>
		<div id="pagecontents" class="main">
			<div id="contentMain">
				<div class="admincontentLeft">
					<%@include file="left.jsp" %>
				</div>
				<div class="admincontentRight">
					<div id="TopInfo">
						<div class="InfoLeft">
							<span id="ngay"></span>
						</div>
						<div class="InfoRight">
							<span>Xin chào: ${userInfo.fullName}</span>
							<a href="${ct}/cm/user/edit"><img border="0px" alt="Thông tin người sử dụng" src="${ct}/resources/images/user_info.png" width="30px" /></a>
							<a href="${ct}/cm/logout"><img border="0px" alt="Logout"  src="${ct}/resources/images/logout.png" width="30px" /></a>        
						</div>
					</div>
					<tiles:insertAttribute name="content" />
				</div>
			</div>
		</div>
		<div id="adminfooter">
			<jsp:include page="bottom.jsp"></jsp:include>
		</div>
		<a href="#" class="back-to-top">Về đầu trang</a>
	</div>
	<div class="modal"></div>
</body>
</html>