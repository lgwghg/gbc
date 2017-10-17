package com.webside.util;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String base64 = FileRWUtil.encodeBase64File("D:\\中国合伙人.flv");
		System.out.println(base64);
		
		
//		System.out.println(DateUtils.getStringDate("2016-09-30 18:13:06").getTime());
//		System.out.println(DateUtils.longToString(1475230386433L));
		
	}

}
