/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.address.service;

import java.util.List;
import java.util.Map;

import com.webside.address.entities.Address;
import com.webside.address.entities.Area;
import com.webside.address.entities.City;
import com.webside.address.entities.Province;
import com.webside.address.entities.Zipcode;

/**
 * 用户收货地址服务接口
 *
 * @author tianguifang
 * @date 2016-11-28 15:18:27
 */
public interface IAddressService 
{
	/*
	 * 按条件查询用户收货地址
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<Address> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户收货地址
	 * @param Address
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final Address entity);
	
	/*
	 * 修改用户收货地址
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final Address entity);
	
	/*
	 * 根据ID获取用户收货地址
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public Address findById(String id);
	
	/*
	 * 根据对象删除用户收货地址
	 * @param Address
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);

	/**
	 * 查询所有的省份
	 * 
	 * @return
	 */
	List<Province> queryAllProvince();

	/**
	 * 根据省份id查城市
	 * 
	 * @param provinceId
	 * @return
	 */
	List<City> queryCityByProvinceId(String provinceId);

	/**
	 * 根据城市Id查区、县
	 * 
	 * @param cityId
	 * @return
	 */
	List<Area> queryAreaByCityId(String cityId);

	/**
	 * 根据区、县id查询邮编区号
	 * 
	 * @param areaId
	 * @return
	 */
	Zipcode queryZipcodeByAreaId(String areaId);

	/**
	 * 根据用户id查询用户默认收货地址或最后更新的地址
	 * 
	 * @param userId
	 */
	public Address findDefaultMyAddressByUserId(String userId);

	/**
	 * 根据用户id和地址id获取地址
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	public Address findAddressByUserIdAndId(String userId, String id);

	/**
	 * 设置用户默认收货地址
	 */
	public int updateDefaultAddress(String userId, String id);

	/**
	 * 查询用户收货地址数量
	 * 
	 * @param userId
	 * @return
	 */
	public Integer queryUserAddressCount(String userId);

}
