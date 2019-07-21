function getFileInfo() {

	getFileInfoAjax(0, 'over');
	getFileInfoAjax(1, 'undown');
	getFileInfoAjax(2, 'downed');

}

function getFileInfoAjax(type, key) {
	var token = localStorage.getItem("TOKEN_TOKEN");
	var data = {
		type: type,
		page: 1,
		token: token,
	}
	$.ajax({
		url: listUrl,
		datatype: "json",
		contentType: 'application/json;charset=UTF-8',
		data: JSON.stringify(data),
		type: 'post',
		success: function(data) {
			console.log(data);
			document.getElementById(key).innerText = data.count;
		},
		error: function(data) {
			document.getElementById(key).innerText = 'NaN';
		}
	})
}
