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
import com.mvc.entityReport.EngineMaterial;
import com.mvc.service.EngineMaterialService;
import com.utils.CookieUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 工程物料管理控制器
 * 
 * @author wangrui
 * @date 2017年2月20日
 */
@Controller
@RequestMapping("/engineMaterial")
public class EngineMaterialController {

	@Autowired
	EngineMaterialService engineMaterialService;

	/**
	 * 查询工程物料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectEngineMaterialBylimits.do")
	public @ResponseBody String selectEngineMaterial(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		List<EngineMaterial> list = engineMaterialService.selectEngineMaterial(map);
		jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 将JsonObject转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Map<String, Object> JsonObjToMap(JSONObject jsonObject) {
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("startTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("startTime"))) {
				startTime = StringUtil.monthFirstDay(jsonObject.getString("startTime"));// 开始时间
			}
		}
		if (jsonObject.containsKey("endTime")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("endTime"))) {
				endTime = StringUtil.monthLastDay(jsonObject.getString("endTime"));// 结束时间
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return map;
	}

	/**
	 * 工程物料统计Word
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportEngineMaterialWordBylimits.do")
	public ResponseEntity<byte[]> exportEngineMaterial(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = requestToMap(request);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.ENGINEMATERIAL_PATH);// 模板路径
		ResponseEntity<byte[]> byteArr = engineMaterialService.exportEngineMaterial(map, path, tempPath);

		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**
	 * 部门员工做房用时统计Excel
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportEngineMaterialExcelBylimits.do")
	public ResponseEntity<byte[]> exportWorkHouseExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = requestToMap(request);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		ResponseEntity<byte[]> byteArr = engineMaterialService.exportEngineMaterialExcel(map, path);

		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**
	 * 将request转换成Map
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> requestToMap(HttpServletRequest request) {
		String startTime = null;
		String endTime = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.monthFirstDay(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.monthLastDay(request.getParameter("endTime"));// 结束时间
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return map;
	}

}
