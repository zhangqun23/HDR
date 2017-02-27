/**
 * 
 */
package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.CheckOutDetail;
import com.mvc.entityReport.CheckOutEfficiency;
import com.utils.Pager;

/**
 * @author 包阿儒汉
 *
 */
public interface CheckOutService {

	List<CheckOutEfficiency> selectCheckOutEfficiency(Map<String, Object> map);

	List<CheckOutDetail> selectCheckOutDetail(Map<String, Object> map, Pager pager);

	int getTotalRowCountCheckOutDetail(Map<String, Object> map);

	String selectCheckOutEffAnalyseByLimits(Map<String, String> map);

	ResponseEntity<byte[]> exportCheckOutEfficiency(Map<String, Object> map, String path, String tempPath);

	ResponseEntity<byte[]> exportCheckOutDetail(Map<String, Object> map, String path, String tempPath);

	ResponseEntity<byte[]> exportCheckOutAnalyseByLimits(Map<String, Object> map, String path, String tempPath,
			String picPath);

	ResponseEntity<byte[]> exportCheckOutDetailExcel(Map<String, Object> map, String path);

	ResponseEntity<byte[]> exportCheckOutEfficiencyExcel(Map<String, Object> map, String path);

	String getAnalyseResult(List<CheckOutEfficiency> list, String writeField);
}
