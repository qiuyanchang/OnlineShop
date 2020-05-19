package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.pojo.TbOrderItem;
import com.taotao.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	/**
	 * 根据id查询实体
	 */
	@Override
	public TbOrderItem selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tbOrderItemMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<TbOrderItem> selectByOrderId(String orderId) {
		
		return tbOrderItemMapper.selectByOrderId(orderId);
	}

}
