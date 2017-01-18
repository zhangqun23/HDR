package com.mvc.service;

import com.mvc.entity.StaffInfo;

/**
 * 用户
 * 
 * @author lwt
 * @date 2017年1月18日
 */
public interface StaffInfoService {

	// 根据staffNo查询用户账号是否存在,返回1存在，返回0不存在
	Long isExist(String staffNo);
	
	// 根据staffNo查询用户信息
	StaffInfo findByStaffNo(String staffNo);
	
}
