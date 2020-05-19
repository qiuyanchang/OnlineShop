<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style type="text/css">
*{ margin:0px; padding:0px;}
/* div{text-align: center}
#one{height:100px;margin-top: 20px;}
#two{height:120px;margin-top: 20px;}
#two img{}
div span{margin-left: 150px;padding-right: 20px} */	
.timer-simple-seconds {font-size:30px; color:red;padding-left:10px;}
#dingdan {font-size:30px; color:red;padding-left:10px;}
.tishi{color:red;}
#two{height:180px;margin-top: 20px;}
#two ul li{float:left;list-style-type: none}
#two ul li:nth-child(2){color:blue;margin-left: 50px;
		width:120px;}
#two ul li:nth-child(3){margin-left: 80px;}
#two ul li:nth-child(4){margin-left: 80px;}
#one{height:60px;margin-top: 20px;}
#one span:nth-child(2){margin-left: 50px;
		padding-top: 20px;width:40px;text-align: center}

</style>


<script type="text/javascript">
$(function(){
	//若订单未支付
	if(${tbOrder.status}==1){ 
    //对所有的计时器进行处理
    var timers=$(".timer-simple-seconds");
    for(var i=0;i<timers.length;i++){
        var timer=$(timers[i]);
       
        if(timer.attr("timestamp")){
            //如果是时间戳，则预处理一下时间为倒计时秒数
            prepareProcessTimestamp2Timer(timer);
        }else if(timer.attr("datetime")){
            //处理时间格式为倒计时秒数
            prepareProcessDatetime2Timer(timer);
        }
        //先调用一次，避免误差
        processTimer(timer);
        setInterval(processTimer,1000,timer);  
    }
    /**
     * doWhat: 这个函数将时间戳预处理成统一的倒计时描述
     * 
     * 对时间做一个预处理，因为如果服务器直接返回剩余的描述的话从服务器相应到客户端虽然短到几百毫秒但总是会有偏差的，这样子不太好
     * 所以服务器只需要设置一个时间戳表示到哪里停止就可以了
     */
    function prepareProcessTimestamp2Timer(timer){
        var total=parseInt(timer.attr("timestamp"));
        total=Math.round(total/1000);
        var now=new Date().getTime()/1000;
        timer.attr("timer",total-now);
    }
    /**
     * 将日期时间格式转为倒计时格式
     */
    function prepareProcessDatetime2Timer(timer){
        var timestamp=new Date(timer.attr("datetime")).getTime();
        timer.attr("timestamp",timestamp);
        prepareProcessTimestamp2Timer(timer);
    }
    /**
     * 倒计时，滴答滴答...
     * @param {Object} timer
     */
    function processTimer(timer){
        var total=parseInt(timer.attr("timer"));
        var t=total;
        
        //倒计时不能为负
        if(total<0) return; //TODO 后续版本加上计时完毕可以回调函数
      //找到显示时间的元素
        var day=timer.find(".day");
        var hour=timer.find(".hour");
        var minute=timer.find(".minute");
        var second=timer.find(".second");
        //刷新计时器显示的值
        if(day.length){
            var d=Math.floor(t/(60*60*24));
            day.text(d);
            t-=d*(60*60*24);
        }
        if(hour.length){
            var h=Math.floor(t/(60*60));
            hour.text((h<10?"0":"")+h);
            t-=h*(60*60);
        }
        if(minute.length){
            var m=Math.floor(t/60);
            minute.text((m<10?"0":"")+m);
            t-=m*60;
        }
        if(second.length){
            second.text((t<10?"0":"")+t);
        }
        
        //一秒过去了...
        total--;
        timer.attr("timer",total);
    }
	}
	//若订单已支付
	if(${tbOrder.status}==2){
			$("#dingdan").html("交易状态:待发货");
		}
	if(${tbOrder.status}==3){
		$("#dingdan").html("交易状态:待发货");
	}
	if(${tbOrder.status}==4){
		$("#dingdan").html("交易状态:配送中");
	}
	if(${tbOrder.status}==5){
		$("#dingdan").html("交易状态:交易成功");
	}
});  

    
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单详情</title>
<div id="dingdan">
<h3 class="tishi">订单还剩有效时间：</h3>
	<div class="timer-simple-seconds" timer="${allTime }">
            <span class="day">0</span>天
            <span class="hour">00</span>时
            <span class="minute">00</span>分
            <span class="second">00</span>秒
	</div>
</div>

  <div id="one" class="product">
  	
    <%-- <span>${tbOrder.createTime }</span> --%>
    <span>订单编号：${tbOrder.orderId }</span>
  	<span id="time">
	当前时间：
	<script>
	document.getElementById('time').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());
	setInterval("document.getElementById('time').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000)
	</script>
  </span>
	   
  </div>
 <c:forEach items="${list }" var="s">
  <div id="two">
  	<ul>
    <li><img src="${s.picPath }" wide="100px" height="100px"></li>
    <li><a href="#">${s.title }</a></li>
    <li>${s.num }(份)</li>
    <li>￥${s.price }</li>
    </ul>
  </div>
</c:forEach> 

<div >
</div>
<div id="three">
  <div>收货人姓名:${tbOrderShipping.receiverName }</div>
  <div>收获地址：${tbOrderShipping.receiverAddress }</div>
  <div>买家电话：${tbOrderShipping.receiverMobile }</div>
</div>
<h3><a href="http://localhost:8055">返回首页</a></h3>  





