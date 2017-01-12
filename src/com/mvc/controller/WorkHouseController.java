package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.WorkHouse;
import com.mvc.service.WorkHouseService;
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
		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	/**
	 * 部门员工做房用时统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportWorkHouse.do")
	public ResponseEntity<byte[]> exportWorkHouse(HttpServletRequest request) {
		Integer cleanType = null;
		String roomType = null;
		String startTime = null;
		String endTime = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("cleanType"))) {
			cleanType = Integer.valueOf(request.getParameter("cleanType"));// 打扫类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("roomType"))) {
			roomType = request.getParameter("roomType");// 房间类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = request.getParameter("startTime");// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = request.getParameter("endTime");// 结束时间
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cleanType", cleanType);
		map.put("roomType", roomType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		ResponseEntity<byte[]> byteArr = workHouseService.exportWorkHouse(map, path);

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
	 * 获取单个用户做房用时（---后期用缓存优化）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectUserWorkHouseByLimits.do")
	public @ResponseBody String selectUserWorkHouseByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMapUser(jsonObject);
		String str = workHouseService.selectUserWorkHouseByLimits(map);
		System.out.println("做房用时分析：" + str);
		return str;
	}

}
