package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entity.DepartmentInfo;
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.HouseCustomerServiceLoad;
import com.mvc.entityReport.HouseCustomerServiceType;

import net.sf.json.JSONObject;

/**
 * 酒店对客服务信息统计
 * 
 * @author wanghuimin
 * @date 2017年1月10日
 */
public interface CustomerServiceService {

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
	ResponseEntity<byte[]> exportRoomType(Map<String, Object> map, String path,String picPath, String modelPath);

	// 查询部门列表
	List<DepartmentInfo> findDep();

	// 根据部门ID筛选员工信息
	List<Object> findStaffByDepId(String departid);

	//导出酒店对客服务信息excel统计表
	ResponseEntity<byte[]> exportCustomerServiceExcel(Map<String, Object> map, String path);

	//导出部门对客服务工作量excel统计表
	ResponseEntity<byte[]> exportRoomWorkloadExcel(Map<String, Object> map, String path);

	//导出部门对客服务类型excel统计表
	ResponseEntity<byte[]> exportRoomTypeExcel(Map<String, Object> map, String path);
	
	//得出服务类型文字分析
	String listtypeToListGoalWord(Map<String, Object> map);

}
