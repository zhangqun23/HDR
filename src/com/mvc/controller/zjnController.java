/**
 * 
 */
package com.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.constants.ReportFormConstants;
import com.mvc.service.ZjnService;
import com.utils.CookieUtil;
import com.utils.StringUtil;

/**
 * 
 * @author zjn
 * @date 2017年1月18日
 */

public class zjnController {
	@Autowired
	ZjnService workLoadService;

	/**
	 * 导出房间或者卫生间耗品用量分析图，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportRoomOrWashExpendPic.do")
	public ResponseEntity<byte[]> exportRoomOrWashExpendPic(HttpServletRequest request, HttpServletResponse response) {

		String svg = "";
		String startTime = "";
		String endTime = "";
		String expendType = "0";// 0代表房间耗品用量分析图;1代表卫生间易耗品分析图
		ResponseEntity<byte[]> byteArr = null;
		Map<String, String> map = new HashMap<String, String>();
		String picCataPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH + "\\");// 图片地址
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		String modelPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.ROOMEXPENDPIC_PATH);// 房间耗品用量分析图模板路径

		if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr"))
				&& StringUtil.strIsNotEmpty(request.getParameter("startTime"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {

			expendType = request.getParameter("expendType");
			svg = request.getParameter("chartSVGStr");
			startTime = request.getParameter("startTime");
			endTime = request.getParameter("endTime");
			if (expendType.equals("1")) {
				modelPath = request.getSession().getServletContext()
						.getRealPath(ReportFormConstants.WASHEXPENDPIC_PATH);// 卫生间易耗品分析图模板路径
			}
			map.put("path", path);
			map.put("modelPath", modelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg", svg);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("expendType", expendType);
			byteArr = workLoadService.exportRoomOrWashExpendPic(map);
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

	/**
	 * 导出房间耗品用量分析图，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportLinenExpendPic.do")
	public ResponseEntity<byte[]> exportLinenExpendPic(HttpServletRequest request, HttpServletResponse response) {

		String svg1 = "";
		String svg2 = "";
		String startTime = "";
		String endTime = "";
		ResponseEntity<byte[]> byteArr = null;
		Map<String, String> map = new HashMap<String, String>();
		String picCataPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH + "\\");// 图片地址
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		String modelPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.ROOMEXPENDPIC_PATH);// 房间耗品用量分析图模板路径

		if (StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr1"))
				&& StringUtil.strIsNotEmpty(request.getParameter("chartSVGStr2"))
				&& StringUtil.strIsNotEmpty(request.getParameter("startTime"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {

			svg1 = request.getParameter("chartSVGStr1");
			svg2 = request.getParameter("chartSVGStr2");
			startTime = request.getParameter("startTime");
			endTime = request.getParameter("endTime");
			map.put("path", path);
			map.put("modelPath", modelPath);
			map.put("picCataPath", picCataPath);
			map.put("svg1", svg1);
			map.put("svg2", svg2);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			byteArr = workLoadService.exportLinenExpendPic(map);
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
}
