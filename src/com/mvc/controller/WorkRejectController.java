package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.WorkReject;
import com.mvc.service.WorkRejectService;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 部门员工做房统计控制器
 * 
 * @author wangrui
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
		List<WorkReject> list=workRejectService.selectWorkRejectByLimits(map);
		jsonObject=new JSONObject();
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
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("startTime")) {
			if (StringUtil.strIsNotEmpty("startTime")) {
				startTime = jsonObject.getString("startTime");// 开始时间
			}
		}
		if (jsonObject.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty("endTime")) {
				endTime = jsonObject.getString("startTime");// 截止时间
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}

}
