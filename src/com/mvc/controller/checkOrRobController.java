package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Controller
@RequestMapping("/checkOrRobHome")
public class CheckOrRobController {

	@RequestMapping("/toCheckOrRobHomePage.do")
	public String InvoiceReceivePage() {
		return "checkOrRobHome/index";
	}

}
