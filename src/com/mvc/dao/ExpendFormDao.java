package com.mvc.dao;

import java.util.List;
import java.util.Map;

/**
 * 酒店对客服务信息统计
 * 
 * @author wq
 * @date 2017年1月13日
 */
public interface ExpendFormDao {

	// 布草统计分页
	List<Object> selectlinenPage(Map<String, Object> map, Integer offset, Integer end, List<Integer> listCondition);

	// 布草统计
	List<Object> selectlinenExpend(Map<String, Object> map, List<Integer> listCondition);

	// 布草统计分析
	List<Object> selectLinenExpendAnalyse(Map<String, Object> map);

	// 统计条件（布草、房间耗品、卫生间耗品）
	List<Integer> selectCondition(String expendType);

	// 查询布草总条数
	Long countlinenTotal(Map<String, Object> map);
	
	// 查询房间耗品总条数
	Long countroomTotal(Map<String, Object> map);
		
	// 查询卫生间耗品总条数
	Long countwashTotal(Map<String, Object> map);

	//房间耗品统计分页
	List<Object> selectroomPage(Map<String, Object> map, Integer offset, Integer end, List<Integer> listCondition);

	// 房间耗品统计
	List<Object> selectroomExpend(Map<String, Object> map, List<Integer> listCondition);

	// 房间耗品统计分析
	List<Object> selectRoomExpendAnalyse(Map<String, Object> map);

	//卫生间耗品统计分页
	List<Object> selectwashPage(Map<String, Object> map, Integer offset, Integer end, List<Integer> listCondition);
	
	// 卫生间耗品统计
	List<Object> selectwashExpend(Map<String, Object> map, List<Integer> listCondition);

	// 卫生间耗品统计分析
	List<Object> selectWashExpendAnalyse(Map<String, Object> map);

}
