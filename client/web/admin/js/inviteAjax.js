var title = "暂无内容";
var listType = GetUrlParam("type");
var listPage = GetUrlParam("page");

var xianzhi = 15;
var listList = ["未使用邀请码", "已使用邀请码"];


window.onload = function() {
	verification();
	document.cookie = setCookie("biaoji", 0, "3");
	showAdmin();
	inviteMain();
}

function inviteMain() {
	var token = getCookie('token');
	var data = {
		type: listType,
		page: listPage,
		token: token,
	}
	$.ajax({
		url: invitelistUrl,
		datatype: "json",
		contentType: 'application/json;charset=UTF-8',
		data: JSON.stringify(data),
		type: 'post',
		success: function(data) {
			if (data.status == 200) {
				document.cookie = setCookie("biaoji", 0, "3");
				console.log(data);
				// data = data.data;
				pages = Math.ceil(data.count / xianzhi); //总页数
				var searchSort = document.getElementById("searchSort");
				for (var i = 0; i < searchSort.length; i++) {
					var valueStr = searchSort[i].value;
					valueStr = parseInt(valueStr.substring(17, 18));
					if (isNaN(valueStr)) {
						valueStr = '-';
					}
					if (valueStr == listType) {
						searchSort[i].selected = true;
					}
				}
				lists = data.list;
				console.log(lists);
				// var newsUrl = '/article.html?id=';
				// var updateUrl = 'update.html?id=';
				// var delUrl = '/del?id=';
				for (var i = 0; i < lists.length; i++) {
					var list = lists[i];
					console.log(list);
					// if (list.title.length > 24) {
					// 	var title = list.title.substr(0, 25) + "…";
					// } else {
					// 	var title = list.title;
					// }					
					// titles = checkTitle(list.title);
					// titles = checkTitle(titles);
					// console.log(titles);
					if (list.status == 1) {
						var sta = "已使用";
						var name = list.invite
					} else (list.status == 0){
						var name = "无"
						var sta = "未使用";
					}
					infoStr = "<tr>" +
						"<td class='tc'><input name='user[]' value='" + list.code + "' type='checkbox'></td>" +
						"<td>" + list.code + "</td>" +
						"<td>" + sta + "</td>" +
						"<td>" + name + "</td>" +
						"</tr>";

					$("#result_info").append(infoStr);
				}
				if (pages > 1) {
					pagesStr = showPage(listPage, listTypec, pages, "userlist.html");
					$("#list_page").append(pagesStr);
				}
				//list_page
			} else {
				var biaoji = getCookie("biaoji");
				sleep(1000);
				if (biaoji > 5) {
					console.log("访问出错！");
					window.location.href = 'login.html'
				} else {
					document.cookie = setCookie("biaoji", biaoji + 1, "3");
					inviteMain();
				}
			}
		}
	})
}


function checkTitle(title) {
	var title = title.replace(/"/g, " ");
	return title;
}

//申请邀请码
function newinvite() {
	num = document.getElementById("invitenum").value;
	if (num == 0) {
		alert("请输入大于0的数");
	} else {
		var msg = "您确定要申请" + num + "个邀请码吗？";
		if (!confirm(msg)) {
			window.event.returnValue = false;
		} else {
			var token = getCookie('token');
			var jsons = {
				"token": token,
				"count": num,
			};
			// jsons["token"] = q,
			console.log(jsons);
			$.ajax({
				url: createInvite,
				type: "post",
				cache: false,
				datatype: "json",
				contentType: 'application/json; charset=UTF-8',
				data: JSON.stringify(jsons),
				success: function(data) {
					console.log(data);
					if (data.status == 200) {
						alert("成功申请了"+ num + "个邀请码。");
						// location.replace(location.href); //成功后刷新页面
					} else {
						alert("申请邀请码失败！<br/>err#" + data.msg+" 多次失败请联系管理员。");
					}
				},
				error: function(data) {
					alert("申请邀请码失败！请检查网络。");
				}
			})
		}
	}
}
