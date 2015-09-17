<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function() {
		document.loginForm.j_username.focus();
		
		$("#loginForm").submit(function() {
			var username = $("#j_username").val();
			var password = $("#j_password").val();
			if (username == '' || password == '') {
				alert('Cần điền đủ username và password');
				return false;
			}
		});
	});
</script>
<div class="box_left" align="center" style="margin-top: 100px">
	<c:if test="${not empty error}">
		<div class="errorblock_login">
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
	<div class="title_left">
		<span class="text-DS">ĐĂNG NHẬP HỆ THỐNG</span>
	</div>
	<div class="out_box_317">
		<form name='loginForm' id="loginForm" action="j_spring_security_check" method='POST'>
			<div class="lbl_login">
				<div class="label">
					<span>Tên người dùng:</span>
				</div>
				<div class="textbox">
					<input class="form-login required" type="text" name='j_username' id="j_username" value=''>
				</div>
			</div>
			<div class="lbl_login">
				<div class="label">
					<span>Mật khẩu:</span>
				</div>
				<div class="textbox">
					<input class="form-login required" type="password" name='j_password' id="j_password">
				</div>
			</div>
			<div class="checkghinho">
				<input type="checkbox"  name="_spring_security_remember_me" id="_spring_security_remember_me"> Ghi nhớ mật khẩu
			</div>
			<div class="btn_dangnhap">
				<span> <input src="images/btnDangNhapTranspan.png"
					style="height: 30px; width: 103px;" type="image">
				</span>
			</div>
			<div class="bottom_dn"></div>
		</form>
	</div>
	<!-- Het out_box_317 -->
</div>
