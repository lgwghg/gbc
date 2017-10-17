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
import com.webside.goods.model.GoodsStock;
import com.webside.goods.model.vo.GoodsStockVo;

/**
 * 商品库存数据访问接口
 * 
 * @author zhangfei
 * @date 2016-11-23 11:25:23
 */
@Repository
public interface IGoodsStockMapper extends BaseMapper<GoodsStock, String> {

	/**
	 * 验证卡号
	 */
	public int checkCardNo(Map<String, Object> map);
	
	/**
	 * 验证编号
	 */
	public int checkGoodsNo(Map<String, Object> map);

	/**
	 * 查询列表
	 */
	public List<GoodsStockVo> findListByParams(Map<String, Object> paramMap);
	
	/**
	 * 通过商品 获取本次兑换的库存
	 */
	public GoodsStock findByGoodsId(Map<String, Object> paramMap);
	
	/**
	 * 修改状态
	 * @param map
	 * @return
	 * @author zhangfei
	 */
	public int updateStockStatus(Map<String, Object> map);
	
}
