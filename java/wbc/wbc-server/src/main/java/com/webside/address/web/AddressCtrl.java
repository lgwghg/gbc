package com.webside.address.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.address.entities.Address;
import com.webside.address.service.IAddressService;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.exception.AjaxException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.util.IdGen;

/**
 * 用户收货地址Controller
 * 
 * @author tianguifang
 * @date 2016-12-1
 */

@Controller
@RequestMapping("/my/address")
public class AddressCtrl extends BaseController {

	@Autowired
	private IAddressService addressService;

	/**
	 * 进去你的收货地址页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String addressUI() {
		return Common.BACKGROUND_PATH_WEB + "/my/address/address";
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param dtGridPager
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		String userId = ShiroAuthenticationManager.getUserId();
		if (userId == null) {
			return null;
		}
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		parameters.put("userId", userId);
		parameters.put("isDeleted", 0);

		// parameters.put("creatorName",
		// ShiroAuthenticationManager.getUserAccountName());

		// 设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "is_default DESC, update_time desc");
		List<Address> list = addressService.queryListByPage(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		// 列表展示数据
		parameters.put("list", list);
		return parameters;
	}

	/**
	 * 查询当前登录用户默认收货地址，如果没默认地址，取最后一次更新的地址
	 * 
	 * @return
	 */
	@RequestMapping("/default")
	@ResponseBody
	public Object queryDefaultMyAddress() {
		String userId = ShiroAuthenticationManager.getUserId();
		Address address = addressService.findDefaultMyAddressByUserId(userId);
		Map<String, Address> map = new HashMap<String, Address>();
		if (address != null) {
			map.put("address", address);
		}
		return map;
	}

	/**
	 * 新增收货地址
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(Address entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setId(IdGen.uuid());// 设置ID 生成 UUID
			entity.setUserId(ShiroAuthenticationManager.getUserId());
			entity.setCreateTime(System.currentTimeMillis());
			Integer count = addressService.queryUserAddressCount(ShiroAuthenticationManager.getUserId());
			if (count >= 10) {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "不能再添加啦，去修改或删后再加。");
			} else {
				int result = addressService.insert(entity);

				if (result > 0) {
					map.put("success", Boolean.TRUE);
					map.put("data", entity);
					map.put("message", "添加成功");
				} else {
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "添加失败");
				}
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 修改用户收货地址
	 * @param entity
	 * @return
	 * @throws AjaxException
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Object update(Address entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		try {
			// 设置创建者姓名
			entity.setUpdateTime(System.currentTimeMillis());
			entity.setUserId(userId);

			int result = addressService.update(entity);

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

	/**
	 * 删除用户收货地址
	 * 
	 * @param entity
	 * @return
	 * @throws AjaxException
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(String id) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		try {
			// 设置创建者姓名
			Address address = new Address();
			address.setId(id);
			address.setUpdateTime(System.currentTimeMillis());
			address.setIsDeleted(1);
			address.setUserId(userId);
			int result = addressService.update(address);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "删除成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "删除失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/*
	 * 跳转编辑用户收货地址页面
	 * 
	 * @param Model model
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	@RequestMapping("getAddressById")
	@ResponseBody
	public Object getAddressById(String id) {
		Address address = null;
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		try {
			address = addressService.findAddressByUserIdAndId(userId, id);
			Map<String, Address> addressMap = new HashMap<String, Address>();
			addressMap.put("address", address);
			return addressMap;
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/*
	 * 设置默认收货地址
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("setDefault")
	@ResponseBody
	public Object setDefaultAddress(String id) {
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = addressService.updateDefaultAddress(userId, id);
			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "设置成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "设置失败");
			}

		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}
}
