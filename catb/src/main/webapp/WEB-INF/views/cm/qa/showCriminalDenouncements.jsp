<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="exTag" uri="/WEB-INF/tag/extags.tld" %>

<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ HÒM THƯ TỐ GIÁC TỘI PHẠM
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form action="${ct}/cm/denouncement/show" method="get">
		<table style="width: 80%" align="center">
			<tr>
				<td valign="middle">
					<span class="lblBlack">Tiêu đề</span>
				</td>
				<td>
					<input type="text" name="t" id="title" maxlength="1000" value="${param.t}" class="textbox" style="width: 100%" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Tìm kiếm" class="button" />
				</td>
			</tr>
		</table>
	</form>

	<table class="responstable">
		<tr class="header">
			<th width="5%">
				<input type="checkbox" name="selectAll" id="selectAll">
			</th>
			<th width="65%">Tiêu đề</th>
			<th width="20%">Trạng thái</th>
			<th>Gửi email phản hồi</th>
		</tr>
		<c:forEach items="${criminalDenouncements}" var="criminalDenouncement">
			<tr>
				<td>
					<input type="checkbox" name="criminalDenouncementId" id="criminalDenouncementId" value="${criminalDenouncement.id}" class="checkbox" />
				</td>
				<td><a href="${ct}/cm/denouncement/view/${criminalDenouncement.id}" class="news_title"><c:out value="${criminalDenouncement.title}"></c:out></a></td>
				<td>
					<c:if test="${criminalDenouncement.status == 0}">Chưa xem</c:if>
					<c:if test="${criminalDenouncement.status == 1}">Đã xem</c:if>
					<c:if test="${criminalDenouncement.status == 2}">Đã xem & trả lời qua email</c:if>
				</td>
				<td>
					<c:if test="${not empty criminalDenouncement.email}">
						<a href="${ct}/cm/denouncement/reply/${criminalDenouncement.id}">
							<img src="${ct}/resources/images/send_email.png" alt="Trả lời" class="update" />
						</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" style="text-align: right; background-color: #FFF; padding: 0.7em;">
				<exTag:paging pageInfo="${pageInfo}" link="${ct}/cm/denouncement/show" cssClass="page_link" params="${params}" />
			</td>
		</tr>
	    <tr>
	    	<td colspan="4" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/denouncement/delete" id="delDenouncement"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>