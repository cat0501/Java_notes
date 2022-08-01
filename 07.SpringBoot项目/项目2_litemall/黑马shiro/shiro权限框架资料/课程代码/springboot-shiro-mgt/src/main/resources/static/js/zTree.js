var parentIdUrl;

var childUrl;

$(document).ready(function() {
	parentIdUrl = $("#parentIdUrl").attr("action");
	childUrl = $("#childUrl").attr("action");
	if(childUrl==""||childUrl==null){
		setting.async.enable=false;
	}
	$.ajax({
		type : "POST",
		url : parentIdUrl,
		async : false,
		dataType : 'json',
		success : function(list) {
			zNodes = list;
		}
	});
	$.fn.zTree.init($("#tree"), setting, zNodes);
});

var setting = {	
	async : {
		enable : true,
		url : getUrl
	},
	
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pId",
			rootPId : null
		}
	},
	view : {
		dblClickExpand : false,
		showLine : true,
		selectedMulti : false,
		expandSpeed : "fast"
	},
	callback : {
		beforeClick : function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			$("#parentId", window.parent.document).val(treeNode.id);
			parent.doSearch("resList", false, false);
			return true;
		},
		beforeExpand : beforeExpand,
		onAsyncSuccess : onAsyncSuccess
	}
};

var zNodes = [];

function onAsyncSuccess(event, treeId, treeNode, msg) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
	if (eval(msg).length == 0) {

		treeNode.icon = content
				+ "/static/jquery/jqueryApi/zTree/css/zTreeStyle/img/diy/2.png";
		treeNode.isParent = false;
		zTree.updateNode(treeNode);

		return;
	}

	zTree.updateNode(treeNode);
	zTree.selectNode(treeNode.children[0]);
}

function beforeExpand(treeId, treeNode) {
	if (!treeNode.isAjaxing) {
		ajaxGetNodes(treeNode, "refresh");
		return true;
	} else {
		alert("zTree 正在下载数据中，请稍后展开节点。。。");
		return false;
	}
}

function getUrl(treeId, treeNode) {
	var param = "?parentId=" + treeNode.id;
	return childUrl + param;
}

function ajaxGetNodes(treeNode, reloadType) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
	if (reloadType == "refresh") {
		zTree.updateNode(treeNode);
	}
	zTree.reAsyncChildNodes(treeNode, reloadType, true);
}
