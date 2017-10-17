package com.webside.frame.db.hibernate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validator {
	/**
	 * (可选) 验证类型.
	 */
	AuType value() default AuType.ISNULL;
	/**
	 * (可选) 当验证类型是ISGREATERTHAN时，该值有效.
	 */
	long compare() default 0;

	//验证类型
	enum AuType {

		/** 是否为空 */
		ISNULL,

		/** 是否为空（包括空字符串） */
		ISEMPTY,

		/** 是否大于 */
		ISGREATERTHAN
	}
}

