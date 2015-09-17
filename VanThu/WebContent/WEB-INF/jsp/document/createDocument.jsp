<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/js/jquery-1.10.2.js"></script>
<script src="/js/vendor/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/jquery.fileupload.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/js/myuploadfunction.js"></script>
<script type="text/javascript" src="/js/functions.js"></script>
<link href="/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="/css/dropzone.css" type="text/css" rel="stylesheet" />
<link href="/css/mystyle.css" rel="stylesheet">
<link href="/css/style.css" rel="stylesheet">
<link href="/css/MVP.css" rel="stylesheet" type="text/css" />
<link href="/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-ui.js"></script>

<style type="text/css">
#upload-file-container {
   width: 200px;
   height: 50px;
   position: relative;
   border: dashed 1px black;
   overflow: hidden;
}

#upload-file-container input[type="file"]
{
   margin: 0;
   opacity: 0;   
   font-size: 100px;
}

#container {
    width: 200px;
    height: 50px;
    position: relative;
}

#dropzone, 
#upload-file-container {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
}

#upload-file-container {
    z-index: 10;
}

span.icon_clear{
    position:absolute;
    right:10px;    
    top: 0px;
    cursor:pointer;
    font: bold 1em sans-serif;
    color:#38468F;  
}

.clearable {
	position: relative;
}

</style>

