var funType = GetUrlParam("fc");

window.onload = function() {
	// verification();
	setTimes();
	main();
}

function main() {
	if (funType == 1) {
		result = mailOk();
	} else if (funType == 2) {
		result = mailCheck();
	} else if (funType == 3) {
		result = mailPwdOk();
		name = result[3];
	} else if (funType == 4) {
		result = mailPwdCheck();
		name = result[3];
	} else if (funType == 5) {
		result = mailPwdCheck();
		name = result[3];
	}
}

function mailCheck() {
	var code = GetUrlParam("verify");
	var data = {
		code: code,
	};
	$.ajax({
		url: verifyEmailUrl,
		// cache: false,
		type: "post",
		datatype: "json",
		// contentType: "application/x-www-form-urlencoded;charset=utf-8",
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(data),
		success: function(data) {
			console.log(data);
			if (data.status == "200") {
				// window.location.href = "index.html";
				var titleC = "恭喜，邮箱绑定成功！";
				var headerC = "邮箱绑定成功！";
				var contentC = "您提交的邮箱已经和您的账号绑定成功！<br>" +
					"您可以通过邮箱进行重置密码等操作，请牢记。" + "<br>" +
					"现在您可以" + "&nbsp;" + "&nbsp;<a href='/'>返回网站首页</a>" + "&nbsp;<a href='index.html'>返回后台首页</a>";
				var result = new Array(titleC, headerC, contentC);
				document.cookie = setCookie("email", 1, "3");
				showInfo(result);
			} else {
				var titleC = "错误，邮箱绑定失败！";
				var headerC = "很遗憾，邮箱绑定失败！";
				var contentC = "您提交的邮箱已经和您的账号绑定失败！<br>" +
					"请检查您的账号或密保邮箱，可能是账号信息填写错误，或者链接已过期。" + "<br>" +
					"错误代码#" + data.status + "，错误信息：" + data.msg + "<br>" +
					"您可以凭借此信息咨询管理员。" + "<br>" +
					"现在您可以" + "&nbsp;" + "&nbsp;<a href='/'>返回网站首页</a>" + "&nbsp;<a href='index.html'>返回后台首页</a>";
				var result = new Array(titleC, headerC, contentC);
				showInfo(result);
			}
		}
	});
}

function mailPwdCheck() {
	var code = GetUrlParam("verify");
	var data = {
		code: code,
	};
	$.ajax({
		url: verifyPwdUrl,
		// cache: false,
		type: "post",
		datatype: "json",
		// contentType: "application/x-www-form-urlencoded;charset=utf-8",
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(data),
		success: function(data) {
			console.log(data);
			if (data.status == "200") {
				console.log("验证成功");
				document.cookie = setCookie("verify", data.code, "1");
				window.location.href = "changepassword.html";
			} else {
				var titleC = "错误，验证失败！";
				var headerC = "密保邮箱验证失败！";
				var contentC = "请检查您的账号或密保邮箱！<br>" +
					"可能是账号信息填写错误，或者链接已过期。" + "<br>" + "<br>" +
					"错误代码#" + data.status + "，错误信息：" + data.msg + "<br>" +
					"您可以凭借此信息咨询管理员。" + "<br>" +
					"现在您可以" + "&nbsp;" + "&nbsp;<a href='/'>返回网站首页</a>" + "&nbsp;<a href='login.html'>登录</a>" +
					"&nbsp;<a href='repassword.html'>忘记用户名或密码</a>";
				var result = new Array(titleC, headerC, contentC);
				showInfo(result);
			}
		}
	});
}

function mailOk() {
	var email = GetUrlParam("email");
	var arr = email.split('@'); //两部分
	var mailName = arr[1].split('.');
	var mailUrl = "https://www.baidu.com/s?ie=utf-8&tn=baidu&wd=" + mailName[0] + "邮箱登录";
	var titleC = "验证邮件发送成功";
	var headerC = titleC;
	var contentC = "您提交的邮箱是：" + email + "<br>" +
		"请前往邮箱查看" + "<br>" +
		"如果没有收到，请稍等片刻或者查看“垃圾箱”或“广告邮件”查找" + "<br>" +
		"您可以" + "&nbsp;<a href='" + mailUrl + "' target='_blank'>登录邮箱</a>" + "&nbsp;&nbsp;<a href='index.html'>返回后台首页</a>";
	var result = new Array(titleC, headerC, contentC);
	showInfo(result);
}

function mailPwdOk() {
	var user = GetUrlParam("name");
	var email = GetUrlParam("email");
	if (user == 0) {
		user = "用户";
	}
	if (email == 0) {
		var mailUrl = "https://www.baidu.com/s?ie=utf-8&tn=baidu&wd=" + "登录邮箱";
	} else {
		var arr = email.split('@'); //两部分
		var mailName = arr[1].split('.');
		var mailUrl = "https://www.baidu.com/s?ie=utf-8&tn=baidu&wd=" + mailName[0] + "邮箱登录";
	}
	var titleC = "邮件发送成功";
	var headerC = titleC;
	var contentC = "您提交的邮箱是：" + email + "<br>" +
		"请前往密保邮箱查看" + "<br>" +
		"如果没有收到，请稍等片刻或者查看“垃圾箱”或“广告邮件”查找" + "<br>" +
		"您可以" + "&nbsp;<a href='" + mailUrl + "' target='_blank'>登录邮箱</a>" + "&nbsp;&nbsp;<a href='/'>返回网站首页</a>";
	var result = new Array(titleC, headerC, contentC, user);
	showInfo(result);
}


function showInfo(result) {
	var title = document.getElementById("title");
	if (result.length == 4) {
		var name = result[3];
	} else {
		var name = getCookie('user');
	}

	var header = document.getElementById("header");
	var content = document.getElementById("content");
	console.log(result);
	title.innerText = result[0];
	header.innerText = result[1];
	content.innerHTML = result[2];
	if (name === null || name === undefined) {
		name = "用户"
	}
	var user = document.getElementById("user");
	user.innerText = name;
}

function setTimes() {
	var date = NOW();
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
