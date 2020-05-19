package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {

	List<EUTreeNode> getCategoryList(long parentId);
	 TbContentCategory getCategory(long id);
	TaotaoResult insertContentCategory(long parentId, String name);
    TaotaoResult deleteContentCategory(long id);
    TaotaoResult updateContentCategory(long id, String name);
}
