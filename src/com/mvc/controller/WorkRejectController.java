package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.WorkReject;
import com.mvc.service.WorkHouseService;
import com.mvc.service.WorkRejectService;
import com.utils.CookieUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 部门员工做房驳回统计控制器
 * 
 * @author zq
 * @date 2016-12-08
 */
@Controller
@RequestMapping("/workReject")
public class WorkRejectController {

	@Autowired
	WorkRejectService workRejectService;

	@RequestMapping("/toReportFormPage.do")
	public String InvoiceReceivePage() {
		return "routineTaskForm/index";
	}

	@RequestMapping("/selectWorkRejectByLimits.do")
	public @ResponseBody String selectWorkRejectByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map = JsonObjToMap(jsonObject);
		String result = null;
		result = workRejectService.selectWorkRejectByLimits(map);
		return result;
	}

	/**
	 * 将统计JsonObject转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, Object> JsonObjToMap(JSONObject jsonObject) {
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("startTime")) {
			if (StringUtil.strIsNotEmpty("startTime")) {
				startTime = StringUtil.dayFirstTime(jsonObject.getString("startTime"));// 开始时间
			}
		}
		if (jsonObject.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty("endTime")) {
				endTime = StringUtil.dayLastTime(jsonObject.getString("endTime"));// 截止时间
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}

	// zq驳回折线图分析
	@RequestMapping("/selectWorkRejectAnalyseByLimits.do")
	public @ResponseBody String selectWorkRejectAnalyseByLimits(HttpServletRequest request) {
		JSONObject jsonobject = JSONObject.fromObject(request.getParameter("limit"));
		Map<String, Object> map = JsonObjToMapAnalyse(jsonobject);
		String str = workRejectService.selectWorkRejectAnalyseByLimits(map);
		return str;
	}

	/**
	 * 将分析折线图JsonObject转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, Object> JsonObjToMapAnalyse(JSONObject jsonObject) {
		String checkYear = null;
		String quarter = null;
		String staffId = null;
		String cleanType = null;
		if (jsonObject.containsKey("checkYear")) {
			if (StringUtil.strIsNotEmpty("checkYear")) {
				checkYear = jsonObject.getString("checkYear");// 查询年份
			}
		}
		if (jsonObject.containsKey("quarter")) {
			if (StringUtil.strIsNotEmpty("quarter")) {
				quarter = jsonObject.getString("quarter");// 季度
			}
		}
		if (jsonObject.containsKey("staffId")) {
			if (StringUtil.strIsNotEmpty("staffId")) {
				staffId = jsonObject.getString("staffId");// 查询员工
			}
		}
		if (jsonObject.containsKey("cleanType")) {
			if (StringUtil.strIsNotEmpty("cleanType")) {
				cleanType = jsonObject.getString("cleanType");// 打扫类型
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("staffId", staffId);
		map.put("cleanType", cleanType);
		return map;
	}

	// zq导出驳回统计表
	@RequestMapping("/exportWorRejectBylimits.do")
	public ResponseEntity<byte[]> exportWorRejectBylimits(HttpServletRequest request, HttpServletResponse response) {
		String startTime = null;
		String endTime = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.monthFirstDay(request.getParameter("startTime"));// 起始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.monthLastDay(request.getParameter("endTime"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.RejectEff_PATH);// 模板路径
		ResponseEntity<byte[]> byteArr = workRejectService.exportWorRejectBylimits(map, path, tempPath);
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	// zq导出驳回统计表Excel
	@RequestMapping("/exportWorRejectExcelBylimits.do")
	public ResponseEntity<byte[]> exportWorRejectExcelBylimits(HttpServletRequest request,
			HttpServletResponse response) {
		String startTime = null;
		String endTime = null;
		ResponseEntity<byte[]> byteArr = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.monthFirstDay(request.getParameter("startTime"));// 起始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.monthLastDay(request.getParameter("endTime"));
		}
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("path", path);
		byteArr = workRejectService.exportWorRejectExcelBylimits(map);
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	// zq驳回折线图的分析
	@RequestMapping("/exportWorkRejectAnalyseBylimits.do")
	public ResponseEntity<byte[]> exportWorkRejectAnalyseBylimits(HttpServletRequest request,
			HttpServletResponse response) {
		String checkYear = null;
		String quarter = null;
		String cleanType = null;
		String staffId = null;
		String staffName = null;
		String chartSVGStr = null;
		String chart1SVGStr = null;
		String analyseResult = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("checkYear"))) {
			checkYear = request.getParameter("checkYear");// 年份
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("quarter"))) {
			quarter = request.getParameter("quarter");// 季度
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("cleanType"))) {
			cleanType = request.getParameter("cleanType");// 打扫类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("staffId"))) {
			staffId = request.getParameter("staffId");// 房间类型名称(sort_name)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("staffName"))) {
			staffName = request.getParameter("staffName");// 员工姓名
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr"))) {
			chartSVGStr = request.getParameter("chartSVGStr");// SVG图片字符串折线图
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("chart1SVGStr"))) {
			chart1SVGStr = request.getParameter("chart1SVGStr");// SVG图片字符串扇形图
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("analyseResult"))) {
			analyseResult = request.getParameter("analyseResult");// SVG图片字符串扇形图
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("staffId", staffId);
		map.put("staffName", staffName);
		map.put("cleanType", cleanType);
		map.put("chartSVGStr", chartSVGStr);
		map.put("chart1SVGStr", chart1SVGStr);
		map.put("analyseResult", analyseResult);
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		String tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.RejectAnalyse_PATH);
		String picPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH);
		ResponseEntity<byte[]> byteArr = workRejectService.exportWorkRejectAna(map, path, tempPath, picPath);
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

}
