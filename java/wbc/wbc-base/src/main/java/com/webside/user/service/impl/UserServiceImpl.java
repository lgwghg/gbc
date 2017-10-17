package com.webside.user.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.role.model.RoleEntity;
import com.webside.role.model.RoleUser;
import com.webside.role.service.RoleService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.mapper.UserIncrementMapper;
import com.webside.user.mapper.UserMapper;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrement;
import com.webside.user.model.UserRole;
import com.webside.user.model.UserWallet;
import com.webside.user.model.vo.UserRoleVo;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserIncrementService;
import com.webside.user.service.UserService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.util.EmailUtil;
import com.webside.util.IdGen;
import com.webside.util.ShortCharUtil;

@Service("userService")
public class UserServiceImpl extends AbstractService<UserEntity, String> implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserIncrementService userIncrementService;
	@Autowired
	private UserIncrementMapper userIncrementMapper;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private IUserCacheService userCacheService;
	@Autowired
	private IUserTaskService userTaskService;
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userMapper);
	}

	/**
	 * 查询用户列表
	 */
	@Override
	public List<UserEntity> queryListByPage(Map<String, Object> parameter) {
		List<UserEntity> userList = userMapper.queryListByPage(parameter);
		if (CollectionUtils.isNotEmpty(userList)) {
			List<String> userIdList = new ArrayList<String>();
			for (UserEntity user : userList) {
				userIdList.add(user.getId());
			}
			Map<String, UserRoleVo> userRoleMap = roleService.findUserRoleByUserIds(userIdList);
				for (UserEntity user : userList) {
					if (userRoleMap != null && userRoleMap.size() > 0) {
						UserRoleVo roleVo = userRoleMap.get(user.getId());
						if (roleVo != null) {
							user.setRoleName(roleVo.getRoleName());
						}
					}
				}

		}
		return userList;
	}
	/**
	 * 重写用户插入，逻辑： 1、插入用户 2、插入用户和角色的对应关系 3、插入用户的个人资料信息
	 */
	public int insert(UserEntity userEntity, String password) {
		try {
			int result = 0;
			// 增加用户信息
			userEntity.setPhoto(GlobalConstant.DEFAULT_PHOTO_URL);
			//推荐key
			String[] shortText = ShortCharUtil.ShortText(userEntity.getId());
			for (int i = 0; i < shortText.length; i++) {
				try {
					userEntity.setCampaignKey(shortText[i]);
					result = userMapper.insert(userEntity);
					break;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			if(result==1){
				if (userEntity.getUserIncrement() == null) {
					userEntity.setUserIncrement(new UserIncrement());
				}
				userEntity.getUserIncrement().setId(IdGen.uuid());
				userEntity.getUserIncrement().setUserId(userEntity.getId());
				userEntity.getUserIncrement().setCreateTime(userEntity.getCreateTime());
				// 用户增量数据
				userIncrementMapper.insert(userEntity.getUserIncrement());
				UserWallet wallet = new UserWallet();
				wallet.setId(IdGen.uuid());
				wallet.setUserId(userEntity.getId());
				wallet.setCreateTime(userEntity.getCreateTime());
				wallet.setCreateOperatorId(userEntity.getId());
				wallet.setUpdateTime(userEntity.getCreateTime());
				wallet.setUpdateOperatorId(userEntity.getId());
				wallet.setGold(0L);
				wallet.setSysGoldNum(0L);
				userMapper.insertUserWallet(wallet);

				// 增加用户缓存信息，失败记录日志即可，不影响事务继续执行
				try {
					userCacheService.updateUserToRedis(userEntity);
				} catch (Exception e) {
					logger.error("更新用户缓存失败", e);
				}
			}
			return result;
		} catch (Exception e) {
			throw new ServiceException("新增用户: " + userEntity.getId() + " 失败", e);
		}
	}

	/**
	 * 重写用户更新逻辑： 1、更新用户 2、更新用户和角色的对应关系 3、更新用户个人资料信息
	 */
	public int update(UserEntity userEntity) {
		int result = 0;
		try {
			// 设置创建者姓名
			userEntity.setUpdatorName(ShiroAuthenticationManager.getUserNickName());
			userEntity.setUpdateTime(System.currentTimeMillis());
			if (userMapper.update(userEntity) == 1) {
				UserRole userRole = userMapper.findUserRoleByUserId(userEntity.getId());
				if (userRole != null) {// 本身有角色
					if (userEntity.getRole() == null || StringUtils.isBlank(userEntity.getRole().getId())) { // 未设角色，或角色被删
						if (userMapper.deleteUserRoleById(userRole.getId()) == 1) {
							result = 1;
						}
					} else {
						if (userMapper.updateUserRole(userEntity) == 1) { //
							result = 1;
						}
					}
				} else { // 本身无角色
					if (userEntity.getRole() == null || StringUtils.isBlank(userEntity.getRole().getId())) { // 未设角色，或角色被删
						result = 1;
					} else { // 新设角色
						RoleUser roleUser = new RoleUser();
						roleUser.setId(IdGen.uuid());
						roleUser.setUserId(userEntity.getId());
						roleUser.setRoleId(userEntity.getRole().getId());
						if (userMapper.insertUserRole(roleUser) == 1) {
							result = 1;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		if (result == 1) {
			userCacheService.updateUserToRedis(userEntity);
		}
		return result;
	}

	/**
	 * 只更新user表
	 */
	public int updateUserOnly(UserEntity userEntity) {
		// UserEntity user =
		// userCacheService.getUserEntityByUserId(userEntity.getId());
		// 设置创建者姓名
		userEntity.setUpdatorName(ShiroAuthenticationManager.getUserNickName());
		userEntity.setUpdateTime(System.currentTimeMillis());
		int result = userMapper.update(userEntity);
		if (result > 0) {
			userCacheService.updateUserToRedis(userEntity);
			if (StringUtils.isNotBlank(userEntity.getPhoto())) {
				userTaskService.updateUserTask(userEntity.getId(), GlobalConstant.USER_TASK_TYPE_SET_PHOTO_5, null);
			}
			if (StringUtils.isNotBlank(userEntity.getEmail())) {
				userTaskService.updateUserTask(userEntity.getId(), GlobalConstant.USER_TASK_TYPE_SET_EMAIL_6, null);
			}
		}
		return result;
	}
	
	/**
	 * 只更新user表
	 */
	public int updateUser(UserEntity userEntity) {
		// 设置创建者姓名
		userEntity.setUpdatorName("-1");
		userEntity.setUpdateTime(System.currentTimeMillis());
		int result = userMapper.update(userEntity);
		if (result > 0) {
			userCacheService.updateUserToRedis(userEntity);
		}
		return result;
	}

	/**
	 * 重写用户删除逻辑： 1、删除用户和角色的对应关系 2、删除用户
	 */
	public int deleteBatchById(List<String> userIds) {
		try {
			int result = userMapper.deleteBatchUserRole(userIds);
			if (result == userIds.size()) {
				return userMapper.deleteBatchById(userIds);
			} else {
				return 0;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateOnly(UserEntity userEntity, String password) throws ServiceException {
		try {
			userEntity.setUpdatorName(ShiroAuthenticationManager.getUserNickName());
			userEntity.setUpdateTime(System.currentTimeMillis());
			int cnt = userMapper.update(userEntity);
			if (StringUtils.isNotBlank(userEntity.getEmail())) {
				// 发送邮件
				//Mail mail = new Mail(null, userEntity.getEmail(), EmailDescription.UPDATE_EMAIL.getSubject(), String.format(EmailDescription.UPDATE_EMAIL.getMessage(), password), null, null);
				//queueSender.send("q.mail", JSON.toJSONString(mail));
				emailUtil.send126Mail(userEntity.getEmail(), "G菠菜", "系统密码重置",
				 "您好，您的密码已重置，新密码是:" + password);
			}
			userCacheService.updateUserToRedis(userEntity);
			if (StringUtils.isNotBlank(userEntity.getEmail())) {
				userTaskService.updateUserTask(userEntity.getId(), GlobalConstant.USER_TASK_TYPE_SET_EMAIL_6, null);
			}
			return cnt;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public UserEntity findByMobile(String mobile) {
		UserEntity user = userMapper.findByMobile(mobile);
		if (user != null) {
			RoleEntity role = roleService.findUserRoleByUserId(user.getId());
			if(role!=null){
				user.setRole(role);
			}
		}
		return user;
	}

	@Override
	public UserEntity findByEmail(String email) {
		UserEntity user = userMapper.findByEmail(email);
		if (user != null) {
			RoleEntity role = roleService.findUserRoleByUserId(user.getId());
			if (role != null) {
				user.setRole(role);
			}
		}
		return user;
	}

	@Override
	public UserEntity findUserById(String id) {
		UserEntity user = userMapper.findById(id);
		if (user != null) {
			RoleEntity role = roleService.findUserRoleByUserId(user.getId());
			if (role != null) {
				user.setRole(role);
			}
		}
		return user;
	}
	@Override
	public List<UserEntity> findRoleUserByRoleId(Map<String, Object> parameter) {

		return userMapper.findRoleUserByRoleId(parameter);
	}

	@Override
	public Integer countAllUser() {
		return userMapper.countAllUser();
	}

	@Override
	public UserEntity findByNickName(String nickName) {
		return userMapper.findByNickName(nickName);
	}

	@Override
	public UserEntity findUserEntity(UserEntity user) {
		return userMapper.findUserEntity(user);
	}

	@Override
	public UserEntity findUserWithRoleAndIncrement(UserEntity user) {
		return userMapper.findUserWithRoleAndIncrement(user);
	}

	@Override
	public String findAlipayAccountByUserId(String userId) {
		return userMapper.findAlipayAccountByUserId(userId);
	}

	@Override
	public int updateUserAlipayAccount(UserEntity userEntity) {
		int result = userMapper.update(userEntity);
		if (result > 0) {//支付密码任务
			userTaskService.updateUserTask(userEntity.getId(), GlobalConstant.USER_TASK_TYPE_SET_PAY_PWD_7, null);
		}
		return result;
	}

	/**
	 * 该方法只用来登录获取用户信息，其他功能绕行
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public UserEntity findUserByMobileOrEmailOrIdOrThirdKey(UserEntity userParam) {
		UserEntity user = userMapper.findUserByMobileOrEmailOrIdOrThirdKey(userParam);
		if (user != null) {
			RoleEntity role = roleService.findUserRoleByUserId(user.getId());
			if (role != null) {
				user.setRole(role);
			}
		}
		return user;
	}

	@Override
	public UserEntity findUserByMobileOrEmailOrIdOrThirdKeyWithoutRole(UserEntity userParam) {
		return userMapper.findUserByMobileOrEmailOrIdOrThirdKey(userParam);
	}
	/**
	 * 取消第三方绑定
	 */
	@Override
	public int updateCancalBind(String userId, String thirdType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("thirdType", thirdType);
		paramMap.put("updateTime", System.currentTimeMillis());
		return userMapper.updateCancalBind(paramMap);
	}

	/**
	 * 盈利top
	 */
	@Override
	public JSONArray profitTop(Integer count) {
		JSONArray jsonArray = new JSONArray();
		List<String> profitTopUserIdList = userIncrementService.profitTop(count);
		if (CollectionUtils.isNotEmpty(profitTopUserIdList)) {
			JSONObject jsonObject = null;
			for (String userId : profitTopUserIdList) {
				UserEntity user = userCacheService.getUserEntityByUserId(userId);
				if (user != null) {
					jsonObject = new JSONObject();
					jsonObject.put("nickName", user.getNickName());
					jsonObject.put("userPhoto_65", user.getPhoto_65());
					jsonObject.put("goldNum", user.getUserIncrement().getTotalProfitGoldNum());
					jsonObject.put("rankLevel", user.getRankLevel());
					jsonArray.add(jsonObject);
				}
			}
		}
		return jsonArray;
	}

	/**
	 * 盈率top
	 */
	@Override
	public JSONArray profitRateTop(Integer count) {
		JSONArray jsonArray = new JSONArray();
		List<String> profitTopUserIdList = userIncrementService.profitRateTop(count);
		if (CollectionUtils.isNotEmpty(profitTopUserIdList)) {
			JSONObject jsonObject = null;
			for (String userId : profitTopUserIdList) {
				UserEntity user = userCacheService.getUserEntityByUserId(userId);
				if (user != null) {
					jsonObject = new JSONObject();
					jsonObject.put("nickName", user.getNickName());
					jsonObject.put("userPhoto_65", user.getPhoto_65());
					DecimalFormat df = new DecimalFormat("######0.00");
					jsonObject.put("victoryRate", df.format(user.getUserIncrement().getVictoryRate()*100));
					jsonObject.put("rankLevel", user.getRankLevel());
					jsonArray.add(jsonObject);
				}
			}
		}
		return jsonArray;
	}

	@Override
	public UserEntity findByCampaignKey(String campaignKey) {
		return userMapper.findByCampaignKey(campaignKey);
	}

}
