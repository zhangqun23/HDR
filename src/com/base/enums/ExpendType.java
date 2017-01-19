package com.base.enums;

/**
 * 打扫类型
 * 
 * @author wangrui
 * @date 2017年1月16日
 */
public enum ExpendType {
	布草(0), 房间耗品(1), 卫生间耗品(2);

	public int value;

	private ExpendType(int value) {
		this.value = value;
	}

	public static String intToStr(int value) {
		ExpendType cont_state = ExpendType.values()[value];
		return cont_state.name();
	}
}
