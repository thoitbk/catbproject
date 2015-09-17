<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" uri="/WEB-INF/tags/tags.tld" %>

<link href="/css/linkbutton.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/functions.js"></script>

<link rel="stylesheet" href="/css/jquery-ui.css">
<script src="/js/jquery-1.10.2.js"></script>
<script src="/js/jquery-ui.js"></script>

<style>
    label, input { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>
<script>
  $(function() {
    var documentType = $( "#documentType" );
    var allFields = $( [] ).add( documentType );
    var tips = $( ".validateTips" );
 
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight");
        tips.empty();
      }, 1500 );
    }
 
    function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips("Điền loại tài liệu");
        return false;
      } else {
        return true;
      }
    }
    
    $( "#createDocument" )
    .button()
    .click(function() {
      $( "#dialog-form" ).dialog( "open" );
    });
    
    $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 300,
      width: 350,
      modal: true,
      buttons: {
        "Thêm mới loại tài liệu": function() {
          var bValid = true;
          allFields.removeClass( "ui-state-error" );
 
          bValid = bValid && checkLength( documentType, "documentType", 1, 100 );
 
          if ( bValid ) {
        	  var name = documentType.val();
        	  $.getJSON( "/documentType/createDocumentType.html?typeName=" + name, function( json ) {
        		  updateTips(json.msg);
        		  if (json.code == 2) {
        			  window.location.reload(true);
        		  }
        		  $( this ).dialog( "close" );
       		  });
          }
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {
        allFields.val( "" ).removeClass( "ui-state-error" );
      }
    });
    
    var updateDocumentType = $("#updateDocumentType");
    var updateAllFields = $( [] ).add( updateDocumentType );
    var s;
    
    $(".updateDocumentType")
    	.button()
    	.click(function() {
    		s = $(this).attr('rel');
    		var arr = s.split(';');
    		$('#updateDocumentType').val(arr[1]);
    		$( "#update-dialog-form" ).dialog( "open" );
	    });
    
    $( "#update-dialog-form" ).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
          "Cập nhật loại tài liệu": function() {
            var bValid = true;
            updateAllFields.removeClass( "ui-state-error" );
   
            bValid = bValid && checkLength( updateDocumentType, "updateDocumentType", 1, 100 );

            if ( bValid ) {
          	  var newName = updateDocumentType.val();
          	  var arr = s.split(';');
          	  var oldName = arr[1];
          	  var id = arr[0];
          	  $.getJSON( "/documentType/updateDocumentType.html?id=" + id + "&oldName=" + oldName + "&newName=" + newName, function( json ) {
          		  updateTips(json.msg);
          		  if (json.code == 2) {
          			  window.location.reload(true);
          		  }
          		  $( this ).dialog( "close" );
          		  
         		  });
            }
          },
          Cancel: function() {
        	  s = null;
            $( this ).dialog( "close" );
          }
        },
        close: function() {
        	s = null;
          allFields.val( "" ).removeClass( "ui-state-error" );
        }
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
			Quản lý loại tài liệu
		</div>
	</div>
	<div class="content_960">
	</div>
</div>

<div id="dialog-form" title="Tạo loại tài liệu">
	<p class="validateTips"></p>
	<form>
		 <fieldset>
			<label for="name">Tên loại tài liệu</label>
			<input type="text" name="documentType" id="documentType" class="text ui-widget-content ui-corner-all">
		 </fieldset>
	</form>
</div>

<div id="update-dialog-form" title="Cập nhật loại tài liệu">
	<p class="validateTips"></p>
	<form>
		 <fieldset>
			<label for="name">Tên loại tài liệu</label>
			<input type="text" name="updateDocumentType" id="updateDocumentType" class="text ui-widget-content ui-corner-all">
		 </fieldset>
	</form>
</div>

<a href="javascript:void(0);" class="linkbutton" id="createDocument">Tạo loại tài liệu</a>

<div class="khung_content_960" id="gridCongViec">
	<div class="content-grid">
		<table class="gridView" cellspacing="1" style="display: block; clear: both; width: 100%;">
			<tr class="odd">
				<th style="width:3%" align="center">STT</th>
				<th style="width:91%" align="center">Tên loại tài liệu</th>
				<th style="width:3%" align="center">Sửa</th>
				<th style="width:3%" align="center">Xóa</th>
			</tr>
			<c:forEach items="${documentTypes}" var="d" varStatus="s">
				<c:choose>
					<c:when test="${s.count % 2 == 1}">
						<c:set var="c" value="odd"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="c" value="even"></c:set>
					</c:otherwise>
				</c:choose>
				
				<tr class="${c}">
					<td align="center">${s.count}</td>
					<td align="center">${d.typeName}</td>
					<td align="center">
						<a href="javascript:void(0);" class="updateDocumentType" rel="${d.id};${d.typeName}">
							<img src="/images/update.png" width="15" height="15" />
						</a>
					</td>
					<td align="center">
						<a href="/documentType/deleteDocumentType/${d.id}.html" class="deleteLink">
							<img src="/images/delete.png" width="15" height="15" />
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="bottom-pager">
			<div class="left">
				<div class="pages">
				</div>
			</div>
			<div class="right">
				Tổng số: ${fn:length(documentTypes)} kết quả
			</div>
		</div>
	</div>
</div>