<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
</head>
<body>

<div class="wrap">

<s:action name="header" executeResult="true"/>

<div class="main">
    <div class="content">
    	<div class="login_panel"  style="width:600px">	
        	<s:actionmessage/>
        	
			<h3>${sessionScope.user.username}</h3>
			
			<form action="my.action" method="post">
       			<input type="hidden" name="user.id" value="${sessionScope.user.id}"/>
       			
				<table class="cart_table">
				
					<tr>
						<td>电话: <input type="text" name="user.phone" value="${sessionScope.user.phone}" style="width:80%"></td>
					</tr>			
					<tr>
						<td>地址: <input type="text" name="user.address" value="${sessionScope.user.address}" style="width:80%"></td>
					</tr>
					<tr>
						<td>原密码: <input type="text" name="user.password" style="width:80%" placeholder="必填"></td>
					</tr>		
					<tr>
						<td>新密码: <input type="text" name="user.passwordNew" style="width:80%" placeholder="选填"></td>
					</tr>		
		          		
		        </table><br>
		        	
		         <div class="buttons">
	            	<div style="float:right; padding-top: 20px">
	            		<button class="grey">提交保存</button>
	            	</div>
	            </div>
            
	        </form>
	        
            <div class="clear"></div>
            
		</div>
		
       <div class="clear"></div>
       
    </div>
    
 </div>

<jsp:include page="footer.jsp"/>

</div>

</body>
</html>