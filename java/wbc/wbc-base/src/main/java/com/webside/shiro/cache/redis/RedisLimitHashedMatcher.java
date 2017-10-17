package com.webside.shiro.cache.redis;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import com.webside.shiro.cache.ICache;

public class RedisLimitHashedMatcher extends HashedCredentialsMatcher
{
	private ICache cache;
	private static final String prefix = "shiro:login:count:";
	private Long expire = 30 * 60L;// 缓存超时时间

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
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
		boolean matches = super.doCredentialsMatch(token, info);
		
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
