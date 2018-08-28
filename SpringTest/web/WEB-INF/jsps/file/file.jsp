<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/29 0029
  Time: 下午 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <div align="center">
    <h1>上传文件</h1>
     <form method="post" action="/hello/photoUpload" enctype="multipart/form-data">
        <input type="file" name="file"/>
       <input type="submit"/>
     </form>
  </div>
</body>
</html>
