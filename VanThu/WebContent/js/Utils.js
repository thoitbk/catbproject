
var urlView = "", viewHeight = 1500, viewWidth = 800;
var urlForm = "", formHeight = 1500, formWidth = 800;
var imageLoading = '<div class="img-loading" style=\"font:12px Arial; width:100%; margin:0 auto; padding:10px 0px;\"><img src="/images/ajax-loader.gif" /><span>Đang tải dữ liệu...</span></div>';
var imageImporting = '<div class="img-loading" style=\"font:12px Arial; width:100%; margin:0 auto; padding:10px 0px;\"><img src="/images/ajax-loader.gif" /><span>Đang thêm dữ liệu...</span></div>';

var imageProcessing = '<div class="img-loading" style=\"font:12px Arial; width:100%; margin:0 auto; padding:10px 0px;\"><img src="/images/ajax-loader.gif" /><span>Đang xử lý dữ liệu...</span></div>';

var jMVP = jQuery.noConflict();
var blFlagSelectTS = false;
var IsUploadImage = "0";
var listcontrolnotallow = "";
//format html 
function escapeHTML(str) {
    var div = document.createElement('div');
    var text = document.createTextNode(str);
    div.appendChild(text);
    return div.innerHTML;
}

function formatMoney(valueFormat, seperatorPlace, decimalPlace, numOfDec, maxValue, minValue, maxOverMes, minLowerMes) {
    var num = jMVP.trim(valueFormat);
    if (num == '' || num == null) {
        return '';
    }

    if (num > maxValue)
        obj.title = maxOverMes;
    if (num < minValue)
        obj.title = minLowerMes;

    var nDecimal = Math.pow(10, numOfDec);
    num = num.toString().replace(RegExp(seperatorPlace == '.' ? '\\.' : seperatorPlace, 'g'), '');
    num = num.toString().replace(RegExp(decimalPlace, 'g'), '.');
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num)) num = '0';
    sign = (num == (num = Math.abs(num)));
    num = Math.round(num * nDecimal);

    cents = num % nDecimal;
    num = Math.floor(num / nDecimal).toString();
    cents_temp = cents.toString();
    for (var k = numOfDec; k > 0; k--) {
        if (cents < Math.pow(10, k - 1) && cents >= Math.pow(10, k - 2)) {
            for (var c = 0; c < numOfDec - k + 1; c++)
                cents_temp = '0' + cents_temp;
        }
    }
    var cents_rel = cents_temp;

    for (var j = cents_temp.length; j >= 0; j--) {
        if (cents_temp.charAt(j - 1) == '0')
            cents_rel = cents_rel.substring(0, j - 1);
        else
            j = -1;
    }

    if (cents == 0) cents_rel = '';
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + seperatorPlace + num.substring(num.length - (4 * i + 3));
    if (cents_rel != '')
        return (((sign) ? '' : '-') + num + decimalPlace + cents_rel);
    else
        return (((sign) ? '' : '-') + num);
}

function createUploader(btnupload, ullistFileAttach, ullistFileAttachRemove, hdlistValueFileAttach, hdListFileRemove) {
    var uploader = new qq.FileUploader({
        element: jMVP("#" + btnupload)[0],
        action: '/WebUI/UploadFile/UploadFile.aspx',
        sizeLimit: 15 * 1024 * 1024,
        messages: {
            typeError: "{file} không được phép upload. Chỉ có định dạng {extensions} mới được upload.",
            sizeError: "{file} quá lớn, Độ lớn tối đa cho phép upload là {sizeLimit}."
        },
        showMessage: function (message) {
            createMessage("Có lỗi xảy ra ", message);
        },
        onSubmit: function (id, fileName) {
            // check trùng file
            var exits = false;
            //check trong trường hợp mới upload
            jMVP("#" + ullistFileAttach + " li").each(function (index, item) {
                if (fileName == jMVP(this).children("span").attr("title"))
                    exits = true;
            });
            //check trên file đã upload
            jMVP("#" + ullistFileAttachRemove + " li").each(function (index, item) {
                if (fileName == jMVP(this).children("span").attr("title"))
                    exits = true;
            });
            if (exits) {
                createMessage("Thông báo", fileName + " Đã tồn tại.");
                return false;
            }
        },
        onComplete: function (id, fileName, responseJSON) {
            jMVP(".qq-upload-list").html("");
            if (responseJSON.upload) {
                jMVP("#" + ullistFileAttach).append(getHTMLDeleteLink(responseJSON, hdlistValueFileAttach, ullistFileAttach, ullistFileAttachRemove));
                jMVP("#" + hdlistValueFileAttach).val(changeHiddenInput(ullistFileAttach));
                //jMVP("#" + hdlistValueFileAttach).val(changeHiddenInput(hdListFileRemove)); 
            }
        }
    });
}

