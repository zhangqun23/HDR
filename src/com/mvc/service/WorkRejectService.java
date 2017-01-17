package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.WorkEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.entityReport.WorkReject;

/**
 * 部门员工做房统计业务层
 * 
 * @author wangrui
 * @date 2016-12-08
 */
public interface WorkRejectService {

	List<WorkReject> selectWorkRejectByLimits(Map<String, Object> map);
}
