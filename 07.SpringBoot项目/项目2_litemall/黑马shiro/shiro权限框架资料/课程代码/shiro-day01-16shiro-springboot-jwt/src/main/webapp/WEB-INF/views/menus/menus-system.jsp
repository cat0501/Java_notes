<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台系统</title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<script type="text/javascript" src="${ctx}/static/js/public.js"></script>
<script type="text/javascript" src="${ctx}/static/js/menus-system.js"></script>
</head>

<body class="easyui-layout" id="layoutbody">
<div data-options="region:'north',split:false,border:false" style="min-width: 1000px;overflow: hidden;">
	
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="nr-logo3">
		<tr>
			<td style="overflow:hidden;">

			<div class="newlogo_right" style="padding-right:15px;">
				<div style="width:auto;float:;">       
					<div class="top_tb05" style="padding-left:25px; float:left; padding-right:15px;color: #e6effe;font-size: 12px">登录用户：${currentUser.realName}</div>
					<div class="newblack12px top_tb01" style="padding-left:25px; float:left;"><a  href="javascript:addTab('首页','${ctx}/menus/home','icon-home');">首页</a></div>
					<div style="float: left; padding-left:12px; padding-top:3px;"><%--<img src="${ctx}/static/images/new_fengexian.png" width="2" height="16"/>--%></div>
					<div class="newblack12px top_tb02" style="padding-left:35px; float:left;"><a href="javascript:setPersonnelInfo('personnelInfo','修改密码','${ctx}/login/editorpassword',$(document).width()*0.3,$(document).height()*0.4);">密码设置</a></div>
					<div style="float: left;padding-left:12px;padding-top:3px;"><%--<img src="${ctx}/static/images/new_fengexian.png" width="2" height="16"/>--%></div>
					<div class="newblack12px top_tb03" style="padding-left:35px; float:left;"><a href="javascript:doLogout();">退出</a></div>
				</div>
				<div style="float:left;color:#c9e4ff; padding-top:20px;font-size: 12px">	
					<marquee onMouseOut="this.start()" onMouseOver="this.stop()" scrollamount="3" >
					<span>尊敬的${currentUser.realName}，欢迎您登录本系统！${today}</span>
					<span id="hour_minute">${hourMinute}</span>
					</marquee>
					<div style="clear:both;"></div>
				</div>
			</div>
			</td>
		</tr>
	</table>
	 
	<div class="newnav">
		<div style="float:left;background:#e0ecff;">
			<a href="#"><img src="${ctx}/static/images/nav_left.png" width="22" height="32" onmouseover="this.src='${ctx}/static/images/newnav_left_1.jpg'" onmouseout="this.src='${ctx}/static/images/nav_left.png'" /></a>
		</div>
		<div style="float:left;" class="list_h">
			<ul id="css3menu" style=" float:left; " >
				<c:forEach var="menu" items="${list}"> 
					<li><a name="${menu.id}"  href="javascript:void(0);"> ${menu.resourceName}</a></li>
                </c:forEach> 
			</ul>
		</div>
		<div style="float:right;background:#e0ecff;">
			<a href="#"><img src="${ctx}/static/images/nav_right.png" width="22" height="32" onmouseover="this.src='${ctx}/static/images/newnav_right_1.jpg'" onmouseout="this.src='${ctx}/static/images/nav_right.png'" /></a>
		</div>
	</div> 
</div>
	
<!--左侧导航start-->
<div data-options="region:'west',split:false" title="<div class='blod'>系统导航</div>" style="width:200px;" >
	<div id='wnav' class="easyui-accordion" data-options="fit:false,border:false"></div>
</div>
<!--左侧导航send--> 

	
<!--欢迎页start-->
<div id="mainPanle" data-options="region:'center',split:false" style="background: #eee; overflow-y:hidden; margin-left: 5px;">
<div id="tabs" class="easyui-tabs"  fit="true" border="false" >  
</div>
</div>
<!--欢迎页end-->
	
<div id="mm" class="easyui-menu" style="width:150px; display:none;">
	<div id="mm-tabupdate">刷新</div>
	<div class="menu-sep"></div>
	<div id="mm-tabclose">关闭</div>
	<div id="mm-tabcloseall">全部关闭</div>
	<div id="mm-tabcloseother">除此之外全部关闭</div>
	<div class="menu-sep"></div>
	<div id="mm-tabcloseright">当前页右侧全部关闭</div>
	<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	<div class="menu-sep"></div>
	<div id="mm-exit">退出</div>
</div>
</body>
</html>


