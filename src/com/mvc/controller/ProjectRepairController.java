package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entity.EngineerCaseSort;
import com.mvc.entityReport.ProjectRepair;
import com.mvc.service.EngineerRepairService;
import com.utils.CookieUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 工程维修项统计分析
 * 
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Controller
@RequestMapping("/projectRepair")
public class ProjectRepairController {
	@Autowired
	EngineerRepairService engineerRepairService;

	/**
	 * 查询工程维修项统计
	 */
	@RequestMapping("/selectProjectRepair.do")
	public @ResponseBody String selectEngineerRepair(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map = engineerRepairService.JsonObjToMap(jsonObject);
		String strr = engineerRepairService.findEngineerRepair(map);

		return strr;
	}

	/*
	 * ***********************************王慧敏报表*******************************
	 */
	/**
	 * 查询工程维修项统计图标
	 */
	@RequestMapping("/selectProjectIcon.do")
	public @ResponseBody String selectProjectIcon(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));
		String str = "";
		String checkYear = "";
		String quarter = "";
		String repairType = "";

		if (jsonObject.containsKey("checkYear")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("checkYear"))) {
				checkYear = jsonObject.getString("checkYear");
			}
		}
		if (jsonObject.containsKey("quarter")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("quarter"))) {
				quarter = jsonObject.getString("quarter");
			}
		}
		if (jsonObject.containsKey("repairType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("repairType"))) {
				repairType = jsonObject.getString("repairType");
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("repairType", repairType);

		str = engineerRepairService.getProjectRepairIcon(map);

		return str;
	}

	/**
	 * 导出工程维修项统计图标
	 */
	@RequestMapping("/exportProRepairAnalyse.do")
	public ResponseEntity<byte[]> exportProjectIconWord(HttpServletRequest request,HttpServletResponse response) {
		String checkYear = "";
		String quarter = "";
		String repairType = "";
		String photo="";

		Map<String, String> map = new HashMap<String, String>();
		checkYear=request.getParameter("checkYear");
		quarter=request.getParameter("quarter");
		repairType=request.getParameter("repairType");
		photo=request.getParameter("chartSVGStr");
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("repairType", repairType);
		map.put("photo", photo);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String picPath=request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH);// 图片路径
		String modelPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PROJECTICONWORD_PATH);// 模板路径
		ResponseEntity<byte[]> byteArr = engineerRepairService.exportProjectIconWord(map, path,picPath, modelPath);
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
	
	/*
	 * ***********************************王慧敏报表服务类型*****************************
	 * **
	 */
	/**
	 * 查询工程维修项统计维修类型
	 */
	@RequestMapping("/selectProjectType.do")
	public @ResponseBody String selectProjectType(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<EngineerCaseSort> list = engineerRepairService.findEngineerRepairType();

		jsonObject.put("list", list);

		return jsonObject.toString();
	}

}
