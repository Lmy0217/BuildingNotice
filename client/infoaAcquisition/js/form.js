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
	console.log(jsonInfo);
	//------------------------
	jsonInfo = zhenliPhoto(jsonInfo);
	damage = zhenliDamage(jsonInfo);
	// console.log(damage);
	typeb = zhenliType(jsonInfo);
	// alert('typeb!');
	console.log(typeb);
	typec = zhenliQuestion(jsonInfo);
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
		typeb.toString() + "','" +
		typec.toString() + "','" +
		jsonInfo.remark + "'," +
		0 + ")";
	openDB();
	// insertSQL(tableName, creatMain, sqlStr);
	console.log(creatMain);
	console.log(sqlStr);
	alert('zuzhi!');
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
					// mui.back();
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
	watchJSON(form_now);
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
function zhenliQuestion(jsonInfo) {
	console.log(jsonInfo);
	var lenq = 2;
	alert('pause!');
	var key = 'type3'
	var result = '';
	if (jsonInfo.type == 1) {
		// 砖木结构		
		var head = ''
		head = creatHead(key, 4, jsonInfo)
		if (head[0] == 1) {
			result = result + fixInteger(jsonInfo.type311, lenq);
		} else if (head[1] == 1) {
			result = result + fixInteger(jsonInfo.type321, lenq);
			var type322=creatHead2('type322', 4, jsonInfo);
			console.log('type322='+type322);
			var type322_x=parseInt(type322.join(''),2);
			console.log(type322_x);
			result = result + fixInteger(type322_x, lenq);
		}else if (head[2] == 1) {
			result = result + fixInteger(jsonInfo.type331, lenq);
			var type332=creatHead2('type332', 3, jsonInfo);
			console.log('type332='+type332);
			var type332_x=parseInt(type332.join(''),2);
			console.log(type332_x);
			result = result + fixInteger(type332_x, lenq);
		}
		else if (head[3] == 1) {
			result = result + fixInteger(jsonInfo.type321, lenq);
			var type342=creatHead('type342', 2, jsonInfo);
			console.log('type342='+type342);
			var type342_x=parseInt(type342.join(''),2);
			console.log(type342_x);
			result = result + fixInteger(type342_x, lenq);
		}
		// ---------------
		for (i = 0; i < jsonInfo.type31.length - 1; i++) {
			result = result + jsonInfo.type21[0] + "、"
		}
		result = result + jsonInfo.type21.length[0];
		// ---------------
		result = result + jsonInfo.type22
		return result;
	} else if (jsonInfo.type == 2) {

	} else if (jsonInfo.type == 3) {

	}
}

//整理form2
function zhenliType(jsonInfo) {
	console.log(jsonInfo);
	alert('pause!');
	var key = 'type2'
	var result = '';
	console.log(jsonInfo.type21);
	if (jsonInfo.type == 1) {
		// 砖木结构	
		// ---------------
		var type21=creatHead2('type21',4,jsonInfo)
		var type21_x=parseInt(type21.join(''),2);
		console.log(type21_x)
		// ---------------
		result = fixInteger(type21_x,2) +''+fixInteger(jsonInfo.type22,2);
		return result;
	} else if (jsonInfo.type == 2) {

	} else if (jsonInfo.type == 3) {

	}
}

// 
function creatHead2(key, len, jsonInfo) {
	var head = new Array(len);
	for (i = 0; i <= jsonInfo[key].length; i++) {
		if(typeof(jsonInfo[key][i])=='underfind'){
			head[i]=0;
		}else{
			head[i]=1;
		}
	}
	console.log(head);
	// head=parseInt(head,2);
	// console.log(head);
	return head;
}

function creatHead(key, len, jsonInfo) {
	var head = new Array(len);
	for (i = 1; i <= len; i++) {
		var keyName = key + i;
		if (jsonInfo.hasOwnProperty(keyName)) {
			head[len - i] = 1;
		} else {
			head[len - i] = 0;
		}
	}
	console.log(head);
	// head=parseInt(head,2);
	// console.log(head);
	return head;
}

//规则化
function regularization(len, head, type) {
	var typeArr = new Array(len);
	typeArr = initArr(typeArr, 0);
	for (i = 0; i < len; i++) {
		var keySrc = type + i;
		if (jsonInfo.hasOwnProperty(keySrc)) {
			queArr[i] = jsonInfo[keySrc];
		} else {
			queArr[i] = 0;
		}
	}

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
	damageArr[12] = jsonInfo.bianliang_w;
	damageArr[13] = jsonInfo.qiangti;
	damageArr[14] = jsonInfo.qiangti_w;
	damageArr[15] = jsonInfo.loubangoujian;
	damageArr[16] = jsonInfo.loubangoujian_w;
	damageArr[17] = jsonInfo.weihugoujian;
	damageArr[18] = jsonInfo.weihugoujian_w;
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
