/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.address.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.address.entities.Address;
import com.webside.address.entities.Area;
import com.webside.address.entities.City;
import com.webside.address.entities.Province;
import com.webside.address.entities.Zipcode;
import com.webside.address.mapper.IAddressMapper;
import com.webside.address.service.IAddressService;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.shiro.cache.redis.VCache;

/**
 * 用户收货地址服务实现类
 *
 * @author tianguifang
 * @date 2016-11-28 15:18:27
 */
@Service("addressService")
public class AddressService extends AbstractService<Address, String> implements IAddressService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(addressMapper);
	}

	/**
	 * 用户收货地址 DAO定义
	 */
	@Autowired
	private IAddressMapper addressMapper;

	@Override
	public List<Province> queryAllProvince() {
		List<Province> provList = (List<Province>) VCache.get("provinces");
		if (provList == null) {
			provList = addressMapper.queryAllProvince();
			if (provList != null) {
				VCache.set("provinces", provList, 7 * 24 * 60);
			}
		}
		return provList;
	}

	@Override
	public List<City> queryCityByProvinceId(String provinceId) {
		List<City> cityList = (List<City>) VCache.get("citys:" + provinceId);
		if (cityList == null) {
			cityList = addressMapper.queryCityByProvinceId(provinceId);
			if (cityList != null) {
				VCache.set("citys:" + provinceId, cityList, 7 * 24 * 60);
			}
		}
		return cityList;
	}

	@Override
	public List<Area> queryAreaByCityId(String cityId) {
		List<Area> areaList = (List<Area>) VCache.get("areas:" + cityId);
		if (areaList == null) {
			areaList = addressMapper.queryAreaByCityId(cityId);
			if (areaList != null) {
				VCache.set("areas:" + cityId, areaList, 7 * 24 * 60);
			}
		}
		return areaList;
	}

	@Override
	public Zipcode queryZipcodeByAreaId(String areaId) {
		List<Zipcode> zipcodeList = addressMapper.queryZipcodeByAreaId(areaId);
		if (CollectionUtils.isNotEmpty(zipcodeList)) {
			return zipcodeList.get(0);
		}
		return null;
	}

	/**
	 * 根据用户id查询用户默认收货地址或最后更新的地址
	 * 
	 * @param userId
	 */
	@Override
	public Address findDefaultMyAddressByUserId(String userId) {
		return addressMapper.findDefaultMyAddressByUserId(userId);
	}

	/**
	 * 根据用户id和地址id获取地址
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	@Override
	public Address findAddressByUserIdAndId(String userId, String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("id", id);
		return addressMapper.findAddressByUserIdAndId(param);
	}

	@Override
	public int updateDefaultAddress(String userId, String id) {
		// 取消原默认地址
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("updateTime", System.currentTimeMillis());
		addressMapper.updateCancelDefaultAddress(param);
		// 更新新的地址为默认地址
		param.put("id", id);
		param.put("updateTime", System.currentTimeMillis());
		int result = addressMapper.updateSetDefaultAddress(param);
		return result;
	}

	/**
	 * 查询用户收货地址数量
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public Integer queryUserAddressCount(String userId) {
		return addressMapper.queryUserAddressCount(userId);
	}

}
