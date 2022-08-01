/**
 * 加载资源列表
 */
$(function(){
    $('#resList').datagrid({
        toolbar:"#toolButton",
        striped: true,
        fit: true,
        pagination:true,
        url: content+"/filterChain/list",
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
            {title:'过滤器描述',field:"urlName",width:80,editor:false,align:"center"},
            {title:'url',field:"url",width:80,editor:false,align:"center"},
            {title:'过滤器表达式',field:"filterName",width:80,editor:false,align:"center"},
            {title:'角色',field:"roles",width:80,editor:false,align:"center"},
            {title:'资源',field:"permissions",width:80,editor:false,align:"center"},
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


