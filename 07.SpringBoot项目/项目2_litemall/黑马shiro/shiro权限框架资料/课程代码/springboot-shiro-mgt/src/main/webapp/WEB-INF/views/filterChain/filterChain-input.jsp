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
            flag = $("#filterChainForm").form('validate');
            if (flag) {
                var param = $("#filterChainForm").serialize();
                var url = $("#filterChainForm").attr("action");
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
<form action="${ctx}/filterChain/save" method="post" id="filterChainForm" name="filterChainForm" >
    <input type="hidden" name="id" id = "id" value="${filterChain.id}" />
    <br />
    <table width="95%" border="0" cellspacing="0" cellpadding="0" class="add_tab" >
        <tr>
            <th width="15%">过滤器描述：</th>
            <td width="35%"><input name="urlName" id="urlName" type="text"  class="easyui-validatebox txtbox"  value="${filterChain.urlName}"/></td>
        </tr>
        <tr>
            <th width="15%">路径：</th>
            <td width="35%"><input name="url" id="url" type="text"  class="easyui-validatebox txtbox" value="${filterChain.url}"/></td>
        </tr>
        <tr>
            <th width="15%">过滤器表达式：</th>
            <td width="35%"><input name="filterName" id="filterName" type="text"  class="easyui-validatebox txtbox" value="${filterChain.filterName}"/></td>
        </tr>

        <tr>
            <th width="15%">所需角色：</th>
            <td width="35%"><input name="roles" id="roles" type="text"  class="easyui-validatebox txtbox"  value="${filterChain.roles}"/></td>
        </tr>
        <tr>
            <th width="15%">所需权限：</th>
            <td width="35%"><input name="permissions" id="permissions" type="text"  class="easyui-validatebox txtbox"  value="${filterChain.permissions}"/></td>
        </tr>
        <tr>
            <th >状态：</th>
            <td >
                <select name="enableFlag" id="enableFlag" class="easyui-combobox"  data-options="editable:false">
                    <option value="${SuperConstant_YES}" <c:if test="${filterChain.enableFlag==SuperConstant_YES}">selected</c:if>>启用</option>
                    <option value="${SuperConstant_NO}"<c:if test="${filterChain.enableFlag==SuperConstant_NO}">selected</c:if>>停用</option>
                </select>
            </td>
        </tr>
        <tr>
            <th >排序：</th>
            <td >
                <input name="sortNo" id="sortNo" type="text" class="easyui-validatebox txtbox" data-options=required:true,validType:["integer"] value="<fmt:formatNumber pattern="0" value="${filterChain.sortNo}" />"/></td>
        </tr>
    </table>
</form>
</body>
</html>


