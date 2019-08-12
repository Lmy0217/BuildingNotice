var funType = GetUrlParam("fc");

window.onload = function() {
	verification();
	setTimes();
	main();
}

function main(){
	var title = document.getElementById("title");
	var name = getCookie('user');
	var user = document.getElementById("user");
	user.innerText = name;
	var header = document.getElementById("header");
	var content = document.getElementById("content");
	
	if (funType == 1) {
		result=mailOk();
	}
	console.log(result);
	title.innerText=result[0];
	header.innerText=result[1];
	content.innerHTML=result[2];
}


function mailOk() {
	var email = GetUrlParam("email");
	var arr = email.split('@'); //两部分
	var mailName=arr[1].split('.');
	var mailUrl = "https://www.baidu.com/s?ie=utf-8&tn=baidu&wd=" + mailName[0] + "邮箱登录";
	var titleC = "验证邮件发送成功";
	var headerC=titleC;
	var contentC = "您提交的邮箱是：" + email + "<br>" +
	"请前往邮箱查看"+"<br>" +
	"如果没有收到，请稍等片刻或者查看“垃圾箱”或“广告邮件”查找"+"<br>"+
	"您可以"+"&nbsp;<a href='"+mailUrl+"' target='_blank'>登录邮箱</a>"+"&nbsp;&nbsp;<a href='/index.html'>返回后台首页</a>";
	var result=new Array(titleC, headerC, contentC);
	console.log(result);
	return result;
}


function setTimes() {
	var date = NOW();
	if (date.length == 2) {
		date1 = date[0].split("-");
		//console.log(date1)
		date2 = date[1].split(":");
		//console.log(date2)
		var dateStr = date1[0] + "年" + date1[1] + "月" + date1[2] + "日" + date2[0] + "时" + date2[1] + "分" + date2[2] + "秒";
		//console.log(document.getElementById("date"));
		document.getElementById("localTime").innerText = dateStr;
	} else {
		document.getElementById("localTime").innerText = "获取本地时间失败.";
	}
}