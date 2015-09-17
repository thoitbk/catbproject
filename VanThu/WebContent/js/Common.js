function popCenter(URL, name, w, h) {
    l = (screen.width - w) / 2;
    t = (screen.height - h) / 2;

    params = 'toolbars=1, scrollbars=1, location=0, statusbars=1, menubars=1, resize=0,';
    popCenterWin = window.open(URL, name, params + 'width=' + w + ', height=' + h + ', left=' + l + ', top=' + t);
    popCenterWin.focus();
    return false;
}

// Bắt độ dài của text box control phải nhỏ hơn hoặc bằng Maxlength
function checkLength(NameofControl, MaxLength) {
    var txtDescription = document.getElementById(NameofControl);

    if (txtDescription.value.length > MaxLength) {
        txtDescription.value = txtDescription.value.substring(0, MaxLength);
        return false;
    }
    return true;
}

function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + '.' +
        num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num);
}
// lay control theo name
// yeu cau fai co jquery
function GetControlByName(Name) {
    var strId = "*[id*='" + Name + "']";
    return $(strId);
} 
// Tính tiền khi số lượng và giá đã được format theo numericTextbox
function TinhTienFormated(num, pice, seperatorPlace, decimalPlace, numOfDec) {
    var nDecimal = Math.pow(10, numOfDec);
    num = num.toString().replace(RegExp(seperatorPlace == '.' ? '\\.' : seperatorPlace, 'g'), '');
    num = num.toString().replace(RegExp(decimalPlace, 'g'), '.');
    num = num.toString().replace(/\$|\,/g, '');

    pice = pice.toString().replace(RegExp(seperatorPlace == '.' ? '\\.' : seperatorPlace, 'g'), '');
    pice = pice.toString().replace(RegExp(decimalPlace, 'g'), '.');
    pice = pice.toString().replace(/\$|\,/g, '');

    if (isNaN(num)) num = '0';
    sign = (num == (num = Math.abs(num)));
    num = Math.round(num * nDecimal);
    pice = Math.round(pice * nDecimal);

    var total = Math.round(num * pice);
    cents = total % (nDecimal * nDecimal);
    total = Math.floor(total / (nDecimal * nDecimal)).toString();
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
    for (var i = 0; i < Math.floor((total.length - (1 + i)) / 3); i++)
        total = total.substring(0, total.length - (4 * i + 3)) + seperatorPlace + total.substring(total.length - (4 * i + 3));
    if (cents_rel != '')
        return (((sign) ? '' : '-') + total + decimalPlace + cents_rel);
    else
        return (((sign) ? '' : '-') + total);
}
    