function createUploaderUnique(btnupload, ullistFileAttach, ullistFileAttachRemove, hdlistValueFileAttach, hdListFileRemove, typeallow) {
    var uploader = new qq.FileUploader({
        element: jMVP("#" + btnupload)[0],
        action: '/WebUI/UploadFile/UploadFile.aspx?tylealow=1',
        allowedExtensions: typeallow,
        messages: {
            typeError: "{file} không được phép upload. Chỉ có định dạng {extensions} mới được upload."
        },
        showMessage: function (message) {
            createMessage("Có lỗi xảy ra ", message);
        },
        onSubmit: function (id, fileName) {
            // check trùng file
            var exits = false;
            //check trong trường hợp mới upload
            jMVP("#" + ullistFileAttach + " li").each(function (index, item) {
                if (fileName == jMVP(this).children("span").attr("title"))
                    exits = true;
            });
            //check trên file đã upload
            jMVP("#" + ullistFileAttachRemove + " li").each(function (index, item) {
                if (fileName == jMVP(this).children("span").attr("title"))
                    exits = true;
            });
            if (exits) {
                createMessage("Thông báo", fileName + " Đã tồn tại.");
                return false;
            }
        },
        onComplete: function (id, fileName, responseJSON) {
            if (responseJSON.upload) {
                jMVP("#" + ullistFileAttach + "").append(getHTMLDeleteLinkUnique(responseJSON, btnupload, hdlistValueFileAttach, ullistFileAttach, ullistFileAttachRemove));
                jMVP("#" + hdlistValueFileAttach + "").val(changeHiddenInputUnique(ullistFileAttach))

            } else {
                //                if (responseJSON.message == undefined) {
                //                    var domain = window.location.protocol + '/' + window.location.host;
                //                    var currentRequest = escape((window.location.href).replace(domain, ''));
                //                    currentRequest = '/login.aspx?ReturnUrl=' + currentRequest;
                //                    window.location.href = currentRequest;
                //                }
                //                else
                //                    createMessage("Thông báo", responseJSON.message);
            }
        }
    });
}
function DeleteFileUpdateUnique(file, btnUpload, ullistFileAttachRemove) {
    var linkDelete = jMVP("#listValueFileAttachRemove").val();
    jMVP("#listValueFileAttachRemove").val(linkDelete + file + "#");
    jMVP("#" + ullistFileAttachRemove + " span[id=" + file + "]").parent().remove();
    jMVP("#" + btnUpload).css("display", "");
    jMVP(".qq-upload-list").html("");
}
//lấy dữ liệu từ list Unique
function changeHiddenInputUnique(ollistFileAttach) {
    var valueFile = '[';
    var total = jMVP("#" + ollistFileAttach + " li").length;
    jMVP("#" + ollistFileAttach + " li").each(function (i) {
        valueFile += '{"FileServer": "' + jMVP(this).children("span").attr("id") + '"\,';
        valueFile += '"FileName": "' + jMVP(this).children("span").attr("title") + '"\}';
        if (i + 1 < total)
            valueFile += ',';
    });
    valueFile += "]";
    return valueFile;
}
//Lấy về html file của upload unique
function getHTMLDeleteLinkUnique(data, btnupdate, hdlistValueFileAttach, ullistFileAttach, ullistFileAttachRemove) {
    jMVP("#" + btnupdate).css("display", "none");
    return "<li><span id=\"" + data.fileserver + "\" title=\"" + data.filename + "\">" +
                    "<a href=\"/webui/file/tmp/" + data.fileserver + "\"  target=\"_blank\">" +
                        data.filename + "</a></span><a href=\"javascript:DeleteFileUnique('" + data.fileserver + "','" + btnupdate + "','" +
                                                                                        hdlistValueFileAttach + "','" +
                                                                                        ullistFileAttach + "','" +
                                                                                        ullistFileAttachRemove + "', '0', '');\"><img src=\"/images/act_icon/act_filedelete.png\" title=\"Xóa file đính kèm\" border=\"0\"></a></li>";
}
//xóa file Unique
function DeleteFileUnique(file, btnupdate, listValueFileAttach, ullistFileAttach, ullistFileAttachRemove, typeupload, fileid) {
    jMVP.post('/WebUI/UploadFile/DeleteFile.aspx', { del: file, act: typeupload });
    if (typeupload == "1") {//update
        var linkDelete = jMVP("#" + listValueFileAttach).val();
        jMVP("#" + listValueFileAttach).val(linkDelete + fileid + "#");
        jMVP("#" + ullistFileAttachRemove + " a[id=" + file + "]").parent().remove();
    } else {//insert
        jMVP("#" + ullistFileAttach + " span[id=" + file + "]").parent().remove();
        jMVP("#" + listValueFileAttach).val(changeHiddenInput(ullistFileAttach));
    }
    jMVP("#" + btnupdate).css("display", "");
    jMVP("#" + btnupdate + " .qq-upload-list").html("");
}
/*file đính kèm*/
//Lấy về html file
function getHTMLDeleteLink(data, hdlistValueFileAttach, ullistFileAttach, ullistFileAttachRemove) {
    return "<li><span id=\"" + data.fileserver + "\" title=\"" + data.filename + "\">" +
                    "<a href=\"/webui/file/tmp/" + data.fileserver + "\"  target=\"_blank\">" +
                        data.filename + "</a></span><a href=\"javascript:DeleteFile('" + data.fileserver + "','" +
                                                                                        hdlistValueFileAttach + "','" +
                                                                                        ullistFileAttach + "','" +
                                                                                        ullistFileAttachRemove + "', '0', '');\"><img src=\"/images/act_icon/act_filedelete.png\" title=\"Xóa file đính kèm\" border=\"0\"></a></li>";
}
// Return val html cho file bị xóa cho trường hợp update
function getHTMLDeleteLinkUpdate(id, fileserver) {
    return "<li><span id=\"" + id + "\" title=\"" + fileserver + "\" style=\"display: none;\"  ></span>";
}

//xóa file
function DeleteFile(file, listValueFileAttach, ullistFileAttach, ullistFileAttachRemove, typeupload, fileid) {
    if (typeupload == "1") {//update
        //var linkDelete = jMVP("#" + listValueFileAttach).val();
        //jMVP("#" + listValueFileAttach).val(linkDelete + fileid + "#");
        jMVP("#" + ullistFileAttachRemove + " span[id=" + file + "]").parent().remove();
        // jMVP("#" + btnupdate + " .qq-upload-list").html("");
        jMVP("#" + ullistFileAttach).append(getHTMLDeleteLinkUpdate(file, fileid));
        jMVP("#" + listValueFileAttach).val(changeHiddenInput(ullistFileAttach));
    } else {//insert
        jMVP.post('/WebUI/UploadFile/DeleteFile.aspx', { del: file, act: typeupload });
        jMVP("#" + ullistFileAttach + " span[id=" + file + "]").parent().remove();
        jMVP("#" + listValueFileAttach).val(changeHiddenInput(ullistFileAttach));
    }
    jMVP(".qq-upload-list").html("");
}

function DeleteFileUpdate(file, typeupload, listValueFileAttach) {
    var linkDelete = jMVP("#listValueFileAttachRemove").val();
    jMVP("#listValueFileAttachRemove").val(linkDelete + file + "#");
    jMVP("#listFileAttachRemove a[id=" + file + "]").parent().remove();
    //  jMVP("#" + listValueFileAttach).val(changeHiddenInput(ullistFileAttach));
    jMVP(".qq-upload-list").html("");
}

