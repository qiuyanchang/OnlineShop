package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.StrUtil;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem tbItem=itemService.getItemById(itemId);
		return tbItem;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemById(Integer page,Integer rows){
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}
	 /**
     * 修改商品信息
     * 
     * @param item
     * @param desc
     * @return
     */
	@RequestMapping(value="/item/update", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateItem(TbItem item,  String desc,
             Long itemParamId, String itemParams) {
    
		System.out.println(item);
		System.out.println(desc);
		TaotaoResult result = this.itemService.updateItem(item, desc,itemParamId,itemParams);
        System.out.println(item);
        return result;
    }
	
	//删除商品
		@RequestMapping(value="/item/delete", method=RequestMethod.POST)
		@ResponseBody
		  private TaotaoResult deleteItem(@RequestParam("ids") long itemId,TbItem item) {
	        TaotaoResult result=itemService.deleteItem(itemId, item);
	        return result;
		}
}
