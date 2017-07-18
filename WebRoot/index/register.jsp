<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商城首页</title>
</head>
<body>

<div class="wrap">

<s:action name="header" executeResult="true"/>

<div class="main">
    <div class="content">
    	 <div class="login_panel">
    		<h3>注册新账户</h3>
    		<s:actionmessage style="color:red;"/>
    		<form action="register.action" method="post" id="form_register">
    		    <input name="user.username" type="text"  class="field" placeholder="用户名">
            	<input name="user.password" type="password" class="field" placeholder="密码">
    		    <input name="user.phone" type="text"  class="field" placeholder="电话" maxlength="11">
    		    <input name="user.address" type="text"  class="field" placeholder="地址">
		   	</form>
            <div class="buttons">
            	<div>
            		<button class="grey" onclick="$('#form_register').submit();">确认注册</button>
            		<a href="login.jsp" style="float:right; padding-top: 10px; padding-right: 50px; font-size: 14px">登录已有账户</a></div>
            </div>
            <div class="clear"></div>
    	</div>  
       <div class="clear"></div>
    </div>
 </div>

<jsp:include page="footer.jsp"/>

</div>

</body>
</html>