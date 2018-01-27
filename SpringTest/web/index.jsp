<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/16 0016
  Time: 上午 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();

%>
<html>
  <head>
    <title>test haha</title>
  </head>
  <body>
hahahahahahahah
  <a href="com/MyServlet" >test doget</a>
  <br>
  <form action="com/MyServlet" method="post">
    <input type="submit" value="post 请求">
  </form>
  </body>
</html>
