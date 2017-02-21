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
import com.mvc.entityReport.LinenCount;
import com.mvc.entityReport.LinenExpend;
import com.mvc.entityReport.MiniCount;
import com.mvc.entityReport.MiniExpend;
import com.mvc.entityReport.RoomCount;
import com.mvc.entityReport.RoomExpend;
import com.mvc.entityReport.WashCount;
import com.mvc.entityReport.WashExpend;
import com.mvc.service.ExpendFormService;
import com.utils.CookieUtil;
import com.utils.Pager;
import com.utils.StringUtil;
import net.sf.json.JSONObject;

/**
 * 
 * @author wq
 * @date 2017年1月12日
 */
@Controller
@RequestMapping("/customerService")
public class ExpendFormController {

	@Autowired
	ExpendFormService expendFormService;

	@RequestMapping("/toReportFormPage.do")
	public String InvoiceReceivePage() {
		return "customerService/index";
	}

	/**
	 * 布草统计
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/selectLinenExpendFormByLlimits.do")
	public @ResponseBody String selectLinenExpendForm(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("llimit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		int totalRow = Integer.parseInt(expendFormService.countlinenTotal(map).toString());
		Pager pager = new Pager();
		pager.setPage(Integer.parseInt(request.getParameter("page")));// 指定页码
		pager.setTotalRow(totalRow);

		LinenCount linenCount = expendFormService.linenTotleCount(map);
		List<LinenExpend> list = expendFormService.selectLinenPage(map, pager);
		jsonObject = new JSONObject();
		jsonObject.put("linenCount", linenCount);
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		return jsonObject.toString();
	}

	/**
	 *
	 * 布草使用量分析
	 */
	@RequestMapping("/selectLinenExpendAnalyseByLlimits.do")
	public @ResponseBody String selectLinenExpendAnalyse(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("allimit"));
		Map<String, Object> map = JsonObjToMap(jsonObject);
		
