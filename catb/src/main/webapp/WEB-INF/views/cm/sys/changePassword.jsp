<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		ĐỔI MẬT KHẨU
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="changePasswordViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="center">
			<tr>
				<td width="20%">
					<span class="lblBlack">Mật khẩu cũ</span>
				</td>
				<td width="80%">
					<form:password path="oldPassword" id="oldPassword" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td width="20%">
					<span class="lblBlack">Mật khẩu mới</span>
				</td>
				<td width="80%">
					<form:password path="newPassword" id="newPassword" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td width="20%">
					<span class="lblBlack">Xác nhận mật khẩu mới</span>
				</td>
				<td width="80%">
					<form:password path="confirmNewPassword" id="confirmNewPassword" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Đổi mật khẩu" class="button" />
				</td>
			</tr>
		</table>
	</form:form>
</div>