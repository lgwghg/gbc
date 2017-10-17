package com.webside.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * 
 * <p>Description: 判断登录</p>
 * <p>Company: 静之殇工作室</p>
 * @author wjggwm
 * @date 2016年7月12日 上午11:34:40
 */
public class UserFilter extends AccessControlFilter
{
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception 
	{
		if (ShiroFilterUtils.isAjax(request))// ajax请求
		{
			return Boolean.TRUE;
		}

		//保存Request到登录后的链接
		saveRequest(request);
		return Boolean.TRUE ;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
	{
		return Boolean.TRUE ;
	}
}
