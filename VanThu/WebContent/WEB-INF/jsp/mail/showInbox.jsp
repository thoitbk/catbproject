<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="/WEB-INF/tags/tags.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="/css/linkbutton.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/functions.js"></script>

<style>
tr.unread
{
    font-weight: bold;
}
</style>

<c:if test="${msg != null}">
	<div class="notify">
		<c:out value="${msg}" />
	</div>
	<c:remove var="msg" scope="session"/>
</c:if>
<br />

<div class="khung_content_960" id="gridCongViec">
	<div class="gridHeader960" style="width: 100%;">DANH SÁCH EMAIL ĐẾN</div>
	<div class="content-grid">
		<table class="gridView" cellspacing="1" style="display: block; clear: both; width: 100%;">
			<tr class="odd">
				<th style="width:3%" align="center">STT</th>
				<th style="width:52%" align="center">Tiêu đề</th>
				<th style="width:15%" align="center">Người gửi</th>
				<th style="width:17%" align="center">Ngày gửi</th>
				<th style="width:8%" align="center">Kích thước</th>
				<th style="width:2%" align="center">Nhập</th>
			</tr>
			<c:forEach items="${mails}" var="mail" varStatus="s">
				<c:choose>
					<c:when test="${s.count % 2 == 1}">
						<c:set var="c" value="odd"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="c" value="even"></c:set>
					</c:otherwise>
				</c:choose>
				<c:set var="u" value=""></c:set>
				<c:if test="${!mail.read}">
					<c:set var="u" value="unread"></c:set>
				</c:if>
				
				<tr class="${u} ${c}">
					<td align="center">${(pageInfo.currentPage - 1) * pageInfo.pageSize + s.count}</td>
					<td>
						<a href="javascript:void(0);" onclick="window.open('/mail/showDetails.html?uid=${mail.uid}', '_blank', 'toolbar=yes, scrollbars=yes, resizable=yes, top=100, left=100, width=1100, height=600');">
							${mail.subject}
						</a>
					</td>
					<td>${mail.from}</td>
					<td>
						<fmt:formatDate var="sentDate" value="${mail.sentDate}" pattern="dd/MM/yyyy HH:mm" />
						${sentDate}
					</td>
					<td>${mail.size} KB</td>
					<td align="center">
						<a href="/document/importDocument.html?uid=${mail.uid}"><img src="/images/plus.png" width="15" height="15" /></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="bottom-pager">
			<div class="left">
				<div class="pages">
					<t:paging pageInfo="${pageInfo}" link="/mail/showInbox.html"/>
				</div>
			</div>
			<div class="right">
				Trang: ${pageInfo.currentPage} / Tổng số: ${pageInfo.totalItems} kết quả
			</div>
		</div>
	</div>
</div>
<script>
$(function() {
	$("tr td a").click(function() {
		$(this).parent().parent().removeClass("unread");
	});
});
</script>