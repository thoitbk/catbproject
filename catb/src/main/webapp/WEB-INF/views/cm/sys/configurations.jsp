<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ct}/resources/css/table.css" rel="stylesheet" type="text/css" />
<script src="${ct}/resources/js/cm/anim.js" type="text/javascript"></script>

<div class="TieuDe">     
	<div class="TieuDe_ND">
		QUẢN TRỊ CẤU HÌNH
	</div>
</div>
<div>
	<c:if test="${not empty msg}">
		<div id="alert" class="alert-box success"><c:out value="${msg}"></c:out></div>
		<c:remove var="msg" scope="session" />
	</c:if>
	<form:form method="post" commandName="commonInfoViewModel">
		<form:errors path="*" cssClass="alert-box warning" element="div" />
		<table class="" style="width: 100%">
			<tr>
				<td><span class="lblBlack">Tiêu đề website</span></td>
				<td colspan="3">
					<form:textarea path="webTitle" id="webTitle" rows="24" cols="50" cssClass="textmulti" cssStyle="width:100%;" cssErrorClass="textmulti_error"/>
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tiêu đề chạy</span>
				</td>
				<td colspan="3">
					<form:input path="marqueeTitle" id="marqueeTitle" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tin mới cập nhật</span>
				</td>
				<td width="30%">
					<form:input path="recentNews" id="recentNews" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Tin xem nhiều nhất</span>
				</td>
				<td>
					<form:input path="mostViewed" id="mostViewed" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Hỏi đáp</span>
				</td>
				<td width="30%">
					<form:input path="questionAnswer" id="questionAnswer" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Số ảnh quảng cáo</span>
				</td>
				<td>
					<form:input path="adAmount" id="adAmount" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Số tin từng chuyên trang</span>
				</td>
				<td width="30%">
					<form:input path="tcCatalogs" id="tcCatalogs" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Tin theo danh mục</span>
				</td>
				<td>
					<form:input path="newsInSameCatalog" id="newsInSameCatalog" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tin cùng chủ đề</span>
				</td>
				<td width="30%">
					<form:input path="sameSubjects" id="sameSubjects" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Tin phân trang tìm kiếm</span>
				</td>
				<td>
					<form:input path="newsInSearchResult" id="newsInSearchResult" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tin nổi bật</span>
				</td>
				<td width="30%">
					<form:input path="headlines" id="headlines" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Tiêu đề các tin khác cùng chủ đề</span>
				</td>
				<td>
					<form:input path="sameSubjectTitle" id="sameSubjectTitle" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tiêu đề nổi bật</span>
				</td>
				<td width="30%">
					<form:input path="headlineCaption" id="headlineCaption" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Hôm nay</span>
				</td>
				<td>
					<form:input path="today" id="today" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tiêu đề ảnh</span>
				</td>
				<td width="30%">
					<form:input path="imageCaption" id="imageCaption" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Ngày đăng</span>
				</td>
				<td>
					<form:input path="postedDate" id="postedDate" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tiêu đề video</span>
				</td>
				<td width="30%">
					<form:input path="videoCaption" id="videoCaption" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Tác giả</span>
				</td>
				<td>
					<form:input path="author" id="author" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tiêu đề audio</span>
				</td>
				<td width="30%">
					<form:input path="audioCaption" id="audioCaption" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">In bài này</span>
				</td>
				<td>
					<form:input path="print" id="print" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Tiêu đề xem chi tiết</span>
				</td>
				<td width="30%">
					<form:input path="detailsCaption" id="detailsCaption" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Trang chủ</span>
				</td>
				<td>
					<form:input path="homePage" id="homePage" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Thủ tục hành chính</span>
				</td>
				<td width="30%">
					<form:input path="administrativeProcedures" id="administrativeProcedures" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Văn bản</span>
				</td>
				<td>
					<form:input path="document" id="document" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Hướng dẫn thủ tục hành chính</span>
				</td>
				<td width="30%">
					<form:input path="administrativeProceduresInstruction" id="administrativeProceduresInstruction" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Văn bản pháp quy pháp luật</span>
				</td>
				<td>
					<form:input path="legalDocument" id="legalDocument" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Số lượt truy cập</span>
				</td>
				<td width="30%">
					<form:input path="views" id="views" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Về đầu trang</span>
				</td>
				<td>
					<form:input path="goTop" id="goTop" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Giới thiệu chung</span>
				</td>
				<td width="30%">
					<form:input path="introduction" id="introduction" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Chức năng nhiệm vụ</span>
				</td>
				<td>
					<form:input path="duty" id="duty" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Cơ cấu tổ chức</span>
				</td>
				<td width="30%">
					<form:input path="organizationalStructure" id="organizationalStructure" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Thành tựu đạt được</span>
				</td>
				<td>
					<form:input path="achievement" id="achievement" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Số danh mục trong menu phải trên</span>
				</td>
				<td width="30%">
					<form:input path="rightTopSize" id="rightTopSize" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td align="left" width="15%" style="padding-left: 2%;">
					<span class="lblBlack">Số tin trong menu phải giữa</span>
				</td>
				<td>
					<form:input path="rightCenterSize" id="rightCenterSize" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<span class="lblBlack">Phân trang quản trị</span>
				</td>
				<td width="30%">
					<form:input path="pageSize" id="pageSize" cssClass="textbox" cssStyle="width: 100%;" cssErrorClass="textbox_error" />
				</td>
				<td colspan="2">
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" value="Cập nhật" class="button" />
				</td>
			</tr>
		</table>
	</form:form>
</div>