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
import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkLoadLevel;
import com.mvc.entityReport.WorkLoadMonth;
import com.mvc.repository.StaffInfoRepository;
import com.mvc.service.WorkLoadService;
import com.utils.CollectionUtil;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.WordHelper;

import net.sf.json.JSONObject;

/**
 * 工作量相关的service层接口实现
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Service("workLoadServiceImpl")
public class WorkLoadServiceImpl implements WorkLoadService {

	@Autowired
	WorkLoadDao workLoadDao;
	@Autowired
	StaffInfoRepository staffInfoRepository;

	// 获取所有员工工作量汇总列表
	@Override
	public List<WorkLoad> getWorkLoadSummaryList(String startTime, String endTime) {

		List<Object> listSource = workLoadDao.getWorkRecordSummary(startTime, endTime);

		Iterator<Object> it = listSource.iterator();
		List<WorkLoad> listGoal = objToWorkLoad(it);
		return listGoal;
	}

	// List<Object>类型转换成List<WorkLoad>
	private List<WorkLoad> objToWorkLoad(Iterator<Object> it) {
		Object[] obj = null;
		WorkLoad workLoad = null;
		List<WorkLoad> listGoal = new ArrayList<WorkLoad>();

		Float totalClean = (float) 0;
		Float totalCheckout = (float) 0;
		Float totalOvernight = (float) 0;
		Float totalActual = (float) 0;
		Float totalBeyond = (float) 0;
		Float totalRated = (float) 0;
		Integer totalWorkdays = 0;

		while (it.hasNext()) {
			obj = (Object[]) it.next();
			workLoad = new WorkLoad();

			workLoad.setStaffNo(obj[0].toString());
			workLoad.setStaffName(obj[1].toString());
			workLoad.setCleanRoom(Float.valueOf(obj[2].toString()));// 抹尘房总工作量
			workLoad.setCheckoutRoom(Float.valueOf(obj[3].toString()));// 离退房总工作量
			workLoad.setOvernightRoom(Float.valueOf(obj[4].toString()));// 过夜房总工作量
			workLoad.setActualLoad(Float.valueOf(obj[5].toString()));// 实际总工作量
			workLoad.setBeyondLoad(Float.valueOf(obj[6].toString()));// 超出总工作量
			workLoad.setRatedLoad(Float.valueOf(obj[7].toString()));// 额定总工作量
			workLoad.setWorkDays(obj[8].toString());// 实际工作天数

			totalClean += Float.valueOf(obj[2].toString());// 所有员工抹尘房总工作量合计
			totalCheckout += Float.valueOf(obj[3].toString());// 所有员工离退房总工作量合计
			totalOvernight += Float.valueOf(obj[4].toString());// 所有员工过夜房总工作量合计
			totalActual += Float.valueOf(obj[5].toString());// 所有员工实际总工作量合计
			totalBeyond += Float.valueOf(obj[6].toString());// 所有员工超出总工作量合计
			totalRated += Float.valueOf(obj[7].toString());// 所有员工额定总工作量合计
			totalWorkdays += Integer.valueOf(obj[8].toString());// 所有员工实际工作天数合计

			listGoal.add(workLoad);
		}
		// 按员工实际工作量进行排序并编号
		sortAndWrite(listGoal, "actualLoad", false, "rank");

		Iterator<WorkLoad> itGoal = listGoal.iterator();
		int i = 0;
		workLoad = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			workLoad = (WorkLoad) itGoal.next();
			workLoad.setOrderNum(String.valueOf(i));
		}
		// 列表最后一行追加合计
		workLoad = new WorkLoad();
		workLoad.setStaffName("合计：");
		workLoad.setCleanRoom(totalClean);// 抹尘房总工作量
		workLoad.setCheckoutRoom(totalCheckout);// 离退房总工作量
		workLoad.setOvernightRoom(totalOvernight);// 过夜房总工作量
		workLoad.setActualLoad(totalActual);// 实际总工作量
		workLoad.setBeyondLoad(totalBeyond);// 超出总工作量
		workLoad.setRatedLoad(totalRated);// 额定总工作量
		workLoad.setWorkDays(totalWorkdays.toString());// 实际工作天数
		listGoal.add(workLoad);

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
	private void sortAndWrite(List<WorkLoad> list, String filedName, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, filedName, ascFlag);
		CollectionUtil<WorkLoad> collectionUtil = new CollectionUtil<WorkLoad>();
		collectionUtil.writeSort(list, writeField);
	}

	// 导出所有员工工作量汇总表，word格式
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportWorkLoadSummaryWord(Map<String, Object> map) {
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String path = (String) map.get("path");
		String modelPath = (String) map.get("modelPath");
		String fileName = "客房部员工工作量汇总表.docx";

		WordHelper wh = new WordHelper();
		ResponseEntity<byte[]> byteArr = null;
		List<WorkLoad> workLoadList = null;
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析
		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据

		path = FileHelper.transPath(fileName, path);// 解析后的上传路径

		// 获取列表和文本信息
		workLoadList = getWorkLoadSummaryList(startDate, endDate);
		listMap.put("0", workLoadList);// 注意：key存放该list在word中表格的索引，value存放list
		contentMap.put("${startDate}", startDate);
		contentMap.put("${endDate}", endDate);
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

	// 导出所有员工工作量汇总表，excel格式
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportWorkLoadSummaryExcel(Map<String, Object> map) {
		ResponseEntity<byte[]> byteArr = null;
		List<WorkLoad> workLoadList = null;

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String path = (String) map.get("path");
		String fileName = "客房部员工工作量汇总表.xlsx";
		String title = "客房部员工工作量汇总表(" + startDate + "至" + endDate + ")";
		try {
			ExcelHelper<WorkLoad> ex = new ExcelHelper<WorkLoad>();
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			// 获取列表和文本信息
			workLoadList = getWorkLoadSummaryList(startDate, endDate);

			String[] header = { "序号", "员工姓名", "员工编号", "抹尘房", "过夜房", "离退房", "实际工作量", "超出工作量", "排名" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, (Collection) workLoadList, out, "yyyy-MM-dd", -1, 2);// -1表示没有合并单元格,2:隐藏了实体类最后两个字段内容

			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArr;
	}

	// 获取所有员工工作量饱和度分析列表
	@Override
	public String getWorkLoadLevelList(String startTime, String endTime) {
		JSONObject jsonObject = new JSONObject();
		String analyseResult = "";

		List<WorkLoad> workLoadList = getWorkLoadSummaryList(startTime, endTime);
		List<WorkLoadLevel> workLoadLevelList = toWorkLoadLevelList(workLoadList);
		analyseResult = getAnalyseResult(workLoadLevelList);// 获取饱和度分析结果
		jsonObject.put("WorkLoadLevelList", workLoadLevelList);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();
	}

	// List<WorkLoad>类型转换成List<WorkLoadLevel>
	private List<WorkLoadLevel> toWorkLoadLevelList(List<WorkLoad> it) {
		List<WorkLoadLevel> listGoal = new ArrayList<WorkLoadLevel>();
		WorkLoadLevel workLoadLevel = null;

		for (int i = 0; i < it.size(); i++) {
			workLoadLevel = new WorkLoadLevel();
			Float beyondTotal = it.get(i).getRatedLoad();
			Float beyondLoad = it.get(i).getBeyondLoad();
			Float beyondLevel = beyondLoad / beyondTotal * 100;

			workLoadLevel.setOrderNum(it.get(i).getOrderNum());
			workLoadLevel.setStaffNo(it.get(i).getStaffNo());
			workLoadLevel.setStaffName(it.get(i).getStaffName());
			workLoadLevel.setRatedLoad(it.get(i).getRatedLoad());
			workLoadLevel.setActualLoad(it.get(i).getActualLoad());// 实际总工作量
			workLoadLevel.setWorkDays(it.get(i).getWorkDays());// 实际工作总天数
			workLoadLevel.setBeyondLoad(beyondLoad);// 超出总工作量
			workLoadLevel.setOutOfRang(String.format("%.2f", beyondLevel) + "%");// 超出幅度
			workLoadLevel.setOutOfRang2(beyondLevel);

			listGoal.add(workLoadLevel);
		}
		return listGoal;
	}

	// 导出所有员工工作量饱和度分析表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportWorkLoadLevelWord(Map<String, Object> map) {

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String path = (String) map.get("path");
		String modelPath = (String) map.get("modelPath");
		String fileName = "客房部所有员工工作量饱和度分析表.docx";
		String analyseResult = "";
		WordHelper wh = new WordHelper();
		ResponseEntity<byte[]> byteArr = null;
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析
		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据

		path = FileHelper.transPath(fileName, path);// 解析后的上传路径

		// 获取列表和文本信息
		List<WorkLoad> workLoadList = getWorkLoadSummaryList(startDate, endDate);
		List<WorkLoadLevel> workLoadLevelList = toWorkLoadLevelList(workLoadList);
		analyseResult = getAnalyseResult(workLoadLevelList);// 获取饱和度分析结果

		listMap.put("0", workLoadLevelList);// 注意：key存放该list在word中表格的索引，value存放list
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

	// 饱和度分析
	private String getAnalyseResult(List<WorkLoadLevel> list) {
		String analyseResult = "额定工作量设置合理，员工实际工作量与额定工作量相差不大";
		Integer size = list.size();
		Float outOfRang = (float) 0;
		Integer beyondPeoNum = 0;
		Integer belowPeoNum = 0;
		Integer highBeyondNum = 0;
		Integer highBelowNum = 0;
		Float beyondLevel = (float) 0;
		Float highBeyondLevel = (float) 0;
		Float belowLevel = (float) 0;
		Float highBelowLevel = (float) 0;

		if (size > 0) {
			for (int i = 0; i < size; i++) {
				outOfRang = list.get(i).getOutOfRang2();
				if (outOfRang > 0) {
					beyondPeoNum++;
					if (outOfRang > 30) {
						highBeyondNum++;
					}
				} else if (outOfRang < 0) {
					belowPeoNum++;
					if (outOfRang < -30) {
						highBelowNum++;
					}
				}
			}
			beyondLevel = (float) (beyondPeoNum / size * 100);
			belowLevel = (float) (belowPeoNum / size * 100);
			highBeyondLevel = (float) (highBeyondNum / size * 100);
			highBelowLevel = (float) (highBelowNum / size * 100);
			if (beyondLevel >= 97) {
				analyseResult = "额定工作量设置略低于正常水平,所有员工实际工作量超出额定工作量，超出幅度在一定范围内";
			} else if (highBeyondLevel >= 97) {
				analyseResult = "额定工作量设置低于正常水平,所有员工实际工作量超出额定工作量，且超出幅度过高，建议提高额定工作量";
			}
			if (belowLevel <= -97) {
				analyseResult = "额定工作量设置略高于正常水平,所有员工实际工作量低于额定工作量，且低出幅度在一定范围内高";
			} else if (highBelowLevel <= -97) {
				analyseResult = "额定工作量设置高于正常水平,所有员工实际工作量低于额定工作量，且低出幅度过高，建议降低额定工作量";
			}
		}
		return analyseResult;
	}

	// 导出所有员工工作量饱和度分析表excel
	@Override
	public ResponseEntity<byte[]> exportWorkLoadLevelExcel(Map<String, Object> map) {
		ResponseEntity<byte[]> byteArr = null;

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String path = (String) map.get("path");
		String fileName = "客房部所有员工工作量饱和度分析表.xlsx";
		String title = "客房部所有员工工作量饱和度分析表(" + startDate + "至" + endDate + ")";
		try {
			ExcelHelper<WorkLoadLevel> ex = new ExcelHelper<WorkLoadLevel>();
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			// 获取列表和文本信息
			List<WorkLoad> workLoadList = getWorkLoadSummaryList(startDate, endDate);
			List<WorkLoadLevel> workLoadLevelList = toWorkLoadLevelList(workLoadList);

			String[] header = { "序号", "员工姓名", "员工编号", "实际工作天数", "额定工作量", "实际工作量", "超出工作量", "超出幅度" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, (Collection<WorkLoadLevel>) workLoadLevelList, out, "yyyy-MM-dd", -1, 1);// -1表示没有合并单元格，1:隐藏了实体类最后一个字段内容

			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArr;
	}

	// 获取员工工作量分析图所需数据
	@Override
	public String getWorkLoadAnalyseInfo(Map<String, String> map) {
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

		staffCount = workLoadDao.staffCount(startTime, endTime);
		if (staffCount != 0) {
			totalActualWorkLoad = workLoadDao.getTotalActualWorkLoad(startTime, endTime);
			listSorce = workLoadDao.getMonthWorkLoad(startTime, endTime, staffId);
			workLoadMonths = perMonth(listSorce, startMonth, monthNum);// 补齐缺省月份
			for (int i = 0; i < workLoadMonths.size(); i++) {
				averageData += workLoadMonths.get(i).getActualLoad();
			}
			averageData = averageData / monthNum;
			allAverageData = totalActualWorkLoad / monthNum / staffCount;
		}
		analyseResult = getAnalyseResult(averageData, allAverageData);

		jsonObject.put("allAverageData", allAverageData);
		jsonObject.put("averageData", averageData);
		jsonObject.put("workLoadMonths", workLoadMonths);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();
	}

	private String getAnalyseResult(Float averageData, Float allAverageData) {
		String analyseResult = "分析结果:  ";
		Float level = (averageData - allAverageData) / allAverageData;
		if (Math.abs(level) <= 0.05) {
			analyseResult += "良好(该员工平均工作量与全体员工平均工作量相差不大)";
		} else if (level > 0.05) {
			analyseResult += "优秀(该员工平均工作量高于全体员工平均工作量)";
		} else if (level < 0.05) {
			analyseResult += "不合格(该员工平均工作量远低于全体员工平均工作量)";
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
						actualLoad.setActualLoad(Float.valueOf(obj[1].toString()));
					}
					if (obj[2] != null)
						actualLoad.setRatedLoad(Float.valueOf(obj[2].toString()));
					listGoal.add(actualLoad);
				} else {
					actualLoad.setMonth(j);
					actualLoad.setActualLoad((float) 0);
					actualLoad.setRatedLoad((float) 0);
					listGoal.add(actualLoad);
					i--;
				}
			} else {
				actualLoad.setMonth(j);
				actualLoad.setActualLoad((float) 0);
				actualLoad.setRatedLoad((float) 0);
				listGoal.add(actualLoad);
			}
		}
		return listGoal;
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

	// 导出员工工作量分析图
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportWorkLoadAnalyse(Map<String, String> map) {

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
		String fileName = "客房部员工" + (staffNo + staffName) + date + "工作量分析结果.docx";
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

}
