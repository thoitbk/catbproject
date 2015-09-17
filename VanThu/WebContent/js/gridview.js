var lastGirdHeight;
var urlPostMaster = "";
var urlPostDetails = "";
var urlFWOnDeleteMasterOrDetails = "";
function initAjaxLoad(urlListLoad, container) {
    jMVP.address.init().change(function (event) {
        var urlTransform = urlListLoad;
        var urlHistory = event.value;
        if (urlHistory.length > 0) {
            urlHistory = urlHistory.substring(1, urlHistory.length);
            if (urlTransform.indexOf('?') > 0)
                urlTransform = urlTransform + '&' + urlHistory;
            else
                urlTransform = urlTransform + '?' + urlHistory;
        }
        jMVP(container).height(lastGirdHeight);
        jMVP(container).html(imageLoading);
        jMVP.post(urlTransform, function (data) {
            jMVP(container).html(data);
            lastGirdHeight = jMVP(container).height();
            jMVP(container).removeAttr("style");
           
        });

    });
}
function initAjaxLoadTD(urlListLoad, container) {
    jMVP.address.init().change(function (event) {
        var urlTransform = urlListLoad;
        var urlHistory = event.value;
        if (urlHistory.length > 0) {
            urlHistory = urlHistory.substring(1, urlHistory.length);
            if (urlTransform.indexOf('?') > 0)
                urlTransform = urlTransform + '&' + urlHistory;
            else
                urlTransform = urlTransform + '?' + urlHistory;
        }
        jMVP(container).height(lastGirdHeight);
        jMVP(container).html(imageLoading);
        jMVP.post(urlTransform, function (data) {
            jMVP(container).html(data);
            lastGirdHeight = jMVP(container).height();
            jMVP(container).removeAttr("style");

        });

    });
}

function changeHashValue(key, value, source) {
    value = encodeURIComponent(value);
    var currentLink = source.substring(1);
    var returnLink = '#';
    var exits = false;
    if (currentLink.indexOf('&') > 0) { // lớn hơn 1
        var tempLink = currentLink.split('&');
        for (idx = 0; idx < tempLink.length; idx++) {
            if (key == tempLink[idx].split('=')[0]) { //check Exits
                returnLink += key + '=' + value;
                exits = true;
            }
            else {
                returnLink += tempLink[idx];
            }
            if (idx < tempLink.length - 1)
                returnLink += '&';
        }
        if (!exits)
            returnLink += '&' + key + '=' + value;
    } else if (currentLink.indexOf('=') > 0) { //Chỉ 1
        if (currentLink.indexOf(key) > -1)
            returnLink = '#' + key + '=' + value;
        else
            returnLink = '#' + currentLink + '&' + key + '=' + value;
    }
    else
        returnLink = '#' + key + '=' + value;
    return returnLink;
}