//lấy dữ liệu từ list 
function changeHiddenInput(ullistFileAttach) {
    var valueFile = '[';
    var total = jMVP("#" + ullistFileAttach + " li").length;
    jMVP("#" + ullistFileAttach + " li").each(function (i) {
        valueFile += '{"FileServer": "' + jMVP(this).children("span").attr("id") + '"\,';
        valueFile += '"FileName": "' + jMVP(this).children("span").attr("title") + '"\}';
        if (i + 1 < total)
            valueFile += ',';
    });
    valueFile += "]";
    return valueFile;
}
//upload ảnh 
function createUploaderImage(btnupload, ullistFileAttach, ullistFileAttachRemove, hdlistValueFileAttach, hdListFileRemove) {
    var uploader = new qq.FileUploader({
        element: jMVP("#" + btnupload)[0],
        action: '/WebUI/UploadFile/UploadFile.aspx?img=1',
        sizeLimit: 15 * 1024 * 1024,
        allowedExtensions: ['jpg', 'jpeg', 'gif', 'bmp', 'png', 'tif'],
        messages: {
            typeError: "{file} không được phép upload. Chỉ có định dạng {extensions} mới được upload.",
            sizeError: "{file} quá lớn, Độ lớn tối đa cho phép upload là {sizeLimit}."
        },
        showMessage: function (message) {
            createMessage("Có lỗi xảy ra ", message);
        },
        multiple: false,
        onSubmit: function (id, fileName) {
            // check trùng file
            var exits = false;
            //check trong trường hợp mới upload
            jMVP("#" + ullistFileAttach + " li").each(function (index, item) {
                if (fileName == jMVP(this).children("span").attr("title"))
                    exits = true;
            });
            //check trên file đã upload
            jMVP("#" + ullistFileAttachRemove + " li").each(function (index, item) {
                if (fileName == jMVP(this).children("span").attr("title"))
                    exits = true;
            });
            if (exits) {
                createMessage("Thông báo", fileName + " Đã tồn tại.");
                return false;
            }
        },
        onComplete: function (id, fileName, responseJSON) {

            if (responseJSON.upload) {
                jMVP("#" + ullistFileAttach).append(getHtmlImageLink(responseJSON, btnupload, hdlistValueFileAttach, ullistFileAttach, ullistFileAttachRemove));
                jMVP("#" + hdlistValueFileAttach).val(changeHiddenInput(ullistFileAttach))
            } else {
                //                if (responseJSON.message == undefined) {
                //                    var domain = window.location.protocol + '/' + window.location.host;
                //                    var currentRequest = escape((window.location.href).replace(domain, ''));
                //                    currentRequest = '/login.aspx?ReturnUrl=' + currentRequest;
                //                    window.location.href = currentRequest;
                //                }
                //                else
                //                    createMessage("Thông báo", responseJSON.message);
            }
        }
    });
}
function getHtmlImageLink(data, btnupdate, hdlistValueFileAttach, ullistFileAttach, ullistFileAttachRemove) {
    jMVP("#" + btnupdate).css("display", "none");
    jMVP("#imgDefault").css("display", "none");

    return "<li><span id=\"" + data.fileserver + "\" title=\"" + data.filename + "\">" +
                    "<a href=\"/webui/file/tmp/" + data.fileserver + "\"  target=\"_blank\">" +
                    "<img id=\"imgDefault\" style=\"width: 80px; height: 106px;\" border=\"0\" title=\"" + data.filename + "\"" +
                                                    "src=\"/webui/file/tmp/" + data.fileserver + "\" alt=\"\"></a></span><a style=\"margin-left: 5px;\" href=\"javascript:DeleteFileImage('" + data.fileserver + "','" +
                                                                                        btnupdate + "','" +
                                                                                        hdlistValueFileAttach + "','" +
                                                                                        ullistFileAttach + "','" +
                                                                                        ullistFileAttachRemove + "', '0', '');\"><img src=\"/images/act_icon/act_filedelete.png\" title=\"Xóa ảnh đại diện\" border=\"0\"></a></li>";
}
function DeleteFileImage(file, btnupdate, listValueFileAttach, ullistFileAttach, ullistFileAttachRemove, typeupload, fileid) {
    jMVP.post('/WebUI/UploadFile/DeleteFile.aspx', { del: file, act: typeupload });
    if (typeupload == "1") {//update
        var linkDelete = jMVP("#" + listValueFileAttach).val();
        jMVP("#" + listValueFileAttach).val(linkDelete + fileid + "#");
        jMVP("#" + ullistFileAttachRemove + " a[id=" + file + "]").parent().remove();
    } else {//insert
        jMVP("#" + ullistFileAttach + " span[id=" + file + "]").parent().remove();
        jMVP("#" + listValueFileAttach).val(changeHiddenInput(ullistFileAttach));
    }
    jMVP("#" + btnupdate).css("display", "");
    jMVP("#imgDefault").css("display", "");
    jMVP("#" + btnupdate + " .qq-upload-list").html("");
}
function OpenPopup(urlForm, formHeight, formWidth, idContent, popuptitle) {
    jMVP("#" + idContent).html(imageLoading);
    jMVP.post(encodeURI(urlForm), function (data) {
        jMVP("#" + idContent).html(data).dialog(
		            { title: popuptitle, height: formHeight, width: formWidth }
	            ).dialog("open");
    });
}
function OpenModal(urlForm, formHeight, formWidth, idContent, popuptitle) {
    jMVP("#" + idContent).html(imageLoading);
    jMVP.post(encodeURI(urlForm), function (data) {
        jMVP("#" + idContent).html(data).dialog(
		            { title: popuptitle, height: formHeight, width: formWidth, modal: true }
	            ).dialog("open");
    });
}

function getQueryStrings(name, strUrl) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&#]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(strUrl);
    if (results == null)
        return "";
    else
        return results[1];
}

function Edit(arrID) {
    jMVP("#dialog-form").html(imageLoading);
    jMVP.post(encodeURI(urlForm), { "do": "Edit", "ItemID": "" + arrID + "" }, function (data) {
        jMVP("#dialog-form").html(data).dialog(
		    { title: "Cập nhật nội dung", height: formHeight, width: formWidth }
	    ).dialog("open");
    });
}

