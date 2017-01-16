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
import com.mvc.dao.WorkLoadDao;
import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkLoadLevel;
import com.mvc.entityReport.WorkLoadMonth;
import com.mvc.service.WorkLoadService;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 工作量相关报表
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Controller
@RequestMapping("/workLoad")
public class WorkLoadController {

	@Autowired
	WorkLoadService workLoadService;
	@Autowired
	WorkLoadDao workLoadDao;

	/**
	 * 获取所有员工工作量汇总列表信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWorkLoadSummaryList.do")
	public @ResponseBody String getWorkLoadSummaryList(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		// String startDate = "2016-01-01";
		// String endDate = "2017-01-20";
		List<WorkLoad> workLoadList = null;
		String startDate = "";
		String endDate = "";

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))) {
			startDate = request.getParameter("startDate");
			endDate = request.getParameter("endDate");
			workLoadList = workLoadService.getWorkLoadSummaryList(startDate, endDate);
		}
		for (int i = 0; i < workLoadList.size(); i++) {
			System.out.println("结果：");
			System.out.println(workLoadList.get(i).getStaffName() + workLoadList.get(i).getStaffNo() + ": 抹尘房"
					+ workLoadList.get(i).getCleanRoom() + ";过夜房" + workLoadList.get(i).getOvernightRoom() + ";离退房"
					+ workLoadList.get(i).getCheckoutRoom());
		}

		jsonObject.put("workLoadList", workLoadList);
		return jsonObject.toString();
	}

	/**
	 * 导出所有员工工作量汇总表，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportWorkLoadSummaryList.do")
	public ResponseEntity<byte[]> exportWorkLoadSummaryList(HttpServletRequest request, HttpServletResponse response) {

		// String startDate = "2016-01-01";
		// String endDate = "2018-01-20";
		ResponseEntity<byte[]> byteArr = null;
		String startDate = "";
		String endDate = "";

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))) {
			startDate = request.getParameter("startDate");
			endDate = request.getParameter("endDate");
			String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
			String modelPath = request.getSession().getServletContext()
					.getRealPath("word\\" + "workLoadSummaryList.docx");// 模板路径
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("path", path);
			map.put("modelPath", modelPath);
			byteArr = workLoadService.exportWorkLoadSummaryList(map);
		}
		return byteArr;
	}

	/**
	 * 获取所有员工工作量饱和度分析列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWorkLoadLevelList.do")
	public @ResponseBody String getWorkLoadLevelList(HttpServletRequest request) {

		JSONObject jsonObject = new JSONObject();
		List<WorkLoadLevel> workLoadLevelList = null;
		String startDate = "";
		String endDate = "";

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))) {
			startDate = request.getParameter("startDate");
			endDate = request.getParameter("endDate");
			workLoadLevelList = workLoadService.getWorkLoadLevelList(startDate, endDate);
		}

		jsonObject.put("WorkLoadLevelList", workLoadLevelList);
		return jsonObject.toString();

	}

	/**
	 * 导出所有员工工作量饱和度分析列表，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportWorkLoadLevelList.do")
	public ResponseEntity<byte[]> exportWorkLoadLevelList(HttpServletRequest request, HttpServletResponse response) {

		ResponseEntity<byte[]> byteArr = null;
		// String startDate = "2016-01-01";
		// String endDate = "2018-01-20";
		String startDate = "";
		String endDate = "";

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))) {
			String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
			String modelPath = request.getSession().getServletContext()
					.getRealPath("word\\" + "workLoadLevelList.docx");// 模板路径

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("path", path);
			map.put("modelPath", modelPath);
			byteArr = workLoadService.exportWorkLoadLevelList(map);
		}
		return byteArr;

	}

	/**
	 * 获取单个员工工作量分析图信息
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getStaffWorkLoadAnalyse.do")
	public @ResponseBody String getWorkLoadAnalyse(HttpServletRequest request) {

		JSONObject jsonObject = new JSONObject();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		// String checkYear = "2017";
		// String quarter = "0";
		// String staffId = "511";
		String checkYear = "";
		String quarter = "";
		String staffId = "";

		if (StringUtil.strIsNotEmpty(request.getParameter("startDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endDate"))
				&& StringUtil.strIsNotEmpty(request.getParameter("staffId"))) {

			map.put("checkYear", checkYear);
			map.put("quarter", quarter);
			map.put("staffId", staffId);
			result = workLoadService.getWorkLoadAnalyseInfo(map);
			List<WorkLoadMonth> workLoadMonths = (List<WorkLoadMonth>) result.get("workLoadMonths");
			System.out.println("allAverageData:" + result.get("allAverageData"));
			System.out.println("averageData:" + result.get("averageData"));
			for (int i = 0; i < workLoadMonths.size(); i++) {
				System.out.println("结果：" + workLoadMonths.get(i).getMonth() + ";"
						+ workLoadMonths.get(i).getActualLoad() + ";" + workLoadMonths.get(i).getRatedLoad());
			}
			jsonObject.put("allAverageData", (String) result.get("allAverageData"));
			jsonObject.put("averageData", (String) result.get("averageData"));
			jsonObject.put("workLoadMonths", workLoadMonths);
		}
		return jsonObject.toString();
	}

	/**
	 * 导出单个员工工作量分析图，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportWorkLoadAnalyse.do")
	public ResponseEntity<byte[]> exportWorkLoadAnalyse(HttpServletRequest request, HttpServletResponse response) {

		String svg = "";
		String checkYear = "";
		String quarter = "";
		String staffId = "";
		ResponseEntity<byte[]> byteArr = null;
		Map<String, String> map = new HashMap<String, String>();

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		String modelPath = request.getSession().getServletContext().getRealPath("word\\" + "workLoadAnalyse.docx");// 模板路径
		String picCataPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH + "\\");// 图片地址

		if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr"))
				&& StringUtil.strIsNotEmpty(request.getParameter("checkYear"))
				&& StringUtil.strIsNotEmpty(request.getParameter("quarter"))
				&& StringUtil.strIsNotEmpty(request.getParameter("staffId"))) {

			svg = request.getParameter("chartSVGStr");
			checkYear = request.getParameter("checkYear");
			quarter = request.getParameter("quarter");
			staffId = request.getParameter("staffId");

			map.put("path", path);
			map.put("modelPath", modelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg", svg);
			map.put("checkYear", checkYear);
			map.put("quarter", quarter);
			map.put("staffId", staffId);
			byteArr = workLoadService.exportWorkLoadAnalyse(map);
		}
		return byteArr;
	}
}
