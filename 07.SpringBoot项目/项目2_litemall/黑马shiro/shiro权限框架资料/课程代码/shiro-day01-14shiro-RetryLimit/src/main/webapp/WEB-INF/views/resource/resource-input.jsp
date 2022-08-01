<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台-资源管理</title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<%@ include file="/WEB-INF/layouts/formvalidator.jsp" %>
<%@ include file="/WEB-INF/layouts/ztree.jsp" %>
<script>
	/**
	 * 提交表单
	 */
	function doSave() {
		var flag = true;
		var result = false;
		flag = $("#resourceForm").form('validate');

		if (flag) {
			var param = $("#resourceForm").serialize();
			var url = $("#resourceForm").attr("action");
			$.ajax({
				type : "POST",
				url : url,
				data : param,
				dataType : "json",
				async : false,
				success : function(returnMsg) {
					result = returnMsg;
				},
				error : function(XHR, textStatus, errorThrown) {
					result = false;
				}
			});
		}
		return result;
	}
</script>
</head>
<body>
	<form action="${ctx}/resource/save" method="post" id="resourceForm" name="resourceForm" >
	  <input type="hidden" name="id" id = "id" value="${resource.id}" />
	  <input type="hidden" name="parentId"  value="${parentId}" />
	  <c:if test="${resource.systemCode ne null}">
	  <input type="hidden" name="systemCode" id = "systemCode" value="${resource.systemCode}" />
	  </c:if>
	  <br />
	    <table width="95%" border="0" cellspacing="0" cellpadding="0" class="add_tab" >
            <tr>
            <th width="15%">父级资源：</th>
            <td width="35%">${parentName}</td>
            <th width="15%">资源名称：</th>
            <td width="35%"><input name="resourceName" id="resourceName" type=" text" class=" easyui-validatebox txtbox"  data-options=required:true,validType:["length[0,10]","isNotEmpty"] value="${resource.resourceName}"/></td>
            
            </tr>
            <tr>
            <th>请求路径：</th>
            <td><input name="requestPath" id="requestPath" type="text" class="easyui-validatebox txtbox " data-options=required:true,validType:["isNotEmpty","length[0,50]"] value="${resource.requestPath}"/></td>
            <th >资源标识：</th>
            <td ><input name="label" id="label" type="text" class="easyui-validatebox txtbox " data-options=required:true,validType:["Label['${ctx}/resource/checkLabel','label','${resource.label}']"] value="${resource.label}"/></td>
            </tr>
            <tr>
            <th >资源类型：</th>
            <td >
            <select id="resourceType" name="resourceType" class="easyui-combobox easyui-validatebox" data-options=editable:false,validType:["selectValueRequired"] style="width:171px;" >
              <option value="" >请选择</option>
              <option value="${ResourceConstant_MENU}"<c:if test="${resource.resourceType == ResourceConstant_MENU}">selected="selected"</c:if>>菜单</option>
              <option value="${ResourceConstant_BUTTON}" <c:if test="${resource.resourceType == ResourceConstant_BUTTON}">selected="selected"</c:if>>按钮</option>
              <option value="${ResourceConstant_ASYNCHRONOUS}" <c:if test="${resource.resourceType == ResourceConstant_ASYNCHRONOUS}">selected="selected"</c:if>>异步请求</option>
              <option value="${ResourceConstant_DUBBO}" <c:if test="${resource.resourceType == ResourceConstant_DUBBO}">selected="selected"</c:if>>dubbo请求</option>
            </select>
            </td>
            
            
            <th >是否叶子节点：</th>
            <td >
            <select id="isLeaf" name="isLeaf" class="easyui-combobox easyui-validatebox" data-options=editable:false,validType:["selectValueRequired"] style="width:80px;" >
              <option value="" >请选择</option>
              <option value="${SuperConstant_YES}" <c:if test="${resource.isLeaf==SuperConstant_YES}">selected="selected"</c:if>>是</option>
              <option value="${SuperConstant_NO}" <c:if test="${resource.isLeaf==SuperConstant_NO}">selected="selected"</c:if>>否</option>
            </select>
            </td>
            </tr>
            <tr>
            <th >图标：</th>
            <td ><input name="icon" id="icon" type="text" class="easyui-validatebox txtbox " data-options=required:true,validType:["isNotEmpty","length[0,10]"] value="${resource.icon}"/></td>
            <th >排序：</th>
            <td>
            	<input name="sortNoOld" type="hidden" value="${resource.sortNo }"/>
            	<input name="sortNo" id="sortNo" type="text" class="easyui-validatebox txtbox" data-options=required:true,validType:["integer","isNotEmpty"] value="${resource.sortNo}"/></td>
            </tr>
            <c:if test="${resource.systemCode eq null}">
            <tr>
            <th>systemCode：</th>
            <td colspan="3"><input type="text" class="easyui-validatebox txtbox" data-options=required:true,validType:["isNotEmpty"] name="systemCode" id = "systemCode" value="${resource.systemCode}" /><font color="red">* 此属性需要在统一注册中心注册</font></td>
            </tr>
            <input type="hidden" name="isSystemRoot" id = "isSystemRoot" value="${SuperConstant_YES}" />
            </c:if>
            <tr>
            <th>备注：</th>
            <td colspan="3"><textarea name="description" id="description"  style="height:100px; width:80%" class="easyui-validatebox txtbox" data-options=validType:['length[0,200]',"isNotEmpty"]>${resource.description}</textarea></td>
            </tr>
	    </table>
	</form>
</body>
</html>


