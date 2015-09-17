<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ QUYỀN
	</div>
</div>
<div>
	<form:form method="post" commandName="permissionViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="center">
			<tr>
				<td width="20%">
					<span class="lblBlack">Tên quyền</span>
				</td>
				<td width="80%">
					<form:input path="name" id="name" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td width="20%">
					<span class="lblBlack">Chuỗi quyền</span>
				</td>
				<td width="80%">
					<form:input path="perStr" id="perStr" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td valign="middle">
					<span class="lblBlack">Mô tả</span>
				</td>
				<td>
					<form:textarea path="description" id="description" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error" />
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
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
			<th width="25%">Tên quyền</th>
			<th width="20%">Chuỗi quyền</th>
			<th width="40%">Mô tả</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${permissions}" var="permission">
			<tr>
				<td width="5%">
					<input type="checkbox" name="permissionId" id="permissionId" value="${permission.id}" class="checkbox" />
				</td>
				<td width="25%"><c:out value="${permission.name}"></c:out></td>
				<td width="20%"><c:out value="${permission.perStr}"></c:out></td>
				<td width="40%"><c:out value="${permission.description}"></c:out></td>
				<td>
					<a href="${ct}/cm/permission/update/${permission.id}">
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="5" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/permission/delete" id="delPermission"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>