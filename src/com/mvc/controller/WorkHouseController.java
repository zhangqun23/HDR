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
import com.mvc.entityReport.WorkEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.service.WorkHouseService;
import com.utils.CookieUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 部门员工做房统计控制器
 * 
 * @author wangrui
 * @date 2016-12-08
 */
@Controller
@RequestMapping("/workHouse")
public class WorkHouseController {

	
	@Autowired
	WorkHouseService workHouseService;

	@RequestMapping("/toReportFormPage.do")
	public String InvoiceReceivePage() {
		return "routineTaskForm/index";
	}

	/**
	 * 查询员工做房效率
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectWorkHouseBylimits.do")
	public @ResponseBody String selectWorkHouse(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		List<WorkHouse> list = workHouseService.selectWorkHouse(map);
		jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 部门员工做房用时统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportWorkHouseBylimits.do")
	public ResponseEntity<byte[]> exportWorkHouse(HttpServletRequest request, HttpServletResponse response) {
		String roomType = null;
		String sortName = null;
		String startTime = null;
		String endTime = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("roomType"))) {
			roomType = request.getParameter("roomType");// 房间类型(sort_no)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("sortName"))) {
			sortName = request.getParameter("sortName");// 房间类型名称(sort_name)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.monthFirstDay(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.monthLastDay(request.getParameter("endTime"));// 结束时间
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomType", roomType);
		map.put("sortName", sortName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.WORKHOUSE_PATH);// 模板路径
		ResponseEntity<byte[]> byteArr = workHouseService.exportWorkHouse(map, path, tempPath);

		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**
	 * 将JsonObject转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, Object> JsonObjToMap(JSONObject jsonObject) {
		String roomType = null;
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("roomType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("roomType"))) {
				roomType = jsonObject.getString("roomType");// 房间类型
			}
		}
		if (jsonObject.containsKey("startTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("startTime"))) {
				startTime = StringUtil.monthFirstDay(jsonObject.getString("startTime"));// 开始时间
			}
		}
		if (jsonObject.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("endTime"))) {
				endTime = StringUtil.monthLastDay(jsonObject.getString("endTime"));// 结束时间
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomType", roomType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return map;
	}

	/**
	 * 将JsonObject转换成Map，单个用户
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, Object> JsonObjToMapUser(JSONObject jsonObject) {
		String checkYear = null;
		String quarter = null;
		String roomType = null;
		String cleanType = null;
		String staffId = null;
		if (jsonObject.containsKey("checkYear")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("checkYear"))) {
				checkYear = jsonObject.getString("checkYear");// 年份
			}
		}
		if (jsonObject.containsKey("quarter")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("quarter"))) {
				quarter = jsonObject.getString("quarter");// 季度
			}
		}
		if (jsonObject.containsKey("roomType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("roomType"))) {
				roomType = jsonObject.getString("roomType");// 房间类型
			}
		}
		if (jsonObject.containsKey("cleanType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("cleanType"))) {
				cleanType = jsonObject.getString("cleanType");// 打扫类型
			}
		}
		if (jsonObject.containsKey("staffId")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("staffId"))) {
				staffId = jsonObject.getString("staffId");// 员工ID
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("roomType", roomType);
		map.put("cleanType", cleanType);
		map.put("staffId", staffId);

		return map;
	}

	/**
	 * 获取单个员工做房用时（---后期用缓存优化）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectUserWorkHouseByLimits.do")
	public @ResponseBody String selectUserWorkHouseByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMapUser(jsonObject);
		String str = workHouseService.selectUserWorkHouseByLimits(map);
		return str;
	}

	/**
	 * 做房用时分析导出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportUserWorkHouseBylimits.do")
	public ResponseEntity<byte[]> exportUserWorkHouseByLimits(HttpServletRequest request,
			HttpServletResponse response) {
		String checkYear = null;
		String quarter = null;
		String sortName = null;
		String cleanType = null;
		String staffName = null;
		String chart1SVGStr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("checkYear"))) {
			checkYear = request.getParameter("checkYear");// 年份
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("quarter"))) {
			quarter = request.getParameter("quarter");// 季度
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("sortName"))) {
			sortName = request.getParameter("sortName");// 房间类型名称(sort_name)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("cleanType"))) {
			cleanType = request.getParameter("cleanType");// 打扫类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("staffName"))) {
			staffName = request.getParameter("staffName");// 员工姓名
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("chart1SVGStr"))) {
			chart1SVGStr = request.getParameter("chart1SVGStr");// SVG图片字符串
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("sortName", sortName);
		map.put("cleanType", cleanType);
		map.put("staffName", staffName);
		map.put("chart1SVGStr", chart1SVGStr);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.WORKHOUSEANA_PATH);// 模板路径
		String picPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH);// 图片路径
		ResponseEntity<byte[]> byteArr = workHouseService.exportWorkHouseAna(map, path, tempPath, picPath);

		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**** 员工工作效率报表 ****/

	/**
	 * 查询员工工作效率
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("selectWorkEfficiencyByLimits.do")
	public @ResponseBody String selectWorkEffByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMapEff(jsonObject);
		List<WorkEfficiency> list = workHouseService.selectWorkEffByLimits(map);
		jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 将JsonObject转换成Map，工作效率
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, Object> JsonObjToMapEff(JSONObject jsonObject) {
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("startTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("startTime"))) {
				startTime = StringUtil.dayFirstTime(jsonObject.getString("startTime"));// 开始时间
			}
		}
		if (jsonObject.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("endTime"))) {
				endTime = StringUtil.dayLastTime(jsonObject.getString("endTime"));// 结束时间
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return map;
	}

	/**
	 * 工作效率分析
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("selectUserWorkEfficiencyByLimits.do")
	public @ResponseBody String selectUserWorkEffByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMapUser(jsonObject);
		String str = workHouseService.selectUserWorkEffByLimits(map);
		return str;
	}

	/**
	 * 部门员工工作效率统计导出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportWorkEfficiencyBylimits.do")
	public ResponseEntity<byte[]> exportWorkEffByLimits(HttpServletRequest request, HttpServletResponse response) {
		String startTime = null;
		String endTime = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.dayFirstTime(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.dayLastTime(request.getParameter("endTime"));// 结束时间
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.WORKEFF_PATH);// 模板路径
		ResponseEntity<byte[]> byteArr = workHouseService.exportWorkEffByLimits(map, path, tempPath);

		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**
	 * 部门员工工作效率分析导出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportUserWorkEfficiencyBylimits.do")
	public ResponseEntity<byte[]> exportUserWorkEffByLimits(HttpServletRequest request, HttpServletResponse response) {
		String checkYear = null;
		String quarter = null;
		String staffName = null;
		String chartSVGStr = null;
		String chart1SVGStr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("checkYear"))) {
			checkYear = request.getParameter("checkYear");// 年份
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("quarter"))) {
			quarter = request.getParameter("quarter");// 季度
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("staffName"))) {
			staffName = request.getParameter("staffName");// 员工姓名
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr"))) {
			chartSVGStr = request.getParameter("chartSVGStr");// 工作效率
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("chart1SVGStr"))) {
			chart1SVGStr = request.getParameter("chart1SVGStr");// 做房效率
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("staffName", staffName);
		map.put("chartSVGStr", chartSVGStr);
		map.put("chart1SVGStr", chart1SVGStr);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.WORKEFFANA_PATH);// 模板路径
		String picPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH);// 图片路径
		ResponseEntity<byte[]> byteArr = workHouseService.exportWorkEffAna(map, path, tempPath, picPath);

		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

}
