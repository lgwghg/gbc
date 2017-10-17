package com.webside.shiro.filter;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import com.webside.user.mapper.UserMapper;
/**
 * 
 * @author wjggwm
 *
 */
public class SysUserFilter extends PathMatchingFilter {

	@Inject
	private UserMapper userMapper;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String userName = (String)SecurityUtils.getSubject().getPrincipal();
        if (userName.indexOf("@") >= 0) {
        	request.setAttribute("user", userMapper.findByEmail(userName));
        } else {
        	request.setAttribute("user", userMapper.findByMobile(userName));
        }
        return true;
    }
}