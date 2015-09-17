<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ DANH MỤC HỎI ĐÁP
	</div>
</div>
<div>
	<form:form method="post" commandName="qaCatalogViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="center">
			<tr>
				<td width="20%">
					<span class="lblBlack">Tên danh mục</span>
				</td>
				<td width="80%">
					<form:input path="name" id="name" maxlength="300" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td valign="middle">
					<span class="lblBlack">Mô tả chi tiết</span>
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
			<th width="40%">Tên danh mục</th>
			<th width="50%">Ghi chú</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${qaCatalogs}" var="qaCatalog">
			<tr>
				<td width="5%">
					<input type="checkbox" name="qaCatalogId" id="qaCatalogId" value="${qaCatalog.id}" class="checkbox" />
				</td>
				<td width="40%"><c:out value="${qaCatalog.name}"></c:out></td>
				<td width="50%"><c:out value="${qaCatalog.description}"></c:out></td>
				<td>
					<a href="${ct}/cm/qaCatalog/update/${qaCatalog.id}">
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="5" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/qaCatalog/delete" id="delQACatalog"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>