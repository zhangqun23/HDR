package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 工程部服务统计(zq建立页面跳转建立)
 * 
 * @author zq
 * @date 2017-2-19
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

	@RequestMapping("/toProjectPage.do")
	public String replyPagePath() {
		return "projectForm/index";
	}
}
