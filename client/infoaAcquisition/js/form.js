function getForm(name) {
	console.log(name);
	//      console.log($('#form1').serializeJSON());
	//      console.log(JSON.stringify($('#form1').serializeJSON()));
	var froma = $(name).serializeJSON();
	console.log(JSON.stringify(froma));
	return (froma);
}

function computeBili(a, a_w) {
	var bili = 0;
	if (a != 0) {
		var bili = a_w / a;
	}
	return bili;
}

function chuliForm(json) {
	delete json["__view_array__"];
	delete json["__IDENTITY__"];
	delete json["__uuid__"];
	delete json["__callbacks__"];
	delete json["__callback_id__"];
	delete json["id"];
	return json;
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

// 执行SQL语句
function insertSQL(jsonInfo){
	openDB();
	console.log('执行SQL语句: ');
	
	sqlStr="insert into database values('"+jsonInfo.danwei+"','"+jsonInfo.floor+"','"+jsonInfo.result+"')"
	plus.sqlite.executeSql({
		name: 'test',
		sql: 'create table if not exists database("danwei" CHAR(110),"floor" INT(2),"result" FLOAT(11))',
		success: function(e){
			console.log('executeSql success: '+JSON.stringify(e))
			plus.sqlite.executeSql({
				name: 'test',
				sql: sqlStr,
				success: function(e){
					console.log('executeSql success: '+JSON.stringify(e))
				},
				fail: function(e){
					console.log('executeSql fail: '+JSON.stringify(e))
				}
			})
		},
		fail: function(e){
			console.log('executeSql fail: '+JSON.stringify(e))
		}
	});
	closeDB()
}


