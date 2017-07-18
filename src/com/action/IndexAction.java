package com.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.entity.Category;
import com.entity.Product;
import com.entity.ProductNew;
import com.entity.ProductSale;
import com.entity.ProductShow;
import com.service.CategoryService;
import com.service.ProductService;
import com.util.PageUtil;

@Namespace("/index")
@Results({
	@Result(name="index",location="/index/index.jsp"),
	@Result(name="header",location="/index/header.jsp"),
	@Result(name="productList",location="/index/product_list.jsp"),
	@Result(name="productShow",location="/index/product_show.jsp"),
	@Result(name="productSale",location="/index/product_sale.jsp"),
	@Result(name="productNew",location="/index/product_new.jsp"),
	@Result(name="detail",location="/index/detail.jsp"),
})	
public class IndexAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final int rows = 16;

	private int productid;
	private int categoryid;
	private String search;
	
	private Product product;
	private Category category;
	
	private List<Product> productList;
	private List<ProductShow> showList;
	private List<ProductSale> saleList;
	private List<ProductNew> newList;
	private List<Category> categoryList;
	
	@Resource
	private ProductService productService;
	@Resource
	private CategoryService categoryService;
	

	/**
	 * 首页
	 * @return
	 */
	@Action("index")
	public String index(){
		showList = productService.getShowList(1, 8);
		saleList = productService.getSaleList(1, 4);
		newList = productService.getNewList(1, 4);
		return "index";
	}
	
	/**
	 * 头部信息
	 * @return
	 */
	@Action("header")
	public String header(){
		categoryList = categoryService.getList();
		showList = productService.getShowList(1, 4);
		return "header";
	}
	
	/**
	 * 商品列表
	 * @return
	 */
	@Action("productList")
	public String productList(){
		if (categoryid > 0) {
			category = categoryService.get(categoryid);
		}
		productList = productService.getCategoryList(categoryid, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, productService.getCategoryTotal(categoryid), page, rows);
		return "productList";
	}
	
	/**
	 * 精品推荐
	 * @return
	 */
	@Action("productShow")
	public String productShow(){
		showList = productService.getShowList(page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, productService.getShowTotal(), page, rows);
		return "productShow";
	}
	
	/**
	 * 优惠促销
	 * @return
	 */
	@Action("productSale")
	public String productSale(){
		saleList = productService.getSaleList(page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, productService.getSaleTotal(), page, rows);
		return "productSale";
	}
	
	/**
	 * 新品
	 * @return
	 */
	@Action("productNew")
	public String productNew(){
		newList = productService.getNewList(page,rows);
		pageTool = PageUtil.getPageTool(servletRequest, productService.getNewTotal(), page, rows);
		return "productNew";
	}
	
	/**
	 * 详情
	 * @return
	 */
	@Action("detail")
	public String detail(){
		product = productService.get(productid);
		categoryList = categoryService.getList();
		return "detail";
	}
	
	/**
	 * 搜索
	 * @return
	 */
	@Action("search")
	public String search() {
		if (search==null || search.isEmpty()) {
			return productList();
		}
		productList = productService.getSearchList(search, page, rows);
		pageTool = PageUtil.getPageTool(servletRequest, productService.getSearchTotal(search), page, rows);
		return "productList";
	}

	


	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<ProductShow> getShowList() {
		return showList;
	}

	public void setShowList(List<ProductShow> showList) {
		this.showList = showList;
	}

	public List<ProductSale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<ProductSale> saleList) {
		this.saleList = saleList;
	}

	public List<ProductNew> getNewList() {
		return newList;
	}

	public void setNewList(List<ProductNew> newList) {
		this.newList = newList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	
}
