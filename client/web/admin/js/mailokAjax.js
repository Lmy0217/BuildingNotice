var funType = GetUrlParam("fc");

var title = document.getElementById("title");
var name = getCookie('user');
var user = document.getElementById("user");
user.innerText = name;
var header = document.getElementById("header");
var content = document.getElementById("content");

if (funType == 1) {
	result=mailOk();
}

title.innerText=result[0];
header.innerText=result[1];
content.innerText=result[2];

function mailOk() {
	var email = GetUrlParam("email");
	var arr = email.split('@'); //两部分
	var mailUrl = "https://www.baidu.com/s?ie=utf-8&tn=baidu&wd=" + arr[1] + "邮箱登录";
	var titleC = "验证邮件发送成功";
	var headerC=titleC;
	var contentC = "您提交的邮箱是：" + email + "<br>" +
	"请前往邮箱查看"+"<br>" +
	"如果没有收到，请稍等片刻或者查看“垃圾箱”或“广告邮件”查找"+"<br>"+
	"您可以"+"<a href='mailUrl'>登录邮箱</a>"+"<a href='/index.html'>返回后台首页</a>";
	var result=new Array(titleC, headerC, contentC);
	return result;
}
