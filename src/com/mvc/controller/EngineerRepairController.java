package com.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.EngineerRepair;
import com.mvc.service.EngineerRepairService;

import net.sf.json.JSONObject;

/**
 * 工程维修项统计分析
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Controller
@RequestMapping("/engineerRepair")
public class EngineerRepairController {
	@Autowired
	EngineerRepairService engineerRepairService;
	
	/**
	 * 查询工程维修项统计
	 */
	@RequestMapping("/selectEngineerRepair.do")
	public @ResponseBody String selectEngineerRepair(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map = engineerRepairService.JsonObjToMap(jsonObject);
		List<EngineerRepair> list = engineerRepairService.findEngineerRepair(map);

		jsonObject = new JSONObject();
		jsonObject.put("list", list);

		return jsonObject.toString();
	}
	/*
	 * ***********************************王慧敏报表*******************************
	 */
	/**
	 * 查询工程维修项统计图标
	 */
	@RequestMapping("/selectEngineerIcon.do")
	public @ResponseBody String selectEngineerIcon(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map = engineerRepairService.JsonObjToMap(jsonObject);
		List<EngineerRepair> list = engineerRepairService.findEngineerRepair(map);

		jsonObject = new JSONObject();
		jsonObject.put("list", list);

		return jsonObject.toString();
	}
	
	

}
