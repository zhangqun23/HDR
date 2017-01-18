package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entity.StaffInfo;

/**
 * 酒店对客服务信息统计
 * @author wanghuimin
 * @date 2017年1月10日
 */
public interface CustomerServiceDao {

	//查询酒店对客服务信息统计
	List<Object> findHotelService(Map<String, Object> map);

	//查询部门对客服务工作量统计
	List<Object> findDepartmentLoad(Map<String, Object> map);

	//查询部门对客服务类型
	List<Object> findRoomType(Map<String, Object> map);

	//根据部门ID筛选员工信息
	List<Object> findStaffByDepId(String departid);

}
