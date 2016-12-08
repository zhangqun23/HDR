package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.WorkHouse;
import com.mvc.service.WorkHouseService;

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
	@RequestMapping("/selectWorkHouse.do")
	public @ResponseBody String selectWorkHouse(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<WorkHouse> list = workHouseService.selectWorkHouse();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

}
