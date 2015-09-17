<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld"%>

<c:choose>
	<c:when test="${f:getUserInfo(pageContext.session).role == 'NORMAL_USER'}">
		<c:import url="/document/unreadDocuments.html"></c:import>
	</c:when>
	<c:when test="${f:getUserInfo(pageContext.session).role == 'VAN_THU'}">
		<c:import url="/document/simpleSearchDocuments.html"></c:import>
	</c:when>
	<c:when test="${f:getUserInfo(pageContext.session).role == 'ADMIN'}">
		<c:import url="/document/simpleSearchDocuments.html"></c:import>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>