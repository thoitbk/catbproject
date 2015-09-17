<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<!-- CSS -->
<link href="/css/common.css" rel="stylesheet" type="text/css" />
<link href="/css/MVP.css" rel="stylesheet" type="text/css" />
<!-- <link href="/css/jquery-ui-1.css" rel="stylesheet" type="text/css" /> -->
<link href="/css/gridview.css" rel="stylesheet" type="text/css" />
<link href="/css/fileuploader.css" rel="stylesheet" type="text/css" />
<link href="/css/jquery.css" rel="stylesheet" type="text/css" />
<link href="/css/jquery_003.css" rel="stylesheet" type="text/css" />
<link href="/css/ucpaging.css" rel="stylesheet" type="text/css" />
<link href="/css/jquery_002.css" rel="stylesheet" type="text/css" />
<link href="/css/splitter.css" rel="stylesheet" type="text/css" />
<link href="/css/paging.css" rel="stylesheet" type="text/css" />
<link href="/css/message.css" rel="stylesheet" type="text/css" />
<!--endCSS -->

<!-- jQuery -->
<script src="/js/jquery-1.js" type="text/javascript"></script>
<script src="/js/Common.js" type="text/javascript"></script>
<script src="/js/jquery-ui-1.js" type="text/javascript"></script>
<script src="/js/splitter.js" type="text/javascript"></script>
<script src="/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<script src="/js/jquery_002.js" type="text/javascript"></script>
<!--Textsuggest-->
<script src="/js/jquery_004.js" type="text/javascript"></script>
<script src="/js/jquery_006.js" type="text/javascript"></script>
<!--endTextsuggest-->
<!-- end jQuery -->

<!-- Other -->
<script src="/js/Utils.js" type="text/javascript"></script>
<script src="/js/gridview.js" type="text/javascript"></script>
<script src="/js/DateValidation.js" type="text/javascript"></script>
<script src="/js/jquery_003.js" type="text/javascript"></script>
<script src="/js/ucpaging.js" type="text/javascript"></script>
<script src="/js/jquery_005.js" type="text/javascript"></script>
<script src="/js/shortcut.js" type="text/javascript"></script>
<script src="/js/fileuploader.js" type="text/javascript"></script>
<script src="/js/notify.js" type="text/javascript"></script>
<!-- end Other -->

<!-- Ribbon -->
<script src="/js/jquery.js" type="text/javascript"></script>
<script src="/js/menu.js" type="text/javascript"></script>
<!-- end Ribbon -->

</head>
<body>
	<div id="wapper">
		<div id="Top">
			<div id="Topmenu">
				<c:choose>
					<c:when test="${f:getUserInfo(pageContext.session).role == 'NORMAL_USER'}">
						<%@ include file="../tiles/normalUserMenu.jsp" %>
					</c:when>
					<c:when test="${f:getUserInfo(pageContext.session).role == 'VAN_THU'}">
						<%@ include file="../tiles/vanThuMenu.jsp" %>
					</c:when>
					<c:when test="${f:getUserInfo(pageContext.session).role == 'ADMIN'}">
						<%@ include file="../tiles/adminMenu.jsp" %>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<div class="DangNhap">
					<div class="Thungay">
						<span id="ngay"> <span></span></span>
					</div>
					<div class="DonVi">
						<span>${sessionScope.userInfo.departmentName}</span>
					</div>
					<div class="nguoidung">
						Họ và tên:<span id="ten"> <span>${sessionScope.userInfo.name}</span></span>
					</div>
					<div class="info-logout">
						<a href="j_spring_security_logout"><img src="/images/Logout.gif"></a>
					</div>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
		<div id="Content">
			<div id="Bottom">
				<div class="bottom">
				</div>
				<%@ include file="footer.jsp" %>
			</div>
		</div>
	</div>
</body>
</html>