<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="/WEB-INF/tags/tags.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld" %>

<script src="/js/jquery-1.js" type="text/javascript"></script>

<style>
tr.unread
{
    font-weight: bold;
}
</style>

<div class="khung_content_960" id="gridCongViec">
	<div class="gridHeader960" style="width: 100%;">DANH SÁCH VĂN BẢN ĐẾN CHƯA ĐỌC</div>
	<div class="content-grid">
		<table class="gridView" cellspacing="1" style="display: block; clear: both; width: 100%;">
			<tr class="odd">
				<th style="width:3%" align="center">STT</th>
				<th style="width:12%" align="center">Số ký hiệu</th>
				<th style="width:50%" align="center">Trích yếu</th>
				<th style="width:10%" align="center">Ngày ban hành</th>
				<th style="width:10%" align="center">Ngày gửi</th>
				<th style="width:15%" align="center">File</th>
			</tr>
			<c:forEach items="${documents}" var="document" varStatus="s">
				<c:choose>
					<c:when test="${s.count % 2 == 1}">
						<c:set var="c" value="odd"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="c" value="even"></c:set>
					</c:otherwise>
				</c:choose>
				<tr class="unread ${c}">
					<td align="center">${(pageInfo.currentPage - 1) * pageInfo.pageSize + s.count}</td>
					<td>
						<a href="javascript:void(0);" onclick="window.open('/document/inboundDocumentDetails.html?d=${document.id}', '_blank', 'toolbar=yes, scrollbars=yes, resizable=yes, top=100, left=100, width=1100, height=600');">
							${document.sign}
						</a>
					</td>
					<td>${document.abs}</td>
					<td>
						<fmt:formatDate var="publishDate" value="${document.publishDate}" pattern="dd/MM/yyyy" />
						${publishDate}
					</td>
					<td>
						<fmt:formatDate var="sendDate" value="${document.sendDate}" pattern="dd/MM/yyyy" />
						${sendDate}
					</td>
					<td>
						<c:forEach items="${document.documentFiles}" var="file">
							<a href="/document/downloadInboundDocument.html?f=${file.id}&d=${document.id}">${file.name}</a><br />
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="bottom-pager">
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