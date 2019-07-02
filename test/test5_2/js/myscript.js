mui.init({
	swipeBack: true //启用右滑关闭功能
});
mui('.mui-input-group').on('change', 'input', function() {
	var value = this.checked ? "true" : "false";
	this.previousElementSibling.innerText = "checked：" + value;
});
