package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.ItemParamWithName;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板管理
 * <p>Title: ItemParamServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		//补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入到规格参数模板表
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}
	 @Override
	    public EUDataGridResult getItemParamList(int page, int rows) {
	        //分页处理
	        PageHelper.startPage(page, rows);
	        List<TbItemParam> paramList = itemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());

	        //查询规格对应的名称并封装
	        List<ItemParamWithName> paramWithNameList = new ArrayList<>(paramList.size());
	        for (TbItemParam itemParam : paramList) {
	            String name = getItemCatName(itemParam.getItemCatId());
	            paramWithNameList.add(new ItemParamWithName(itemParam, name));
	        }

	        //总记录条数
	        PageInfo<TbItemParam> pageInfo = new PageInfo<>(paramList);
	        long total = pageInfo.getTotal();

	        EUDataGridResult result = new EUDataGridResult();
	        result.setTotal(total);
	        result.setRows(paramWithNameList);

	        return result;

	    }
	    private String getItemCatName(long itemCatId) {
	        TbItemCatExample example = new TbItemCatExample();
	        TbItemCatExample.Criteria criteria = example.createCriteria();
	        criteria.andIdEqualTo(itemCatId);

	        List<TbItemCat> list = itemCatMapper.selectByExample(example);
	        if (list != null && !list.isEmpty()) {
	            return list.get(0).getName();
	        }
	        return null;
	    }

}
