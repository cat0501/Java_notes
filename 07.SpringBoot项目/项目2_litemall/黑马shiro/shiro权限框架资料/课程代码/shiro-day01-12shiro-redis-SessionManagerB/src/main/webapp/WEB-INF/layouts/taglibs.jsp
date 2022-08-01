<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:if test="${pageContext.request.contextPath!='/'}">
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
</c:if>
<script>
	var ctx = "${ctx}";
	var nginxPath = '${nginxPath}';
</script>