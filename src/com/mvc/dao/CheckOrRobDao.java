/**
 * 
 */
package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.utils.Pager;

/**
 * @author 包阿儒汉
 *
 */
public interface CheckOrRobDao {

	List<Object> selectRobEfficiency(Map<String, Object> map);

	List<Object> selectRobDetail(Map<String, Object> map);

	int getTotalRowCountRobDetail(Map<String, Object> map);

	List<Object> avgPerMonthsStaff(String startTime, String endTime, String staffId, String roomType);

	String allAverWorkEfficiency(String startTime, String endTime, String roomType);

	String averWorkEfficiency(String startTime, String endTime, String roomType, String staffId);

	List<Object> selectRobDetailByPage(Map<String, Object> map, Pager pager);

}
