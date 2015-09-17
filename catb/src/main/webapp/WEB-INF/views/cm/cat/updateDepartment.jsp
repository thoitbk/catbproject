<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ DANH MỤC PHÒNG BAN
	</div>
</div>
<div>
	<form:form method="post" commandName="departmentViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="center">
			<tr>
				<td width="20%">
					<span class="lblBlack">Mã phòng ban</span>
				</td>
				<td width="80%">
					<form:input path="code" id="code" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>
					<span class="lblBlack">Tên phòng ban</span>
				</td>
				<td>
					<form:input path="name" id="name" maxlength="500" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>
					<span class="lblBlack">Số điện thoại</span>
				</td>
				<td>
					<form:input path="phone" id="phone" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>
					<span class="lblBlack">Fax</span>
				</td>
				<td>
					<form:input path="fax" id="fax" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td valign="middle">
					<span class="lblBlack">Ghi chú</span>
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
			<th width="10%">Mã phòng ban</th>
			<th width="30%">Tên phòng ban</th>
			<th width="15%">Số điện thoại</th>
			<th width="15%">Fax</th>
			<th width="30%">Ghi chú</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${departments}" var="department">
			<tr>
				<td width="5%">
					<input type="checkbox" name="departmentId" id="departmentId" value="${department.id}" class="checkbox" />
				</td>
				<td width="10%"><c:out value="${department.code}"></c:out></td>
				<td width="30%"><c:out value="${department.name}"></c:out></td>
				<td width="15%"><c:out value="${department.phone}"></c:out></td>
				<td width="15%"><c:out value="${department.fax}"></c:out></td>
				<td width="30%"><c:out value="${department.description}"></c:out></td>
				<td>
					<a href="${ct}/cm/department/update/${department.id}">
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="7" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/department/delete" id="delDepartment"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>