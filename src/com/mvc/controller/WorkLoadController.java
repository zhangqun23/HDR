/**
 * 
 */
package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.dao.WorkLoadDao;
import com.mvc.entity.GoodsInfo;
import com.mvc.entity.RoomInfo;

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
	WorkLoadDao workLoadDao;

	@RequestMapping("/test.do")
	public void test(HttpSession session, HttpServletRequest request, ModelMap model, HttpServletResponse res) {
		List<RoomInfo> count = workLoadDao.count();
		System.out.println("count:" + count.get(0).getRoomSort().getSortName());
	}
	@RequestMapping("/test0.do")
	public void test0(HttpSession session, HttpServletRequest request, ModelMap model, HttpServletResponse res) {
		List<GoodsInfo> count = workLoadDao.count0();
		System.out.println("count:" + count.get(0).getpcCode());
	}


}
