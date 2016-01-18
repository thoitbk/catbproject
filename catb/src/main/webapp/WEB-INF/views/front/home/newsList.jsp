<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld"%>

<script type="text/javascript" src="${ct}/resources/js/jquery.ellipsis.min.js" ></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.lead').ellipsis({
			row: 8,
		    onlyFullWords: true
		});
	});
</script>

<div id="MainDMTin">
	<div id="">
		<div>
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" height="85" width="730">
			<param name="movie" value="/images/advertise/bannertrungtam_1312011_154213.swf">
			<param name="quality" value="high">
			<param name="wmode" value="transparent">
			<embed wmode="transparent" src="${ct}/resources/images/khauhieu.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" height="85" width="730"></object>
		</div>
	</div>
	<c:if test="${fn:length(specialSiteInfos) > 0}">
		<div id="special_sites">
			<c:forEach begin="0" end="${fn:length(specialSiteInfos) - 1}" step="2" var="index">
				<div class="special_sites">
					<c:if test="${index < fn:length(specialSiteInfos)}">
						<c:set var="specialSiteInfo" value="${specialSiteInfos[index]}" scope="request"></c:set>
						<div class="special_site_left">
							<div class="TieuDe">
								<div class="TieuDe_dau"></div>
								<div class="TieuDe_ND">
									<a href='${news_ct}/${specialSiteInfo.newsCatalog.url}'>${specialSiteInfo.newsCatalog.name}</a>
								</div>
								<div class="TieuDe_Cuoi"></div>
							</div>
							<div class="Khung">
								<c:if test="${specialSiteInfo.newses != null and not empty specialSiteInfo.newses}">
									<c:set var="news" value="${specialSiteInfo.newses[0]}" scope="request" />
									<div class="main_news">
										<c:choose>
											<c:when test="${not empty news.image}">
												<c:set var="img" value="${news.image}" scope="request"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="img" value="${ct}/resources/images/default.png" scope="request"></c:set>
											</c:otherwise>
										</c:choose>
										<a href='${news_ct}/${specialSiteInfo.newsCatalog.url}/${news.id}/${f:toFriendlyUrl(news.title)}'>
											<img class='news_thumb' src='${img}' alt="Ảnh" />
										</a>
										<p class="main_news_title">
											<a href="${news_ct}/${specialSiteInfo.newsCatalog.url}/${news.id}/${f:toFriendlyUrl(news.title)}">${news.title}</a>
										</p>
										<p class="lead">
											<c:out value="${news.summary}"></c:out>
										</p>
									</div>
								</c:if>
								<c:if test="${specialSiteInfo.newses != null and fn:length(specialSiteInfo.newses) gt 1}">
									<ul class="other_newses">
										<c:forEach begin="1" end="${fn:length(specialSiteInfo.newses) - 1}" step="1" var="i">
											<li>
												<span title='cssbody=[boxbody] singleclickstop=[on] cssheader=[boxheader] header=[${specialSiteInfo.newses[i].title}]  body=[${specialSiteInfo.newses[i].summary}]'>
													<a href="${news_ct}/${specialSiteInfo.newsCatalog.url}/${specialSiteInfo.newses[i].id}/${f:toFriendlyUrl(specialSiteInfo.newses[i].title)}">${specialSiteInfo.newses[i].title}</a>
												</span>
											</li>
										</c:forEach>
									</ul>
								</c:if>
								<div class="XemChiTiet">
									<a href='${news_ct}/${specialSiteInfo.newsCatalog.url}'>${COMMONINFO.detailsCaption}</a>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${index + 1 < fn:length(specialSiteInfos)}">
						<c:set var="specialSiteInfo" value="${specialSiteInfos[index + 1]}" scope="request"></c:set>
						<div class="special_site_right">
							<div class="TieuDe">
								<div class="TieuDe_dau"></div>
								<div class="TieuDe_ND">
									<a href='${news_ct}/${specialSiteInfo.newsCatalog.url}'>${specialSiteInfo.newsCatalog.name}</a>
								</div>
								<div class="TieuDe_Cuoi"></div>
							</div>
							<div class="Khung">
								<c:if test="${specialSiteInfo.newses != null and not empty specialSiteInfo.newses}">
									<c:set var="news" value="${specialSiteInfo.newses[0]}" scope="request" />
									<div class="main_news">
										<c:choose>
											<c:when test="${not empty news.image}">
												<c:set var="img" value="${news.image}" scope="request"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="img" value="${ct}/resources/images/default.png" scope="request"></c:set>
											</c:otherwise>
										</c:choose>
										<a href='${news_ct}/${specialSiteInfo.newsCatalog.url}/${news.id}/${f:toFriendlyUrl(news.title)}'>
											<img class='news_thumb' src='${news.image}' alt="Ảnh" />
										</a>
										<p class="main_news_title">
											<a href="${news_ct}/${specialSiteInfo.newsCatalog.url}/${news.id}/${f:toFriendlyUrl(news.title)}">${news.title}</a>
										</p>
										<p class="lead">
											<c:out value="${news.summary}"></c:out>
										</p>
									</div>
								</c:if>
								<c:if test="${specialSiteInfo.newses != null and fn:length(specialSiteInfo.newses) gt 1}">
									<ul class="other_newses">
										<c:forEach begin="1" end="${fn:length(specialSiteInfo.newses) - 1}" step="1" var="i">
											<li>
												<span title='cssbody=[boxbody] singleclickstop=[on] cssheader=[boxheader] header=[${specialSiteInfo.newses[i].title}]  body=[${specialSiteInfo.newses[i].summary}]'>
													<a href="${news_ct}/${specialSiteInfo.newsCatalog.url}/${specialSiteInfo.newses[i].id}/${f:toFriendlyUrl(specialSiteInfo.newses[i].title)}">${specialSiteInfo.newses[i].title}</a>
												</span>
											</li>
										</c:forEach>
									</ul>
								</c:if>
								<div class="XemChiTiet">
									<a href='${news_ct}/${specialSiteInfo.newsCatalog.url}'>${COMMONINFO.detailsCaption}</a>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
	</c:if>
</div>