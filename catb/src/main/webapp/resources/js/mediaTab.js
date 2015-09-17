jQuery(document).ready(function ($) {
	
	$('#tabs').tabs({
		activate: function(event, ui){
			var selected = ui.newTab.index();
			if (selected == 1) {
				$.ajax({
		            type : "GET",
		            url : '/videos',
		            dataType: "json",
		            success : function(response) {
		            	var videoContainer = $('#video_container');
		            	var videoList = $('#video_list');
		            	videoContainer.empty();
		            	videoList.empty();
		            	if (response != null && response.length > 0) {
		            		var firstUrl = response[0].file;
		            		jwplayer("video_container").setup({
			                    file: firstUrl,
			                    width: '100%',
			                    height: '100%',
			                    aspectratio: '16:12',
			                    autostart: false
			                });
		            		var list = videoList.append('<ul class="video_ul"></ul>').find('ul');
		            		for (var i = 0; i < response.length; i++) {
		            			var caption = response[i].caption;
		            			var url = response[i].file;
		            			var s = '<li><a href="javascript:void(0);" rel="' + url + '" class="other_video">' + caption + '</a></li>';
		            			list.append(s);
		            		}
		            	}
					},
					error : function(response) {
						alert('Xảy ra lỗi khi tải video');
					}
		        });
			}
		}
	});
	
    var options = {
        $AutoPlay: true, 
        $AutoPlaySteps: 1,
        $AutoPlayInterval: 2000,
        $PauseOnHover: 1,       

        $ArrowKeyNavigation: true,
        $SlideEasing: $JssorEasing$.$EaseOutQuint,
        $SlideDuration: 800,                      
        $MinDragOffsetToSlide: 20,                
        //$SlideWidth: 600,                       
        //$SlideHeight: 300,                      
        $SlideSpacing: 0, 					      
        $DisplayPieces: 1,                        
        $ParkingPosition: 0,                      
        $UISearchMode: 1,                         
        $PlayOrientation: 1,                      
        $DragOrientation: 1,                      

        $ArrowNavigatorOptions: {                 
            $Class: $JssorArrowNavigator$,        
            $ChanceToShow: 2,                     
            $AutoCenter: 2,                       
            $Steps: 1,                            
            $Scale: false                         
        },

        $BulletNavigatorOptions: {                
            $Class: $JssorBulletNavigator$,       
            $ChanceToShow: 2,                     
            $AutoCenter: 1,                       
            $Steps: 1,                            
            $Lanes: 1,                            
            $SpacingX: 12,                        
            $SpacingY: 4,                         
            $Orientation: 1,                      
            $Scale: false                         
        }
    };

    $("#image_slider").css("display", "block");
    var jssor_slider1 = new $JssorSlider$("image_slider", options);

    function ScaleSlider() {
        var parentWidth = jssor_slider1.$Elmt.parentNode.clientWidth;
        if (parentWidth) {
            jssor_slider1.$ScaleWidth(parentWidth - 0);
        }
        else
            window.setTimeout(ScaleSlider, 0);
    }
    ScaleSlider();

    $(window).bind("load", ScaleSlider);
    $(window).bind("resize", ScaleSlider);
    $(window).bind("orientationchange", ScaleSlider);
    
	$("#video_list").on('click', ".other_video", function() {
		var url = $(this).attr("rel");
		$(".selected_video").removeClass('selected_video');
		$(this).addClass("selected_video");
		jwplayer("video_container").setup({
            file: url,
            width: '100%',
            height: '100%',
            aspectratio: '16:12',
            autostart: true
        });
	});
});