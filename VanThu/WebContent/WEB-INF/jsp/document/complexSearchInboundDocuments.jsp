<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="/WEB-INF/tags/tags.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld" %>

<link href="/css/linkbutton.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/functions.js"></script>
<link href="/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-ui.js"></script>

<style>
tr.unread
{
    font-weight: bold;
}

span.icon_clear{
    position:absolute;
    right:10px;    
    top: 0px;
    cursor:pointer;
    font: bold 1em sans-serif;
    color:#38468F;  
}

.clearable {
	position: relative;
}
</style>

<script type="text/javascript">
$(function() {
	$.datepicker.regional['vi'] = {
	        closeText: 'Đóng',
	        prevText: '&#x3c;Trước',
	        nextText: 'Tiếp&#x3e;',
	        currentText: 'Hôm nay',
	        monthNames: ['Tháng Một', 'Tháng Hai', 'Tháng Ba', 'Tháng Tư', 'Tháng Năm', 'Tháng Sáu',
	        'Tháng Bảy', 'Tháng Tám', 'Tháng Chín', 'Tháng Mười', 'Tháng Mười Một', 'Tháng Mười Hai'],
	        monthNamesShort: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
	        'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
	        dayNames: ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'],
	        dayNamesShort: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
	        dayNamesMin: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
	        weekHeader: 'Tu',
	        dateFormat: 'dd/mm/yy',
	        firstDay: 0,
	        isRTL: false,
	        showMonthAfterYear: false,
	        yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['vi']);
	$( "#sfromPublishDate" ).datepicker();
	$( "#stoPublishDate" ).datepicker();
	$( "#sfromReceiveDate" ).datepicker();
	$( "#stoReceiveDate" ).datepicker();
	$(".icon_clear").click(function() {
		$(this).parent().find("input").val('');
	});
});
</script>

<script type="text/javascript">
function report() {
	var sign = $('#ssign').val();
	var type = $('#stype').val();
	var confidentialLevel = $('#sconfidentialLevel').val();
	var urgentLevel = $('#surgentLevel').val();
	var sender = $('#ssender').val();
	var signer = $('#ssigner').val();
	var fromPublishDate = $('#sfromPublishDate').val();
	var fromReceiveDate = $('#sfromReceiveDate').val();
	var toPublishDate = $('#stoPublishDate').val();
	var toReceiveDate = $('#stoReceiveDate').val();
	var abs = $('#sabs').val();
	
	var url = '/document/complexReportInbound.html?ssign=' + sign + '&stype=' + type + '&sconfidentialLevel=' + confidentialLevel + '&surgentLevel=' + urgentLevel +
			'&ssender=' + sender + '&ssigner=' + signer + '&sfromPublishDate=' + fromPublishDate + '&sfromReceiveDate=' + fromReceiveDate +
			'&stoPublishDate=' + toPublishDate + '&stoReceiveDate=' + toReceiveDate + '&sabs=' + abs;
	window.open(url, '_blank', 'toolbar=yes, scrollbars=yes, resizable=yes, top=100, left=100, width=1100, height=600');
}
</script>

