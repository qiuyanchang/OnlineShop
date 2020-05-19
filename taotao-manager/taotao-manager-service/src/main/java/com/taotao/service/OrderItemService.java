package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TbOrderItem;

public interface OrderItemService {
	 TbOrderItem selectByPrimaryKey(String id);
	 
	//通过订单id查询商品
	List<TbOrderItem> selectByOrderId(String orderId);

}
