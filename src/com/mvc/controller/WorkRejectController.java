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
		List<WorkReject> list = workRejectService.selectWorkRejectByLimits(map);
		jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
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

}
