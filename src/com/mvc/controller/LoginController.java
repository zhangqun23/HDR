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

import com.base.constants.CookieKeyConstants;
import com.base.constants.PageNameConstants;
import com.base.constants.SessionKeyConstants;
import com.mvc.entity.StaffInfo;
import com.mvc.service.StaffInfoService;
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
	@Autowired
	StaffInfoService staffInfoService;

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
		String error_msg = "";
		String userNum = request.getParameter("userName");
		String password = request.getParameter("password");
		String isRemember = request.getParameter("isRemember"); // 记住密码//值获取不到
		StaffInfo staffInfo = staffInfoService.findByStaffNo(userNum);
		CookieUtil cookie_u = new CookieUtil();
		if (staffInfo != null) { // 用户存在
			String passwd = staffInfo.getStaff_pwd();
			if (passwd != null && passwd.equals(password)) {
				session.setAttribute(SessionKeyConstants.LOGIN, staffInfo);
				model.addAttribute("user", staffInfo);
				cookie_u.add_cookie(CookieKeyConstants.USERNAME, userNum, res, 60 * 60 * 24 * 15);
				if (isRemember != null) {
					cookie_u.add_cookie(CookieKeyConstants.PASSWORD, password, res, 60 * 60 * 24 * 7);
				} else {
					cookie_u.del_cookie(CookieKeyConstants.PASSWORD, request, res);
				}
				model.addAttribute("password", password);
				Cookie cookie = new Cookie("userNum", userNum);
				cookie.setMaxAge(30 * 60);
				cookie.setPath("/");
				res.addCookie(cookie);
				return "index";// 返回到index主页
			} else { // 密码错误
				error_msg = "err_password";
				cookie_u.del_cookie(CookieKeyConstants.PASSWORD, request, res);
				model.addAttribute("error", error_msg);
				return HttpRedirectUtil.redirectStr(PageNameConstants.TOLOGIN);
			}
		} else { // 用户不存在
			error_msg = "err_user";
			model.addAttribute("error", error_msg);
			return HttpRedirectUtil.redirectStr(PageNameConstants.TOLOGIN);
		}

	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout.do")
	public String logout(HttpSession session, HttpServletResponse response) {
		session.removeAttribute(SessionKeyConstants.LOGIN);
		Cookie cookie = new Cookie("userNum", null);
		cookie.setMaxAge(30 * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
		return HttpRedirectUtil.redirectStr("toLoginPage.do");
	}
	
	/**
	 * 检查该用户是否存在
	 * 
	 * @param request
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/checkUserName.do")
	public @ResponseBody Long checkUserName(HttpServletRequest request, HttpSession session, ModelMap map) {
		String userNum = request.getParameter("userName");
		Long result = staffInfoService.isExist(userNum);
		return result;
	}

	/**
	 * 登录验证用户名和密码是否正确
	 * 
	 * @param session
	 * @param request
	 * @param model
	 * @param res
	 * @return
	 */
	@RequestMapping("/loginValidate.do")
	public @ResponseBody JSONObject loginValidate(HttpSession session, HttpServletRequest request, ModelMap model,
			HttpServletResponse res) {
		String userNum = request.getParameter("userName");
		String passWord =request.getParameter("password");
		StaffInfo staffInfo = staffInfoService.findByStaffNo(userNum);
		JSONObject jsonObject = new JSONObject();
		if (staffInfo != null) {
			String passwd = staffInfo.getStaff_pwd();
			if (passwd != null && passwd.equals(passWord)) {
				jsonObject.put("err_message", "OK");
			} else {
				jsonObject.put("err_message", "err_password");
			}
		} else {
			jsonObject.put("err_message", "err_user");
		}
		return jsonObject;
	}

}
