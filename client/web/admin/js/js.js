// 转为json数据格式
function transformToJson(formData) {
	var obj = {}
	for (var i in formData) {
		/*[{"name":"user","value":"hpc"},{"name":"pwd","value":"123"},{"name":"sex","value":"M"},{"name":"age","value":"100"},{"name":"phone","value":"13011112222"},{"name":"email","value":"xxx@xxx.com"}]
		 */
		//下标为的i的name做为json对象的key，下标为的i的value做为json对象的value
		obj[formData[i].name] = formData[i]['value'];
	}
	return obj;
}

function sleep(d) {
	// sleep(5000); //当前方法暂停5秒
	for (var t = Date.now(); Date.now() - t <= d;);
}

//添加或者修改json数据
function setJson(jsonStr, name, value) {
	if (!jsonStr) jsonStr = "{}";
	var jsonObj = JSON.parse(jsonStr);
	jsonObj[name] = value;
	return JSON.stringify(jsonObj);
}

//删除数据
function deleteJson(jsonStr, name) {
	if (!jsonStr) return null;
	var jsonObj = JSON.parse(jsonStr);
	delete jsonObj[name];
	return JSON.stringify(jsonObj);
}
//获得浏览器系统当前时间
function NOW() {
	var now = new Date();
	var nowYear = now.getFullYear(); //年
	var nowMonth = now.getMonth() + 1 < 10 ? "0" + (now.getMonth() + 1) : (now.getMonth() + 1); //月
	var nowDay = now.getDate() < 10 ? "0" + now.getDate() : now.getDate(); //日期
	var nowHour = now.getHours() < 10 ? "0" + now.getHours() : now.getHours(); //时
	var nowMinute = now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes(); //分
	var nowSeconds = now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds(); //分
	var nowDate = nowYear + "-" + nowMonth + "-" + nowDay;
	var nowTime = nowHour + ":" + nowMinute + ":" + nowSeconds;
	var dates = new Array(nowDate, nowTime);
	//console.log(dates);
	return dates;
}
//获取服务器时间
function getServerDate() {
	return new Date($.ajax({
		async: false
	}).getResponseHeader("Date"));
}

