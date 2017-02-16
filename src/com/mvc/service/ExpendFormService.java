package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.ExpendAnalyse;
import com.mvc.entityReport.LinenCount;
import com.mvc.entityReport.LinenExpend;
import com.mvc.entityReport.MiniCount;
import com.mvc.entityReport.MiniExpend;
import com.mvc.entityReport.RoomCount;
import com.mvc.entityReport.RoomExpend;
import com.mvc.entityReport.WashCount;
import com.mvc.entityReport.WashExpend;
import com.utils.Pager;

/**
 * 耗品统计
 * 
 * @author wq
 * @date 2017年1月13日
 */
public interface ExpendFormService {

	// 布草总数统计
	LinenCount linenTotleCount(Map<String, Object> map);
	
	// 房间耗品总数统计
	RoomCount roomTotleCount(Map<String, Object> map);
		
	// 卫生间耗品总数统计
	WashCount washTotleCount(Map<String, Object> map);
	
	// 迷你吧总数统计
	MiniCount miniTotleCount(Map<String, Object> map);

	// 布草统计分页
	List<LinenExpend> selectLinenPage(Map<String, Object> map, Pager pager);

	// 导出布草使用量统计表
	ResponseEntity<byte[]> exportLinenExpendForm(Map<String, Object> map, String path, String tempPath);

	// 查询布草总条数
	Long countlinenTotal(Map<String, Object> map);

	// 查询房间耗品总条数
	Long countroomTotal(Map<String, Object> map);

	// 查询卫生间耗品总条数
	Long countwashTotal(Map<String, Object> map);
	
	// 查询迷你吧总条数
	Long countminiTotal(Map<String, Object> map);

	// 布草统计分析
	List<ExpendAnalyse> selectLinenExpendAnalyse(Map<String, Object> map);

	// 房间耗品统计分页
	List<RoomExpend> selectRoomExpend(Map<String, Object> map, Pager pager);

	// 导出房间耗品使用量统计表
	ResponseEntity<byte[]> exportRoomExpendForm(Map<String, Object> map, String path, String tempPath);

	// 房间耗品统计分析
	List<ExpendAnalyse> selectRoomExpendAnalyse(Map<String, Object> map);

	// 卫生间耗品统计分页
	List<WashExpend> selectWashExpend(Map<String, Object> map, Pager pager);

	// 导出卫生间耗品使用量统计表
	ResponseEntity<byte[]> exportWashExpendForm(Map<String, Object> map, String path, String tempPath);

	// 卫生间耗品统计分析
	List<ExpendAnalyse> selectWashExpendAnalyse(Map<String, Object> map);
	
	// 迷你吧统计分页
	List<MiniExpend> selectMiniPage(Map<String, Object> map, Pager pager);

	/********** zjn添加 **********/
	// 导出房间或卫生间耗品分析图
	ResponseEntity<byte[]> exportRoomOrWashExpendPic(Map<String, String> map);

	// 导出布草用量分析图
	ResponseEntity<byte[]> exportLinenExpendPic(Map<String, String> map);
}
