package com.taotao.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.service.OrderItemService;
import com.taotao.service.OrderService;
import com.taotao.service.OrderShippingService;

@Controller
public class OrderItemController {
	/**
	 * 根据订单号查询购买商品信息
	 */
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderShippingService orderShippingService;
	
	@RequestMapping("/getItemListByorderId")
	public String getItemList(String orderId,Model model) {
		System.out.println("订单id"+orderId);
		
		//根据订单编号得到购买商品
		List<TbOrderItem> list=orderItemService.selectByOrderId(orderId);
		//根据订单编号得到 订单创建时间
		TbOrder tbOrder=orderService.getOrderByOrderId(orderId);
	
		 //得到交易有效剩余时间
		 Date staTime=tbOrder.getCreateTime();
		 Date nowTime=new Date();
		 long dif = staTime.getTime()+86400*1000-nowTime.getTime();
		 	long day= dif /(24*60*60*1000);
	        long hour=( dif /(60*60*1000)-day*24);
	        long min=(( dif /(60*1000))-day*24*60-hour*60);
	        long allMIN = hour*24+min;
	        long allTime=(day*24*60*60*1000+hour*60*60*1000+min*60*1000+allMIN)/1000;
	      //根据订单id查询物流信息实体
		TbOrderShipping tbOrderShipping=orderShippingService.selectByPrimaryKey(orderId);
	        
		model.addAttribute("tbOrderShipping", tbOrderShipping);	 //返回物流信息     
	    model.addAttribute("list", list);
	    model.addAttribute("tbOrder", tbOrder);	//返回订单
	    model.addAttribute("allTime", allTime);  //返回结束时间
		return "order-info";
		
	}

}
