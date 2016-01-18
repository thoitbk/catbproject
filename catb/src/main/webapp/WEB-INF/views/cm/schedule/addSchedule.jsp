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
		LẬP LỊCH CÔNG TÁC - ${userInfo.departmentCode}
	</div>
</div>

<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="scheduleViewModel">
		<table style="width: 90%; margin-left: 10px;">
			<tr>
				<td width="20%" align="center">
					<span class="lblBlack" style="font-weight: bold;">Ngày</span>
				</td>
				<td width="40%" align="center" style="font-weight: bold;">
					<span class="lblBlack">Lãnh đạo trực</span>
				</td>
				<td width="40%" align="center" style="font-weight: bold;">
					<span class="lblBlack">Cán bộ trực</span>
				</td>
			</tr>
			<c:forEach begin="0" end="6" step="1" var="i">
				<tr>
					<td width="20%">
						<span class="lblBlack">${daysInWeek[i]}, ${dates[i]}</span>
					</td>
					<td width="20%">
						<form:input path="leaders" id="leaders" maxlength="100" cssClass="textbox" cssStyle="width: 100%;" />
					</td>
					<td>
						<form:textarea path="staffs" id="staffs" rows="3" cols="50" cssClass="textmulti" cssStyle="width:100%;"/>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3" align="center">
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
			<th width="80%">Lịch</th>
			<th>Cập nhật</th>
		</tr>
		<c:forEach items="${schedules}" var="schedule">
			<tr>
				<td width="5%">
					<input type="checkbox" name="scheduleId" id="scheduleId" value="${schedule.id}" class="checkbox" />
				</td>
				<td width="80%">
					<fmt:formatDate var="date" value="${schedule.date}" pattern="dd/MM/yyyy" />
					<a href="${ct}/cm/schedule/view/${schedule.id}" class="news_title">Tuần ${schedule.week}, từ ngày <c:out value="${date}"></c:out></a>
				</td>
				<td>
					<a href="${ct}/cm/schedule/update/${schedule.id}">
						<img src="${ct}/resources/images/update.png" alt="Cập nhật" class="update" />
					</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="3" style="text-align: right; background-color: #FFF; padding: 0.7em;">
				<exTag:paging pageInfo="${pageInfo}" link="${ct}/cm/schedule/add" cssClass="page_link" />
			</td>
		</tr>
	    <tr>
	    	<td colspan="3" style="text-align: left; background-color: #FFF; padding: 0.7em;">
	    		<a href="${ct}/cm/schedule/delete" id="delSchedule"><img alt="Xóa" src="${ct}/resources/images/delete.png" class="delete" title="Xóa" /></a>&#8592; Click vào đây để xóa
	    	</td>
	    </tr>
	</table>
</div>