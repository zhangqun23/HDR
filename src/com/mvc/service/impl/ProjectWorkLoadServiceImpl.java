package com.mvc.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvc.dao.WorkLoadDao;
import com.mvc.entity.StaffInfo;
import com.mvc.entityReport.ProjectWorkLoad;
import com.mvc.entityReport.WorkLoadMonth;
import com.mvc.repository.StaffInfoRepository;
import com.mvc.service.ProjectWorkLoadService;
import com.utils.CollectionUtil;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.WordHelper;

import net.sf.json.JSONObject;

/**
 * 工程部员工工作量相关Service层实现
 * 
 * @author zjn
 * @date 2017年2月20日
 */
@Service("projectWorkLoadServiceImpl")
public class ProjectWorkLoadServiceImpl implements ProjectWorkLoadService {

	@Autowired
	WorkLoadDao workLoadDao;
	@Autowired
	StaffInfoRepository staffInfoRepository;

	// 获取工程部所有员工工作量列表
	@Override
	public List<ProjectWorkLoad> getProWorkLoadList(String startTime, String endTime) {
		List<Object> listSource = workLoadDao.getProWorkLoadInfo(startTime, endTime);

		Iterator<Object> it = listSource.iterator();
		List<ProjectWorkLoad> listGoal = objToProWorkLoad(it);
		return listGoal;
	}

	// List<Object>类型转换成List<ProjectWorkLoad>
	private List<ProjectWorkLoad> objToProWorkLoad(Iterator<Object> it) {
		Object[] obj = null;
		ProjectWorkLoad proWorkLoad = null;
		List<ProjectWorkLoad> listGoal = new ArrayList<ProjectWorkLoad>();

		Integer totalWorkLoad = 0;

		while (it.hasNext()) {
			obj = (Object[]) it.next();
			proWorkLoad = new ProjectWorkLoad();

			proWorkLoad.setStaffNo(obj[0].toString());
			proWorkLoad.setStaffName(obj[1].toString());
			proWorkLoad.setWorkLoad(Integer.valueOf(obj[2].toString()));// 单个员工总工作量
			totalWorkLoad += Integer.valueOf(obj[2].toString());// 所有员工总工作量

			listGoal.add(proWorkLoad);
		}
		// 按员工实际工作量进行排序并编号
		sortAndWrite(listGoal, "workLoad", false, "rank");

		Iterator<ProjectWorkLoad> itGoal = listGoal.iterator();
		int i = 0;
		proWorkLoad = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			proWorkLoad = (ProjectWorkLoad) itGoal.next();
			proWorkLoad.setOrderNum(String.valueOf(i));
		}
		// 列表最后一行追加合计
		proWorkLoad = new ProjectWorkLoad();
		proWorkLoad.setStaffName("合计：");
		proWorkLoad.setWorkLoad(totalWorkLoad);// 抹尘房总工作量
		listGoal.add(proWorkLoad);

