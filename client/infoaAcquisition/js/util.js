function watchJSON(json) {
	console.log(JSON.stringify(json));
}

function sleep(d) {
	// sleep(5000); //当前方法暂停5秒
	for (var t = Date.now(); Date.now() - t <= d;);
}
// 关闭数据库
function closeDB() {
	console.log('关闭数据库: ');
	plus.sqlite.closeDatabase({
		name: 'info',
		success: function(e) {
			console.log('closeDatabase success: ' + JSON.stringify(e));
		},
		fail: function(e) {
			console.log('closeDatabase fail: ' + JSON.stringify(e));
		}
	});
}

// 打开数据库
function openDB() {
	console.log('打开数据库: ');
	plus.sqlite.openDatabase({
		name: 'info',
		path: '_doc/info.db',
		success: function(e) {
			console.log('openDatabase success: ' + JSON.stringify(e));
		},
		fail: function(e) {
			console.log('openDatabase success: ' + JSON.stringify(e));
		}
	});
}

// 查询SQL语句
function selectSQL(sqlStr) {
	openDB();
	console.log('查询SQL语句: ' + sqlStr);
	plus.sqlite.selectSql({
		name: 'info',
		sql: sqlStr,
		success: function(e) {
			console.log('selectSql success: ' + JSON.stringify(e));
		},
		fail: function(e) {
			console.log('selectSql fail: ' + JSON.stringify(e));
		}
	});
	closeDB();
}

// 检查数据库是否打开
function isOpenDB() {
	if (plus.sqlite.isOpenDatabase({
			name: 'info',
			path: '_doc/info.db',
		})) {
		// plus.nativeUI.alert('Opened!');
		return true;
	} else {
		// plus.nativeUI.alert('Unopened!');
		return false;
	}
}

/**
 * 从当前页面pop到目标页面
 * @param {String} targetId 目标页面ID
 */
function popToTarget(targetId) {
	//获取目标页面
	var target = plus.webview.getWebviewById(targetId);
	if (!target) {
		console.log("目标页面不存在！");
		return;
	}
	//获取当前页面
	var current = plus.webview.currentWebview();
	if (current === target) {
		console.log("目标页面是当前页面！");
		return;
	}
	//将要关闭的页面
	var pages = new Array(current);
	//父级页面
	var opener = current.opener();
	while (opener) {
		if (opener === target) { //找到了目标页面
			//关闭目标页面的所有子级页面pages
			pages.map(function(page) {
				page.close();
			});
			return;
		}
		pages.push(opener);
		opener = opener.opener();
	}
	//没有找到目标页面
	console.log("目标页面不是当前页面的祖先页面！");
}


function goHome() {
	//到首页去，关闭其他所有页面
	console.log('到首页去，关闭其他所有页面');
	// 	var btnArray = ['否', '是']; //注意这里的顺序是先否再是
	// 
	// 	mui.confirm("MUI是个好框架，确认？", 'Hello MUI', btnArray, function(e) {
	// 
	// 			if (e.index == 1) { //索引是1的就是选择的是
	// 
	// 				info.innerText = '你刚确认MUI是个好框架';
	// 
	// 			} else {
	// 
	// 				info.innerText = 'MUI没有得到你的认可，继续加油'
	// 
	// 			}
	// 		}
	var allPage = plus.webview.all();
	watchJSON(allPage);
	homeId = plus.webview.getLaunchWebview().id;
	console.log(
		homeId);

	for (var i = 0, l = allPage.length; i < l; i++) {
		if (allPage[i].id != homeId || typeof(allPage[i]) == 'undefined' || typeof(allPage[i]['id']) == 'undefined') {
			allPage[i].close('none');
		}
		// for (var key in allPage[i]) {
		key = 'id';
		console.log(key + ':' + allPage[i][key]);
		if (typeof(allPage[i][key]) == 'undefined') {
			var webview = plus.webview.getWebviewById(homeId); //假设第一个Webview的id是home
			webview.show();
		}
		// }
	}
}
var oldBack = mui.back;
//重写返回上一页，方式页面丢失的问题
mui.back = function(pageId) {
	console.log(pageId);
	if (typeof(pageId) == "undefined") {
		goHome();
	} else {
		var webview = plus.webview.getWebviewById(pageId); //假设第一个Webview的id是home
		webview.show();
	}
};

