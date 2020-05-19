package com.taotao.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	EUDataGridResult getItemList(int page,int rows);
	TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception;
	//删除
	TaotaoResult deleteItem(@RequestParam("ids") long itemId,TbItem item);
	public TaotaoResult updateItem(TbItem item, String desc,Long itemParamId, String itemParams);
	
}