<script type="text/javascript">
$(function() {
	
	var __senders = [];
	var __receivers = [];
	
	<c:forEach items="${pDepartments}" var="pDepartment" varStatus="s">
		__senders[${s.count - 1}] = ${pDepartment.key};
	</c:forEach>
	<c:forEach items="${departments}" var="department" varStatus="s">
		__receivers[${s.count - 1}] = ${department.key};
	</c:forEach>
	
	jQuery.expr[':'].Contains = function(a,i,m){
      return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
  	};
	
	$('#searchReceiver').keyup(function(){
	   var valThis = $(this).val().toLowerCase();
	   $("#receivers_").find("option:Contains(" + valThis + ")").show();
	   $("#receivers_").find("option:not(:Contains(" + valThis + "))").hide();
	});
	$('#searchSender').keyup(function(){
	   var valThis = $(this).val().toLowerCase();
	   $("#senders_").find("option:Contains(" + valThis + ")").show();
	   $("#senders_").find("option:not(:Contains(" + valThis + "))").hide();
	});
	
	$.datepicker.regional['vi'] = {
            closeText: 'Đóng',
            prevText: '&#x3c;Trước',
            nextText: 'Tiếp&#x3e;',
            currentText: 'Hôm nay',
            monthNames: ['Tháng Một', 'Tháng Hai', 'Tháng Ba', 'Tháng Tư', 'Tháng Năm', 'Tháng Sáu',
            'Tháng Bảy', 'Tháng Tám', 'Tháng Chín', 'Tháng Mười', 'Tháng Mười Một', 'Tháng Mười Hai'],
            monthNamesShort: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
            'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
            dayNames: ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'],
            dayNamesShort: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
            dayNamesMin: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
            weekHeader: 'Tu',
            dateFormat: 'dd/mm/yy',
            firstDay: 0,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''};
    $.datepicker.setDefaults($.datepicker.regional['vi']);
	$( "#publishDate" ).datepicker();
	$(".icon_clear").click(function() {
		$("#publishDate").val('');
	});
	
	$("#addSender").click(function() {
		$('#senders_ :selected').each(function(i, selected) {
		    value = $(selected).val();
		    text = $(selected).text();
		    $("#senders").append("<option value='" + value + "'>" + text + "</option>");
		    $(selected).remove();
		});
	});
	$("#removeSender").click(function() {
		$('#senders :selected').each(function(i, selected) {
		    value = $(selected).val();
		    text = $(selected).text();
			var ___senders = $.map($('#senders_ option'), function(e) {
				return e.value; 
			});
		    var pos = 0;
		    for (var i = 0; i < __senders.length; i++) {
		    	if (inArray(__senders[i], ___senders)) {
		    		pos++;
		    	} else if (__senders[i] == value) {
		    		break;
		    	}
		    }
		    if (pos > 0) {
		    	$("#senders_ option").eq(pos - 1).after("<option value='" + value + "'>" + text + "</option>");
		    } else {
		    	$("#senders_").prepend("<option value='" + value + "'>" + text + "</option>");
		    }
		    $(selected).remove();
		});
	});
	
	function inArray(v, arr) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i] == v) {
				return true;
			}
		}
		return false;
	}
	
	$("#addReceiver").click(function() {
		$('#receivers_ :selected').each(function(i, selected) {
		    value = $(selected).val();
		    text = $(selected).text();
		    $("#receivers").append("<option value='" + value + "'>" + text + "</option>");
		    $(selected).remove();
		});
	});
	$("#removeReceiver").click(function() {
		$('#receivers :selected').each(function(i, selected) {
		    value = $(selected).val();
		    text = $(selected).text();
		    var ___receivers = $.map($('#receivers_ option'), function(e) {
				return e.value; 
			});
		    var pos = 0;
		    for (var i = 0; i < __receivers.length; i++) {
		    	if (inArray(__receivers[i], ___receivers)) {
		    		pos++;
		    	} else if (__receivers[i] == value) {
		    		break;
		    	}
		    }
		    if (pos > 0) {
		    	$("#receivers_ option").eq(pos - 1).after("<option value='" + value + "'>" + text + "</option>");
		    } else {
		    	$("#receivers_").prepend("<option value='" + value + "'>" + text + "</option>");
		    }
		    $(selected).remove();
		});
	});
	$("#createDocumentViewModel").submit(function(event) {
		event.preventDefault();
		var _sign = $("#sign").val();
		var _type = $("#type").val();
		var _publishDate = $("#publishDate").val();
		var _confidentialLevel = $("#confidentialLevel").val();
		var _urgentLevel = $("#urgentLevel").val();
		var _signer = $("#signer").val();
		var _abs = $("#abs").val();
		var _senders = $.map($('#senders option'), function(e) {
			return e.value; 
		});
		var _receivers = $.map($('#receivers option'), function(e) {
			return e.value; 
		});
		var _sendMail = $("#sendMail").prop('checked');
		
		/* $.ajax({
	            type: "POST",
	            url: $(this).attr("action"),
	            data: { 
	    			sign: _sign, type: _type, 
	    			publishDate: _publishDate, 
	    			confidentialLevel: _confidentialLevel, 
	    			urgentLevel: _urgentLevel, 
	    			signer: _signer, 
	    			abs: _abs, 
	    			senders: JSON.stringify(_senders), 
	    			receivers: JSON.stringify(_receivers)},
	            dataType: "json",
	            success: function (data) {
	            	alert(data.msg);
	            },
	            error: function (result) {
	                alert("Error");
	            }
	        });*/
		
		$.post($(this).attr("action"), { 
			sign: _sign, type: _type, 
			publishDate: _publishDate, 
			confidentialLevel: _confidentialLevel, 
			urgentLevel: _urgentLevel, 
			signer: _signer, 
			abs: _abs, 
			senders: JSON.stringify(_senders), 
			receivers: JSON.stringify(_receivers),
			sendMail: _sendMail})
		  .done(function( data ) {
			  $("#error").empty();
			  if (data.code == 0) {
				  $("#error").html(data.msg);
			  } else if (data.code == 1) {
				  window.location.href="/document/createDocument.html";
			  }
		  });
	});
});
</script>

<c:if test="${msg != null}">
	<div class="notify">
		<c:out value="${msg}" />
	</div>
	<c:remove var="msg" scope="session"/>
</c:if>
<br />

