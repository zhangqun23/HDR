package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.LinenExpend;
import com.mvc.service.ExpendFormService;

import com.utils.StringUtil;

import net.sf.json.JSONObject;


/**
 * 
 * @author wq
 * @date 2017年1月12日
 */
@Controller
@RequestMapping("/customerService")
public class ExpendFormController {
	
	@Autowired
	ExpendFormService expendFormService;

	@RequestMapping("/toReportFormPage.do")
	public String InvoiceReceivePage() {
		return "customerService/index";
	}
	
	/**
	 * 布草统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectLinenExpendFormByLlimits.do")
	public @ResponseBody String selectLinenExpendForm(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("llimit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		List<LinenExpend> list = expendFormService.selectLinenExpend(map);
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
		String formType = null;
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("formType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("formType"))) {
				formType = jsonObject.getString("formType");// 报表类型
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
		map.put("formType", formType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return map;
	}


}