		String list = expendFormService.selectLinenExpendAnalyse(map);
		return list;
	}

	/**
	 * 房间耗品统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectRoomExpendFormByRlimits.do")
	public @ResponseBody String selectRoomExpendForm(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("rlimit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		int totalRow = Integer.parseInt(expendFormService.countroomTotal(map).toString());
		Pager pager = new Pager();
		pager.setPage(Integer.parseInt(request.getParameter("page")));// 指定页码
		pager.setTotalRow(totalRow);
		
		RoomCount roomCount = expendFormService.roomTotleCount(map);
		List<RoomExpend> list = expendFormService.selectRoomExpend(map, pager);
		jsonObject = new JSONObject();
		jsonObject.put("roomCount", roomCount);
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		return jsonObject.toString();
	}

	/**
	 *
	 * 房间耗品使用量分析
	 */
	@RequestMapping("/selectRoomExpendAnalyseByRlimits.do")
	public @ResponseBody String selectRoomExpendAnalyse(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("arlimit"));
		Map<String, Object> map = JsonObjToMap(jsonObject);
		
		String list = expendFormService.selectRoomExpendAnalyse(map);
		return list;
	}

	/**
	 * 卫生间耗品统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectWashExpendFormByWlimits.do")
	public @ResponseBody String selectWashExpendForm(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("wlimit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		int totalRow = Integer.parseInt(expendFormService.countwashTotal(map).toString());
		Pager pager = new Pager();
		pager.setPage(Integer.parseInt(request.getParameter("page")));// 指定页码
		pager.setTotalRow(totalRow);
		
		WashCount washCount = expendFormService.washTotleCount(map);
		List<WashExpend> list = expendFormService.selectWashExpend(map, pager);
		jsonObject = new JSONObject();
		jsonObject.put("washCount", washCount);
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		return jsonObject.toString();
	}

	/**
	 *
	 * 卫生间耗品使用量分析
	 */
	@RequestMapping("/selectWashExpendAnalyseByWlimits.do")
	public @ResponseBody String selectWashExpendAnalyse(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("wrlimit"));
		Map<String, Object> map = JsonObjToMap(jsonObject);
		
		String list = expendFormService.selectWashExpendAnalyse(map);
		return list;
	}
	
	/**
	 * 迷你吧统计
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/selectMiniExpendFormByMlimits.do")
	public @ResponseBody String selectMiniExpendForm(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("mlimit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		int totalRow = Integer.parseInt(expendFormService.countminiTotal(map).toString());
		Pager pager = new Pager();
		pager.setPage(Integer.parseInt(request.getParameter("page")));// 指定页码
		pager.setTotalRow(totalRow);

		MiniCount miniCount = expendFormService.miniTotleCount(map);
		List<MiniExpend> list = expendFormService.selectMiniPage(map, pager);
		jsonObject = new JSONObject();
		jsonObject.put("miniCount", miniCount);
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		return jsonObject.toString();
	}
	
	/**
	 *
	 * 迷你吧使用量分析
	 */
	@RequestMapping("/selectMiniExpendAnalyseByMlimits.do")
	public @ResponseBody String selectMiniExpendAnalyse(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("amlimit"));
		Map<String, Object> map = JsonObjToMap(jsonObject);
		
		String list = expendFormService.selectMiniExpendAnalyse(map);
		return list;
	}

	
	/**
	 * 将JsonObject转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, Object> JsonObjToMap(JSONObject jsonObject) {
		String formType = null;
		String formName = null;
		String startTime = null;
		String endTime = null;
		String tableType = null;
		if (jsonObject.containsKey("formType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("formType"))) {
				formType = jsonObject.getString("formType");// 报表类型
			}
		}
		if (jsonObject.containsKey("formName")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("formName"))) {
				formName = jsonObject.getString("formName");// 报表类型
			}
		}
		if (jsonObject.containsKey("startTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("startTime"))) {
				startTime = StringUtil.dayFirstTime(jsonObject.getString("startTime"));// 开始时间
			}
		}
		if (jsonObject.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("endTime"))) {
				endTime = StringUtil.dayLastTime(jsonObject.getString("endTime"));// 结束时间
			}
		}
		if (jsonObject.containsKey("tableType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("tableType"))) {
				tableType = jsonObject.getString("tableType");// 报表类型名称
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("formType", formType);
		map.put("formName", formName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("tableType", tableType);

		return map;
	}
	
	/**
	 * 导出耗品消耗，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExpendForm.do")
	public ResponseEntity<byte[]> exportExpendForm(HttpServletRequest request, HttpServletResponse response) {
		String formType = null;
		String formName = null;
		String startTime = null;
		String endTime = null;
		String tableType = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("formType"))) {
			formType = request.getParameter("formType");// 报表类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("formName"))) {
			formName = request.getParameter("formName");// 报表类型名称
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.dayFirstTime(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.dayLastTime(request.getParameter("endTime"));// 结束时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))){
			tableType =  request.getParameter("tableType");//耗品类型选择
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("formType", formType);
		map.put("formName", formName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = null;
		ResponseEntity<byte[]> byteArr = null;
		switch(tableType){
		case "0":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.LINENEXPEND_PATH);// 模板路径
			byteArr = expendFormService.exportLinenExpendForm(map, path, tempPath);
			break;
		case "1":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.ROOMEXPEND_PATH);// 模板路径
			byteArr = expendFormService.exportRoomExpendForm(map, path, tempPath);
			break;
		case "2":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.WASHEXPEND_PATH);// 模板路径
			byteArr = expendFormService.exportWashExpendForm(map, path, tempPath);
			break;
		case "3":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.MINIEXPEND_PATH);// 模板路径
			byteArr = expendFormService.exportMiniExpendForm(map, path, tempPath);
			break;
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
	
	/**
	 * 导出耗品消耗，Excel格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExpendExcel.do")
	public ResponseEntity<byte[]> exportExpendExcel(HttpServletRequest request, HttpServletResponse response) {
		String formType = null;
		String formName = null;
		String startTime = null;
		String endTime = null;
		String tableType = null;
		ResponseEntity<byte[]> byteArr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("formType"))) {
			formType = request.getParameter("formType");// 报表类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("formName"))) {
			formName = request.getParameter("formName");// 报表类型名称
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.dayFirstTime(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.dayLastTime(request.getParameter("endTime"));// 结束时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))){
			tableType =  request.getParameter("tableType");//耗品类型选择
		}
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("formType", formType);
		map.put("formName", formName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("tableType", tableType);
		map.put("path", path);		
		byteArr = expendFormService.exportExpendExcel(map);
		
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
	
	/**
	 * 导出耗品用量分析图，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExpendPic.do")
	public ResponseEntity<byte[]> exportExpendPic(HttpServletRequest request, HttpServletResponse response) {
		String svg1 = "";
		String svg2 = "";
		String startTime = "";
		String endTime = "";
		String tableType = "";
		String analyseResult = "";
		ResponseEntity<byte[]> byteArr = null;
		Map<String, String> map = new HashMap<String, String>();
		String picCataPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH + "\\");// 图片地址
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);

		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = request.getParameter("startTime");
		}

		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = request.getParameter("endTime");
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))) {
			tableType = request.getParameter("tableType");
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))) {
			analyseResult = request.getParameter("analyseResult");
		}
		switch(tableType){
		case "0":
			if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr1"))) {
				svg1 = request.getParameter("chartSVGStr1");
			}

			if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr2"))) {
				svg2 = request.getParameter("chartSVGStr2");
			}

			
			String modelPath = request.getSession().getServletContext()
			.getRealPath(ReportFormConstants.LINENEXPENDPIC_PATH);// 房间布草耗品用量分析图模板路径
			map.put("path", path);
			map.put("modelPath", modelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg1", svg1);
			map.put("svg2", svg2);	
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("tableType", tableType);
			map.put("analyseResult", analyseResult);
			byteArr = expendFormService.exportLinenOrMiniExpendPic(map);
			break;
		case "1":
			if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr3"))) {
				svg1 = request.getParameter("chartSVGStr3");
			}

			String roommodelPath = request.getSession().getServletContext()
			.getRealPath(ReportFormConstants.ROOMEXPENDPIC_PATH);// 房间布草耗品用量分析图模板路径
			map.put("path", path);
			map.put("modelPath", roommodelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg", svg1);	
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("tableType", tableType);
			map.put("analyseResult", analyseResult);
			byteArr = expendFormService.exportRoomOrWashExpendPic(map);
			break;
		case "2":
			if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr4"))) {
				svg1 = request.getParameter("chartSVGStr4");
			}

			String washmodelPath = request.getSession().getServletContext()
			.getRealPath(ReportFormConstants.ROOMEXPENDPIC_PATH);// 房间布草耗品用量分析图模板路径
			map.put("path", path);
			map.put("modelPath", washmodelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg", svg1);	
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("tableType", tableType);
			map.put("analyseResult", analyseResult);
			byteArr = expendFormService.exportRoomOrWashExpendPic(map);
			break;
		case "3":
			if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr5"))) {
				svg1 = request.getParameter("chartSVGStr5");
			}

			if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr6"))) {
				svg2 = request.getParameter("chartSVGStr6");
			}
			String minimodelPath = request.getSession().getServletContext()
			.getRealPath(ReportFormConstants.MINIEXPENDPIC_PATH);// 房间布草耗品用量分析图模板路径
			
			map.put("path", path);
			map.put("modelPath", minimodelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg1", svg1);
			map.put("svg2", svg2);	
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("tableType", tableType);
			map.put("analyseResult", analyseResult);
			byteArr = expendFormService.exportLinenOrMiniExpendPic(map);
			break;
		}
		
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
		
	}
	/**
	 * 员工耗品统计
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/selectStaExpendByLimits.do")
	public @ResponseBody String selectStaExpendByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("sLimit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		
		jsonObject = new JSONObject();
		jsonObject = expendFormService.selectStaExpend(map);
		return jsonObject.toString();
	}
	/**
	 * 导出员工领取耗品，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportStaExpendForm.do")
	public ResponseEntity<byte[]> exportStaExpendForm(HttpServletRequest request, HttpServletResponse response) {
		String tableType = null;
		String startTime = null;
		String endTime = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))) {
			tableType = request.getParameter("tableType");// 报表类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.dayFirstTime(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.dayLastTime(request.getParameter("endTime"));// 结束时间
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableType", tableType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = null;
		ResponseEntity<byte[]> byteArr = null;
		switch(tableType){
		case "0":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.STALINEN_PATH);// 模板路径
			byteArr = expendFormService.exportStaLinen(map, path, tempPath);
			break;
		case "1":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.STAROOM_PATH);// 模板路径
			byteArr = expendFormService.exportStaRoom(map, path, tempPath);
			break;
		case "2":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.STAWASH_PATH);// 模板路径
			byteArr = expendFormService.exportStaWash(map, path, tempPath);
			break;
		case "3":
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.STAMINI_PATH);// 模板路径
			byteArr = expendFormService.exportStaMini(map, path, tempPath);
			break;
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
	
	/**
	 * 导出员工领取耗品统计，Excel格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportStaExpendExcel.do")
	public ResponseEntity<byte[]> exportStaExpendExcel(HttpServletRequest request, HttpServletResponse response) {
		String startTime = null;
		String endTime = null;
		String tableType = null;
		ResponseEntity<byte[]> byteArr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.dayFirstTime(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.dayLastTime(request.getParameter("endTime"));// 结束时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))){
			tableType =  request.getParameter("tableType");//耗品类型选择
		}
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("tableType", tableType);
		map.put("path", path);		
		byteArr = expendFormService.exportStaExpendExcel(map);
		
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

}
