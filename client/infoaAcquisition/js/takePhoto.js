//弹出系统按钮选择框
var page = null;
page = {
	imgUp: function() {
		var m = this;
		// console.log(m);
		plus.nativeUI.actionSheet({
			cancel: "取消",
			buttons: [{
					title: "拍照"
				},
				{
					title: "从相册中选择"
				}
			]
		}, function(e) { //1 是拍照  2 从相册中选择 
			switch (e.index) {
				case 1:
					appendByCamera();
					break;
				case 2:
					appendByGallery();
					break;
			}
		});
	}
}

// 拍照添加文件
function appendByCamera() {
	plus.camera.getCamera().captureImage(function(e) {
		console.log("e is" + e);
		plus.io.resolveLocalFileSystemURL(e, function(entry) {
			var path = entry.toLocalURL();
			var indexa = liIndex()
			console.log(indexa);
			$(".headimg")[indexa].style.display = "block";
			$(".photoDes")[indexa].style.display = "block";//如果拍了照片,这里就显示出来
			$(".headimg")[indexa].src = path;
			$(".photoSrc")[indexa].value = path;
		}, function(e) {
			mui.toast("读取拍照文件错误：" + e.message);
		});
	});
}
// 从相册添加文件
function appendByGallery() {
	plus.gallery.pick(function(path) {
		var indexa = liIndex();
		console.log(indexa);
		$(".headimg")[indexa].style.display = "block";
		$(".photoDes")[indexa].style.display = "block";
		$(".photoSrc")[indexa].value = path;
		$(".headimg")[indexa].src = path;
	});
}

//判断点击的是上传的第几个li             
function liIndex() {
	var lis = $(".list").children();
	console.log(lis.length)
	for (var i = 0; i < lis.length; i++) {
		console.log($(lis[i]).attr("class"))
		var lisClass = $(lis[i]).attr("class").split(" ");
		if (lisClass[2] == "selectLi") {
			console.log(i);
			return i;
		}
	}
}


