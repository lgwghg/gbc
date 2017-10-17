package com.webside.address.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.address.entities.Province;
import com.webside.address.service.IAddressService;

/**
 * 省份Controller
 * 
 * @author tianguifang
 * @date 2016-11-29
 */

@Controller
@RequestMapping("/province")
public class ProvinceController {
	/**
	 * 用户收货地址 Service定义
	 */
	@Autowired
	private IAddressService addressService;

	/**
	 * 查询所有的省份
	 * 
	 * @return
	 */
	@RequestMapping("")
	@ResponseBody
	public Object queryAllProvince() {
		List<Province> provinceList = addressService.queryAllProvince();
		Map<String, Object> provMap = new HashMap<>();
		provMap.put("provinceList", provinceList);
		return provMap;
	}
}
