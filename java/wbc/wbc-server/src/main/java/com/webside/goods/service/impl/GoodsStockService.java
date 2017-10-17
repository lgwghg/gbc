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

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.goods.mapper.IGoodsStockMapper;
import com.webside.goods.model.GoodsStock;
import com.webside.goods.model.vo.GoodsStockVo;
import com.webside.goods.service.IGoodsService;
import com.webside.goods.service.IGoodsStockService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 商品库存服务实现类
 * 
 * @author zhangfei
 * @date 2016-11-23 11:25:23
 */
@Service("goodsStockService")
public class GoodsStockService extends AbstractService<GoodsStock, String> implements IGoodsStockService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(goodsStockMapper);
	}

	/**
	 * 商品库存 DAO定义
	 */
	@Autowired
	private IGoodsStockMapper goodsStockMapper;
	@Autowired
	private IGoodsService goodsService;

	public int insert(GoodsStock goodsStock) {
		if (goodsStock == null) {
			throw new RuntimeException("goodsStock 不能为空");
		}
		goodsStock.setId(IdGen.uuid());// 设置ID 生成 UUID
		if (StringUtils.isNotBlank(goodsStock.getEffectiveTime())) {
			goodsStock.setEffectiveTime(StringUtils.toString(DateUtils.parseDate(goodsStock.getEffectiveTime()).getTime()));
		} else {
			goodsStock.setEffectiveTime(null);
		}
		int count = goodsStockMapper.insert(goodsStock);
		if (1 == count && GlobalConstant.DICTTYPE_STOCK_STATUS_1.equals(goodsStock.getStatus())) {
			goodsService.updateGoodsNumByStockId(goodsStock.getId(), true);
		}

		return count;
	}

	public int update(GoodsStock goodsStock) {
		if (goodsStock == null || StringUtils.isBlank(goodsStock.getId())) {
			throw new RuntimeException("goodsStock 不能为空");
		}
		if (StringUtils.isNotBlank(goodsStock.getEffectiveTime())) {
			goodsStock.setEffectiveTime(StringUtils.toString(DateUtils.parseDate(goodsStock.getEffectiveTime()).getTime()));
		} else {
			goodsStock.setEffectiveTime(null);
		}
		
		GoodsStock entity = findById(goodsStock.getId());
		if(entity.getStatus() != goodsStock.getStatus()) {// 两次状态不一致
			if(GlobalConstant.DICTTYPE_STOCK_STATUS_1.equals(entity.getStatus())) {// 原来是未兑换状态
				goodsService.updateGoodsNumByStockId(goodsStock.getId(), false);
			} else if(GlobalConstant.DICTTYPE_STOCK_STATUS_1.equals(goodsStock.getStatus())) {// 现在是未兑换
				goodsService.updateGoodsNumByStockId(goodsStock.getId(), true);
			}
		}
		
		return goodsStockMapper.update(goodsStock);
	}

	public int deleteBatchById(String ids) {
		int count = goodsService.updateGoodsNumByStockId(ids, false);
		if (0 < count) {
			List<String> list = StringUtils.StringToList(ids, ",");
			count = goodsStockMapper.deleteBatchById(list);
		}
		return count;
	}

	public int checkCardNo(String id, String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("cardNo", cardNo);
		return goodsStockMapper.checkCardNo(map);
	}

	public int checkGoodsNo(String id, String goodsNo, String goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("goodsNo", goodsNo);
		map.put("goodsId", goodsId);
		return goodsStockMapper.checkGoodsNo(map);
	}

	public List<GoodsStockVo> findListByIds(List<String> idList) {
		if (idList == null || idList.isEmpty()) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("stockIds", idList);

		return findListByParams(paramMap);
	}

	public List<GoodsStockVo> findListByParams(Map<String, Object> paramMap) {
		if (MapUtils.isEmpty(paramMap)) {
			return null;
		}

		return goodsStockMapper.findListByParams(paramMap);
	}
	
	public GoodsStock findByGoodsId(String goodsId) {
		if(StringUtils.isBlank(goodsId)) {
			throw new RuntimeException("请传入商品 goodsId");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("goodsId", goodsId);
		paramMap.put("currentTime", System.currentTimeMillis());
		
		return goodsStockMapper.findByGoodsId(paramMap);
	}

}
