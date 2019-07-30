function submitInfo() {
	info = getForm('#' + 'infoForm');
	var perChk = info.perChk;
	console.log(perChk);
	var wait = document.getElementById("wait");
	openDB();
	var stop = document.getElementById("endq");
	stop.value = perChk.length;
	console.log('stop.value' + stop.value)
	var tempwait = document.getElementById("tempwait");
	for (var i = 0; i < perChk.length; i++) {
		console.log('stop.valueaaaaa' + stop.value + ' ' + perChk.length);
		wait.value = 0;
		var waitInput = document.createElement("input"); //创建一个标签
		waitInput.id = "wait" + i;
		waitInput.type = 'text';
		waitInput.value = '0';
		waitInput.setAttribute("value", "0");
		tempwait.appendChild(waitInput);
		document.getElementById(waitInput.id).value = '0';
		console.log(waitInput);
		var wa = plus.nativeUI.showWaiting("正在上传" + (i + 1) + "/" + perChk.length + "..");
		// mui.showLoading("正在上传" + (i + 1) + "/" + perChk.length + "..", "div");
		plus.sqlite.selectSql({
			name: 'info',
			sql: 'select rowid, * from infoDB where rowid==' + perChk[i],
			success: function(e) {
				console.log('selectSql success: ' + JSON.stringify(e));
				// photoId = upPhoto(e[0]);
				flag_arr = getFlag(e[0]);
				console.log(flag_arr);
				// flag=w_upPhoto(e[0]);
				var flag = flag_arr.length;
				if (flag == 0) {
					photoId = getImageId();
					console.log(photoId);
					data = zhenliJson(e[0], photoId);
					console.log(data);
					console.log(i)
					upData(data, wait.value);
					wait.value = parseInt(wait.value) + 1;
				} else {
					console.log(i)
					w_upPhoto(flag_arr, wait.value);
					wait.value = parseInt(wait.value) + 1;
				}
			},
			fail: function(e) {
				console.log('selectSql fail: ' + JSON.stringify(e));
				wa.close();
			}
		});
		wa.close();
	}
}

//本地删除
function deleteInfo() {
	info = getForm('#' + 'infoForm');
	var perChk = info.perChk;
	console.log(perChk);
	var btnArray = ['否', '是'];
	if (typeof(perChk) !== "undefined") {
		mui.confirm('删除后无法在手机上查看，确定要删除吗？（仅删除手机数据，云端数据不受影响。）', '信采助手', btnArray, function(e) {
			if (e.index == 1) {
				deleteInfoa(perChk);
			} else {
				mui.toast('您取消了删除');
			}
		})
	} else {
		mui.toast("没有选中任何项")
	}
}

function deleteInfoa(perChk) {
	var idnum = '(';
	for (var i = 0; i < perChk.length; i++) {
		idnum = idnum + perChk[i] + ',';
	}
	idnum = idnum.substr(0, idnum.length - 1);
	idnum = idnum + ")";
	console.log(idnum);
	plus.sqlite.selectSql({
		name: 'info',
		sql: 'update infoDB set isUp = 8 where rowid in ' + idnum,
		success: function(e) {
			console.log('runSql success: ' + JSON.stringify(e));
			mui.toast("删除成功！");
			w_showInfo()
		},
		fail: function(e) {
			console.log('runSql fail: ' + JSON.stringify(e));
			w_showInfo();
		}
	});
}

