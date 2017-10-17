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
<script language="JavaScript">
	var _path = '${path}';
	var _staticPrefix = '${staticPrefix}';
	var user = '${user}';		
	var userId = '${userId}';		
	var ip = '${ip}';
	var seoConfigMap = '${seoConfigMap}';
	var _version = '${version}';
	var cookie_nick_name = '${cookie_nick_name}';
	var cookie_login_name = '${cookie_login_name}';
	var cookie_user_photo = '${cookie_user_photo}';
</script>

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
    
<link type="text/css" href="${staticPrefix }/css/bootstrap.min.css?v=${version }" rel="stylesheet">
<link type="text/css" href="${staticPrefix }/css/bootstrap-material-design.css?v=${version }" rel="stylesheet">
<link type="text/css" href="${staticPrefix }/css/base.css?v=${version }" rel="stylesheet">
<link type="text/css" href="${staticPrefix }/css/ripples.min.css?v=${version }" rel="stylesheet">
<link type="text/css" href="http://at.alicdn.com/t/font_cl5ct5ejudptx1or.css?v=${version }" rel="stylesheet">
<link type="text/css" href="${staticPrefix }/css/fallback.css?v=${version }" rel="stylesheet">
<link type="text/css" href="${staticPrefix }/css/nprogress.css?v=${version }" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.mCustomScrollbar.css?v=${version }">
 <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
    <script>
        document.createElement('video');
    </script>
    <![endif]-->
<link href="${ctx }/resources/images/wbc.ico" rel="shortcut icon"/>
<link href="${ctx }/resources/images/wbc.ico" type="image/x-icon" rel="bookmark"/>   
<link href="${ctx }/resources/images/wbc.ico" type="image/x-icon" rel="shortcut icon"/>  
<script type="text/javascript" src="${staticPrefix }/js/jquery.min.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/bootstrap.min.js?v=${version }"></script>
<script type="text/javascript" src="${ctx }/resources/js/layer-v2.3/layer.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/common/base.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/material.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/ripples.min.js?v=${version }"></script>
<%-- <script type="text/javascript" src="${staticPrefix }/js/fakeLoader.min.js?v=${version }"></script> --%>
<script type="text/javascript" src="${staticPrefix }/js/common/captcha.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/jquery.pjax.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/nprogress.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/common/jqPaginator.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/siderbar.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/clipboard.min.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/qrcode/jquery.qrcode.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/qrcode/jquery.utf.js?v=${version }"></script>
<script src="${staticPrefix }/js/jquery.mCustomScrollbar.concat.min.js?v=${version }"></script>
<!-- // 解决shiro登陆超时ajax请求跳转   -->
<script type="text/javascript">
	$.ajaxSetup({
	    complete:function(XMLHttpRequest,textStatus) {
	 		var status = XMLHttpRequest.getResponseHeader("session_status");  
	 		if(status == "timeout") {  
// 	   			var top = getTopWinow();  
// 	            layer.alert("由于您长时间没有操作, session已过期, 请重新登录.", {icon : 5,shift : 6,time : 3000});
// 	            top.location.href = "/login";  
	 			$("#myModal").modal("show");
	        }  
	    }
		
	});
	
	$(function () {
		$("#new-container").css("min-height",document.body.clientHeight-381);
		//header进度条
	    NProgress.start();
	    var tmp_url = window.location.href;
	    tmp_url = tmp_url.substring(ip.length);
	    if(tmp_url != 'index'){
	    	setTimeout(function () {
		        NProgress.done();
		    }, 1000);
	    }
    });
	
	/** 
	 * 在页面中任何嵌套层次的窗口中获取顶层窗口 
	 * @return 当前页面的顶层窗口对象 
	 */  
	function getTopWinow()
	{  
		var p = window;  
	    
	    while(p != p.parent)
	    {  
	        p = p.parent;  
	    }
	    
	    return p;  
	}
	
	
	
	/**
		ajax 错误 error处理
	*/
	function ajaxToLogin(jqXHR) {
		/*弹出jqXHR对象的信息*/
		if(jqXHR.status = "518"){
			layer.confirm('请登录...', {icon: 3, title:'提示'}, function(index){
				if(index > 0) {
				   //getTopWinow().location.href = "/login"; 
				   $("#myModal").modal("show");
				}
				  layer.close(index);
				});
	         //layer.alert("由于您长时间没有操作, session已过期, 请重新登录.", {icon : 5,shift : 6,time : 3000});
	         
      	} else if (jqXHR.status == "611") {
      		layer.alert("重复提交表单!", {icon : 5,shift : 6,time : 3000});
      	}
	}
</script>
