<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<form action="admin!productAdd.action" method="post" enctype="multipart/form-data">

		封面：<input type="file" name="photo" size="12"/><br>
		名称：<input type="text" name="product.name"/><br>
		价格：<input type="text" name="product.price"/><br>
		介绍：<input type="text" name="product.intro"/><br>
		库存：<input type="text" name="product.stock"/><br>
		分类：<select name="product.category.id">
						<s:iterator value="categoryList">
							<option value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator>
				   </select>
		<input type="submit" value="添加"/>
		
	</form><s:actionerror/>
	
</body>
</html>
