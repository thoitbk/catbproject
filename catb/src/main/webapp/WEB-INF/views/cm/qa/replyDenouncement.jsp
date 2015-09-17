<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ taglib uri="http://cksource.com/ckfinder" prefix="ckfinder" %>

<style type="text/css">
	body {
		font-family: sans-serif,Arial,Verdana,"Trebuchet MS";
	}
	.label {
		color: navy;
	}
	
	th, td { padding: 5px; }

	table { border-collapse: separate; border-spacing: 5px; }
	table { border-collapse: collapse; border-spacing: 0; }

	th, td { vertical-align: top; }

	table { margin: 0 auto; }
</style>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		GỬI MAIL PHẢN HỒI TỐ GIÁC
	</div>
</div>
<div>
	<table style="font-size: 13px; width: 100%; border-width: 1px;" border="1">
		<tr>
			<td width="15%">
				<span class="lblBlack"><strong>Tiêu đề</strong></span>
			</td>
			<td width="75%">${criminalDenouncement.title}</td>
		</tr>
		<tr>
			<td>
				<span class="lblBlack"><strong>Người tố giác</strong></span>
			</td>
			<td>${criminalDenouncement.name}</td>
		</tr>
		<tr>
			<td>
				<span class="lblBlack"><strong>Địa chỉ</strong></span>
			</td>
			<td>${criminalDenouncement.address}</td>
		</tr>
		<tr>
			<td>
				<span class="lblBlack"><strong>Số điện thoại</strong></span>
			</td>
			<td>${criminalDenouncement.phoneNumber}</td>
		</tr>
		<tr>
			<td>
				<span class="lblBlack"><strong>Email</strong></span>
			</td>
			<td>${criminalDenouncement.email}</td>
		</tr>
		<tr>
			<td>
				<span class="lblBlack"><strong>Nội dung tố cáo</strong></span>
			</td>
			<td><span style="white-space: pre-wrap;">${criminalDenouncement.content}</span></td>
		</tr>
	</table>
	<form action="${ct}/cm/denouncement/reply/${id}" method="post">
		<table style="width: 100%">
			<tr>
				<td>
					<span class="lblBlack"><strong>Phản hồi</strong></span>
				</td>
			</tr>
			<tr>
				<td>
					<textarea rows="24" cols="50" name="replyContent" id="replyContent" class="textmulti"></textarea>
					<%-- in order to support context path, have to add context path mannually to basePath property --%>
					<ckfinder:setupCKEditor basePath="/ckfinder" />
					<ckeditor:replace replace="replyContent" basePath="/ckeditor" />
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="submit" value="Gửi mail" class="button" />
				</td>
			</tr>
		</table>
	</form>
</div>