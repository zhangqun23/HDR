package com.mvc.service.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mvc.dao.CheckOrRobDao;
import com.mvc.entity.DepartmentInfo;
import com.mvc.entityReport.RobDetail;
import com.mvc.entityReport.RobEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.service.CheckOrRobService;
import com.utils.FileHelper;
import com.utils.Pager;
import com.utils.StringUtil;
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
		int no = 0;
		RobEfficiency robEfficiency = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			robEfficiency = new RobEfficiency();
			robEfficiency.setAuthorName(obj[1].toString());
			robEfficiency.setAuthorNo(obj[2].toString());
			robEfficiency.setSumTime(obj[3].toString());
			robEfficiency.setGivenTime(obj[4].toString());
			robEfficiency.setWorkCount(obj[5].toString());
			robEfficiency.setWorkEffeciencyAvg(obj[8].toString());

			String UsedTimeAvg = StringUtil.divide(obj[2].toString(), obj[4].toString());
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
		int no = 0;
		RobDetail robDetail = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			robDetail = new RobDetail();
			robDetail.setRoomNo(obj[0].toString());
			robDetail.setUsedTime(obj[1].toString());
			robDetail.setGivenTime(obj[2].toString());
			robDetail.setAuthor_name(obj[3].toString());
			robDetail.setIsBack(obj[4].toString());
			robDetail.setCheckUsedTime(obj[5].toString());
			robDetail.setCheckerName(obj[6].toString());
			robDetail.setOrderNum(no + "");

			robDetail.setWorkEffeciency(StringUtil.divide(obj[1].toString(), obj[2].toString()));// 效率

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
		return null;
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
						useTime[i] = Float.parseFloat(StringUtil.divide(obj[0].toString(), obj[2].toString()));
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

			wh.export2007Word(tempPath, listMap, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
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

			wh.export2007Word(tempPath, listMap, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

}
