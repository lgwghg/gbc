/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.goods.mapper.IGoodsMapper;
import com.webside.goods.mapper.IGoodsStockMapper;
import com.webside.goods.model.Goods;
import com.webside.goods.service.IGoodsService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 商品服务实现类
 * 
 * @author zhangfei
 * @date 2016-11-23 11:24:26
 */
@Service("goodsService")
public class GoodsService extends AbstractService<Goods, String> implements IGoodsService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(goodsMapper);
	}

	/**
	 * 商品 DAO定义
	 */
	@Autowired
	private IGoodsMapper goodsMapper;
	@Autowired
	private IGoodsStockMapper goodsStockMapper;

	public int insert(Goods goods) {
		if (goods == null) {
			throw new RuntimeException("goods 不能为空");
		}
		goods.setId(IdGen.uuid());// 设置ID 生成 UUID
		goods.setGoodsNum(0L);
		goods.setAddUserId(ShiroAuthenticationManager.getUserId());
		goods.setCreateTime(System.currentTimeMillis());

		return goodsMapper.insert(goods);
	}

	public int update(Goods goods) {
		if (goods == null) {
			throw new RuntimeException("goods 不能为空");
		}
		goods.setUpdateUserId(ShiroAuthenticationManager.getUserId());
		goods.setUpdateTime(System.currentTimeMillis());

		return goodsMapper.update(goods);
	}

	public int updateGoodsNumByStockId(String stockIds, boolean add) {
		if (add) {
			return updateGoodsNumByStockId(stockIds, 1);
		} else {
			return updateGoodsNumByStockId(stockIds, -1);
		}
	}

	private int updateGoodsNumByStockId(String ids, int num) {
		if (num < 0) {
			// 减少库存，验证数量
			int count = checkGoodsNumBeforeDel(num, ids);
			if(count > 0) {
				throw new RuntimeException("库存不能小于0");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", StringUtils.StringToList(ids, ","));
		map.put("num", num);

		return goodsMapper.updateGoodsNumByStockId(map);
	}
	
	private int checkGoodsNumBeforeDel(int num, String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		map.put("ids", StringUtils.StringToList(ids, ","));
		return goodsMapper.checkGoodsNumBeforeDel(map);
	}

	public int checkGoodsName(String goodsName, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsName", goodsName);
		map.put("id", id);
		return goodsMapper.checkGoodsName(map);
	}

	public List<Goods> findListForMall(Map<String, Object> map) {
		
		return goodsMapper.findListForMall(map);
	}
	
	public void updateGoodsNumForJob() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentTime", System.currentTimeMillis());
		
		goodsMapper.updateGoodsNum(map);
		goodsStockMapper.updateStockStatus(map);
	}

}
