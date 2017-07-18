/**
 * 加入购物车
 */
function buy(productid){
	$.post("buy.action", {productid:productid}, function(data){
		if(data=="ok"){
			//alert("已加入购物车!");
			location.reload();
		}else if(data=="login"){
			alert("请登录后购买!");
			location.href="login.jsp";
		}else if(data=="empty"){
			alert("库存不足!");
			location.reload();
		}else{
			alert("请求失败!");
		}
	});
}
/**
 * 购物车减去
 */
function lessen(productid){
	$.post("lessen.action", {productid:productid}, function(data){
		if(data=="ok"){
			location.href="cart.action";
		}else if(data=="login"){
			alert("请登录后操作!");
			location.href="login.jsp";
		}else{
			alert("请求失败!");
		}
	});
}
/**
 * 购物车删除
 */
function deletes(productid){
	$.post("delete.action", {productid:productid}, function(data){
		if(data=="ok"){
			location.reload();
		}else if(data=="login"){
			alert("请登录后操作!");
			location.href="login.jsp";
		}else{
			alert("请求失败!");
		}
	});
}