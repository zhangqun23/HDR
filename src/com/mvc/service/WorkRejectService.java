package com.mvc.service;


import java.util.Map;

import org.springframework.http.ResponseEntity;

/**
 * 部门员工做房驳回统计业务层
 * 
 * @author zq
 * @date 2016-1-17
 */
public interface WorkRejectService {

	String selectWorkRejectByLimits(Map<String, Object> map);

	String selectWorkRejectAnalyseByLimits(Map<String, Object> map);

	ResponseEntity<byte[]> exportWorRejectBylimits(Map<String, Object> map, String path, String tempPath);

	ResponseEntity<byte[]> exportWorRejectExcelBylimits(Map<String, Object> map);

	ResponseEntity<byte[]> exportWorkRejectAna(Map<String, Object> map, String path, String tempPath, String picPath);
}
