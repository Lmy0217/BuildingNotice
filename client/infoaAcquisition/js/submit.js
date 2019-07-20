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

function w_upPhoto(flag_arr,num) {
	console.log(num)
	var flag = flag_arr.length;
	for (i = 0; i <= flag; i++) {
		var c=0;
		if (i == flag) {
			do {
				sleep(500);
				c++;
			}
			while (checkIdChange(i)&&c<10);
			photoId = getImageId0();
			console.log(photoId);
		} else {
			image = flag_arr[i];
			console.log(image);
			upPhotoMain(image,num);
			do {
				setTimeout("console.log('5 seconds!')", 500);
				c++;
			}
			while (checkIdChange(i)&&c<10);
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

function upPhotoMain(image,num) {

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
			var waitstr="wait"+num;
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
						waitInput.value=-i;
						mui.openWindow({
							url: '../login.html'
						})
					} else if (data.status != "200") {
						// 如果密码错误，提示一下信息  
						mui.alert('err#' + data.status + ':' + data.msg);
						// console.log(tempId)
						tempInput.value = -i;
						waitInput.value=-i;
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
						var stop=document.getElementById("endq");
						console.log(stop.value);
						var temp = data.imageid;
						//------------------
						if (waitInput.value == image.count) {
							photoId = getImageId();
							console.log(photoId);
							data = zhenliJson(image.mother, photoId);
							console.log(data);
							
							upData(data,num);
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
