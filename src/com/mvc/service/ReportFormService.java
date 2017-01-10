package com.mvc.service;

import java.util.List;

import com.mvc.entity.RoomSort;
import com.mvc.entity.StaffInfo;

/**
 * 报表公共类业务层
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface ReportFormService {

	// 获取房型列表
	List<RoomSort> selectRoomSort();

	// 获取人员列表
	List<StaffInfo> selectAllStaff();
}
