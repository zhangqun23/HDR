package com.mvc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utils.CookieUtil;
import com.utils.HttpRedirectUtil;

import net.sf.json.JSONObject;

/**
 * 登陆
 * 
 * @author zjn
 * @date 2016年9月7日
 */

@Controller
@RequestMapping("/login")
public class LoginController {

	/**
	 * 加载默认起始页
	 * 
	 * @return
	 */
	@RequestMapping("/toLoginPage.do")
	public String contractInformationPage() {
		return "login";
	}


	/**
	 * 验证登陆之后写入Cookie和Session
	 * 
	 * @param session
	 * @param request
	 * @param model
	 * @param res
	 * @return
	 */
	@RequestMapping("/login.do")
	public String login(HttpSession session, HttpServletRequest request, ModelMap model, HttpServletResponse res) {

		return "index";// 返回到index主页

	}

}
