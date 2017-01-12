package com.mvc.dao;

import java.util.List;

import com.mvc.entity.StaffInfo;

/**
 * 报表公共类数据持久层
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface ReportFormDao {

	// 获取客房部员工
	List<StaffInfo> selectRoomStaff(String department_name);
}
