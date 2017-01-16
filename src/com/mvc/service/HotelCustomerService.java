package com.mvc.service;

import java.util.List;
import java.util.Map;

<<<<<<< HEAD
import org.springframework.http.ResponseEntity;

import com.mvc.entity.DepartmentInfo;
import com.mvc.entity.StaffInfo;
=======
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.HouseCustomerServiceLoad;
import com.mvc.entityReport.HouseCustomerServiceType;

import net.sf.json.JSONObject;

/**
 * 酒店对客服务信息统计
<<<<<<< HEAD
 * 
=======
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
 * @author wanghuimin
 * @date 2017年1月10日
 */
public interface HotelCustomerService {

<<<<<<< HEAD
	// 将json转换为Map
	Map<String, Object> JsonObjToMap(JSONObject jsonObject);

	// 查询酒店对客服务信息统计
	List<HoCustomerService> findHotelService(Map<String, Object> map);

	// 查询部门对客服务工作量统计
	List<HouseCustomerServiceLoad> findDepartmentLoad(Map<String, Object> map);

	// 查询部门对客服务类型
	List<HouseCustomerServiceType> findRoomType(Map<String, Object> map);

	// 导出酒店对客服务信息统计表
	ResponseEntity<byte[]> exportCustomerService(Map<String, Object> map, String path, String modelPath);

	// 导出部门对客服务工作量统计表
	ResponseEntity<byte[]> exportRoomWorkload(Map<String, Object> map, String path, String modelPath);

	// 导出部门对客服务服务类型统计表
	ResponseEntity<byte[]> exportRoomType(Map<String, Object> map, String path, String modelPath);

	// 查询部门列表
	List<DepartmentInfo> findDep();

	// 根据部门ID筛选员工信息
	List<Object> findStaffByDepId(String departid);

=======
	//将json转换为Map
	Map<String, Object> JsonObjToMap(JSONObject jsonObject);

	//查询酒店对客服务信息统计
	List<HoCustomerService> findHotelService(Map<String, Object> map);

	//查询部门对客服务工作量统计
	List<HouseCustomerServiceLoad> findDepartmentLoad(Map<String, Object> map);

	//查询部门对客服务类型
	List<HouseCustomerServiceType> findRoomType(Map<String, Object> map);

>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
}
