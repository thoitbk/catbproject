jQuery(document).ready(function() {

	$(".video").click(function() {
		$.fancybox({
			'padding'		: 0,
			'autoScale'		: false,
			'transitionIn'	: 'none',
			'transitionOut'	: 'none',
			'title'			: this.title,
			'width'			: 640,
			'height'		: 385,
			'type'			: 'swf',
			'swf'			: {
			'wmode'				: 'transparent',
			'allowfullscreen'	: 'true', 
			afterShow: function () {
				    // now, use JWPlayer to setup the player instead of embedding Flash
				    jwplayer('uploadedVideo').setup({
				    	file: 'http://localhost/video_gallery/2015-06-18/1434596742497.mp4'
				    });
			  	}
			}
		});

		return false;
	});



});
