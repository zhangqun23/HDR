/**
 * 
 */
package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.RobDetail;
import com.mvc.entityReport.RobEfficiency;
import com.utils.Pager;

/**
 * @author 包阿儒汉
 *
 */

public interface CheckOrRobService {

	List<RobEfficiency> selectRobEfficiency(Map<String, Object> map);

	List<RobDetail> selectRobDetailByLimits(Map<String, Object> map, Pager pager);

	int getTotalRowCountRobDetail(Map<String, Object> map);

	String selectRobEffAnalyseByLimits(Map<String, String> map);

	ResponseEntity<byte[]> exportRobEfficiency(Map<String, Object> map, String path, String tempPath);

	ResponseEntity<byte[]> exportRobDetail(Map<String, Object> map, String path, String tempPath);

	ResponseEntity<byte[]> exportRobAnalyseByLimits(Map<String, Object> map, String path, String tempPath,
			String picPath);

	ResponseEntity<byte[]> exportRobDetailExcel(Map<String, Object> map, String path);

	ResponseEntity<byte[]> exportRobEfficiencyExcel(Map<String, Object> map, String path);

	String getAnalyseResult(List<RobEfficiency> list, String writeField);

}
