package com.webside.shiro;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.webside.common.exception.ForbiddenAccountException;
import com.webside.resource.mapper.ResourceMapper;
import com.webside.resource.model.ResourceEntity;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;


/**
 * 
 * @ClassName: MyRealm
 * @Description: 自定义jdbcRealm,认证&授权
 * @author gaogang
 * @date 2016年7月12日 下午4:30:16
 *
 */
public class MyRealm extends AuthorizingRealm {

	@Inject
	private ResourceMapper resourceMapper;

	@Inject
	private UserService userService;

	/**
	 * 授权信息
	 * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		UserEntity user = ShiroAuthenticationManager.getUserEntity();
		if (user != null) {
			List<ResourceEntity> resourceList = resourceMapper.findResourcesByUserId(user.getId());
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			//根据用户ID查询角色（role），放入到Authorization里。
			// 单角色用户情况
			info.addRole(user.getRole().getName());
			// 多角色用户情况
			// info.setRoles(user.getRolesName());
			// 用户的角色对应的所有权限
			for (ResourceEntity resourceEntity : resourceList) {
				info.addStringPermission(resourceEntity.getSourceKey());
			}
			//或者直接查询出所有权限set集合
			//info.setStringPermissions(permissions);
			return info;
		}
		return null;
	}

	/**
	 * 认证信息,认证回调函数,登录时调用
	 * 首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
	 * 如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
	 * 交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
	 * 如果不匹配将抛出密码错误异常IncorrectCredentialsException；
	 * 另外如果密码重试次数太多将抛出超出重试次数异常ExcessiveAttemptsException；
	 * 在组装SimpleAuthenticationInfo信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、加密盐（username+salt），
	 * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo authenticationInfo = null;
		String loginName = (String)token.getPrincipal();
		
		UserEntity userEntity = null;
		if (StringUtils.isNotBlank(loginName)) {
			if (loginName.indexOf("@") > -1) { // 邮箱登录
				userEntity = userService.findByEmail(loginName);
			} else { // 手机登录
				userEntity = userService.findByMobile(loginName);
			}
		}
		if (userEntity != null) {
			// 用户角色为禁止访问
			if (userEntity.getRole() != null && "forbidden".equals(userEntity.getRole().getKey())) {
				throw new ForbiddenAccountException(); // 帐号被禁止访问
			}
			if ("2".equals(userEntity.getLocked())) {
				throw new LockedAccountException(); // 帐号被锁定
			}
			// 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
			// 当用户执行登录时,在方法处理上要实现subject.login(token);
			// 然后会自动进入这个类进行认证
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得shiro自带的不好可以自定义实现
            authenticationInfo = new SimpleAuthenticationInfo(
            		userEntity, // 用户对象
            		userEntity.getPassword(), // 密码
//					ByteSource.Util.bytes(username + userEntity.getCredentialsSalt()),// salt=username+salt
					ByteSource.Util.bytes(userEntity.getId() + userEntity.getCredentialsSalt()),// salt = userID + salt
					getName() // realm name
			);
            /*
             * NOTE Shiro-redis don't support SimpleAuthenticationInfo created by this constructor 
             * org.apache.shiro.authc.SimpleAuthenticationInfo.SimpleAuthenticationInfo(Object principal, Object hashedCredentials, ByteSource credentialsSalt, String realmName). 
             * Please use org.apache.shiro.authc.SimpleAuthenticationInfo.SimpleAuthenticationInfo(Object principal, Object hashedCredentials, String realmName) instead.
             */
            //TODO：因为shiro-redies 不支持带加密盐方式的验证，所以暂时不使用shiro-redies，后续再研究
            //authenticationInfo.setCredentialsSalt(new MySimpleByteSource(username + userEntity.getCredentialsSalt()));
			return authenticationInfo;
		} else {
			throw new UnknownAccountException();// 没找到帐号
		}

	}
	
	/**
     * 清除当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		super.clearCachedAuthorizationInfo(principalCollection);
	}
	
	/**
     * 清除当前用户认证信息
     */
	public  void clearCachedAuthenticationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		super.clearCachedAuthenticationInfo(principalCollection);
	}
	
	/**
	 * 清除指定 principalCollection 的权限信息
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		super.clearCachedAuthorizationInfo(principalCollection);
	}

	/**
     * 清除用户认证信息
     */
	public void clearCachedAuthenticationInfo(PrincipalCollection principalCollection) {
		super.clearCachedAuthenticationInfo(principalCollection);
	}


	/**
	 * 清除当前用户的认证和授权缓存信息
	 */
	public void clearAllCache() {
		clearCachedAuthorizationInfo();
		clearCachedAuthenticationInfo();
	}

}