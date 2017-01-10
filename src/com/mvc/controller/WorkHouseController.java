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
import com.utils.Pager;
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

	/**
	 * 查询员工做房效率
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectWorkHouseBylimits.do")
	public @ResponseBody String selectWorkHouse(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));
		Integer page = Integer.parseInt(request.getParameter("page"));// 指定页码

		Map<String, Object> map = JsonObjToMap(jsonObject);
		Pager pager = workHouseService.pagerTotalWorkHouse(map, page);
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		List<WorkHouse> list = workHouseService.selectWorkHouse(map, pager);
		jsonObject.put("list", list);
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
		Integer cleanType = null;
		String roomType = null;
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("cleanType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("cleanType"))) {
				cleanType = Integer.valueOf(jsonObject.getString("cleanType"));// 打扫类型
			}
		}
		if (jsonObject.containsKey("roomType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("roomType"))) {
				roomType = jsonObject.getString("roomType");// 房间类型
			}
		}
		if (jsonObject.containsKey("startTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("startTime"))) {
				startTime = jsonObject.getString("startTime");// 开始时间
			}
		}
		if (jsonObject.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("endTime"))) {
				endTime = jsonObject.getString("endTime");// 结束时间
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cleanType", cleanType);
		map.put("roomType", roomType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return map;
	}

}
