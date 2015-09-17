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
			Cập nhật phòng ban
		</div>
	</div>
	<div class="content_960">
		<form:form method="post" commandName="updateDepartmentViewModel">
			<form:errors path="*" cssClass="errorblock" element="div" />
			<form:hidden path="id" id="id" />
			<table class="table-form" style="width: 70%;" align="center">
				<tr>
					<td class="label" style="width: 30%;">Mã phòng</td>
					<td class="data txtinput"><form:input path="code" id="code" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Tên phòng</td>
					<td class="data txtinput"><form:input path="name" id="name" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Số điện thoại</td>
					<td class="data txtinput"><form:input path="phoneNumber" id="phoneNumber" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Email</td>
					<td class="data txtinput"><form:input path="email" id="email" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Thuộc công an tỉnh</td>
					<td>
						<form:checkbox path="inProvince" id="inProvince" />
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Nhóm</td>
					<td class="data txtinput">
						<form:select path="bureau">
							<form:option value="-1" label="---- Chọn nhóm ----"></form:option>
							<form:options items="${bureaus}"/>
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