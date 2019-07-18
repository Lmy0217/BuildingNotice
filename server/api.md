# API v0.1-alpha3

* [创建用户](#创建用户)
* [用户登录](#用户登录)
* [用户登出](#用户登出)


* [更新令牌](#更新令牌)
* [修改密码](#修改密码)


* [修改权限](#修改权限)
* [用户列表](#用户列表)


* [上传图片](#上传图片)
* [下载图片](#下载图片)


* [创建报告](#创建报告)
* [下载报告](#下载报告)
* [报告列表](#报告列表)


## **创建用户**
创建一个用户。创建时，请使用 HTTP POST 方式
#### API地址
[POST] /user/create
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|name|String|(必填) 用户名|
|pwd|String|(必填) 密码|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|userid|Integer|用户 Id|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **用户登录**
用户登录。登录时，请使用 HTTP POST 方式
#### API地址
[POST] /user/login
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|name|String|(必填) 用户名|
|pwd|String|(必填) 密码|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|token|String|令牌|
|perm|Integer|权限 (暂定：1 普通用户 2 管理员)|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **用户登出**
用户登出。登出时，请使用 HTTP POST 方式
#### API地址
[POST] /user/logout
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|msg|String|成功描述|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **更新令牌**
更新令牌。更新时，请使用 HTTP POST 方式
#### API地址
[POST] /user/token
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|token|String|新令牌|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **修改密码**
修改密码。修改时，请使用 HTTP POST 方式
#### API地址
[POST] /user/pwd
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|pwd|String|(必填) 旧密码|
|newpwd|String|(必填) 新密码|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|token|String|新令牌|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **修改权限**
管理员修改普通用户权限。修改时，请使用 HTTP POST 方式
#### API地址
[POST] /user/pwd
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|userid|Integer|(必填) 要修改的用户 Id|
|role|Integer|(必填) 要修改到的权限 (暂定：0 无权限用户 1 普通用户)|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **用户列表**
管理员查看普通用户列表。修改时，请使用 HTTP POST 方式
#### API地址
[POST] /user/list
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|page|Integer|(必填) 第几页|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|count|Integer|用户数|
|list|List (Json)|用户信息列表 (用户信息：用户 Id、用户权限、用户报告数)|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **上传图片**
上传图片到服务器。上传时，请使用 HTTP POST 方式，其中 enctype 为 'multipart/form-data'
#### API地址
[POST] /image/upload
#### POST参数 (非Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|file|File|(必填) 需要上传的图片文件|
|depict|String|(必填) 图片描述|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|imageid|Integer|图片 Id|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **下载图片**
从服务器下载图片。下载时，请使用 HTTP POST 方式
#### API地址
[POST] /image/download
#### POST参数 (非Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|id|Integer|(必填) 图片 Id (参见 [上传图片](#上传图片))|
#### 成功返回数据 (非Json)
|参数|类型|说明|
|-|-|-|
|-|Stream|图片文件流|
#### 失败返回数据 (非Json)
|参数|类型|说明|
|-|-|-|
|-|String|错误描述|

## **创建报告**
上传报告信息到服务器。上传时，请使用 HTTP POST 方式，其中 enctype 为 'application/json; charset=UTF-8'
#### API地址
[POST] /archive/create
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|unit|String|(选填) 委托单位 (人)|
|phone|String|(选填) 联系电话|
|material|String|(选填) 提供资料情况|
|addr|String|(选填) 房屋地址|
|hold|String|(选填) 户主姓名|
|holdid|String|(选填) 户主身份证号|
|attr|String|(选填) 房屋属性，如：住宅 / 附属用房|
|layer|Integer|(选填) 层数|
|createyear|Integer|(选填) 建造年份|
|typeid|Integer|(必填) 结构类型 Id，可选值：1：砖混结构，2：砖木结构，3：底框结构，4：框架结构|
|body1|String|(选填) 关于"设计、施工、监理单位和相关资料"部分的字符串 (参见 [fromApi](../client/fromApi.md))|
|body2|String|(必填) 关于"基础和主体结构概况"部分的字符串 (参见 [fromApi](../client/fromApi.md))|
|body3|String|(必填) 关于"结构现状描述"部分的字符串 (参见 [fromApi](../client/fromApi.md))|
|remark|String|(选填) 备注|
|imgs|List (Integer)|(必填) 图片 Id 列表，长度不限 (参见 [上传图片](#上传图片))|
|damage|List (Integer)|(必填) 受损信息，长度 18<br><br>填以下数值：[共有中柱, 危险中柱, 共有边柱, 危险边柱, 共有角柱, 危险角柱, 共有屋架, 危险屋架, 共有中梁, 危险中梁, 共有边梁, 危险边梁, 共有称重墙体, 危险称重墙体, 共有楼板构件, 危险楼板构件, 共有围护构件, 危险围护构件]|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|archId|Integer|报告 Id (参见 [创建报告](#创建报告))|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **下载报告**
从服务器下载报告压缩包。下载时，请使用 HTTP POST 方式
#### API地址
[POST] /archive/download
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|type|Integer|(选填) 报告类型|
|ids|List (Integer)|(选填) 报告 Id|
#### 成功返回数据 (非Json)
|参数|类型|说明|
|-|-|-|
|-|Stream|报告压缩包文件流|
#### 失败返回数据 (非Json)
|参数|类型|说明|
|-|-|-|
|-|String|错误描述|

## **报告列表**
查看报告列表。下载时，请使用 HTTP POST 方式
#### API地址
[POST] /archive/list
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|type|Integer|(必填) 报告类型|
|page|Integer|(必填) 第几页|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|count|Integer|报告数|
|list|List (Json)|报告信息列表 (报告信息：报告 Id、标题、采集时间)|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## 状态码
成功 `200`，请求错误 `400`，未登录 `401`，权限禁止 `403`，服务器错误 `500`