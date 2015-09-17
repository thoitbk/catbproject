$(document).ready(function() {
	$("#alert").hide().slideDown();
	setTimeout(function(){
		$("#alert").fadeOut();        
	}, 5000);
});