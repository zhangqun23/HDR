package com.mvc.dao;

import java.util.List;

/**
 * 工作量相关的dao层接口
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public interface WorkLoadDao {

	/*********************** 客房部工作量相关 ********************************/
	// 获取客房部员工工作量汇总列表
	List<Object> getWorkRecordSummary(String startTime, String endTime);

	// 获取客房部全体员工实际总工作量
	Float getTotalActualWorkLoad(String startTime, String endTime);

	// 获取客房部某个员工每个月的实际总工作量、额定总工作量
	List<Object> getMonthWorkLoad(String startTime, String endTime, Integer staffId);

	// 获取统计的客房部员工总数
	Integer staffCount(String startTime, String endTime);

	// 获取员工打扫的房间数信息
	List<Object> getWorkRoomNumList(String startTime, String endTime);

	/*********************** 工程部工作量相关 ********************************/
	// 获取工程部员工工作量汇总列表
	List<Object> getProWorkLoadInfo(String startTime, String endTime);

	// 获取工程部全体员工实际总工作量
	Float getTotalActualProWorkLoad(String startTime, String endTime);

	// 获取工程部某个员工每个月的实际总工作量
	List<Object> getMonthProWorkLoad(String startTime, String endTime, Integer staffId);

	// 获取统计的工程部员工总数
	Integer proStaffCount(String startTime, String endTime);

}
