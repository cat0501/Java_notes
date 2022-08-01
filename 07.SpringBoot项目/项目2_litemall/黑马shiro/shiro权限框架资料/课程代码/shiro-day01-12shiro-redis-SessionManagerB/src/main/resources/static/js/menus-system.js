/**
 * 解决弹出框按钮非中文
 */
$(function(){
	$.extend($.messager.defaults,{
		ok:"确定",
		cancel:"取消"
	});
}); 

/**修改数据，刷新列表
 * @param {Object} id		弹出框名称
 * @param {Object} title	弹出框标题
 * @param {Object} url		调用url
 * @param {Object} width	宽度
 * @param {Object} height	高度
 */
function setPersonnelInfo(id,title,url,winWidth,winHeight){

	var d =$.dialog({
	    id:id,
	    autoPos:{left:'center',top:'center'}, 
	    title:title,
	    width: winWidth,
	    height:winHeight,
	    lock: true,
	    content:'url:'+url,
	    ok: function(){
	    	var iframe = this.iframe.contentWindow;
	    	var validFlag = iframe.doSave();//调用子页面函数
	    	if(validFlag){
		    	$.dialog.tips('操作成功!');
	    		d.close();
	    	}else{
	    		$.dialog.tips('操作失败!');
	    	}
			return false;
	    },
	    cancelVal: '关闭',
	    cancel: true 
	});
}