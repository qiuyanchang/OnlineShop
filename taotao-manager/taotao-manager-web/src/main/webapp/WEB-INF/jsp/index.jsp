<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>淘淘商城后台管理系统</title>
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="css/taotao.css" />
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="echarts/echarts.js"></script>
<style type="text/css">
	.content {
		padding: 10px 10px 10px 10px;
	}
</style>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'菜单',split:true" style="width:180px;">
    	<ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
         	<li>
         		<span>商品管理</span>
         		<ul>
	         		<li data-options="attributes:{'url':'item-add'}">新增商品</li>
	         		<li data-options="attributes:{'url':'item-list'}">查询商品</li>
	         		<li data-options="attributes:{'url':'item-param-list'}">规格参数</li>
	         	</ul>
         	</li>
         	<li>
         		<span>网站内容管理</span>
         		<ul>
	         		<li data-options="attributes:{'url':'content-category'}">内容分类管理</li>
	         		<li data-options="attributes:{'url':'content'}">内容管理</li>
	         	</ul>
         	</li>
         	<!--  <li>
         		<span>订单管理</span>
         		<ul>
	         		<li data-options="attributes:{'url':'sh'}">查询订单</li>	         	    			         		
	         		
	         	</ul>
         	</li>
         	-->
         </ul>
    </div>
    <div data-options="region:'center',title:''">
    	<div id="tabs" class="easyui-tabs">
		    <div title="首页" style="padding:20px;">
		        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
     			<div id="main" style="height:350px; width:600px;"></div> 	
		    </div>
		</div>
    </div>
    
<script type="text/javascript">
$(function(){
	$('#menu').tree({
		onClick: function(node){
			if($('#menu').tree("isLeaf",node.target)){
				var tabs = $("#tabs");
				var tab = tabs.tabs("getTab",node.text);
				if(tab){
					tabs.tabs("select",node.text);
				}else{
					tabs.tabs('add',{
					    title:node.text,
					    href: node.attributes.url,
					    closable:true,
					    bodyCls:"content"
					});
				}
			}
		}
	});
	
	 $(function(){  //加载
		   //发送异步请求获取数据
		   $.post("getMoney",null,function(data){
			   //alert(data[0].money);
			   //alert(data[0]["月份"]);
			   //获得标题
			   var title=[];
			   var info=[];
			   for(var i=0;i<data.length;i++){
				 // title[i]=data[i].name;
				   info.push(data[i].money);
				   //alert(title);
				   title.push(data[i].month);
				   //alert(info);
				   
			   }
			   writePhoto(title,info);
		   },"json");
	  });
	
	
	
});
	//输出图表
	  //function writePhoto(datatitle,datainfo){
		function writePhoto(title,info){
		  // 路径配置
		     require.config({
		         paths: {
		             echarts: 'echarts'
		         }
		     });
//路径配置
require.config({
    paths: {
        echarts: 'echarts'
    }
});

//使用echart
require(
    [
        'echarts',
        'echarts/chart/line' // 使用折线图就加载bar模块，按需加载
    ],
    function (ec) {
     //1.获得图表对象
     var myechart=ec.init(document.getElementById("main"));

   var  option = {
		    title : {
		        text: '2018年每月营业额',
		        //subtext1: '单位/￥<br>未显示的月份营业额为零',
		        subtext: '未显示的月份营业额为零'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['营业额']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [           //x轴
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : title 
		            //data : ['周一','周二','周三','周四','周五','周六','周日']
		            //data : ['01','02','03','04','05','06','07','08','09','10','11','12']
		            
		        }
		    ],
		    yAxis : [			//y轴
		        {
		            type : 'value',
		            axisLabel : {
		                formatter: '{value} 元'
		            }
		        }
		    ],
		    series : [
		        {
		            name:'当月营业额',
		            type:'line',
		            //data:${list}.get("营业额"),
		           //data:[11, 11, 15, 13, 12, 13, 10],
		         	data:info,
		          
		             markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            }, 
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		    ]
		};
		                    
    		                    
    		          

     //2.设置图表参数
     myechart.setOption(option);	
 });
 
} 

</script>
</body>
</html>