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


