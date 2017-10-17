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

import com.webside.goods.model.GoodsStock;
import com.webside.goods.model.vo.GoodsStockVo;

/**
 * 商品库存服务接口
 * 
 * @author zhangfei
 * @date 2016-11-23 11:25:23
 */
public interface IGoodsStockService {
	/**
	 * 按条件查询商品库存
	 * 
	 * @throws Exception
	 * @author zhangfei
	 */
	public List<GoodsStock> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增商品库存
	 * 
	 * @param GoodsStock
	 * @throws Exception
	 * @author zhangfei
	 */
	public int insert(GoodsStock goodsStock);

	/**
	 * 修改商品库存
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	public int update(GoodsStock goodsStock);

	/**
	 * 根据ID获取商品库存
	 * 
	 * @param ID
	 * @throws Exception
	 * @author zhangfei
	 */
	public GoodsStock findById(String id);

	/**
	 * 根据对象删除商品库存
	 * 
	 * @param GoodsStock
	 * @throws Exception
	 * @author zhangfei
	 */
	public int deleteBatchById(String ids);

	/**
	 * 
	 * @param id 库存主键，可以为空
	 * @param cardNo 卡号
	 * @return 0不存在
	 * @author zhangfei
	 */
	public int checkCardNo(String id, String cardNo);

	/**
	 * 验证编号
	 * @param id 库存主键，可以为空
	 * @param goodsNo 商品编号
	 * @param goodsId 商品主键
	 * @return 0不存在
	 * @author zhangfei
	 */
	public int checkGoodsNo(String id, String goodsNo, String goodsId);

	/**
	 * 通过库存ID查询列表
	 * @param idList
	 * @return
	 * @author zhangfei
	 */
	public List<GoodsStockVo> findListByIds(List<String> idList);
	
	/**
	 * 通过商品 获取本次兑换的库存
	 * @param goodsId 商品主键
	 * @return 库存主键
	 * @author zhangfei
	 */
	public GoodsStock findByGoodsId(String goodsId);
	
}