function EditCustom(arrID, urlFormPage) {
    jMVP("#dialog-form").html(imageLoading);
    jMVP.post(encodeURI(urlFormPage), { "do": "Edit", "ItemID": "" + arrID + "" }, function (data) {
        jMVP("#dialog-form").html(data).dialog(
		    { title: "Cập nhật nội dung", height: formHeight, width: formWidth }
	    ).dialog("open");
    });
}

function SelectTS(taisanId, divContain) {
    if (taisanId > 0) {
        //jMVP("#" + divContain).parent().remove();
        jMVP("#" + divContain).remove();
        jMVP("#dialog-form").append("<div id=\"" + divContain + "\" style=\"display: none;\"></div>");
        blFlagSelectTS = true;
        //ReloadParentPopup(taisanId);
        RefeshData();
    }
}
function ReloadParentPopup(taisanId) {
    jMVP("#dialog-form").parent().remove();
    jMVP("#dialog-form").remove();
    jMVP("body").append("<div id=\"dialog-form\" style=\"display: none;\"></div>");
    urlForm += "?tsId=" + taisanId;
    jMVP.post(encodeURI(urlForm), function (data) {
        jMVP("#dialog-form").html(data).dialog(
		            { title: "Thêm mới  sửa chữa", height: formHeight, width: formWidth, modal: true }
	            ).dialog("open");
    });
}
function ClosePopup(divContainer) {
    jMVP("#" + divContainer).parent().remove();
    jMVP("#" + divContainer).remove();
    jMVP("body").append("<div id=\"" + divContainer + "\" style=\"display: none;\"></div>");
}

function createCloseMessage(title, message, urlFw) {
    //jMVP("#dialog-message").attr("title", title);
    jMVP("#dialog-message").html("<p>" + message + "</p>");
    jMVP("#dialog-message").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Đóng lại": function () {
                jMVP(this).dialog("close");
                window.location.href = urlFw;
            }
        }
    });
   //setTimeout(jMVP("#dialog-message").dialog("close"), 15000);
    setTimeout(function () { jMVP("#dialog-message").dialog("close") }, 30000);
    window.location.href = urlFw;
}
function createCloseMessageTT(title, message, urlFw) {
    //jMVP("#dialog-message").attr("title", title);
    jMVP("#dialog-message").html("<p>" + message + "</p>");
    jMVP("#dialog-message").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Đóng lại": function () {
                jMVP(this).dialog("close");
                window.location.href = urlFw;
            }
        }
    });
    //setTimeout(jMVP("#dialog-message").dialog("close"), 15000);
    setTimeout(function () { jMVP("#dialog-message").dialog("close") }, 30000);
    window.location.href = urlFw;
}
function createCloseMessageNoButtonClose(title, message, urlFw) {
    //jMVP("#dialog-message").attr("title", title);
    jMVP("#dialog-message").html("<p>" + message + "</p>");
    jMVP("#dialog-message").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Đóng lại": function () {
                jMVP(this).dialog("close");
                window.location.href = urlFw;
            }
        }
    });
    setTimeout(function () { jMVP("#dialog-message").dialog("close") }, 10000);
    window.location.href = urlFw;
}
function createMessage(title, message) {
    //jMVP("#dialog-message").attr("title", title);
    jMVP("#dialog-message").html("<p>" + message + "</p>");
    jMVP("#dialog-message").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Đóng lại": function () {
                jMVP(this).dialog("close");
            }
        }
    });
    setTimeout(function () { jMVP("#dialog-message").dialog("close") }, 30000);
}

function createMessageT(title, message, urlFw) {
    //jMVP("#dialog-message").attr("title", title);
    jMVP("#dialog-message").html("<p>" + message + "</p>");
    jMVP("#dialog-message").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Đóng lại": function () {
                jMVP(this).dialog("close");
                window.location.href = urlFw;
            }
        }
    });
    setTimeout(function () { jMVP("#dialog-message").dialog("close") }, 30000);
}
function AutoCloseDialogBox(WaitSeconds) {
    //Auto Close Dialog Box after few seconds
    setTimeout(
                function () {
                    jMVP(this).dialog("close");
                }, WaitSeconds);
}



function createConfirm(title, message) {
    //jMVP("#dialog-confirm").attr("title", title);
    jMVP("#dialog-confirm").html("<p>" + message + "</p>");
    jMVP("#dialog-confirm").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Tiếp tục": function () {
                jMVP(this).dialog("close");
                return true;
            },
            "Hủy": function () {
                jMVP(this).dialog("close");
                return false;
            }
        }
    });
}


function createConfirmT(title, message, urlFw) {
    //jMVP("#dialog-confirm").attr("title", title);
    jMVP("#dialog-confirm").html("<p>" + message + "</p>");
    jMVP("#dialog-confirm").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Tiếp tục": function () {
                jMVP(this).dialog("close");
                window.location.href = urlFw;
                return true;
            },
            "Hủy": function () {
                jMVP(this).dialog("close");
                return false;
            }
        }
    });
}


function createMessageRespone(title, message, url) {
    //jMVP("#dialog-message").attr("title", title);
    jMVP("#dialog-message").html("<p>" + message + "</p>");
    jMVP("#dialog-message").dialog({
        title: title,
        resizable: false,
        height: 160,
        modal: true,
        close: function () { window.location = url; },
        buttons: {
            "Đóng lại": function () {
                jMVP(this).dialog("close");
                window.location = url;
            }
        }
    });
    setTimeout(jMVP("#dialog-message").dialog("close"), 10000);
}
function CheckedItemGrid(gvID) {
    return (jMVP("#" + gvID + " input[type='checkbox']:checked").length > 0);
}
function CheckAll(gvID) {
    jMVP("#" + gvID + " input[type='checkbox']").attr('checked', jMVP('#checkAll').is(':checked'));
}
/* check quyền */
function checkPermission() {
    if (listcontrolnotallow != "") {
        var arrControl = listcontrolnotallow.split(";");
        jMVP.each(arrControl, function () {
            jMVP("." + this).remove();
        });
    }
}
function PermissionPara(listcontrolnotallow) {
    if (listcontrolnotallow != "") {
        var arrControl = listcontrolnotallow.split(";");
        jMVP.each(arrControl, function () {
            jMVP("." + this).remove();
            var classPer = this + "";
            jMVP("." + classPer.toLowerCase()).remove();
            jMVP("." + classPer.toUpperCase()).remove();
        });
    }
}
/* endcheck quyền */

