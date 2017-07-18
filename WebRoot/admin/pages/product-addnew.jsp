<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	
	<form action="admin!productNewAdd.action" method="post">

		<input type="hidden" name="status" value="${status}"/>
		<input type="hidden" name="page" value="${page}"/>
		
		<input type="hidden" name="productNew.product.id" value="${product.id}"/>
		
		最新商品: <a href="../index/detail.action?productid=${product.id}" target="_blank">${product.name}</a><br>
		商品价格: ${product.price}<br>
		商品简介: ${product.intro}
		
		<input type="submit" value="提交"/>
		
	</form>
	
</body>
</html>
