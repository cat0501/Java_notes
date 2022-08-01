<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/layouts/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星心芯平台-用户登录</title>
<%@ include file="/WEB-INF/layouts/meta.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<link rel="stylesheet" type="text/css"  href="${ctx}/static/styles/login.css" />
<script type="text/javascript" src="${ctx}/static/js/login.js"></script>
<script >
</script>
</head>
<body>
<form id="loginForm" name="loginForm"  action="${ctx}/login/usersLongin" method="post">
<div class="login_main">
  <div style="float:right;">
    <table width="322" border="0" align="" cellpadding="0" cellspacing="0" style="margin-right:90px;margin-top: -60px;">
      <tr>
        <td width="65" height="40" class="l_font1">用户名：</td>
        <td width="257"><input type="text" id="loginName" name="loginName" class="txtbox" style="height:20px; line-height:20px;width: 160px;"  value="${loginName}"/></td>
      </tr>
      <tr>
        <td height="40" class="l_font1">密　码：</td>
        <td><input type="password" id ="passWord" name="passWord" class=" txtbox" value="${passWord}" style="line-height:20px;width: 160px;" /></td>
      </tr>
      <tr>      
        <td width="65" height="40" class="l_font1"></td>
        <td width="257">        
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td colspan="3" style="color: red;">${shiroLoginFailure}</td>
            </tr>
          </table>       
        </td>       
      </tr>
      <tr>
        <td colspan="2">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" >
            <tr>            
              <td width="40" height="40" align="left">              
               <input type="button"  name="登录" onclick="login.doLogin()"  value=""  class="input1" />                           
              </td>           
              
              <td width="60%" align="left" >
              <input  type="reset"  name="重置"  value=""  class="input2" />             
              </td>              
            </tr>
          </table>
          </td>
      </tr>
    </table>
  </div>
</div>
</form>
</body>
</html>


