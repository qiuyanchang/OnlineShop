package com.taotao.portal.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbOrder;
import com.taotao.portal.service.MyOrdersService;


/**
 * 调用服务层，查询内容
 * 
 * @author pkq
 *
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {


	@Override
	public String getOrders() {
		// 调用服务层服务
		String result = HttpClientUtil.doGet("http://localhost:8081/rest/order");
		System.out.println("1111111");
		System.out.println(result);
		// 把字符串转换成TaotaoResult
		TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbOrder.class);
		System.out.println(taotaoResult);
		try {
			// 取内容列表
			/*ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(result);


			List<TbOrder> list = objectMapper.readValue(jsonNode.get("extend").traverse(),
					// new TypeReference<List<TbOrder>>() {}
					objectMapper.getTypeFactory().constructCollectionType(List.class, TbOrder.class));
			System.out.println(list.size());*/
			
			 List<TbOrder> list=(List<TbOrder>) taotaoResult.getData();
			System.out.println("3333333");
		    Map<String, Object> map1=new HashMap<String,Object>();
			List resultList = new ArrayList();
			System.out.println(list);
			// 创建一个jsp页码要求的pojo列表
			for (TbOrder tbOrder : list) {
				Map map = new HashMap();
				map.put("payment", tbOrder.getPayment());
				map.put("paymentType", tbOrder.getPaymentType());
				map.put("status", tbOrder.getStatus());
				map.put("endTime", tbOrder.getEndTime());
				map.put("shippingCode", tbOrder.getShippingCode());
				resultList.add(map);
			}
			System.out.println("999h9l99");
		    map1.put("list", resultList);
		    //"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"total":6,"pages":2,
		    map1.put("pageNum", taotaoResult.getInfoByNodeName("pageNum", Integer.class));
		
			return JsonUtils.objectToJson(map1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}