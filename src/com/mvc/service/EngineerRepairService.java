package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.entityReport.EngineerRepair;

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
	List<EngineerRepair> findEngineerRepair(Map<String, Object> map);

}
