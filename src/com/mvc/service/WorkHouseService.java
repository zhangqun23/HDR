package com.mvc.service;

import java.util.List;

import com.mvc.entityReport.WorkHouse;

/**
 * 部门员工做房统计业务层
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public interface WorkHouseService {

	// 查询员工做房
	List<WorkHouse> selectWorkHouse();
}
