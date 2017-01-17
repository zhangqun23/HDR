package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.LinenExpend;
import com.mvc.entityReport.RoomExpend;
import com.mvc.entityReport.WashExpend;


/**
 * 耗品统计
 * @author wq
 * @date 2017年1月13日
 */
public interface ExpendFormService {
	
	//布草统计
	List<LinenExpend> selectLinenExpend(Map<String, Object> map);
	
	//导出布草统计
	ResponseEntity<byte[]> exportLinenExpendForm(Map<String, Object> map, String path, String tempPath);
	
	//房间耗品统计
	List<RoomExpend> selectRoomExpend(Map<String, Object> map);
	
	//卫生间耗品统计
	List<WashExpend> selectWashExpend(Map<String, Object> map);

	
}
