<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		THAY ĐỔI THÔNG TIN
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="editUserViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="" style="width: 100%">
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tài khoản</span>
				</td>
				<td width="30%">
					<form:input path="username" id="username" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Tên</span>
				</td>
				<td>
					<form:input path="fullName" id="fullName" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Số di động</span>
				</td>
				<td width="30%">
					<form:input path="mobileNumber" id="mobileNumber" maxlength="50" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Số điện thoại phòng</span>
				</td>
				<td>
					<form:input path="officeNumber" id="officeNumber" maxlength="50" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Số điện thoại nhà</span>
				</td>
				<td width="30%">
					<form:input path="homeNumber" id="homeNumber" maxlength="50" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Địa chỉ</span>
				</td>
				<td>
					<form:input path="address" id="address" maxlength="500" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Email</span>
				</td>
				<td width="30%">
					<form:input path="email" id="email" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Mật khẩu</span>
				</td>
				<td><span class="lblBlack">********</span> <a href="${ct}/cm/user/changePassword"><img alt="Đổi mật khẩu" src="${ct}/resources/images/changePassword.png" style="width: 30px; height: 30px;"></a></td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" value="Cập nhật" class="button" />
				</td>
			</tr>
		</table>
	</form:form>
</div>