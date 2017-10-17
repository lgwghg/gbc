/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.address.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.address.entities.Address;
import com.webside.address.entities.Area;
import com.webside.address.entities.City;
import com.webside.address.entities.Province;
import com.webside.address.entities.Zipcode;
import com.webside.base.basemapper.BaseMapper;

/**
 * 用户收货地址数据访问接口
 *
 * @author tianguifang
 * @date 2016-11-28 15:18:27
 */
@Repository
public interface IAddressMapper extends BaseMapper<Address, String> 
{
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
	List<Zipcode> queryZipcodeByAreaId(String areaId);

	/**
	 * 根据用户id查询用户默认收货地址或最后更新的地址
	 * 
	 * @param userId
	 */
	Address findDefaultMyAddressByUserId(String userId);

	/**
	 * 根据用户id和地址id获取地址
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	Address findAddressByUserIdAndId(Map<String, Object> param);

	/**
	 * 取消用户默认地址
	 * 
	 * @param userId
	 */
	void updateCancelDefaultAddress(Map<String, Object> param);

	/**
	 * 设置默认地址
	 * 
	 * @param param
	 * @return
	 */
	int updateSetDefaultAddress(Map<String, Object> param);

	/**
	 * 查询用户收货地址数量
	 * 
	 * @param userId
	 * @return
	 */
	Integer queryUserAddressCount(String userId);
}
