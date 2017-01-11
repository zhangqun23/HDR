package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author lwt
 * @date 2017年1月11日
 */
@Controller
@RequestMapping("/customerService")
public class CustomerServiceController {

	@RequestMapping("/toReportFormPage.do")
	public String InvoiceReceivePage() {
		return "customerService/index";
	}

}
