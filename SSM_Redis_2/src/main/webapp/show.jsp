<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
</head>
<body>
<a href="add.jsp">新增</a>
<table border="1" cellspacing="0">
 <tr><td>id</td>
 <td>姓名</td>
 <td>密码</td>
 <td>钱</td>
 <td>操作</td></tr>
 <c:forEach items="${list}" var="user">
  <tr><td>${user.id }</td>
  <td>${user.name }</td>
  <td>${user.password }</td>
  <td>${user.qian }</td>
  <td><a href="selectById?id=${user.id}">✎</a>
  <a href="delete?id=${user.id }">x</a>
  </tr>
 </c:forEach>
 
</table>
</body>
</html>