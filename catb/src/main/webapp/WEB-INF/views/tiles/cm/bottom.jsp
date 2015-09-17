<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="exTag" uri="/WEB-INF/tag/extags.tld" %>
<script>            
	jQuery(document).ready(function() {
		var offset = 220;
		var duration = 500;
		jQuery(window).scroll(function() {
			if (jQuery(this).scrollTop() > offset) {
				jQuery('.back-to-top').fadeIn(duration);
			} else {
				jQuery('.back-to-top').fadeOut(duration);
			}
		});
		
		jQuery('.back-to-top').click(function(event) {
			event.preventDefault();
			jQuery('html, body').animate({scrollTop: 0}, duration);
			return false;
		})
	});
</script>
<div class="Duong_ke"></div>
<div class="Thong_Tin">
	<div style="text-align: center; position: relative;">
		<span style="font-weight: bold; color: #830909;">Cổng thông tin điện tử Công an tỉnh Thái Bình</span><br />
		<span style="color: #805050;">
			Địa chỉ: Đường Lê Quý Đôn - Thành phố Thái Bình - Tỉnh Thái Bình. Điện thoại: (036) 3870100 <br />
			Email: conganthaibinh@gmail.com - Website: http://conganthaibinh.gov.vn <br />
			Bản quyền thuộc về Công an tỉnh Thái Bình
		</span>
		<div class="dev"><exTag:cr/></div>
	</div>
</div>