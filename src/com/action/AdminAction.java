package com.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.entity.Admin;
import com.entity.Category;
import com.entity.Indent;
import com.entity.Items;
import com.entity.Product;
import com.entity.ProductNew;
import com.entity.ProductSale;
import com.entity.ProductShow;
import com.entity.Users;
import com.service.AdminService;
import com.service.CategoryService;
import com.service.IndentService;
import com.service.ProductService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.SafeUtil;
import com.util.UploadUtil;

@Namespace("/admin")
@Action("admin")
@Results({
	@Result(name="login",location="/admin/login.jsp"),
	@Result(name="main",location="/admin/main.jsp"),
	@Result(name="indent",location="/admin/pages/indent-list.jsp"),
	@Result(name="reindent",type="redirect",location="admin!indentList.action?status=${status}&page=${page}"),
	@Result(name="item",location="/admin/pages/item-list.jsp"),
	@Result(name="user",location="/admin/pages/user-list.jsp"),
	@Result(name="useradd",location="/admin/pages/user-add.jsp"),
	@Result(name="userreset",location="/admin/pages/user-reset.jsp"),
	@Result(name="userupdate",location="/admin/pages/user-update.jsp"),
	@Result(name="reuser",type="redirect",location="admin!userList.action?page=${page}"),
	@Result(name="product",location="/admin/pages/product-list.jsp"),
	@Result(name="productadd",location="/admin/pages/product-add.jsp"),
	@Result(name="productupdate",location="/admin/pages/product-update.jsp"),
	@Result(name="productaddshow",location="/admin/pages/product-addshow.jsp"),
	@Result(name="productaddsale",location="/admin/pages/product-addsale.jsp"),
	@Result(name="productaddnew",location="/admin/pages/product-addnew.jsp"),
	@Result(name="reproduct",type="redirect",location="admin!productList.action?status=${status}&page=${page}"),
	@Result(name="category",location="/admin/pages/category-list.jsp"),
	@Result(name="categoryupdate",location="/admin/pages/category-update.jsp"),
	@Result(name="recategory",type="redirect",location="admin!categoryList.action?page=${page}"),
	@Result(name="admin",location="/admin/pages/admin-list.jsp"),
	@Result(name="adminadd",location="/admin/pages/admin-add.jsp"),
	@Result(name="adminreset",location="/admin/pages/admin-reset.jsp"),
	@Result(name="readmin",type="redirect",location="admin!adminList.action?page=${page}"),
	@Result(name="stocklist",location="/admin/pages/stock-list.jsp"),
	@Result(name="restocklist",type="redirect",location="admin!stockList.action?page=${page}"),
	@Result(name="restockwarn",type="redirect",location="admin!stockWarn.action?page=${page}"),
})	
public class AdminAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static final int rows = 10;
	
	@Resource
	private AdminService adminService;
	@Resource
	private IndentService indentService;
	@Resource
	private UserService userService;
	@Resource
	private ProductService productService;
	@Resource
	private CategoryService categoryService;
	
	private List<Indent> indentList;
	private List<Items> itemList;
	private List<Users> userList;
	private List<Product> productList;
	private List<Category> categoryList;
	private List<Admin> adminList;
	
	private Users user;
	private Product product;
	private ProductShow productShow;
	private ProductSale productSale;
	private ProductNew productNew;
	private Category category;
	private Admin admin;
	private int status;
	private int id;
	
	private File photo;		//获取上传文件
    private String photoFileName;	//获取上传文件名称
    private String photoContentType;		//获取上传文件类型
	
	/**
	 * 管理员登录
	 * @return
	 */
	public String login() {
		if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
			getSession().put("admin", admin.getUsername());
			return "main";
		}
		addActionError("用户名或密码错误!");
		return "login";
	}
	
	
	/**
	 * 订单列表
	 * @return
	 */
	public String indentList(){
		indentList = indentService.getList(status, page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, indentService.getTotal(status), page, rows);
		return "indent";
	}
	
	/**
	 * 订单处理
	 * @return
	 */
	public String indentDispose(){
		indentService.dispose(id);
		return "reindent";
	}
	
	/**
	 * 订单删除
	 * @return
	 */
	public String indentDelete(){
		indentService.delete(id);
		return "reindent";
	}
	
	/**
	 * 订单项列表
	 * @return
	 */
	public String itemList(){
		itemList = indentService.getItemList(id, page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, indentService.getItemTotal(id), page, rows);
		return "item";
	}
	
	
	/**
	 * 顾客管理
	 * @return
	 */
	public String userList(){
		userList = userService.getList(page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, userService.getTotal(), page, rows);
		return "user";
	}
	
	/**
	 * 顾客添加
	 * @return
	 */
	public String userAdd(){
		if (userService.isExist(user.getUsername())) {
			addActionError("用户名已存在!");
			return "useradd";
		}
		 userService.add(user);
		 return "reuser";
	}
	
	/**
	 * 顾客密码重置页面
	 * @return
	 */
	public String userRe(){
		user = userService.get(id);
		return "userreset";
	}
	
	/**
	 * 顾客密码重置
	 * @return
	 */
	public String userReset(){
		String password = SafeUtil.encode(user.getPassword());
		user = userService.get(user.getId());
		user.setPassword(password);
		userService.update(user);
		return "reuser";
	}
	
	/**
	 * 顾客更新
	 * @return
	 */
	public String userUp(){
		user = userService.get(id);
		return "userupdate";
	}
	
	/**
	 * 顾客更新
	 * @return
	 */
	public String userUpdate(){
		userService.update(user);
		return "reuser";
	}
	
	/**
	 * 顾客删除
	 * @return
	 */
	public String userDelete(){
		userService.delete(id);
		return "reuser";
	}
	
	
	/**
	 * 产品列表
	 * @return
	 */
	public String productList(){
		productList = productService.getStatusList(status, page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, productService.getStatusTotal(status), page, rows);
		return "product";
	}
	
	/**
	 * 产品添加
	 * @return
	 */
	public String productA(){
		categoryList = categoryService.getList();
		return "productadd";
	}
	
	/**
	 * 产品添加
	 * @return
	 */
	public String productAdd(){
		product.setCover(UploadUtil.fileUpload(photo, photoFileName, "picture"));
		productService.add(product);
		return "reproduct";
	}
	
	/**
	 * 产品更新
	 * @return
	 */
	public String productUp(){
		categoryList = categoryService.getList();
		product = productService.get(id);
		return "productupdate";
	}
	
	/**
	 * 产品更新
	 * @return
	 */
	public String productUpdate(){
		if (photo != null) {
			product.setCover(UploadUtil.fileUpload(photo, photoFileName, "picture"));
		}
		productService.update(product);
		return "reproduct";
	}
	
	/**
	 * 产品删除
	 * @return
	 */
	public String productDelete(){
		productService.delete(product.getId());
		return "reproduct";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String productShowA(){
		product = productService.get(id);
		return "productaddshow";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String productShowAdd(){
		productService.add(productShow);
		return "reproduct";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String productShowDelete(){
		productService.delete(productService.getShow(id));
		return "reproduct";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String productSaleA(){
		product = productService.get(id);
		return "productaddsale";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String productSaleAdd(){
		productService.add(productSale);
		return "reproduct";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String productSaleDelete(){
		productService.delete(productService.getSale(id));
		return "reproduct";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String productNewA(){
		product = productService.get(id);
		return "productaddnew";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String productNewAdd(){
		productService.add(productNew);
		return "reproduct";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String productNewDelete(){
		productService.delete(productService.getNew(id));
		return "reproduct";
	}
	
	/**
	 * 库存列表
	 * @return
	 */
	public String stockList(){
		status = 1; 
		productList = productService.getProductList(page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, productService.getProductTotal(), page, rows);
		return "stocklist";
	}
	
	/**
	 * 库存预警(库存小于5)
	 * @return
	 */
	public String stockWarn(){
		status = 2; 
		productList = productService.getWarnList(page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, productService.getWarnTotal(), page, rows);
		return "stocklist";	
	}
	
	/**
	 * 库存更新
	 * @return
	 */
	public String stockUpdate(){
		productService.updateStock(product.getId(), product.getStock());
		return status==1 ? "restocklist" : "restockwarn";
	}
	
	/**
	 * 类目列表
	 * @return
	 */
	public String categoryList(){
		categoryList = categoryService.getList(page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, categoryService.getTotal(), page, rows);
		return "category";
	}
	
	/**
	 * 类目添加
	 * @return
	 */
	public String categoryAdd(){
		categoryService.add(category);
		return "recategory";
	}
	
	/**
	 * 类目更新
	 * @return
	 */
	public String categoryUp(){
		category = categoryService.get(id);
		return "categoryupdate";
	}
	
	/**
	 * 类目更新
	 * @return
	 */
	public String categoryUpdate(){
		categoryService.update(category);
		return "recategory";
	}
	
	/**
	 * 类目删除
	 * @return
	 */
	public String categoryDelete(){
		categoryService.delete(category);
		return "recategory";
	}
	
	
	/**
	 * 管理员列表
	 * @return
	 */
	public String adminList(){
		adminList = adminService.getList(page, rows);
		pageTool = PageUtil.getPageToolAdmin(servletRequest, adminService.getTotal(), page, rows);
		return "admin";
	}
	
	/**
	 * 管理员添加
	 * @return
	 */
	public String adminAdd(){
		if (adminService.isExist(admin.getUsername())) {
			addActionError("用户名已存在!");
			return "adminadd";
		}
		adminService.add(admin);
		return "readmin";
	}
	
	/**
	 * 重置密码页面
	 * @return
	 */
	public String adminRe(){
		admin = adminService.get(id);
		return "adminreset";
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	public String adminReset(){
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		adminService.update(admin);
		return "readmin";
	}
	
	/**
	 * 管理员删除
	 * @return
	 */
	public String adminDelete(){
		adminService.delete(admin);
		return "readmin";
	}
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Indent> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<Indent> indentList) {
		this.indentList = indentList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Items> getItemList() {
		return itemList;
	}

	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public ProductShow getProductShow() {
		return productShow;
	}

	public void setProductShow(ProductShow productShow) {
		this.productShow = productShow;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public ProductNew getProductNew() {
		return productNew;
	}

	public void setProductNew(ProductNew productNew) {
		this.productNew = productNew;
	}

}
