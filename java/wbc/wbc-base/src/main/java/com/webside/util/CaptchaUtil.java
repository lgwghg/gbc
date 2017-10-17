package com.webside.util;

import java.util.Random;

public class CaptchaUtil {
	/**
	 * 获得0-9范围的随机数 4位
	 * 
	 * @return String
	 */
	public static String getNumCaptcha() {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
	}

}
