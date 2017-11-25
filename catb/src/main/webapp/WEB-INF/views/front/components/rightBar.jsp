<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld"%>

<!-- jwplayer -->
<script type="text/javascript" src="${ct}/jwplayer/jwplayer.js"></script>
<script type="text/javascript">jwplayer.key="L+vphptqtQHGd4F4yQP0ujFMkZL+3WfIXul0HQ==";</script>

<%@ include file="contact.jsp" %>
<div id="Chuyen_Muc">
	<div class="TieuDe">
		<img alt="" src="${ct}/resources/images/communication.png" style="width: 20px; height: 20px; vertical-align: middle;" />
		Chuyên Mục
	</div>
	<div id="menuRight" class="menu">
		<ul class="main">
			<c:forEach items="${rightTopNewsCatalogs}" var="rightTopNewsCatalog">
				<li>
					<a href="${news_ct}/${rightTopNewsCatalog.url}" class="rightTopLink">${rightTopNewsCatalog.name}</a>
				</li>
			</c:forEach>
			<c:forEach items="${columns}" var="column">
				<li>
					<a href="${ct}/${column.url}" class="rightTopLink">${column.name}</a>
				</li>
			</c:forEach>
			<li>
				<a href="ftp://10.25.3.10" class="rightTopLink">Tải phần mềm và hướng dẫn</a>
			</li>
		</ul>
	</div>
</div>
<c:if test="${rightCenterNews != null}">
	<div id="Thong_Bao">
		<div class="TieuDe">
			<img alt="" src="${ct}/resources/images/common_infor.png" style="width: 20px; height: 20px; vertical-align: middle;" />
			<c:out value="${rightCenterNews.newsCatalog.name}"></c:out>
		</div>
		<div class="main">
			<ul>
				<c:forEach items="${rightCenterNews.newses}" var="news">
					<li>
						<span title='cssbody=[boxbody] singleclickstop=[on] cssheader=[boxheader] header=[${news.title}]  body=[${news.summary}]'>
							<a href='${news_ct}/${rightCenterNews.newsCatalog.url}/${news.id}/${f:toFriendlyUrl(news.title)}'>${news.title}</a>
						</span>
					</li>
				</c:forEach>
			</ul>
			<div class="XemChiTiet">
				<a href='${news_ct}/${rightCenterNews.newsCatalog.url}'>${COMMONINFO.detailsCaption}</a>
			</div>
		</div>
	</div>
</c:if>
<%-- <div id="Lien_Ket_Web">
	<select name="linkSites" id="linkSites" class="ddl">
		<option value="">------ Lịch công tác ------</option>
		<c:forEach items="${DEPARTMENTS_LIST}" var="department">
			<option value="${ct}/lich-cong-tac/?dId=${department.id}" class="blank">${department.code}</option>
		</c:forEach>
	</select>
</div> --%>

<link href="${ct}/resources/css/jquery-ui-tab.css" rel="stylesheet" type="text/css" />

<div id="tabs">
	<ul>
		<li><a href="#video_gallery">Video</a></li>
		<li><a href="#image_gallery">Ảnh</a></li>
	</ul>
	<div id="video_gallery" style="padding-top: 5px;">
		<div id="video_container" style="border: 1px solid #D0D0D0;">
			
		</div>
		<div id="video_list">
			
		</div>
		<div class="XemChiTiet">
			<a href='${ct}/thu-vien-video' style="color: #FF0000">${COMMONINFO.detailsCaption}</a>
		</div>
	</div>
	<div id="image_gallery" style="padding-top: 5px;">
		<div class="image_container" style="border: 1px solid #D0D0D0;">
			<div id="image_slider" style="display: none; position: relative; margin: 0 auto; width: 120px; height: 90px; overflow: hidden;">
		        <div u="loading" style="position: absolute; top: 0px; left: 0px;">
		            <div style="filter: alpha(opacity=70); opacity:0.7; position: absolute; display: block;
		                background-color: #000000; top: 0px; left: 0px;width: 100%;height:100%;">
		            </div>
		            <div style="position: absolute; display: block; background: url(${ct}/jssor/img/loading.gif) no-repeat center center;
		                top: 0px; left: 0px;width: 100%;height:100%;">
		            </div>
		        </div>
				<div u="slides" style="cursor: move; position: absolute; left: 0px; top: 0px; width: 120px; height: 90px; overflow: hidden;">
					<c:forEach items="${images}" var="image">
						<div>
		                    <img u="image" src="${image.file}" alt="" />
		                </div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="XemChiTiet">
			<a href='${ct}/thu-vien-anh' style="color: #FF0000">${COMMONINFO.detailsCaption}</a>
		</div>
	</div>
</div>

<script type="text/javascript">
</script>

<%-- <div id="Lien_Ket_Web">
	<select name="linkSites" id="linkSites" class="ddl">
		<option value="">------ Liên kết website ------</option>
		<c:forEach items="${LINK_LIST}" var="link">
			<option value="${link.linkSite}" class="${link.openBlank ? 'blank' : ''}">${link.title}</option>
		</c:forEach>
	</select>
</div>
<div id="Quang_Cao">
	<span>
		<c:forEach items="${ADVERTISEMENTS_LIST}" var="ad">
			<c:if test="${ad.openBlank}">
				<a href="${ad.link}" target='_blank'><img src="${ad.image}" alt="" style="width: 100%;" /></a>
			</c:if>
			<c:if test="${!ad.openBlank}">
				<a href="${ad.link}"><img src="${ad.image}" alt="" style="width: 100%;" /></a>
			</c:if>
		</c:forEach>
	</span>
</div> --%>
<!-- <div id="Luot_Truy_Cap">
	<div class='truycap'>Số lượt truy cập:<br /> </div>
	<div class="space5">
		<script type="text/javascript" src="http://counter8.bestfreecounterstat.com/private/counter.js?c=567e1ecbea1d5b5680b62929274f3cfc"></script>
	</div>
</div> -->

<script src="${ct}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ct}/resources/js/docs.min.js" type="text/javascript"></script>
<script src="${ct}/resources/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>

<script src="${ct}/resources/js/jssor.slider.mini.js" type="text/javascript"></script>
<script src="${ct}/resources/js/mediaTab.js" type="text/javascript"></script>