<div class="box960">
	<div class="title">
		<div class="left"></div>
		<div class="right"></div>
		<div class="text_form" style="width: 320px;">
			<img src="/images/left_title.png" style="float: left"> 
			<img src="/images/right_title.png" style="float: right">
			Tạo mới văn bản đi
		</div>
	</div>
	<div class="content_960">
		<form action="/document/createDocument.html" method="get" id="createDocumentViewModel">
			<div class="errorblock" id="error"></div>
			<table class="table-form" style="width: 80%;" align="center">
				<tr>
					<td class="label" style="width: 30%;">Số ký hiệu</td>
					<td class="data txtinput"><input type="text" name="sign" id="sign" value="${documentNumber}/" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Loại văn bản</td>
					<td class="data txtinput">
						<select name="type" id="type">
							<option value="-1">---- Chọn loại văn bản ----</option>
							<c:forEach items="${documentTypes}" var="documentType">
								<option value="${documentType.key}">${documentType.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Ngày ban hành</td>
					<td class="data txtinput">
						<span class="clearable">
							<input type="text" name="publishDate" id="publishDate" readonly="readonly" />
							<span class="icon_clear">x</span>
						</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Độ mật</td>
					<td class="data txtinput">
						<select name="confidentialLevel" id="confidentialLevel">
							<option value="-1">---- Chọn độ mật ----</option>
							<c:forEach items="${confidentialLevels}" var="confidentialLevel">
								<option value="${confidentialLevel.key}">${confidentialLevel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Độ khẩn</td>
					<td class="data txtinput">
						<select name="urgentLevel" id="urgentLevel">
							<option value="-1">---- Chọn độ khẩn ----</option>
							<c:forEach items="${urgentLevels}" var="urgentLevel">
								<option value="${urgentLevel.key}">${urgentLevel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Người ký</td>
					<td class="data txtinput"><input type="text" name="signer" id="signer" /></td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Trích yếu</td>
					<td class="data txtinput"><textarea name="abs" id="abs" cols="10" rows="5"></textarea></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="text" id="searchSender" style="width: 50%" /> &#8592; Nhập để lọc nơi gửi</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Nơi gửi</td>
					<td>
						<select multiple="multiple" size="8" style="width:40%;" id="senders_" name="senders_" class="senders_">
							<c:forEach items="${pDepartments}" var="pDepartment">
								<option value="${pDepartment.key}">${pDepartment.value}</option>
							</c:forEach>
						</select>
						<span style="display: inline-block; width: 70px; vertical-align: top; padding-top: 30px; padding-left: 20px;">
							<a id="addSender" href="javascript:void(0);" class="SaveButton"> >> </a> <p></p>
							<a id="removeSender" href="javascript:void(0);" class="SaveButton"> << </a>
						</span>
						<select  name="senders" id="senders" size="8" style="width:40%;" multiple="multiple">
						</select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="text" id="searchReceiver" style="width: 50%" /> &#8592; Nhập để lọc nơi nhận</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Nơi nhận</td>
					<td>
						<select multiple="multiple" size="8" style="width: 40%" id="receivers_" name="receivers_" class="receivers_">
							<c:forEach items="${departments}" var="department">
								<option value="${department.key}">${department.value}</option>
							</c:forEach>
						</select>
						<span style="display: inline-block; width: 70px; vertical-align: top; padding-top: 30px; padding-left: 20px;">
							<a id="addReceiver" href="javascript:void(0);" class="SaveButton"> >> </a> <p></p>
							<a id="removeReceiver" href="javascript:void(0);" class="SaveButton"> << </a>
						</span>
						<select  name="receivers" id="receivers" size="8" style="width: 40%" multiple="multiple">
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">File văn bản</td>
					<td>
						<div id="container" style="float: left;">
							<div id="upload-file-container" class="fade well">
								<input id="fileupload" type="file" name="files[]" data-url="/document/uploadDocument.html" multiple>
							</div>
							<div id="dropzone" class="fade well">
							Chọn file
							</div>
						</div>
						<div style="margin-left: 250px; margin-top: 15px;" id="progress"></div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<div id="uploaded-files">
							<c:if test="${files != null && !empty files}">
								<c:forEach items="${files}" var="file">
									<div id="${file.id}">
										${file.fileName} &nbsp;<a href='javascript:delDocument("${file.id}");'><img src='/images/deleteDocument.png' /></a>
									</div>
								</c:forEach>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 30%;">Gửi mail</td>
					<td>
						<input type="checkbox" name="sendMail" id="sendMail" />
					</td>
				</tr>
				<tr class="submit">
					<td colspan="2" align="center">
						<input type="submit" value="Thêm mới" class="SaveButton" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
function delDocument(id) {
	$.getJSON( "/document/deleteDocument.html?id=" + id, function( json ) {
		if (json.code == 1) {
			$("#" + id).remove();
		}
	});
}
</script>