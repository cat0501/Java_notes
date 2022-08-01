if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combogrid){
	$.fn.combogrid.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText,
		missingMessage: $.fn.datebox.defaults.missingMessage
	});
}
$.extend($.fn.validatebox.defaults.rules, {
	
		selectValueRequired: { 
			validator: function(value,param){ 		
				 if (value == "" || value.indexOf('请选择') >= 0) { 
				 	return false;
				 }else {
				 	return true;
				 }  
			}, 
			message: '该下拉框为必选项' 
		},


	    idcard : {// 验证身份证 
	        validator : function(value) { 
	            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value); 
	        }, 
	        message : '身份证号码格式不正确' 
	    },
	    isNotEmpty: {//输入内容前后空格提示
	        validator: function(value){
	            return $.trim(value).length == value.length;
	        },
	        message: '输入内容前后不允许有空格'
	    },
	    length:{validator:function(value,param){ 
	        var len=$.trim(value).length; 
	            return len>=param[0]&&len<=param[1]; 
	        }, 
	            message:"输入内容长度必须介于{0}和{1}之间." 
	        }, 
	    phone : {// 验证电话号码 
	        validator : function(value) { 
	            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
	        }, 
	        message : '格式不正确,请使用下面格式:020-88888888' 
	    }, 
	    mobile : {// 验证手机号码 
	        validator : function(value) { 
	            return /^(13|15|18)\d{9}$/i.test(value); 
	        }, 
	        message : '手机号码格式不正确' 
	    }, 
	    intOrFloat : {// 验证整数或小数 
	        validator : function(value) { 
	            return /^\d+(\.\d+)?$/i.test(value); 
	        }, 
	        message : '请输入数字，并确保格式正确' 
	    }, 
	    currency : {// 验证货币 
	        validator : function(value) { 
	            return /^\d+(\.\d+)?$/i.test(value); 
	        }, 
	        message : '货币格式不正确' 
	    }, 
	    qq : {// 验证QQ,从10000开始 
	        validator : function(value) { 
	            return /^[1-9]\d{4,9}$/i.test(value); 
	        }, 
	        message : 'QQ号码格式不正确' 
	    }, 
	    integer : {// 验证整数 
	        validator : function(value) { 
	            return /^[+]?[1-9]+\d*$/i.test(value)&value>0&value<9999999999; 
	        }, 
	        message : '请输入大于0小于9999999999的整数' 
	    }, 
	    age : {// 验证年龄
	        validator : function(value) { 
	            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value); 
	        }, 
	        message : '年龄必须是0到120之间的整数' 
	    }, 
	    
	    chinese : {// 验证中文 
	        validator : function(value) { 
	            return /^[\Α-\￥]+$/i.test(value); 
	        }, 
	        message : '请输入中文' 
	    }, 
	    english : {// 验证英语 
	        validator : function(value) { 
	            return /^[A-Za-z]+$/i.test(value); 
	        }, 
	        message : '请输入英文' 
	    }, 
	    unnormal : {// 验证是否包含空格和非法字符 
	        validator : function(value) { 
	            return /.+/i.test(value); 
	        }, 
	        message : '输入值不能为空和包含其他非法字符' 
	    }, 
	    checkLoginName : {// 验证用户名 
	        validator : function(value) { 
	            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value); 
	        }, 
	        message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）' 
	    },
	    
	    loginName: {
			validator: function (value, param) {
				var url = param[0];
				var loginName = param[1];
				var oldLoginName = param[2];
				var param = {loginName:value,"oldLoginName":oldLoginName};
				var result =$.ajax({ 
					type: "POST",  
				    url: url,    
				    data:param,      
					dataType: "json",   
				    async: false  
				}).responseText; 
				if(result =="pass"){
					return true;
				}else{
					$.fn.validatebox.defaults.rules.loginName.message = '已被注册，请重新输入！';
					return false;
				}
			},
			message: ''
		},
		
		Label: {
			validator: function (value, param) {
				var url = param[0];
				var loginName = param[1];
				var oldLoginName = param[2];
				var param = {label:value,"oldLabel":oldLoginName};
				var result =$.ajax({ 
					type: "POST",  
				    url: url,    
				    data:param,      
					dataType: "json",   
				    async: false  
				}).responseText; 
				if(result =="pass"){
					return true;
				}else{
					$.fn.validatebox.defaults.rules.Label.message = '已被使用，请重新输入！';
					return false;
				}
			},
			message: ''
		},
		
		
	    faxno : {// 验证传真 
	        validator : function(value) { 
//	            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value); 
	            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
	        }, 
	        message : '传真号码不正确' 
	    }, 
	    zip : {// 验证邮政编码 
	        validator : function(value) { 
	            return /^[1-9]\d{5}$/i.test(value); 
	        }, 
	        message : '邮政编码格式不正确' 
	    }, 
	    ip : {// 验证IP地址 
	        validator : function(value) { 
	            return /d+.d+.d+.d+/i.test(value); 
	        }, 
	        message : 'IP地址格式不正确' 
	    }, 
	    name : {// 验证姓名，可以是中文或英文 
	            validator : function(value) { 
	                return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value); 
	            }, 
	            message : '请输入姓名' 
	    },
	    date : {// 验证姓名，可以是中文或英文 
	        validator : function(value) { 
	         //格式yyyy-MM-dd或yyyy-M-d
	            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value); 
	        },
	        message : '清输入合适的日期格式'
	    },
	    msn:{ 
	        validator : function(value){ 
	        return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
	    }, 
	    message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)' 
	    },
	    same:{ 
	        validator : function(value, param){ 
	            if($("#"+param[0]).val() != "" && value != ""){ 
	                return $("#"+param[0]).val() == value; 
	            }else{ 
	                return true; 
	            } 
	        }, 
	        message : '两次输入的密码不一致！'    
	    },
	    imageType:{
	        validator: function (value) {
	            return /\.BMP$|\.GIF$|\.JPG$|\.JPEG$|\.ICO$|\.PNG$/.test(value.toUpperCase());
	        },
	        message: '请选择(BMP|GIF|JPG|JPEG|ICO|PNG)等格式的图片'
	    },
	    proportionalCost:{
	        validator: function (value) {
	            return /^(0(\.\d{1,4})?|1(\.0{1,4})?)$/.test(value);
	        },
	        message: '费用比必须0到1之间的四位小数'
	    },
	    
	});