//hàm lấy về danh sách các ID được chọn qua checkbox
function getArrIDCheckBox(strGridId) {
    var arrID = '';
    jMVP("#" + strGridId + " input[type='checkbox']:checked").not("#checkAll").not(".checkAll").each(function () {
        arrID += jMVP(this).val() + ",";
    });
    arrID = (arrID.length > 0) ? arrID.substring(0, arrID.length - 1) : arrID;
    return arrID;
}
function reLoadListContent() {
    var urlReload;
    if (getUrlReload() != null)
        urlReload = urlLists + getSpliter(urlLists) + getDefaultQueryValue() + "&" + getUrlReload();
    else
        urlReload = urlLists + getSpliter(urlLists) + getDefaultQueryValue();
    LoadListContent(urlReload);
}

function reLoadListByContainer(container, listUrl) {
    var urlReload;
    if (getUrlReload() != null)
        urlReload = listUrl + getSpliter(listUrl) + getDefaultQueryValue() + "&" + getUrlReload();
    else
        urlReload = listUrl + getSpliter(listUrl) + getDefaultQueryValue();
    LoadListByContainer(urlReload, container);
}
function getUrlReload() {
    url = window.location.href;
    if (url.indexOf('#') != -1) {
        url = url.split('#');
        return url[1];
    }
}
function getSpliter(urlcheck) {
    var strReturn = "?";
    if (urlcheck.indexOf(strReturn) > -1)
        strReturn = "&";
    return strReturn;
}
function getDefaultQueryValue() {
    return "RowPerPage=" + SoBanGhiTrenTrang + "&PageStep=" + SoTrangHienThi;
}
function LoadListByContainer(urlContent, container) {
    jMVP("#" + container).html(imageLoading);
    loadAjaxContent(urlContent, "#" + container)
}
function LoadListContent(urlContent) {
    jMVP("#list-container").html(imageLoading);
    loadAjaxContent(urlContent, '#list-container')
}
function loadAjaxContent(urlContent, container) {
    jMVP.ajax({
        url: encodeURI(urlContent),
        cache: false,
        type: "POST",
        success: function (data) {
            jMVP(container).html(data);

        }
    });
}
function Delete(arrID) {
    var titleDiag = '';
    if (arrID == '') {
        createMessage("Thông báo", "Bạn chưa chọn bản ghi nào");
    }
    else {
        if (arrID.indexOf(',') > 0) {
            titleDiag = "Xóa các bản ghi đã chọn";
            jMVP("#dialog-confirm").html("<p>Bạn có chắc chắn muốn xóa các bản ghi đã chọn?</p>");
        }
        else {
            titleDiag = "Xóa bản ghi đã chọn";
            jMVP("#dialog-confirm").html("<p>Bạn có chắc chắn muốn xóa bản ghi đã chọn?</p>");
        }
        var comfirmReturn = false;
        jMVP("#dialog-confirm").dialog({
            title: titleDiag,
            resizable: false,
            height: 140,
            modal: true,
            buttons: {
                "Tiếp tục": function () {
                    postAction("delete", arrID);
                    jMVP(this).dialog("close");
                },
                "Hủy lệnh xóa": function () {
                    jMVP(this).dialog("close");
                }
            }
        });
    }
}
function DeleteCustom(container, listUrl, arrID, urlFormPage) {
    var titleDiag = '';

    if (arrID == '') {
        createMessage("Thông báo", "Bạn chưa chọn bản ghi nào");
    }
    else {
        if (arrID.indexOf(',') > 0) {
            titleDiag = "Xóa các bản ghi đã chọn";
            jMVP("#dialog-confirm").html("<p>Bạn có chắc chắn muốn xóa các bản ghi đã chọn?</p>");
        }
        else {
            titleDiag = "Xóa bản ghi đã chọn";
            jMVP("#dialog-confirm").html("<p>Bạn có chắc chắn muốn xóa các bản ghi đã chọn?</p>");
        }
        var comfirmReturn = false;
        jMVP("#dialog-confirm").dialog({
            title: titleDiag,
            resizable: false,
            height: 140,
            modal: true,
            buttons: {
                "Tiếp tục": function () {
                    postActionCustom(container, listUrl, "delete", arrID, urlFormPage);
                    jMVP(this).dialog("close");
                },
                "Hủy lệnh xóa": function () {
                    jMVP(this).dialog("close");
                }
            }
        });
    }
}
function postActionCustom(container, listUrl, method, arrID, urlFormPage) {
    jMVP.post(encodeURI(urlFormPage), { "do": "" + method + "", "ItemID": "" + arrID + "" }, function (data) {
        if (data.Erros) {
            createMessage("Có lỗi xảy ra", "Lỗi được thông báo: " + data.Message);
            reLoadListByContainer(container, listUrl);
        }
        else {
            createMessage("Thông báo", data.Message);
            if (method == "delete") {
                window.location.href = "#page=1";
                reLoadListByContainer(container, listUrl);
            }
        }
    });
}
function postAction(method, arrID) {
    jMVP.post(encodeURI(urlPostAction), { "do": "" + method + "", "ItemID": "" + arrID + "" }, function (data) {
        if (data.Erros) {
            createMessage("Có lỗi xảy ra", "Lỗi được thông báo: " + data.Message);
            reLoadListContent();
        }
        else {
            createMessage("Thông báo", data.Message);
            if (method == "delete") {
                window.location.href = "#page=1";
                reLoadListContent();
            }
        }
    });
}
function thowMessageOnError(message) {
    jMVP(".validation").html(message).addClass("ui-state-error");
    jMVP('html, body').animate({ scrollTop: 0 }, 'fast');
}
function thowMessageOnPopupError(message) {
    jMVP(".popupValidation").html(message).addClass("ui-state-error");
}
function SetCSS() {
    jMVP(".C1Heading th.action a").parent().next().remove();
    jMVP(".C1Heading th.action a").remove();
    var strColumnName = decodeURI(getQueryStrings("col", window.location.href));
    if (strColumnName != '') {
        jMVP(".C1Heading a").each(function () {
            if (jMVP(this).html() == strColumnName) {
                var childDiv = jMVP(this).parent().next().children("div");
                //add class                 
                var strQuery = getQueryStrings("sort", window.location.href);
                if (strQuery.indexOf('-') != -1)
                    strQuery = strQuery.split('-');

                if (strQuery[1] != "" && strQuery[1] != "1") {
                    jMVP(childDiv).removeClass();
                    jMVP(childDiv).addClass("C1ImageSortAsc");
                } else {
                    jMVP(childDiv).removeClass();
                    jMVP(childDiv).addClass("C1ImageSortDesc");
                }
            }
        });
    }
}
function GetSortUrl() {
    var strSort = getQueryStrings("sort", window.location.href);
    var strCol = getQueryStrings("col", window.location.href);
    if (strSort != "" && strCol != "") {
        return "&sort=" + strSort + "&col=" + strCol;
    }
    else return "";
}
function AddRegularValidation() {
    jMVP.validator.addMethod(
        "RE",
        function (value, element, params) {
            var re = new RegExp(params)
            return this.optional(element) || re.test(value);
        },
    "Sai định dạng"
    );
}
function AddCustomRequired() {
    jMVP.validator.addMethod("crequired", function (value, element, params) {
        blreturn = true;
        switch (element.nodeName.toLowerCase()) {
            case 'select':
                var options = jMVP("option:selected", element);
                blreturn = options.length > 0 && (element.type == "select-multiple" || (jMVP.browser.msie && !(options[0].attributes['value'].specified) ? options[0].text : options[0].value).length > 0);
            case 'input':
                blreturn = jMVP.trim(value).length > 0 && jMVP(element);
            default:
                blreturn = jMVP.trim(value).length > 0 && jMVP(element);
        }
        return blreturn;
    }, jMVP.format('{0}'));
}
function AddNguyenGiaValid() {
    var strMessageNguyenGia = "";
    jMVP.validator.addMethod("nguyengiavalid", function (value, element, params) {
        blreturn = true;
        if (params == "1") {
            var giatriconlai = jMVP("#" + jMVP(element).parent().parent().next().attr("id") + " input.inputgiatri").val();
            if (giatriconlai == "")
                giatriconlai = "0";
            if (value == "")
                value = "0";
            giatriconlai = giatriconlai.toString().replace(RegExp('\\.', 'g'), '');
            giatriconlai = giatriconlai.replace(RegExp('\\,', 'g'), '.');
            giatriconlai = giatriconlai.replace(RegExp('\\a', 'g'), ',');

            value = value.toString().replace(RegExp('\\.', 'g'), '');
            value = value.replace(RegExp('\\,', 'g'), '.');
            value = value.replace(RegExp('\\a', 'g'), ',');
            jMVP.validator.messages.nguyengiavalid = "Nguyên giá phải lớn hơn hoặc bằng giá trị còn lại";
            blreturn = parseFloat(value) >= parseFloat(giatriconlai);
        } else {
            var nguyengia = jMVP("#" + jMVP(element).parent().parent().prev().attr("id") + " input.inputgiatri").val();
            if (nguyengia == "")
                nguyengia = "0";
            if (value == "")
                value = "0";

            nguyengia = nguyengia.toString().replace(RegExp('\\.', 'g'), '');
            nguyengia = nguyengia.replace(RegExp('\\,', 'g'), '.');
            nguyengia = nguyengia.replace(RegExp('\\a', 'g'), ',');

            value = value.toString().replace(RegExp('\\.', 'g'), '');
            value = value.replace(RegExp('\\,', 'g'), '.');
            value = value.replace(RegExp('\\a', 'g'), ',');
            jMVP.validator.messages.nguyengiavalid = "Giá trị còn lại phải nhỏ hơn hoặc bằng nguyên giá";
            blreturn = parseFloat(value) <= parseFloat(nguyengia);
        }
        return blreturn;
    });
}
function AddTyLeValid() {
    jMVP.validator.addMethod("tylevalid", function (value, element, params) {
        blreturn = true;
        if (params == "1") {
            var tylekhauhao = jMVP("#" + jMVP(element).parent().parent().attr("id") + " input.inputtyle").not(element).val();
            if (tylekhauhao == "")
                tylekhauhao = "0";
            if (value == "")
                value = "0";
            tylekhauhao = tylekhauhao.toString().replace(RegExp('\\.', 'g'), '');
            tylekhauhao = tylekhauhao.replace(RegExp('\\,', 'g'), '.');
            tylekhauhao = tylekhauhao.replace(RegExp('\\a', 'g'), ',');

            value = value.toString().replace(RegExp('\\.', 'g'), '');
            value = value.replace(RegExp('\\,', 'g'), '.');
            value = value.replace(RegExp('\\a', 'g'), ',');
            jMVP.validator.messages.tylevalid = "Tỷ lệ khấu hao lũy kế phải lớn hơn hoặc bằng tỷ lệ khấu hao";
            blreturn = parseFloat(value) >= parseFloat(tylekhauhao);
        } else {
            var luyke = jMVP("#" + jMVP(element).parent().parent().attr("id") + " input.inputtyle").not(element).val();
            if (luyke == "")
                luyke = "0";
            if (value == "")
                value = "0";

            luyke = luyke.toString().replace(RegExp('\\.', 'g'), '');
            luyke = luyke.replace(RegExp('\\,', 'g'), '.');
            luyke = luyke.replace(RegExp('\\a', 'g'), ',');

            value = value.toString().replace(RegExp('\\.', 'g'), '');
            value = value.replace(RegExp('\\,', 'g'), '.');
            value = value.replace(RegExp('\\a', 'g'), ',');
            jMVP.validator.messages.tylevalid = "Tỷ lệ khấu hao phải nhỏ hơn hoặc bằng tỷ lệ khấu hao lũy kế";
            blreturn = parseFloat(value) <= parseFloat(luyke);
        }
        return blreturn;
    });
}
function AddMinValid() {
    jMVP.validator.addMethod("minvalid", function (value, element, params) {
        blreturn = true;
        if (params == "")
            params = "0";
        if (value == "")
            value = "0";
        value = value.toString().replace(RegExp('\\.', 'g'), '');
        value = value.replace(RegExp('\\,', 'g'), '.');
        value = value.replace(RegExp('\\a', 'g'), ',');
        jMVP.validator.messages.minvalid = "Yêu cầu nhập giá trị lớn hơn " + params;
        blreturn = parseFloat(value) >= parseFloat(params);

        return blreturn;
    });
}
function AddMaxValid() {
    jMVP.validator.addMethod("maxvalid", function (value, element, params) {
        blreturn = true;
        if (params == "")
            params = "0";
        if (value == "")
            value = "0";
        value = value.toString().replace(RegExp('\\.', 'g'), '');
        value = value.replace(RegExp('\\,', 'g'), '.');
        value = value.replace(RegExp('\\a', 'g'), ',');
        jMVP.validator.messages.maxvalid = "Yêu cầu nhập giá trị nhỏ hơn " + formatNuber(params, '.', ',', 0);
        blreturn = parseFloat(value) <= parseFloat(params);

        return blreturn;
    });
}
function AddHoiDongValid() {
    jMVP.validator.addMethod("hdvalid", function (value, element, params) {
        blreturn = true;
        if (jMVP.trim(value).length <= 0) {
            blreturn = jMVP.trim(jMVP("#" + jMVP(element).parent().parent().attr("id") + " input.hdinput").not(element).val()).length <= 0;
        }
        return blreturn;
    }, jMVP.format('{0}'));
}
function AddChuTichValid() {
    jMVP.validator.addMethod("hdname", function (value, element, params) {
        blreturn = true;
        if (jMVP("#" + jMVP(element).parent().parent().attr("id") + " input[type='radio']:checked").length > 0) {
            blreturn = jMVP.trim(value).length > 0;
        }
        return blreturn;
    }, jMVP.format('{0}'));
}
function AddDateCompare() {
    // lớn hơn
    jMVP.validator.addMethod("dateGr", function (value, element, params) {
        if (jMVP.trim(value) == '')
            return true;
        if (!/Invalid|NaN/.test(new Date(value))) {
            return this.optional(element) || jMVP(element).datepicker("getDate") > jMVP(params).datepicker("getDate");
        }
        return this.optional(element) || isNaN(value) && isNaN(jMVP(params).val()) || (parseFloat(value) > parseFloat(jMVP(params).val()));
    }, 'Dữ liệu nhập vào chưa chính xác!');

    // lớn hơn hoặc bằng
    jMVP.validator.addMethod("dateGrEq", function (value, element, params) {
        if (jMVP.trim(value) == '')
            return true;
        if (!/Invalid|NaN/.test(new Date(value))) {
            return this.optional(element) || jMVP(element).datepicker("getDate") >= jMVP(params).datepicker("getDate");
        }
        return this.optional(element) || isNaN(value) && isNaN(jMVP(params).val()) || (parseFloat(value) >= parseFloat(jMVP(params).val()));
    }, 'Dữ liệu nhập vào chưa chính xác!');

    // nhỏ hơn
    jMVP.validator.addMethod("dateSm", function (value, element, params) {
        if (jMVP.trim(value) == '')
            return true;
        if (!/Invalid|NaN/.test(new Date(value))) {
            return this.optional(element) || jMVP(element).datepicker("getDate") < jMVP(params).datepicker("getDate");
        }
        return this.optional(element) || isNaN(value) && isNaN(jMVP(params).val()) || (parseFloat(value) < parseFloat(jMVP(params).val()));
    }, 'Dữ liệu nhập vào chưa chính xác!');

    // nhỏ hơn hoặc bằng
    jMVP.validator.addMethod("dateSmEq", function (value, element, params) {
        if (jMVP.trim(value) == '')
            return true;
        if (!/Invalid|NaN/.test(new Date(value))) {
            return this.optional(element) || jMVP(element).datepicker("getDate") <= jMVP(params).datepicker("getDate");
        }
        return this.optional(element) || isNaN(value) && isNaN(jMVP(params).val()) || (parseFloat(value) <= parseFloat(jMVP(params).val()));
    }, 'Dữ liệu nhập vào chưa chính xác!');

    // lớn hơn hoặc bằng ngày hiện tại
    jMVP.validator.addMethod("dateGrVal", function (value, element, params) {
        if (jMVP.trim(value) == '')
            return true;
        if (!/Invalid|NaN/.test(new Date(value))) {
            return this.optional(element) || jMVP(element).datepicker("getDate") >= new Date(params); //jMVP(params).datepicker("getDate");
        }
        return this.optional(element) || isNaN(value) && isNaN(jMVP(params).val()) || (parseFloat(value) > parseFloat(jMVP(params).val()));
    }, 'Dữ liệu nhập vào chưa chính xác!');
}

