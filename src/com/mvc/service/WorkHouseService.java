package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.WorkEfficiency;
import com.mvc.entityReport.WorkHouse;

/**
 * 部门员工做房统计业务层
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public interface WorkHouseService {

	// 查询员工做房
	List<WorkHouse> selectWorkHouse(Map<String, Object> map);

	// 部门员工做房用时统计
	ResponseEntity<byte[]> exportWorkHouse(Map<String, Object> map, String path, String tempPath);

	// 获取单个员工做房用时
	String selectUserWorkHouseByLimits(Map<String, Object> map);

	/**** 员工工作效率报表 ****/

	// 查询员工工作效率
	List<WorkEfficiency> selectWorkEffByLimits(Map<String, Object> map);

	// 获取单个员工工作效率
	String selectUserWorkEffByLimits(Map<String, Object> map);
}
