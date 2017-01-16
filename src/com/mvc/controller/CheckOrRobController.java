package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.RobEfficiency;
import com.mvc.service.CheckOrRobService;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Controller
@RequestMapping("/checkOrRobHome")
public class CheckOrRobController {
	@Autowired
	CheckOrRobService checkOrRobService;

	@RequestMapping("/toCheckOrRobHomePage.do")
	public String InvoiceReceivePage() {
		return "checkOrRobHome/index";
	}

	/**
	 * 查询抢房效率统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectRobEfficiencyByLimits.do")
	public @ResponseBody String selectWorkHouse(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		List<RobEfficiency> list = checkOrRobService.selectRobEfficiency(map);
		jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
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

}
