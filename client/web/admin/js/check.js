function verification() {
	var q = getCookie('token');
	console.log(document.cookie);
	// console.log(q);
	var u = getCookie('user');
	// console.log(u);
	// window.onload = function() {
	if (q == undefined) {
		window.location.href = 'login.html'
	} else {
		var c_start = document.cookie.indexOf("token");
		//	console.log(c_start);
		if (c_start == -1) {
			window.location.href = 'login.html'
		} else {
			var data = {};
			var data = transformToJson(data);
			data["token"] = q;
			//		console.log(data);
			$.ajax({
				url: checkUser,
				cache: false,
				type: "post",
				datatype: "json",
				contentType: 'application/json;charset=UTF-8',
				data: JSON.stringify(data),
				success: function(data) {
					// console.log(data);
					if (data.status == "200") {
						//根据后台返回值确定是否操作成功
						console.log("验证成功");
						if (u != undefined) {
							$("#user").append(u);
							$("#user").val(u);
						} else {
							$("#user").append("管理员");
						}
						document.cookie = setCookie("token", data.token, "3");
						// showAdmin() ;
						// document.cookie = setCookie("perm", data.role, "3");
					} else {
						console.log("非法访问");
						window.location.href = 'login.html'
					}
				}
			});
		}

	}
}

// }

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

function showAdmin() {
	perm = getCookie('perm');
	// console.log(perm);
	if (perm > 1) {
		console.log('管理员登录了,等级：'+perm);
		var adminShow=document.getElementsByClassName("admin");
		for(var i=0;i<adminShow.length;i++){
			adminShow[i].style.display = "block";
		}
	} else {
		console.log('普通用户登录了,等级：'+perm);
	}
	email = getCookie('email');
	if(email==0){
		console.log('没有验证邮箱');
		var emailShow=document.getElementsByClassName("email");
		for(var i=0;i<emailShow.length;i++){
			emailShow[i].style.display = "block";
		}
	}
}

function w_checkRole(mubiao) {
	if (!checkRole(mubiao)) {
		window.location.href = 'index.html'
	}
}

function checkRole(mubiao) {
	perm = getCookie('perm');
	if (perm >= mubiao) {
		console.log('条件满足');
		return true;
	} else {
		console.log('非法登录');
		return false;
	}
}
