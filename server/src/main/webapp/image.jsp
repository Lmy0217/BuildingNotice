<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<title>Image</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/image/upload" method="post" enctype="multipart/form-data">
文件名<input type="file" name="file"/><br/>
     <input type="text" name="depict"/> <br/>
     <input type="submit" value="提交"/><br/>
</form>
<form action="${pageContext.request.contextPath}/image/download" method="get" enctype="multipart/form-data">
     <input type="text" name="id"/> <br/>
     <input type="submit" value="下载"/><br/>
</form>
</body>
</html>