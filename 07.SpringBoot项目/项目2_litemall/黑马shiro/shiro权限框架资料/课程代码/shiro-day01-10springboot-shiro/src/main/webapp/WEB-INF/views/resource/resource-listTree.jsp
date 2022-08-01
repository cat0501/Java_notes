<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台-资源列表</title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<%@ include file="/WEB-INF/layouts/ztree.jsp" %>
<script type="text/javascript" src="${ctx}/static/js/zTree.js"></script>
</head>

<body>
<form action="${ctx}/resource/tree?parentId=-1" id ="parentIdUrl"></form>
<form action="${ctx}/resource/tree" id ="childUrl"></form>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
   		<td valign="top">
         <ul id="tree" class="ztree"></ul>
		</td>
	</tr>
</table>
</body>
</html>