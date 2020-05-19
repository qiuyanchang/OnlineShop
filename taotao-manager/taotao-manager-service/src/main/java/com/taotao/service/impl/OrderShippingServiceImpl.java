package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.service.OrderShippingService;
@Service
public class OrderShippingServiceImpl implements OrderShippingService {
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;

	@Override
	public int insert(TbOrderShipping record) {
		int temp=tbOrderShippingMapper.insert(record);
		return temp;
	}
	/**
	 * 根据订单id查询物流信息实体
	 */
	@Override
	public TbOrderShipping selectByPrimaryKey(String orderId) {
		
		return tbOrderShippingMapper.selectByPrimaryKey(orderId);
	}
	
	

}
