package com.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.service.ProjectWorkLoadService;
import com.utils.CookieUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 工程部员工工作量相关
 * 
 * @author zjn
 * @date 2017年2月20日
 */
@Controller
@RequestMapping("/projectWorkLoad")
public class ProjectWorkLoadController {

	@Autowired
	ProjectWorkLoadService projectWorkLoadService;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping("/toProjectPage.do")
	public String replyPagePath() {
		return "projectForm/index";
	}

	/**
	 * 获取工程部所有员工工作量汇总列表信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectProWorkLoad.do")
	public @ResponseBody String getProWorkLoadList(HttpServletRequest request) {
		String startDate = "";
		String endDate = "";
		JSONObject jsonObject = new JSONObject();

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))) {
			startDate = request.getParameter("startDate");
			endDate = request.getParameter("endDate");

			// List<WorkLoad> workLoadList = null;
			// workLoadList = workLoadService.getWorkLoadSummaryList(startDate,
			// endDate);
			// jsonObject.put("workLoadList", workLoadList);
		}
		return jsonObject.toString();
	}

	/**
	 * 导出工程部所有员工工作量汇总表，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportProWorkLoadWord.do")
	public ResponseEntity<byte[]> exportProWorkLoadWord(HttpServletRequest request, HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		ResponseEntity<byte[]> byteArr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))) {

			startDate = request.getParameter("startDate");
			endDate = request.getParameter("endDate");
			String modelPath = "";
			String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
			modelPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.WORKLOAD_PATH);// 模板路径
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("path", path);
			map.put("modelPath", modelPath);

			// byteArr = workLoadService.exportWorkLoadSummaryWord(map);

		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**
	 * 导出工程部所有员工工作量汇总表，excel格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportProWorkLoadExcel.do")
	public ResponseEntity<byte[]> exportProWorkLoadExcel(HttpServletRequest request, HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		ResponseEntity<byte[]> byteArr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))) {

			startDate = request.getParameter("startDate");
			endDate = request.getParameter("endDate");
			String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("path", path);

			// byteArr = workLoadService.exportWorkLoadSummaryExcel(map);
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
}
