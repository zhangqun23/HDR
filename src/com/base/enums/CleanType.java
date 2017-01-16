package com.base.enums;

/**
 * 打扫类型
 * 
 * @author wangrui
 * @date 2017年1月16日
 */
public enum CleanType {
	抹尘房(0), 离退房(1), 过夜房(2);

	public int value;

	private CleanType(int value) {
		this.value = value;
	}

	public static String intToStr(int value) {
		CleanType cont_state = CleanType.values()[value];
		return cont_state.name();
	}
}
