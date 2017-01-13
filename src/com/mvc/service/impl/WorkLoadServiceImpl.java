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

	private List<WorkLoad> objToWorkLoad(Iterator<Object> it) {
		List<WorkLoad> listGoal = new ArrayList<WorkLoad>();
		Object[] obj = null;
		WorkLoad workLoad = null;

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
	public List<WorkLoadLevel> getWorkLoadLevelList(String startTime, String endTime) {
		List<WorkLoad> workLoadList = getWorkLoadSummaryList(startTime, endTime);
		List<WorkLoadLevel> listGoal = toWorkLoadList(workLoadList);
		return listGoal;
	}

	private List<WorkLoadLevel> toWorkLoadList(List<WorkLoad> it) {
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
			workLoadLevel.setActualLoad(it.get(i).getActualLoad());// 实际总工作量
			workLoadLevel.setWorkDays(it.get(i).getWorkDays());// 实际工作总天数
			workLoadLevel.setBeyondLoad(beyondLoad);// 超出总工作量
			workLoadLevel.setOutOfRang(String.format("%.2f", beyondLevel) + "%");// 超出幅度

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

		WordHelper wh = new WordHelper();
		ResponseEntity<byte[]> byteArr = null;
		List<WorkLoadLevel> workLoadLevelList = null;
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析
		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据

		path = FileHelper.transPath(fileName, path);// 解析后的上传路径

		// 获取列表和文本信息
		workLoadLevelList = getWorkLoadLevelList(startDate, endDate);
		listMap.put("0", workLoadLevelList);// 注意：key存放该list在word中表格的索引，value存放list
		contentMap.put("${startDate}", startDate);
		contentMap.put("${endDate}", endDate);
		contentMap.put("${analysisResult}", "所有员工工作量超出额定工作量，且超出幅度过高，因此建议将额定工作量调整至35");

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

	// 获取员工工作量分析图所需数据
	@Override
	public Map<String, Object> getWorkLoadAnalyseInfo(Map<String, String> map) {

		Float averageData = (float) 0;
		WorkLoadMonth actualLoad = null;
		Object[] obj = null;
		Map<String, Object> resultData = new HashMap<String, Object>();
		List<WorkLoadMonth> workLoadMonths = new ArrayList<WorkLoadMonth>();

		Map<String, Object> dateMap = getDate(map);
		String startTime = (String) dateMap.get("startTime");
		String endTime = (String) dateMap.get("endTime");
		Integer monthNum = (Integer) dateMap.get("monthNum");
		Integer staffId = Integer.valueOf(map.get("staffId"));

		List<Object> listSorce = workLoadDao.getMonthWorkLoad(startTime, endTime, staffId);
		Float totalActualWorkLoad = workLoadDao.getTotalActualWorkLoad(startTime, endTime);
		Integer staffCount = workLoadDao.staffCount(startTime, endTime);

		Iterator<Object> it = listSorce.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			actualLoad = new WorkLoadMonth();
			if (obj[0] != null)
				actualLoad.setMonth(Integer.valueOf(obj[0].toString()).toString());
			if (obj[1] != null) {
				averageData += Float.valueOf(obj[1].toString());
				actualLoad.setActualLoad(obj[1].toString());
			}
			if (obj[2] != null)
				actualLoad.setRatedLoad(obj[2].toString());
			workLoadMonths.add(actualLoad);
		}
		averageData = averageData / monthNum;
		Float allAverageData = totalActualWorkLoad / monthNum / staffCount;
		resultData.put("allAverageData", String.format("%.2f", allAverageData));
		resultData.put("averageData", String.format("%.2f", averageData));
		resultData.put("workLoadMonths", workLoadMonths);
		return resultData;
	}

	private Map<String, Object> getDate(Map<String, String> map) {

		String checkYear = map.get("checkYear");
		String quarter = map.get("quarter");
		Map<String, Object> dateMap = new HashMap<String, Object>();
		Integer monthNum = 12;
		String startTime = "";
		String endTime = "";
		String quarterName = "";

		switch (quarter) {
		case "0":
			startTime = checkYear + "-01-01 00:00:00";
			endTime = checkYear + "-12-31 23:59:59";
			monthNum = 12;
			quarterName = "全年";
			break;
		case "1":
			startTime = checkYear + "-01-01 00:00:00";
			endTime = checkYear + "-03-31 23:59:59";
			monthNum = 3;
			quarterName = "第一季度";
			break;
		case "2":
			startTime = checkYear + "-04-01 00:00:00";
			endTime = checkYear + "-06-30 23:59:59";
			monthNum = 3;
			quarterName = "第二季度";
			break;
		case "3":
			startTime = checkYear + "-07-01 00:00:00";
			endTime = checkYear + "-09-30 23:59:59";
			monthNum = 3;
			quarterName = "第三季度";
			break;
		case "4":
			startTime = checkYear + "-10-01 00:00:00";
			endTime = checkYear + "-12-31 23:59:59";
			monthNum = 3;
			quarterName = "第四季度";
			break;
		default:
			break;
		}
		dateMap.put("startTime", startTime);
		dateMap.put("endTime", endTime);
		dateMap.put("monthNum", monthNum);
		dateMap.put("quarterName", quarterName);
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
		String date = map.get("checkYear");
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
		picMap.put("width", 420);
		picMap.put("height", 280);
		picMap.put("type", "png");
		try {
			picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPath), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		contentMap.put("${staffName}", staffName);
		contentMap.put("${date}", date);
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
