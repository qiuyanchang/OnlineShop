package com.taotao.service;

import com.taotao.pojo.TbOrderShipping;

public interface OrderShippingService {
	//添加订单物流 
    int insert(TbOrderShipping record);
    //根据订单id查实体
    TbOrderShipping selectByPrimaryKey(String orderId);

}
