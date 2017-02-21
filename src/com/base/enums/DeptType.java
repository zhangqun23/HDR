package com.base.enums;

/**
 * 部门类型
 * 
 * @author zjn
 * @date 2017年2月20日
 */
public enum DeptType {

	客房部("客房部"), 工程部("工程部");

	public String value;

	private DeptType(String value) {
		this.value = value;
	}

}
