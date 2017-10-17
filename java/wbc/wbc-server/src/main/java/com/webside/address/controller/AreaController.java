package com.webside.address.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.address.entities.Area;
import com.webside.address.service.IAddressService;

/**
 * 区，县Controller
 * 
 * @author tianguifang
 * @date 2016-11-29
 */

@Controller
@RequestMapping("/area/")
public class AreaController {
	@Autowired
	private IAddressService addressService;

	/**
	 * 根据市id获取区、县list
	 * 
	 * @return
	 */
	@RequestMapping("{cityId}")
	@ResponseBody
	public Object queryCityByProvId(@PathVariable("cityId") String cityId) {
		List<Area> areaList = addressService.queryAreaByCityId(cityId);
		Map<String, Object> areaMap = new HashMap<String, Object>();
		areaMap.put("areaList", areaList);
		return areaMap;
	}
}
