<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="/WEB-INF/tags/tags.tld" %>

<link href="/css/linkbutton.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/functions.js"></script>

<c:if test="${msg != null}">
	<div class="notify">
		<c:out value="${msg}" />
	</div>
	<c:remove var="msg" scope="session"/>
</c:if>
<br />

<div class="box960">
	<div class="title">
		<div class="left"></div>
		<div class="right"></div>
		<div class="text_form" style="width: 320px;">
			<img src="/images/left_title.png" style="float: left"> 
			<img src="/images/right_title.png" style="float: right">
			Quản lý phòng ban
		</div>
	</div>
	<div class="content_960">
	</div>
</div>
<a href="/department/createDepartment.html" class="linkbutton">Tạo phòng ban</a>
<div class="khung_content_960" id="gridCongViec">
	<div class="content-grid">
		<table class="gridView" cellspacing="1" style="display: block; clear: both; width: 100%;">
			<tr class="odd">
				<th style="width:3%" align="center">STT</th>
				<th style="width:15%" align="center">Mã phòng</th>
				<th style="width:30%" align="center">Tên phòng</th>
				<th style="width:25%" align="center">Số điện thoại</th>
				<th style="width:24%" align="center">Email</th>
				<th style="width:3%" align="center">Sửa</th>
				<th style="width:3%" align="center">Xóa</th>
			</tr>
			<c:forEach items="${departments}" var="d" varStatus="s">
				<c:choose>
					<c:when test="${s.count % 2 == 1}">
						<c:set var="c" value="odd"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="c" value="even"></c:set>
					</c:otherwise>
				</c:choose>
				
				<tr class="${c}">
					<td align="center">${(pageInfo.currentPage - 1) * pageInfo.pageSize + s.count}</td>
					<td align="center">${d.code}</td>
					<td>${d.name}</td>
					<td>${d.phoneNumber}</td>
					<td>${d.email}</td>
					<td align="center">
						<a href="/department/updateDepartment/${d.id}.html?p=${pageInfo.currentPage}"><img src="/images/update.png" width="15" height="15" /></a>
					</td>
					<td align="center">
						<a href="/department/deleteDepartment/${d.id}.html?p=${pageInfo.currentPage}" class="deleteLink"><img src="/images/delete.png" width="15" height="15" /></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="bottom-pager">
			<div class="left">
				<div class="pages">
					<t:paging pageInfo="${pageInfo}" link="/department/listDepartments.html"/>
				</div>
			</div>
			<div class="right">
				Trang: ${pageInfo.currentPage} / Tổng số: ${pageInfo.totalItems} kết quả
			</div>
		</div>
	</div>
</div>