function upData(data, num) {
	console.log(document.getElementById('wait' + num).value);
	console.log(data)
	var token = localStorage.getItem('TOKEN_TOKEN');
	data['token'] = token;
	// {
	// 	'data': data,
	// }
	// var AUTH_TOKEN = myStorage.getItem('AUTH_TOKEN');
	// var USER_KEY = myStorage.getItem('USER_KEY');
	var stop = document.getElementById("endq");
	mui.ajax(upUrl, {
		type: 'POST',
		dataType: 'json',
		cache: false,
		data: data,
		contentType: "application/json; charset=UTF-8",
		async: false,
		timeout: 1000, //超时时间设置为10秒；
		success: function(res) {
			console.log(stop.value);
			stop.value = parseInt(stop.value) - 1;
			if (typeof res == 'string') {
				var res = JSON.parse(res);
			}
			//如果登陆失效
			if (res.status == '401' || data.status == "400") {
				mui.toast(res.msg);
				localStorage.removeItem("TOKEN_TOKEN");
				mui.openWindow({
					url: '../login.html'
				})
			} else if (res.status == '403') {
				mui.toast(res.msg);
				// hideLoading();
				return false;
			} else if (res.status != '200') {
				mui.toast('err#' + res.status + ':' + res.msg);
			} else {
				console.log('stop' + stop.value);
				console.log('成功了！')
				console.log(res);
				document.getElementById('wait' + num).value = 1;
				plus.sqlite.selectSql({
					name: 'info',
					sql: 'update infoDB set isUp = 1 where rowid = ' + data.rowid,
					success: function(e) {
						console.log('runSql success: ' + JSON.stringify(e));
						w_showInfo()
						if (stop.value == 0) {
							w_showInfo();
						}
					},
					fail: function(e) {
						console.log('runSql fail: ' + JSON.stringify(e));
						if (stop.value == 0) {
							w_showInfo();
						}
					}
				});

			}
			// mui.hideLoading();
		},
		error: function(xhr, type, errorThrown) {

			console.log(stop.value);
			stop.value = parseInt(stop.value) - 1;
			console.log(stop.value);
			console.log(JSON.stringify(xhr));
			if (stop.value == 0) {
				w_showInfo(0);
			}
			mui.hideLoading();

		},
		headers: {
			// 'access_token': AUTH_TOKEN,
			// 'user_key': USER_KEY,
		}
	});
}


function zhenliJson(res, photoId) {
	res['imgs'] = photoId;
	var a = res.damage;
	res.damage = a.split(',').map(Number);
	console.log(res.damage);
	res.body1 = res.typea;
	res.body2 = res.typeb;
	res.body3 = res.typec;
	return res;
}

//打开"采集信息"
function allChk(iNum) {
	// var aForm = document.getElementById("mainList");
	var mychk = document.getElementsByName("perChk[]");
	for (var i = 0; i < mychk.length; i++) {
		if (iNum <= 0) {
			mychk[i].checked = !mychk[i].checked;
		} else {
			mychk[i].checked = iNum;
		}
	}
}

//打开"任务上传"
function openSubmit() {
	var url = "testTakePhoto.html";
	plus.webview.create(url).show();
}

function showInfo(result) {
	watchJSON(result);
	var mainList = document.getElementById("mainList");
	mainList.innerHTML = "";
	for (i = result.length - 1; i >= 0; i--) {
		var temp = "<li class='mui-table-view-cell'>" +
			"<div class='mui-input-row mui-checkbox mui-left'>" +
			"<label>" + result[i].rowid + "." + result[i].hold + "</label>" +
			"<input name='perChk[]' value='" + result[i].rowid + "' type='checkbox' >" +
			"</div>" +
			"</li>"
		// console.log(temp);
		mainList.innerHTML += temp;
	}
}

function showInfoFail(result) {
	watchJSON(result);
	var mainList = document.getElementById("mainList");
	mainList.innerHTML = "";
	var temp = "<li class='mui-table-view-cell'>" +
		"<div class='mui-input-row mui-checkbox mui-left'>" +
		"<label> 数据库中没有数据 </label>" +
		"<input name='perChk[]' value='" + 'result[i].rowid' + "' type='checkbox' disabled='disabled' >" +
		"</div>" +
		"</li>"
	// console.log(temp);
	mainList.innerHTML += temp;
}

function upPhoto2(result) {
	var photoId = new Array();
	watchJSON(result);
	var flag = 0;
	return photoId;
}

