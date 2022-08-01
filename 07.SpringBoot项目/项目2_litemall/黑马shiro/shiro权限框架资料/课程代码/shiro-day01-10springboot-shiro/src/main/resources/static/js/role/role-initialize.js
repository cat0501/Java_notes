/**
 * 加载资源列表
 */
$(function(){
	$('#resList').datagrid({
		toolbar:"#toolButton",
		striped: true,
		fit: true,
		pagination:true,
		url: content+"/role/list",
		idField:'id',
		singleSelect:false,
		pageNumber:1,
		pageSize:30,   
		fitColumns:true,
		nowrap:true,
		loadMsg:'数据加载中,请稍后……',
		
		frozenColumns:[[
		{field:'ck',checkbox:true},
		{title:'主键ID',field:'id',width:60,align:'center',sortable:true,hidden:true}     
		]],
		columns:[[
			{title:'名称',field:"roleName",width:80,editor:false,align:"none"},
			{title:'标识',field:"label",width:200,editor:false,align:"none"},
			{title:'描述',field:"description",width:180,editor:false,align:"none"},
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


