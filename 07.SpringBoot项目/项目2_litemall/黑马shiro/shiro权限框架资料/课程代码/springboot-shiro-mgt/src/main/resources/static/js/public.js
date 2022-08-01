var _menu;
var start = 0;
var menuTitle = new Array();
$(document).ready(function(){
	tabClose();
	tabCloseEven();
	$('#css3menu a').click(function() {
		$('#css3menu a').removeClass('active');
		$(this).addClass('active');
		var menuId = $(this).attr('name');
		$.ajax({
			type: "post",
			url:content+"/menus/findAllMenu",
			data: {id:menuId} ,
			dataType : 'json',
			success: function(list){
				_menu = list;
				Clearnav();
				addNav(list);
				InitLeftMenu();
		   }
		});
	addTab("首页",content+"/menus/home","icon-home");
	});
	
//导航菜单绑定初始化
$("#wnav").accordion( {
	animate : true
});
	var firstMenu = $('#css3menu a:first');
	firstMenu.click();
});

function Clearnav() {
	for(var i=0;i<menuTitle.length;i++){
		var p = $('#wnav').accordion('getPanel', menuTitle[i]);
		if(p){
			$('#wnav').accordion('remove', p.panel('options').title);
		}
	}
}

//添加导航信息
function addNav(data) {
	//情况菜单数组
	menuTitle.length = 0;
	$.each(data, function(i, sm) {
		var menulist = "";
		menulist += '<ul>';
		$.each(sm.menus, function(j, o) {
			menulist += '<li><div><a ref="' + o.menuid + '" href="#" rel="'+content+'/'
					+ o.url + '" ><span class="icon ' + o.icon
					+ '" >&nbsp;</span><span class="nav">' + o.menuname
					+ '</span></a></div></li> ';
		});
		menulist += '</ul>';
		$('#wnav').accordion('add', {
			title : sm.menuname,
			content : menulist,
			iconCls :sm.icon
		});
		menuTitle.push(sm.menuname);
	});
	var pp = $('#wnav').accordion('panels');
	if(pp.length > 0){
		var t = pp[0].panel('options').title;
		$('#wnav').accordion('select', t);
	}
}

// 初始化左侧
function InitLeftMenu() {
	hoverMenuItem();
	$('#wnav li a').bind('click', function() {
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(menuid);
		addTab(tabTitle, url, icon);
		$('#wnav li div').removeClass("selected");
		$(this).parent().addClass("selected");
	});
}

/**
 * 菜单项鼠标Hover
 */
function hoverMenuItem() {
	$(".easyui-accordion").find('a').hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});
}

// 获取左侧导航的图标
function getIcon(menuid) {
	var icon = 'icon ';
	$.each(_menu, function(i, o) {
		$.each(o.menus, function(k, m){
			if (m.menuid == menuid) {
				if(m.icon!="null"){
					icon += m.icon;
				}else{
					icon += "icon-nav";
				}
				return false;
			}
		});
	});
	return icon;
}

function addTab(subtitle, url, icon) {
	if (!$('#tabs').tabs('exists', subtitle)) {
		
		$('#tabs').tabs('add', {
			title : subtitle,
			content : createFrame(subtitle,url),
			closable : true,
			icon : icon
		});
		/**********选项卡打开数量超过6个关闭除欢迎页的第一个************/
		var tabnum = 0;
		var firstTitle = "";
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			if(i==2){
				firstTitle = t;
			}
			if(t!=""){
				tabnum+=1;
			}
		});
		if(tabnum > 6){
			$('#tabs').tabs('close', firstTitle);
		}
		/**********选项卡打开数量超过6个关闭除欢迎页的第一个************/
	} else {
		$('#tabs').tabs('select', subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(subtitle,url) {
	var s = '<iframe scrolling="auto" name="'+subtitle+'" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
		var subtitle = $(this).children(".tabs-closable").text();
		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		var subtitle = currTab.panel('options').title;
		$('#tabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(subtitle,url)
			}
		});
	});
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			// msgShow('系统提示','后边没有啦~~','error');
			//alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			//alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

/**
 * 退出登录
 * @return
 */
function doLogout(){
	$.messager.confirm('提示', '确定要退出系统吗?', function(confirmInfo){
		if (confirmInfo){
			window.top.location = content+"/login/usersLongout";
		}
	});
}
/**
 * 显示时分秒
 * @return
 */
function showtime(){
	today=new Date(); 
	var day=today.getDate();
	var hours = today.getHours(); 
	var minutes = today.getMinutes(); 
	var seconds = today.getSeconds(); 
	var timeValue= ((hours<10) ? "0" :"")+hours;//((hours >12) ? hours -12 :hours);
	timeValue += ((minutes < 10) ? ":0" : ":") + minutes+""; 
	//timeValue += (hours >= 12) ? "PM" : "AM"; 
	timeValue+=((seconds < 10) ? ":0" : ":") + seconds+"";
	$("#hour_minute").html(timeValue);
	setTimeout("showtime()",1000); //设置过1000毫秒就是1秒，调用showtime方法 
}

$(document).ready(function(){
	showtime();
});
