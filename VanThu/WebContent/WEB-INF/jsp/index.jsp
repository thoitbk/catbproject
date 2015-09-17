<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<head>
<title><tiles:insertAttribute name="title" /></title>
<link href="css/style_homepage.css" rel="stylesheet" type="text/css">

<!-- jQuery -->
<script src="js/jquery-1.js" type="text/javascript"></script>
<!-- end jQuery -->
<style type="text/css">
	.stretch {
		background-size: 100% 100%; /* w3 spec - no browser supports it yet */
		-moz-background-size: 100% 100%; /* used for firefox */
		-o-background-size: 100% 100%; /* used for opera */
		-webkit-background-size: 100% 100%; /* used for safari and chrome */
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$(".list-lv2").hide();
	});
	
	function closeGroup(control) {
		if (control == "" || $(control).hasClass("active"))
			return false;
		else {
			$(".list-lv2").hide();
			$(".img_body_ds li").removeClass("active");
			var id = $(control).attr('id');
			$("#" + id + " .list-lv2").show();
		}
	}
</script>
</head>
<body>
	<div id="wapper">
		<div id="top">
			<div class="banner-gt">
			</div>
			<div class="txt_top">
				<marquee direction="left" style="padding-top: 10px;" scrollamount="2" scrolldelay="10">CÔNG AN TỈNH THÁI BÌNH HỌC
					TẬP VÀ LÀM THEO TẤM GƯƠNG ĐẠO ĐỨC HỒ CHÍ MINH </marquee>
			</div>
		</div>
		<div id="Main">
			<div class="VB_new stretch"></div>
			<div id="Thongbao">
				<tiles:insertAttribute name="left" />
			</div>
		</div>
		<div id="Bottom">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>