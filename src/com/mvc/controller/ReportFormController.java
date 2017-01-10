package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entity.RoomSort;
import com.mvc.entity.StaffInfo;
import com.mvc.service.ReportFormService;

import net.sf.json.JSONObject;

/**
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Controller
@RequestMapping("/reportForm")
public class ReportFormController {

	@Autowired
	ReportFormService reportFormService;

	@RequestMapping("/toReportFormPage.do")
	public String InvoiceReceivePage() {
		return "reportForm/index";
	}

	@RequestMapping("/getRoomSorts.do")
	public @ResponseBody String selectRoomSort(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<RoomSort> list = reportFormService.selectRoomSort();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	@RequestMapping("/selectAllStaffs.do")
	public @ResponseBody String selectAllStaff(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<StaffInfo> list = reportFormService.selectAllStaff();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

}
