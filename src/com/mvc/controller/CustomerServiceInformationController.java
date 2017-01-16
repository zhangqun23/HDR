package com.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.base.constants.ReportFormConstants;
import com.mvc.entity.DepartmentInfo;
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
	@RequestMapping("/selectDepWorkload.do")
	public @ResponseBody String selectHotelCustomerService(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		List<HoCustomerService> list=hotelCustomerService.findHotelService(map);
		
		jsonObject=new JSONObject();
		jsonObject.put("list", list);
		
		return jsonObject.toString();	
	}
	/**
	 * 导出酒店对客服务信息统计表
	 */
	@RequestMapping("/exportDepWorkload.do")
	public ResponseEntity<byte[]> exportCustomerService(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径	
		String modelPath = request.getSession().getServletContext()
				.getRealPath("word\\" + "customerService.docx");// 模板路径
		ResponseEntity<byte[]> byteArr = hotelCustomerService.exportCustomerService(map, path,modelPath);		
		return byteArr;	
	}
	
	
	
	
	
	/*
	 * ***********************************王慧敏报表1*******************************
	 */
	/**
	 * 查询客房部对客服务工作量统计
	 */
	@RequestMapping("/selectStaffWorkload.do")
	public @ResponseBody String selectRoomWorkload(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		List<HouseCustomerServiceLoad> list=hotelCustomerService.findDepartmentLoad(map);
		
		jsonObject=new JSONObject();
		jsonObject.put("list", list);
		
		return jsonObject.toString();	
	}
	/**
	 * 导出部门对客服务工作量统计表
	 */
	@RequestMapping("/exportStaffWorkload.do")
	public ResponseEntity<byte[]> exportRoomWorkload(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径	
		String modelPath = request.getSession().getServletContext()
				.getRealPath("word\\" + "roomWorkload.docx");// 模板路径
		ResponseEntity<byte[]> byteArr = hotelCustomerService.exportRoomWorkload(map, path,modelPath);		
		return byteArr;	
	}
	/*
	 * ***********************************王慧敏报表2*******************************
	 */
	/**
	 * 查询部门对客服务类型统计
	 */
	@RequestMapping("/selectType.do")
	public @ResponseBody String selectRoomType(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		List<HouseCustomerServiceType> list=hotelCustomerService.findRoomType(map);
		
		jsonObject=new JSONObject();
		jsonObject.put("list", list);
		for(int i=0;i<list.size();i++){
			System.out.println("测试："+list.get(i).getAverageWorkTime()+list.get(i).getOrderNum());
		}
		
		return jsonObject.toString();
	}
	/**
	 * 导出部门对客服务类型统计
	 */
	@RequestMapping("/exportType.do")
	public ResponseEntity<byte[]> exportRoomType(HttpServletRequest request){
		JSONObject jsonObject=JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map=hotelCustomerService.JsonObjToMap(jsonObject);
		
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径	
		String modelPath = request.getSession().getServletContext()
				.getRealPath("word\\" + "roomType.docx");// 模板路径
		ResponseEntity<byte[]> byteArr = hotelCustomerService.exportRoomType(map, path,modelPath);		
		return byteArr;		
	}
	/*
	 * ***********************************王慧敏报表需求方法*******************************
	 */
	//查询部门列表
	@RequestMapping("/selectDep.do")
	public @ResponseBody String selectDep(HttpServletRequest request){
		List<DepartmentInfo> list=hotelCustomerService.findDep();
		for (int i = 0; i < list.size(); i++) {	
			System.out.println(list.get(i).getDepartmentName());
		}
			
		return JSON.toJSONString(list);
		
	}	
	//根据部门ID筛选员工信息
		@RequestMapping("/selectStaffByDepId.do")
		public @ResponseBody String selectStaffByDepId(HttpServletRequest request){
			JSONObject jsonObject = new JSONObject();
			String departid=request.getParameter("depart_id");
			List<Object> list=hotelCustomerService.findStaffByDepId(departid);
			
			jsonObject.put("list", list);
				
			return jsonObject.toString();
			
		}

}
