package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkRoomNum;

/**
 * 客房部员工工作量相关的service层接口
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public interface WorkLoadService {

	// 获取所有员工工作量汇总列表信息
	List<WorkLoad> getWorkLoadSummaryList(String startTime, String endTime);

	// 导出所有员工工作量汇总表Word
	ResponseEntity<byte[]> exportWorkLoadSummaryWord(Map<String, Object> map);

	// 导出所有员工工作量汇总表excel
	ResponseEntity<byte[]> exportWorkLoadSummaryExcel(Map<String, Object> map);

	// 获取所有员工工作量饱和度分析列表
	String getWorkLoadLevelList(String startTime, String endTime);

	// 导出所有员工工作量饱和度分析表world
	ResponseEntity<byte[]> exportWorkLoadLevelWord(Map<String, Object> map);

	// 导出所有员工工作量饱和度分析表excel
	ResponseEntity<byte[]> exportWorkLoadLevelExcel(Map<String, Object> map);

	// 获取员工工作量分析图所需数据
	String getWorkLoadAnalyseInfo(Map<String, String> map);

	// 导出员工工作量分析图
	ResponseEntity<byte[]> exportWorkLoadAnalyse(Map<String, String> map);

	// 获取员工打扫的房间数统计信息
	List<WorkRoomNum> getWorkRoomNumInfo(String startTime, String endTime);

	// 导出员工打扫的房间数统计信息Word
	ResponseEntity<byte[]> exportWorkRoomNumWord(Map<String, Object> map);

	// 导出员工打扫的房间数统计信息Excel
	ResponseEntity<byte[]> exportWorkRoomNumExcel(Map<String, Object> map);

}
