/**
 * 
 */
package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.CheckOutDetail;
import com.mvc.entityReport.CheckOutEfficiency;
import com.mvc.entityReport.RobDetail;
import com.mvc.entityReport.RobEfficiency;
import com.mvc.service.CheckOutService;
import com.utils.Pager;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * @author 包阿儒汉
 *
 */
@Controller
@RequestMapping("/checkOutHome")
public class CheckOutController {
	@Autowired
	CheckOutService checkOutService;

	/**
	 * 查询抢房效率统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectCheckOutEfficiency.do")
	public @ResponseBody String selectCheckOutEfficiency(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		List<CheckOutEfficiency> list = checkOutService.selectCheckOutEfficiency(map);
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
		String roomType = null;
		String startTime = null;
		String endTime = null;
		if (jsonObject.containsKey("roomType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("roomType"))) {
				roomType = jsonObject.getString("roomType");// 房间类型
			}
		}
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
		map.put("roomType", roomType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return map;
	}

	/**
	 * 查询抢房明细统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectCheckOutDetailByLimits.do")
	public @ResponseBody String selectRobDetailByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, Object> map = JsonObjToMap(jsonObject);
		Pager pager = new Pager();
		pager.setPage(Integer.parseInt(request.getParameter("page")));
		pager.setPageSize(10);
		pager.setTotalRow(checkOutService.getTotalRowCountCheckOutDetail(map));

		List<CheckOutDetail> list = checkOutService.selectCheckOutDetail(map, pager);
		jsonObject = new JSONObject();
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		return jsonObject.toString();
	}

	/**
	 * 查询抢房效率分析折线图
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectCheckOutEffAnalyseByLimits.do")
	public @ResponseBody String selectRobEffAnalyseByLimits(HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("limit"));

		Map<String, String> map = JsonObjToMapCheckOutEffAnalyse(jsonObject);

		String str = checkOutService.selectCheckOutEffAnalyseByLimits(map);
		return str;
	}

	private Map<String, String> JsonObjToMapCheckOutEffAnalyse(JSONObject jsonObject) {
		String checkYear = null;
		String quarter = null;
		String roomType = null;
		String StaffId = null;
		if (jsonObject.containsKey("checkYear")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("checkYear"))) {
				checkYear = jsonObject.getString("checkYear");
			}
		}
		if (jsonObject.containsKey("quarter")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("quarter"))) {
				quarter = jsonObject.getString("quarter");
			}
		}
		if (jsonObject.containsKey("roomType")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("roomType"))) {
				roomType = jsonObject.getString("roomType");
			}
		}
		if (jsonObject.containsKey("staffId")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("staffId"))) {
				StaffId = jsonObject.getString("staffId");
				System.out.println(StaffId);
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("roomType", roomType);
		map.put("staffId", StaffId);
		return map;
	}

	/**
	 * word导出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportCheckOutRooms.do")
	public ResponseEntity<byte[]> exportCheckOutRooms(HttpServletRequest request) {
		String roomType = null;
		String sortName = null;
		String startTime = null;
		String endTime = null;
		String tableType = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("roomType"))) {
			roomType = request.getParameter("roomType");// 房间类型(sort_no)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("sortName"))) {
			sortName = request.getParameter("sortName");// 房间类型名称(sort_name)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.monthFirstDay(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.monthLastDay(request.getParameter("endTime"));// 结束时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))) {
			tableType = request.getParameter("tableType");// 结束时间
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomType", roomType);
		map.put("sortName", sortName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = null;

		ResponseEntity<byte[]> byteArr = null;
		if (tableType.equals("0")) {
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.ROBEFFICIENCY_PATH);// 模板路径
			byteArr = checkOutService.exportCheckOutEfficiency(map, path, tempPath);
		} else {
			tempPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.ROBDETAIL_PATH);// 模板路径
			byteArr = checkOutService.exportCheckOutDetail(map, path, tempPath);
		}
		return byteArr;
	}

	/**
	 * excel导出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportCheckOutRoomsExcel.do")
	public ResponseEntity<byte[]> exportRobExcelBylimits(HttpServletRequest request) {
		String roomType = null;
		String sortName = null;
		String startTime = null;
		String endTime = null;
		String tableType = null;
		if (StringUtil.strIsNotEmpty(request.getParameter("roomType"))) {
			roomType = request.getParameter("roomType");// 房间类型(sort_no)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("sortName"))) {
			sortName = request.getParameter("sortName");// 房间类型名称(sort_name)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))) {
			startTime = StringUtil.monthFirstDay(request.getParameter("startTime"));// 开始时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			endTime = StringUtil.monthLastDay(request.getParameter("endTime"));// 结束时间
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("tableType"))) {
			tableType = request.getParameter("tableType");// 结束时间
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomType", roomType);
		map.put("sortName", sortName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径

		ResponseEntity<byte[]> byteArr = null;
		if (tableType.equals("0")) {
			byteArr = checkOutService.exportCheckOutEfficiencyExcel(map, path);
		} else {
			byteArr = checkOutService.exportCheckOutDetailExcel(map, path);
		}
		return byteArr;
	}

	/**
	 * 做房用时分析导出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportCheckOutAnalyse.do")
	public ResponseEntity<byte[]> exportCheckOutAnalyse(HttpServletRequest request) {
		String checkYear = null;
		String quarter = null;
		String roomType = null;
		String sortName = null;
		String staffName = null;
		String chart1SVGStr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("checkYear"))) {
			checkYear = request.getParameter("checkYear");// 年份
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("quarter"))) {
			quarter = request.getParameter("quarter");// 季度
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("sortName"))) {
			sortName = request.getParameter("sortName");// 房间类型名称(sort_name)
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("roomType"))) {
			roomType = request.getParameter("roomType");// 打扫类型
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("staffName"))) {
			staffName = request.getParameter("staffName");// 员工姓名
		}
		if (StringUtil.strIsNotEmpty(request.getParameter("chart1SVGStr"))) {
			chart1SVGStr = request.getParameter("chart1SVGStr");// SVG图片字符串
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkYear", checkYear);
		map.put("quarter", quarter);
		map.put("sortName", sortName);
		map.put("roomType", roomType);
		map.put("staffName", staffName);
		map.put("chart1SVGStr", chart1SVGStr);

		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);// 上传服务器的路径
		String tempPath = request.getSession().getServletContext()
				.getRealPath(ReportFormConstants.ROBEFFICIENCYANALYSE_PATH);// 模板路径
		String picPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH);// 图片路径
		ResponseEntity<byte[]> byteArr = checkOutService.exportCheckOutAnalyseByLimits(map, path, tempPath, picPath);

		return byteArr;
	}
}
