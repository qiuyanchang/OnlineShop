package com.taotao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.service.ItemService;
import com.taotao.service.OrderService;
import com.taotao.service.OrderShippingService;


@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderShippingService orderShippingService;
	@Autowired
	private ItemService itemService;
	/**
	 * 分页显示所有的订单
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/sh")	
	public String showAllOrder(Integer pageNum,Model model) {		
		PageHelper.startPage(pageNum==null?1:pageNum, 5);
		List<TbOrder> list1=orderService.getAllOrder();
		for (TbOrder tbOrder : list1) {
			Date staTime=tbOrder.getCreateTime();
			String orderId=tbOrder.getOrderId();
			//Date nowTime=new Date();
			//得到当前时间  精确到毫秒
			Calendar cal = Calendar.getInstance(); 
			Date nowTime = cal.getTime();
			
			long dif = nowTime.getTime()-staTime.getTime();
		 	long day= dif /(24*60*60*1000);
	        long hour=( dif /(60*60*1000)-day*24);
	        long min=(( dif /(60*1000))-day*24*60-hour*60);
	        long allMIN = hour*24+min;
	        long allTime=day*24*60*60*1000+hour*60*60*1000+min*60*1000+allMIN;
	        int time=new Long(allTime).intValue(); 
	        System.out.println("时间差"+allTime);
	        int s = tbOrder.getStatus();
	        
	        
	        if( s == 1){
	        //若超出12小时 未支付，则删除订单
	        if(time>12*3600*1000) {
	        System.out.println("符合删除条件");
	         int sx=orderService.deleteByPrimaryKey(orderId);
	         System.out.println("执行了删除");
	        }
	         List<TbOrder> list=orderService.getAllOrder();
			 PageInfo<TbOrder> page=new PageInfo<>(list);
			 model.addAttribute("page", page);
	        }else {	        
	   		 PageInfo<TbOrder> page=new PageInfo<>(list1);
	   		model.addAttribute("page", page);
	        }	       
		}			 	
		return "order";
	}
	
	/**
	 * 点击发货后修改状态
	 * @throws ParseException 
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/fahuo")
	public void updateStatus1(String orderId,TbOrderShipping record,HttpServletResponse response) throws ParseException {	
		PrintWriter out;
		int temp=-1;
		int ap;
		int ar;
		Date consignTime;
		List<TbOrder> list=null;
		try {
			out = response.getWriter();		
			TbOrder tbOrder=orderService.getOrderByOrderId(orderId);			
			int s=tbOrder.getStatus();
			if(s==1) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date=new Date();
				String dateStr = sdf.format(date);
				consignTime = sdf.parse(dateStr);  			
			//System.out.println(consignTime);
			 //1.修改订单表(发货时间和订单状态)
			 ar=orderService.update1(consignTime,orderId);
			 //System.out.println("订单表已修改");
			 //2.添加物流信息
			 ap=orderShippingService.insert(record);
			 //System.out.println("物流信息已经添加");
			 //3.重新查询订单表
			 list=orderService.getAllOrder();
			// System.out.println("重新加载过了");
			 temp=1;			 
			}
			//else {temp=0;}				
			if(s!=1) {temp=2;}
			out.println("{\"res\":"+temp+"}");			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		 			
	}
	@Transactional
	@ResponseBody
	@RequestMapping("/fahuoMore")
	public void updateStatus1More(String[] ids,TbOrderShipping record,HttpServletResponse response) {
		
		PrintWriter out;
		int temp=0;
		int ap;
		int ar;
		int sum=0;
		Date ConsignTime;
		try {			
			out= response.getWriter();
			//遍历订单id数组
			for (String orderId : ids) {
				//设置物流信息表的订单id值
				record.setOrderId(orderId);
				out = response.getWriter();	
				System.out.println(orderId);
				//根据订单id查询订单实体
				TbOrder tbOrder=orderService.getOrderByOrderId(orderId);			
				int s=tbOrder.getStatus(); //取订单的状态值
				System.out.println(s);
				if(s==1) { //判断订单是否已发货
				ConsignTime=new Date();
				 ap=orderShippingService.insert(record);
				 ar=orderService.update1(ConsignTime,orderId);	
				 temp=1;
				 sum=sum+1;
				}else {temp=0;}	
				if(s!=1) {temp=2;}						
			}	
			System.out.println(sum);
			out.println("[{\"res\":"+temp+"},{\"sum\":"+sum+"}]");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	/**
	 * 按支付状态查询订单
	 */
	@RequestMapping("/cxStatus")
	public String chaxvnByClass(Model model,int status,Integer pageNum ) {
		PageHelper.startPage(pageNum==null?1:pageNum, 5);
		
		List<TbOrder> list=orderService.getOrderByStatus(status);
		
		 PageInfo<TbOrder> page=new PageInfo<>(list);
		model.addAttribute("page", page);
		return "order";	
	}
	/**
	 * 按订单编号查询单个订单
	 * @throws IOException 
	 */
	
	@RequestMapping("/cxOrderId")
	public String chaxvnByKey(Model model,String orderId) throws IOException {
			
		//根据订单编号查询订单实体(商品id)
		TbOrder tbOrder=orderService.getOrderByOrderId(orderId);
		model.addAttribute("tbOrder", tbOrder);			
		return "singleOrder";
		
	}
 
}
