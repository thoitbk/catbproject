<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- fancybox -->
<script type="text/javascript" src="${ct}/fancybox-2/lib/jquery.mousewheel-3.0.6.pack.js"></script>
<script type="text/javascript" src="${ct}/fancybox-2/source/jquery.fancybox.js?v=2.1.5"></script>
<link rel="stylesheet" type="text/css" href="${ct}/fancybox-2/source/jquery.fancybox.css?v=2.1.5" media="screen" />

<!-- jssor -->
<script type="text/javascript" src="${ct}/jssor/js/jssor.js"></script>
<script type="text/javascript" src="${ct}/jssor/js/jssor.slider.js"></script>

<div id="Tin_Chi_Tiet">
	<div class="DanhMuc">
		<strong>THƯ VIỆN ẢNH</strong>
	</div>
	<c:if test="${fn:length(imageGalleries) > 0}">
		<table style="margin-top: 30px; margin-left: 20px; margin-right: 20px; width: 100%;">
			<c:forEach begin="0" end="${fn:length(imageGalleries) - 1}" step="3" var="index">
				<tr>
					<td style="width: 33%; padding: 5px; text-align: center; vertical-align: top;">
						<c:if test="${index < fn:length(imageGalleries)}">
							<c:set var="imageGallery" value="${imageGalleries[index]}" scope="request"></c:set>
							<span title='cssbody=[boxbody] singleclickstop=[on] cssheader=[boxheader] header=[${imageGallery.imageCatalog.name}]  body=[${imageGallery.imageCatalog.description}<br/>]'>
								<a href="javascript:void(0);" rel="${ct}/thu-vien-anh/${imageGallery.imageCatalog.id}/${f:toFriendlyUrl(imageGallery.imageCatalog.name)}" class="image_gallery_title">
									<img alt="" src="${imageGallery.images[0].file}" class="image_gallery_thumb" /><br />
									<span>${imageGallery.imageCatalog.name}</span>
								</a>
							</span>
						</c:if>
					</td>
					<td style="width: 33%; padding: 5px; text-align: center; vertical-align: top;">
						<c:if test="${index + 1 < fn:length(imageGalleries)}">
							<c:set var="imageGallery" value="${imageGalleries[index + 1]}" scope="request"></c:set>
							<span title='cssbody=[boxbody] singleclickstop=[on] cssheader=[boxheader] header=[${imageGallery.imageCatalog.name}]  body=[${imageGallery.imageCatalog.description}<br/>]'>
								<a href="javascript:void(0);" rel="${ct}/thu-vien-anh/${imageGallery.imageCatalog.id}/${f:toFriendlyUrl(imageGallery.imageCatalog.name)}" class="image_gallery_title">
									<img alt="" src="${imageGallery.images[0].file}" class="image_gallery_thumb" /><br />
									<span>${imageGallery.imageCatalog.name}</span>
								</a>
							</span>
						</c:if>
					</td>
					<td style="width: 33%; padding: 5px; text-align: center; vertical-align: top;">
						<c:if test="${index + 2 < fn:length(imageGalleries)}">
							<c:set var="imageGallery" value="${imageGalleries[index + 2]}" scope="request"></c:set>
							<span title='cssbody=[boxbody] singleclickstop=[on] cssheader=[boxheader] header=[${imageGallery.imageCatalog.name}]  body=[${imageGallery.imageCatalog.description}<br/>]'>
								<a href="javascript:void(0);" rel="${ct}/thu-vien-anh/${imageGallery.imageCatalog.id}/${f:toFriendlyUrl(imageGallery.imageCatalog.name)}" class="image_gallery_title">
									<img alt="" src="${imageGallery.images[0].file}" class="image_gallery_thumb" /><br />
									<span>${imageGallery.imageCatalog.name}</span>
								</a>
							</span>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>

<script type="text/javascript" src="${ct}/resources/js/imageGallery.js"></script>
<link rel="stylesheet" type="text/css" href="${ct}/resources/css/imageGallery.css" media="screen" />

<div id="slider_container" style="position: relative; top: 0px; left: 0px; width: 800px; height: 556px; background: #191919; overflow: hidden; display: none;">
    <!-- Loading Screen -->
    <div u="loading" style="position: absolute; top: 0px; left: 0px;">
        <div style="filter: alpha(opacity=70); opacity:0.7; position: absolute; display: block;
            background-color: #000000; top: 0px; left: 0px;width: 100%;height:100%;">
        </div>
        <div style="position: absolute; display: block; background: url(/jssor/img/loading.gif) no-repeat center center;
            top: 0px; left: 0px;width: 100%;height:100%;">
        </div>
    </div>
    <div u="slides" id="images_gallery" style="cursor: move; position: absolute; left: 0px; top: 0px; width: 800px; height: 456px; overflow: hidden;">
        
    </div>
    <span u="arrowleft" class="jssora05l" style="top: 158px; left: 8px;">
    </span>
    <span u="arrowright" class="jssora05r" style="top: 158px; right: 8px">
    </span>
    <div u="thumbnavigator" class="jssort01" style="left: 0px; bottom: 0px;">
        <div u="slides" style="cursor: default;" id="image_thumb_1">
            <div u="prototype" class="p">
                <div class=w><div u="thumbnailtemplate" class="t"></div></div>
                <div class=c></div>
            </div>
        </div>
    </div>
</div>