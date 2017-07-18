package com.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.entity.Indent;
import com.entity.Items;
import com.entity.Product;
import com.entity.Users;
import com.service.IndentService;
import com.service.ProductService;
import com.service.UserService;
import com.util.SafeUtil;

@Namespace("/index")
@Results({
	@Result(name="login",location="/index/login.jsp"),
	@Result(name="register",location="/index/register.jsp"),
	@Result(name="reindex",type="redirect",location="index.action"),
	@Result(name="cart",location="/index/cart.jsp"),
	@Result(name="order",location="/index/order.jsp"),
	@Result(name="my",location="/index/my.jsp"),
})
public class UserAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	private static final String INDENT_KEY = "indent";
	
	private Users user;
	private int productid;
	private List<Indent> indentList;
	
	@Resource
	private UserService userService;
	@Resource
	private IndentService indentService;
	@Resource
	private ProductService productService;

	
	/**
	 * 注册用户
	 * @return
	 */
	@Action("register")
	public String register(){
		if (user.getUsername().isEmpty()) {
			addActionMessage("用户名不能为空!");
			return "register";
		}else if(user.getPassword().isEmpty()){
			addActionMessage("密码不能为空!");
			return "register";
		}else if (userService.isExist(user.getUsername())) {
			addActionMessage("用户名已存在!");
			return "register";
		}else {
			userService.add(user);
			addActionError("注册成功, 请登录!");
			return "login";
		}
	}
	
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	@Action("login")
	public String login() {
		if(userService.checkUser(user.getUsername(), user.getPassword())){
			getSession().put("user", userService.get(user.getUsername()));
			return "reindex";
		} else {
			addActionError("用户名或密码错误!");
			return "login";
		}
	}

	/**
	 * 注销登录
	 * @return
	 */
	@Action("logout")
	public String logout() {
		getSession().remove("user");
		getSession().remove("indent");
		return "login";
	}
	
	/**
	 * 查看购物车
	 * @return
	 */
	@Action("cart")
	public String cart() {
		return "cart";
	}
	
	/**
	 * 购买
	 * @return
	 */
	@Action("buy")
	public void buy(){
		Product product = productService.get(productid);
		if (product .getStock() <= 0) { // 库存不足
			sendResponseMsg("empty");
			return;
		}
		Indent indent = (Indent) getSession().get(INDENT_KEY);
		if (indent==null) {
			getSession().put(INDENT_KEY, indentService.createIndent(product));
		}else {
			getSession().put(INDENT_KEY, indentService.addIndentItem(indent, product));
		}
		sendResponseMsg("ok");
	}
	
	/**
	 * 减少
	 */
	@Action("lessen")
	public void lessen(){
		Indent indent = (Indent) getSession().get(INDENT_KEY);
		if (indent != null) {
			getSession().put(INDENT_KEY, indentService.lessenIndentItem(indent, productService.get(productid)));
		}
		sendResponseMsg("ok");
	}
	
	/**
	 * 删除
	 */
	@Action("delete")
	public void delete(){
		Indent indent = (Indent) getSession().get(INDENT_KEY);
		if (indent != null) {
			getSession().put(INDENT_KEY, indentService.deleteIndentItem(indent, productService.get(productid)));
		}
		sendResponseMsg("ok");
	}
	
	
	/**
	 * 提交订单
	 * @return
	 */
	@Action("save")
	public String save(){
		Users user = (Users) getSession().get("user");
		if (user == null) {
			addActionError("请登录后提交订单!");
			return "login";
		}
		Indent indent = (Indent) getSession().get(INDENT_KEY);
		if (indent != null) {
			if (indent != null) {
				for(Items item : indent.getItemList()){ // 检测商品库存(防止库存不足)
					Product product = productService.get(item.getProduct().getId());
					if(item.getAmount() > product.getStock()){
						addActionMessage("商品 ["+product.getName()+"] 库存不足! 当前库存数量: "+product.getStock());
						return "cart";
					}
				}
			}
			Users u = userService.get(user.getId());
			indent.setUser(u);
			indentService.saveIndent(indent);	// 保存订单
			getSession().remove(INDENT_KEY);	// 清除购物车
			addActionMessage("订单提交成功!");
		}
		return "cart";
	}
	
	/**
	 * 查看订单
	 * @return
	 */
	@Action("order")
	public String order(){
		Users user = (Users) getSession().get("user");
		if (user == null) {
			addActionError("请登录后查看订单!");
			return "login";
		}
		indentList = indentService.getListByUserid(user.getId());
		if (indentList!=null && !indentList.isEmpty()) {
			for(Indent indent : indentList){
				indent.setItemList(indentService.getItemList(indent.getId(), 1, 100)); // 暂不分页
			}
		}
		return "order";
	}
	
	
	/**
	 * 个人信息
	 * @return
	 */
	@Action("my")
	public String my(){
		Users userLogin = (Users) getSession().get("user");
		if (userLogin == null) {
			addActionError("请先登录!");
			return "login";
		}
		if (user==null) {
			return "my";
		}
		Users u = userService.get(user.getId());
		if (user.getPassword()!=null && !user.getPassword().trim().isEmpty() 
				&& SafeUtil.encode(user.getPassword()).equals(u.getPassword())) {
			if (user.getPasswordNew()!=null && !user.getPasswordNew().trim().isEmpty()) {
				u.setPassword(SafeUtil.encode(user.getPasswordNew()));
			}
			u.setPhone(user.getPhone());
			u.setAddress(user.getAddress());
			userService.update(u);  // 更新数据库
			getSession().put("user", u); // 更新session
			addActionMessage("保存信息成功!");
		}else {
			addActionMessage("原密码错误!");
		}
		return "my";
	}
	
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public List<Indent> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<Indent> indentList) {
		this.indentList = indentList;
	}
	
}
