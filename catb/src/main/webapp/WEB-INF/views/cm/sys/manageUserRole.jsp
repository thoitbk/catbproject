<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		PHÂN QUYỀN NGƯỜI DÙNG
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<table style="width: 100%">
		<tr>
			<td style="width: 40%;" align="center">
				<span class="lblBlack">Vai trò</span>
			</td>
			<td colspan="2">
				<select name="roleId" id="roleId" class="combobox">
					<option value="-1">--- Chọn vai trò ---</option>
					<c:forEach items="${roleMap}" var="r">
						<c:if test="${r.key == param.id}">
							<option value="${r.key}" selected="selected">${r.value}</option>
						</c:if>
						<c:if test="${r.key != param.id}">
							<option value="${r.key}">${r.value}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td><span class="lblBlack">Danh sách người dùng</span></td>
			<td></td>
			<td><span class="lblBlack">Danh sách người dùng đã chọn</span></td>
		</tr>
		<tr>
			<td style="width: 40%;">
				<select name="unAssigned" id="unAssigned" multiple="multiple" style="height: 330px; width: 100%;" class="listbox">
					<c:forEach items="${notAssignedUsers}" var="u">
						<option value="${u.id}">${u.username} (${u.fullName})</option>
					</c:forEach>
				</select>
			</td>
			<td style="width: 20%;" align="center" valign="middle">
				<input type="button" value="Thêm vào" name="assignRole" id="assignRole" class="normal_button" />
				<div style="height: 20px;"></div>
				<input type="button" value="Bớt đi" name="revokeRole" id="revokeRole" class="normal_button" />
			</td>
			<td style="width: 40%;">
				<select name="assigned" id="assigned" multiple="multiple" style="height: 330px; width: 100%;" class="listbox">
					<c:forEach items="${assignedUsers}" var="u">
						<option value="${u.id}">${u.username} (${u.fullName})</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>
</div>