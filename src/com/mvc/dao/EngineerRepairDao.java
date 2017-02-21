package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entity.EngineerCaseSort;

/**
 * 工程维修项统计分析数据持久层
 * @author wanghuimin
 * @date 2017年2月20日
 */
public interface EngineerRepairDao {

	//查询工程维修项统计
	List<Object> getEngineerRepairList(Map<String, Object> map);

	//工程维修项统计服务类型
	List<EngineerCaseSort> getEngineerRepairTypeList();

}
