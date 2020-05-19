package com.taotao.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbOrderMapper;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.service.OrderService;
@Service()
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper tbOrderMapper;

	@Override
	public int update1(Date consignTime,String orderId) {
		
		return tbOrderMapper.update1(consignTime,orderId);
	}
	/**
	 * 按支付状态查询所有订单
	 */
	@Override
	public List<TbOrder> getOrderByStatus(int status) {
		
		return tbOrderMapper.getOrderByStatus(status);
	}

	@Override
	public int addOrder(TbOrder tbOrder) {
		// TODO Auto-generated method stub
		return tbOrderMapper.insert(tbOrder);
	}

	@Override
	public List<TbOrder> getAllOrder() {
		List<TbOrder> list=tbOrderMapper.getAllOrder();		
		return list;
	}
	/**
	 * 通过主键查找订单实体
	 */
	@Override
	public TbOrder getOrderByOrderId(String orderId) {
	TbOrder tbOrder=tbOrderMapper.getOrderByOrderId(orderId);
		return tbOrder;
	}

	@Override
	public int update1More(Date consignTime,String[] orderIds) {
		 		
		int temp=tbOrderMapper.update1More(consignTime,orderIds);
		System.out.println(temp);
		return temp;
	}
	@Override
	public int deleteByPrimaryKey(String orderId) {
		
		return tbOrderMapper.deleteByPrimaryKey(orderId);
	}
	@Override
	public List<Map<String,Object>> getMoney() {
		
		return tbOrderMapper.getMoney();
	}
	
	

	

}
