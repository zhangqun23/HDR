package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.ExpendAnalyse;
import com.mvc.entityReport.LinenExpend;
import com.mvc.entityReport.RoomExpend;
import com.mvc.entityReport.WashExpend;
import com.utils.Pager;


/**
 * 耗品统计
 * @author wq
 * @date 2017年1月13日
 */
public interface ExpendFormService {
	
	//布草统计
	List<LinenExpend> selectLinenExpend(Map<String, Object> map);
	
	//导出布草使用量统计表
	ResponseEntity<byte[]> exportLinenExpendForm(Map<String, Object> map, String path, String tempPath);
	
	//布草统计分析
	List<ExpendAnalyse> selectLinenExpendAnalyse(Map<String, Object> map);
	
	//房间耗品统计
	List<RoomExpend> selectRoomExpend(Map<String, Object> map);
	
	//导出房间耗品使用量统计表
	ResponseEntity<byte[]> exportRoomExpendForm(Map<String, Object> map, String path, String tempPath);
	
	//房间耗品统计分析
	List<ExpendAnalyse> selectRoomExpendAnalyse(Map<String, Object> map);
	
	//卫生间耗品统计
	List<WashExpend> selectWashExpend(Map<String, Object> map);
	
	//导出卫生间耗品使用量统计表
	ResponseEntity<byte[]> exportWashExpendForm(Map<String, Object> map, String path, String tempPath);
	
	//卫生间耗品统计分析
	List<ExpendAnalyse> selectWashExpendAnalyse(Map<String, Object> map);
}
