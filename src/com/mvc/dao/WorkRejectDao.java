package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entityReport.WorkReject;

/**
 * 部门员工做房统计数据持久层
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public interface WorkRejectDao {

	List<Object> selectWorkRejectByLimits(Map<String, Object> map);
}
