<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="Tin_Chi_Tiet">
	<div class="DanhMuc">
		<strong><a href="${ct}/home">${COMMONINFO.homePage}</a> > ${newsCatalog.name}</strong>
	</div>
	<div class="news_content">
		<div class="TieuDeTin">
			${news.title}
		</div>
		<p class="NgayCapNhat">
			<fmt:formatDate var="postedDate" value="${news.postedDate}" pattern="dd/MM/yyyy" />
			${COMMONINFO.postedDate} ${postedDate}
		</p>
		<span class="MoTa">${news.summary}</span>
		<div class="space">
		</div>
		<span class="news-detail">
			${content.content}
		</span>
		<div class="space">
		</div>
		<div class="Tacgia">
			${COMMONINFO.author} ${news.author}
		</div>
		<div class="space">
		</div>
		<div id="Tin_Chi_Tiet_Khac">
			<div class="TinCungChuDe">
				${COMMONINFO.sameSubjectTitle}
			</div>
			<div>
				<div id="">
					<ul>
						<c:forEach items="${newses}" var="news">
							<li>
								<fmt:formatDate var="postedDate" value="${news.postedDate}" pattern="dd/MM/yyyy" />
								<a href="${news_ct}/${url}/${news.id}/${f:toFriendlyUrl(news.title)}"><span title="cssbody=[boxbody] singleclickstop=[on] cssheader=[boxheader] header=[${news.title}] body=[${news.summary}]">${news.title}<i>(${postedDate})</i></span></a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="XemChiTiet">
				<a href="${news_ct}/${url}">${COMMONINFO.detailsCaption}</a>
			</div>
		</div>
	</div>
</div>