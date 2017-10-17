package com.webside.address.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.address.entities.City;
import com.webside.address.service.IAddressService;

/**
 * 市Controller
 * 
 * @author tianguifang
 * @date 2016-11-29
 */

@Controller
@RequestMapping("/city/")
public class CityController {

	@Autowired
	private IAddressService addressService;
	/**
	 * 根据省份id获取城市list
	 * 
	 * @return
	 */
	@RequestMapping("{provId}")
	@ResponseBody
	public Object queryCityByProvId(@PathVariable("provId") String provId) {
		List<City> cityList = addressService.queryCityByProvinceId(provId);
		Map<String, Object> cityMap = new HashMap<String, Object>();
		cityMap.put("cityList", cityList);
		return cityMap;
	}
}
