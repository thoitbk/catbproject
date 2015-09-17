$(function() {
	var sliderContainerClone = $("#slider_container").clone();
	$(".image_gallery_title").click(function() {
		$("#slider_container").replaceWith(sliderContainerClone.clone());
		var imageGalleryUrl = $(this).attr("rel");
		displayImageGallery(imageGalleryUrl);
	});
	
	function displayImageGallery(imageGalleryUrl) {
		var imagesGallery = $('#images_gallery');
		$.ajax({
            type : "GET",
            url : imageGalleryUrl,
            dataType: "json",
            success : function(response) {
            	for (var i = 0; i < response.length; i++) {
            		var caption = response[i].caption;
            		var url = response[i].url;
            		var s = '<div><img u="image" src="' + url + '" /><img u="thumb" src="' + url + '" /><div u="caption" t="MCLIP|B" class="image_gallery_caption"><div class="image_gallery_caption_bg"></div><div class="image_gallery_caption_txt">' + caption + '</div></div></div>';
            		imagesGallery.append(s);
            	}
            	
            	$.fancybox({
        	        content: $('#slider_container'),
        	        scrolling: 'no',
        	        autoScale: false,
        	        fitToView: false,
        	        openEffect : 'elastic',
        	        closeEffect : 'elastic',
        	        minWidth : 640,
        	        minHeight: 380,
        	        width: 640,
        	        height: 380, 
        	        modal: true,
        	        showCloseButton: true,
        	        afterShow: function()
                    {
                        $('.fancybox-skin').append('<a title="Close" class="fancybox-item fancybox-close" href="javascript:jQuery.fancybox.close();"></a>');
                    }
        	    });
            	
            	var _SlideshowTransitions = [
                     {$Duration: 2000, x: 0.3, $During: { $Left: [0.3, 0.7] }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, x: -0.3, $SlideOut: true, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, x: -0.3, $During: { $Left: [0.3, 0.7] }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, x: 0.3, $SlideOut: true, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, y: 0.3, $During: { $Top: [0.3, 0.7] }, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, y: -0.3, $SlideOut: true, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, y: -0.3, $During: { $Top: [0.3, 0.7] }, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, y: 0.3, $SlideOut: true, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, x: 0.3, $Cols: 2, $During: { $Left: [0.3, 0.7] }, $ChessMode: { $Column: 3 }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, x: 0.3, $Cols: 2, $SlideOut: true, $ChessMode: { $Column: 3 }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, y: 0.3, $Rows: 2, $During: { $Top: [0.3, 0.7] }, $ChessMode: { $Row: 12 }, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, y: 0.3, $Rows: 2, $SlideOut: true, $ChessMode: { $Row: 12 }, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, y: 0.3, $Cols: 2, $During: { $Top: [0.3, 0.7] }, $ChessMode: { $Column: 12 }, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, y: -0.3, $Cols: 2, $SlideOut: true, $ChessMode: { $Column: 12 }, $Easing: { $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, x: 0.3, $Rows: 2, $During: { $Left: [0.3, 0.7] }, $ChessMode: { $Row: 3 }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, x: -0.3, $Rows: 2, $SlideOut: true, $ChessMode: { $Row: 3 }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, x: 0.3, y: 0.3, $Cols: 2, $Rows: 2, $During: { $Left: [0.3, 0.7], $Top: [0.3, 0.7] }, $ChessMode: { $Column: 3, $Row: 12 }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, x: 0.3, y: 0.3, $Cols: 2, $Rows: 2, $During: { $Left: [0.3, 0.7], $Top: [0.3, 0.7] }, $SlideOut: true, $ChessMode: { $Column: 3, $Row: 12 }, $Easing: { $Left: $JssorEasing$.$EaseInCubic, $Top: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2, $Outside: true }
                     , { $Duration: 2000, $Delay: 20, $Clip: 3, $Assembly: 260, $Easing: { $Clip: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, $Delay: 20, $Clip: 3, $SlideOut: true, $Assembly: 260, $Easing: { $Clip: $JssorEasing$.$EaseOutCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, $Delay: 20, $Clip: 12, $Assembly: 260, $Easing: { $Clip: $JssorEasing$.$EaseInCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     , { $Duration: 2000, $Delay: 20, $Clip: 12, $SlideOut: true, $Assembly: 260, $Easing: { $Clip: $JssorEasing$.$EaseOutCubic, $Opacity: $JssorEasing$.$EaseLinear }, $Opacity: 2 }
                     ];
            	
				var _CaptionTransitions = [];
				_CaptionTransitions["L"] = { $Duration: 1000, x: 0.6, $Easing: { $Left: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				_CaptionTransitions["R"] = { $Duration: 1000, x: -0.6, $Easing: { $Left: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				_CaptionTransitions["T"] = { $Duration: 1000, y: 0.6, $Easing: { $Top: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				_CaptionTransitions["B"] = { $Duration: 1000, y: -0.6, $Easing: { $Top: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				_CaptionTransitions["TL"] = { $Duration: 1000, x: 0.6, y: 0.6, $Easing: { $Left: $JssorEasing$.$EaseInOutSine, $Top: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				_CaptionTransitions["TR"] = { $Duration: 1000, x: -0.6, y: 0.6, $Easing: { $Left: $JssorEasing$.$EaseInOutSine, $Top: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				_CaptionTransitions["BL"] = { $Duration: 1000, x: 0.6, y: -0.6, $Easing: { $Left: $JssorEasing$.$EaseInOutSine, $Top: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				_CaptionTransitions["BR"] = { $Duration: 1000, x: -0.6, y: -0.6, $Easing: { $Left: $JssorEasing$.$EaseInOutSine, $Top: $JssorEasing$.$EaseInOutSine }, $Opacity: 2 };
				
				_CaptionTransitions["WAVE|L"] = { $Duration: 1500, x: 0.6, y: 0.3, $Easing: { $Left: $JssorEasing$.$EaseLinear, $Top: $JssorEasing$.$EaseInWave }, $Opacity: 2, $Round: { $Top: 2.5} };
				_CaptionTransitions["MCLIP|B"] = { $Duration: 1000, $Clip: 8, $Move: true, $Easing: $JssorEasing$.$EaseOutExpo };
            	
				var options = {
			         $AutoPlay: true,
			         $AutoPlayInterval: 2000,
			         $PauseOnHover: 1,  
			         $FillMode: 1,
			
			         $DragOrientation: 3,    
			         $ArrowKeyNavigation: true,
			         $SlideDuration: 1500,      
			
			         $SlideshowOptions: {      
			             $Class: $JssorSlideshowRunner$,
			             $Transitions: _SlideshowTransitions,    
			             $TransitionsOrder: 1,                   
			             $ShowLink: true                         
			         },
			         
			         $CaptionSliderOptions: {
						$Class: $JssorCaptionSlider$,
						$CaptionTransitions: _CaptionTransitions,
						$PlayInMode: 1,                          
						$PlayOutMode: 3                          
	                 },
			
			         $ArrowNavigatorOptions: {                   
			             $Class: $JssorArrowNavigator$,          
			             $ChanceToShow: 1                        
			         },
			
			         $ThumbnailNavigatorOptions: {               
			             $Class: $JssorThumbnailNavigator$,      
			             $ChanceToShow: 2,                       
			
			             $ActionMode: 1,                         
			             $SpacingX: 8,                           
			             $DisplayPieces: 10,                     
			             $ParkingPosition: 360                   
			         }
			     };
			
			     var jssor_slider1 = new $JssorSlider$("slider_container", options);
			     
			     function ScaleSlider() {
			         var parentWidth = jssor_slider1.$Elmt.parentNode.clientWidth;
			         if (parentWidth)
			             jssor_slider1.$ScaleWidth(Math.max(Math.min(parentWidth, 1800), 800));
			         else
			             window.setTimeout(ScaleSlider, 30);
			     }
			     ScaleSlider();
			
			     $(window).bind("load", ScaleSlider);
			     $(window).bind("resize", ScaleSlider);
			     $(window).bind("orientationchange", ScaleSlider);
			},
			error : function(response) {
				alert('Xảy ra lỗi khi tải ảnh');
			}
        });
	}
});