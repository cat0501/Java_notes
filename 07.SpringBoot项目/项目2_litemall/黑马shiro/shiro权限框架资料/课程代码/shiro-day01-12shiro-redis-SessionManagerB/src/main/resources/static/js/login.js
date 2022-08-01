
$(document).keydown(function(e){
    var currKey=e.keyCode||e.which||e.charCode;
    if(currKey==13){
        if ( e && e.preventDefault ){
           //阻止默认浏览器动作(W3C)
           e.preventDefault();
        }else{
           //IE中阻止函数器默认动作的方式
           window.event.returnValue = false;
        }
        login.doLogin();
    }
 });

$(function(){
	login.changePicture();
});


var login = {
	/**
	 * 重新更换验证码
	 */
	changePicture:function (){
		$.ajax({
			type: "post",
			url:ctx+"/login/picture",
			dataType : 'json',
			success: function(date){
				var key = date.key;
				var picture = date.picture;
				$("#randompicture").attr("src","data:image/png;base64,"+picture);
				$("#key").val(key);
		   }
		});
		
	},
	/**
	 * 登陆系统
	 */
	doLogin:function(){
		$("#loginForm").submit();
	}
}