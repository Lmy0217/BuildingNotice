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
	var len = 2;
	var douhao = "','"
	console.log(jsonInfo);
	//------------------------
	jsonInfo = zhenliPhoto(jsonInfo);
	damage = zhenliDamage(jsonInfo);
	// console.log(damage);
	typeb = zhenliType(jsonInfo, len);
	// alert('typeb!');
	console.log(typeb);
	typec = zhenliQuestion(jsonInfo, len);
	console.log(typec);
	// insertSQL(tableName,creatImage)
	var tableName = 'infoDB';
	creatMain = "create table if not exists " + tableName + "(" +
		"'unit'	text," +
		"'phone'	int(11)," +
		"'material'	text ," +
		"'addr'	text," +
		"'hold'	text," +
		"'holdid' int(20)," +
		"'attr' int(2)," +
		"'layer' int(2)," +
		"'typeid'	int(1)," +
		"'createyear'	text(10)," +
		"'identitytime'	text(10)," +
		"'photoSrc1'	text," +
		"'photoDes1'	text," +
		"'photoSrc2'	text," +
		"'photoDes2'	text," +
		"'photoSrc3'	text," +
		"'photoDes3'	text," +
		"'photoSrc4'	text," +
		"'photoDes4'	text," +
		"'damage'	text," +
		"'typea'	text," +
		"'typeb'	text," +
		"'typec'	text," +
		"'remark'	text," +

		"'isUp'	int(1)" + ")";
	sqlStr = "insert into " + tableName + " values('" + jsonInfo.unit + "'," +
		jsonInfo.phone + ",'" +
		jsonInfo.material + "','" +
		jsonInfo.addr + "','" +
		jsonInfo.hold + "'," +
		jsonInfo.holdid + "," +
		jsonInfo.attr + "," +
		jsonInfo.layer + "," +
		jsonInfo.type + ",'" +
		jsonInfo.createyear + "','" +
		jsonInfo.identitytime + "','" +
		jsonInfo.photoSrc1 + "','" +
		jsonInfo.photoDes1 + "','" +
		jsonInfo.photoSrc2 + "','" +
		jsonInfo.photoDes2 + "','" +
		jsonInfo.photoSrc3 + "','" +
		jsonInfo.photoDes3 + "','" +
		jsonInfo.photoSrc4 + "','" +
		jsonInfo.photoDes4 + "','" +
		damage.toString() + "','" +
		"" + "','" +
		typeb + "','" +
		typec + "','" +
		jsonInfo.remark + "'," +
		0 + ")";
	openDB();
	// insertSQL(tableName, creatMain, sqlStr);
	console.log(creatMain);
	console.log(sqlStr);
	// alert('zuzhi!');
	plus.sqlite.executeSql({
		name: 'info',
		// "create table if not exists infoDB('danwei' CHAR(110),'floor' INT(2),'result' FLOAT(11))",
		sql: creatMain,
		success: function(e) {
			console.log('creatTable success: ' + JSON.stringify(e))
			plus.sqlite.executeSql({
				name: 'info',
				sql: sqlStr,
				success: function(e) {
					console.log('insertSQL success: ' + JSON.stringify(e))
					closeDB();
					plus.nativeUI.alert('缓存成功，表单已完成！');
					mui.back();
				},
				fail: function(e) {
					console.log('executeSql fail: ' + JSON.stringify(e))
					plus.nativeUI.alert('插入失败，请重试！多次失败请联系管理员');
				}
			})
		},
		fail: function(e) {
			console.log('executeSql fail: ' + JSON.stringify(e))
			return e;
		}
	});
	// console.log(isOpenDB());
	// if(!isOpenDB()){
	// 	goHome();
	// }else{
	// 	
	// }

}

// 执行SQL语句，这里要弄三个表
function w_insertSQL_todo(jsonInfo) {
	var douhao = "','"
	console.log(douhao);
	var tableName = 'imageDB';
	var creatImage = "create table if not exists imageDB(" + // 'id' integer PRIMARY KEY,
		"'filePath'	TEXT," +
		"'depict'	TEXT(255)" + ")";
	// console.log(creatImage);
	openDB();
	//第一张图片------------------------
	var keySrc = "photoSrc1";
	var keyDes = "photoDes1";
	if (jsonInfo.hasOwnProperty(keySrc)) {
		// console.log(imageId);
		var imageSqlStr = "insert into " + tableName + " values('" + jsonInfo.photoSrc1 + "','" + jsonInfo.photoDes1 + "')";

		console.log(creatImage);
		console.log(imageSqlStr)
		var imageId = addPhoto(creatImage, imageSqlStr);
		console.log(imageId);
		// closeDB();	

	}
	//------------------------

	zhenliDamage(jsonInfo);
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

		"'isUp'	integer(1)" + "')";

	// sqlStr="insert into "+tableName+" values("+jsonInfo.uint+douhao+jsonInfo.phone+douhao+jsonInfo.material+douhao+
	// jsonInfo.addr+douhao+hold+douhao+jsonInfo.holdid+douhao
}

