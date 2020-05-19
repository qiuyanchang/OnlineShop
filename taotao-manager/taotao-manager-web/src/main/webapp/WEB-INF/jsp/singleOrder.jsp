<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript">

$(function(){   //加载事件		
		 $(".fahuo").click(function(){												 
			 var id=$(this).next().val();
			 var td=$(this).parent().prev().prev().prev().prev().prev().prev();
			 var pay=$(this).parent().prev().prev().prev();
			  $(this).parent().parent().css("background","pink");
			$.post("fahuo",{"orderId":id},function(data){ 				
				var mydate = new Date();
				var t=mydate.toLocaleString();	
			 if(data.res==1)
				{alert("发货成功!");} 
			 $(td).html("2");
			 $(pay).html(t);
			$("#div").html("异步刷新");
			if(data.res==2) 
			{alert("该订单已发货！");} 			
			if (data.res==0)
			{alert("发货失败！")};
			},"json")		
		})
})
</script>

<table  title="订单列表" border="1" >  
        <tr>      	     	                     
            <td>订单编号</td> <td>实付金额</td> <td>邮费</td> <td>订单状态</td>                             
            <td>创建日期</td><td>支付日期</td><td>发货时间</td><td>用户id</td>           
        	<td>发货</td>
        </tr>   
    	 <tr>
    	 	
    	 	<td><a href="getItemListByorderId?orderId=${tbOrder.orderId }"> ${tbOrder.orderId }</a></td>
    	 	<td>${tbOrder.payment }</td>
    	 	<td>${tbOrder.postFee }</td>
    	 	<td>${tbOrder.status }</td>
    	 	<td><fmt:formatDate value="${tbOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    	 	<td><fmt:formatDate value="${tbOrder.paymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>   	    	 	
    	 	<td><fmt:formatDate value="${tbOrder.consignTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>	 	
    	 	<td>${tbOrder.userId }</td>   	 	
    	 	<td><button value='发货' class='fahuo'>发货</button>
    	 	<input value="${tbOrder.orderId }" type="hidden"/></td>    	 	  	 	
    	 </tr>    
</table>

 <h3><a href="http://localhost:8055">返回首页</a></h3> 



