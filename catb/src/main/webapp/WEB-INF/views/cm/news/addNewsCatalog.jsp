<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ DANH MỤC TIN TỨC
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="newsCatalogViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="" style="width: 100%">
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Vị trí hiển thị</span>
				</td>
				<td width="30%">
					<form:select path="displayLocation" id="displayLocation" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="" label="------ Chọn vị trí hiển thị ------"></form:option>
						<form:options items="${displayLocations}"/>
					</form:select>
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Danh mục cha</span>
				</td>
				<td>
					<form:select path="parentId" id="parentId" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="-1" label="------ Chọn danh mục cha ------"></form:option>
						<form:options items="${newsCatalogsMap}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td align="left">
					<span id="" class="lblBlack">Tên danh mục tin</span>
				</td>
				<td>
					<form:input path="name" id="name" maxlength="200" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
				<td align="left" style="padding-left: 2%;">
					<span class="lblBlack">Tên hiển thị URL</span>
				</td>
				<td>
					<form:input path="url" id="url" maxlength="200" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Thứ tự</span>
				</td>
				<td>
					<form:input path="sqNumber" id="sqNumber" maxlength="5" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" style="padding-left: 2%;">
					<form:checkbox path="display" />
					<span class="lblBlack">Hiển thị</span>
				</td>
				<td>
					<form:checkbox path="specialSite" />
					<span class="lblBlack">Chuyên trang</span>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" value="Thêm mới" class="button" />
				</td>
			</tr>
		</table>
	</form:form>

	<table class="responstable">
		<tr class="header">
			<th width="5%">
				<input type="checkbox" name="selectAll" id="selectAll">
			</th>
			<th width="45%">Danh mục tin</th>
			<th width="25%">URL hiển thị</th>
			<th width="15%">Thứ tự</th>
			<th width="5%">Hiển thị</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${newsCatalogs}" var="newsCatalog">
			<tr>
				<td width="5%">
					<input type="checkbox" name="newsCatalogId" id="newsCatalogId" value="${newsCatalog.id}" class="checkbox" />
				</td>
				<td width="45%"><c:out value="${newsCatalog.name}"></c:out></td>
				<td width="25%"><c:out value="${newsCatalog.url}"></c:out></td>
				<td width="15%"><c:out value="${newsCatalog.sqNumber}"></c:out></td>
				<td width="5%" style="text-align: center;">
					<c:if test="${newsCatalog.display}">
						<input type="checkbox" checked="checked" disabled="disabled" />
					</c:if>
					<c:if test="${!newsCatalog.display}">
						<input type="checkbox" disabled="disabled" />
					</c:if>
				</td>
				<td>
					<a href="${ct}/cm/newsCatalog/update/${newsCatalog.id}?location=${newsCatalog.displayLocation}">
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="6" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/newsCatalog/delete" id="delNewsCatalog"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>