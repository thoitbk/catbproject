$(document).ready(function(){	
		$('.selected_item').find('.child').show();
		$('ul.child li:first').css("height","30px").css("line-height","30px");
		$('ul.child li:last').css("border-bottom","solid 1px #666");
		$('.item').mouseenter(function(){
			if ( $(this).hasClass('selected_item') == false ) {
				$(this).find('.counts').addClass('selected_counts');
			}
		});
		$('.item').mouseleave(function(){
			if ( $(this).hasClass('selected_item') == false ) {
				$(this).find('.counts').removeClass('selected_counts');
			}
		});

		$('.item').click(function(){
			var item	= $(this);
			$('.selected_item').each(function(){
				if (item.attr('id')!=$(this).attr('id')) {
					var child	= $(this).find('.child');
					if (child.parent().attr("id")) {
						$(this).find('.child').slideUp('slow', function(){
							$(this).parent().removeClass('selected_item').find('.counts').removeClass('selected_counts');
						});
					} else {
						$(this).removeClass('selected_item').find('.counts').removeClass('selected_counts');
					}
				}
			});
			item.addClass('selected_item').find('.counts').addClass('selected_counts');
			item.find('.child').slideDown();
		});
	});