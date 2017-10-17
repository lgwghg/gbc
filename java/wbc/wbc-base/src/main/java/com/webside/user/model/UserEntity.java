package com.webside.user.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.webside.role.model.RoleEntity;

/**
 * 
 * @ClassName: UserEntity
 * @Description: 用户账户信息
 * @author gaogang
 * @date 2016年7月12日 下午2:39:12
 * 
 */
@Alias("userEntity")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickName; // 用户昵称
	private String mobile; // 手机号码
	private String photo; // 头像
	private String email; // 邮箱
	private String sign; // 签名
	private String steamKey; // STEAM_KEY
	private String qqKey; // QQ_KEY
	private String wechatKey; // WECHAT_KEY
	private String weiboKey; // WEIBO_KEY
	private String steamNick; // steam 账号昵称
	private String qqNick; // qq账号昵称
	private String wechatNick; // 微信 账号昵称
	private String weiboNick; // 微博 账号昵称
	private Integer isDeleted = 0; // 用户是否被删除 默认值0 0：未删除 1：已删除
	private String rankLevel;// 头衔id
	private String campaignKey;// 推广key
	private String newPassword; // 新密码
	private int loginMethod; // 登录方式 0：手机登录，1：邮箱登录 2：STEAM_KEY 3：QQ_KEY
								// 4：WECHAT_KEY 5：WEIBO_KEY
	public final static int LOGIN_METHOD_BY_MOBILE = 0;
	public final static int LOGIN_METHOD_BY_EMAIL = 1;
	public final static int LOGIN_METHOD_BY_STEAM = 2;
	public final static int LOGIN_METHOD_BY_QQ = 3;
	public final static int LOGIN_METHOD_BY_WECHAT = 4;
	public final static int LOGIN_METHOD_BY_WEIBO = 5;
	/**
	 * 用户id
	 */
	private String id;
	/*
	 * 密码
	 */
	private String password;
	/**
	 * 支付密码
	 */
	private String payPassword;
	/**
	 * 支付密码的加密盐
	 */
	private String payPasswordSalt;
	/*
	 * 加密盐
	 */
	private String credentialsSalt;
	/*
	 * 是否锁定：0：正常；1：锁定
	 */
	private Integer locked;
	/*
	 * 这里使用accountName
	 */
	private String creatorName;
	private String updatorName;
	/*
	 * 创建时间
	 */
	private Long createTime;
	/*
	 * 更新时间
	 */
	private Long updateTime;
	/*
	 * 所属角色
	 */
	private RoleEntity role;
	/*
	 * 个人增量信息
	 */
	private UserIncrement userIncrement;
	/*
	 * 前端列表页使用
	 */
	private String roleName;
	/** 支付宝账号 */
	private String alipayAccount;
	
	public UserEntity() {

	}

	public UserEntity(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.password = userEntity.getPassword();
		this.credentialsSalt = userEntity.getCredentialsSalt();
		this.creatorName = userEntity.getCreatorName();
		this.createTime = userEntity.getCreateTime();
		this.updateTime = userEntity.getUpdateTime();
		this.role = userEntity.getRole();
		this.userIncrement = userEntity.getUserIncrement();
		this.roleName = userEntity.getRoleName();
	}

	private Date dateCreateTime;
	private Date dateUpdateTime;

	public void setDateCreateTime(Date dateCreateTime) {
		this.dateCreateTime = dateCreateTime;
	}

	public void setDateUpdateTime(Date dateUpdateTime) {
		this.dateUpdateTime = dateUpdateTime;
	}

	public Date getDateCreateTime() {
		if (this.createTime != null) {
			Date createT = new Date(this.createTime);
			return createT;
		}
		return null;
	}

	public Date getDateUpdateTime() {
		if (this.updateTime != null) {
			return new Date(this.updateTime);
		}
		return null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
		// 设置角色名称,dtgrid使用
		if (role != null) {
			this.roleName = role.getName();
		}
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public String getPhoto_170() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(photo)) {
			if(photo.lastIndexOf(".") < 0) return null;
			String name  = photo.substring(0, photo.lastIndexOf("."));
			String fix   = photo.substring(photo.lastIndexOf(".")+1, photo.length());
			newPhoto = name + "_170." + fix;
		}
		return newPhoto;
	}
	
	public String getPhoto_65() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(photo)) {
			if(photo.lastIndexOf(".") < 0) return null;
			String name  = photo.substring(0, photo.lastIndexOf("."));
			String fix   = photo.substring(photo.lastIndexOf(".")+1, photo.length());
			newPhoto = name + "_65." + fix;
		}
		return newPhoto;
	}

	
	public String getPhoto_35() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(photo)) {
			if(photo.lastIndexOf(".") < 0) return null;
			String name  = photo.substring(0, photo.lastIndexOf("."));
			String fix   = photo.substring(photo.lastIndexOf(".")+1, photo.length());
			newPhoto = name + "_35." + fix;
		}
		return newPhoto;
	}
	
	public String getPhoto_18() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(photo)) {
			if(photo.lastIndexOf(".") < 0) return null;
			String name  = photo.substring(0, photo.lastIndexOf("."));
			String fix   = photo.substring(photo.lastIndexOf(".")+1, photo.length());
			newPhoto = name + "_18." + fix;
		}
		return newPhoto;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSteamKey() {
		return steamKey;
	}

	public void setSteamKey(String steamKey) {
		this.steamKey = steamKey;
	}

	public String getQqKey() {
		return qqKey;
	}

	public void setQqKey(String qqKey) {
		this.qqKey = qqKey;
	}

	public String getWechatKey() {
		return wechatKey;
	}

	public void setWechatKey(String wechatKey) {
		this.wechatKey = wechatKey;
	}

	public String getWeiboKey() {
		return weiboKey;
	}

	public void setWeiboKey(String weiboKey) {
		this.weiboKey = weiboKey;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public int getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(int loginMethod) {
		this.loginMethod = loginMethod;
	}

	public String getUpdatorName() {
		return updatorName;
	}

	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}

	public UserIncrement getUserIncrement() {
		return userIncrement;
	}

	public void setUserIncrement(UserIncrement userIncrement) {
		this.userIncrement = userIncrement;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public String getSteamNick() {
		return steamNick;
	}

	public void setSteamNick(String steamNick) {
		this.steamNick = steamNick;
	}

	public String getQqNick() {
		return qqNick;
	}

	public void setQqNick(String qqNick) {
		this.qqNick = qqNick;
	}

	public String getWechatNick() {
		return wechatNick;
	}

	public void setWechatNick(String wechatNick) {
		this.wechatNick = wechatNick;
	}

	public String getWeiboNick() {
		return weiboNick;
	}

	public void setWeiboNick(String weiboNick) {
		this.weiboNick = weiboNick;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getRankLevel() {
		return rankLevel;
	}

	public void setRankLevel(String rankLevel) {
		this.rankLevel = rankLevel;
	}

	public String getCampaignKey() {
		return campaignKey;
	}

	public void setCampaignKey(String campaignKey) {
		this.campaignKey = campaignKey;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getPayPasswordSalt() {
		return payPasswordSalt;
	}

	public void setPayPasswordSalt(String payPasswordSalt) {
		this.payPasswordSalt = payPasswordSalt;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", nickName=" + nickName + ", createTime=" + createTime + ", updateTime=" + updateTime + ", role=" + role + "]";
	}

}
