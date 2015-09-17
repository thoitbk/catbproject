<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		PHÂN QUYỀN NHÓM NGƯỜI DÙNG
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<table class="center">
		<tr>
			<td width="80%">
				<span class="lblBlack">Vai trò</span>
			</td>
			<td>
				<select name="role" id="role" class="combobox">
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
			<td>
				<input type="button" id="updatePerOfRole" value="Cập nhật" class="button" style="margin-left: 100px;" />
			</td>
		</tr>
	</table>

	<table class="responstable">
		<tr class="header">
			<th width="80%">Danh sách các quyền</th>
			<th>Cấp phép <br /><input type="checkbox" name="selectAll" id="selectAll"></th>
		</tr>
		<c:forEach items="${permissionInfos}" var="permissionInfo">
			<tr>
				<td width="80%" style="text-align: left;">
					<c:out value="${permissionInfo.permission.name}"></c:out>
				</td>
				<td style="text-align: center;">
					<c:if test="${permissionInfo.granted}">
						<input type="checkbox" name="granted" id="granted" class="checkbox" value="${permissionInfo.permission.id}" checked="checked" />
					</c:if>
					<c:if test="${!permissionInfo.granted}">
						<input type="checkbox" name="granted" id="granted" class="checkbox" value="${permissionInfo.permission.id}" />
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>