// 执行SQL语句
function insertSQL(tableName, creatTable, sqlStr) {
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
}


//打开"下一单"
function openNext(formNow, formNext) {
	var tmp = plus.webview.currentWebview();
	var form_per = chuliForm(tmp);
	var form_now = getForm('#' + formNow);
	console.log(form_now);

	var extras = $.extend({}, form_per, form_now);

	var url = formNext + ".html";
	var id = formNext;
	var style = {};
	plus.webview.create(url, id, style, extras).show();
}

// 输入获取父元素的字段与子元素的字段，改变子元素的可选状态
function changeChild2(father, child1, child2) {
	var father_str = "input[type=checkbox][name=" + father + "]";
	var child_str1 = "input[type=radio][name=" + child1 + "]";
	// var child_str2 = "input[type=checkbox][name=" + child2 + "]";
	var child2 = document.getElementsByName(child2);
	// console.log(child2);
	// console.log($(child2));
	$(father_str).change(function() {
		if (this.checked) {
			// alert("启用"); 
			for (i = 0; i < $(child_str1).length; i++) {
				$(child_str1)[i].disabled = "";
			}
			$(child_str1)[0].checked = "checked";
			for (i = 0; i < child2.length; i++) {
				child2[i].disabled = "";
			}
			child2[0].checked = "checked";
		} else {
			// alert("未启用"); 
			// $(child_str1).disabled = "disabled";
			for (i = 0; i < $(child_str1).length; i++) {
				$(child_str1)[i].disabled = "disabled";
				$(child_str1)[i].checked = "";
			}
			for (i = 0; i < child2.length; i++) {
				child2[i].disabled = "disabled";
				child2[i].checked = "";
			}
		}
	});
}
// 输入获取父元素的字段与子元素的字段，改变子元素的可选状态
function changeChild(father, child) {
	var father_str = 'input[type=checkbox][name=' + father + ']';
	var child_str = 'input[type=radio][name=' + child + ']';
	// console.log(child);
	$(father_str).change(function() {
		if (this.checked) {
			// alert("启用"); 
			for (i = 0; i < $(child_str).length; i++) {
				$(child_str)[i].disabled = "";
			}
			$(child_str)[0].checked = "checked";
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

//整理form3 
function zhenliQuestion(jsonInfo, lenq) {
	console.log(jsonInfo);
	var key = 'type3'
	var result = '';
	var head = ''
	if (jsonInfo.type == 1) {
		// 砖木结构		
		head = creatHead(key, 4, jsonInfo)
		console.log(head);
		var relu = [1, 1, 2, 1];
		var relu2 = [0, 0, 4, 0]


	} else if (jsonInfo.type == 2) {
		head = creatHead(key, 4, jsonInfo);
		console.log(head);
		var relu = [1, 1, 2, 1];
		var relu2 = [0, 0, 3, 0];
	} else if (jsonInfo.type == 3) {
		head = creatHead(key, 5, jsonInfo);
		console.log(head);
		var relu = [1, 2, 2, 1, 1];
		var relu2 = [0, 2, 2, 0, 0];
	} else if (jsonInfo.type == 4) {
		head = creatHead(key, 5, jsonInfo);
		console.log(head);
		var relu = [1, 2, 1, 1];
		var relu2 = [0, 2, 0, 0];
	} else if (jsonInfo.type == 5) {
		head = creatHead(key, 5, jsonInfo);
		console.log(head);
		var relu = [1, 2, 2, 2, 2];
		var relu2 = [0, 4, 4, 3, 2];
	}
	result = regularization(jsonInfo, head, relu, relu2, key, lenq);
	console.log(result);
	return lenq + ';' + fixInteger(two2x(head), lenq) + result;
}

//把一个表示二进制的数组转化为十进制
function two2x(arr) {
	console.log('arr=' + arr);
	var arr_x = parseInt(arr.join(''), 2);
	console.log(arr_x);
	return arr_x;
}
//整理form2
function zhenliType(jsonInfo, lenq) {
	var key = 'type2'
	var result = '';
	console.log(jsonInfo.type21);
	if (jsonInfo.type == 1) {
		// 砖木结构	
		var type21 = creatHead2('type21', 4, jsonInfo)
		var type21_x = parseInt(type21.join(''), lenq);
		console.log(type21_x)
		result = fixInteger(type21_x, lenq) + '' + fixInteger(jsonInfo.type22, lenq);
	} else if (jsonInfo.type == 2) {

	} else if (jsonInfo.type == 3) {
		var type21 = creatHead2('type21', 4, jsonInfo)
		var type21_x = parseInt(type21.join(''), lenq);
		result = result + fixInteger(type21_x, lenq);
		var type22 = creatHead2('type22', 4, jsonInfo)
		var type22_x = parseInt(type22.join(''), lenq);
		result = result + fixInteger(type22_x, lenq);
	} else if (jsonInfo.type == 4) {
		var type21 = creatHead2('type21', 4, jsonInfo)
		var type21_x = parseInt(type21.join(''), lenq);
		result = result + fixInteger(type21_x, lenq);
	} else if (jsonInfo.type == 5) {
		result = result + fixInteger(jsonInfo.type21, lenq);
		var type22 = creatHead2('type22', 4, jsonInfo)
		var type22_x = parseInt(type22.join(''), lenq);
		result = result + fixInteger(type22_x, lenq);
		result = result + fixInteger(jsonInfo.type23, lenq);
	}
	return lenq + ';' + result;
}

// 
function creatHead2(key, len, jsonInfo) {
	var head = new Array(len);
	head = initArr(head, 0);
	console.log(key);
	console.log(jsonInfo[key])
	for (i = 0; i < jsonInfo[key].length; i++) {
		if (typeof(jsonInfo[key][i]) == 'underfind') {
			head[i] = 0;
		} else {
			head[i] = 1;
		}
	}
	console.log('head2=' + head);
	// head=parseInt(head,2);
	// console.log(head);
	return head;
}

function creatHead(key, len, jsonInfo) {
	var head = new Array(len);
	for (i = 1; i <= len; i++) {
		var keyName = key + i;
		if (jsonInfo.hasOwnProperty(keyName)) {
			console.log(i)
			head[i - 1] = 1;
		} else {
			console.log(i)
			head[i - 1] = 0;
		}
		console.log(head)
	}
	console.log(head);
	// head=parseInt(head,2);
	// console.log(head);
	return head;
}

//规则化
/**
 * @param {Object} head 头两位数的规则
 * @param {Object} relu n个选项的规则，是1情况还是2情况
 * @param {Object} relu2 n个选项的长度，既有几个子选项
 * @param {Object} key 关键字，如'type31'
 * @param {Object} len 字符换算长度，否则补零
 */
function regularization(jsonInfo, head, relu, relu2, key, len) {
	var result = '';
	console.log('regularization' + head)
	for (i = 0; i < 4; i++) {
		keym = key + (i + 1);
		if (head[i] == 1) {
			if (relu[i] == 1) {
				var keya = keym + 1;
				result = result + "" + fixInteger(jsonInfo[keya], len);
			} else if (relu[i] == 2) {
				var keya = keym + 1;
				var keyb = keym + 2;
				console.log(keya);
				console.log(keyb);
				var keyb = creatHead2(keyb, relu2[i], jsonInfo);
				var keyb_x = two2x(keyb);
				result = result + "" + fixInteger(keyb_x, len);
				result = result + fixInteger(jsonInfo[keya], len);
			}
		}
	}
	console.log(result);
	return result;
}
// 补全json字段，补全缺少的photo字段
function zhenliPhoto(jsonInfo) {
	for (i = 1; i <= 4; i++) {
		var keySrc = "photoSrc" + i;
		if (!jsonInfo.hasOwnProperty(keySrc)) {
			jsonInfo[keySrc] = "";
		}
	}
	return jsonInfo;
}

//整理承重墙的部分统计,返回一个18位长的数组
function zhenliDamage(jsonInfo) {
	var damageArr = new Array(18)
	damageArr[0] = jsonInfo.zhongzhu;
	damageArr[1] = jsonInfo.zhongzhu_w;
	damageArr[2] = jsonInfo.bianzhu;
	damageArr[3] = jsonInfo.bianzhu_w;
	damageArr[4] = jsonInfo.jiaozhu;
	damageArr[5] = jsonInfo.jiaozhu_w;
	damageArr[6] = jsonInfo.wujia;
	damageArr[7] = jsonInfo.wujia_w;
	damageArr[8] = jsonInfo.zhongjianliang;
	damageArr[9] = jsonInfo.zhongjianliang_w;
	damageArr[10] = jsonInfo.bianliang;
	damageArr[11] = jsonInfo.bianliang_w;
	damageArr[12] = jsonInfo.qiangti;
	damageArr[13] = jsonInfo.qiangti_w;
	damageArr[14] = jsonInfo.loubangoujian;
	damageArr[15] = jsonInfo.loubangoujian_w;
	damageArr[16] = jsonInfo.weihugoujian;
	damageArr[17] = jsonInfo.weihugoujian_w;
	return damageArr;
}

function initArr(arr, q) {
	for (i = 0; i < arr.length; i++) {
		arr[i] = q;
	}
	return arr;
}

function addPhoto(creatImage, imageSqlStr) {
	var isOk = false;
	var imageId = "";
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
					a = plus.sqlite.selectSql({
						name: 'info',
						sql: 'select rowid from imageDB',
						success: function(e) {
							console.log('selectSql success: ' + JSON.stringify(e));
							var tempid = e.rowid;
							// console.log(e.length)
							imageId = e[e.length - 1].rowid;
							isOk = true;
							console.log(imageId)
							return imageId;
						},
						fail: function(e) {
							console.log('selectSql fail: ' + JSON.stringify(e));
						}
					});
					console.log(a);

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
	if (isOk) {
		// closeDB();
		console.log(imageId);
		return imageId;
	} else {
		console.log('失败了' + isOk)
		return null;
	}
}
