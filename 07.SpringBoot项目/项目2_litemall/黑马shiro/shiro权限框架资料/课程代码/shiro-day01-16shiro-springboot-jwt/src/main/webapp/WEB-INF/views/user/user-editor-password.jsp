<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台-注册用户</title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<%@ include file="/WEB-INF/layouts/formvalidator.jsp" %>
<script>
	//提交表单
	function doSave() {
		var flag = true;
		var result = false;
		flag = $("#userForm").form('validate');
		if (flag) {
			var param = $("#userForm").serialize();
			var url = $("#userForm").attr("action");
			$.ajax({
				type : "POST",
				url : url,
				data : param,
				dataType : "json",
				async : false,
				success:function(returnMsg){
					result=returnMsg;
				},
				error : function(XHR, textStatus, errorThrown) {
					result=false;
				}
			});
		}
		return result;
	}
</script>
</head>
<body>
	<form action="${ctx}/login/saveNewPassword" method="post" id="userForm" name="userForm" >
	    <table width="95%" border="0" cellspacing="0" cellpadding="0" class="add_tab" >
	    	<tr>
            <th >原密码：</th>
            <td ><input name="oldPassword" id="oldPassword" type="password" class="easyui-validatebox txtbox " data-options=required:true value=""/></td>
            </tr>
            <tr>
            <th >新密码：</th>
            <td ><input name="plainPassword" id="plainPassword" type="password" class="easyui-validatebox txtbox " data-options=required:true,validType:["same['plainPassword1']","length[0,10]"] value=""/></td>
            </tr>
            <tr>
            <th >再次确认：</th>
            <td ><input name="plainPassword1" id="plainPassword1" type="password" class="easyui-validatebox txtbox " data-options=required:true,validType:["same['plainPassword']","length[0,10]"] value=""/></td>
            </tr>
            
	    </table>
	</form>
</body>
</html>


