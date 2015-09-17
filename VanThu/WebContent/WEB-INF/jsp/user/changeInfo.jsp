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
			Cập nhật thông tin người dùng
		</div>
	</div>
	<div class="content_960">
		<form:form method="post" commandName="userChangeInfoViewModel">
			<form:errors path="*" cssClass="errorblock" element="div" />
			<table class="table-form" style="width: 70%;" align="center">
				<tr>
					<td class="label" style="width: 30%;">Tài khoản</td>
					<td class="data txtinput"><form:input path="username" id="username" readonly="true" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Tên</td>
					<td class="data txtinput"><form:input path="name" id="name" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Chức vụ</td>
					<td class="data txtinput"><form:input path="position" id="position" readonly="true" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Email</td>
					<td class="data txtinput"><form:input path="email" id="email" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Số điện thoại</td>
					<td class="data txtinput"><form:input path="phoneNumber" id="phoneNumber" /></td>
				</tr>
				<tr class="submit">
					<td colspan="2" align="center">
						<input type="submit" value="Cập nhật" class="SaveButton" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>