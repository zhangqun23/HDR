package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.HouseCustomerServiceLoad;
import com.mvc.entityReport.HouseCustomerServiceType;

import net.sf.json.JSONObject;

/**
 * 酒店对客服务信息统计
 * @author wanghuimin
 * @date 2017年1月10日
 */
public interface HotelCustomerService {

	//将json转换为Map
	Map<String, Object> JsonObjToMap(JSONObject jsonObject);

	//查询酒店对客服务信息统计
	List<HoCustomerService> findHotelService(Map<String, Object> map);

	//查询部门对客服务工作量统计
	List<HouseCustomerServiceLoad> findDepartmentLoad(Map<String, Object> map);

	//查询部门对客服务类型
	List<HouseCustomerServiceType> findRoomType(Map<String, Object> map);

}
