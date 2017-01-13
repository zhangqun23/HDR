package com.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.HouseCustomerServiceLoad;
import com.mvc.entityReport.HouseCustomerServiceType;
import com.mvc.service.HotelCustomerService;

import net.sf.json.JSONObject;

/**
 * 对客服务信息统计
 * @author wanghuimin
 * @date 2017年1月10日
 */
@Controller
@RequestMapping("/customerServiceInformation")
public class CustomerServiceInformationController {
	@Autowired
	HotelCustomerService hotelCustomerService;
	
	
	/**
	 * 查询酒店对客服务信息统计
	 */
	@RequestMapping("/selectHotelCustomerService.do")
	public @ResponseBody String selectHotelCustomerService(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		List<HoCustomerService> list=hotelCustomerService.findHotelService(map);
		
		jsonObject=new JSONObject();
		jsonObject.put("list", list);
		
		return jsonObject.toString();	
	}
	/*
	 * ***********************************王慧敏报表1*******************************
	 */
	/**
	 * 查询客房部对客服务工作量统计
	 */
	@RequestMapping("/selectRoomWorkload.do")
	public @ResponseBody String selectRoomWorkload(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		List<HouseCustomerServiceLoad> list=hotelCustomerService.findDepartmentLoad(map);
		
		jsonObject=new JSONObject();
		jsonObject.put("list", list);
		
		return jsonObject.toString();	
	}
	/*
	 * ***********************************王慧敏报表2*******************************
	 */
	/**
	 * 查询客房部对客服务工作量统计
	 */
	@RequestMapping("/selectRoomType.do")
	public @ResponseBody String selectRoomType(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		List<HouseCustomerServiceType> list=hotelCustomerService.findRoomType(map);
		
		jsonObject=new JSONObject();
		jsonObject.put("list", list);
		
		return jsonObject.toString();
	}

}
