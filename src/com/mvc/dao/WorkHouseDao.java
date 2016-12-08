package com.mvc.dao;

import java.util.List;

import com.mvc.entity.CaseInfo;

/**
 * 部门员工做房统计数据持久层
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public interface WorkHouseDao {

	// 查询员工做房
	List<CaseInfo> selectWorkHouse();
}
