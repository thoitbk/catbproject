<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="exTag" uri="/WEB-INF/tag/extags.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		LỊCH CÔNG TÁC LÃNH ĐẠO
	</div>
</div>

<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="scheduleFileViewModel" enctype="multipart/form-data">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table style="width: 90%; margin-left: 10px;">
			<tr>
				<td width="40%" align="center">
					<span class="lblBlack" style="font-weight: bold;">Tiêu đề</span>
				</td>
				<td width="60%">
					<form:input path="title" id="title" maxlength="500" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td width="40%" align="center" style="font-weight: bold;">
					<span class="lblBlack">File</span>
				</td>
				<td width="60%">
					<form:input type="file" path="file" id="file"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Cập nhật" class="button" />
				</td>
			</tr>
		</table>
	</form:form>

	<table class="responstable">
		<tr class="header">
			<th width="80%">Lịch</th>
			<th>Chi tiết</th>
		</tr>
		<c:if test="${leaderSchedule != null}">
		<tr>
			<td style="text-align: left;"><a href="${ct}/lich-lanh-dao" target="_blank" style="text-decoration: none;">${leaderSchedule.title}</a></td>
			<td style="text-align: center;"><a href="${ct}/tai-lich-lanh-dao">download</a></td>
		</tr>
		</c:if>
	</table>
</div>