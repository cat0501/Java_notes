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
	<form action="${ctx}/user/save" method="post" id="userForm" name="userForm" >
	  <input type="hidden" name="id" id = "id" value="${user.id}" />
	  <br />
	    <table width="95%" border="0" cellspacing="0" cellpadding="0" class="add_tab" >
            <tr>
            <th width="15%">登录名称：</th>
            <td width="35%"><input name="loginName" id="loginName" type="text"  class="easyui-validatebox txtbox" data-options=required:true,validType:["different['email']","different['mobil']","loginName['${ctx}/user/checkLoginName','loginName','${user.loginName}']"] value="${user.loginName}"/></td>
            <th width="15%">真实姓名：</th>
            <td width="35%"><input name="realName" id="realName" type=" text" class=" easyui-validatebox txtbox"  data-options=required:true,validType:["length[0,5]","chinese"] value="${user.realName}"/></td>
            </tr>
            <c:if test="${user.id==null}">
            <tr>
            <th >密码：</th>
            <td ><input name="plainPassword" id="plainPassword" type="password" class="easyui-validatebox txtbox " data-options=required:true,validType:["same['plainPassword1']","length[0,10]"] value=""/></td>
            <th >密码确认：</th>
            <td ><input name="plainPassword1" id="plainPassword1" type="password" class="easyui-validatebox txtbox " data-options=required:true,validType:["same['plainPassword']","length[0,10]"] value=""/></td>
            </tr>
            </c:if>
            <tr>
            <th >性别：</th>
            <td >
            <select name="sex" id="sex" class="easyui-combobox"  data-options="editable:false">
            <option value="1" <c:if test="${user.sex=='1'}">selected</c:if>>男</option>
            <option value="0" <c:if test="${user.sex=='0'}">selected</c:if>>女</option>
            </select>
            </td>
            <th >职务：</th>
            <td ><input name="duties" id="duties" type="text" class="easyui-validatebox txtbox" data-options=validType:["length[0,10]"] value="${user.duties}"/></td>
            </tr>
            <tr>
            <th >昵称：</th>
            <td ><input name="nickName" id="nickName" type="text" class="easyui-validatebox txtbox" data-options=validType:["length[0,5]"] value="${user.nickName}"/></td>
            </tr>
            <tr>
              <th>角色：</th>
              <td colspan="3">
              <input id="hasRoleIds" name="hasRoleIds" class="easyui-combobox" 
            	data-options="url:'${ctx}/role/findRoleComboboxVo?roleIds=${user.hasRoleIds}',editable:false,method:'get',valueField:'id',textField:'text',multiple:true" style="width:380px;"/></td>
            </tr>
            <tr>
            <th >电话：</th>
            <td ><input name="tel"  id="tel" type="text" class="easyui-validatebox txtbox" data-options=validType:["phone"] value="${user.tel}"/></td>
            <th >手机：</th>
            <td ><input name="mobil" id="mobil" type="text" class="easyui-validatebox txtbox" data-options=required:true,validType:["mobile","different['loginName']","loginName['${ctx}/user/checkLoginName','mobil','${user.mobil}']"] value="${user.mobil}"/></td>
            </tr>
            <tr>
            <th >电子邮件：</th>
            <td ><input name="email" id="email" type="text" class="easyui-validatebox txtbox" data-options=required:true,validType:["email","different['loginName']","loginName['${ctx}/user/checkLoginName','email','${user.email}']"] value="${user.email}"/></td>
            <th >状态：</th>
            <td >
            <select name="enableFlag" id="enableFlag" class="easyui-combobox"  data-options="editable:false">
            <option value="${SuperConstant_YES}" <c:if test="${user.enableFlag==SuperConstant_YES}">selected</c:if>>启用</option>
            <option value="${SuperConstant_NO}"<c:if test="${user.enableFlag==SuperConstant_NO}">selected</c:if>>停用</option>
            </select>
            </td>
            </tr>
            <tr>
            <th >地址：</th>
            <td ><input name="address" id="address" type="text" class="easyui-validatebox txtbox" data-options=validType:["length[0,128]"] value="${user.address}"/></td>
            <th >邮编：</th>
            <td ><input name="zipcode" id="zipcode" type="text" class="easyui-validatebox txtbox" data-options=validType:["zip"] value="${user.zipcode}"/></td>
            </tr>
            <tr>
            <th >排序：</th>
            <td colspan="3">
           		<input name="sortNoOld" type="hidden" value="${user.sortNo }"/>
            	<input name="sortNo" id="sortNo" type="text" class="easyui-validatebox txtbox" data-options=required:true,validType:["integer"] value="<fmt:formatNumber pattern="0" value="${user.sortNo}" />"/></td>
            </tr>
	    </table>
	</form>
</body>
</html>


