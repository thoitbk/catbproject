$(function () {
	
    $('#fileupload').fileupload({
    	
        dataType: 'json',
        
        done: function (e, data) {
        	$("#uploaded-files").empty();
            $.each(data.result, function (index, file) {
            	
            	
                $("#uploaded-files").append(
                		$('<div/>')
                		.append(file.fileName)
                		.append("&nbsp;<a href='javascript:delDocument(" + file.id + ");'><img src='/images/deleteDocument.png' /></a>")
                		.attr('id', file.id)
                		
                		
//                		$('<tr/>')
//                		.append($('<td/>').text(file.fileName))
//                		.append($('<td/>').text(file.fileSize))
//                		.append($('<td/>').text(file.fileType))
//                		.append($('<td/>').html("<a href='upload?f="+index+"'>Click</a>"))
//                		.append($('<td/>').text("@"+file.twitter))

                		);//end $("#uploaded-files").append()
            });
        },
        
        progressall: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        var percent = $('#progress');
	        percent.text("Đang upload... " + progress + " %");
	        if (progress == 100) {
	        	percent.empty();
	        	percent.append("<span id='uploadsuccess'>Upload thành công</span>");
	        	$("#uploadsuccess").delay(2000).fadeOut();
//	        	percent.delay(2000).fadeOut();
	        }
	       /* $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );*/
   		},
   		
		dropZone: $('#dropzone')
    }).bind('fileuploadsubmit', function (e, data) {
        // The example input, doesn't have to be part of the upload form:
        var twitter = $('#twitter');
        data.formData = {twitter: twitter.val()};        
    });
});