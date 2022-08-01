<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台-角色管理</title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<%@ include file="/WEB-INF/layouts/formvalidator.jsp" %>
<%@ include file="/WEB-INF/layouts/ztree.jsp" %>
<script>
	function doSave(){
		var flag = true;
		var result= false;
		count();
		if($("input[name='hasResourceIds']").val()==""){
			$.messager.alert('提示','请给角色分配资源！');
			return result;
		}
	    flag = $("#roleForm").form('validate');
		if(flag){
			var param = $("#roleForm").serialize();
			var url = $("#roleForm").attr("action");
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
	
	var zTree;
	
	var zNodes;
	
	var setting = {
		view: {
				selectedMulti: false
			},
		check: {
			enable: true,
			chkboxType: { "Y" : "ps", "N" : "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onCheck: onCheck
		}
	};
	
	var clearFlag = false;
	
	function onCheck(e, treeId, treeNode) {
		if (clearFlag) {
			clearCheckedOldNodes();
		}
	}
	function clearCheckedOldNodes() {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTree.getChangeCheckedNodes();
		for (var i=0, l=nodes.length; i<l; i++) {
			nodes[i].checkedOld = nodes[i].checked;
		}
	}
	function count() {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		nocheckCount = zTree.getCheckedNodes(false).length,
		changeCount = zTree.getChangeCheckedNodes().length;
		var checkNodes = zTree.getCheckedNodes(true);
		var ids = "";
		for(var i=0;i<checkNodes.length;i++){
			if(checkNodes[i].id!=0){
				ids+= checkNodes[i].id+",";
			}
		}
		$("input[name='hasResourceIds']").val(ids);
	}
	
	$(document).ready(function(){
		$.ajax({
			type: "POST",
			url: content+"/resource/roleResourceTree?hasResourceIds=${role.hasResourceIds}",
			async: false,
			dataType:'json',
			success: function(list){
				zNodes = list;
			}
		});
		var t = $("#tree");
		$.fn.zTree.init(t, setting, zNodes);
	});
	
</script>
</head>			
<body>	
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',title:''" style="width:220px;height: 300px;">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                <ul id="tree" class="ztree"></ul>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',title:''" >			
    <form action="${ctx}/role/save" method="post" id="roleForm" name="roleForm" >
      <input type="hidden" name="hasResourceIds" value=""/>
      <input type="hidden" name="id" id = "id" value="${role.id}" />
      <table width="95%" border="0" cellspacing="0" cellpadding="0" class="add_tab" >
        <tr>   
          <th width="25%">角色名称：</th>
          <td><input name="roleName" id="roleName" type=" text" class=" easyui-validatebox txtbox" style="width:170px;"  data-options="required:true,validType:['length[0,20]','isNotEmpty','onlyLetterNumber']" value="${role.roleName}"/></td>
        </tr>
        <tr>
          <th width="25%">角色标志：</th>
          <td><input name="label" id="label" type="text" class="easyui-validatebox txtbox" style="width:170px;" data-options=required:true,validType:["Label['${ctx}/role/checkLabel','','${role.label}']"] value="${role.label}"/></td>
         </tr>
          <th width="25%">状态：</th>
          <td>
            <select id="enableFlag" name="enableFlag" class="easyui-combobox easyui-validatebox" data-options="editable:false,validType:['selectValueRequired']" style="width:170px;" >
              <option value="${SuperConstant_YES}"<c:if test="${role.enableFlag == SuperConstant_YES}">selected="selected"</c:if>>启用</option>
              <option value="${SuperConstant_NO}"<c:if test="${role.enableFlag == SuperConstant_NO}">selected="selected"</c:if>>禁用</option>
            </select>
          </td>
         </tr>
         <tr>
            <th width="25%">备注：</th>
            <td><textarea name="description" id="description"  style="height:100px;width:80%;" class="easyui-validatebox txtbox" data-options="validType:['length[0,100]','isNotEmpty']">${role.description}</textarea></td>
          </tr>
          <tr>
          <th width="25%">排序：</th>
          <td>
            <input name="sortNo" id="sortNo" type="text" class="easyui-validatebox txtbox" data-options=required:true,validType:["integer","isNotEmpty"] value="${role.sortNo}"/></td>
          </tr>
      </table>
    </form>
    </div>
</div>
</body>
</html>
