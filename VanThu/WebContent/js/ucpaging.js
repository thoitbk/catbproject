//các hàm phục vụ phân trang
function SortddlRowPerPage(controlid) {
    // get the select
    var jMVPdd = jMVP('#' + controlid);
    if (jMVPdd.length > 0) { // make sure we found the select we were looking for

        // save the selected value
        var selectedVal = jMVPdd.val();

        // get the options and loop through them
        var jMVPoptions = jMVP('option', jMVPdd);
        var arrVals = [];
        jMVPoptions.each(function () {
            // push each option value and text into an array
            arrVals.push({
                val: jMVP(this).val(),
                text: jMVP(this).text()
            });
        });

        // sort the array by the value (change val to text to sort by text instead)
        arrVals.sort(function (a, b) {
            return a.val - b.val;
        });

        // loop through the sorted array and set the text/values to the options
        for (var i = 0, l = arrVals.length; i < l; i++) {
            jMVP(jMVPoptions[i]).val(arrVals[i].val).text(arrVals[i].text);
        }

        // set the selected value back
        jMVPdd.val(selectedVal);
    }
}
//check enter
function checkEnter(e) {

    var characterCode

    if (e && e.which) {
        e = e
        characterCode = e.which
    }
    else {
        e = event
        characterCode = e.keyCode
    }

    if (characterCode == 13) { //if generated character code is equal to ascii 13 (if enter key)
        return false;
    }
    else {
        return true;
    }

}
function isInt(x) {
    var y = parseInt(x);
    if (isNaN(y)) return false;
    return x == y && x.toString() == y.toString() && x.toString() != "0";
}
function SetJumpPage(pvalue, nextPre, CPage, toltalpage, pagingButton) {
    //nếu pre trang hiện tại là trang đầu bỏ qua
    if (nextPre == 'p' && parseInt(CPage) == 1)
        return false;
    var iMaxValue = parseInt(toltalpage);
    //nếu next trang hiện tại là trang cuối bỏ qua
    if (nextPre == 'n' && parseInt(CPage) == iMaxValue)
        return false;
    //không phải là số bỏ qua
    if (!isInt(pvalue) || parseInt(pvalue) < 1) {
        createMessage("Thông báo", "Số trang phải là số nguyên dương");
        return false;
    }
    //nhảy chính trang hiện tại bỏ qua
    if (parseInt(pvalue) == parseInt(CPage))
        return false;
    //vượt khoảng bỏ qua
    if (parseInt(pvalue) > iMaxValue) {
        createMessage("Thông báo", "Số trang phải nhỏ hơn " + toltalpage);
        return false;
    }

    jMVP("#" + pagingButton).attr("title", pvalue);
    jMVP("#" + pagingButton).click();
    return false;
}
function chageRowPerPage(ddlRowPerPageId, pagingButton) {
    SoBanGhiTrenTrang = jMVP("#" + ddlRowPerPageId).val();
//    rowperpagereturn = jMVP("#" + ddlRowPerPageId).val();
    jMVP("#" + pagingButton).attr("title", 1);
    jMVP("#" + pagingButton).click();
    return false;
}
function SetJumpPageTextBox(e, prefix) {
    if (!checkEnter(e))
        return SetJumpPage(jMVP.trim(jMVP("#" + prefix + "txtPageNumber").val()), 'txt');
    else
        return true;
}
function RegisCombobox(controlid, comboboxtype) {
    if (comboboxtype == "1")
    {
        registerSpecial(comboboxtype);

    } 
    else 
    {
        registerNormal(controlid);
    }
}
function registerSpecial(comboboxtype) {
    (function (jMVP) {
        jMVP.widget("ui.combobox", {
            _create: function () {
                var self = this,
					    select = this.element.hide(),
					    selected = select.children(":selected"),
					    value = selected.val() ? selected.text() : "";
                var input = this.input = jMVP("<input>")
					    .insertAfter(select)
					    .val(value)
					    .autocomplete({
					        delay: 0,
					        minLength: 0,
					        source: function (request, response) {
					            var matcher = new RegExp(jMVP.ui.autocomplete.escapeRegex(request.term), "i");
					            response(select.children("option").map(function () {
					                var text = jMVP(this).text();
					                if (this.value && (!request.term || matcher.test(text)))
					                    return {
					                        label: text.replace(
											    new RegExp(
												    "(?![^&;]+;)(?!<[^<>]*)(" +
												    jMVP.ui.autocomplete.escapeRegex(request.term) +
												    ")(?![^<>]*>)(?![^&;]+;)", "gi"
											    ), "<strong>$1</strong>"),
					                        value: text,
					                        option: this
					                    };
					            }));
					        },
					        select: function (event, ui) {
					            ui.item.option.selected = true;
					            self._trigger("selected", event, {
					                item: ui.item.option
					            });
					        },
					        change: function (event, ui) {
					            if (!ui.item) {
					                var matcher = new RegExp("^" + jMVP.ui.autocomplete.escapeRegex(jMVP(this).val()) + "$", "i"),
									    valid = false;
					                select.children("option").each(function () {
					                    if (jMVP(this).text().match(matcher)) {
					                        this.selected = valid = true;
					                        return false;
					                    }
					                });
					                if (!valid) {
					                    // remove invalid value, as it didn't match anything
					                    jMVP(this).val("");
					                    select.val("");
					                    input.data("autocomplete").term = "";
					                    return false;
					                }
					            }
					        }
					    })
					    .addClass("ui-widget ui-widget-content ui-corner-left");

                input.data("autocomplete")._renderItem = function (ul, item) {
                    return jMVP("<li></li>")
						    .data("item.autocomplete", item)
						    .append("<a>" + item.label + "</a>")
						    .appendTo(ul);
                };

                this.button = jMVP("<button type='button'>&nbsp;</button>")
					    .attr("tabIndex", -1)
					    .attr("title", "Show All Items")
					    .insertAfter(input)
					    .button({
					        icons: {
					            primary: "ui-icon-triangle-1-s"
					        },
					        text: false
					    })
					    .removeClass("ui-corner-all")
					    .addClass("ui-corner-right ui-button-icon")
					    .click(function () {
					        // close if already visible
					        if (input.autocomplete("widget").is(":visible")) {
					            input.autocomplete("close");
					            return;
					        }

					        // work around a bug (likely same cause as #5265)
					        jMVP(this).blur();

					        // pass empty string as value to search for, displaying all results
					        input.autocomplete("search", "");
					        input.focus();
					    });
            },

            destroy: function () {
                this.input.remove();
                this.button.remove();
                this.element.show();
                jMVP.Widget.prototype.destroy.call(this);
            }
        });
    })(jQuery);
    GoiCombobox(comboboxtype);
}
function GoiCombobox(comboboxtype) {
    jMVP("#" + comboboxtype).combobox();
    jMVP("#toggle").click(function () {
        jMVP("#" + comboboxtype).toggle();
    });
}

