<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ NGƯỜI DÙNG
	</div>
</div>
<div>
	<form:form method="post" commandName="updateUserViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="" style="width: 100%">
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tên đăng nhập</span>
				</td>
				<td width="30%">
					<form:input path="username" id="username" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Tên đầy đủ</span>
				</td>
				<td>
					<form:input path="fullName" id="fullName" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span id="" class="lblBlack">Password</span>
				</td>
				<td>
					<form:password path="password" id="password" maxlength="100" cssClass="textbox" cssStyle="width: 90%" cssErrorClass="textbox_error" disabled="true" />
					<a href="javascript:void(0);" id="enablePassword"><img src="${ct}/resources/images/enable.png" alt="edit" style="width: 1em; height: 1em; vertical-align: middle" /></a>
				</td>
				<td align="left" style="padding-left: 2%;">
					<span class="lblBlack">Giới tính</span>
				</td>
				<td>
					<form:select path="gender" id="gender" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="-1" label="---- Chọn giới tính ---"></form:option>
						<form:option value="0" label="Nam"></form:option>
						<form:option value="1" label="Nữ"></form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Địa chỉ</span>
				</td>
				<td>
					<form:input path="address" id="address" maxlength="500" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" style="padding-left: 2%;">
					<span class="lblBlack">Điện thoại nhà riêng</span>
				</td>
				<td>
					<form:input path="homePhoneNumber" id="homePhoneNumber" maxlength="50" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Điện thoại di động</span>
				</td>
				<td>
					<form:input path="mobileNumber" id="mobileNumber" maxlength="50" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" style="padding-left: 2%;">
					<span class="lblBlack">Điện thoại cơ quan</span>
				</td>
				<td>
					<form:input path="officePhoneNumber" id="officePhoneNumber" maxlength="50" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Email</span>
				</td>
				<td>
					<form:input path="email" id="email" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" style="padding-left: 2%;">
					<span class="lblBlack">Chức danh</span>
				</td>
				<td>
					<form:select path="position" id="position" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="-1" label="------ Chọn chức danh ------"></form:option>
						<form:options items="${positions}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Phòng ban</span>
				</td>
				<td>
					<form:select path="department" id="department" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="-1" label="------ Chọn phòng ban ------"></form:option>
						<form:options items="${departments}"/>
					</form:select>
				</td>
				<td align="left" style="padding-left: 2%;">
					<span class="lblBlack">Mô tả</span>
				</td>
				<td>
					<form:textarea path="description" id="description" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error" />
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" value="Cập nhật" class="update_button" />
				</td>
			</tr>
		</table>
	</form:form>

	<table class="responstable">
		<tr class="header">
			<th width="5%">
				<input type="checkbox" name="selectAll" id="selectAll">
			</th>
			<th width="10%">Tên đăng nhập</th>
			<th width="30%">Tên đầy đủ</th>
			<th width="15%">Điện thoại di động</th>
			<th width="15%">Điện thoại cơ quan</th>
			<th width="30%">Địa chỉ</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td width="5%">
					<input type="checkbox" name="userId" id="userId" value="${user.id}" class="checkbox" />
				</td>
				<td width="10%"><c:out value="${user.username}"></c:out></td>
				<td width="30%"><c:out value="${user.fullName}"></c:out></td>
				<td width="15%"><c:out value="${user.mobileNumber}"></c:out></td>
				<td width="15%"><c:out value="${user.officePhoneNumber}"></c:out></td>
				<td width="30%"><c:out value="${user.address}"></c:out></td>
				<td>
					<a href="${ct}/cm/user/update/${user.id}">
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="7" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/user/delete" id="delUser"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>