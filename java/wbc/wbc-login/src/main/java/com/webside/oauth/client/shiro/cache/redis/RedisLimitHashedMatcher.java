package com.webside.oauth.client.shiro.cache.redis;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.webside.oauth.client.shiro.support.UsernamePasswordAndClientToken;
import com.webside.oauth.client.shiro.support.UsernamePasswordAndClientToken.TokenType;
import com.webside.shiro.cache.ICache;

public class RedisLimitHashedMatcher extends HashedCredentialsMatcher
{
	private ICache cache;
	private static final String prefix = "shiro:login:count:";
	private Long expire = 30 * 60L;// 缓存超时时间

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		final UsernamePasswordAndClientToken clientToken = (UsernamePasswordAndClientToken) token;
		String username = null;

		if (clientToken.getTokenType() == TokenType.CLIENT) {
			if (StringUtils.isBlank(clientToken.getClientName())) {
				username = clientToken.getUserId();
			} else {
				username = clientToken.getClientName().length() <= 2 ? clientToken.getClientName().toLowerCase() : clientToken.getClientName().substring(0, 2).toLowerCase();
				username += ":" + (String) clientToken.getUserId();
			}
		} else {
			username = (String) token.getPrincipal();
		}

		if (StringUtils.isBlank(username)) {
			return false;
		}

		String cacheName = prefix + username;

		// +1
		String count = cache.get(cacheName);
		AtomicInteger retryCount = null;
		if (count == null) {
			retryCount = new AtomicInteger(0);
			cache.setEx(cacheName, retryCount.toString(), expire);
		} else {
			retryCount = new AtomicInteger(Integer.valueOf(count));
		}
		int m = retryCount.incrementAndGet();
		if (m > 5) {
			// count > 5 throw 5次登陆异常
			throw new ExcessiveAttemptsException();
		}
		/*String credentials = new Md5Hash(token.getCredentials()).toString();
		boolean matches = credentials.equals(getCredentials(info).toString());*/
		boolean matches = false;
		if (clientToken.getTokenType() == TokenType.CLIENT) {
			matches = new SimpleCredentialsMatcher().doCredentialsMatch(token, info);
		} else {
			matches = super.doCredentialsMatch(token, info);
		}
		
		if (matches) {
			// 去掉错误记录
			cache.del(cacheName);
		} 
		else {
			cache.setEx(cacheName, retryCount.toString(), expire);
		}
		
		return matches;
	}

	public ICache getCache() {
		return cache;
	}

	public void setCache(ICache cache) {
		this.cache = cache;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

}
