var title = "暂无内容";
var listType = GetUrlParam("type");
var listPage = GetUrlParam("page");

var xianzhi = 15;
var listList = ["全部分类", "未下文件", "已下文件"];

window.onload = function() {
	verification();
	showAdmin();
	document.cookie = setCookie("biaoji", 0, "3");
	designMain();
}

function designMain() {
	var token = getCookie('token');
	var data = {
		type: listType,
		page: listPage,
		token: token,
	}
	$.ajax({
		url: listUrl,
		datatype: "json",
		contentType: 'application/json;charset=UTF-8',
		data: JSON.stringify(data),
		type: 'post',
		success: function(data) {
			console.log(data);
			if (data.status == 200) {
				document.cookie = setCookie("biaoji", 0, "3");
				// data = data.data;
				pages = Math.ceil(data.count / xianzhi); //总页数
				var searchSort = document.getElementById("searchSort");
				for (var i = 0; i < searchSort.length; i++) {
					var valueStr = searchSort[i].value;
					valueStr = parseInt(valueStr.substring(17, 18));
					if (valueStr == listType) {
						searchSort[i].selected = true;
					}
				}
				lists = data.list;
				console.log(lists);
				// var newsUrl = '/article.html?id=';
				// var updateUrl = 'update.html?id=';
				// var delUrl = '/del?id=';
				for (i = 0; i < lists.length; i++) {
					var list = lists[i];
					if (list.title.length > 24) {
						var title = list.title.substr(0, 25) + "…";
					} else {
						var title = list.title;
					}
					if (list.author == undefined)
						author = "user";
					else {
						author = list.author;
					}
					titles = checkTitle(list.title);
					titles = checkTitle(titles);
					// console.log(titles);
					infoStr = "<tr>" +
						"<td class='tc'><input name='word[]' value='" + list.id + "' type='checkbox'></td>" +
						"<td>" + titles + "</td>" +
						"<td>" + list.date + "</td>" +
						"<td>" +
						"<a class='link-update' href='javascript:void(0)'  onclick='downFiles([" + list.id + "])'>下载</a> " +
						"<a class='link-update' href='javascript:void(0)'  onclick='delFile([" + list.id + "],\"" + titles +
						"\")'>删除</a> " +
						"</td>" +
						"</tr>";
					$("#result_info").append(infoStr);
				}
				// var pagesStr = "" + data.count + " 条 " + listPage + "/" + pages + " 页"
				if (pages > 1) {
					pagesStr = showPage(listPage, listType, pages, "design.html");
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
					designMain();
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

function download(type, downList) {
	var token = getCookie('token');
	var jsons = {
		"token": token,
		"type": type,
		"ids": downList,
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

//删除
function delFile(id, title) {
	console.log(id, title);
	var msg = "您真的确定要删除《" + title + "》吗？";
	if (!confirm(msg)) {
		window.event.returnValue = false;
	} else {
		console.log("已经执行删除");
		deleteF(-1, id)
	}
}

//批量删除
function delFiles() {
	var type = -1;
	var delList = getChkValue('word[]');
	console.log(delList);
	if (delList.length < 1) {
		alert("没有选择要删除的文件！");
	} else {
		var msg = "您确定要删除这" + delList.length + "个文件吗？";
		if (!confirm(msg)) {
			window.event.returnValue = false;
		} else {
			console.log("已经执行批量删除");
			deleteF(type, delList);
		}
	}

}


function deleteF(type, delList) {
	var token = getCookie('token');
	var jsons = {
		"token": token,
		"type": type,
		"ids": delList,
	};
	$.ajax({
		url: deleteFile,
		type: "post",
		cache: false,
		datatype: "json",
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(jsons),
		success: function(data) {
			console.log(data);
			if (data.status == 200) {
				alert("成功删除");
				location.replace(location.href); //成功后刷新页面
			} else {
				alert("删除失败！");
			}
		},
		error: function(data) {
			alert("删除失败！");
		}
	})
}
