<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="box960">
	<div class="title">
		<div class="left"></div>
		<div class="right"></div>
		<div class="text_form" style="width: 320px;">
			<img src="/images/left_title.png" style="float: left"> 
			<img src="/images/right_title.png" style="float: right">
			Đổi mật khẩu
		</div>
	</div>
	<div class="content_960">
		<form:form method="post" commandName="userChangePasswordViewModel">
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table class="table-form" style="width: 70%;" align="center">
				<tr>
					<td class="label" style="width: 30%;">Mật khẩu cũ</td>
					<td class="data txtinput"><form:password path="oldPassword" id="oldPassword" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Mật khẩu mới</td>
					<td class="data txtinput"><form:password path="newPassword" id="newPassword" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Xác nhận mật khẩu mới</td>
					<td class="data txtinput"><form:password path="confirmNewPassword" id="confirmNewPassword" /></td>
				</tr>
				<tr  class="submit">
					<td colspan="2" align="center"><input type="submit" value="Đổi mật khẩu" class="SaveButton" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</div>