//Chuyển trang với value mới
function changeHashUrl(key, value) {
    var currentLink = jMVP.address.value();
    return changeHashValue(key, value, currentLink);
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
function registerGridView(selector) {
    //Đổi màu row
    jMVP(selector + " .gridView tr").each(function (index) {
        if (index % 2 == 0)
            jMVP(this).addClass("odd");
    });

    //Sắp xếp các cột
    jMVP(selector + " .gridView th a").each(function (idx) {
        var link = jMVP(this).attr("href");
        link = link.substring(1, link.length);
        if (jMVP.address.value().indexOf(link) > 0) {
            if (jMVP.address.value().indexOf('FieldOption=1') > 0) {
                jMVP(this).addClass('desc');
                jMVP(this).attr("href", '#' + link + '&FieldOption=0');
            }
            else {
                jMVP(this).addClass('asc');
                jMVP(this).attr("href", '#' + link + '&FieldOption=1');
            }
        }
    });
    jMVP(selector + " .gridView th a").click(function () {

        var urlTransform = jMVP.address.value();
        var link = jMVP(this).attr("href");
        link = "?" + link.substring(1, link.length);
        var fieldvalue = getQueryStrings("Field", link);
        var fieldoptionvalue = getQueryStrings("FieldOption", link);

        urlTransform = changeHashValue("Field", fieldvalue, urlTransform);
        if (fieldoptionvalue != "")
            urlTransform = changeHashValue("FieldOption", fieldoptionvalue, urlTransform);

        window.location.href = urlTransform;
        return false;
    });

    //khi người dùng click trên 1 row
    jMVP(selector + " .gridView tr").not("first").click(function () {
        jMVP(selector + " .gridView tr").removeClass("hightlight");
        jMVP(this).addClass("hightlight");
    });

    //checkall
    jMVP(selector + ' .checkAll').click(function () {

        var selectQuery = selector + " input[type='checkbox']";
        if (jMVP(this).val() != '')
            selectQuery = selector + " #" + jMVP(this).val() + " input[type='checkbox']";

        jMVP(selectQuery).attr('checked', jMVP(this).is(':checked'));
    });

    //Nhảy trang
    jMVP(selector + " .bottom-pager input").change(function () {
        var cPage = trim12(jMVP(this).val());
        var maxPage = jMVP(selector + " .bottom-pager input[type=hidden]").val();
        if (cPage.length == 0)
            createMessage("Thông báo", "Yêu cầu nhập trang cần chuyển đến");
        else if (isNaN(cPage))
            createMessage("Thông báo", "trang chuyển đến phải là kiểu số");
        else if (parseInt(cPage) > maxPage)
            createMessage("Thông báo", "trang không được lớn hơn " + maxPage + "");
        else if (parseInt(cPage) <= 0) {
            createMessage("Thông báo", "trang phải lớn hơn 0");
        }
        else {
            window.location.href = changeHashUrl('Page', cPage); ;
        }
    });

    //ẩn hiện nhóm
    jMVP(selector + " .gridView a.group").click(function () {
        var idShowHide = jMVP(this).attr("href");
        if (jMVP(this).text() == '+') {
            jMVP(idShowHide).show();
            jMVP(this).text("-");
        } else {
            jMVP(idShowHide).hide();
            jMVP(this).text("+");
        }
        return false;
    });

    //Thay đổi số bản ghi trên trang
    jMVP(selector + " .bottom-pager select").change(function () {
        var urlFWs = jMVP.address.value();
        urlFWs = changeHashValue("Page", 1, urlFWs); //Replace  &Page=.. => Page=1
        urlFWs = changeHashValue("RowPerPage", jMVP(this).val(), urlFWs); //Replace  &TenDonVi=.. => TenDonVi=donViNhan
        window.location.href = urlFWs;
    });

    //Đăng ký xóa nhiều
    jMVP(selector + " .gridView a.deleteAll").click(function () {
        var arrRowId = '';
        var rowTitle = '';
        var linkFW = jMVP(this).attr("id");
        var linkFW = (linkFW == '') ? '#Page=1' : linkFW;
        jMVP(selector + " input[type='checkbox']:checked").not("#checkAll").not(".checkAll").each(function () {
            arrRowId += jMVP(this).val() + ",";
            rowTitle += jMVP(this).parent().parent().find('a.delete').attr("title") + "<br />";
        });
        arrRowId = (arrRowId.length > 0) ? arrRowId.substring(0, arrRowId.length - 1) : arrRowId;
        rowDelete(urlPostAction, arrRowId, rowTitle, linkFW);
        return false;
    });

    //Đăng ký button xóa row
    jMVP(selector + " .gridView a.delete").click(function () {
        rowDelete(urlPostAction, jMVP(this).attr("href").substring(1), escapeHTML(jMVP(this).attr("title")), "#Page=1");
        return false;
    });

    //Xóa master
    jMVP(selector + " .gridView a.deleteMaster").click(function () {
        rowDelete(urlPostMaster, jMVP(this).attr("href").substring(1), escapeHTML(jMVP(this).attr("title")), "#" + urlFWOnDeleteMasterOrDetails + "&Page=1");
        return false;
    });

    //Xóa details
    jMVP(selector + " .gridView a.deleteDetails").click(function () {
        rowDelete(urlPostDetails, jMVP(this).attr("href").substring(1), escapeHTML(jMVP(this).attr("title")), "#" + urlFWOnDeleteMasterOrDetails + "&Page=1");
        return false;
    });

    //đăng ký xem row
    jMVP(selector + " .gridView a.view").click(function () {
        var titleDiag = jMVP(this).attr("title");
        if (titleDiag == '')
            titleDiag = 'Xem thông tin bản ghi';
        jMVP.post(urlview + '?itemid=' + jMVP(this).attr("href").substring(1), function (data) {
            jMVP("#dialog-form").html(data).dialog({
                title: titleDiag,
                resizable: true,
                height: viewHeight,
                width: viewWidth,
                modal: false,
                buttons: {
                    "Đóng cửa sổ": function () {
                        jMVP(this).html("").dialog("close");
                        jMVP("div.ui-dialog-buttonpane").remove();
                    }
                }
            });
        });
        return false;
    });
    //đăng ký xem bản đồ
    jMVP(selector + " .gridView a.gmap").click(function () {
        jMVP.post(encodeURI(urlgmapview + '?itemid=' + jMVP(this).attr("href").substring(1)), { "do": "viewmap" }, function (data) {
            jMVP("#dialog-form").html(data);
        });
        jMVP("#dialog-form").dialog(
				{ title: "Xem thông tin bản đồ", width: gmapwidth, height: gmapheight }).dialog("open");
        return false;
    });
}


//xoa row tren grid
function rowDelete(urlPost, arrRowId, rowTitle, urlFw) {
    var titleDia = '';
    if (arrRowId == '')
        createMessage("Thông báo", "Bạn chưa chọn bản ghi nào");
    else {
        if (arrRowId.indexOf(',') > 0)
            titleDia = "Xóa các bản ghi đã chọn";
        else
            titleDia = "Xóa bản ghi đã chọn";
        jMVP("#dialog-confirm").attr(titleDia);
        jMVP("#dialog-confirm").html("<p>Bạn có chắc chắn muốn xóa:<br> " + rowTitle + "</p>");
        var comfirmReturn = false;
        jMVP("#dialog-confirm").dialog({
            title: titleDia,
            resizable: false,
            height: 200,
            width: 400,
            modal: false,
            buttons: {
                "Tiếp tục": function () {
                    jMVP(this).dialog("close");
                    jMVP.post(encodeURI(urlPost), { "do": "delete", "itemId": "" + arrRowId + "" }, function (data) {
                        if (data.Erros) {
                            createMessage("Có lỗi xảy ra", "Lỗi được thông báo: " + data.Message);
                        }
                        else {
                            createMessage("Thông báo", data.Message);
                            window.location.href = urlFw + '&type=delete&idDelete=' + arrRowId;
                        }
                    });
                },
                "Hủy lệnh xóa": function () {
                    jMVP(this).dialog("close");
                }
            }
        });
    }
}
function trim12(str) {
    var str = str.replace(/^\s\s*/, ''),
		ws = /\s/,
		i = str.length;
    while (ws.test(str.charAt(--i)));
    return str.slice(0, i + 1);
}