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
			Cập nhật người dùng
		</div>
	</div>
	<div class="content_960">
		<form:form method="post" commandName="updateUserViewModel">
			<form:errors path="*" cssClass="errorblock" element="div" />
			<form:hidden path="id" id="id" />
			<table class="table-form" style="width: 70%;" align="center">
				<tr>
					<td class="label" style="width: 30%;">Tài khoản</td>
					<td class="data txtinput"><form:input path="username" id="username" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Tên</td>
					<td class="data txtinput"><form:input path="name" id="name" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Chức vụ</td>
					<td class="data txtinput"><form:input path="position" id="position" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Email</td>
					<td class="data txtinput"><form:input path="email" id="email" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Số điện thoại</td>
					<td class="data txtinput"><form:input path="phoneNumber" id="phoneNumber" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Quyền</td>
					<td>
						<form:radiobutton path="role" value="0"/> Admin
						<form:radiobutton path="role" value="1"/> Văn thư
						<form:radiobutton path="role" value="2"/> Người dùng
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Phòng</td>
					<td class="data txtinput">
						<form:select path="department">
							<form:option value="-1" label="---- Chọn phòng ----"></form:option>
							<form:options items="${departments}"/>
						</form:select>
					</td>
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