$(document).ready(function() {
	
//	$(document).on({
//	    ajaxStart: function() { $('body').addClass("loading"); },
//	     ajaxStop: function() { $('body').removeClass("loading"); }    
//	});
	//var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
	var cp = '';
    
    var errorUrl = cp + '/cm/internalError';
    var unauthenticatedUrl = cp + '/cm/login';
    var unauthorizedUrl = cp + '/cm/unauthorized';
    var notExistedUrl = cp + '/cm/notExistedResource';
    var notOkUrl = cp + '/cm/requestError';
    
    $('#ngay').text(getDate());
    
    // Assign and revoke role to/from user
    $("#roleId").change(function () {
    	var id = $(this).find('option:selected').val();
    	if (id < 0) {
    		window.location.href = cp + '/cm/manageUserRole';
    	} else {
    		window.location.href = cp + '/cm/manageUserRole?id=' + id;
    	}
    });
    
    $('#assignRole').click(function(event) {
    	event.preventDefault();
    	
    	var _roleId = $('#roleId').val();
    	if (_roleId < 0) {
    		alert('Chưa chọn quyền');
    		return;
    	}
    	
    	var _userIds = $("#unAssigned option:selected").map(function(){
            return $(this).val();
        }).get();
    	if (_userIds == null || _userIds.length == 0) {
    		alert('Chưa chọn người dùng cần cấp quyền');
    		return;
    	}
    	
    	postUrl = cp + '/cm/assignRoleToUser';
    	reloadUrl = cp + '/cm/manageUserRole?id=' + _roleId;
    	
    	$("body").addClass("loading");
    	
    	$.ajax({
            type : "POST",
            url : postUrl,
            dataType: "json",
            data : {
            	roleId: _roleId,
                userIds: _userIds.toString()
            },
            success : function(response) {
            	reload(response.code, reloadUrl);
            },
            error : function(response) {
            	reload(response.code, reloadUrl);
            }
        });
    });
    
    $('#revokeRole').click(function(event) {
    	event.preventDefault();
    	
    	var _roleId = $('#roleId').val();
    	if (_roleId < 0) {
    		alert('Chưa chọn quyền');
    		return;
    	}
    	
    	var _userIds = $("#assigned option:selected").map(function(){
            return $(this).val();
        }).get();
    	if (_userIds == null || _userIds.length == 0) {
    		alert('Chưa chọn người dùng cần thu hồi quyền');
    		return;
    	}
    	
    	postUrl = cp + '/cm/revokeRoleFromUser';
    	reloadUrl = cp + '/cm/manageUserRole?id=' + _roleId;
    	
    	$('body').addClass("loading");
    	
    	$.ajax({
            type : "POST",
            url : postUrl,
            dataType: "json",
            data : {
            	roleId: _roleId,
                userIds: _userIds.toString()
            },
            success : function(response) {
            	reload(response.code, reloadUrl);
            },
            error : function(response) {
            	reload(response.code, reloadUrl);
            }
        });
    });
    
    // Assign permissions to roles
    $("#role").change(function () {
    	var id = $(this).find('option:selected').val();
    	if (id < 0) {
    		window.location.href = cp + '/cm/showPermission';
    	} else {
    		window.location.href = cp + '/cm/showPermission?id=' + id;
    	}
    });
    
    $('#updatePerOfRole').click(function(event) {
    	event.preventDefault();
    	
    	var _roleId = $('#role').val();
    	if (_roleId < 0) {
    		alert('Chưa chọn nhóm người dùng');
    		return;
    	}
    	var _permissionIds = $(".checkbox:checked").map(function(){
            return $(this).val();
        }).get();
    	
    	if (_permissionIds == null || _permissionIds.length == 0) {
    		_permissionIds = [-100];
    	}
    	
    	postUrl = cp + '/cm/changePermission';
    	reloadUrl = cp + '/cm/showPermission?id=' + _roleId;
    	
    	if (!confirm('Bạn có chắc chắn muốn cập nhật ?')) {
            return;
        }
    	
    	$('body').addClass("loading");
    	
    	$.ajax({
            type : "POST",
            url : postUrl,
            dataType: "json",
            data : {
            	roleId: _roleId,
                permissionIds: _permissionIds.toString()
            },
            success : function(response) {
            	reload(response.code, reloadUrl);
            },
            error : function(response) {
            	reload(response.code, reloadUrl);
            }
        });
    });
    
    // Manage adding news catalog
    $("#displayLocation").change(function () {
    	var location = $(this).find('option:selected').val();
    	if (location == null || location == '') {
    		window.location.href = cp + '/cm/newsCatalog/add';
    	} else {
    		window.location.href = cp + '/cm/newsCatalog/add?location=' + location;
    	}
    });
    
    $("#parentId").change(function () {
    	var location = $('#displayLocation').val();
    	var parent = $(this).find('option:selected').val();
    	var _url = '';
    	if (location == null || location == '') {
    		if (parent == null || parent < 0) {
    			_url = cp + '/cm/newsCatalog/add';
    		} else {
    			_url = cp + '/cm/newsCatalog/add?parent=' + parent;
    		}
    	} else {
    		if (parent == null || parent < 0) {
    			_url = cp + '/cm/newsCatalog/add?location=' + location;
    		} else {
    			_url = cp + '/cm/newsCatalog/add?location=' + location + "&parent=" + parent;
    		}
    	}
		window.location.href = _url;
    });
    
    // Update news catalog
    $("#updateDisplayLocation").change(function () {
    	var location = $(this).find('option:selected').val();
    	var postUrl = cp + '/cm/newsCatalogsByLocation';
    	reloadUrl = '/cm/newsCatalog/add';
    	if (location != null && location != '') {
    		postUrl = postUrl + "?location=" + location;
    	}
    	
    	$.ajax({
            type : "POST",
            url : postUrl,
            dataType: "json",
            success : function(response) {
            	if (response.code == null) {
            		$("#updateParentId").empty().append('<option value="-1">------ Chọn danh mục cha ------</option>');
            		jQuery.each(response, function(key, val) {
                		$("#updateParentId").append('<option value="' + key + '">' + val + '</option>');
            		});
            	} else {
            		reload(response.code, reloadUrl);
            	}
            },
            error : function(response) {
            	reload(response.code, reloadUrl);
            }
        });
    });
    
    // Update single news status
    var timer = null;
    $('.approveNews').click(function(event) {
    	event.preventDefault();
    	url = $(this).attr('href');
    	if (!confirm('Bạn có chắc chắn muốn duyệt tin này ?')) {
    		return;
    	}
    	clearTimer();
        var statusCol = $(this).parent().parent().find('td.statusCol');
        var successMsg = 'Tin đã duyệt';
        
    	postForApproveOrDeny(url, statusCol, successMsg);
    });
    
    $('.denyNews').click(function(event) {
    	event.preventDefault();
    	url = $(this).attr('href');
    	if (!confirm('Bạn có chắc chắn muốn từ chối tin này ?')) {
    		return;
    	}
    	clearTimer();
        var statusCol = $(this).parent().parent().find('td.statusCol');
        var successMsg = 'Tin bị từ chối';
        
    	postForApproveOrDeny(url, statusCol, successMsg);
    });
    
    function clearTimer() {
    	clearTimeout(timer);
        timer = null;
    }
    
    function postForApproveOrDeny(url, statusCol, successMsg) {
    	$('body').addClass("loading");
    	$.ajax({
            type : "POST",
            url : url,
            dataType: "json",
            success : function(response) {
            	$('body').removeClass("loading");
            	var statusCode = response.code;
	        	switch (statusCode) {
	        	case 1:
	        		handleApproveOrDenySuccess(statusCol, response.msg, successMsg);
	        		break;
	        	case 2:
	        		window.location.href = errorUrl;
	        		break;
	        	case 3:
	        		window.location.href = unauthenticatedUrl;
	        		break;
	        	case 4:
	        		window.location.href = unauthorizedUrl;
	        		break;
	        	case 5:
	        		window.location.href = notExistedUrl;
	        		break;
	        	default:
	        		window.location.href = notOkUrl;
	        		break;
	        	}
            },
            error : function(response) {
            	var errorString = 'Đã có lỗi xảy ra';
            	handlApproveOrDenyError(errorString);
            }
        });
    }
    
    function handleApproveOrDenySuccess(statusCol, responseString, statusString) {
    	if (statusCol != null) {
			statusCol.text(statusString);
		}
		$('#msg').empty();
		var append = '<div id="alert" class="alert-box success">' + responseString + '</div>';
		$('#msg').append(append).hide().fadeIn();
		timer = setTimeout(function() {
			$('#msg').fadeOut(300, function() { $(this).empty(); });
		}, 5000);
    }
    
    function handlApproveOrDenyError(errorString) {
    	$('#msg').empty();
    	$('body').removeClass("loading");
    	var append = '<div id="alert" class="alert-box success">' + errorString + '</div>';
    	$('#msg').append(append);
    	timer = setTimeout(function() {
			$('#msg').fadeOut(300, function() { $(this).empty(); });
		}, 5000);
    }
    
    // Update multiple news statuses
    $('#approveSelectedNews').click(function(event) {
    	event.preventDefault();
    	url = $(this).attr('href');
    	reloadUrl = window.location.href;
    	var warnMsg = 'Bạn có chắc chắn muốn duyệt các tin này ?';
    	
    	postForApproveOrDenySelected(url, reloadUrl, warnMsg);
    });
    
    $('#denySelectedNews').click(function(event) {
    	event.preventDefault();
    	url = $(this).attr('href');
    	reloadUrl = window.location.href;
    	var warnMsg = 'Bạn có chắc chắn muốn từ chối các tin này ?';
    	
    	postForApproveOrDenySelected(url, reloadUrl, warnMsg);
    });
    
    function postForApproveOrDenySelected(postUrl, reloadUrl, warnMsg) {
        var _ids = $(".checkbox:checked").map(function(){
            return $(this).val();
        }).get();
        if (_ids.length == 0) {
            alert('Chưa chọn mục nào');
            return;
        }
        if (!confirm(warnMsg)) {
            return;
        }
        
        $('body').addClass("loading");
        
        $.ajax({
            type : "POST",
            url : postUrl,
            dataType: "json",
            data : {
                ids: _ids.toString()
            },
            success : function(response) {
            	reload(response.code, reloadUrl);
            },
            error : function(response) {
            	reload(response.code, reloadUrl);
            }
        });
    }
    
    $('.hotNewsCheckbox').change(function() {
    	var _hotNews = false;
    	var confirmStr = '';
    	if ($(this).is(':checked')) {
    		_hotNews = true;
    		confirmStr = 'Bạn có chắc chắn muốn cập nhật thành tin nóng ?';
    	} else {
    		_hotNews = false;
    		confirmStr = 'Bạn có chắc chắn muốn bỏ tin nóng ?';
    	}
    	
    	if (!confirm(confirmStr)) {
    		if (!_hotNews) {
    			$(this).attr('checked', 'checked');
    		} else {
    			$(this).removeAttr('checked');
    		}
    		return;
    	}
    	
    	var _id = $(this).val();
    	var postUrl = cp + '/cm/news/updateHotNews';
    	var reloadUrl = window.location.href;
    	
    	$('body').addClass("loading");
    	
    	$.ajax({
            type : "POST",
            url : postUrl,
            dataType: "json",
            data : {
                id: _id,
                hot: _hotNews
            },
            success : function(response) {
            	reload(response.code, reloadUrl);
            },
            error : function(response) {
            	reload(response.code, reloadUrl);
            }
        });
    });
    
    // View news
    $('.news_title').click(function(event) {
    	event.preventDefault();
    	url = $(this).attr('href');
    	window.open(url, "_blank", "scrollbars=yes, resizable=yes, top=100, left=100, width=1100, height=600");
    });
    
    // Image management
//    $("#imageCatalogId").change(function () {
//    	var id = $(this).find('option:selected').val();
//    	if (id == null || id == '' || id < 0) {
//    		window.location.href = cp + '/cm/image/add';
//    	} else {
//    		window.location.href = cp + '/cm/image/add?cId=' + id;
//    	}
//    });
    
    // Video management
//    $("#videoCatalogId").change(function () {
//    	var id = $(this).find('option:selected').val();
//    	if (id == null || id == '' || id < 0) {
//    		window.location.href = cp + '/cm/video/add';
//    	} else {
//    		window.location.href = cp + '/cm/video/add?cId=' + id;
//    	}
//    });
    
    // ------------------------------- For delete functionalities ----------------------------------
    // select all checkboxs
    $('#selectAll').click(function(event) {
        if(this.checked) {
            $('.checkbox').each(function() {
                this.checked = true;
            });
        }else{
            $('.checkbox').each(function() {
                this.checked = false;
            });         
        }
    });
    
    $('#enablePassword').click(function() {
    	$('#password').attr("disabled",false);
    });
	
    // Delete position
    $("#delPosition").click(function(event){
        event.preventDefault();
        postUrl = $('#delPosition').attr('href');
        reloadUrl = cp + '/cm/position/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete department
    $("#delDepartment").click(function(event){
        event.preventDefault();
        postUrl = $('#delDepartment').attr('href');
        reloadUrl = cp + '/cm/department/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete user
    $("#delUser").click(function(event){
        event.preventDefault();
        postUrl = $('#delUser').attr('href');
        reloadUrl = cp + '/cm/user/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete role
    $("#delRole").click(function(event){
        event.preventDefault();
        postUrl = $('#delRole').attr('href');
        reloadUrl = cp + '/cm/role/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete permission
    $("#delPermission").click(function(event){
        event.preventDefault();
        postUrl = $('#delPermission').attr('href');
        reloadUrl = cp + '/cm/permission/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete newsCatalog
    $("#delNewsCatalog").click(function(event){
        event.preventDefault();
        postUrl = $('#delNewsCatalog').attr('href');
        reloadUrl = cp + '/cm/newsCatalog/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete news
    $("#delNews").click(function(event){
        event.preventDefault();
        postUrl = $('#delNews').attr('href');
        reloadUrl = window.location.href;
        post(postUrl, reloadUrl);
    });
    
    // Delete link catalog
    $("#delLink").click(function(event){
        event.preventDefault();
        postUrl = $('#delLink').attr('href');
        reloadUrl = cp + '/cm/linkCatalog/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete document type catalog
    $("#delDocumentType").click(function(event){
        event.preventDefault();
        postUrl = $('#delDocumentType').attr('href');
        reloadUrl = cp + '/cm/documentType/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete field
    $("#delField").click(function(event){
        event.preventDefault();
        postUrl = $('#delField').attr('href');
        reloadUrl = cp + '/cm/field/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete administrative procedure
    $("#delAdministrativeProcedure").click(function(event){
        event.preventDefault();
        postUrl = $('#delAdministrativeProcedure').attr('href');
        reloadUrl = cp + '/cm/administrativeProcedure/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete document
    $("#delDocument").click(function(event){
        event.preventDefault();
        postUrl = $('#delDocument').attr('href');
        reloadUrl = cp + '/cm/document/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete image catalog
    $("#delImageCatalog").click(function(event){
        event.preventDefault();
        postUrl = $('#delImageCatalog').attr('href');
        reloadUrl = cp + '/cm/imageCatalog/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete video catalog
    $("#delVideoCatalog").click(function(event){
        event.preventDefault();
        postUrl = $('#delVideoCatalog').attr('href');
        reloadUrl = cp + '/cm/videoCatalog/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete image
    $("#delImage").click(function(event){
        event.preventDefault();
        postUrl = $('#delImage').attr('href');
        reloadUrl = cp + '/cm/image/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete video
    $("#delVideo").click(function(event){
        event.preventDefault();
        postUrl = $('#delVideo').attr('href');
        reloadUrl = cp + '/cm/video/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete QA catalog
    $("#delQACatalog").click(function(event){
        event.preventDefault();
        postUrl = $('#delQACatalog').attr('href');
        reloadUrl = cp + '/cm/qaCatalog/add';
        post(postUrl, reloadUrl);
    });
    
    // Delete comment
    $("#delComment").click(function(event){
        event.preventDefault();
        postUrl = $('#delComment').attr('href');
        reloadUrl = cp + '/cm/comment/show';
        post(postUrl, reloadUrl);
    });
    
    // Delete denouncement
    $("#delDenouncement").click(function(event){
        event.preventDefault();
        postUrl = $('#delDenouncement').attr('href');
        reloadUrl = cp + '/cm/denouncement/show';
        post(postUrl, reloadUrl);
    });
    
    // Delete denouncement
    $("#delAdvertisement").click(function(event){
        event.preventDefault();
        postUrl = $('#delAdvertisement').attr('href');
        reloadUrl = cp + '/cm/adv/add';
        post(postUrl, reloadUrl);
    });
    
    // Send post request to specific url
    function post(postUrl, reloadUrl) {
        var _ids = $(".checkbox:checked").map(function(){
            return $(this).val();
        }).get();
        if (_ids.length == 0) {
            alert('Chưa chọn mục cần xóa');
            return;
        }
        if (!confirm('Bạn có chắc chắn muốn xóa ?')) {
            return;
        }
        
        $('body').addClass("loading");
        
        $.ajax({
            type : "POST",
            url : postUrl,
            dataType: "json",
            data : {
                ids: _ids.toString()
            },
            success : function(response) {
            	reload(response.code, reloadUrl);
            },
            error : function(response) {
            	reload(response.code, reloadUrl);
            }
        });
    }
    
    function reload(statusCode, reloadUrl) {
    	_reload = reloadUrl;
    	switch (statusCode) {
    	case 1:
    		_reload = reloadUrl;
    		break;
    	case 2:
    		_reload = errorUrl;
    		break;
    	case 3:
    		_reload = unauthenticatedUrl;
    		break;
    	case 4:
    		_reload = unauthorizedUrl;
    		break;
    	case 5:
    		_reload = notExistedUrl;
    		break;
    	default:
    		_reload = notOkUrl;
    		break;
    	}
    	window.location.href = _reload;
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
        
        return thu + ", ngày " + ngay + " tháng " + thang + " năm " + nam;
}