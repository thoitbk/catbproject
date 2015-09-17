<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ taglib uri="http://cksource.com/ckfinder" prefix="ckfinder" %>

<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>
<!-- datetime picker -->
<link href="${ct}/resources/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/jquery-ui.js" type="text/javascript"></script>
<script src="${ct}/resources/js/datetimepicker-vi.js" type="text/javascript"></script>
<!-- upload file -->
<link href="${ct}/resources/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ct}/resources/css/dropfilezone.css" type="text/css" rel="stylesheet" />
<script src="${ct}/resources/js/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${ct}/resources/js/jquery.iframe-transport.js" type="text/javascript"></script>
<script src="${ct}/resources/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="${ct}/resources/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ct}/resources/js/cm/uploader.js" type="text/javascript"></script>

<script>
	jQuery(function ($) {
		$( "#publishedDate" ).datepicker();
		$("#clear").click(function() {
			$("#publishedDate").val('');
		})
	});
</script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ THỦ TỤC HÀNH CHÍNH
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="administrativeProcedureViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="" style="width: 100%">
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Cơ quan ban hành</span>
				</td>
				<td width="60%">
					<form:select path="departmentId" id="departmentId" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="" label="------ Chọn cơ quan ban hành ------"></form:option>
						<form:options items="${departmentMap}"/>
					</form:select>
				</td>
				<td align="left" width="10%" style="padding-left: 2%;">
					<span class="lblBlack">Thứ tự</span>
				</td>
				<td>
					<form:input path="sqNumber" id="sqNumber" maxlength="10" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Lĩnh vực</span>
				</td>
				<td width="60%">
					<form:select path="fieldId" id="fieldId" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="" label="------ Chọn lĩnh vực ------"></form:option>
						<form:options items="${fieldMap}"/>
					</form:select>
				</td>
				<td align="left" width="10%" style="padding-left: 2%;">
					<span class="lblBlack">Thời hạn</span>
				</td>
				<td>
					<form:input path="validDuration" id="validDuration" maxlength="100" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span id="" class="lblBlack">Mã TTHC</span>
				</td>
				<td colspan="3">
					<form:input path="code" id="code" maxlength="100" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span id="" class="lblBlack">Tên TTHC</span>
				</td>
				<td colspan="3">
					<form:input path="name" id="name" maxlength="1000" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Ngày ban hành</span>
				</td>
				<td colspan="3">
					<form:input path="publishedDate" id="publishedDate" maxlength="20" cssClass="textbox" cssStyle="width: 50%" cssErrorClass="textbox_error" />
					<img src="${ct}/resources/images/clear.png" style="width: 1.2em; height: 1.2em; vertical-align: middle; cursor: pointer;" alt="Xóa" id="clear" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Ghi chú</span>
				</td>
				<td colspan="3">
					<form:textarea path="description" id="description" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">File đính kèm</span>
				</td>
				<td align="left" width="30%">
					<div id="container" style="float: left;">
						<div id="upload-file-container" class="fade well">
							<input id="administrativeProcedureFileUpload" type="file" name="files[]" data-url="${ct}/cm/administrativeProcedure/uploadFiles" multiple="multiple" />
						</div>
						<div id="dropzone" class="fade well">
							<span class="lblBlack">Chọn file</span>
						</div>
					</div>
					<div style="margin-left: 20px; margin-top: 17px;" id="progress" class="lblBlack"></div>
				</td>
				<td colspan="2">
					<div>
						<select id="uploadedAdministrativeProcedureFiles" name="uploadedAdministrativeProcedureFiles" multiple="multiple" style="width: 200px; height: 100px; float: left;">
							<c:forEach items="${administrativeProcedureFiles}" var="file">
								<option value="${file.id}">${file.fileName}</option>
							</c:forEach>
						</select>
						<div>
							<a href="javascript:void(0);" id="removeAdministrativeProcedureFile"><img src="${ct}/resources/images/remove.png" alt="Xóa file" style="width: 20px; height: 20px" /></a>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<span class="lblBlack">Nội dung</span><br />
					<form:textarea path="content" id="content" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
					<%-- in order to support context path, have to add context path mannually to basePath property --%>
					<ckfinder:setupCKEditor basePath="/ckfinder" />
					<ckeditor:replace replace="content" basePath="/ckeditor" />
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
			<th width="20%">Mã TTHC</th>
			<th width="50%">Tên TTHC</th>
			<th width="20%">Ngày ban hành</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${administrativeProcedures}" var="administrativeProcedure">
			<tr>
				<td width="5%">
					<input type="checkbox" name="administrativeProcedureId" id="administrativeProcedureId" value="${administrativeProcedure.id}" class="checkbox" />
				</td>
				<td width="20%"><c:out value="${administrativeProcedure.code}"></c:out></td>
				<td width="50%"><c:out value="${administrativeProcedure.name}"></c:out></td>
				<td width="20%">
					<fmt:formatDate var="publishedDate" value="${administrativeProcedure.publishedDate}" pattern="dd/MM/yyyy" />
					<c:out value="${publishedDate}"></c:out>
				</td>
				<td>
					<a href='${ct}/cm/administrativeProcedure/update/${administrativeProcedure.id}'>
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="5" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/administrativeProcedure/delete" id="delAdministrativeProcedure"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>
