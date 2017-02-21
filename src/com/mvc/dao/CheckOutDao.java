package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.utils.Pager;

/**
 * @author 包阿儒汉
 * 查退房DAO层接口
 */
public interface CheckOutDao {
	List<Object> selectCheckOutEfficiency(Map<String, Object> map);

	List<Object> selectCheckOutDetail(Map<String, Object> map);

	int getTotalRowCountCheckOutDetail(Map<String, Object> map);

	List<Object> avgPerMonthsStaff(String startTime, String endTime, String staffId, String roomType);

	String allAverWorkEfficiency(String startTime, String endTime, String roomType);

	String averWorkEfficiency(String startTime, String endTime, String roomType, String staffId);

	List<Object> selectCheckOutDetailByPage(Map<String, Object> map, Pager pager);
}
