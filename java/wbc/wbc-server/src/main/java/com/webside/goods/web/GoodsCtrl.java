package com.webside.goods.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.exception.AjaxException;
import com.webside.goods.model.Goods;
import com.webside.goods.model.GoodsStock;
import com.webside.goods.service.IGoodsService;
import com.webside.goods.service.IGoodsStockService;

/**
 * 商城
 * @author zhangfei
 */

@Controller
@RequestMapping("/shopping/")
public class GoodsCtrl extends BaseController {

	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IGoodsStockService goodsStockService;
	
	/**
	 * 商城列表
	 * @param gridPager 分页相关参数
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "EXCHANGE_COUNT desc, t.GOODS_GOLD desc");
			
			List<Goods> list = goodsService.findListForMall(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			parameters.put("list", list);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取商城列表数据出错：", e);
		}
		return parameters;
	}
	
	/**
	 * 商品详情
	 */
	@RequestMapping("{goodsId}")
	public String goodsDetails(@PathVariable String goodsId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", goodsId);

		List<Goods> list = goodsService.findListForMall(map);
		if (list != null && !list.isEmpty()) {
			model.addAttribute("goods", list.get(0));
		}

		return Common.BACKGROUND_PATH_WEB + "/goods/goodsDetails";
	}
	
	/**
	 * 更新库存
	 * @param entity
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "editGoodsStock", method = RequestMethod.POST)
	@ResponseBody
	public Object editGoodsStock(GoodsStock entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = goodsStockService.update(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", entity);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}
	
}
