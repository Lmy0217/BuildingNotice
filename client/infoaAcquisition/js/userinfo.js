var userList = ["未开通用户，请联系管理员开通", "普通用户", "管理员"];
userList[10] = "超级管理员";

function getFileInfo() {

	// getFileInfoAjax(0, 'over');
	// getFileInfoAjax(1, 'undown');
	// getFileInfoAjax(2, 'downed');
getFileInfoAjax();
}

function getFileInfoAjax() {
	var token = localStorage.getItem("TOKEN_TOKEN");
	var data = {
		token: token,
	}
	$.ajax({
		url: meUrl,
		datatype: "json",
		contentType: 'application/json;charset=UTF-8',
		data: JSON.stringify(data),
		type: 'post',
		success: function(data) {
			console.log(data);
			if (data.status == 200) {
				showInfo("role", userList[data.role]);
				showInfo("over", data.archcount);
				showInfo("undown", data.archnodown);
				showInfo("downed", data.archdown);
				showInfo("deleted", data.archdelete);
			} else {
				showError();
			}
		},
		error: function(data) {
			showError();
		}
	})
}

function showInfo(key, value) {
	if (value !== undefined || value !== null) {
		document.getElementById(key).innerText = value;
	} else {
		document.getElementById(key).innerText = 'NaN';
	}
}

function showError() {
	document.getElementById("role").innerText = '不明';
	document.getElementById("over").innerText = 'NaN';
	document.getElementById("undown").innerText = 'NaN';
	document.getElementById("downed").innerText = 'NaN';
	document.getElementById("deleted").innerText = 'NaN';
}