<div class="box960">
	<div class="title">
		<div class="left"></div>
		<div class="right"></div>
		<div class="text_form" style="width: 320px;">
			<img src="/images/left_title.png" style="float: left"> 
			<img src="/images/right_title.png" style="float: right">
			Tìm kiếm văn bản đến - nâng cao
		</div>
	</div>
	<div class="content_960">
		<form action="/document/complexSearchInboundDocuments.html" method="GET">
			<table class="table-form" style="width: 90%;" align="center">
				<tr>
					<td class="label" style="width:10%">Số ký hiệu</td>
					<td class="data txtinput" style="width:35%"><input type="text" name="ssign" id="ssign" value="${param.ssign}" /></td>
					<td class="label" style="width:10%">Loại văn bản</td>
					<td class="data txtinput" style="width:35%">
						<select name="stype" id="stype">
							<option value="-1">---- Chọn loại văn bản ----</option>
							<c:forEach items="${documentTypes}" var="documentType">
								<c:if test="${documentType.key == param.stype}">
									<option value="${documentType.key}" selected="selected">${documentType.value}</option>
								</c:if>
								<c:if test="${documentType.key != param.stype}">
									<option value="${documentType.key}">${documentType.value}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 10%;">Độ mật</td>
					<td class="data txtinput" style="width:35%">
						<select name="sconfidentialLevel" id="sconfidentialLevel">
							<option value="-1">---- Chọn độ mật ----</option>
							<c:forEach items="${confidentialLevels}" var="confidentialLevel">
								<c:if test="${confidentialLevel.key == param.sconfidentialLevel}">
									<option value="${confidentialLevel.key}" selected="selected">${confidentialLevel.value}</option>
								</c:if>
								<c:if test="${confidentialLevel.key != param.sconfidentialLevel}">
									<option value="${confidentialLevel.key}">${confidentialLevel.value}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td class="label" style="width: 10%;">Độ khẩn</td>
					<td class="data txtinput" style="width:35%">
						<select name="surgentLevel" id="surgentLevel">
							<option value="-1">---- Chọn độ khẩn ----</option>
							<c:forEach items="${urgentLevels}" var="urgentLevel">
								<c:if test="${urgentLevel.key == param.surgentLevel}">
									<option value="${urgentLevel.key}" selected="selected">${urgentLevel.value}</option>
								</c:if>
								<c:if test="${urgentLevel.key != param.surgentLevel}">
									<option value="${urgentLevel.key}">${urgentLevel.value}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 10%;">Nơi gửi</td>
					<td class="data txtinput" style="width:35%">
						<select id="ssender" name="ssender">
							<option value="-1">---- Chọn nơi gửi ----</option>
							<c:forEach items="${pDepartments}" var="pDepartment">
								<c:if test="${pDepartment.key == param.ssender}">
									<option value="${pDepartment.key}" selected="selected">${pDepartment.value}</option>
								</c:if>
								<c:if test="${pDepartment.key != param.ssender}">
									<option value="${pDepartment.key}">${pDepartment.value}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td class="label" style="width:10%">Lãnh đạo ký</td>
					<td class="data txtinput" style="width:35%"><input type="text" name="ssigner" id="ssigner" value="${param.ssigner}" /></td>
				</tr>
				<tr>
					<td class="label" colspan="2">Ngày ban hành</td>
					<td class="label" colspan="2">Ngày nhận</td>
				</tr>
				<tr>
					<td class="label1" align="right">&nbsp;&nbsp;&nbsp; Từ ngày</td>
					<td class="data txtinput">
						<span class="clearable">
							<input type="text" name="sfromPublishDate" id="sfromPublishDate" readonly="readonly" value="${param.sfromPublishDate}" />
							<span class="icon_clear">x</span>
						</span>
					</td>
					<td class="label1" align="right">&nbsp;&nbsp;&nbsp; Từ ngày</td>
					<td class="data txtinput">
						<span class="clearable">
							<input type="text" name="sfromReceiveDate" id="sfromReceiveDate" readonly="readonly" value="${param.sfromReceiveDate}" />
							<span class="icon_clear">x</span>
						</span>
					</td>
				</tr>
				<tr>
					<td class="label1" align="right">&nbsp;&nbsp;&nbsp; Đến ngày</td>
					<td class="data txtinput">
						<span class="clearable">
							<input type="text" name="stoPublishDate" id="stoPublishDate" readonly="readonly" value="${param.stoPublishDate}" />
							<span class="icon_clear">x</span>
						</span>
					</td>
					<td class="label1" align="right">&nbsp;&nbsp;&nbsp; Đến ngày</td>
					<td class="data txtinput">
						<span class="clearable">
							<input type="text" name="stoReceiveDate" id="stoReceiveDate" readonly="readonly" value="${param.stoReceiveDate}" />
							<span class="icon_clear">x</span>
						</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:10%" valign="middle">Trích yếu</td>
					<td class="data txtinput" valign="top" colspan="3">
                		<textarea name="sabs" rows="2" cols="20" id="sabs">${param.sabs}</textarea>
            		</td>
				</tr>
				<tr class="submit">
					<td colspan="4" align="center">
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