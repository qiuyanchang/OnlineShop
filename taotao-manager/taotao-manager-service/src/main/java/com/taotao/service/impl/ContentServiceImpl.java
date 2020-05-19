package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;

/**
 * 内容管理
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * @version 1.0
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper  contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok(content);
	}
	   @Override
	    public EUDataGridResult getContentList(int page, int rows, long categoryId) {
	        //分页处理,获得记录
	        PageHelper.startPage(page, rows);
	        TbContentExample example = new TbContentExample();
	        Criteria criteria = example.createCriteria();
	        criteria.andCategoryIdEqualTo(categoryId);
	        List<TbContent> list = contentMapper.selectByExample(example);

	        //获得记录的总条数
	        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
	        long total = pageInfo.getTotal();

	        EUDataGridResult result = new EUDataGridResult();
	        result.setRows(list);
	        result.setTotal(total);
	        
	        return result;
	    }

}