function uploadPictuer(files, depict, server) {
	var wt = plus.nativeUI.showWaiting();
	var task = plus.uploader.createUpload(server, {
			method: "POST"
		},
		function(t, status) { //上传完成
			if (status == 200) {
				alert("上传成功：" + t.responseText);
				wt.close(); //关闭等待提示按钮
			} else {
				alert("上传失败：" + status);
				wt.close(); //关闭等待提示按钮
			}
		}
	);
	//添加其他参数
	task.addData("depict", depict);
	task.addFile(files.src, {
		key: "dddd"
	});
	task.start();
}


function getFlag(result) {
	var flag = 0;
	var flag_arr = [];
	var photoId = [];
	for (var i = 1; i <= 4; i++) {
		var photoSrc = 'photoSrc' + i;
		var photoDes = 'photoDes' + i;
		var tempId = "imgid" + i;
		console.log('wai' + typeof(result[photoSrc]) + '_' + photoSrc)
		if (!result[photoSrc] == '') {
			var image = new Image();
			image.src = result[photoSrc];
			image.alt = result[photoDes];
			image.info = tempId;
			image.mother = result;
			flag_arr[flag] = image;
			flag = flag + 1;
		}
	}
	console.log(flag_arr);
	for (i = 0; i < flag; i++) {
		var image = flag_arr[i];
		image.count = flag;
		flag_arr[i] = image;
	}
	return flag_arr;
}

function w_upPhoto(flag_arr, num) {
	console.log(num)
	var flag = flag_arr.length;
	for (i = 0; i <= flag; i++) {
		var c = 0;
		if (i == flag) {
			do {
				sleep(500);
				c++;
			}
			while (checkIdChange(i) && c < 10);
			photoId = getImageId0();
			console.log(photoId);
		} else {
			image = flag_arr[i];
			console.log(image);
			upPhotoMain(image, num);
			do {
				setTimeout("console.log('5 seconds!')", 500);
				c++;
			}
			while (checkIdChange(i) && c < 10);
			console.log('正在上传第' + i);
		}
	}
}


function checkIdChange(i) {
	var obj = document.getElementsByName("imgid[]");
	// console.log(i);
	console.log(obj[i].value + "" + i);
	if (obj[i].value == 0) {
		return false;
	} else {
		return true;
	}
}

