package com.taotao.mapper;


import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbOrderMapper {
	
	int countByExample(TbOrderExample example);

    int deleteByExample(TbOrderExample example);
    //按主键删除订单
    int deleteByPrimaryKey(String orderId);

    int insert(TbOrder record);

    int insertSelective(TbOrder record);
    //按排序条件查询
    List<TbOrder> selectByExample(TbOrderExample example);

    TbOrder selectByPrimaryKey(String orderId);
    //按条件修改
    int updateByExampleSelective(@Param("record") TbOrder record, @Param("example") TbOrderExample example);
    
    int updateByExample(@Param("record") TbOrder record, @Param("example") TbOrderExample example);
    
    int updateByPrimaryKeySelective(TbOrder record);
    
    int updateByPrimaryKey(TbOrder record);
    //通过主键修改订单状态 和发货时间
    int update1(Date consignTime,String orderId); 
    //通过主键查询单个订单实体
    TbOrder  getOrderByOrderId(String orderId);
    //批量通过主键修改订单状态
    int update1More(Date consignTime,String[] orderIds); 
    //查询 所有订单
    List<TbOrder> getAllOrder();
    //按支付状态查询所有订单
    List<TbOrder> getOrderByStatus(int status);
    //查询每月营业额
    List<Map<String,Object>> getMoney(); 
    
}