function AddSelectUnique() {
    jMVP.validator.addMethod("selectUni", function (value, element, params) {
        blreturn = true;
        if (jMVP(element).is(":hidden"))
            return blreturn;
        jMVP("select." + params).filter(":visible").each(function () {
            if (jMVP(this).attr("id") != jMVP(element).attr("id") && jMVP(this).val() == value)
                blreturn = false;
        });
        return blreturn;
    }, 'Đối tượng này đã được chọn');
}
function submitSearchEnter() {
    if (window.event.keyCode == 13) {
        submitSearch();
        return false;
    }
}
function ChonDonViPopup(urlDonVi, iwidthdv, iheightdv) {
    jMVP("#dialog-form").html(imageLoading);
    jMVP.post(encodeURI(urlDonVi), { "do": "chondonvi" }, function (data) {
        jMVP("#dialog-form").html(data);
    });
    jMVP("#dialog-form").dialog(
				        { title: "Chọn đơn vị", width: iwidthdv, height: iheightdv, close: function () { } }
			        ).dialog("open");
}
//xóa 1 từ trong 1 chuỗi
function removeString(s, t) {
    i = s.indexOf(t);
    r = "";
    if (i == -1) return s;
    r += s.substring(0, i) + removeString(s.substring(i + t.length), t);
    return r;
}