function GetUrlParam(paraName) {
	var url = document.location.toString();
	var arrObj = url.split("?");
	if (arrObj.length > 1) {
		//console.log(arrObj[1]);　　　　　　
		var arrPara = arrObj[1].split("&");
		//console.log(arrObj[1]);　　　　　　
		var arr;
		for (var i = 0; i < arrPara.length; i++) {
			arr = arrPara[i].split("=");
			if (arr != null && arr[0] == paraName) {
				console.log(arr[1].replace(/#/g, ""));
				return arr[1].replace(/#/g, "");
				//				return arr[1];　　　　　　　　
			}
		}
		return "";
	} else {
		return "";
	}
}
/**
 * 检查文件的后缀名
 * @param {Object} fileObj 文件对象
 */
function checkFileExt(fileObj) {
	var fileName = fileObj.name;
	var idx = fileName.lastIndexOf("."); //获取文件扩展名分割位
	if (idx != -1) {
		ext = fileName.substr(idx + 1).toUpperCase();
		ext = ext.toLowerCase();
		if (ext != 'doc' && ext != 'docx' && ext != 'pdf' && ext != 'xls' && ext != 'xlsx' && ext != 'ppt' && ext != 'pptx' &&
			ext != 'wps' && ext != 'cad' && ext != 'zip' && ext != '7z' && ext != 'rar') {
			alert("上传的不是文档文件！请上传以下格式文件： \r\n doc,docx,pdf,xls,xlsx,ppt,pptx,wps,cad,zip,rar,7z. \r\n 请重新选择文件上传");
			return false;
		} else {
			return true;
		}
	} else if (idx == -1) {
		alert("上传的不是文档文件！请上传以下格式文件： \r\n doc,docx,pdf,xls,xlsx,ppt,pptx,wps,cad,zip,rar,7z. \r\n 请重新选择文件上传");
		return false;
	} else {
		return true;
	}
}

function checkNULL(value, nameAlert) {
	////console.log(value + "," + nameAlert);
	if (value == '') {
		var alert = document.getElementById(nameAlert);
		////console.log(alert);
		alert.style.display = "inline";
		return false;
	}
	return true;
}

/**
 * 检查上传的文件大小
 */
function checkFileSize(fileObj) {
	////console.log("校验文件大小");
	////console.log(fileObj);
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	if (isIE && !fileObj) {
		//      var filePath = target.value;       
		//      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");          
		//      var file = fileSystem.GetFile (filePath);       
		//      fileSize = file.Size;      
	} else {
		fileSize = fileObj.size;
	}
	////console.log(fileSize);
	var size = fileSize / (1024 * 1024);
	////console.log(size);
	if (size > (10000)) {
		//		alert("文件大小不能超过2MB");
		return false;
	} else {
		return true;
	}
}

function checkImageSize(fileObj) {
	////console.log("校验文件大小");
	////console.log(fileObj);
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	if (isIE && !fileObj) {
		//      var filePath = target.value;       
		//      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");          
		//      var file = fileSystem.GetFile (filePath);       
		//      fileSize = file.Size;      
	} else {
		fileSize = fileObj.size;
	}
	////console.log(fileSize);
	var size = fileSize / (1024 * 1024);
	////console.log(size);
	if (size > (1000)) {
		alert("图片大小不能超过1000MB");
		return false;
	} else {
		return true;
	}
}

function setIndex1Times(date) {
	if (date.length == 2) {
		date1 = date[0].split("-");
		//console.log(date1)
		date2 = date[1].split(":");
		//console.log(date2)
		var dateStr = date1[0] + "年" + date1[1] + "月" + date1[2] + "日" + date2[0] + "时" + date2[1] + "分" + date2[2] + "秒";
		//console.log(document.getElementById("date"));
		document.getElementById("localTime").innerText = dateStr;
	} else {
		document.getElementById("localTime").innerText = "获取本地时间失败.";
	}
}

function setIndex2Times(date) {
	if (date.length == undefined) {
		document.getElementById("serverTime").innerText = date;
	} else {
		document.getElementById("serverTime").innerText = "获取服务器时间失败,请检查网络!";
	}
}

function runSetIndexTimes() {
	var date = NOW();
	setIndex1Times(date);
	var dates = getServerDate();
	setIndex2Times(dates);
}
// setInterval("runSetIndexTimes()", 1000);




//获取cookie
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') c = c.substring(1);
		if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
	}
	return "";
}

function dropOut(name) {
	if (confirm("您确定要退出吗?")) {
		clearCookie("token");
		window.location.href = "login.html";
	}

}

//清除cookie  
function clearCookie(name) {
	console.log(document.cookie);
	setCookie(name, "", -1);
	console.log(document.cookie);
}
//设置cookie
function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

// 确认密码
function verifyPass() {
	var pass = $("#pass").val();
	var qrpass = $("#qrpass").val();
	if (pass == qrpass) {
		alert("SUCCESS");
	} else {
		alert("FAIL");
	}
}

//获取操作系统信息
/*
 * 获取系统版本信息
 */
function getOsInfo() {
	let userAgent = window.navigator.userAgent.toLowerCase()
	let version = ''
	if (userAgent.indexOf("win") > -1) {
		if (userAgent.indexOf("windows nt 5.0") > -1 || userAgent.indexOf("Windows 2000") > -1) {
			version = "Windows 2000";
		} else if (userAgent.indexOf("windows nt 5.1") > -1 || userAgent.indexOf("Windows XP") > -1) {
			version = "Windows XP";
		} else if (userAgent.indexOf("windows nt 5.2") > -1 || userAgent.indexOf("Windows 2003") > -1) {
			version = "Windows 2003";
		} else if (userAgent.indexOf("windows nt 6.0") > -1 || userAgent.indexOf("Windows Vista") > -1) {
			version = "Windows Vista";
		} else if (userAgent.indexOf("windows nt 6.1") > -1 || userAgent.indexOf("windows 7") > -1) {
			version = "Windows 7";
		} else if (userAgent.indexOf("windows nt 6.2") > -1 || userAgent.indexOf("windows 8") > -1) {
			version = "Windows 8";
		} else if (userAgent.indexOf("windows nt 6.3") > -1) {
			version = "Windows 8.1";
		} else if (userAgent.indexOf("windows nt 6.4") > -1 || userAgent.indexOf("windows nt 10") > -1) {
			version = "Windows 10";
		} else {
			version = "Unknown";
		}
	} else if (userAgent.indexOf("iphone") > -1) {
		version = "Iphone";
	} else if (userAgent.indexOf("mac") > -1) {
		version = "Mac";
	} else if (userAgent.indexOf("x11") > -1 || userAgent.indexOf("unix") > -1 || userAgent.indexOf("sunname") > -1 ||
		userAgent.indexOf("bsd") > -1) {
		version = "Unix";
	} else if (userAgent.indexOf("linux") > -1) {
		if (userAgent.indexOf("android") > -1) {
			version = "Android"
		} else {
			version = "Linux";
		}
	} else {
		version = "Unknown";
	}
	return version;
}

//全选输入框
function checkAll(all, cchk) {
	// console.log(cchk);
	var all = document.getElementById(all);
	var mychk = document.getElementsByName(cchk);
	console.log(mychk);
	if (all.checked == true) {
		// alert("all.checked==true全选");
		if (mychk.length) {
			for (var i = 0; i < mychk.length; i++) {
				mychk[i].checked = true;
			}

		}
		mychk.chcked = true;
	} else {
		// alert("all.checked==false全不选");
		if (mychk.length) {
			for (var i = 0; i < mychk.length; i++) {
				mychk[i].checked = false;
			}

		}
	}

}

//根据checkbox获取勾选的值
function getChkValue(chk) {
	console.log(chk);
	var obj = document.getElementsByName(chk);
	console.log(obj)
	var baseTable = [];
	for (var i in obj) {
		if (obj[i].checked) {
			baseTable.push(obj[i].value);
		}
	}
	console.log(baseTable)
	return baseTable;
}

//分页列表展示
function showPage(page, type, total, url) {
	page = parseInt(page);
	total = parseInt(total);
	var str = '<span class="current">' + page + '</span>';

	for (var i = 1; i <= 3; i++) {
		if (page - i > 1) {
			str = '<a href=' + url + '?type=' + type + '&page=' + (page - i) + '>' + (page - i) + '</a> ' + str;
		}
		if (page + i < total) {
			str = str + ' ' + '<a href=' + url + '?type=' + type + '&page=' + (page + i) + '>' + (page + i) + '</a> ';
		}
	}

	if (page - 4 > 1) {
		str = '<a href="javascript:void(0)">...</a> ' + str;
	}

	if (page > 1) {
		str = '<a href=' + url + '?type=' + type + '&page=' + (page - 1) + '>' + '<上一页 ' + '</a> ' +
			'<a href=' + url + '?type=' + type + '&page=' + 1 + '>' + 1 + '</a> ' + str;
	}

	if (page + 4 < total) {
		str = str + '<a href="javascript:void(0)">...</a> ';
	}

	if (page < total) {
		str = str + ' ' + '<a href=' + url + '?type=' + type + '&page=' + (total) + '>' + total + '</a> ' +
			'<a href=' + url + '?type=' + type + '&page=' + (page + 1) + '>' + ' 下一页 >' + '</a> ';
	}

	return str;
}

//公用
function scroll() {
	var PartArea = document.getElementById('part2');
	var Scroll1 = document.getElementById('scroll1');
	var Scroll2 = document.getElementById('scroll2');
	console.log(Scroll2);
	Scroll2.innerHTML = Scroll1.innerHTML;

	function roll() {
		if (Scroll2.offsetHeight - PartArea.scrollTop <= 0) {
			PartArea.scrollTop -= Scroll1.offsetHeight;
		} else {
			PartArea.scrollTop++;
		}
	}

	var StopRoll = setInterval(roll(), 30);

	PartArea.onmouseover = function() {
		clearInterval(StopRoll);
	}
	PartArea.onmouseout = function() {
		StopRoll = setInterval(roll(), 30);
	}

}
