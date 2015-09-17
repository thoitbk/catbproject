<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		DANH MỤC LIÊN KẾT WEB
	</div>
</div>
<div>
	<form:form method="post" commandName="linkCatalogViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table>
			<tr>
				<td width="15%">
					<span class="lblBlack">Tiêu đề</span>
				</td>
				<td width="40%">
					<form:input path="title" id="title" maxlength="500" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td width="30%" style="padding-left: 5%;">
					<span class="lblBlack">Thứ tự</span>
				</td>
				<td>
					<form:input path="sqNumber" id="sqNumber" maxlength="9" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td>
					<span class="lblBlack">Site liên kết</span>
				</td>
				<td>
					<form:input path="linkSite" id="linkSite" maxlength="500" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td style="padding-left: 5%;">
					<span class="lblBlack">Mở trên cửa sổ mới</span>
				</td>
				<td>
					<form:checkbox path="openBlank"/>
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
			<th width="35%">Tiêu đề</th>
			<th width="55%">URL</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${linkCatalogs}" var="linkCatalog">
			<tr>
				<td width="5%">
					<input type="checkbox" name="linkCatalogId" id="linkCatalogId" value="${linkCatalog.id}" class="checkbox" />
				</td>
				<td width="35%"><c:out value="${linkCatalog.title}"></c:out></td>
				<td width="55%"><c:out value="${linkCatalog.linkSite}"></c:out></td>
				<td>
					<a href="${ct}/cm/linkCatalog/update/${linkCatalog.id}">
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="4" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/linkCatalog/delete" id="delLink"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>