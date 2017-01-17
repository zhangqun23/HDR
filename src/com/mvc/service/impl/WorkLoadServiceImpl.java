package com.mvc.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvc.dao.WorkLoadDao;
import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkLoadLevel;
import com.mvc.entityReport.WorkLoadMonth;
import com.mvc.repository.StaffInfoRepository;
import com.mvc.service.WorkLoadService;
import com.utils.CollectionUtil;
import com.utils.FileHelper;
import com.utils.SvgPngConverter;
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
	public ResponseEntity<byte[]> exportWorkLoadSummaryList(Map<String, Object> map) {
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

			workLoadLevel.setOrderNum(String.valueOf(i + 1));
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
	public ResponseEntity<byte[]> exportWorkLoadLevelList(Map<String, Object> map) {

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
				analyseResult = "额定工作量设置略低于正常水平,所有员工工作量超出额定工作量，超出幅度在一定范围内";
			} else if (highBeyondLevel >= 97) {
				analyseResult = "额定工作量设置低于正常水平,所有员工工作量超出额定工作量，且超出幅度过高，建议提高额定工作量";
			}
			if (belowLevel <= -97) {
				analyseResult = "额定工作量设置略高于正常水平,所有员工工作量低于额定工作量，且低出幅度在一定范围内高";
			} else if (highBelowLevel <= -97) {
				analyseResult = "额定工作量设置高于正常水平,所有员工工作量低于额定工作量，且低出幅度过高，建议降低额定工作量";
			}
		}
		return analyseResult;
	}

	// 获取员工工作量分析图所需数据
	@Override
	public String getWorkLoadAnalyseInfo(Map<String, String> map) {
		JSONObject jsonObject = new JSONObject();
		Integer staffCount = 0;
		Float averageData = (float) 0;// 个人平均工作量
		Float allAverageData = (float) 0;// 全体员工的平均工作量
		Float totalActualWorkLoad = (float) 0;// 全体员工的实际总工作量
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
		jsonObject.put("allAverageData", allAverageData);
		jsonObject.put("averageData", averageData);
		jsonObject.put("workLoadMonths", workLoadMonths);
		return jsonObject.toString();
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
		String startTime = "";
		String endTime = "";
		String quarterName = "";
		Integer startMonth = 1;

		switch (quarter) {
		case "0":
			startTime = "-01-01 00:00:00";
			endTime = "-12-31 23:59:59";
			quarterName = "全年";
			break;
		case "1":
			startTime = "-01-01 00:00:00";
			endTime = "-03-31 23:59:59";
			monthNum = 3;
			quarterName = "第一季度";
			startMonth = 1;
			break;
		case "2":
			startTime = "-04-01 00:00:00";
			endTime = "-06-30 23:59:59";
			monthNum = 3;
			quarterName = "第二季度";
			startMonth = 4;
			break;
		case "3":
			startTime = "-07-01 00:00:00";
			endTime = "-09-30 23:59:59";
			monthNum = 3;
			quarterName = "第三季度";
			startMonth = 7;
			break;
		case "4":
			startTime = "-10-01 00:00:00";
			endTime = "-12-31 23:59:59";
			monthNum = 3;
			quarterName = "第四季度";
			startMonth = 10;
			break;
		default:
			break;
		}
		dateMap.put("startTime", checkYear + startTime);
		dateMap.put("endTime", checkYear + endTime);
		dateMap.put("monthNum", monthNum);
		dateMap.put("quarterName", quarterName);
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
		Integer staffId = Integer.valueOf(map.get("staffId"));
		String staffName = staffInfoRepository.findById(staffId).getStaff_name();
		String quarterName = (String) dateMap.get("quarterName");
		String fileName = "客房部员工" + staffName + quarterName + "工作量分析结果.docx";
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		String picName = "pic.png";
		String picPath = FileHelper.transPath(picName, picCataPath);// 解析后的上传路径

		try {
			SvgPngConverter.convertToPng(svg, picPath);// 图片svgCode转化为png格式，并保存到picPath
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TranscoderException e1) {
			e1.printStackTrace();
		}
		picMap.put("width", 960);
		picMap.put("height", 400);
		picMap.put("type", "png");
		try {
			picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPath), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		contentMap.put("${pic}", picMap);
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
