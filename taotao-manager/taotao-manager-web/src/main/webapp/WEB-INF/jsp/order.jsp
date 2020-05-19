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
		
		//全选  反选
		
		$("#quanxuan").on('click',function(){
			$("input[name='xuan']").prop("checked",$(this).prop("checked")).parent().parent();
			});
		$("#more").click(function(){
			var t = new Date().format("yyyy-MM-dd hh:mm:ss");		
			var doms = $(":checkbox:checked");
			var len = doms.length;		
			if (len == 0) {
				alert("你还没有选择订单，请选中订单。");
			} else {
				//获取选中项的id
				var str = "";
				doms.each(function(i, obj) {
					str = str + obj.value + ",";
				});
				str = str.substring(0, str.length - 1);
				$.post("fahuoMore",{"ids":str},function(data){						
					if(data[0].res==2) 						
					{alert("该订单已发货！");
					$("#s").val("已修改");
					} 
					if (data[0].res==0)
					{alert("发货失败！")};
					if (data[0].res==1)
					{alert("成功处理"+data[1].sum+"个订单！");
					doms.each(function(i, obj) {						
				$(obj).parent().next().next().next().next().next().next().next().html(t);
					});
											 
					} 
					
				},"json")
				}
			
		})
		
		
		
	}) 	
</script>





<form id="form1" name="form1" method="post" action="cxStatus">
  订单状态：   
  <select name="status" id="select">
  	<option >请选择订单状态</option>
    <option value="1">未付款</option>
    <option value="2">已付款</option>
  </select>
  <input type="submit" name="keyword" value="查询"/>
</form>
<form id="form1" name="form1" method="post" action="cxOrderId">
  订单编号：   
  <input type="text" name="orderId" id="keyId" placeholder="请输入订单编号"/>
  <input type="submit" value="查询" id="byId"/>
</form>
<table  title="订单列表" border="1" >  
        <tr> <td><input type="checkbox" id="quanxuan"/>全选</td>     	     	                     
            <td>订单编号</td> <td>实付金额</td> <td>邮费</td> <td>订单状态</td>                             
            <td>创建日期</td><td>支付日期</td><td>发货时间</td><td>用户id</td>           
        	<td>发货</td>
        </tr>
     <c:forEach items="${page.list}" var="s">
    	 <tr>
    	 	<td><input type="checkbox" name="xuan" value="${s.orderId }"/></td>
    	 	<td><a href="getItemListByorderId?orderId=${s.orderId }"> ${s.orderId }</a></td>
    	 	<td>${s.payment }</td>
    	 	<td>${s.postFee }</td>
    	 	<td>${s.status }</td>
    	 	<td><fmt:formatDate value="${s.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    	 	<td><fmt:formatDate value="${s.paymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>   	    	 	
    	 	<td><fmt:formatDate value="${s.consignTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>	 	
    	 	<td>${s.userId }</td>   	 	
    	 	<td><button value='发货' class='fahuo'>发货</button>
    	 	<input value=${s.orderId } type="hidden"/></td> 
    	 	  	 	
    	 </tr>
     </c:forEach>      
</table>
<a href="sh?pageNum=${page.firstPage }">首页</a> 
<a href="sh?pageNum &gt;=2?${page.prePage }:${page.firstPage }">上一页</a>
<a href="sh?pageNum &lt;=${page.pages }?${page.pages }:${page.nextPage }" >下一页 </a>
<a href="sh?pageNum=${page.pages }"> 尾页</a> 
  



