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
//提交表单到本地			
function submitFrom() {
	var tmp = plus.webview.currentWebview();
	var form_per = chuliForm(tmp);
	var form_now = getForm('#form4');
	watchJSON(form_per);
	var extras = $.extend({}, form_per, form_now);
	w_insertSQL(extras);
}

// 执行SQL语句，这里要弄三个表
function w_insertSQL(jsonInfo) {
	var douhao = "','"
	console.log(douhao);
	var tableName = 'imageDB';
	var creatImage = "create table if not exists imageDB(" + // 'id' integer PRIMARY KEY,
		"'filePath'	TEXT(255)," +
		"'depict'	TEXT(255)" + ")";
	// console.log(creatImage);
	var keySrc = "photoSrc1";
	var keyDes = "photoDes1";
	if (jsonInfo.hasOwnProperty(keySrc)) {
		// console.log(imageId);
		var imageSqlStr = "insert into " + tableName + " values('" + jsonInfo.photoSrc1 + "','" + jsonInfo.photoDes1 + "')";
		// imageId=insertSQL(tableName,creatImage,imageSqlStr);
		// console.log(imageId);
	}
	openDB();
	var isOk = false;
	console.log(creatImage);
	console.log(imageSqlStr)
	plus.sqlite.executeSql({
		name: 'info',
		// "create table if not exists infoDB('danwei' CHAR(110),'floor' INT(2),'result' FLOAT(11))",
		sql: creatImage,
		success: function(e) {
			// isOpenDB();
			console.log('表存在');
			plus.sqlite.executeSql({
				name: 'info',
				sql: imageSqlStr,
				success: function(e) {
					console.log('executeSql success: ' + JSON.stringify(e));
					plus.sqlite.selectSql({
						name: 'info',
						sql: 'select rowid from imageDB',
						success: function(e) {
							console.log('selectSql success: ' + JSON.stringify(e));
							var tempid=e.rowid;
							// console.log(e.length)
							var imageId=e[e.length-1].rowid;
							isOk = true;
							closeDB();
						},
						fail: function(e) {
							console.log('selectSql fail: ' + JSON.stringify(e));
						}
					});
				},
				fail: function(e) {
					console.log('executeSql fail: ' + JSON.stringify(e));
					isOk = false;
				}
			})
		},
		fail: function(e) {
			isOk = false;
			console.log('executeSql fail: ' + JSON.stringify(e));
		}
	});
	// closeDB();	
	if (isOk) {
		console.log(imageId);
	} else {
		console.log('失败了' + isOk)
	}




	// insertSQL(tableName,creatImage)
	var tableName = 'infoDB';
	creatMain = "create table if not exists main('id' integer PRIMARY KEY AUTOINCREMENT," +
		"'uint'	TEXT(255)," +
		"'phone'	integer(11)," +
		"'material'	TEXT(255) ," +
		"'addr'	TEXT(255)," +
		"'hold'	TEXT(255)," +
		"'holdid' INTEGER(20)," +
		"'attr' integer(2)," +
		"'layer' integer(2)," +
		"'type'	TEXT," +
		"'identitytime'	text(10)," +
		"'imageId'	TEXT(255)," +
		"'damage'	TEXT(255)," +

		"'isUp'	integer(1)"
	// sqlStr="insert into "+tableName+" values("+jsonInfo.uint+douhao+jsonInfo.phone+douhao+jsonInfo.material+douhao+
	// jsonInfo.addr+douhao+hold+douhao+jsonInfo.holdid+douhao
}

// 执行SQL语句
function insertSQL(tableName, creatTable, sqlStr) {
	openDB();
	// isOpenDB();
	// console.log('执行SQL语句: ');

	// sqlStr = sqlStr + " select last_insert_rowid() from " + tableName;
	console.log(creatTable);
	console.log(sqlStr);
	plus.sqlite.executeSql({
		name: 'info',
		// "create table if not exists infoDB('danwei' CHAR(110),'floor' INT(2),'result' FLOAT(11))",
		sql: creatTable,
		success: function(e) {
			plus.sqlite.executeSql({
				name: 'info',
				sql: sqlStr,
				success: function(e) {
					console.log('insertSQL success: ' + JSON.stringify(e))
				},
				fail: function(e) {
					console.log('executeSql fail: ' + JSON.stringify(e))
				}
			})
		},
		fail: function(e) {
			console.log('executeSql fail: ' + JSON.stringify(e))
			return e;
		}
	});
	closeDB();
}


//打开"下一单"
function openNext(formNow, formNext) {
	var tmp = plus.webview.currentWebview();
	var form_per = chuliForm(tmp);
	var form_now = getForm('#' + formNow);
	watchJSON(form_now);
	var extras = $.extend({}, form_per, form_now);

	var url = formNext + ".html";
	var id = formNext;
	var style = {};
	plus.webview.create(url, id, style, extras).show();
}

// 输入获取父元素的字段与子元素的字段，改变子元素的可选状态
function changeChild(father, child) {
	var father_str = 'input[type=checkbox][name=' + father + ']';
	var child_str = 'input[type=radio][name=' + child + ']';

	$(father_str).change(function() {
		if (this.checked) {
			// alert("启用"); 
			for (i = 0; i < $(child_str).length; i++) {
				try {
					$(child_str)[i].disabled = "";
				} catch (err) {
					console.log(child_str);
					console.log(i);
					$(child_str)[i].disabled = "";
				}

			}
		} else {
			// alert("未启用"); 
			$(child_str).disabled = "disabled";
			for (i = 0; i < $(child_str).length; i++) {
				$(child_str)[i].disabled = "disabled";
				$(child_str)[i].checked = "";
			}
		}
	});
}
// 输入获取父元素的字段与子元素的字段，改变子元素的可选状态
function changeChild2(father, child) {
	var father_str = 'input[type=checkbox][name=' + father + ']';
	var child_str = 'input[type=checkbox][name=' + child + ']';
	// console.log(child);
	$(father_str).change(function() {
		if (this.checked) {
			// alert("启用"); 
			for (i = 0; i < $(child_str).length; i++) {
				$(child_str)[i].disabled = "";
			}
		} else {
			// alert("未启用"); 
			$(child_str).disabled = "disabled";
			for (i = 0; i < $(child_str).length; i++) {
				$(child_str)[i].disabled = "disabled";
				$(child_str)[i].checked = "";
			}
		}
	});
}
