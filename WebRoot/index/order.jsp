<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>

</head>
<body>

<div class="wrap">

<s:action name="header" executeResult="true"/>

<div class="main">
    <div class="content">
    	<div class="login_panel"  style="width:600px" align="center">	
        	<s:actionmessage/>
        	
        	<s:if test="indentList != null">
        	
				<h3>我的订单</h3>
        		<s:iterator value="indentList">
        		
					<table class="cart_table">
					
						<tr>
		          			<td>
			          			订单号: <s:property value="id"/>
			          			<s:if test="status==1"><span style="color:red">(未发货)</span></s:if>
			          			<s:if test="status==2">(已发货)</s:if>
		          			</td>
		          			<td colspan="2">下单时间: <s:date name="systime" format="yyyy-MM-dd HH:mm:ss"/></td>
				            <td class="cart_total"><span class="red">总价: </span></td>
				            <td>￥<s:property value="total"/></td>
		          		</tr>
		          		
						<s:iterator value="itemList">
							<tr class="cart_title">
								<td>
									<a href="detail.action?productid=${product.id}">
										<img src="../${product.cover}" class="cart_thumb" border="0"/>
									</a>
								</td>
								<td>${product.name}</td>
								<td>￥${product.price}</td>
								<td>x ${amount}</td>
								<td>￥${total}</td>
							</tr>			
						</s:iterator>
						
		          		
		        	</table><br>
	        	
	        	</s:iterator>
        	
            </s:if>
            
            <div class="clear"></div>
		</div>
       <div class="clear"></div>
    </div>
 </div>

<jsp:include page="footer.jsp"/>

</div>

</body>
</html>