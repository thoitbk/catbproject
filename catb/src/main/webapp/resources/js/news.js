$(document).ready(function() {
	
	var cp = '';
	
	$('#ngay').text(getDate());
	
    $("#linkSites").change(function () {
    	var link = $(this).find('option:selected').val();
    	if (link != null && link != '') {
    		var c = $(this).find('option:selected').attr('class');
    		if (c == "blank") {
    			window.open(link);
    		} else {
    			window.location.href = link;
    		}
    	}
    });
	
	// Get current date
	function getDate() {
	        var d = new Date();
	        var weekday = new Array(7);
	        weekday[0]=  "Chủ nhật";
	        weekday[1] = "Thứ hai";
	        weekday[2] = "Thứ ba";
	        weekday[3] = "Thứ tư";
	        weekday[4] = "Thứ năm";
	        weekday[5] = "Thứ sáu";
	        weekday[6] = "Thứ bảy";

	        var thu = weekday[d.getDay()];
	        var ngay = d.getDate();
	        var thang = d.getMonth() + 1;
	        var nam = d.getFullYear();
	        
	        return "Hôm nay, " + thu + " ngày " + ngay + "/" + thang + "/" + nam;
	}
});