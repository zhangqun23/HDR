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

import com.mvc.dao.EngineerRepairDao;
import com.mvc.entity.EngineerCaseSort;
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.ProjectRepair;
import com.mvc.service.EngineerRepairService;
import com.utils.CollectionUtil;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.WordHelper;

import net.sf.json.JSONObject;

/**
 * 工程维修项统计分析
 * 
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Service("engineerRepairServiceImpl")
public class EngineerRepairServiceImpl implements EngineerRepairService {
	@Autowired
	EngineerRepairDao engineerRepairDao;

	// 将json转换为Map
	@Override
	public Map<String, Object> JsonObjToMap(JSONObject jsonObject) {
		String starttime = null;// 开始时间
		String endtime = null;// 结束时间
		String repairtype = null;// 部门
		if (jsonObject.containsKey("start_time")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("start_time"))) {
				starttime = jsonObject.getString("start_time");
			}
		}
		if (jsonObject.containsKey("end_time")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("end_time"))) {
				endtime = jsonObject.getString("end_time");
			}
		}
		if (jsonObject.containsKey("repairtype")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("repairtype"))) {
				repairtype = jsonObject.getString("repairtype");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("start_time", starttime);
		map.put("end_time", endtime);
		map.put("repairtype", repairtype);
		return map;
	}

	// 查询工程维修项统计
	@Override
	public String findEngineerRepair(Map<String, Object> map) {
		List<Object> listSource = engineerRepairDao.findProjectRepairList(map);
		List<String> list = engineerRepairDao.getProjectRepairList(map);// 父名称可能是重复的

		List<ProjectRepair> listGoal = listsourceToListGoal(listSource, list);
		String analyseResult = listsourceToListGoalIcon(listSource, list, map);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", listGoal);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();
	}

	private List<ProjectRepair> listsourceToListGoal(List<Object> listSource, List<String> list) {
		Iterator<Object> it = listSource.iterator();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int j = 0; j < list.size(); j++) {
			map.put(list.get(j), 0);
			System.out.println(list.get(j));
		}

		List<ProjectRepair> listGoal = new ArrayList<ProjectRepair>();
		Object[] objects;
		ProjectRepair projectRepair;
		while (it.hasNext()) {
			objects = (Object[]) it.next();
			projectRepair = new ProjectRepair();
			projectRepair.setRepairParentType(objects[3].toString());// 父类型
			projectRepair.setRepairType(objects[1].toString());// 子类型
			projectRepair.setServiceLoad(Integer.valueOf(objects[4].toString()));// 数量

			if (map.containsKey(objects[3].toString())) {
				Integer m = map.get(objects[3].toString());
				m += Integer.valueOf(objects[4].toString());
				map.put(objects[3].toString(), m);
			}
			listGoal.add(projectRepair);
		}

		// 序号
		Iterator<ProjectRepair> itGoal = listGoal.iterator();
		projectRepair = null;
		int i = 0;
		while (itGoal.hasNext()) {
			i++;
			projectRepair = itGoal.next();
			projectRepair.setOrderNum(String.valueOf(i));
			projectRepair.setEngineerAmount(map.get(projectRepair.getRepairParentType()).toString());

		}

		return listGoal;
	}

	// 图标添加文字
	private String listsourceToListGoalIcon(List<Object> listSource, List<String> list, Map<String, Object> mAp) {
		String starttime = null;// 开始时间
		String endtime = null;// 结束时间
		if ((String) mAp.get("start_time") != null) {
			starttime = (String) mAp.get("start_time");
		}
		if ((String) mAp.get("end_time") != null) {
			endtime = (String) mAp.get("end_time");
		}
		Iterator<Object> it = listSource.iterator();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int j = 0; j < list.size(); j++) {
			map.put(list.get(j), 0);
			System.out.println(list.get(j));
		}

		List<ProjectRepair> listGoal = new ArrayList<ProjectRepair>();
		Object[] objects;
		ProjectRepair projectRepair;
		String amount = "0";

		while (it.hasNext()) {
			objects = (Object[]) it.next();
			projectRepair = new ProjectRepair();
			projectRepair.setRepairParentType(objects[3].toString());// 父类型
			projectRepair.setRepairType(objects[1].toString());// 子类型
			projectRepair.setServiceLoad(Integer.valueOf(objects[4].toString()));// 数量

			amount = StringUtil.add(amount, objects[4].toString());

			if (map.containsKey(objects[3].toString())) {
				int m = map.get(objects[3].toString());
				map.put(objects[3].toString(), m + Integer.valueOf(objects[4].toString()));
			}
			listGoal.add(projectRepair);
		}
		sortAndWriteW(listGoal, "serviceLoad", false);// 数量排名

		// 序号
		Iterator<ProjectRepair> itGoal = listGoal.iterator();
		projectRepair = null;
		int i = 0;
		while (itGoal.hasNext()) {
			i++;
			projectRepair = itGoal.next();
			projectRepair.setOrderNum(String.valueOf(i));
			projectRepair.setEngineerAmount(map.get(projectRepair.getRepairParentType()).toString());

		}

		Iterator<ProjectRepair> itGoalRange = listGoal.iterator();
		projectRepair = null;
		int w = 0;
		String analyseResult0;
		if (i <= 10) {
			analyseResult0 = "排名前" + i + "的维修项包括：";
			while (itGoalRange.hasNext()) {
				w++;
				projectRepair = itGoalRange.next();
				if (w < i) {
					analyseResult0 += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")，";
				} else {
					analyseResult0 += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")。";
				}
			}
		} else {
			analyseResult0 = "排名前十的维修项包括：";
			while (itGoalRange.hasNext() && w < 10) {
				w++;
				projectRepair = itGoalRange.next();
				analyseResult0 += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")，";
				if (w == 9) {
					analyseResult0 += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")。";
				}

			}
		}

		// 字符串
		String analyseResult = "从" + starttime + "至" + endtime + "共产生报修项" + amount + "项。其中：";
		List<String> list1 = engineerRepairDao.getProjectRepairListNo(mAp);// 父名称可能是(去重复)
		for (int j = 0; j < list1.size(); j++) {
			analyseResult += list1.get(j) + "(" + map.get(list1.get(j) + "项" + ")");
		}
		analyseResult += analyseResult0;
		return analyseResult;

	}

	/**
	 * 排序
	 * 
	 * @param list
	 * @param filedName
	 *            按指定字段排序
	 * @param ascFlag
	 *            true升序,false降序
	 */
	private void sortAndWriteW(List<ProjectRepair> list, String filedName, boolean ascFlag) {
		CollectionUtil.sort(list, filedName, ascFlag);
	}

	// 导出工程维修项统计word
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportProRepairWord(Map<String, Object> map, String path, String modelPath) {
		ResponseEntity<byte[]> byteww = null;
		String starttime = (String) map.get("start_time");// 开始时间
		String endtime = (String) map.get("end_time");// 结束时间

		List<ProjectRepair> listGoal = null;
		String analyseResult = null;
		WordHelper wh = new WordHelper();

		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析

		if (StringUtil.strIsNotEmpty(starttime) && StringUtil.strIsNotEmpty(endtime)) {
			List<Object> listSource = engineerRepairDao.findProjectRepairList(map);
			List<String> list = engineerRepairDao.getProjectRepairList(map);// 父名称可能是重复的
			listGoal = listsourceToListGoal(listSource, list);

			analyseResult = listsourceToListGoalIcon(listSource, list, map);
		}
		if (listGoal != null) {
			String fileName = starttime + "至" + endtime + "工程部维修项统计.docx";
			String path0 = FileHelper.transPath(fileName, path);// 解析后的上传路径

			// 获取列表和文本信息
			listMap.put("0", listGoal);
			contentMap.put("${starttime}", starttime);
			contentMap.put("${endtime}", endtime);
			contentMap.put("${analyseResult}", analyseResult);

			try {
				OutputStream out = new FileOutputStream(path0);// 保存路径
				wh.export2007Word(modelPath, listMap, contentMap, 1, out);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byteww = FileHelper.downloadFile(fileName, path0);

		}

		return byteww;

	}

	// 导出工程维修项统计excel
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportProRepairExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteww = null;
		try{
			ExcelHelper<ProjectRepair> ex = new ExcelHelper<ProjectRepair>();
			String starttime = (String) map.get("start_time");// 开始时间
			String endtime = (String) map.get("end_time");// 结束时间
			String fileName = starttime + "至" + endtime + "工程部维修项统计.xlsx";
			
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);	
			
			List<Object> listSource = engineerRepairDao.findProjectRepairList(map);
			List<String> list = engineerRepairDao.getProjectRepairList(map);// 父名称可能是重复的
			List<ProjectRepair> listGoal = listsourceToListGoal(listSource, list);
			
			
			String title = "工程部维修项统计("+ starttime + "至" + endtime +")";
			
			String[] header = { "序号", "大区域", "小区域", "数量", "总计"};
			ex.export2007Excel(title, header, (Collection) listGoal, out, "yyyy-MM-dd", -1,1,4,0,1);// -1表示没有合并单元格,2:隐藏了实体类最后两个字段内容
			out.close();
			byteww = FileHelper.downloadFile(fileName, path);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteww;
	}

	/*
	 * ***********************************王慧敏报表图标*******************************
	 */
	// 工程报修图标
	@Override
	public String getProjectRepairIcon(Map<String, String> map) {

		Map<String, Object> dateMap = getDate(map);
		String repairType = map.get("repairType");

		dateMap.put("repairType", repairType);
		List<Object> listSource = engineerRepairDao.getProjectRepairIcon(dateMap);
		String stww = listsourceToListGoalIcon(listSource);
		return stww;

	}

	private String listsourceToListGoalIcon(List<Object> listSource) {
		Iterator<Object> it = listSource.iterator();

		List<ProjectRepair> listGoal = new ArrayList<ProjectRepair>();
		Object[] objects;
		ProjectRepair projectRepair;
		String parentname = null;
		String analyseResult;
		while (it.hasNext()) {
			objects = (Object[]) it.next();
			projectRepair = new ProjectRepair();
			projectRepair.setRepairType(objects[1].toString());// 子类型
			projectRepair.setServiceLoad(Integer.valueOf(objects[4].toString()));// 数量
			parentname = objects[3].toString();

			listGoal.add(projectRepair);
		}
		// 序号
		Iterator<ProjectRepair> itGoal = listGoal.iterator();
		projectRepair = null;
		int i = 0;
		while (itGoal.hasNext()) {
			i++;
			projectRepair = itGoal.next();
			projectRepair.setOrderNum(String.valueOf(i));

		}

		sortAndWriteW(listGoal, "serviceLoad", false);// 数量排名		

		Iterator<ProjectRepair> itGoalRange = listGoal.iterator();
		projectRepair = null;
		int w = 0;
		analyseResult = parentname + "报修项排名前三的是：";
		if (i <= 3) {
			while (itGoalRange.hasNext()) {
				w++;
				projectRepair = itGoalRange.next();
				if (w < i) {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")，";
				} else {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")。";
				}
			}
		} else {
			while (itGoalRange.hasNext() && w < 3) {
				w++;
				projectRepair = itGoalRange.next();
				if (w < i && w < 3) {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")，";
				} else if (w == 3) {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")。";
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", listGoal);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();

	}

	// 工程报修图标添加文字
	public String getProjectRepairIconn(Map<String, String> map) {
		Map<String, Object> dateMap = getDate(map);
		String repairType = map.get("repairType");

		dateMap.put("repairType", repairType);
		List<Object> listSource = engineerRepairDao.getProjectRepairIcon(dateMap);
		String stww = listsourceToListGoalIconn(listSource);
		return stww;

	}

	private String listsourceToListGoalIconn(List<Object> listSource) {
		Iterator<Object> it = listSource.iterator();

		List<ProjectRepair> listGoal = new ArrayList<ProjectRepair>();
		Object[] objects;
		ProjectRepair projectRepair;
		String parentname = null;
		String analyseResult;
		while (it.hasNext()) {
			objects = (Object[]) it.next();
			projectRepair = new ProjectRepair();
			projectRepair.setRepairType(objects[1].toString());// 子类型
			projectRepair.setServiceLoad(Integer.valueOf(objects[4].toString()));// 数量
			parentname = objects[3].toString();

			listGoal.add(projectRepair);
		}
		// 序号
		Iterator<ProjectRepair> itGoal = listGoal.iterator();
		projectRepair = null;
		int i = 0;
		while (itGoal.hasNext()) {
			i++;
			projectRepair = itGoal.next();
			projectRepair.setOrderNum(String.valueOf(i));

		}

		sortAndWriteW(listGoal, "serviceLoad", false);// 数量排名
		Iterator<ProjectRepair> itGoalRange = listGoal.iterator();
		projectRepair = null;
		int w = 0;
		analyseResult = parentname + "报修项排名前三的是：";
		if (i <= 3) {
			while (itGoalRange.hasNext()) {
				w++;
				projectRepair = itGoalRange.next();
				if (w < i) {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")，";
				} else {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")。";
				}
			}
		} else {
			while (itGoalRange.hasNext() && w < 3) {
				w++;
				projectRepair = itGoalRange.next();
				if (w < i && w < 3) {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")，";
				} else if (w == 3) {
					analyseResult += projectRepair.getRepairType() + "(" + projectRepair.getServiceLoad() + ")。";
				}
			}
		}

		return analyseResult;

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
		dateMap.put("start_time", startTime);
		dateMap.put("end_time", endTime);
		dateMap.put("monthNum", monthNum);
		dateMap.put("quarterName", checkYear + quarterName);
		dateMap.put("startMonth", startMonth);
		return dateMap;
	}

	// 导出工程报修图标
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportProjectIconWord(Map<String, String> map, String path, String picPath,
			String modelPath) {
		ResponseEntity<byte[]> byteww = null;

		String photo = (String) map.get("photo");// 图片
		String checkYear = map.get("checkYear");
		String quarter = map.get("quarter");
		String quarterName = "";
		switch (quarter) {
		case "0":
			quarterName = "全年";
			break;
		case "1":
			quarterName = "年第一季度";
			break;
		case "2":
			quarterName = "年第二季度";
			break;
		case "3":
			quarterName = "年第三季度";
			break;
		case "4":
			quarterName = "年第四季度";
			break;
		default:
			break;
		}

		String analyseResult = getProjectRepairIconn(map);// 图片文字
		// 添加图片
		String picName1 = null;
		if (StringUtil.strIsNotEmpty(photo)) {
			picName1 = "pic1.png";
			picPath = FileHelper.transPath(picName1, picPath);
		}
		Map<String, Object> picMap = new HashMap<String, Object>();
		picMap = PictureUtil.getPicMap(picPath, photo);
		WordHelper wh = new WordHelper();

		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据

		String fileName = checkYear + quarterName + "工程维修项统计分析.docx";
		String path0 = FileHelper.transPath(fileName, path);// 解析后的上传路径

		// 文本信息
		contentMap.put("${checkYear}", checkYear);
		contentMap.put("${quarterName}", quarterName);
		contentMap.put("${photo}", picMap);
		contentMap.put("${analyseResult}", analyseResult);

		try {
			OutputStream out = new FileOutputStream(path0);// 保存路径
			wh.export2007Word(modelPath, null, contentMap, 1, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteww = FileHelper.downloadFile(fileName, path0);

		return byteww;
	}

	/*
	 * ***********************************王慧敏报表服务类型*****************************
	 * **
	 */
	// 工程维修项统计服务类型
	@Override
	public List<EngineerCaseSort> findEngineerRepairType() {
		List<EngineerCaseSort> list = engineerRepairDao.getEngineerRepairTypeList();
		return list;
	}

}
