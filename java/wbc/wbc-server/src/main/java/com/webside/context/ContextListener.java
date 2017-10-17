package com.webside.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.webside.system.sn.service.ISysNoticeService;
import com.webside.system.sw.service.ISensitiveWordsService;
import com.webside.util.SpringBeanUtil;

public class ContextListener implements ServletContextListener
{
	public static final Logger logger = Logger.getLogger(ContextListener.class);
	private ServletContext context = null;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) 
	{
		this.context = servletContextEvent.getServletContext();
		logger.debug("ContextListener contextInitialized .....");
		//初始化redis中的通知集合
		ISysNoticeService sysNoticeService = SpringBeanUtil.getBean(ISysNoticeService.class);
		sysNoticeService.initCacheList();
		//初始化敏感词集合
		ISensitiveWordsService sensitiveWordsService = SpringBeanUtil.getBean(ISensitiveWordsService.class);
		sensitiveWordsService.initSensitiveWordToRedis();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) 
	{
		logger.debug("ContextListener contextDestroyed .....");
		this.context = null;
	}

	public ServletContext getContext() 
	{
		return this.context;
	}
}