jMVP.datepicker.setDefaults({
    minDate: new Date(1945, 1 - 1, 1),
    maxDate: new Date(2080, 1 - 1, 1)
});
function CheckNameTs(controlid) {
    jMVP.ajax({
        type: "GET",
        datatype: 'json',
        data: "ten=" + encodeURI(jMVP("#" + controlid).val()),
        url: "/webui/taisan/hTenTaiSan.aspx",
        success: function (data) {
            if (data.length == 0) {
                jMVP.post(encodeURI("/webui/taisan/pThemTenTaiSan.aspx"), { "cid": controlid, "ten": jMVP("#" + controlid).val() }, function (data) {
                    jMVP("#dialog-form").html(data);
                });
                jMVP("#dialog-form").dialog(
				                { title: "Thêm tên ", width: 500, height: 350, modal: true }
			                ).dialog("open");
            }
            else if (data.length == 1)
                jMVP("#" + controlid).val(data[0].Title);
            else if (data.length > 1) {
                jMVP("#dialog-form").html(imageLoading);
                jMVP.post(encodeURI("/webui/taisan/pChonTenTaiSan.aspx"), { "ten": jMVP("#" + controlid).val(), "cid": controlid }, function (data) {
                    jMVP("#dialog-form").html(data);
                });
                jMVP("#dialog-form").dialog(
				                { title: "Chọn tên ", width: 750, height: 580, modal: true }
			                ).dialog("open");
            }
        }
    });
}

