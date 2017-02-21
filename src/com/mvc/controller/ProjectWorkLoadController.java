package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.ProjectWorkLoad;
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
	ProjectWorkLoadService proWorkLoadService;

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
		JSONObject limit = JSONObject.fromObject(request.getParameter("limit"));

		if (limit.containsKey("startTime") && limit.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))
					&& StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {

				startDate = jsonObject.getString("startTime");
				endDate = jsonObject.getString("endTime");
				List<ProjectWorkLoad> proWorkLoadList = null;

				proWorkLoadList = proWorkLoadService.getProWorkLoadList(startDate, endDate);
				jsonObject.put("list", proWorkLoadList);
			}
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
			modelPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PROWORKLOAD_PATH);// 模板路径
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("path", path);
			map.put("modelPath", modelPath);

			byteArr = proWorkLoadService.exportProWorkLoadWord(map);
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

			byteArr = proWorkLoadService.exportProWorkLoadExcel(map);
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**
	 * 获取单个员工工作量分析图信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectProWorkLoadAnalyse.do")
	public @ResponseBody String selectProWorkLoadAnalyse(HttpServletRequest request) {
		String str = "";
		JSONObject limit = JSONObject.fromObject(request.getParameter("limit"));
		Map<String, String> map = JsonObjToMap(limit);

		str = proWorkLoadService.getProWorkLoadAnalyse(map);
		return str;
	}

	/**
	 * 将JsonObject转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, String> JsonObjToMap(JSONObject jsonObject) {
		String checkYear = "";
		String quarter = "";
		String staffId = "";
		Map<String, String> map = new HashMap<String, String>();

		if (jsonObject.containsKey("checkYear")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("checkYear"))) {
				checkYear = jsonObject.getString("checkYear");// 年份
				map.put("checkYear", checkYear);
			}
		}
		if (jsonObject.containsKey("quarter")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("quarter"))) {
				quarter = jsonObject.getString("quarter");// 季度，0代表全部年
				map.put("quarter", quarter);
			}
		}
		if (jsonObject.containsKey("staffId")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("staffId"))) {
				staffId = jsonObject.getString("staffId");// 员工ID
				map.put("staffId", staffId);
			}
		}

		return map;
	}

	/**
	 * 导出单个员工工作量分析图，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportProWorkLoadAnalyse.do")
	public ResponseEntity<byte[]> exportProWorkLoadAnalyse(HttpServletRequest request, HttpServletResponse response) {

		String svg = "";
		String checkYear = "";
		String quarter = "";
		String staffId = "";
		String analyseResult = "";
		ResponseEntity<byte[]> byteArr = null;
		Map<String, String> map = new HashMap<String, String>();

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		String modelPath = request.getSession().getServletContext()
				.getRealPath(ReportFormConstants.PROWORKLOADANALYSE_PATH);// 模板路径
		String picCataPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH + "\\");// 图片地址

		if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr"))
				&& StringUtil.strIsNotEmpty(request.getParameter("checkYear"))
				&& StringUtil.strIsNotEmpty(request.getParameter("quarter"))
				&& StringUtil.strIsNotEmpty(request.getParameter("staffId"))) {

			svg = request.getParameter("chartSVGStr");
			checkYear = request.getParameter("checkYear");
			quarter = request.getParameter("quarter");
			staffId = request.getParameter("staffId");
			analyseResult = request.getParameter("analyseResult");

			map.put("path", path);
			map.put("modelPath", modelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg", svg);
			map.put("checkYear", checkYear);
			map.put("quarter", quarter);
			map.put("staffId", staffId);
			map.put("analyseResult", analyseResult);
			byteArr = proWorkLoadService.exportProWorkLoadAnalyse(map);
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
}
