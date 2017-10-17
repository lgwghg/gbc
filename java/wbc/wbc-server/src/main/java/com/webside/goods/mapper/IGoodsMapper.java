/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.goods.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.goods.model.Goods;

/**
 * 商品数据访问接口
 * 
 * @author zhangfei
 * @date 2016-11-23 11:24:26
 */
@Repository
public interface IGoodsMapper extends BaseMapper<Goods, String> {

	/**
	 * 修改商品数量
	 */
	public int updateGoodsNum(Map<String, Object> map);
	
	/**
	 * 修改商品数量
	 */
	public int updateGoodsNumByStockId(Map<String, Object> map);
	
	/**
	 * 验证商品名称
	 */
	public int checkGoodsName(Map<String, Object> map);
	
	/**
	 * 新增之前验证数量
	 */
	public int checkGoodsNumBeforeDel(Map<String, Object> map);
	
	/**
	 * 商城列表
	 */
	public List<Goods> findListForMall(Map<String, Object> map);
	
}
