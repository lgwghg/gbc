<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@page import="com.webside.util.CookieUtil"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%@page import="com.webside.util.StringUtils"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.webside.common.config.service.ConfigService" %>
<%@page import="com.webside.util.SpringBeanUtil"%>
<%@page import="com.webside.system.seo.service.ISeoConfigService"%>
<%@page import="com.webside.shiro.ShiroAuthenticationManager"%>
<%@page import="com.webside.user.service.IUserCacheService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("path", request.getContextPath());
	request.setAttribute("ctx", request.getContextPath());
	request.setAttribute("staticPrefix",  request.getContextPath()+"/resources/wbc");
	request.setAttribute("user",  ShiroAuthenticationManager.getUserEntity());
	if (ShiroAuthenticationManager.getUserId() != null && ShiroAuthenticationManager.getUserId() != "") {
		request.setAttribute("userId",  ShiroAuthenticationManager.getUserId());
		IUserCacheService userCacheService = SpringBeanUtil.getBean(IUserCacheService.class);
		request.setAttribute("user",  userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId()));
	}
	
	//seo配置
	ISeoConfigService seoConfigService = SpringBeanUtil.getBean(ISeoConfigService.class);
	request.setAttribute("seoConfigMap",  seoConfigService.list());
	
	//获取当前域名地址
	StringBuffer url = request.getRequestURL();
	String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
	
	if(StringUtils.isNotEmpty(tempContextUrl))
	{
		//判断是否是真实域名
		if(tempContextUrl.indexOf("gbocai") > -1 && tempContextUrl.split(":").length == 3)
		{
			request.setAttribute("ip", tempContextUrl.substring(0, tempContextUrl.lastIndexOf(":")) + "/");
		}
		else
		{
			request.setAttribute("ip", tempContextUrl);
		}
	}
	else
	{
		ConfigService configService = SpringBeanUtil.getBean(ConfigService.class);
		String domainUrl = configService.findConfigValueByKey("domain_url");
		
		if(StringUtils.isNotEmpty(domainUrl))
		{
			JSONObject domainUrlJson = new JSONObject(domainUrl);
			request.setAttribute("ip",  domainUrlJson.get("gbocai"));
		}
	}
	//版本
	request.setAttribute("version", GlobalConstant.VERSION);
	//用户cookies
	String cookie_nick_name = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_NICK_NAME);
	if(StringUtils.isNotBlank(cookie_nick_name)){
		cookie_nick_name = URLDecoder.decode(cookie_nick_name,"UTF-8");
		request.setAttribute(GlobalConstant.COOKIE_NICK_NAME, cookie_nick_name);
	}
	String cookie_user_photo = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_USER_PHOTO);
	if(StringUtils.isNotBlank(cookie_user_photo)){
		cookie_user_photo = URLDecoder.decode(cookie_user_photo,"UTF-8");
		request.setAttribute(GlobalConstant.COOKIE_USER_PHOTO, cookie_user_photo);
	}
	String cookie_login_name = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_LOGIN_NAME);
	if(StringUtils.isNotBlank(cookie_login_name)){
		cookie_login_name = URLDecoder.decode(cookie_login_name,"UTF-8");
		request.setAttribute(GlobalConstant.COOKIE_LOGIN_NAME, cookie_login_name);
	}
%>

<script>
	(function(){
	    var bp = document.createElement('script');
	    var curProtocol = window.location.protocol.split(':')[0];
	    if (curProtocol === 'https') {
	        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';        
	    }
	    else {
	        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
	    }
	    var s = document.getElementsByTagName("script")[0];
	    s.parentNode.insertBefore(bp, s);
	})();
</script>

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-91489703-1', 'auto');
  ga('send', 'pageview');
</script>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta property="wb:webmaster" content="1b546de972a961f4" />
<meta name="baidu-site-verification" content="LajTczLfXc" />