function upPhotoMain(image, num) {

	// var files = document.getElementById('image');
	// 上传文件
	// --------------
	console.log(image);
	// uploadPictuer(image,imgUrl);
	// --------------
	// console.log(image);
	console.log(image.src);
	// var AUTH_TOKEN = myStorage.getItem('AUTH_TOKEN');
	var token = localStorage.getItem('TOKEN_TOKEN');
	getBase64Image2(image.src)
		.then(function(base64) {
			var imgData = base64;
			var imgBlob = dataURLtoBlob(imgData);
			document.getElementById("imgBlob").value = imgBlob;
			console.log(imgBlob);

			var wd = plus.nativeUI.showWaiting();
			// console.log(photoDes);
			console.log(image.alt);
			var tempId = image.info;
			console.log('tempId:' + tempId)
			// 调用ajax  
			var data = {
				'token': token,
				'file': imgBlob,
				'depict': image.alt,
			}
			console.log(data);
			console.log(imgUrl);
			var formData = new FormData();
			// formData.append("accountnum", 123456); 
			formData.append('depict', data.depict);
			formData.append('token', data.token);
			formData.append('file', new File([data.file], '12345.png'));
			console.log(formData);
			var temp = null;
			var tempInput = document.getElementById(tempId);
			var waitstr = "wait" + num;
			console.log(waitstr);
			var waitInput = document.getElementById(waitstr);

			mui.ajax(imgUrl, {
				data: formData,
				cache: false,
				processData: false,
				contentType: false,
				async: false,
				type: 'post',
				// contentType: "multipart/form-data",
				timeout: 5000,
				success: function(data) {
					console.log(data);
					wd.close(); // 调用成功，先关闭等待的对话框  
					if (data.status == "401" || data.status == "400") {
						// 如果密码错误，提示一下信息  
						mui.alert(data.msg);
						localStorage.removeItem("TOKEN_TOKEN");
						tempInput.value = -i;
						waitInput.value = -i;
						mui.openWindow({
							url: '../login.html'
						})
					} else if (data.status != "200") {
						// 如果密码错误，提示一下信息  
						mui.alert('err#' + data.status + ':' + data.msg);
						// console.log(tempId)
						tempInput.value = -i;
						waitInput.value = -i;
						return false;
					} else {
						// mui.alert('图片上传成功！');
						// console.log(data.imageid);
						console.log(tempId)
						tempInput.value = data.imageid;
						tempInput.checked = "checked";
						if (waitInput.value < 0) {
							mui.alert('图片上传失败，中断！', '信息助手', '确定', function(e) {
								e.index
							}, 'div');
							closeDB()
							return false;
						}
						waitInput.value = parseInt(waitInput.value) + 1;
						console.log(waitInput.value);
						var stop = document.getElementById("endq");
						console.log(stop.value);
						var temp = data.imageid;
						//------------------
						if (waitInput.value == image.count) {
							photoId = getImageId();
							console.log(photoId);
							data = zhenliJson(image.mother, photoId);
							console.log(data);

							upData(data, num);
						}

					}
				},
				error: function(xhr, type, errorThrown) {
					// waitingDialog.close();
					console.log(JSON.stringify(errorThrown));
					mui.alert("<网络连接失败，请重新尝试一下>", "错误", "OK", null);
				},
			});
			// console.log(temp);
		}, function(err) {
			console.log(err);
		});
}

function w_upPhoto2(result) {
	function upPhoto(result) {
		for (i = 1; i <= 4; i++) {
			var photoSrc = 'photoSrc' + i;
			var photoDes = 'photoDes' + i;
			var tempId = "imgid" + i;
			console.log('wai' + typeof(result[photoSrc]) + '_' + photoSrc)
			if (!result[photoSrc] == '') {
				console.log('nei' + typeof(result[photoSrc]) + '_' + photoSrc)
				var image = new Image();
				image.style.width = "60px";
				image.style.height = "60px";
				image.src = result[photoSrc];
				// var files = document.getElementById('image');
				// 上传文件
				// --------------
				console.log(image);
				// uploadPictuer(image,imgUrl);
				// --------------
				// console.log(image);
				console.log(image.src);
				// var AUTH_TOKEN = myStorage.getItem('AUTH_TOKEN');
				var token = localStorage.getItem('TOKEN_TOKEN');
				image.onload = function() {
					var imgData = getBase64Image(image);

				}
			}
		}

		var deferred = $.Deferred();
		if (result) {
			deferred.resolve(upPhoto(result)); //将base64传给done上传处理
			// console.log(deferred.promise());
			return deferred.promise(); //问题要让onload完成后再return sessionStorage['imgTest']
		}
	}
}


//
function getImageId() {
	var obj = document.getElementsByName("imgid[]");
	console.log(obj);
	var photoId = [];
	var testPhoto = [];
	for (var j = 0; j < obj.length; j++) {
		testPhoto.push(parseInt(obj[j].value));
		if (obj[j].checked) {
			photoId.push(parseInt(obj[j].value));
			obj[j].checked = "";
		}
		obj[j].value = "0";
	}
	return photoId;
}

function getImageId0() {
	var obj = document.getElementsByName("imgid[]");
	// console.log(obj);
	var photoId = [];
	var testPhoto = [];
	for (var j = 0; j < obj.length; j++) {
		testPhoto.push(parseInt(obj[j].value));
		if (obj[j].checked) {
			photoId.push(parseInt(obj[j].value));
			// obj[j].checked = "";
		}
		// obj[j].value = "0";
	}
	console.log(photoId);
	return photoId;
}
