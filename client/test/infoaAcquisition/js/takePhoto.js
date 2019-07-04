
//打开手机摄像头
function openCamera() {
	var cmr = plus.camera.getCamera();
	cmr.captureImage(function(p) {
		plus.io.resolveLocalFileSystemURL(p, function(entry) {
			plus.nativeUI.showWaiting("拍照中...", ""); //显示系统loading框
			plus.zip.compressImage({
				src: entry.toLocalURL(),
				dst: '_doc/camera/' + p,
				overwrite: true,
				format: "jpg",
				width: "30%"
			}, function(zip) {
				if (zip.size > (1 * 1024 * 1024)) {
					return mui.toast('文件超大,请调整相机重新拍照');
				}
				file_url = zip.target;
				//转为base64
				getBase64(file_url);
				uploadToServer(file_url);
			}, function(zipe) {
				plus.nativeUI.closeWaiting();
				mui.toast('压缩失败！')
			});
		}, function(e) {
			plus.nativeUI.closeWaiting(); //获取图片失败,loading框取消
			mui.toast('失败：' + e.message); //打印失败原因,或给出错误提示
		});
	}, function(e) {
		plus.nativeUI.closeWaiting(); //开启照相机失败,关闭loading框
		mui.toast('失败：' + e.message); //打印错误原因,给出错提示
	}, {
		filename: '_doc/camera/', //图片名字
		index: 1 //摄像头id
	});
}

