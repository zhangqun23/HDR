package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.entity.EngineerCaseSort;
import com.mvc.entityReport.ProjectRepair;

import net.sf.json.JSONObject;

/**
 * 
 * @author wanghuimin
 * @date 2017年2月20日
 */
public interface EngineerRepairService {

	//json转map
	Map<String, Object> JsonObjToMap(JSONObject jsonObject);

	//查询工程维修项统计
	String findEngineerRepair(Map<String, Object> map);

	//查询工程维修项统计维修类型
	List<EngineerCaseSort> findEngineerRepairType();

	//查询工程维修项统计图标
	String getProjectRepairIcon(Map<String, String> map);

}
