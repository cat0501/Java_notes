<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台-资源列表</title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<script type="text/javascript" src="${ctx}/static/js/resource/resource-initialize.js"></script>
<script type="text/javascript">
</script>
</head>
<body> 
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',split:true,title:'查询条件'"  style="height:90px;padding:5px; border-width: 0 0 1px 0;">
        <form name="searchForm" id ="searchForm" action="" method="post">
        <input id="parentId" name="parentId"  type="hidden" value=""/>
	    <table width="100%" border="0" cellspacing="5" cellpadding="0">
		  <tr>
			<td width="60" class="right_">资源名称</td>
			<td width="130"><input name="resourceName" class="txtbox" type="text" style="width:120px"/></td>
			<td width="60" class="right_">资源标识</td>
			<td width="130"><input name="label" class="txtbox"  type="text" style="width:120px"/></td>
			<td width="60" class="right_">资源url</td>
			<td width="130"><input name="requestPath" class="txtbox"  type="text" style="width:120px"/></td>
			<td width="10" >&nbsp;
	         </td>
			<td width="150">
				<a  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('resList',true,true)">查 询</a>&nbsp;&nbsp;
	        	<a  href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-reload'" onclick="doReload('${ctx}/resource/listInitialize')">重 置</a>
			</td>
			<td width="*">&nbsp;</td>
		  </tr>
		</table>
        </form>
	</div>
	<div data-options="region:'west',title:''" style="width:220px;height: 300px;">
	 	<iframe id="listTree" width="100%" height="95%" allowtransparency="true"  frameborder="0" scrolling="auto" src="${ctx}/resource/listTree"></iframe>
    </div>
	<div data-options="region:'center',title:''" >
	 <table id="resList"></table>
    </div>
  
    <div id="toolButton" style="display: none;">
		<a href="#" id="addbtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addDataInfoRefresh('resList','资源添加','${ctx}/resource/input',$(document).width()*0.7,$(document).height()*0.85,true,true,true)">新增</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="updateDataInfoRefresh('resList','资源修改','${ctx}/resource/input',$(document).width()*0.7,$(document).height()*0.85,true,true)">修改/查看</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick='delDataInfoRefresh("resList","${ctx}/resource/delete",true,true)'>删除</a>
	</div>
</div>
</body>
</html>