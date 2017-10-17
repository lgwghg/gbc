/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.goods.service;

import java.util.List;
import java.util.Map;

import com.webside.goods.model.Goods;

/**
 * 商品服务接口
 * 
 * @author zhangfei
 * @date 2016-11-23 11:24:26
 */
public interface IGoodsService {
	/**
	 * 按条件查询商品
	 * 
	 * @throws Exception
	 * @author zhangfei
	 */
	public List<Goods> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增商品
	 * 
	 * @param Goods
	 * @throws Exception
	 * @author zhangfei
	 */
	public int insert(Goods goods);

	/**
	 * 修改商品
	 * 
	 * @param goods
	 * @throws Exception
	 * @author zhangfei
	 */
	public int update(Goods goods);

	/**
	 * 根据ID获取商品
	 * 
	 * @param ID
	 * @throws Exception
	 * @author zhangfei
	 */
	public Goods findById(String id);

	/**
	 * 根据对象删除商品
	 * 
	 * @param Goods
	 * @throws Exception
	 * @author zhangfei
	 */
	public int deleteBatchById(List<String> ids);
	
	/**
	 * 修改商品数量
	 * @param stockIds 库存主键
	 * @param add 是否增加商品数量
	 * @return
	 * @author zhangfei
	 */
	public int updateGoodsNumByStockId(String stockIds, boolean add);
	
	/**
	 * 验证商品名称
	 * @param goodsName 商品名称
	 * @param id 商品主键
	 * @return
	 * @author zhangfei
	 */
	public int checkGoodsName(String goodsName, String id);
	
	/**
	 * 商城列表, id 商品主键
	 * @param map
	 * @return
	 * @author zhangfei
	 */
	public List<Goods> findListForMall(Map<String, Object> map);
	
	/**
	 * 定时器同步库存
	 * 
	 * @author zhangfei
	 */
	public void updateGoodsNumForJob();
	
}
