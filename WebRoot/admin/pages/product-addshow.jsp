<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	
	<form action="admin!productShowAdd.action" method="post">

		<input type="hidden" name="status" value="${status}"/>
		<input type="hidden" name="page" value="${page}"/>
		
		<input type="hidden" name="productShow.product.id" value="${product.id}"/>

		推荐商品: <a href="../index/detail.action?productid=${product.id}" target="_blank">${product.name}</a><br>
		商品价格: ${product.price}<br>
		商品简介: ${product.intro}<br>
		推荐理由: <input type="text" name="productShow.showtext" />
		
		<input type="submit" value="提交"/>
		
	</form>
	
</body>
</html>
