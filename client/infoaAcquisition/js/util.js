function watchJSON(json) {
	console.log(JSON.stringify(json));
}

// 关闭数据库
function closeDB() {
	console.log('关闭数据库: ');
	plus.sqlite.closeDatabase({
		name: 'info',
		success: function(e) {
			console.log('closeDatabase success: ' + JSON.stringify(e));
		},
		fail: function(e) {
			console.log('closeDatabase fail: ' + JSON.stringify(e));
		}
	});
}

// 打开数据库
function openDB() {
	console.log('打开数据库: ');
	plus.sqlite.openDatabase({
		name: 'info',
		path: '_doc/info.db',
		success: function(e) {
			console.log('openDatabase success: ' + JSON.stringify(e));
		},
		fail: function(e) {
			console.log('openDatabase success: ' + JSON.stringify(e));
		}
	});
}

// 查询SQL语句
function selectSQL(sqlStr) {
	openDB();
	console.log('查询SQL语句: ' + sqlStr);
	plus.sqlite.selectSql({
		name: 'info',
		sql: sqlStr,
		success: function(e) {
			console.log('selectSql success: ' + JSON.stringify(e));
		},
		fail: function(e) {
			console.log('selectSql fail: ' + JSON.stringify(e));
		}
	});
	closeDB();
}

// 检查数据库是否打开
function isOpenDB() {
	if (plus.sqlite.isOpenDatabase({
			name: 'info',
			path: '_doc/info.db',
		})) {
		// plus.nativeUI.alert('Opened!');
		return true;
	} else {
		// plus.nativeUI.alert('Unopened!');
		return false;
	}
}

/**
 * 从当前页面pop到目标页面
 * @param {String} targetId 目标页面ID
 */
function popToTarget(targetId) {
	//获取目标页面
	var target = plus.webview.getWebviewById(targetId);
	if (!target) {
		console.log("目标页面不存在！");
		return;
	}
	//获取当前页面
	var current = plus.webview.currentWebview();
	if (current === target) {
		console.log("目标页面是当前页面！");
		return;
	}
	//将要关闭的页面
	var pages = new Array(current);
	//父级页面
	var opener = current.opener();
	while (opener) {
		if (opener === target) { //找到了目标页面
			//关闭目标页面的所有子级页面pages
			pages.map(function(page) {
				page.close();
			});
			return;
		}
		pages.push(opener);
		opener = opener.opener();
	}
	//没有找到目标页面
	console.log("目标页面不是当前页面的祖先页面！");
}


function goHome() {
	//到首页去，关闭其他所有页面
	console.log('到首页去，关闭其他所有页面');
	var allPage = plus.webview.all();
	watchJSON(allPage);
	homeId = plus.webview.getLaunchWebview().id;
	console.log(homeId);
	for (var i = 0, l = allPage.length; i < l; i++) {
		if (allPage[i].id != homeId || typeof(allPage[i]) == 'undefined') {
			allPage[i].close('none');
		}
		// for (var key in allPage[i]) {
		key = 'id';
		console.log(key + ':' + allPage[i][key]);

		// }
	}
}
var oldBack = mui.back;
//重写返回上一页，方式页面丢失的问题
mui.back = function(pageId) {
	console.log(pageId);
	if(typeof(pageId)=="undefined"){
		goHome();
	}else{
	var webview = plus.webview.getWebviewById(pageId); //假设第一个Webview的id是home
	webview.show();}
};
