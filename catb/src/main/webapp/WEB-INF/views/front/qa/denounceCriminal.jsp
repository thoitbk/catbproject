<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link href="${ct}/resources/css/notification.css" rel="stylesheet" type="text/css" />
<link href="${ct}/resources/css/form.css" rel="stylesheet" type="text/css" />

<script src="${ct}/resources/js/anim.js" type="text/javascript"></script>

<div id="Tin_Chi_Tiet">
	<div class="DanhMuc">
		<strong>TỐ GIÁC TỘI PHẠM</strong>
	</div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="createCriminalDenouncementViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table style="width: 90%; font-size: 12px; border-spacing: 0 5px; margin-top: 20px;" align="center">
			<tr>
				<td>Tên</td>
				<td>
					<form:input path="name" id="name" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>Địa chỉ</td>
				<td>
					<form:input path="address" id="address" maxlength="500" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>Số điện thoại</td>
				<td>
					<form:input path="phoneNumber" id="phoneNumber" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>Email</td>
				<td>
					<form:input path="email" id="email" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>Tiêu đề</td>
				<td>
					<form:input path="title" id="title" maxlength="1000" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>Nội dung</td>
				<td>
					<form:textarea path="content" id="content" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
				</td>
			</tr>
			<tr>
				<td>Mã xác nhận</td>
				<td>
					<img alt="" src="${ct}/cd-captcha-generator" style="float: left;" id="captcha">
					<a href="javascript:void(0);" id="refresh_captcha"><img alt="Xem mã khác" src="${ct}/resources/images/refresh.png" style="width: 20px; height: 20px; margin-left: 10px;"></a>
				</td>
			</tr>
			<tr>
				<td>Nhập mã xác nhận</td>
				<td>
					<form:input path="captcha" id="captcha_confirm" maxlength="10" cssClass="textbox" cssStyle="width: 200px;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width: 10px;">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Gửi" class="button" id="send_denouncement" />
				</td>
			</tr>
		</table>
	</form:form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('#refresh_captcha').click(function() {
			$('#captcha').attr('src', '/cd-captcha-generator?q=' + Math.random());
		});
	});
	
	$('#send_denouncement').click(function() {
		var title = $('#title').val();
		var name = $('#name').val();
		var content = $('#content').val();
		var captcha_confirm = $('#captcha_confirm').val();
		var error = false;
		if (title == null || title == '') {
			$('#title').addClass('textbox_error');
			error = true;
		} else {
			$('#title').removeClass('textbox_error');
		}
		if (name == null || name == '') {
			$('#name').addClass('textbox_error');
			error = true;
		} else {
			$('#name').removeClass('textbox_error');
		}
		if (content == null || content == '') {
			$('#content').addClass('textmulti_error');
			error = true;
		} else {
			$('#content').removeClass('textmulti_error');
		}
		if (captcha_confirm == null || captcha_confirm == '') {
			$('#captcha_confirm').addClass('textbox_error');
			error = true;
		} else {
			$('#captcha_confirm').removeClass('textbox_error');
		}
		if (error) {
			alert('Điền thiếu thông tin');
			return false;
		}
	});
</script>