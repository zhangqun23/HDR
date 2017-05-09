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

	// 布草总数统计
	List<Object> linenTotleCount(Map<String, Object> map, List<Integer> listCondition);
	
	// 房间耗品总数统计
	List<Object> roomTotleCount(Map<String, Object> map, List<Integer> listCondition);
		
	// 卫生间耗品总数统计
	List<Object> washTotleCount(Map<String, Object> map, List<Integer> listCondition);
	
	// 迷你吧总数统计
	List<Object> miniTotleCount(Map<String, Object> map);
	
	// 布草统计分页
	List<Object> selectlinenPage(Map<String, Object> map, Integer offset, Integer end, List<Integer> listCondition);

	// 布草统计(用于导出)
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
	
	// 查询迷你吧总条数
	Long countminiTotal(Map<String, Object> map);
	
	// 查询员工消耗耗品总条数
	Long countStaTotal(Map<String, Object> map);

	// 房间耗品统计分页
	List<Object> selectroomPage(Map<String, Object> map, Integer offset, Integer end, List<Integer> listCondition);

	// 房间耗品统计(用于导出)
	List<Object> selectroomExpend(Map<String, Object> map, List<Integer> listCondition);

	// 房间耗品统计分析
	List<Object> selectRoomExpendAnalyse(Map<String, Object> map);

	// 卫生间耗品统计分页
	List<Object> selectwashPage(Map<String, Object> map, Integer offset, Integer end, List<Integer> listCondition);
	
	// 卫生间耗品统计(用于导出)
	List<Object> selectwashExpend(Map<String, Object> map, List<Integer> listCondition);

	// 卫生间耗品统计分析
	List<Object> selectWashExpendAnalyse(Map<String, Object> map);
	
	// 迷你吧统计分页
	List<Object> selectminiPage(Map<String, Object> map, Integer offset, Integer end);

	// 迷你吧统计(用于导出)
	List<Object> selectminiExpend(Map<String, Object> map);
	
	// 卫生间耗品统计分析
	List<Object> selectMiniExpendAnalyse(Map<String, Object> map);
	
	// 员工消耗布草量、房间耗品、卫生间耗品(用于导出)
	List<Object> selectStaExpend(Map<String, Object> map, List<Integer> listCondition);
	
	// 员工消耗迷你吧量(用于导出)
	List<Object> selectStaMini(Map<String, Object> map);

	// 员工领取耗品总数
	List<Object> selectExpendGet(Map<String, Object> map, List<Integer> listCondition);

	//得到迷你吧物品id
	List<Integer> selectMiniCondition();

	//判断是否有查询结果，防止sql查询时遍历整个数据库
	boolean getstaffisnull(Map<String, Object> map, List<Integer> listCondition);

	//判断是否有查询结果，防止sql查询时遍历整个数据库
	boolean getroomisnull(Map<String, Object> map, List<Integer> listCondition);

}
