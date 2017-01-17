package com.mvc.dao;

import java.util.List;
import java.util.Map;

/**
 * 酒店对客服务信息统计
 * @author wq
 * @date 2017年1月13日
 */
public interface ExpendFormDao {

	//布草统计
	List<Object> selectlinenExpend(Map<String, Object> map);
	
	//布草统计分析
	List<Object> selectLinenExpendAnalyse(Map<String, Object> map);
	
	//房间耗品统计
	List<Object> selectroomExpend(Map<String, Object> map);
	
	//房间耗品统计分析
	List<Object> selectRoomExpendAnalyse(Map<String, Object> map);
	
	//卫生间耗品统计
	List<Object> selectwashExpend(Map<String, Object> map);
	
	//卫生间耗品统计分析
	List<Object> selectWashExpendAnalyse(Map<String, Object> map);
}
