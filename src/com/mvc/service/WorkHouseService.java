package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

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

	// 获取单个用户做房用时
	String selectUserWorkHouseByLimits(Map<String, Object> map);
}
