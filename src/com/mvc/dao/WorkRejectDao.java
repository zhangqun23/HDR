package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entityReport.WorkReject;

/**
 * 部门员工做房驳回统计数据持久层
 * 
 * @author zq
 * @date 2017-1-17
 */
public interface WorkRejectDao {

	List<Object> selectWorkRejectByLimits(Map<String, Object> map);

	List<Object> selectMonthWorkReject(Map<String, Object> map);

	List<Object> selectAllAverRejectEff(Map<String, Object> map);

	List<Object> selectReasonsByLimits(Map<String, Object> map);
}