		return listGoal;
	}

	/**
	 * 排序并插入序号
	 * 
	 * @param list
	 * @param filedName
	 *            按指定字段排序
	 * @param ascFlag
	 *            true升序,false降序
	 * @param writeField
	 *            向指定字段插入序号
	 */
	private void sortAndWrite(List<ProjectWorkLoad> list, String filedName, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, filedName, ascFlag);
		CollectionUtil<ProjectWorkLoad> collectionUtil = new CollectionUtil<ProjectWorkLoad>();
		collectionUtil.writeSort(list, writeField);
	}

	// 导出工程部所有员工工作量word
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportProWorkLoadWord(Map<String, Object> map) {
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String path = (String) map.get("path");
		String modelPath = (String) map.get("modelPath");
		String fileName = "工程部员工工作量汇总表.docx";
		String analyseResult = "";

		WordHelper wh = new WordHelper();
		ResponseEntity<byte[]> byteArr = null;
		List<ProjectWorkLoad> proWorkLoadList = null;
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析
		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据

		path = FileHelper.transPath(fileName, path);// 解析后的上传路径

		// 获取列表和文本信息
		proWorkLoadList = getProWorkLoadList(startDate, endDate);
		analyseResult = getAnalyseResult(proWorkLoadList);

		listMap.put("0", proWorkLoadList);// 注意：key存放该list在word中表格的索引，value存放list
		contentMap.put("${startDate}", startDate);
		contentMap.put("${endDate}", endDate);
		contentMap.put("${result}", analyseResult);

		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, listMap, contentMap, 1, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteArr = FileHelper.downloadFile(fileName, path);
		return byteArr;
	}

	// 导出工程部所有员工工作量Excel
	@Override
	public ResponseEntity<byte[]> exportProWorkLoadExcel(Map<String, Object> map) {
		ResponseEntity<byte[]> byteArr = null;
		List<ProjectWorkLoad> proWorkLoadList = null;

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String path = (String) map.get("path");
		String fileName = "客房部员工工作量汇总表.xlsx";
		String title = "客房部员工工作量汇总表(" + startDate + "至" + endDate + ")";
		try {
			ExcelHelper<ProjectWorkLoad> ex = new ExcelHelper<ProjectWorkLoad>();
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			// 获取列表和文本信息
			proWorkLoadList = getProWorkLoadList(startDate, endDate);

			String[] header = { "序号", "员工姓名", "员工编号", "工作量", "排名" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, (Collection<ProjectWorkLoad>) proWorkLoadList, out, "yyyy-MM-dd",-1, -1,-1, 0,
					1);// -1表示没有合并单元格,2:隐藏了实体类最后两个字段内容,1表示一行表头

			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArr;
	}

	// 获取工程部单个员工工作量分析图所需数据
	@Override
	public String getProWorkLoadAnalyse(Map<String, String> map) {
		JSONObject jsonObject = new JSONObject();
		Integer staffCount = 0;
		Float averageData = (float) 0;// 个人平均工作量
		Float allAverageData = (float) 0;// 全体员工的平均工作量
		Float totalActualWorkLoad = (float) 0;// 全体员工的实际总工作量
		String analyseResult = "";// 分析结果
		List<WorkLoadMonth> workLoadMonths = new ArrayList<WorkLoadMonth>();
		List<Object> listSorce = new ArrayList<Object>();

		Integer staffId = Integer.valueOf(map.get("staffId"));
		Map<String, Object> dateMap = getDate(map);
		String startTime = (String) dateMap.get("startTime");
		String endTime = (String) dateMap.get("endTime");
		Integer monthNum = (Integer) dateMap.get("monthNum");
		Integer startMonth = (Integer) dateMap.get("startMonth");

		staffCount = workLoadDao.proStaffCount(startTime, endTime);
		if (staffCount != 0) {
			totalActualWorkLoad = workLoadDao.getTotalActualProWorkLoad(startTime, endTime);
			listSorce = workLoadDao.getMonthProWorkLoad(startTime, endTime, staffId);

			workLoadMonths = perMonth(listSorce, startMonth, monthNum);// 补齐缺省月份
			for (int i = 0; i < workLoadMonths.size(); i++) {
				averageData += workLoadMonths.get(i).getProActWorkLoad();
			}
			averageData = averageData / monthNum;
			allAverageData = totalActualWorkLoad / monthNum / staffCount;
			analyseResult = getAnalyseResult(averageData, allAverageData);

			jsonObject.put("allAverWorkLoad", allAverageData);// 全体平均工作量
			jsonObject.put("averWorkLoad", averageData);// 个人平均工作量
			jsonObject.put("list", workLoadMonths);// 个人每个月的工作量
			jsonObject.put("analyseResult", analyseResult);// 分析结果
		}

		return jsonObject.toString();
	}

	private String getAnalyseResult(Float averageData, Float allAverageData) {
		String analyseResult = "分析结果: ";
		if (allAverageData > 0) {
			analyseResult += "全体员工的平均工作量为：" + StringUtil.save2Float(allAverageData) + "。 该员工的平均工作量为："
					+ StringUtil.save2Float(averageData) + "，其整体表现： ";

			Float level = (averageData - allAverageData) / allAverageData;
			if (Math.abs(level) <= 0.05) {
				analyseResult += "良好（平均工作量与全体员工平均工作量相差不大）。";
			} else if (level > 0.05) {
				analyseResult += "优秀（平均工作量高于全体员工平均工作量）。";
			} else if (level < 0.05) {
				analyseResult += "不合格（平均工作量远低于全体员工平均工作量）。";
			}
		}
		return analyseResult;
	}

	/**
	 * 补齐缺少月份，将其字段设为0
	 * 
	 * @param list
	 * @param startMonth：起始月份，每个季度不一样
	 * @param monthNum:总共统计月份个数
	 * @return
	 */
	private List<WorkLoadMonth> perMonth(List<Object> list, Integer startMonth, Integer monthNum) {

		List<WorkLoadMonth> listGoal = new ArrayList<WorkLoadMonth>();
		WorkLoadMonth actualLoad = null;
		Integer j = startMonth;
		Integer size = list.size();
		Object[] obj = null;
		Integer month = null;

		for (int i = 0; i < size || j < startMonth + monthNum; i++, j++) {
			actualLoad = new WorkLoadMonth();
			if (i < size) {
				obj = (Object[]) list.get(i);
				month = Integer.valueOf(obj[0].toString());
				if (month == j) {
					actualLoad.setMonth(Integer.valueOf(obj[0].toString()));
					if (obj[1] != null) {
						actualLoad.setProActWorkLoad(Integer.valueOf(obj[1].toString()));
					}
					listGoal.add(actualLoad);
				} else {
					actualLoad.setMonth(j);
					actualLoad.setProActWorkLoad(0);
					listGoal.add(actualLoad);
					i--;
				}
			} else {
				actualLoad.setMonth(j);
				actualLoad.setProActWorkLoad(0);
				listGoal.add(actualLoad);
			}
		}
		return listGoal;
	}

	// 导出工程部单个员工工作量分析图
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportProWorkLoadAnalyse(Map<String, String> map) {
		ResponseEntity<byte[]> byteArr = null;
		WordHelper wh = new WordHelper();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		Map<String, Object> picMap = new HashMap<String, Object>();
		Map<String, Object> dateMap = getDate(map);

		String path = map.get("path");
		String modelPath = map.get("modelPath");
		String picCataPath = map.get("picCataPath");
		String svg = map.get("svg");
		StaffInfo staffInfo = staffInfoRepository.findById(Integer.valueOf(map.get("staffId")));
		String staffName = staffInfo.getStaff_name();
		String staffNo = staffInfo.getStaff_no();
		String date = (String) dateMap.get("quarterName");
		String fileName = "工程部员工" + (staffNo + staffName) + date + "工作量分析结果.docx";
		String analyseResult = map.get("analyseResult");

		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		picMap = PictureUtil.getPicMap(picCataPath, svg);

		contentMap.put("${staffName}", staffNo + staffName);
		contentMap.put("${date}", date);
		contentMap.put("${pic}", picMap);
		contentMap.put("${analyseResult}", analyseResult);

		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, null, contentMap, 1, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteArr = FileHelper.downloadFile(fileName, path);
		return byteArr;
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

	// 获取分析结果
	@Override
	public String getAnalyseResult(List<ProjectWorkLoad> list) {
		String analyseResult = "";

		if (list.size() > 1) {
			analyseResult += "所有员工的总工作量为" + list.get(list.size() - 1).getWorkLoad() + ",其中员工("
					+ list.get(0).getStaffNo() + ")" + list.get(0).getStaffName() + "表现最好";
		}
		return analyseResult;
	}

}
