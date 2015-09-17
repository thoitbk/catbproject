jMVP(document).ready(
		function() {
			// initAjaxLoad("/VanBan/TimKiem/pFormTimVB.aspx", '.SearchVB');
			jMVP("#Ribbon").officebar(
					{
						onSelectTab : function(e) {
							var link = e.rel;
							if (link != "javascript:;" && link != "#"
									&& link != "") {
								if (link == "out") {
									jMVP("#btnDangXuat").click();
								} else if (link == "old") {
									redirectOldVersion();
								} else {
									window.location = link;
								}
							}
						},
						onBeforeShowSplitMenu : function(e) {
							jMVP("#log").append(
									"before split open: " + e.rel + "<br/>");
						},
						onAfterShowSplitMenu : function(e) {
							jMVP("#log").append(
									"after split open: " + e.rel + "<br/>");
						},
						onAfterHideSplit : function(e) {
							jMVP("#log").append("split menu closed<br/>");
						},
						onShowDropdown : function(e) {
							jMVP("#log").append(
									"opened dropdown: " + e.rel + "<br/>");
						},
						onHideDropdown : function(e) {
							jMVP("#log").append(
									"closed dropdown: " + e.rel + "<br/>");
						},
						onClickButton : function(e) {
							var link = e.rel;
							if (link != "javascript:;" && link != "#"
									&& link != "") {
								if (link == "out") {
									jMVP("#btnDangXuat").click();
								} else if (link == "old") {
									redirectOldVersion();
								} else {
									window.location = link;
								}
							}
						} // jMVP("#log").append("clicked on:
							// "+e.rel+"<br/>"); }
					});
			jMVP("#Ribbon").officebarBind("textboxes", function(e) {
				jMVP("#log").append("custom bind on textboxes tab<br/>");
			});
			jMVP("#Ribbon").officebarBind("home>blablabutton", function(e) {
				jMVP("#log").append("custom bind on blablabutton<br/>");
			});
			jMVP("#Ribbon").officebarBind("insert>new", function(e) {
				jMVP("#log").append("custom bind on new<br/>");
			});

			if ("269d2bf5-971a-4d7b-97fa-5cfe3eb41b03" != "") {
				SetActiveRibbon("269d2bf5-971a-4d7b-97fa-5cfe3eb41b03",
						"269d2bf5-971a-4d7b-97fa-5cfe3eb41b03");
			}
			
			document.getElementById("ngay").innerHTML = getDate();
		});

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

function SetActiveRibbon(parentid, relateid) {
	jMVP("#Ribbon li").removeClass("current");
	jMVP("#Ribbon li[attrcheck='" + relateid + "']").addClass("current");
	jMVP("#Ribbon li[attrcheck='" + relateid + "'] div.officetab").show();
	// if (relateid != "" && parentid != relateid) {
	// jMVP("#Ribbon a").removeClass("current");
	// jMVP("#Ribbon a[attrcheck='" + relateid + "']").removeClass("current");
	// }
}
function SignOut() {
	jMVP.post("/hethong/logout.aspx", {
		"do" : "logout"
	},
			function(data) {
				if (!data.Erros) {
					var domain = window.location.protocol + '//'
							+ window.location.host;
					var currentRequest = escape((window.location.href).replace(
							domain, ''));
					currentRequest = '/Home.aspx';
					window.location.href = '/Default.aspx';
				}
			});
}
function ChangeInfo() {

	jMVP.post(encodeURI("/DanhMuc/Users/pformChangeUsers.aspx"), {
		"do" : "edit"
	}, function(data) {
		jMVP("#dialog-form").html(data);
	});
	jMVP("#dialog-form").dialog({
		title : "Thay đổi thông tin người dùng",
		width : 600,
		height : 300
	}).dialog("open");

}
function redirectOldVersion() {
	window.location = "MVPLocal.aspx";
}
