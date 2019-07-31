var title = "暂无内容";
var listType = GetUrlParam("role");
var listPage = GetUrlParam("page");

var xianzhi = 15;
var listList = ["全部用户", "未开通用户", "普通用户"];


window.onload = function() {
	verification();
	document.cookie = setCookie("biaoji", 0, "3");
	showAdmin();
	userlistMain();
}

function userlistMain() {
	var token = getCookie('token');
	if (listType == '-') {
		listTypec = null
	} else {
		listTypec = listType;
	}
	var data = {
		role: listTypec,
		page: listPage,
		token: token,
	}
	$.ajax({
		url: userlistUrl,
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
					valueStr = parseInt(valueStr.substring(19, 20));
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
					if (list.name == undefined)
						name = "user";
					else {
						name = list.name;
					}
					// titles = checkTitle(list.title);
					// titles = checkTitle(titles);
					// console.log(titles);
					if (list.role - 1 <= 0) {
						infoStr = "<tr>" +
							"<td class='tc'><input name='user[]' value='" + list.id + "' type='checkbox'></td>" +
							"<td>" + name + "</td>" +
							"<td>" + list.role + "</td>" +
							"<td>" + list.archcount + "</td>" +
							"<td>" +
							"<a class='link-update' href='javascript:void(0)'  onclick='chrole([" + list.id + '],' + (list.role + 1) +
							")'>提权</a>&nbsp;&nbsp; " +
							"降权" +
							"</td>" +
							"</tr>";
					} else if (list.role + 1 >= 2) {
						infoStr = "<tr>" +
							"<td class='tc'><input name='user[]' value='" + list.id + "' type='checkbox'></td>" +
							"<td>" + name + "</td>" +
							"<td>" + list.role + "</td>" +
							"<td>" + list.archcount + "</td>" +
							"<td>" + "提权&nbsp;&nbsp; " +
							"<a class='link-update' href='javascript:void(0)'  onclick='chrole([" + list.id + '],' + (list.role - 1) +
							")'>降权" +
							"</td>" +
							"</tr>";
					} else {
						infoStr = "<tr>" +
							"<td class='tc'><input name='user[]' value='" + list.id + "' type='checkbox'></td>" +
							"<td>" + name + "</td>" +
							"<td>" + list.role + "</td>" +
							"<td>" + list.archcount + "</td>" +
							"<td>" + "<a class='link-update' href='javascript:void(0)'  onclick='chrole([" + list.id + '],' + (list.role +
								1) +
							")'>提权</a>&nbsp;&nbsp; " +
							"<a class='link-update' href='javascript:void(0)'  onclick='chrole([" + list.id + '],' + (list.role - 1) +
							")'>降权" +
							"</td>" +
							"</tr>";
					}


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
					userlistMain();
				}
			}
		}
	})
}


function checkTitle(title) {
	var title = title.replace(/"/g, " ");
	return title;
}

//下载全部未下载文件
function downFiles2(type) {
	// var type = 1;
	var downList = [];
	var r = confirm("下载全部文件耗时较多，确定要下载吗？");
	if (r == true) {
		download(type, downList);
	} else {}
}
//批量下载
function downFiles(c) {
	console.log(c);
	var type = -1;
	if (typeof(c) == 'undefined') {
		var downList = getChkValue('word[]');
		console.log(downList);
	} else {
		var downList = c;
	}
	console.log(downList)
	if (downList.length < 1) {
		alert("没有选择要下载的文件！");
	} else {
		download(type, downList);
	}
}

function chrole2(id, mubiao) {
	var token = getCookie('token');
	var jsons = {
		"token": token,
		"userid": id,
		"role": mubiao,
	};
	var form = $("<form>");
	form.attr('style', 'display:none');
	form.attr('target', '');
	form.attr('method', 'post'); //请求方式
	form.attr('action', downUrl); //请求地址

	var input1 = $('<input>'); //将你请求的数据模仿成一个input表单
	input1.attr('type', 'hidden');
	input1.attr('name', 'json'); //该输入框的name
	input1.attr('value', JSON.stringify(jsons)); //该输入框的值

	$('body').append(form);
	form.append(input1);

	form.submit();
	form.remove();
}

function w_chrole(mubiao) {
	var chgList = getChkValue('user[]');
	console.log(chgList);
	console.log(downList)
	if (downList.length < 1) {
		alert("没有选择要改变权限的用户！")
	} else {
		chrole(chgList, mubiao);
	}
}

//删除
function chrole(id, mubiao) {
	console.log(id);
	var msg = "您真的确定要修改这些用户的权限吗？";
	if (!confirm(msg)) {
		window.event.returnValue = false;
	} else {
		var token = getCookie('token');
		var jsons = {
			"token": token,
			"userid": id[0],
			"role": mubiao,
		};
		jsons = JSON.stringify(jsons);
		jsons = JSON.parse(jsons);
		// jsons["token"] = q,
		console.log(jsons);
		$.ajax({
			url: changeRole,
			type: "post",
			cache: false,
			datatype: "json",
			contentType: 'application/json; charset=UTF-8',
			data: JSON.stringify(jsons),
			success: function(data) {
				console.log(data);
				if (data.status == 200) {
					alert("成功改变权限");
					location.replace(location.href); //成功后刷新页面
				} else {
					alert("改变权限失败！<br/>err#" + data.msg);
				}
			},
			error: function(data) {
				alert("改变权限失败！");
			}
		})
		//					$.post("http://47.100.192.151:5555/news/delete", {
		//							name: "news_id",
		//							city: id
		//						},
		//						function(data, status) {
		//							alert("Data: " + data + "\nStatus: " + status);
		//						});
	}
}
