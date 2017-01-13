package com.mvc.dao;

import java.util.List;

/**
 * 工作量相关的dao层接口
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public interface WorkLoadDao {

	// 获取员工工作量汇总列表
	List<Object> getWorkRecordSummary(String startTime, String endTime);

	// 获取全体员工实际总工作量
	Float getTotalActualWorkLoad(String startTime, String endTime);

	// 获取某个员工每个月的实际总工作量、额定总工作量
	List<Object> getMonthWorkLoad(String startTime, String endTime, Integer staffId);

	// 获取员工总数
	Integer staffCount(String startTime, String endTime);

}
