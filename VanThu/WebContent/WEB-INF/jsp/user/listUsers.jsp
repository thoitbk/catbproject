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
			Quản lý người dùng
		</div>
	</div>
	<div class="content_960">
		<form action="/user/searchUsers.html" method="GET">
			<table class="table-form" style="width: 70%;" align="center">
				<tr>
					<td class="label" style="width: 30%;">Tài khoản</td>
					<td class="data txtinput"><input type="text" name="susername" id="susername" value="${param.susername}" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Tên</td>
					<td class="data txtinput"><input type="text" name="sname" id="sname" value="${param.sname}" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Chức vụ</td>
					<td class="data txtinput"><input type="text" name="sposition" id="sposition" value="${param.sposition}" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Email</td>
					<td class="data txtinput"><input type="text" name="semail" id="semail" value="${param.semail}" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Số điện thoại</td>
					<td class="data txtinput"><input type="text" name="sphoneNumber" id="sphoneNumber" value="${param.sphoneNumber}" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Phòng</td>
					<td class="data txtinput">
						<select name="sdepartment" id="sdepartment">
							<option value="-1">---- Chọn phòng ----</option>
							<c:forEach items="${departments}" var="d">
								<c:if test="${d.key == param.sdepartment}">
									<option value="${d.key}" selected="selected">${d.value}</option>
								</c:if>
								<c:if test="${d.key != param.sdepartment}">
									<option value="${d.key}">${d.value}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="submit">
					<td colspan="2" align="center">
						<input type="submit" value="Tìm kiếm" class="SaveButton" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<a href="/user/createUser.html" class="linkbutton">Tạo người dùng</a>
<div class="khung_content_960" id="gridCongViec">
	<div class="content-grid">
		<table class="gridView" cellspacing="1" style="display: block; clear: both; width: 100%;">
			<tr class="odd">
				<th style="width:3%" align="center">STT</th>
				<th style="width:10%" align="center">Tài khoản</th>
				<th style="width:15%" align="center">Tên</th>
				<th style="width:20%" align="center">Chức vụ</th>
				<th style="width:15%" align="center">Email</th>
				<th style="width:15%" align="center">SĐT</th>
				<th style="width:12%" align="center">Quyền</th>
				<th style="width:5%" align="center">Phòng</th>
				<th style="width:3%" align="center">Sửa</th>
				<th style="width:3%" align="center">Xóa</th>
			</tr>
			<c:forEach items="${users}" var="user" varStatus="s">
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
					<td>${user.username}</td>
					<td>${user.name}</td>
					<td>${user.position}</td>
					<td>${user.email}</td>
					<td>${user.phoneNumber}</td>
					<td>
						<c:choose>
							<c:when test="${user.role == 0}">
								<c:out value="Admin"></c:out>
							</c:when>
							<c:when test="${user.role == 1}">
								<c:out value="Văn thư"></c:out>
							</c:when>
							<c:when test="${user.role == 2}">
								<c:out value="Người dùng"></c:out>
							</c:when>
						</c:choose>
					</td>
					<td align="center">${user.department.code}</td>
					<td align="center">
						<c:if test="${pageContext.request.queryString != null && pageContext.request.queryString != ''}">
							<a href="/user/updateUser/${user.id}.html?${pageContext.request.queryString}"><img src="/images/update.png" width="15" height="15" /></a>
						</c:if>
						<c:if test="${pageContext.request.queryString == null || pageContext.request.queryString == ''}">
							<a href="/user/updateUser/${user.id}.html"><img src="/images/update.png" width="15" height="15" /></a>
						</c:if>
					</td>
					<td align="center">
						<c:if test="${pageContext.request.queryString != null && pageContext.request.queryString != ''}">
							<a href="/user/deleteUser/${user.id}.html?${pageContext.request.queryString}" class="deleteLink"><img src="/images/delete.png" width="15" height="15" /></a>
						</c:if>
						<c:if test="${pageContext.request.queryString == null || pageContext.request.queryString == ''}">
							<a href="/user/deleteUser/${user.id}.html" class="deleteLink"><img src="/images/delete.png" width="15" height="15" /></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="bottom-pager">
			<div class="left">
				<div class="pages">
					<t:paging pageInfo="${pageInfo}" link="/user/searchUsers.html" params="${params}"/>
				</div>
			</div>
			<div class="right">
				Trang: ${pageInfo.currentPage} / Tổng số: ${pageInfo.totalItems} kết quả
			</div>
		</div>
	</div>
</div>