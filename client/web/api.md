# 后台相关API

说明：{website} 是向后端请求的链接，请补齐。

## 1. 登录

```json
post {website}
//发送json
//contentType: "application/x-www-form-urlencoded;charset=utf-8",
{
    name:(str),
    pwd:(str),
    imei:'pc'
}
//返回
{
    status:200;//成功登陆
    token:(str);
	over:(num),//已经提交的任务
	undown:(num),//还未下载的任务总数
}
//其他失败同APP api

```
## 2. 获取后台展示列表

```json
get {website}?type=1&page=1

//发送
//contentType:  'application/json; charset=UTF-8'
{
	type: (num),//type: 0,所有类别；1，未下载的文件；2，已下载的文件
	page: (num),//page:(页数，一页15条记录，从最后开始算)
	token: (str)
}

返回值
{
status:200;//成功
type:(num)，  //
		count:(str),//总条数
		list:[
		   {
      	    id:(num),//文件id，用于向后台请求文件的依据
      		title:(str),//文件名字，建议用  “采集时间-户主”命名
      		date:(str) //(可以是文件完成转换的时间，或者文件上传的时间)
      		},
			...]	
			
		}
}
//说明，这里展示list，从最近往之前读，先取最近的15条。但同是返回总条数
// 用户可以按照三种方式查看其名下的文件，分别是还未下载的和已经下载的，以及全部文件
// 如果请求第n页的文件，如{website}?type=1&page=3，那么需要从数据库取倒数46-60条文件
```

## 3 下载请求

```json
get {website}
//说明：
//type: 0,所有类别；1，未下载的文件；2，已下载的文件
//page:(页数，一页15条记录，从最后开始算)
//发送
//contentType:  'application/json; charset=UTF-8'
{
    type:-1,// -1，按照id读取要下载的文件；1，所有未下载的文件，不需要读取id；2，所有已下载的文件，不需要读取id.
    id:[
        (num),
   		... ],//文件id的数组
    token:(str),
}

//返回值
文件压缩包的stream

//文件下载提供两种方式
// 一种是勾选式，用户勾选若干文件，然后获取到这些文件的id，提供下载。
// 一种是全部式，用户可以选择下载所有的“未下载”的文件，或者已下载的文件。这时，id为空
// 下载全部文件或者已下载的文件对服务器压力太大。是否需要这种功能，待商榷。
```