//将图片压缩并转成base64 
function getBase64Image(img) {
	var canvas = document.createElement("canvas");
	// console.log(img);
	// var img = document.getElementById('image');
	var width = img.width;
	var height = img.height;
	console.log(width);
	// calculate the width and height, constraining the proportions 
	if (width > 500) {
		height = height / 1.5;
		width = width / 1.5;
	}

	if (height > 500) {
		height = height / 1.5;
		width = width / 1.5;
	}

	canvas.width = width; /*设置新的图片的宽度*/
	canvas.height = height; /*设置新的图片的长度*/
	var ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0, width, height); /*绘图*/
	var dataURL = canvas.toDataURL("image/png", 1);
	// console.log(dataURL);
	return dataURL;
	// return dataURL.replace("data:image/png;base64,", "");
}

//将账号与密码保存到Localstore 
function remPwd() {
	var autoLoginButton = document.getElementById("autoLogin");
	var accountBox = document.getElementById('account');
	var passwordBox = document.getElementById('password');
	gender = autoLoginButton.classList.contains('mui-active');
	console.log(gender) //ture/false

	if (gender == true) {
		localStorage.setItem("account", accountBox.value);
		localStorage.setItem("password", passwordBox.value);
	} else {
		localStorage.removeItem("account");
		localStorage.removeItem("password");
	}
}

//日期时间处理
function conver(s) {
	return s < 10 ? '0' + s : s;
}

function dataURLtoBlob(dataurl) {
	var arr = dataurl.split(','),
		mime = arr[0].match(/:(.*?);/)[1],
		bstr = atob(arr[1]),
		n = bstr.length,
		u8arr = new Uint8Array(n);
	while (n--) {
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {
		type: mime
	});
}

function fixInteger(num, n) {
	return (Array(n).join(0) + num).slice(-n);
}

function postData(url, data, callback, waitingDialog) {
	console.log()
	console.log(data);
	console.log(url);
	// formData.append('new', data.file);
	// Object.keys(data).forEach((key) => {
	//   formData.append(key, data[key]);
	// });
	// console.log(formData);
	mui.ajax(url, {
		data: data,
		// cache: false,
		// processData: false,
		contentType: 'application/json; charset=UTF-8',
		type: 'post',
		// contentType: "multipart/form-data",
		timeout: 5000,
		success: callback,
		error: function(xhr, type, errorThrown) {
			waitingDialog.close();
			console.log(JSON.stringify(errorThrown));
			mui.alert("<网络连接失败，请重新尝试一下>", "错误", "OK", null);

		},
		headers: {
			// 'access_token': AUTH_TOKEN,
			// 'user_key': USER_KEY,
		}
	});
}

function postImage(url, data, callback, waitingDialog) {
	console.log()
	console.log(data);
	console.log(url);
	// var formData = new FormData();
	var formData = new FormData();
	// formData.append("accountnum", 123456); 
	formData.append('depict', data.depict);

	formData.append('file', new File([data.file], '12345.png'));
	// formData.append('new', data.file);
	// Object.keys(data).forEach((key) => {
	//   formData.append(key, data[key]);
	// });
	console.log(formData);
	mui.ajax(url, {
		data: formData,
		// cache: false,
		processData: false,
		contentType: false,
		type: 'post',
		// contentType: "multipart/form-data",
		timeout: 50000,
		success: callback,
		error: function(xhr, type, errorThrown) {
			waitingDialog.close();
			console.log(JSON.stringify(errorThrown));
			mui.alert("<网络连接失败，请重新尝试一下>", "错误", "OK", null);

		},
		headers: {
			// 'access_token': AUTH_TOKEN,
			// 'user_key': USER_KEY,
		}
	});
}

function getBase64Image2(img) { //传入图片路径，返回base64
	function getBase64Image(img, width, height) { //width、height调用时传入具体像素值，控制大小 ,不传则默认图像大小
		var canvas = document.createElement("canvas");
		canvas.width = width ? width : img.width;
		canvas.height = height ? height : img.height;

		var ctx = canvas.getContext("2d");
		ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
		var dataURL = canvas.toDataURL();
		return dataURL;
	}
	var image = new Image();
	image.crossOrigin = '';
	image.src = img;
	var deferred = $.Deferred();
	if (img) {
		image.onload = function() {
			deferred.resolve(getBase64Image(image)); //将base64传给done上传处理
		}
		return deferred.promise(); //问题要让onload完成后再return sessionStorage['imgTest']
	}
}
// getBase64(imgSrc)
// 	.then(function(base64) {
// 		console.log(base64);
// 	}, function(err) {
// 		console.log(err);
// 	});
