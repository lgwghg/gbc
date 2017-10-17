/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.goods.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.goods.model.Goods;
import com.webside.goods.model.GoodsStock;
import com.webside.goods.service.IGoodsService;
import com.webside.goods.service.IGoodsStockService;
import com.webside.system.dict.service.DictService;
import com.webside.util.DateUtils;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 商品库存Controller
 * 
 * @author zhangfei
 * @date 2016-11-23 11:25:23
 */

@Controller
@RequestMapping("/goodsStock/")
public class GoodsStockController extends BaseController {
	/**
	 * 跳转查询商品库存页面
	 * 
	 * @param Model
	 *            model HttpServletRequest request
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		PageUtil page = null;

		try {
			page = new PageUtil();

			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}

			model.addAttribute("page", page);
			// 查询选项数据
			model.addAttribute("stockStatus", dictService.getDictType(GlobalConstant.DICTTYPE_STOCK_STATUS));
			return Common.BACKGROUND_PATH + "/business/goods/stockList";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param dtGridPager
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();

		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<GoodsStock> list = goodsStockService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "g.type,t.goods_id DESC");
			List<GoodsStock> list = goodsStockService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			// 权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("goods:edit");
			boolean delete = subject.isPermitted("goods:delete");
			// 列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list, edit ? "edit" : "", delete ? "del" : ""));
			return parameters;
		}
	}

	/**
	 * 跳转新增商品库存页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("status", 1);// 查询有效的商品
			List<Goods> goodsList = goodsService.queryListByPage(paramMap);

			model.addAttribute("goodsList", goodsList);
			// 查询选项数据
			model.addAttribute("stockStatus", dictService.getDictType(GlobalConstant.DICTTYPE_STOCK_STATUS));
			return Common.BACKGROUND_PATH + "/business/goods/form/stockForm";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增商品库存 ajax
	 * 
	 * @param GoodsStock
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(GoodsStock entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = goodsStockService.insert(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 跳转编辑商品库存页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		try {
			GoodsStock goodsStock = goodsStockService.findById(id);
			if (StringUtils.isNoneBlank(goodsStock.getEffectiveTime())) {
				goodsStock.setEffectiveTime(DateUtils.longToString(StringUtils.toLong(goodsStock.getEffectiveTime())));
			}
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<Goods> goodsList = goodsService.queryListByPage(paramMap);

			model.addAttribute("goodsList", goodsList);
			model.addAttribute("page", page);
			model.addAttribute("goodsStock", goodsStock);
			// 查询选项数据
			model.addAttribute("stockStatus", dictService.getDictType(GlobalConstant.DICTTYPE_STOCK_STATUS));
			return Common.BACKGROUND_PATH + "/business/goods/form/stockForm";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改商品库存
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(GoodsStock entity) throws AjaxException {
		Map<String, Object> map = null;

		try {
			map = new HashMap<String, Object>();
			// 设置创建者姓名
			int result = goodsStockService.update(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
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

	/**
	 * 根据ID列表删除商品库存
	 * 
	 * @param GoodsStock
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("deleteBatch.html")
	@ResponseBody
	public Object deleteBatch(String ids) {
		Map<String, Object> result = null;
		try {
			result = new HashMap<String, Object>();
			
			int cnt = goodsStockService.deleteBatchById(ids);
			if (cnt > 0) {
				result.put("success", true);
				result.put("data", null);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("data", null);
				result.put("message", "删除失败");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("data", null);
			result.put("message", "删除失败：" + e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id) {

		return deleteBatch(id);
	}

	/**
	 * 根据ID 查询商品库存
	 * 
	 * @param String
	 *            id
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) {
		try {
			GoodsStock entity = goodsStockService.findById(id);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/user/info";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 验证卡号
	 */
	@RequestMapping(value = "checkCardNo.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkCardNo(@RequestParam String id, @RequestParam(required = true) String cardNo) {
		if (StringUtils.isBlank(id) || id.equals("undefined")) {
			id = null;
		}
		int count = goodsStockService.checkCardNo(id, cardNo);
		if (count == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 验证编号
	 */
	@RequestMapping(value = "checkGoodsNo.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkGoodsNo(HttpServletRequest request) {
		String id = request.getParameter("id");
		String goodsNo = request.getParameter("goodsNo");
		String goodsId = request.getParameter("goodsId");
		if (StringUtils.isBlank(id) || id.equals("undefined")) {
			id = null;
		}
		int count = goodsStockService.checkGoodsNo(id, goodsNo, goodsId);
		if (count == 0) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "findListByIds.json")
	@ResponseBody
	public Object findListByIds(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Object data = null;
		String msg = "";
		boolean success = true;
		try {
			String stockIds = request.getParameter("stockIds");
			List<String> idList = StringUtils.StringToList(stockIds, ",");
			data = goodsStockService.findListByIds(idList);
		} catch (Exception e) {
			success = false;
			msg = "查询失败：" + e.getMessage();
		} finally {
			result.put("data", data);
			result.put("msg", msg);
			result.put("success", success);
		}

		return result;
	}

	/**
	 * 商品库存 Service定义
	 */
	@Autowired
	private IGoodsStockService goodsStockService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private DictService dictService;
}
