<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	
	<form action="admin!productUpdate.action" method="post" enctype="multipart/form-data">

		<input type="hidden" name="page" value="${param.page}"/>
		
		<input type="hidden" name="product.id" value="${product.id}"/>
		<input type="hidden" name="product.cover" value="${product.cover}"/>

		封面：<img src="../${product.cover}" width="100px" height="150px">
		<br>
		修改: <input type="file" name="photo" size="12"/><br>
		书名：<input type="text" name="product.name" value="${product.name}"/><br>
		价格：<input type="text" name="product.price" value="${product.price}"/><br>
		介绍：<input type="text" name="product.intro" value="${product.intro}"/><br>
		库存：<input type="text" name="product.stock" value="${product.stock}"/><br>
		分类：<select name="product.category.id">
						<s:iterator value="categoryList">
							<option value="<s:property value="id"/>" <s:if test="product.category.id==id">selected="selected"</s:if>><s:property value="name"/></option>
						</s:iterator>
				   </select>
		
		<input type="submit" value="修改"/>
		
	</form>
	
</body>
</html>
