<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台-首页</title>
<link type="text/css"  rel="stylesheet"  href="${ctx}/static/styles/home.css"  />
<script type="text/javasctipt">
</script>
<style>
	a:hover{ text-decoration:underline;color:blue; } 	
	</style>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding:4px; ">
  <tr>
    <td width="246" valign="top"><div class="message" id="xtxx">
        <div class="message_nav">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="30" height="30" align="center" valign="middle"><img src="${ctx}/static/images/qs_tb1.png" width="20" height="19" /></td>
              <td class="title_font">会议通知</td>
              <td width="50" align="center" class="more_font"><a href='javascript:void(0)' onclick='sysInfo("adddate","会议通知","${ctx}/framework/daily/meeting!seelist.action",$(document).width()*0.95,$(document).height()*1)'>更多&gt;&gt;</a></td>
            </tr>
          </table>
        </div>
         <div class="tzgg_main">
          <ul>
            <s:iterator value="meetingList" id="s">
             <li>
              <div  class="hui_font tzgg_width zuo"><a href='javascript:void(0)' onclick='sysInfo("adddate","会议通知","${ctx}/framework/daily/meeting!see.action?id=${s.id}",$(document).width()*0.75,$(document).height()*1)'>${s.name}</a></div>
              <div class="time_font you"><fmt:formatDate value="${s.startTime}" pattern="yyyy-MM-dd"/> </div>
             </li>
            </s:iterator>
          </ul>
        </div>
      </div>
      <div class="message" id="bwl">
        <div class="message_nav">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="30" height="30" align="center" valign="middle"><img src="${ctx}/static/images/qs_tb2.png" width="15" height="15" /></td>
              <td class="title_font">备忘录</td>
              <td width="50" align="center" class="more_font"><a href='javascript:void(0)' onclick='sysInfo("adddate","备忘录","${ctx}/framework/daily/memoire!list.action?type=SYS",$(document).width()*0.85,$(document).height()*1)'>更多&gt;&gt;</a></td>
            </tr>
          </table>
        </div>
        <div class="message_main">
          <ul>
            <s:iterator value="memoireList" var="s">
            <li><a href='javascript:sysInfo("view","备忘录","${ctx}/framework/daily/memoire!input.action?id=${s.id}",$(document).width()*0.75,$(document).height()*0.8)'>${s.name }</a></li>
            </s:iterator>
          </ul>
        </div>
      </div></td>
    <td valign="top" width="*" style="padding-left:15px; padding-right:15px;"><table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:solid 1px #8db3e2; margin-bottom:10px;">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left" valign="middle" class="message_nav"><table width="130" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="22" height="30" align="center" valign="middle"><img src="${ctx}/static/images/qs_tb3.png" width="16" height="16" /></td>
                      <td width="72" class="title_font"><a href="javascript:reload()" style="color: #15428b">待办事项</a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td  valign="top" style="height:244px; overflow:hidden;">
	                <table id="checkRecordList" width="100%" border="0" cellspacing="0" cellpadding="0">
	                
	                </table>
                 </td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:solid 1px #8db3e2; height:278px; overflow:hidden;">
        <tr>
          <td>
          	<table id="checkRecordDetailList"  width="100%" border="0" cellspacing="0" cellpadding="0">
          	
            </table>
          </td>
        </tr>
      </table></td>
    <td width="246" valign="top"><div class="message" id="tzgg">
        <div class="message_nav">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="30" height="30" align="center" valign="middle"><img src="${ctx}/static/images/qs_tb4.png" width="14" height="14" /></td>
              <td class="title_font">通知公告</td>
              <td width="50" align="center" class="more_font"><a href='javascript:void(0)' onclick='sysInfo("adddate","通知公告","${ctx}/framework/daily/notice!list.action?type=NOTSYS",$(document).width()*0.85,$(document).height()*1)'>更多&gt;&gt;</a></td>
            </tr>
          </table>
        </div>
        <div class="tzgg_main">
          <ul>
            <s:iterator value="noticeList" id="s">
             <li>
              <div  class="hui_font tzgg_width zuo"><a href='javascript:void(0)' onclick='sysInfo("adddate","通知公告","${ctx}/framework/daily/notice!input.action?id=${s.id}",$(document).width()*0.75,$(document).height()*0.8)'>${s.name}</a></div>
              <div class="time_font you"><fmt:formatDate value="${s.sentTime }" pattern="yyyy-MM-dd"/> </div>
             </li>
            </s:iterator>
          </ul>
        </div>
      </div>
      <div class="message" id="gzt">
        <div class="message_nav">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="30" height="30" align="center" valign="middle"><img src="${ctx}/static/images/qs_tb5.png" width="16" height="16" /></td>
              <td class="title_font">工作台</td>
            </tr>
          </table>
        </div>
        <div class="tzgg_main">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="45%" height="60" align="right"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat; ">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb6.png" width="16" height="16" /></td>
                    <td width="85" align="left" class="hui_font02">填工作日志</td>
                  </tr>
                </table></td>
              <td width="10%"></td>
              <td width="45%" align="left"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat;">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb7.png" width="16" height="16" /></td>
                    <td width="85" align="left" class="hui_font02">请假申请</td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td height="50" align="right"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat;">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb8.png" width="16" height="15" /></td>
                    <td width="85" align="left" class="hui_font02">出差申请</td>
                  </tr>
                </table></td>
              <td></td>
              <td align="left"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat;">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb9.png" width="17" height="16" /></td>
                    <td width="85" align="left" class="hui_font02">报销申请</td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td height="50" align="right"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat;">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb4.png" width="16" height="15" /></td>
                    <td width="85" align="left" class="hui_font"><a href='javascript:void(0)' style="color: black" onclick='addNotice("adddate","通知公告新增","${ctx}/framework/daily/notice!input.action?deptId=${deptId}",$(document).width()*0.75,$(document).height()*0.8)' style="color: black">通知公告 </a></td>
                   </tr>
                </table></td>
              <td></td>
              <td align="left"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat;">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb11.png" width="16" height="15" /></td>
                    <td width="85" align="left" class="hui_font02"><a href='javascript:clickDeptTask("addData","我的任务新增","${ctx}/framework/synthesis/dept-task!input.action",$(document).width()*0.8,$(document).height()*0.9)'>部门协同 </a></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
            <td height="50" align="right"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat;">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb2.png" width="16" height="15" /></td>
                    <td width="85" align="left" class="hui_font"><a href='javascript:void(0)' style="color: black" onclick='addDataInfoNoClose("addData","备忘录新增","${ctx}/framework/daily/memoire!input.action",$(document).width()*0.75,$(document).height()*0.8)' >备忘录</a></td>
                  </tr>
                </table></td>
              <td></td>
              <td align="left"><table width="107" border="0" cellspacing="0" cellpadding="0" style="background:url(${ctx}/WEB-INF/static/images/bottton_bj.png) no-repeat;">
                  <tr>
                    <td width="35" height="35" align="center"><img src="${ctx}/static/images/qs_tb1.png" width="16" height="15" /></td>
                    <td width="85" align="left" class="hui_font "><a href='javascript:void(0)' style="color: black" onclick='add("adddate","会议信息新增","${ctx}/framework/daily/meeting!input.action",$(document).width()*0.85,$(document).height()*0.9)' style="color: black">发起会议 </a></td>
                   </tr>
                </table></td>
            </tr>
          </table>
        </div>
      </div></td>
  </tr>
</table>
</body>
</html>

  