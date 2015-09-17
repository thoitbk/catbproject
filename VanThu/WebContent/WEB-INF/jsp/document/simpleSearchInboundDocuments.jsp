<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="/WEB-INF/tags/tags.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld" %>

<link href="/css/linkbutton.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/functions.js"></script>
<style type="text/css">
    tr.unread
    {
        font-weight: bold;
    }
</style>

<script type="text/javascript">
function report() {
	var month = $('#smonth').val();
	var year = $('#syear').val();
	if (month == null || month == -1) {
		alert('Chọn tháng');
		return;
	}
	if (year == null || year == -1) {
		alert('Chọn năm');
		return;
	}
	var url = '/document/reportInbound.html?smonth=' + month + '&syear=' + year;
	window.open(url, '_blank', 'toolbar=yes, scrollbars=yes, resizable=yes, top=100, left=100, width=1100, height=600');
}
</script>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="yearNow" value="${now}" pattern="yyyy" />
<c:set var="n" value="${yearNow - 2001}"></c:set>

<div class="box960">
	<div class="title">
		<div class="left"></div>
		<div class="right"></div>
		<div class="text_form" style="width: 320px;">
			<img src="/images/left_title.png" style="float: left"> 
			<img src="/images/right_title.png" style="float: right">
			Tìm kiếm văn bản đến - đơn giản
		</div>
	</div>
	<div class="content_960">
		<form action="/document/simpleSearchInboundDocuments.html" method="GET">
			<table class="table-form" style="width: 70%;" align="center">
				<tr>
					<td class="label" style="width: 30%;">Thông tin văn bản</td>
					<td class="data txtinput"><input type="text" name="sdocumentInfo" id="sdocumentInfo" value="${param.sdocumentInfo}" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Năm</td>
					<td class="data txtinput">
						<select name="syear" id="syear">
							<option value="-1">---- Chọn năm ----</option>
							<c:forEach var="i" begin="0" end="${n}">
								<c:if test="${yearNow - i == param.syear}">
									<option value="${yearNow - i}" selected="selected">${yearNow - i}</option>
								</c:if>
								<c:if test="${yearNow - i != param.syear}">
									<option value="${yearNow - i}">${yearNow - i}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Tháng</td>
					<td class="data txtinput">
						<select name="smonth" id="smonth">
							<option value="-1">---- Chọn tháng ----</option>
							<c:forEach begin="1" end="12" var="month">
								<c:if test="${month == param.smonth}">
									<option value="${month}" selected="selected">${month}</option>
								</c:if>
								<c:if test="${month != param.smonth}">
									<option value="${month}">${month}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="submit">
					<td colspan="2" align="center">
						<input type="submit" value="Tìm kiếm" class="SaveButton" />
						<input type="button" onclick="report();" class="SaveButton" value="Trích xuất" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div class="khung_content_960" id="gridCongViec">
	<div class="gridHeader960" style="width: 100%;">DANH SÁCH VĂN BẢN ĐẾN</div>
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
				<c:if test="${!f:isRead(document, departmentId)}">
					<c:set var="r" value="unread" ></c:set>
				</c:if>
				<tr class="${r} ${c}">
					<c:set var="r" value="" ></c:set>
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
			<div class="left">
				<div class="pages">
					<t:paging pageInfo="${pageInfo}" link="/document/simpleSearchInboundDocuments.html" params="${params}"/>
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