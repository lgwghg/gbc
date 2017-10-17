/*******************************************************************************
 * All rights reserved. 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.data.spring;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author sunhw
 * 
 */
public class AppContext implements ApplicationContextAware {

	private static AppContext context = new AppContext();

	private ApplicationContext applicationContext = null;

	private static final String iocFilePath = "classpath*:spring/ioc-*.xml";

	public void setApplicationContext(
			final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	private ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			synchronized (applicationContext) {
				if (applicationContext == null) {
					this.initFromClasspathXmlFiles(iocFilePath);
				}
			}
		}
		return applicationContext;
	}

	public static ApplicationContext getSpringContext() {
		return getInstance().getApplicationContext();
	}

	public static AppContext getInstance() {
		return context;
	}

	/**
	 * 根据类型获取对应的实现
	 * 
	 * @param clazz
	 *            可以为接口和类
	 * @return 对应的实现，实现只能有且只有一个
	 */
	public static <T> T getBean(final Class<T> clazz) {
		Map<String, T> beans = getSpringContext().getBeansOfType(clazz);
		if (beans == null || beans.size() == 0) {
			throw new RuntimeException("There is no [" + clazz
					+ "] configuration");
		} else if (beans.size() > 1) {
			throw new RuntimeException("The [" + clazz
					+ "] configuration is more than one");
		}
		return beans.values().iterator().next();
	}

	public ApplicationContext initFromClasspathXmlFiles(final String configFiles) {
		if (applicationContext == null)
			applicationContext = new ClassPathXmlApplicationContext(
					parsePaths(configFiles));
		else {
			throw new RuntimeException("Don't repeat initialization！");
		}

		return applicationContext;
	}

	public ApplicationContext initFromSystemXmlFiles(final String configFiles) {
		if (applicationContext == null)
			applicationContext = new FileSystemXmlApplicationContext(
					parsePaths(configFiles));
		else {
			throw new RuntimeException("Don't repeat initialization！");
		}

		return applicationContext;
	}

	private String[] parsePaths(final String configFiles) {
		return configFiles.split(",|;|:|\\s");
	}
}