function GetDon_Vi_DeNghiCanCu(controlid, DeNghiCanCuId, DonViId) {

    var isDeNghiCanCuNotNull = "";
    if (DeNghiCanCuId != "") {

        isDeNghiCanCuNotNull = "1";
    }
    //alert(DonViId);
    jMVP.ajax({
        type: "GET",
        datatype: 'json',
        data: "hdfLyDo=" + isDeNghiCanCuNotNull,
        url: "/webui/quanlychung/suachua/hDsDonVi.aspx",
        success: function (data) {
            //alert(controlid);
            var htmldonvi = "";
            jMVP.each(data, function () {

                if (DonViId != "" && DonViId == this["Id"]) {
                    htmldonvi += "<option value=\"" + this["Id"] + "\" selected=\"selected\">" + this["Title"] + "</option>";
                }
                else {
                    htmldonvi += "<option value=\"" + this["Id"] + "\">" + this["Title"] + "</option>";
                }
            });
            jMVP("#" + controlid).html(htmldonvi);
        }
    });
}

function CheckExistNameTs(controlid) {
    var IsExistTenTaiSan = false;
    jMVP.get("/webui/taisan/hTenTaiSan.aspx", { "ten": jMVP("#" + controlid).val(), "exist": "1" }, function (datacheck) {
        if (datacheck.length > 0)
            IsExistTenTaiSan = true;
        else {
            IsExistTenTaiSan = false;
            createMessage("Kiểm tra lại tên ", "Tên chưa có");
        }
    });
    return IsExistTenTaiSan;
}
function SetMinHeight() {
    var _heightWap = jMVP("#wapper").height();
    var _heightScr = screen.height;
    var _heightTop = jMVP("#Top").height();
    if (_heightWap < _heightScr) {
        jMVP("#wapper").height(_heightScr - _heightTop);
    }
}
// phân phối văn bản
function PhanPhoiVB(itemid) {
    jMVP.post(urlFormPhanPhoi, { "ItemId": itemid, "do": "dtb" }, function (data) {
        jMVP("#form-contaier").html(data);
    });
    return false;
}
//enable popup
function popCenter(URL, name, w, h) {
    l = (screen.width - w) / 2;
    t = (screen.height - h - 100) / 2;

    params = 'toolbars=1, scrollbars=1, location=0, statusbars=0, menubars=1, resizable=1,';
    popCenterWin = window.open(URL, name, params + 'width=' + w + ', height=' + h + ', left=' + l + ', top=' + t);

    if (_hasPopupBlocker(popCenterWin))
        alert('Bạn cần thiết lập chế độ cho phép mở cửa sổ popup cho ứng dụng này!');
    else
        popCenterWin.focus();

    return false;
}
function _hasPopupBlocker(poppedWindow) {
    var result = false;

    try {
        if (typeof poppedWindow == 'undefined') {
            // Safari with popup blocker... leaves the popup window handle undefined
            result = true;
        }
        else if (poppedWindow && poppedWindow.closed) {
            // This happens if the user opens and closes the client window...
            // Confusing because the handle is still available, but it's in a "closed" state.
            // We're not saying that the window is not being blocked, we're just saying
            // that the window has been closed before the test could be run.
            result = false;
        }
        else if (poppedWindow && poppedWindow.test) {
            // This is the actual test. The client window should be fine.
            result = false;
        }
        else {
            // Else we'll assume the window is not OK
            result = true;
        }

    } catch (err) {
        //if (console) {
        //    console.warn("Could not access popup window", err);
        //}
    }

    return result;
}
// Download file
function DownloadFile(type, _ID) {
    jMVP.post(urlDownloadFile, { "type": type, "itemid": _ID }, function (data) {
        //jMVP("#dialog-form").html(data.Value);
        //window.open(data.Value); 
        var url = data.Value;
        if (url == undefined)
            createMessage("Thông báo", "File không tồn tại hoặc mất kết nối. Xin vui lòng kiểm tra lại");
        else if (data.Error) // Nếu lỗi thì post ra thông báo lỗi
        //jMVP("#dialog-form").load(url).dialog({modal:true});             
            createMessage("Thông báo", data.Value);
        else // Nếu không thì trả về file
        {
            //jMVP("#dialog-form").load(url).dialog({modal:true}); 
            // window.location = url;
          //  window.location.assign(url);
              window.open(url, '_blank', 'toolbar=0,location=0,menubar=0,top=200, left=200,width=800');

           // popCenter(url, _ID + "", 600, 800);
            // window.OpenModal(url, '_blank');
            //var newtab = window.open();
            //newtab.location = url;
        }
    });

}; 