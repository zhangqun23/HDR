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
<<<<<<< HEAD
=======

	/**** 员工工作效率报表 ****/

	// 查询员工工作效率
	List<Object> selectWorkEffByLimits(Map<String, Object> map);

	// 获取员工每个月做房效率
	List<Object> selectMonthHouseEff(Map<String, Object> map);

	// 获取员工每个月工作效率
	List<Object> selectMonthWorkEff(Map<String, Object> map);

	// 获取全体员工平均做房效率
	List<Object> selectAllAverHouseEff(Map<String, Object> map);

	// 获取全体员工平均工作效率
	List<Object> selectAllAverWorkEff(Map<String, Object> map);
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
}
