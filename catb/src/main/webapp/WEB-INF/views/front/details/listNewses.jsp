<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="exTag" uri="/WEB-INF/tag/extags.tld" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld"%>

<div id="Tin_Trong_DM">
	<div class="DanhMuc">
		<strong><a href="${ct}/home">${COMMONINFO.homePage}</a> > ${newsCatalog.name}</strong>
	</div>
	<c:forEach items="${newses}" var="news">
		<div class="TinCT">
			<a href="${news_ct}/${url}/${news.id}/${f:toFriendlyUrl(news.title)}"><img class="list_thumb" src="${news.image}" border="0"></a>
			<p class="title">
				<a href="${news_ct}/${url}/${news.id}/${f:toFriendlyUrl(news.title)}">${news.title}</a>
				<fmt:formatDate var="postedDate" value="${news.postedDate}" pattern="dd/MM/yyyy" />
				<span class="NgayCapNhat">(${postedDate})</span>
			</p>
			<div>
				${news.summary}
			</div>
		</div>
	</c:forEach>
	<div class="phantrang_repeater">
		<exTag:paging pageInfo="${pageInfo}" link="${news_ct}/${url}" cssClass="page_link" />
	</div>
</div>