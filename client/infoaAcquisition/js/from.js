function onClik(){
    //var data = $("#form1").serializeArray(); //自动将form表单封装成json
    //alert(JSON.stringify(data));
    var jsonuserinfo = $('#form1').serializeObject();
    alert(JSON.stringify(jsonuserinfo));
}
$.fn.serializeObject = function()
{
  var o = {};
  var a = this.serializeArray();
  $.each(a, function() {
    if (o[this.name]) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};