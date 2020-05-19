package com.taotao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderShipping;


public interface OrderService {
	//根据主键修改订单
	 int update1(Date consignTime,String orderId); 
	 //通过主键查询单个订单实体
	 TbOrder  getOrderByOrderId(String orderId);	 
	//批量通过主键修改订单状态
	  int update1More(Date consignTime,String[] orderIds); 
	 
	 
	 //按支付状态查询所有订单
	  List<TbOrder> getOrderByStatus(int status);
	 //添加订单
	 int addOrder(TbOrder tbOrder);
	 List<TbOrder> getAllOrder();
	//按主键删除订单
	  int deleteByPrimaryKey(String orderId);
	//查询每月营业额
	  List<Map<String,Object>> getMoney(); 
}
