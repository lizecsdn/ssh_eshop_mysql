package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IndentDao;
import com.entity.Indent;
import com.entity.Items;
import com.entity.Product;

@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class IndentService {

	@Resource
	private IndentDao indentDao;
	@Resource
	private ProductService productService;
	
	
	/**
	 * 创建订单
	 * @param product
	 * @return
	 */
	public Indent createIndent(Product product) {
		List<Items> itemList = new ArrayList<Items>();
		itemList.add(createItems(product));
		Indent indent = new Indent();
		indent.setItemList(itemList);
		indent.setTotal(product.getPrice());
		indent.setAmount(1);
		return indent;
	}
	
	/**
	 * 创建订单项
	 * @param product
	 * @return
	 */
	private Items createItems(Product product) {
		Items item = new Items();
		item.setProduct(product);
		item.setAmount(1);
		item.setPrice(product.getPrice());
		item.setTotal(product.getPrice());
		return item;
	}

	/**
	 * 向订单添加项目
	 * @param indent
	 * @param product
	 * @return
	 */
	public Indent addIndentItem(Indent indent, Product product) {
		List<Items> itemList = indent.getItemList();
		itemList = itemList==null ? new ArrayList<Items>() : itemList;
		// 如果购物车已有此项目, 数量+1
		boolean notThis = true;
		for (Items item : itemList) {
			if (item.getProduct().getId() == product.getId()) {
				item.setAmount(item.getAmount() + 1);
				item.setTotal(product.getPrice() * item.getAmount());
				notThis = false;
			}
		}
		// 如果当前购物车没有此项目, 创建新条目
		if (notThis) {
			itemList.add(createItems(product));
		}
		indent.setTotal(indent.getTotal() + product.getPrice());
		indent.setAmount(indent.getAmount() + 1);
		return indent;
	}
	
	/**
	 * 从订单中减少项目
	 * @param indent
	 * @param product
	 * @return
	 */
	public Indent lessenIndentItem(Indent indent, Product product) {
		List<Items> itemList = indent.getItemList();
		itemList = itemList==null ? new ArrayList<Items>() : itemList;
		// 如果购物车已有此项目, 数量-1
		boolean noneThis = true;
		for (Items item : itemList) {
			if (item.getProduct().getId() == product.getId()) {
				if (item.getAmount() - 1 <= 0) { // 减少到0后删除
					return deleteIndentItem(indent, product);
				}
				item.setAmount(item.getAmount() - 1);
				item.setTotal(product.getPrice() * item.getAmount());
				noneThis = false;
			}
		}
		// 如果当前购物车没有项目, 直接返回
		if (noneThis) {
			return indent;
		}
		indent.setTotal(indent.getTotal() - product.getPrice());
		indent.setAmount(indent.getAmount() - 1);
		return indent;
	}
	
	/**
	 * 从订单中删除项目
	 * @param indent
	 * @param product
	 * @return
	 */
	public Indent deleteIndentItem(Indent indent, Product product) {
		List<Items> itemList = indent.getItemList();
		itemList = itemList==null ? new ArrayList<Items>() : itemList;
		// 如果购物车已有此项目, 数量清零
		boolean noneThis = true;
		int itemAmount = 0;
		List<Items> resultList = new ArrayList<Items>();
		for (Items item : itemList) {
			if (item.getProduct().getId() == product.getId()) {
				itemAmount = item.getAmount();
				noneThis = false;
				continue;
			}
			resultList.add(item);
		}
		// 如果已经没有项目, 返回null
		if (resultList.isEmpty()) {
			return null;
		}
		indent.setItemList(resultList);
		// 如果当前购物车没有项目, 直接返回
		if (noneThis) {
			return indent;
		}
		indent.setTotal(indent.getTotal() - product.getPrice() * itemAmount);
		indent.setAmount(indent.getAmount() - itemAmount);
		return indent;
	}

	/**
	 * 保存订单
	 * @param indent
	 */
	public void saveIndent(Indent indent) {
		indent.setStatus(1);
		indent.setSystime(new Date());
		int indentId = indentDao.save(indent);
		for(Items item : indent.getItemList()){
			item.setIndent(indentDao.get(Indent.class, indentId));
			productService.lessenStock(item.getProduct().getId(), item.getAmount());
			indentDao.save(item);
		}
	}
	
	/**
	 * 获取订单列表
	 * @param page
	 * @param row
	 * @return
	 */
	public List<Indent> getList(int status, int page, int row) {
		return indentDao.getList(status, page, row);
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getTotal(int status) {
		return (int)indentDao.getTotal(status);
	}

	/**
	 * 处理订单
	 * @param id
	 * @return 
	 */
	public boolean dispose(int id) {
		Indent indent = indentDao.get(Indent.class, id);
		indent.setStatus(2);
		return indentDao.update(indent);
	}

	/**
	 * 删除订单
	 * @param id
	 */
	public boolean delete(int id) {
		Indent indent = new Indent();
		indent.setId(id);
		return indentDao.delete(indent);
	}

	/**
	 * 订单项列表
	 * @param indentid
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Items> getItemList(int indentid, int page, int rows) {
		List<Items> itemList = indentDao.getItemList(indentid, page, rows);
		if (itemList!=null && !itemList.isEmpty()) {
			for (Items item : itemList) {
				item.setTotal(item.getPrice() * item.getAmount());
			}
		}
		return itemList;
	}
	
	/**
	 * 订单项总数
	 * @return
	 */
	public int getItemTotal(int status) {
		return (int)indentDao.getItemTotal(status);
	}
	
	
	/**
	 * 获取某用户全部订单
	 * @param userid
	 */
	public List<Indent> getListByUserid(int userid) {
		return indentDao.getListByUserid(userid);
	}
	
}
