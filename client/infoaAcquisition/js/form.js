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
	var tableName='infoDB';
	var douhao='","'
	creatType1="create table if not exists type('id' integer PRIMARY KEY AUTOINCREMENT," +
	""
	creatMain="create table if not exists main('id' integer PRIMARY KEY AUTOINCREMENT," +
		"'uint'	TEXT(255)," +
		"'phone'	integer(11)," +
		"'material'	TEXT(255) ," +
		"'addr'	TEXT(255)," +
		"'hold'	TEXT(255)," +
		"'holdid' INTEGER(20)," +
		"'attr' TEXT," +
		"'layer' integer(2)," +
		"'type'	integer(1) NOT NULL," +
		"'typeId'	INTEGER," +
		"'identitytime'	text(10)," +
		"'rankresult'	TEXT," +
		"'imageId'	integer(2)," +
		"'zhongzhu'	integer(2)," +
		"'zhongzhu_w'	integer(2)," +
		"'bianzhu'	integer(2)," +
		"'bianzhu_w'	integer(2)," +
		"'jiaozhu'	integer(2)," +
		"'jiaozhu_w'	integer(2)," +
		"'wujia'	integer(2)," +
		"'wujia_w'	integer(2)," +
		"'zhongjianliang'	integer(2)," +
		"'zhongjianliang_w'	integer(2)," +
		"'bianliang'	integer(2)," +
		"'bianliang_w'	integer(2)," +
		"'qiangti'	integer(2)," +
		"'qiangti_w'	integer(2)," +
		"'loubangoujian'	integer(2)," +
		"'loubangoujian_w'	integer(2)," +
		"'weihugoujian'	integer(2)," +
		"'weihugoujian_w'	integer(2)," +
		"'result'	integer," +
		"'isUp'	integer(1)," 
	sqlStr="insert into "+tableName+" values("+jsonInfo.uint+douhao+jsonInfo.phone+douhao+jsonInfo.material+douhao+
	jsonInfo.addr+douhao+hold+douhao+jsonInfo.holdid+douhao
}

// 执行SQL语句
function insertSQL(tableName,creatTable,sqlStr) {
	openDB();
	// console.log('执行SQL语句: ');

	sqlStr = "insert into infoDB values('" + jsonInfo.danwei + "','" + jsonInfo.floor + "','" + jsonInfo.result + "')"
	console.log(sqlStr)
	plus.sqlite.executeSql({
			name: tableName,
			// "create table if not exists infoDB('danwei' CHAR(110),'floor' INT(2),'result' FLOAT(11))",
			sql: creatTable,
		); success: function(e) {
			console.log('executeSql success: ' + JSON.stringify(e))
			plus.sqlite.executeSql({
				name: tableName,
				sql: sqlStr,
				success: function(e) {
					console.log('executeSql success: ' + JSON.stringify(e))
				},
				fail: function(e) {
					console.log('executeSql fail: ' + JSON.stringify(e))
				}
			})
		},
		fail: function(e) {
			console.log('executeSql fail: ' + JSON.stringify(e))
		}
	});
closeDB()
}


//打开"下一单"
function openNext(formNow, formNext) {
	var tmp = plus.webview.currentWebview();
	var form_per = chuliForm(tmp);
	var form_now = getForm('#' + formNow);
	var extras = $.extend({}, form_per, form_now);
	var url = formNext + ".html";
	var id = formNext;
	var style = {};
	plus.webview.create(url, id, style, extras).show();
}

// 输入获取父元素的字段与子元素的字段，改变子元素的可选状态
function changeChild(father, child) {
	console.log(child);
	var father_str = 'input[type=checkbox][name=' + father + ']';
	var child_str = 'input[type=radio][name=' + child + ']';
	console.log(child);
	$(father_str).change(function() {
		if (this.checked) {
			// alert("启用"); 
			for (i = 0; $(child_str).length; i++) {
				$(child_str)[i].disabled = "";
			}
		} else {
			// alert("未启用"); 
			$(child_str).disabled = "disabled";
			for (i = 0; $(child_str).length; i++) {
				$(child_str)[i].disabled = "disabled";
				$(child_str)[i].checked = "";
			}
		}
	});
}
// 输入获取父元素的字段与子元素的字段，改变子元素的可选状态
function changeChild2(father, child) {
	console.log(child);
	var father_str = 'input[type=checkbox][name=' + father + ']';
	var child_str = 'input[type=checkbox][name=' + child + ']';
	console.log(child);
	$(father_str).change(function() {
		if (this.checked) {
			// alert("启用"); 
			for (i = 0; $(child_str).length; i++) {
				$(child_str)[i].disabled = "";
			}
		} else {
			// alert("未启用"); 
			$(child_str).disabled = "disabled";
			for (i = 0; $(child_str).length; i++) {
				$(child_str)[i].disabled = "disabled";
				$(child_str)[i].checked = "";
			}
		}
	});
}
