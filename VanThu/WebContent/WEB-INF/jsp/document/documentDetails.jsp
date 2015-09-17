<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thông tin văn bản</title>

<link href="/css/common.css" rel="stylesheet" type="text/css" />
<link href="/css/MVP.css" rel="stylesheet" type="text/css" />
<link href="/css/gridview.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<div class="gridHeader960" style="width: 100%;" align="center">THÔNG TIN VĂN BẢN ĐI</div>
	<div class="content-grid">
		<table class="gridView" cellspacing="1">
			<tr>
				<td style="width:20%">Số ký hiệu</td>
				<td style="width:30%" align="center">${document.sign}</td>
				<td style="width:20%">Loại văn bản</td>
				<td style="width:30%" align="center">${document.documentType.typeName}</td>
			</tr>
			<tr>
				<td style="width:20%">Độ mật</td>
				<td style="width:30%" align="center">
					<c:forEach items="${confidentialLevels}" var="c">
						<c:if test="${c.key == document.confidentialLevel}">${c.value}</c:if>
					</c:forEach>
				</td>
				<td style="width:20%">Độ khẩn</td>
				<td style="width:30%" align="center">
					<c:forEach items="${urgentLevels}" var="u">
						<c:if test="${u.key == document.urgentLevel}">${u.value}</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="width:20%">Ngày ban hành</td>
				<td style="width:30%" align="center">
					<fmt:formatDate var="publishDate" value="${document.publishDate}" pattern="dd/MM/yyyy" />
					${publishDate}
				</td>
				<td style="width:20%">Lãnh đạo ký</td>
				<td style="width:30%" align="center">${document.signer}</td>
			</tr>
			<tr>
				<td style="width:20%">Đơn vị ban hành</td>
				<td colspan="3">
					<c:set var="len" value="${fn:length(document.departments)}"></c:set>
					<c:forEach items="${document.departments}" var="d" varStatus="s">
						<c:if test="${s.count == len}">
							${d.code}
						</c:if>
						<c:if test="${s.count != len}">
							${d.code}, 
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td style="width:20%" valign="middle">Trích yếu</td>
				<td colspan="3">${document.abs}</td>
			</tr>
			<tr>
				<td style="width:20%" valign="middle">File văn bản</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${t == 0}"><c:set var="url" value="/document/downloadDocument.html" /></c:when>
						<c:when test="${t == 1}"><c:set var="url" value="/document/downloadOutboundDocument.html" /></c:when>
						<c:when test="${t == 2}"><c:set var="url" value="/document/downloadInboundDocument.html" /></c:when>
					</c:choose>
					<c:forEach items="${document.documentFiles}" var="file">
						<a href="${url}?f=${file.id}&d=${document.id}">${file.name}</a><br />
					</c:forEach>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="khung_content_960" id="gridCongViec">
		<div class="gridHeader960" style="width: 100%;">CÁC ĐƠN VỊ NHẬN</div>
		<div class="content-grid">
			<table class="gridView" cellspacing="1" style="display: block; clear: both; width: 100%;">
				<tr class="odd">
					<th style="width:3%" align="center">STT</th>
					<th style="width:20%" align="center">Thời gian gửi</th>
					<th style="width:40%" align="center">Đơn vị nhận</th>
					<th style="width:20%" align="center">Thời gian nhận</th>
					<th style="width:17%" align="center">Tiến độ</th>
				</tr>
				<c:forEach items="${document.departmentDocuments}" var="d" varStatus="s">
					<c:choose>
						<c:when test="${s.count % 2 == 1}">
							<c:set var="c" value="odd"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="c" value="even"></c:set>
						</c:otherwise>
					</c:choose>
					
					<tr class="${c}">
						<td align="center">${s.count}</td>
						<td>
							<fmt:formatDate var="sendTime" value="${d.sendTime}" pattern="dd/MM/yyyy HH:mm" />
							${sendTime}
						</td>
						<td align="center">${d.pk.department.code}</td>
						<c:if test="${d.pk.department.inProvince}">
							<td>
								<c:if test="${d.receiveTime != null}">
									<fmt:formatDate var="receiveTime" value="${d.receiveTime}" pattern="dd/MM/yyyy HH:mm" />
									${receiveTime}
								</c:if>
							</td>
							<td align="center">
								<c:if test="${d.isRead}">Đã nhận</c:if>
								<c:if test="${!d.isRead}">Chưa nhận</c:if>
							</td>
						</c:if>
						<c:if test="${!d.pk.department.inProvince}">
							<td colspan="2" align="center" style="background-color: gray;">Chỉ lưu</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<p><u>Ghi chú:</u> Trạng thái <b>Chỉ lưu</b> tức là văn bản sẽ không được gửi đến các đơn vị nằm ngoài Công an tỉnh</p>
</body>
</html>