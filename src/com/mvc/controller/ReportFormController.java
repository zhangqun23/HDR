package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.enums.DeptType;
import com.mvc.entity.RoomSort;
import com.mvc.entity.StaffInfo;
import com.mvc.service.ReportFormService;
import com.utils.StringUtil;

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

	@RequestMapping("/selectRoomStaffs.do")
	public @ResponseBody String selectStaffByDept(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		if (StringUtil.strIsNotEmpty(request.getParameter("deptType"))) {
			String deptType = request.getParameter("deptType");
			switch (deptType) {
			case "0":
				deptType = DeptType.客房部.value;
				break;
			case "1":
				deptType = DeptType.工程部.value;
				break;
			default:
				break;
			}
			List<StaffInfo> list = reportFormService.selectStaffByDept(deptType);
			jsonObject.put("list", list);
		}
		return jsonObject.toString();
	}

}
