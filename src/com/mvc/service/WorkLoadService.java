/**
 * 
 */
package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkLoadLevel;

/**
 * 工作量相关的service层接口
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public interface WorkLoadService {

	// 获取所有员工工作量汇总列表信息
	List<WorkLoad> getWorkLoadSummaryList(String startTime, String endTime);

	// 导出所有员工工作量汇总表
	ResponseEntity<byte[]> exportWorkLoadSummaryList(Map<String, Object> map);

	// 获取所有员工工作量饱和度分析列表
	List<WorkLoadLevel> getWorkLoadLevelList(String startTime, String endTime);

	// 导出所有员工工作量饱和度分析表
	ResponseEntity<byte[]> exportWorkLoadLevelList(Map<String, Object> map);

	// 获取员工工作量分析图所需数据
	Map<String, Object> getWorkLoadAnalyseInfo(Map<String, String> map);
}
