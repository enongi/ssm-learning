<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/19
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人中心</title>
</head>
<body>
<div>
    <c:if test="${not empty tbUser}">
        <div>欢迎您，${tbUser.username}${tbUser.sex?'先生':'女士'}             <a href="/user/logout">注销</a></div></div>
    </c:if>
    <c:if test="${ empty tbUser}">
        对不起，请先<a href="/user/login">登录</a>
    </c:if>
</div>
</body>
</html>
