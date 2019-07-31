var title = "暂无内容";
// var listType = GetUrlParam("type");
// var listPage = GetUrlParam("page");
// 
var userList = ["未开通用户，请联系管理员开通", "普通用户", "管理员"];
userList[10] = "超级管理员";
window.onload = function() {
	verification();
	showAdmin();
	document.cookie = setCookie("biaoji", 0, "3");
	usermeMain();
}

function usermeMain() {
	var token = getCookie('token');
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
				document.cookie = setCookie("biaoji", 0, "3");
				var user = getCookie('user');
				document.getElementById("name").innerText = user;
				document.getElementById("role").innerText = userList[data.role];
				document.getElementById("archcount").innerText = data.archcount;
				document.getElementById("archdown").innerText = data.archdown;
				document.getElementById("archnodown").innerText = data.archnodown;
				document.getElementById("archdelete").innerText = data.archdelete;
				document.getElementById("archsee").innerText = data.archcount-data.archdelete;
				if(data.adminname!==null){
					document.getElementById("adminname").innerText =data.adminname;
				}else{
					document.getElementById("adminname").innerText ="暂无" ;
				}
				
			} else {
				var biaoji = getCookie("biaoji");
				sleep(1000);
				if (biaoji > 5) {
					console.log("访问出错！");
					window.location.href = 'login.html'
				} else {
					document.cookie = setCookie("biaoji", biaoji + 1, "3");
					usermeMain();
				}
			}
		}
	})
}
