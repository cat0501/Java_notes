<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h6>
    <a href="${pageContext.request.contextPath}/logout">退出</a>

    <shiro:hasRole name="admin">
        <a href="${pageContext.request.contextPath}/order-list">列表</a>
    </shiro:hasRole>

    <shiro:hasPermission name="order:add">
        <a href="${pageContext.request.contextPath}/order-add">添加</a>
    </shiro:hasPermission>
</h6>
</body>
</html>