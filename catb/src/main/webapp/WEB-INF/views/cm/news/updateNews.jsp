<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ taglib uri="http://cksource.com/ckfinder" prefix="ckfinder" %>

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
<!-- image viewer fancy box -->
<link href="${ct}/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" media="screen" />
<script src="${ct}/fancybox/jquery.mousewheel-3.0.4.pack.js" type="text/javascript"></script>
<script src="${ct}/fancybox/jquery.fancybox-1.3.4.pack.js" type="text/javascript"></script>

<script>
	jQuery(function ($) {
		$( "#postedDate" ).datepicker();
		$("#clear").click(function() {
			$("#postedDate").val('');
		})
		$("a#thumbImage").fancybox({
			'overlayShow'	: false,
			'transitionIn'	: 'elastic',
			'transitionOut'	: 'elastic'
		});
	});
</script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		CẬP NHẬT TIN TỨC
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="newsViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="" style="width: 100%">
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Danh mục tin</span>
				</td>
				<td width="60%">
					<form:select path="newsCatalogId" id="newsCatalogId" cssStyle="width: 100%;" cssClass="combobox">
						<form:option value="" label="------ Chọn danh mục tin ------"></form:option>
						<form:options items="${newsCatalogs}"/>
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
				<td align="left">
					<span id="" class="lblBlack">Tiêu đề tin</span>
				</td>
				<td colspan="3">
					<form:input path="title" id="title" maxlength="1000" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Tóm tắt</span>
				</td>
				<td colspan="3">
					<form:textarea path="summary" id="summary" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
				</td>
			</tr>
			<tr>
				<td align="left">
					<span id="" class="lblBlack">Tác giả</span>
				</td>
				<td colspan="3">
					<form:input path="author" id="author" maxlength="200" cssClass="textbox" cssStyle="width: 100%" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Ngày đăng tin</span>
				</td>
				<td colspan="3">
					<form:input path="postedDate" id="postedDate" maxlength="20" cssClass="textbox" cssStyle="width: 50%" cssErrorClass="textbox_error" />
					<img src="${ct}/resources/images/clear.png" style="width: 1.2em; height: 1.2em; vertical-align: middle; cursor: pointer;" alt="Xóa" id="clear" />
				</td>
			</tr>
			<tr>
				<td align="left">
					<span class="lblBlack">Tin nóng</span>
				</td>
				<td colspan="3">
					<form:checkbox path="hotNews" id="hotNews" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Ảnh đại diện</span>
				</td>
				<td align="left" width="30%">
					<div id="container" style="float: left;">
						<div id="upload-file-container" class="fade well">
							<input id="newsImageUpload" type="file" name="files[]" data-url="${ct}/cm/news/uploadNewsImage" />
						</div>
						<div id="dropzone" class="fade well">
							<span class="lblBlack">Chọn file</span>
						</div>
					</div>
					<div style="margin-left: 20px; margin-top: 17px;" id="progress" class="lblBlack"></div>
				</td>
				<td colspan="2">
					<div id="imageBox">
						<div id="uploadedImage" style="height: 50px; vertical-align: middle; text-align: left; float: left;">
							<c:if test="${newsImage != null}">
								<a href="${newsImage.path}" id="thumbImage"><img src="${newsImage.path}" alt="Ảnh đại diện" style="max-height: 100%; max-width: 100%;" class="thumb" /></a>
							</c:if>
						</div>
						<div id="removeIcon">
							<c:if test="${newsImage != null}">
								<a href="javascript:void(0);" id="removeNewsImage"><img src="${ct}/resources/images/remove.png" alt="Xóa ảnh" style="width: 20px; height: 20px" /></a>
							</c:if>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<span class="lblBlack">Nội dung bài viết</span><br />
					<form:textarea path="content" id="content" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
					<%-- in order to support context path, have to add context path mannually to basePath property --%>
					<ckfinder:setupCKEditor basePath="/ckfinder" />
					<ckeditor:replace replace="content" basePath="/ckeditor" />
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" value="Cập nhật" class="update_button" />
				</td>
			</tr>
		</table>
	</form:form>
</div>