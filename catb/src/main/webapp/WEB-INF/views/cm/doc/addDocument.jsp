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
		$( "#publishDate" ).datepicker();
		$( "#validDate" ).datepicker();
		$("#clear_1").click(function() {
			$("#publishDate").val('');
		});
		$("#clear_2").click(function() {
			$("#validDate").val('');
		});
	});
</script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ VĂN BẢN QUY PHẠM PHÁP LUẬT
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="documentViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="" style="width: 100%">
			<tr>
				<td align="left" width="10%">
					<span class="lblBlack">Cơ quan ban hành</span>
				</td>
				<td width="40%">
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
				<td align="left" width="10%">
					<span class="lblBlack">Loại văn bản</span>
				</td>
				<td width="40%">
					<form:select path="documentTypeCatalogId" id="documentTypeCatalogId" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="" label="------ Chọn loại văn bản ------"></form:option>
						<form:options items="${documentTypeCatalogMap}"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Ngày ban hành</span>
				</td>
				<td>
					<form:input path="publishDate" id="publishDate" maxlength="20" cssClass="textbox" cssStyle="width: 90%" cssErrorClass="textbox_error" />
					<img src="${ct}/resources/images/clear.png" style="width: 1.2em; height: 1.2em; vertical-align: middle; cursor: pointer;" alt="Xóa" id="clear_1" />
				</td>
				<td align="left">
					<span class="lblBlack">Ngày có hiệu lực</span>
				</td>
				<td>
					<form:input path="validDate" id="validDate" maxlength="20" cssClass="textbox" cssStyle="width: 90%" cssErrorClass="textbox_error" />
					<img src="${ct}/resources/images/clear.png" style="width: 1.2em; height: 1.2em; vertical-align: middle; cursor: pointer;" alt="Xóa" id="clear_2" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Số ký hiệu</span>
				</td>
				<td>
					<form:input path="code" id="code" maxlength="100" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
				<td align="left">
					<span class="lblBlack">Lãnh đạo ký</span>
				</td>
				<td>
					<form:input path="leader" id="leader" maxlength="200" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Trích yếu</span>
				</td>
				<td>
					<form:textarea path="summary" id="summary" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
				</td>
				<td align="left">
					<span class="lblBlack">Ghi chú</span>
				</td>
				<td>
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
							<input id="documentFileUpload" type="file" name="files[]" data-url="${ct}/cm/document/uploadFiles" multiple="multiple" />
						</div>
						<div id="dropzone" class="fade well">
							<span class="lblBlack">Chọn file</span>
						</div>
					</div>
					<div style="margin-left: 20px; margin-top: 17px;" id="progress" class="lblBlack"></div>
				</td>
				<td></td>
				<td align="left">
					<div>
						<select id="uploadedDocumentFiles" name="uploadedDocumentFiles" multiple="multiple" style="width: 200px; height: 100px; float: left;">
							<c:forEach items="${documentFiles}" var="file">
								<option value="${file.id}">${file.fileName}</option>
							</c:forEach>
						</select>
						<div>
							<a href="javascript:void(0);" id="removeDocumentFile"><img src="${ct}/resources/images/remove.png" alt="Xóa file" style="width: 20px; height: 20px" /></a>
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
			<th width="20%">Số ký hiệu</th>
			<th width="50%">Trích yếu</th>
			<th width="20%">Ngày ban hành</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${documents}" var="document">
			<tr>
				<td width="5%">
					<input type="checkbox" name="documentId" id="documentId" value="${document.id}" class="checkbox" />
				</td>
				<td width="20%"><c:out value="${document.code}"></c:out></td>
				<td width="50%"><c:out value="${document.summary}"></c:out></td>
				<td width="20%">
					<fmt:formatDate var="publishDate" value="${document.publishedDate}" pattern="dd/MM/yyyy" />
					<c:out value="${publishDate}"></c:out>
				</td>
				<td>
					<a href='${ct}/cm/document/update/${document.id}'>
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
	    <tr>
	    	<td colspan="5" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/document/delete" id="delDocument"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>