function registerNormal(controlid) {
    (function (jMVP) {
        jMVP.widget("ui.combobox", {
            _create: function () {
                var self = this,
					    select = this.element.hide(),
					    selected = select.children(":selected"),
					    value = selected.val() ? selected.text() : "";
                var input = this.input = jMVP("<input>")
					    .insertAfter(select)
					    .val(value)
					    .autocomplete({
					        delay: 0,
					        minLength: 0,
					        source: function (request, response) {
					            var matcher = new RegExp(jMVP.ui.autocomplete.escapeRegex(request.term), "i");
					            response(select.children("option").map(function () {
					                var text = jMVP(this).text();
					                if (this.value && (!request.term || matcher.test(text)))
					                    return {
					                        label: text.replace(
											    new RegExp(
												    "(?![^&;]+;)(?!<[^<>]*)(" +
												    jMVP.ui.autocomplete.escapeRegex(request.term) +
												    ")(?![^<>]*>)(?![^&;]+;)", "gi"
											    ), "<strong>$1</strong>"),
					                        value: text,
					                        option: this
					                    };
					            }));
					        },
					        select: function (event, ui) {
					            ui.item.option.selected = true;
					            self._trigger("selected", event, {
					                item: ui.item.option
					            });
					        },
					        change: function (event, ui) {
					            if (!ui.item) {
					                var matcher = new RegExp("^" + jMVP.ui.autocomplete.escapeRegex(jMVP(this).val()) + "$", "i"),
									    valid = false;
					                select.children("option").each(function () {
					                    if (jMVP(this).text().match(matcher)) {
					                        this.selected = valid = true;
					                        return false;
					                    }
					                });
					                if (!valid) {
					                    // remove invalid value, as it didn't match anything
					                    jMVP(this).val("");
					                    select.val("");
					                    input.data("autocomplete").term = "";
					                    return false;
					                }
					            }
					        }
					    })
					    .addClass("ui-widget ui-widget-content ui-corner-left");

                input.data("autocomplete")._renderItem = function (ul, item) {
                    return jMVP("<li></li>")
						    .data("item.autocomplete", item)
						    .append("<a>" + item.label + "</a>")
						    .appendTo(ul);
                };

                this.button = jMVP("<button type='button'>&nbsp;</button>")
					    .attr("tabIndex", -1)
					    .attr("title", "Show All Items")
					    .insertAfter(input)
					    .button({
					        icons: {
					            primary: "ui-icon-triangle-1-s"
					        },
					        text: false
					    })
					    .removeClass("ui-corner-all")
					    .addClass("ui-corner-right ui-button-icon")
					    .click(function () {
					        // close if already visible
					        if (input.autocomplete("widget").is(":visible")) {
					            input.autocomplete("close");
					            return;
					        }

					        // work around a bug (likely same cause as #5265)
					        jMVP(this).blur();

					        // pass empty string as value to search for, displaying all results
					        input.autocomplete("search", "");
					        input.focus();
					    });
            },

            destroy: function () {
                this.input.remove();
                this.button.remove();
                this.element.show();
                jMVP.Widget.prototype.destroy.call(this);
            }
        });
    })(jQuery);
    callCombobox(controlid);
}
function callCombobox(controlid) {
    jMVP("#" + controlid).combobox();
    jMVP("#toggle").click(function () {
        jMVP("#" + controlid).toggle();
    });
}