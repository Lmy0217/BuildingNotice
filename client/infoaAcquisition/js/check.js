var token = localStorage.getItem('TOKEN_TOKEN');
console.log(token);

function checkUser(){
	if (typeof(token) == 'undefined') {
		window.location.href = 'login.html'
		localStorage.removeItem("TOKEN_TOKEN");
		mui.openWindow({
			url: '../login.html'
		})
		return false;
	} else {
		data={};
		data["token"] = token;
		console.log(data);
		$.ajax({
			url: checkUrl,
			cache: false,
			type: "post",
			datatype: "json",
			contentType: 'application/json;charset=UTF-8',
			data: JSON.stringify(data),
			success: function(data) {
				console.log(data);
				if (data.status == "200") {
					//根据后台返回值确定是否操作成功
					console.log("更新令牌");
					localStorage.setItem('TOKEN_TOKEN', data.token);
					return true;
				} else {
					console.log("非法访问");
					mui.alert(data.msg,'未登录','确定',function (e) {
					   localStorage.removeItem("TOKEN_TOKEN");
					   mui.openWindow({
					   	url: '../login.html'
					   })
					},'div')
					return false;
				}
			}
		});
	}
	
}

