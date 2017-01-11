package com.base.enums;

/**
 * 统计表日期标记
 * 
 * @author zjn
 * @date 2016年9月27日
 */
public enum DateFlag {

	// 0:全年，1:第一季度，2:第二季度，3:第三季度，4:第四季度
	totalYear(0), firstQuarter(1), secondQuarter(2), thirdQuarter(3), fourQuarter(4);

	public int value;

	private DateFlag(int value) {
		this.value = value;
	}
}
