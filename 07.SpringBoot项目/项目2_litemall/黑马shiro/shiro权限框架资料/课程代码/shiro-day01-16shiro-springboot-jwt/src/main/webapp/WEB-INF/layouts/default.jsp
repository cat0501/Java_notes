<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>springplay演示:<sitemesh:title/></title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<script>
//全局参数建议
var content = '${ctx}';
</script>
<sitemesh:head/>
</head>

<body>
	<sitemesh:body/>
</body>
</html>