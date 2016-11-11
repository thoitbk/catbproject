<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<style>
		body {
			font-family: sans-serif,Arial,Verdana,"Trebuchet MS" !important;
			font-size: 12px !important;
		    width: 1000px !important;
		    margin: 0 auto !important;
		    padding: 0 auto !important;
		    text-align: left !important;
		    line-height: 15px !important;
		    margin: 5px;
		}
		.label {
			color: navy;
		}
	</style>
	<link href="${ct}/resources/css/news.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="banner">
	<img alt="" src="${ct}/resources/images/b.png" width="100%" />
</div>
<div class="news_content">
	<div class="TieuDeTin">
		${news.title}
	</div>
	<span class="MoTa" style="color: navy;">${news.summary}</span>
	<div class="space"></div>
	<span class="news-detail">
		${content.content}
	</span>
</div>
</body>
</html>