# API v0.1-beta1

* [创建用户](#创建用户) | [用户登录](#用户登录) | [用户登出](#用户登出)

* [个人信息](#个人信息) | [更新令牌](#更新令牌) | [修改密码](#修改密码)

* [发送邮箱验证邮件](#发送邮箱验证邮件) | [邮箱验证](#邮箱验证)

* [发送重置密码邮件](#发送重置密码邮件) | [验证重置密码邮件](#验证重置密码邮件) | [重置密码](#重置密码)

* [生成邀请码](#生成邀请码) | [邀请码列表](#邀请码列表)

* [修改权限](#修改权限) | [用户列表](#用户列表)

* [上传图片](#上传图片) | [下载图片](#下载图片)

* [创建报告](#创建报告) | [删除报告](#删除报告) | [下载报告](#下载报告) | [报告列表](#报告列表)


## **创建用户**
创建一个用户。创建时，请使用 HTTP POST 方式
#### API地址
[POST] /user/create
#### POST参数 (Json)
|参数|类型|说明|规则|
|-|-|-|-|
|name|String|(必填) 用户名|^\[a-zA-Z0-9\]\[a-zA-Z0-9\]{2,15}$|
|pwd|String|(必填) 密码|^\[a-zA-Z\]\[a-zA-Z0-9_\]{7,17}$|
|invite|String|(必填) 邀请码|^\[a-zA-Z0-9\]{8}$|
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
|role|Integer|权限|
|email|String|邮箱 (未绑定返回 null)|
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

## **个人信息**
用户获取个人信息。获取时，请使用 HTTP POST 方式
#### API地址
[POST] /user/my
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|role|Integer|权限|
|email|String|邮箱 (未绑定返回 null)|
|archcount|Integer|总报告数|
|archdown|Integer|已下载报告数|
|archnodown|Integer|未下载报告数|
|archdelete|Integer|已删除报告数|
|adminname|String|上级管理员名 (不存在返回 null)|
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

## **发送邮箱验证邮件**
发送邮箱验证邮件。发送时，请使用 HTTP POST 方式
#### API地址
[POST] /user/email/send
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|email|String|(必填) 邮箱地址|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **邮箱验证**
邮箱验证。验证时，请使用 HTTP POST 方式
#### API地址
[POST] /user/email/verify
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|code|String|(必填) 邮箱验证代码|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **发送重置密码邮件**
发送重置密码邮件。发送时，请使用 HTTP POST 方式
#### API地址
[POST] /user/reset/send
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|name|String|(选填) 用户名|
|email|String|(选填) 邮箱地址|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **验证重置密码邮件**
验证重置密码邮件。验证时，请使用 HTTP POST 方式
#### API地址
[POST] /user/reset/verify
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|code|String|(必填) 验证代码|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|code|String|重置密码代码|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **重置密码**
重置密码。重置时，请使用 HTTP POST 方式
#### API地址
[POST] /user/reset/action
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|code|String|(必填) 重置密码代码|
|pwd|String|(必填) 新密码|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|token|String|令牌|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **生成邀请码**
生成邀请码。修改时，请使用 HTTP POST 方式
#### API地址
[POST] /invite/create
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|count|Integer|(必填) 生成个数 (不超过 10)|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **邀请码列表**
用户获取自己生成的邀请码列表。获取时，请使用 HTTP POST 方式
#### API地址
[POST] /invite/list
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|type|Integer|(必填) 邀请码类型 (0: 所有类型, 1: 未使用, 2: 已使用)|
|page|Integer|(必填) 第几页|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|count|Integer|邀请码数|
|list|List (Json)|邀请码信息列表 (邀请码信息：邀请码 (code)、使用者用户名 (invite, 未使用返回 null)、状态 (status, 0: 未使用, 1: 已使用))|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **修改权限**
管理员修改普通用户权限。修改时，请使用 HTTP POST 方式
#### API地址
[POST] /user/role
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|userid|Integer|(必填) 要修改的用户 Id|
|role|Integer|(必填) 要修改到的权限|
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
|list|List (Json)|用户信息列表 (用户信息：用户 Id (id)、用户名 (name)、用户权限 (role)、用户报告数 (archcount))|
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
|json|String|(必填) Json 字符串|
Json 字符串
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

## **删除报告**
用户删除报告。删除时，请使用 HTTP POST 方式
#### API地址
[POST] /archive/delete
#### POST参数 (Json)
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|ids|List (Integer)|(必填) 报告 Id 列表|
#### 成功返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|200|
|count|Integer|删除报告数|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## **下载报告**
从服务器下载报告压缩包。下载时，请使用 HTTP POST 方式
#### API地址
[POST] /archive/download
#### POST参数 (非Json)
|参数|类型|说明|
|-|-|-|
|json|String|(必填) Json 字符串|
Json 字符串
|参数|类型|说明|
|-|-|-|
|token|String|(必填) 令牌|
|type|Integer|(选填) 报告类型|
|ids|List (Integer)|(选填) 报告 Id 列表|
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
|list|List (Json)|报告信息列表 (报告信息：报告 Id (id)、标题 (title)、采集时间 (date))|
#### 失败返回数据 (Json)
|参数|类型|说明|
|-|-|-|
|status|Integer|错误状态码 (参见 [状态码](#状态码))|
|msg|String|错误描述|

## 状态码
成功 `200`，请求错误 `400`，未登录 `401`，权限禁止 `403`，服务器错误 `500`