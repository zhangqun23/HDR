package com.mvc.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkLoadLevel;
import com.mvc.service.WorkLoadService;
import com.utils.FileHelper;
import com.utils.StringUtil;
import com.utils.SvgPngConverter;
import com.utils.WordHelper;

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

	/**
	 * 获取所有员工工作量汇总列表信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWorkLoadSummaryList.do")
	public @ResponseBody String getWorkLoadSummaryList(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		String startDate = "2016-01-01";
		String endDate = "2017-01-20";

		List<WorkLoad> workLoadList = workLoadService.getWorkLoadSummaryList(startDate, endDate);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/exportWorkLoadSummaryList.do")
	public ResponseEntity<byte[]> exportWorkLoadSummaryList(HttpServletRequest request, HttpServletResponse response) {

		String startDate = "2016-01-01";
		String endDate = "2017-01-20";

		WordHelper wh = new WordHelper();
		String fileName = "客房部员工工作量汇总表.docx";
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		String modelPath = request.getSession().getServletContext().getRealPath("word\\" + "workLoadSummaryList.docx");// 模板路径

		// 获取列表信息
		List<WorkLoad> workLoadList = workLoadService.getWorkLoadSummaryList(startDate, endDate);
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析
		listMap.put("0", workLoadList);// 注意：key存放该list在word中表格的索引，value存放list

		// 获取文本数据
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("${startDate}", startDate);
		contentMap.put("${endDate}", endDate);

		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, listMap, contentMap, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResponseEntity<byte[]> byteArr = FileHelper.downloadFile(fileName, path);
		return byteArr;
	}

	/**
	 * 获取单个员工工作量分析图信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getStaffWorkLoadAnalyse.do")
	public @ResponseBody String getWorkLoadAnalyse(HttpServletRequest request) {

		JSONObject jsonObject = new JSONObject();
		String startDate = "2016-01-01 00:00:00";
		String endDate = "2017-01-20 23:59:00";
		String staffNo = "125";

		jsonObject.put("data", null);
		return jsonObject.toString();
	}

	/**
	 * 导出单个员工工作量分析图，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/exportWorkLoadAnalyse.do")
	public ResponseEntity<byte[]> exportWorkLoadAnalyse(HttpServletRequest request, HttpServletResponse response) {

		WordHelper wh = new WordHelper();
		String fileName = "客房部单个员工工作量分析表.docx";
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		String modelPath = request.getSession().getServletContext().getRealPath("word\\" + "workLoadAnalyse.docx");// 模板路径

		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("${staffName}", "张三");
		contentMap.put("${date}", "2017");

		// 图片相关
		String picCataPath = request.getSession().getServletContext().getRealPath(ReportFormConstants.PIC_PATH + "\\");
		String svg = request.getParameter("chartSVGStr");

		if (StringUtil.strIsNotEmpty(svg)) {
			String picName = "pic.png";
			String picPath = FileHelper.transPath(picName, picCataPath);// 解析后的上传路径
			try {
				// 图片svgCode转化为png格式，并保存到picPath
				SvgPngConverter.convertToPng(svg, picPath);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (TranscoderException e1) {
				e1.printStackTrace();
			}
			Map<String, Object> picMap = new HashMap<String, Object>();
			picMap.put("width", 420);
			picMap.put("height", 280);
			picMap.put("type", "png");
			try {
				picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPath), true));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			contentMap.put("${pic}", picMap);
		}

		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, null, contentMap, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ResponseEntity<byte[]> byteArr = FileHelper.downloadFile(fileName, path);
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
		String startDate = "2016-01-01";
		String endDate = "2017-01-20";

		List<WorkLoadLevel> WorkLoadLevelList = workLoadService.getWorkLoadLevelList(startDate, endDate);

		jsonObject.put("WorkLoadLevelList", WorkLoadLevelList);
		return jsonObject.toString();
	}

	/**
	 * 导出所有员工工作量饱和度分析列表，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/exportWorkLoadLevelList.do")
	public ResponseEntity<byte[]> exportWorkLoadLevelList(HttpServletRequest request, HttpServletResponse response) {

		String startDate = "2016-01-01";
		String endDate = "2017-01-20";

		WordHelper wh = new WordHelper();
		String fileName = "客房部所有员工工作量饱和度分析表.docx";
		String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		String modelPath = request.getSession().getServletContext().getRealPath("word\\" + "workLoadLevelList.docx");// 模板路径

		// 获取列表信息
		List<WorkLoadLevel> workLoadList = workLoadService.getWorkLoadLevelList(startDate, endDate);
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析
		listMap.put("0", workLoadList);// 注意：key存放该list在word中表格的索引，value存放list

		// 获取文本数据
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("${startDate}", startDate);
		contentMap.put("${endDate}", endDate);
		contentMap.put("${analysisResult}", "所有员工工作量超出额定工作量，且超出幅度过高，因此建议将额定工作量调整至35");

		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, listMap, contentMap, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResponseEntity<byte[]> byteArr = FileHelper.downloadFile(fileName, path);
		return byteArr;
	}
}
