package com.mvc.dao;

import java.util.List;
import java.util.Map;

/**
 * 部门员工做房统计数据持久层
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public interface WorkHouseDao {

	// 查询员工做房
	List<Object> selectWorkHouse(Map<String, Object> map);

	// 获取全体员工平均做房用时
	List<Object> selectAllAverWorkTime(Map<String, Object> map);

	// 获取员工每个月做房用时
	List<Object> selectMonthWorkTime(Map<String, Object> map);
}
