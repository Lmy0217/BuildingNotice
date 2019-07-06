function watchJSON(json){
	console.log(JSON.stringify(json));
}

// 关闭数据库
function closeDB(){
	console.log('关闭数据库: ');
	plus.sqlite.closeDatabase({
		name: 'test',
		success: function(e){
			console.log('closeDatabase success: '+JSON.stringify(e));
		},
		fail: function(e){
			console.log('closeDatabase fail: '+JSON.stringify(e));
		}
	});
}

// 打开数据库
function openDB(){
	console.log('打开数据库: ');
	plus.sqlite.openDatabase({
		name: 'test',
		path: '_doc/test.db',
		success: function(e){
			console.log('openDatabase success: '+JSON.stringify(e));
		},
		fail: function(e){
			console.log('openDatabase success: '+JSON.stringify(e));
		}
	});
}

// 查询SQL语句
function selectSQL(sqlStr){
	console.log('查询SQL语句: '+sqlStr);
	plus.sqlite.selectSql({
		name: 'test',
		sql: sqlStr,
		success: function(e){
			console.log('selectSql success: '+JSON.stringify(e));
			return e;
		},
		fail: function(e){
			console.log('selectSql fail: '+JSON.stringify(e));
			return e;
		}
	});
}

// 检查数据库是否打开
function isOpenDB(){
	if(plus.sqlite.isOpenDatabase({
		name: 'test',
		path: '_doc/test.db',
	})){
		plus.nativeUI.alert('Opened!');
		return true;
	}else{
		plus.nativeUI.alert('Unopened!');
		return false;
	}
}