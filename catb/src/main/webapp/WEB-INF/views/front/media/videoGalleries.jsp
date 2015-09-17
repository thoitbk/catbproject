<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="exTag" uri="/WEB-INF/tag/extags.tld" %>

<!-- jwplayer -->
<script type="text/javascript" src="${ct}/jwplayer/jwplayer.js"></script>
<script type="text/javascript">jwplayer.key="L+vphptqtQHGd4F4yQP0ujFMkZL+3WfIXul0HQ==";</script>

<div id="Tin_Chi_Tiet">
	<div class="DanhMuc">
		<strong>THƯ VIỆN VIDEO</strong>
	</div>
	<div class="showing_video">
		<div id="video_container_details">
			
		</div>
		<div class="video_title"></div>
	</div>
	<div class="other_video_list">Các video khác</div>
    <div>
    	<ul class="related_video_list">
	    	<c:forEach items="${videos}" var="video">
	    		<li><a href="javascript:void(0);" rel="${video.file}" class="other_video">${video.caption}</a></li>
	    	</c:forEach>
    	</ul>
    </div>
    <div class="phantrang_repeater">
		<exTag:paging pageInfo="${pageInfo}" link="${ct}/thu-vien-video" cssClass="page_link" />
	</div>
</div>

<script type="text/javascript">
	$(function () {
		var first = $('.related_video_list li:first a');
		if (first != null) {
			var url = first.attr('rel');
			var title = first.text();
			$('.video_title').text(title);
			jwplayer("video_container_details").setup({
                file: url,
                width: '80%',
                height: '80%',
                aspectratio: '16:10',
                autostart: false
            });
		}
		$('.other_video').click(function() {
			var url = $(this).attr('rel');
			var title = $(this).text();
			$('.video_title').text(title);
			jwplayer("video_container_details").setup({
                file: url,
                width: '80%',
                height: '80%',
                aspectratio: '16:10',
                autostart: true
            });
		});
	});
</script>