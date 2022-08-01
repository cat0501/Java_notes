/**
 * 加载资源列表
 */
$(function(){
	$('#resList').datagrid({
		toolbar:"#toolButton",
		striped: true,
		fit: true,
		pagination:true,
		url: content+"/user/list",
		idField:'id',
		singleSelect:false,
		pageNumber:1,
		pageSize:30,   
		fitColumns:true,
		nowrap:true,
		loadMsg:'数据加载中,请稍后……',
		
		frozenColumns:[[
		{field:'ck',checkbox:true},
		]],
		columns:[[
			{title:'名称',field:"realName",width:80,editor:false,align:"center"},
			{title:'登录名称',field:"loginName",width:80,editor:false,align:"center"},
			{title:'性别',field:"sex",width:80,editor:false,align:"center",
				formatter:function(val,rec){
				    if (val == '1'){
				    	return '男';
				    } else if(val == '0'){
				   	    return '女';
				    }else{
				    	return '';
				    }
    		}},
			{title:'邮箱',field:"email",width:80,editor:false,align:"center"},
			{title:'手机',field:"mobil",width:80,editor:false,align:"center"},
			{title:'排序',field:"sortNo",width:80,editor:false,align:"center"},
			{title:'状态',field:"enableFlag",width:80,editor:false,align:"center",
			formatter:function(val,rec){
				    if (val == 'YES'){
				    	return '启用';
				    } else if(val == 'NO'){
				   	    return '禁用';
				    }else{
				    	return '';
				    }
    		}}
		]],
			
		pagination:true,
		rownumbers:true,
		
		onBeforeEdit:function(index,row){
		row.editing = true;
		
		},
		onDblClickRow:function(index,row){
		    $('#resList').datagrid('beginEdit', index);
		},
		onClickRow:function(index,row){
		    $('#resList').datagrid('endEdit', index);
		}
	});
}); 


