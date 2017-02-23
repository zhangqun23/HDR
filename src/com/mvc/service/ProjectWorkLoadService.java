package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.ProjectWorkLoad;

/**
 * 工程部员工工作量相关Service层接口
 * 
 * @author zjn
 * @date 2017年2月20日
 */
public interface ProjectWorkLoadService {

	// 获取工程部所有员工工作量列表
	List<ProjectWorkLoad> getProWorkLoadList(String startTime, String endTime);

	// 导出工程部所有员工工作量word
	ResponseEntity<byte[]> exportProWorkLoadWord(Map<String, Object> map);

	// 导出工程部所有员工工作量Excel
	ResponseEntity<byte[]> exportProWorkLoadExcel(Map<String, Object> map);

	// 获取工程部单个员工工作量分析图所需数据
	String getProWorkLoadAnalyse(Map<String, String> map);

	// 导出工程部单个员工工作量分析图
	ResponseEntity<byte[]> exportProWorkLoadAnalyse(Map<String, String> map);

	// 获取分析结果
	String getAnalyseResult(List<ProjectWorkLoad> list);

}
