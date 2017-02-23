/**
 * 
 */
package com.mvc.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mvc.dao.CheckOutDao;
import com.mvc.entityReport.CheckOutDetail;
import com.mvc.entityReport.CheckOutEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.service.CheckOutService;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.Pager;
import com.utils.StringUtil;
import com.utils.SvgPngConverter;
import com.utils.WordHelper;

/**
 * @author 包阿儒汉
 *
 */
@Service("checkOutServiceImpl")
public class CheckOutServiceImpl implements CheckOutService {
	@Autowired
	CheckOutDao checkOutDao;

	@Override
	public List<CheckOutEfficiency> selectCheckOutEfficiency(Map<String, Object> map) {
		List<Object> listSource = checkOutDao.selectCheckOutEfficiency(map);
		Iterator<Object> it = listSource.iterator();
		List<CheckOutEfficiency> listGoal = objToCheckOutEfficiency(it);

		return listGoal;
	}

	private List<CheckOutEfficiency> objToCheckOutEfficiency(Iterator<Object> it) {
		List<CheckOutEfficiency> listGoal = new ArrayList<CheckOutEfficiency>();
		Object[] obj = null;
		int no = 1;
		DecimalFormat fnum = new DecimalFormat("##0.00");

		CheckOutEfficiency checkOutEfficiency = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			if (obj[0] == null) {
				continue;
			}
			checkOutEfficiency = new CheckOutEfficiency();
			checkOutEfficiency.setAuthorName(obj[1].toString());
			checkOutEfficiency.setAuthorNo(obj[2].toString());
			checkOutEfficiency.setSumTime(obj[3].toString());
			checkOutEfficiency.setGivenTime(fnum.format(Float.parseFloat(obj[4].toString())));
			checkOutEfficiency.setWorkCount(obj[5].toString());
			checkOutEfficiency
					.setWorkEffeciencyAvg(StringUtil.strFloatToPer(fnum.format(Float.parseFloat(obj[8].toString()))));

			String UsedTimeAvg = StringUtil.divide(obj[3].toString(), obj[5].toString());
			checkOutEfficiency.setUsedTimeAvg(UsedTimeAvg);// 平均用时
			String timeOutRate = StringUtil.divide(obj[7].toString(), obj[4].toString());
			checkOutEfficiency.setTimeOutRate(timeOutRate);// 超时率

			checkOutEfficiency.setOrderNum(no + "");
			no++;

			listGoal.add(checkOutEfficiency);
		}
		return listGoal;
	}

	@Override
	public List<CheckOutDetail> selectCheckOutDetail(Map<String, Object> map, Pager pager) {
		List<Object> listSource = checkOutDao.selectCheckOutDetailByPage(map, pager);
		Iterator<Object> it = listSource.iterator();
		List<CheckOutDetail> listGoal = objToCheckOutDetail(it);

		return listGoal;
	}

	private List<CheckOutDetail> objToCheckOutDetail(Iterator<Object> it) {
		List<CheckOutDetail> listGoal = new ArrayList<CheckOutDetail>();
		Object[] obj = null;
		int no = 1;
		CheckOutDetail checkOutDetail = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			if (obj[3] == null) {
				continue;
			}
			if (obj[0] == null) {
				continue;
			}
			checkOutDetail = new CheckOutDetail();
			checkOutDetail.setRoomNo(obj[0].toString());
			checkOutDetail.setUsedTime(obj[1].toString());
			checkOutDetail.setGivenTime(obj[2].toString());
			checkOutDetail.setAuthorName(obj[3].toString());
			checkOutDetail.setOrderNum(no + "");

			checkOutDetail.setWorkEffeciency(
					StringUtil.divide(Float.parseFloat(obj[2].toString()) * 100 + "", obj[1].toString()));// 效率

			no++;

			listGoal.add(checkOutDetail);
		}
		return listGoal;
	}

	@Override
	public int getTotalRowCountCheckOutDetail(Map<String, Object> map) {
		return checkOutDao.getTotalRowCountCheckOutDetail(map);
	}

	@Override
	public String selectCheckOutEffAnalyseByLimits(Map<String, String> map) {
		float[] useTime = null;
		String allAverWorkEfficiency = null;
		String averWorkEfficiency = null;

		Map<String, Object> dateMap = getDate(map);
		String startTime = (String) dateMap.get("startTime");
		String endTime = (String) dateMap.get("endTime");
		Integer monthNum = (Integer) dateMap.get("monthNum");
		Integer startMonth = (Integer) dateMap.get("startMonth");
		String staffId = map.get("staffId");
		String roomType = map.get("roomType");

		List<Object> listObj = new ArrayList<>();
		listObj = checkOutDao.avgPerMonthsStaff(startTime, endTime, staffId, roomType);
		useTime = handelPerMonthsStaff(listObj, startMonth, monthNum);// 补齐月份

		allAverWorkEfficiency = (Math
				.round(Float.parseFloat(checkOutDao.allAverWorkEfficiency(startTime, endTime, roomType)) * 100) / 100)
				+ "";
		averWorkEfficiency = (Math.round(
				Float.parseFloat(checkOutDao.averWorkEfficiency(startTime, endTime, roomType, staffId)) * 100) / 100)
				+ "";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("list", useTime);// 按月平均 公式：当月任务用时总合/任务数量
		jsonObj.put("averWorkEfficiency", averWorkEfficiency);// 所选员工全年任务用时总合/全年任务数量
		jsonObj.put("allAverWorkEfficiency", allAverWorkEfficiency);// 所有员工全年任务用时总合/全年任务数量
		
		float ff = 0f;
		ff = (Float.parseFloat(averWorkEfficiency) - Float.parseFloat(allAverWorkEfficiency))
				/ Float.parseFloat(allAverWorkEfficiency);
		StringBuilder analyseResult = new StringBuilder();
		analyseResult.append("分析结果：");
		if (ff > 0.05f) {
			analyseResult.append("优秀（该员工平均用时高于全体员工平均用时）");
		} else if (ff > 0.0000f) {
			analyseResult.append("良好（该员工平均用时略高于全体员工平均用时）");
		} else if (ff < -0.05f) {
			analyseResult.append("较差（该员工平均用时低于全体员工平均用时）");
		} else if (ff < 0.0000f) {
			analyseResult.append("一般（该员工平均用时略低于全体员工平均用时）");
		}

		jsonObj.put("analyseResult", analyseResult);
		return jsonObj.toString();
	}

	private float[] handelPerMonthsStaff(List<Object> list, Integer startMonth, Integer monthNum) {
		float[] useTime = new float[monthNum];
		int j = startMonth;
		int size = list.size();
		Object[] obj = null;
		Integer month = null;

		for (int i = 0; i < size || j < startMonth + monthNum; i++, j++) {
			if (i < size) {
				obj = (Object[]) list.get(i);
				month = Integer.valueOf(obj[1].toString());
				if (month == j) {
					if (obj[0] != null && obj[2] != null) {
						useTime[j - 1] = Float.parseFloat(StringUtil.divide(obj[0].toString(), obj[2].toString()));
					}
				} else {
					useTime[i] = 0f;
					i--;
				}
			} else {
				useTime[i] = 0f;
			}
		}

		return useTime;
	}

	// 获取每个季度相关信息
	private Map<String, Object> getDate(Map<String, String> map) {

		String checkYear = map.get("checkYear");
		String quarter = map.get("quarter");
		Map<String, Object> dateMap = new HashMap<String, Object>();
		Integer monthNum = 12;
		String startTime = StringUtil.quarterFirstDay(checkYear, quarter);
		String endTime = StringUtil.quarterLastDay(checkYear, quarter);
		String quarterName = "";
		Integer startMonth = 1;

		switch (quarter) {
		case "0":
			quarterName = "全年";
			break;
		case "1":
			monthNum = 3;
			quarterName = "年第一季度";
			startMonth = 1;
			break;
		case "2":
			monthNum = 3;
			quarterName = "年第二季度";
			startMonth = 4;
			break;
		case "3":
			monthNum = 3;
			quarterName = "年第三季度";
			startMonth = 7;
			break;
		case "4":
			monthNum = 3;
			quarterName = "年第四季度";
			startMonth = 10;
			break;
		default:
			break;
		}
		dateMap.put("startTime", startTime);
		dateMap.put("endTime", endTime);
		dateMap.put("monthNum", monthNum);
		dateMap.put("quarterName", checkYear + quarterName);
		dateMap.put("startMonth", startMonth);
		return dateMap;
	}

	@Override
	public ResponseEntity<byte[]> exportCheckOutEfficiency(Map<String, Object> map, String path, String tempPath) {
		String sortName = (String) map.remove("sortName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工查退房（" + sortName + "）效率统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = checkOutDao.selectCheckOutEfficiency(map);
			Iterator<Object> it = listSource.iterator();
			List<CheckOutEfficiency> listGoal = objToCheckOutEfficiency(it);

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("${sortName}", sortName);
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 7));
			contentMap.put("${endTime}", endTime.substring(0, 7));

			wh.export2007Word(tempPath, listMap, contentMap, 1, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

	@Override
	public ResponseEntity<byte[]> exportCheckOutDetail(Map<String, Object> map, String path, String tempPath) {
		String sortName = (String) map.remove("sortName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工查退房（" + sortName + "）明细表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = checkOutDao.selectCheckOutDetail(map);
			Iterator<Object> it = listSource.iterator();
			List<CheckOutDetail> listGoal = objToCheckOutDetail(it);

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("${sortName}", sortName);
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 7));
			contentMap.put("${endTime}", endTime.substring(0, 7));

			wh.export2007Word(tempPath, listMap, contentMap, 1, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

	@Override
	public ResponseEntity<byte[]> exportCheckOutAnalyseByLimits(Map<String, Object> map, String path, String tempPath,
			String picPath) {
		String staffName = (String) map.get("staffName");
		String sortName = (String) map.get("sortName");
		String year = (String) map.get("checkYear");
		String quarter = (String) map.get("quarter");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工查退房（" + sortName + "）用时分析.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("${staffName}", staffName);
			contentMap.put("${sortName}", sortName);// 房间类型名称
			if (StringUtil.strIsNotEmpty(year) && StringUtil.strIsNotEmpty(quarter)) {
				String startTime = StringUtil.quarterFirstDay(year, quarter);
				String endTime = StringUtil.quarterLastDay(year, quarter);
				startTime = startTime.substring(0, 10);// 保留到天
				endTime = endTime.substring(0, 10);
				contentMap.put("${startTime}", startTime);
				contentMap.put("${endTime}", endTime);
			}

			// 图片相关
			String svg1 = (String) map.get("chart1SVGStr");
			String picName1 = null;
			if (StringUtil.strIsNotEmpty(svg1)) {
				picName1 = "pic1.png";
				picPath = FileHelper.transPath(picName1, picPath);
			}
			Map<String, Object> picMap = null;
			picMap = new HashMap<String, Object>();
			picMap.put("width", 960);
			picMap.put("height", 400);
			picMap.put("type", "png");
			try {
				SvgPngConverter.convertToPng(svg1, picPath);// 图片svgCode转化为png格式，并保存到服务器
				picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPath), true));// 将图片流放到map中
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			contentMap.put("${pic1}", picMap);

			wh.export2007Word(tempPath, null, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

	@Override
	public ResponseEntity<byte[]> exportCheckOutDetailExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteArr = null;
		String sortName = (String) map.remove("sortName");
		String startDate = ((String) map.get("startTime")).substring(0, 7);
		String endDate = ((String) map.get("endTime")).substring(0, 7);
		String fileName = "客房部员工查退房（" + sortName + "）明细表.xlsx";
		String title = "客房部员工查退房（" + sortName + "）明细表(" + startDate + "至" + endDate + ")";
		try {
			ExcelHelper<CheckOutDetail> ex = new ExcelHelper<CheckOutDetail>();
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			// 获取列表和文本信息
			List<Object> listSource = checkOutDao.selectCheckOutDetail(map);
			Iterator<Object> it = listSource.iterator();
			List<CheckOutDetail> listGoal = objToCheckOutDetail(it);
	
			String[] header = { "序号", "房号", "查退房时间（分钟）", "给定时间（分钟）", "效率", "完成员工" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, (Collection<CheckOutDetail>) listGoal, out, "yyyy-MM-dd", -1, 0, 1);// -1表示没有合并单元格，1:隐藏了实体类最后一个字段内容

			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArr;
	}

	@Override
	public ResponseEntity<byte[]> exportCheckOutEfficiencyExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteArr = null;
		String sortName = (String) map.remove("sortName");
		String startDate = ((String) map.get("startTime")).substring(0, 7);
		String endDate = ((String) map.get("endTime")).substring(0, 7);
		String fileName = "客房部员工查退房（" + sortName + "）效率统计表.xlsx";
		String title = "客房部员工查退房（" + sortName + "）效率统计表(" + startDate + "至" + endDate + ")";
		try {
			ExcelHelper<CheckOutEfficiency> ex = new ExcelHelper<CheckOutEfficiency>();
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			// 获取列表和文本信息
			List<Object> listSource = checkOutDao.selectCheckOutEfficiency(map);
			Iterator<Object> it = listSource.iterator();
			List<CheckOutEfficiency> listGoal = objToCheckOutEfficiency(it);
		
			String[] header = { "序号", "员工姓名", "员工编号", "总用时（分钟）", "平均给定时间（分钟）", "平均抢房时间（分钟）", "抢房总数", "平均抢房效率", "超时率"};// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, (Collection<CheckOutEfficiency>) listGoal, out, "yyyy-MM-dd", -1, 0, 1);// -1表示没有合并单元格，1:隐藏了实体类最后一个字段内容

			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArr;
	}

}
