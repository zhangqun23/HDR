package com.mvc.dao;

import java.util.List;
import java.util.Map;

<<<<<<< HEAD
import com.mvc.entity.StaffInfo;

=======
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
/**
 * 酒店对客服务信息统计
 * @author wanghuimin
 * @date 2017年1月10日
 */
public interface HotelCustomerDao {

	//查询酒店对客服务信息统计
	List<Object> findHotelService(Map<String, Object> map);

	//查询部门对客服务工作量统计
	List<Object> findDepartmentLoad(Map<String, Object> map);

	//查询部门对客服务类型
	List<Object> findRoomType(Map<String, Object> map);

<<<<<<< HEAD
	//根据部门ID筛选员工信息
	List<Object> findStaffByDepId(String departid);

=======
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
}
