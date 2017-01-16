package com.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.service.WorkRejectService;

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

}
