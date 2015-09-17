<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ taglib uri="http://cksource.com/ckfinder" prefix="ckfinder" %>

<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		TRẢ LỜI CÂU HỎI
	</div>
</div>
<div>
	<form:form method="post" commandName="replyCommentViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table style="width: 100%">
			<tr>
				<td width="20%">
					<span class="lblBlack">Tiêu đề câu hỏi</span>
				</td>
				<td width="80%">
					<form:input path="title" id="title" maxlength="1000" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td width="20%">
					<span class="lblBlack">Tên người hỏi</span>
				</td>
				<td width="80%">
					<form:input path="name" id="name" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td width="20%">
					<span class="lblBlack">Lĩnh vực</span>
				</td>
				<td width="60%">
					<form:select path="qaCatalogId" id="qaCatalogId" cssStyle="width: 100%;" cssClass="combobox">
						<form:options items="${qaCatalogMap}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td valign="middle">
					<span class="lblBlack">Câu hỏi</span>
				</td>
				<td>
					<form:textarea path="content" id="content" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error" />
				</td>
			</tr>
			<tr>
				<td valign="middle">
					<span class="lblBlack">Hiển thị trả lời</span>
				</td>
				<td>
					<form:checkbox path="show" id="show" />
				</td>
			</tr>
			<tr>
				<td width="20%">
					<span class="lblBlack">Người trả lời</span>
				</td>
				<td width="80%">
					<form:input path="answerer" id="answerer" maxlength="200" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span class="lblBlack">Trả lời</span><br />
					<form:textarea path="replyContent" id="replyContent" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
					<%-- in order to support context path, have to add context path mannually to basePath property --%>
					<ckfinder:setupCKEditor basePath="/ckfinder" />
					<ckeditor:replace replace="replyContent" basePath="/ckeditor" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Trả lời" class="button" />
				</td>
			</tr>
		</table>
	</form:form>
</div>