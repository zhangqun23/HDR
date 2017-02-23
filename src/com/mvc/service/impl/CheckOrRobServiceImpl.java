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
import com.base.enums.CleanType;
import com.mvc.dao.CheckOrRobDao;
import com.mvc.entity.DepartmentInfo;
import com.mvc.entityReport.RobDetail;
import com.mvc.entityReport.RobEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkLoadLevel;
import com.mvc.service.CheckOrRobService;
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
@Service("checkOrRobServiceImpl")
public class CheckOrRobServiceImpl implements CheckOrRobService {
	@Autowired
	CheckOrRobDao checkOrRobDao;

	@Override
	public List<RobEfficiency> selectRobEfficiency(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Object> listSource = checkOrRobDao.selectRobEfficiency(map);
		Iterator<Object> it = listSource.iterator();
		List<RobEfficiency> listGoal = objToRobEfficiency(it);

		return listGoal;
	}

	private List<RobEfficiency> objToRobEfficiency(Iterator<Object> it) {
		List<RobEfficiency> listGoal = new ArrayList<RobEfficiency>();
		Object[] obj = null;
		int no = 1;
		DecimalFormat fnum = new DecimalFormat("##0.00");

		RobEfficiency robEfficiency = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			if (obj[0] == null) {
				continue;
			}
			robEfficiency = new RobEfficiency();
			robEfficiency.setAuthorName(obj[1].toString());
			robEfficiency.setAuthorNo(obj[2].toString());
			robEfficiency.setSumTime(obj[3].toString());
			robEfficiency.setGivenTime(fnum.format(Float.parseFloat(obj[4].toString())));
			robEfficiency.setWorkCount(obj[5].toString());
			robEfficiency
					.setWorkEffeciencyAvg(StringUtil.strFloatToPer(fnum.format(Float.parseFloat(obj[8].toString()))));

			String UsedTimeAvg = StringUtil.divide(obj[3].toString(), obj[5].toString());
			robEfficiency.setUsedTimeAvg(UsedTimeAvg);// 平均用时
			String backRate = StringUtil.divide(obj[6].toString(), obj[4].toString());
			robEfficiency.setBackRate(backRate);// 驳回率
			String timeOutRate = StringUtil.divide(obj[7].toString(), obj[4].toString());
			robEfficiency.setTimeOutRate(timeOutRate);// 超时率

			robEfficiency.setOrderNum(no + "");
			no++;

			listGoal.add(robEfficiency);
		}
		return listGoal;
	}

	@Override
	public List<RobDetail> selectRobDetailByLimits(Map<String, Object> map, Pager pager) {
		List<Object> listSource = checkOrRobDao.selectRobDetailByPage(map, pager);
		Iterator<Object> it = listSource.iterator();
		List<RobDetail> listGoal = objToRobDetail(it);

		return listGoal;

	}

	private List<RobDetail> objToRobDetail(Iterator<Object> it) {
		List<RobDetail> listGoal = new ArrayList<RobDetail>();
		Object[] obj = null;
		int no = 1;
		RobDetail robDetail = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			robDetail = new RobDetail();
			robDetail.setRoomNo(obj[0].toString());
			robDetail.setUsedTime(obj[1].toString());
			robDetail.setGivenTime(obj[2].toString());
			robDetail.setAuthorName(obj[3].toString());
			robDetail.setIsBack(obj[4].toString());
			robDetail.setCheckUsedTime(obj[5].toString());
			robDetail.setCheckerName(obj[6].toString());
			robDetail.setOrderNum(no + "");

			robDetail.setWorkEffeciency(
					StringUtil.divide(Float.parseFloat(obj[2].toString()) * 100 + "", obj[1].toString()));// 效率

			no++;

			listGoal.add(robDetail);
		}
		return listGoal;
	}

	@Override
	public int getTotalRowCountRobDetail(Map<String, Object> map) {

		return checkOrRobDao.getTotalRowCountRobDetail(map);
	}

	@Override
	public String selectRobEffAnalyseByLimits(Map<String, String> map) {
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
		listObj = checkOrRobDao.avgPerMonthsStaff(startTime, endTime, staffId, roomType);
		useTime = handelPerMonthsStaff(listObj, startMonth, monthNum);// 补齐月份

		allAverWorkEfficiency = (Math
				.round(Float.parseFloat(checkOrRobDao.allAverWorkEfficiency(startTime, endTime, roomType)) * 100) / 100)
				+ "";
		averWorkEfficiency = (Math.round(
				Float.parseFloat(checkOrRobDao.averWorkEfficiency(startTime, endTime, roomType, staffId)) * 100) / 100)
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
	public ResponseEntity<byte[]> exportRobEfficiency(Map<String, Object> map, String path, String tempPath) {
		String sortName = (String) map.remove("sortName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工抢房（" + sortName + "）效率统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = checkOrRobDao.selectRobEfficiency(map);
			Iterator<Object> it = listSource.iterator();
			List<RobEfficiency> listGoal = objToRobEfficiency(it);

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
	public ResponseEntity<byte[]> exportRobEfficiencyExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteArr = null;
		String sortName = (String) map.remove("sortName");
		String startDate = ((String) map.get("startTime")).substring(0, 7);
		String endDate = ((String) map.get("endTime")).substring(0, 7);
		String fileName = "客房部员工抢房（" + sortName + "）效率统计表.xlsx";
		String title = "客房部员工抢房（" + sortName + "）效率统计表(" + startDate + "至" + endDate + ")";
		try {
			ExcelHelper<RobEfficiency> ex = new ExcelHelper<RobEfficiency>();
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			// 获取列表和文本信息
			List<Object> listSource = checkOrRobDao.selectRobEfficiency(map);
			Iterator<Object> it = listSource.iterator();
			List<RobEfficiency> listGoal = objToRobEfficiency(it);

			String[] header = { "序号", "员工姓名", "员工编号", "总用时（分钟）", "平均给定时间（分钟）", "平均抢房时间（分钟）", "抢房总数", "平均抢房效率", "超时率",

					"驳回率" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, (Collection<RobEfficiency>) listGoal, out, "yyyy-MM-dd", -1, 0, 1);// -1表示没有合并单元格，1:隐藏了实体类最后一个字段内容


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
	public ResponseEntity<byte[]> exportRobDetail(Map<String, Object> map, String path, String tempPath) {
		String sortName = (String) map.remove("sortName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工抢房（" + sortName + "）明细表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = checkOrRobDao.selectRobDetail(map);
			Iterator<Object> it = listSource.iterator();
			List<RobDetail> listGoal = objToRobDetail(it);

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
	public ResponseEntity<byte[]> exportRobDetailExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteArr = null;
		String sortName = (String) map.remove("sortName");
		String startDate = ((String) map.get("startTime")).substring(0, 7);
		String endDate = ((String) map.get("endTime")).substring(0, 7);
		String fileName = "客房部员工抢房（" + sortName + "）明细表.xlsx";
		String title = "客房部员工抢房（" + sortName + "）明细表(" + startDate + "至" + endDate + ")";
		try {
			ExcelHelper<RobDetail> ex = new ExcelHelper<RobDetail>();
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			// 获取列表和文本信息
			List<Object> listSource = checkOrRobDao.selectRobDetail(map);
			Iterator<Object> it = listSource.iterator();
			List<RobDetail> listGoal = objToRobDetail(it);

			String[] header = { "序号", "房号", "做房时间（分钟）", "给定时间（分钟）", "效率", "完成员工", "驳回次数", "检查用时（分钟）", "检查人" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, (Collection<RobDetail>) listGoal, out, "yyyy-MM-dd",-1, 0, 1);// -1表示没有合并单元格，1:隐藏了实体类最后一个字段内容

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
	public ResponseEntity<byte[]> exportRobAnalyseByLimits(Map<String, Object> map, String path, String tempPath,
			String picPath) {

		String staffName = (String) map.get("staffName");
		String sortName = (String) map.get("sortName");
		String year = (String) map.get("checkYear");
		String quarter = (String) map.get("quarter");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工（" + sortName + "）抢房用时分析.docx";
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

}
