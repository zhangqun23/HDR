package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Controller
@RequestMapping("/routineTaskForm")
public class routineTaskFormController {

	@RequestMapping("/toReportFormPage.do")
	public String InvoiceReceivePage() {
		return "routineTaskForm/index";
	}

}
