$(document).ready(function() {
	$(".notify").delay(5000).fadeOut();
	$(".deleteLink").click(function() {
		return confirm("Bạn có chắc chắn muốn xóa ?");
	});
});