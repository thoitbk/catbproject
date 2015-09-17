<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>Đăng nhập</title>
	<c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
	<link rel="stylesheet" href="resources/css/base.css">
	<link rel="stylesheet" href="resources/css/layout.css">
	<script type="text/javascript" src="resources/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		document.login.username.focus();
		
		$("#login").submit(function() {
			var username = $("#username").val();
			var password = $("#password").val();
			if (username == '' || password == '') {
				alert('Cần điền đủ username và password');
				return false;
			}
		});
		
		$("#close").click(function() {
			$("#notice").hide();
		});
	});
	</script>
</head>
<body>
	<c:if test="${not empty loginMsg}">
	<div class="notice" id="notice">
		<a href="javascript:void(0);" class="close" id="close">close</a>
		<p class="warn">${loginMsg}</p>
		<c:remove var="loginMsg" scope="session" />
	</div>
	</c:if>
	<div class="container">
		<div class="form-bg">
			<form action="cm/login" method="post" name="login" id="login">
				<h2>Đăng nhập</h2>
				<p><input type="text" name="username" id="username" /></p>
				<p><input type="password" name="password" id="password" /></p>
				<label for="remember">
				  <input type="checkbox" id="rememberMe" name="rememberMe" value="false" />
				  <span>Ghi nhớ đăng nhập</span>
				</label>
				<button type="submit" value="Đăng nhập"></button>
			</form>
		</div>
	</div>
</body>
</html>