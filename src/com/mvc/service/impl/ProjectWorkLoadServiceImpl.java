/**
 * 
 */
package com.mvc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvc.entityReport.ProjectWorkLoad;
import com.mvc.service.ProjectWorkLoadService;

/**
 * 工程部员工工作量相关Service层实现
 * 
 * @author zjn
 * @date 2017年2月20日
 */
@Service("projectWorkLoadServiceImpl")
public class ProjectWorkLoadServiceImpl implements ProjectWorkLoadService {

	// 获取工程部所有员工工作量列表
	@Override
	public List<ProjectWorkLoad> getProWorkLoadList(String startTime, String endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	// 导出工程部所有员工工作量word
	@Override
	public ResponseEntity<byte[]> exportProWorkLoadWord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	// 导出工程部所有员工工作量word
	@Override
	public ResponseEntity<byte[]> exportProWorkLoadExcel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	// 获取工程部单个员工工作量分析图所需数据
	@Override
	public String getProWorkLoadAnalyse(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	// 导出工程部单个员工工作量分析图
	@Override
	public ResponseEntity<byte[]> exportProWorkLoadAnalyse(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
