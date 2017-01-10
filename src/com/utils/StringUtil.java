package com.utils;

import java.math.BigDecimal;

/**
 * 字符串工具类
 * 
 * @author wangrui
 * @date 2016-11-17
 */
public class StringUtil {

	/**
	 * 判读字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static Boolean strIsNotEmpty(String s) {
		Boolean flag = true;
		if (s == null || s.length() <= 0) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 4舍5入保留2位小数
	 * 
	 * @param fl
	 * @return
	 */
	public static Float save2Float(Float fl) {
		Float result = 0.0f;
		if (fl != null) {
			String str = String.format("%.2f", fl);
			result = Float.valueOf(str);
		}
		return result;
	}

	/**
	 * 判断对象为空或者为0
	 * 
	 * @param obj
	 * @return
	 */
	public static Boolean isNullOrZero(Object obj) {
		Boolean flag = false;
		if (obj == null || obj.toString().equals("0")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 相加
	 * 
	 * @param s1
	 * @param s2
	 * @return s1+s2
	 */
	public static String add(String s1, String s2) {
		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);
		return b1.add(b2).toString();
	}

	/**
	 * 相减
	 * 
	 * @param s1
	 * @param s2
	 * @return s1-s2
	 */
	public static String sub(String s1, String s2) {
		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 相除
	 * 
	 * @param s1
	 * @param s2
	 * @return s1/s2
	 */
	public static String divide(String s1, String s2) {
		String str = "0";
		if (!s2.equals("0")) {
			BigDecimal b1 = new BigDecimal(s1);
			BigDecimal b2 = new BigDecimal(s2);
			str = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toString();// 四舍五入，保留2位小数
		}
		return str;
	}

	/**
	 * 相乘
	 * 
	 * @param s1
	 * @param s2
	 * @return s1*s2
	 */
	public static String multiply(String s1, String s2) {
		BigDecimal b1 = new BigDecimal(s1);
		BigDecimal b2 = new BigDecimal(s2);
		return b1.multiply(b2).toString();
	}

}
