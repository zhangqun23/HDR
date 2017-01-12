package com.mvc.dao;

import java.util.List;

/**
 * 工作量相关的dao层接口
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public interface WorkLoadDao {

	// 获取员工打扫各类房间的数量列表
	List<Object> getRoomNumByPrame(String startTime, String endTime);
}
