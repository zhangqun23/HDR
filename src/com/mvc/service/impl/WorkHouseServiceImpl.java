package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvc.dao.WorkHouseDao;
import com.mvc.entity.DepartmentInfo;
import com.mvc.entityReport.WorkEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.repository.DepartmentInfoRepository;
import com.mvc.service.WorkHouseService;
import com.utils.CollectionUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 部门员工做房统计业务层实现
 * 
 * @author wangrui
 * @date 2016-12-08
 */
@Service("workHouseServiceImpl")
public class WorkHouseServiceImpl implements WorkHouseService {

	@Autowired
	WorkHouseDao workHouseDao;
	@Autowired
	DepartmentInfoRepository departmentInfoRepository;

	// 查询员工做房
	@Override
	public List<WorkHouse> selectWorkHouse(Map<String, Object> map) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());

		List<Object> listSource = workHouseDao.selectWorkHouse(map);
		Iterator<Object> it = listSource.iterator();
		List<WorkHouse> listGoal = objToWorkHouse(it);

		return listGoal;
	}

	// 部门员工做房用时统计
	@Override
<<<<<<< HEAD
	public ResponseEntity<byte[]> exportWorkHouse(Map<String, Object> map, String path) {
		// List<WorkHouse> list = workHouseDao.selectWorkHouse();

		return null;
=======
	public ResponseEntity<byte[]> exportWorkHouse(Map<String, Object> map, String path, String tempPath) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());
		String sortName = (String) map.remove("sortName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工" + sortName + "做房时间统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = workHouseDao.selectWorkHouse(map);
			Iterator<Object> it = listSource.iterator();
			List<WorkHouse> listGoal = objToWorkHouse(it);

			WorkHouse sum = sumWorkHouse(listGoal);// 合计
			listGoal.add(sum);

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
>>>>>>> e2f7892bffe266d817c60cd54f15e418c6d68cf0
	}

	// 计算及排序
	private List<WorkHouse> objToWorkHouse(Iterator<Object> it) {
		List<WorkHouse> listGoal = new ArrayList<WorkHouse>();
		Object[] obj = null;
		WorkHouse workHouse = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			workHouse = new WorkHouse();
			workHouse.setStaff_name(obj[0].toString());
			workHouse.setStaff_no(obj[1].toString());
			workHouse.setNum_dust(obj[2].toString());// 抹尘房数量
			workHouse.setTotal_time_dust(obj[3].toString());// 抹尘房总用时
			workHouse.setNum_night(obj[4].toString());// 过夜房数量
			workHouse.setTotal_time_night(obj[5].toString());// 过夜房总用时
			workHouse.setNum_leave(obj[6].toString());// 离退房数量
			workHouse.setTotal_time_leave(obj[7].toString());// 离退房总用时

			workHouse.setAvg_time_dust(StringUtil.divide(obj[3].toString(), obj[2].toString()));// 抹尘房平均用时
			workHouse.setAvg_time_night(StringUtil.divide(obj[5].toString(), obj[4].toString()));// 过夜房平均用时
			workHouse.setAvg_time_leave(StringUtil.divide(obj[7].toString(), obj[6].toString()));// 离退房平均用时

			listGoal.add(workHouse);
		}
		// 分别对抹尘房、过夜房、离退房排序并编号
		sortAndWrite(listGoal, "avg_time_dust", false, "rank_dust");
		sortAndWrite(listGoal, "avg_time_night", false, "rank_night");
		sortAndWrite(listGoal, "avg_time_leave", false, "rank_leave");

		Iterator<WorkHouse> itGoal = listGoal.iterator();
		int i = 0;
		workHouse = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			workHouse = (WorkHouse) itGoal.next();
			workHouse.setOrderNum(String.valueOf(i));
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
	private void sortAndWrite(List<WorkHouse> list, String filedName, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, filedName, ascFlag);
		CollectionUtil<WorkHouse> collectionUtil = new CollectionUtil<WorkHouse>();
		collectionUtil.writeSort(list, writeField);
	}

	// 获取单个员工做房用时
	@Override
	public String selectUserWorkHouseByLimits(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();

		String year = (String) map.remove("checkYear");
		String quarter = (String) map.remove("quarter");
		String startMonth = null;
		String endMonth = null;
		if (StringUtil.strIsNotEmpty(year) && StringUtil.strIsNotEmpty(quarter)) {
			String startTime = StringUtil.quarterFirstDay(year, quarter);
			String endTime = StringUtil.quarterLastDay(year, quarter);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			startMonth = startTime.substring(startTime.indexOf("-") + 1, 7);// 截取月份
			endMonth = endTime.substring(endTime.indexOf("-") + 1, 7);
		}

		List<Object> monthList = workHouseDao.selectMonthWorkTime(map);// 获取员工每个月做房用时
		List<Integer> monthListStr = perMonth(monthList, startMonth, endMonth);
		jsonObject.put("list", monthListStr);

		String staffId = (String) map.remove("staffId");// 去掉staffId
		Object[] obj = null;
		String averWorkTime = null;
		Boolean flag = true;
		Long sumNum = (long) 0;
		Long sumTime = (long) 0;
		List<Object> averList = workHouseDao.selectAllAverWorkTime(map);// 获取全体员工平均做房用时
		Iterator<Object> it = averList.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sumNum += Long.valueOf(obj[1].toString());
			sumTime += Long.valueOf(obj[2].toString());
			if (flag) {
				if (obj[0].toString().trim().equals(staffId)) {
					averWorkTime = StringUtil.divide(obj[2].toString(), obj[1].toString());// 员工平均做房用时
					flag = false;
				}
			}
		}
		if (averWorkTime != null) {
			jsonObject.put("averWorkTime", Float.valueOf(averWorkTime));
		}
		String allAverWorkTime = StringUtil.divide(sumTime.toString(), sumNum.toString());
		jsonObject.put("allAverWorkTime", Float.valueOf(allAverWorkTime));// 全体员工平均做房用时

		return jsonObject.toString();
	}

	/**
	 * 去掉月份列，缺少的月份的列置为0
	 * 
	 * @param list
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	private List<Integer> perMonth(List<Object> list, String startMonth, String endMonth) {
		List<Integer> listGoal = new ArrayList<Integer>();
		if (StringUtil.strIsNotEmpty(startMonth) && StringUtil.strIsNotEmpty(endMonth)) {
			Integer len = Integer.valueOf(endMonth) - Integer.valueOf(startMonth) + 1;
			Integer size = list.size();

			Object[] obj = null;
			Integer month = null;
			for (int i = 0, j = 1; i < size || j <= len; i++, j++) {
				if (i < size) {
					obj = (Object[]) list.get(i);
					month = Integer.valueOf(obj[0].toString());
					if (month == j) {
						listGoal.add(Integer.valueOf(obj[1].toString()));
					} else {
						listGoal.add(0);
						i--;
					}
				} else {
					listGoal.add(0);
				}
			}
		}
		return listGoal;
	}

<<<<<<< HEAD
=======
	/**
	 * list求和
	 * 
	 * @param list
	 * @return
	 */
	private WorkHouse sumWorkHouse(List<WorkHouse> list) {
		WorkHouse sum = new WorkHouse();
		Iterator<WorkHouse> it = list.iterator();

		Long sum_num_dust = (long) 0;// 抹尘房
		Long sum_total_time_dust = (long) 0;
		Float sum_avg_time_dust = 0f;
		Long sum_num_night = (long) 0;// 过夜房
		Long sum_total_time_night = (long) 0;
		Float sum_avg_time_night = 0f;
		Long sum_num_leave = (long) 0;// 离退房
		Long sum_total_time_leave = (long) 0;
		Float sum_avg_time_leave = 0f;

		WorkHouse workHouse = null;
		while (it.hasNext()) {
			workHouse = it.next();
			sum_num_dust += Integer.valueOf(workHouse.getNum_dust());// 抹尘房
			sum_total_time_dust += Integer.valueOf(workHouse.getTotal_time_dust());
			sum_avg_time_dust += workHouse.getAvg_time_dust();
			sum_num_night += Integer.valueOf(workHouse.getNum_night());// 过夜房
			sum_total_time_night += Integer.valueOf(workHouse.getTotal_time_night());
			sum_avg_time_night += workHouse.getAvg_time_night();
			sum_num_leave += Integer.valueOf(workHouse.getNum_leave());// 离退房
			sum_total_time_leave += Integer.valueOf(workHouse.getTotal_time_leave());
			sum_avg_time_leave += workHouse.getAvg_time_leave();
		}
		sum.setOrderNum("合计");
		sum.setNum_dust(String.valueOf(sum_num_dust));// 抹尘房
		sum.setTotal_time_dust(String.valueOf(sum_total_time_dust));
		sum.setAvg_time_dust(Float.valueOf(sum_avg_time_dust));
		sum.setNum_night(String.valueOf(sum_num_night));// 过夜房
		sum.setTotal_time_night(String.valueOf(sum_total_time_night));
		sum.setAvg_time_night(Float.valueOf(sum_avg_time_night));
		sum.setNum_leave(String.valueOf(sum_num_leave));// 离退房
		sum.setTotal_time_leave(String.valueOf(sum_total_time_leave));
		sum.setAvg_time_leave(Float.valueOf(sum_avg_time_leave));

		return sum;
	}

	/**** 员工工作效率报表 ****/

	// 查询员工工作效率
	@Override
	public List<WorkEfficiency> selectWorkEffByLimits(Map<String, Object> map) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());

		List<Object> listSource = workHouseDao.selectWorkEffByLimits(map);
		Iterator<Object> it = listSource.iterator();
		List<WorkEfficiency> listGoal = objToWorkEff(it);

		return listGoal;
	}

	// 工作效率计算
	private List<WorkEfficiency> objToWorkEff(Iterator<Object> it) {
		List<WorkEfficiency> listGoal = new ArrayList<WorkEfficiency>();
		Object[] obj = null;
		WorkEfficiency workEff = null;
		int i = 0;
		while (it.hasNext()) {
			i++;
			obj = (Object[]) it.next();
			workEff = new WorkEfficiency();
			workEff.setOrderNum(String.valueOf(i));
			workEff.setStaff_name(obj[0].toString());
			workEff.setStaff_no(obj[1].toString());
			workEff.setWork_time(obj[2].toString());// 当班时间
			workEff.setHouse_time(obj[3].toString());// 做房时间
			workEff.setHouse_serv_time(obj[4].toString());// 做房+客服时间

			String house_eff = StringUtil.divide(obj[3].toString(), obj[2].toString());
			workEff.setHouse_eff(StringUtil.strFloatToPer(house_eff));// 做房效率(%)
			String house_serv_eff = StringUtil.divide(obj[4].toString(), obj[2].toString());
			workEff.setHouse_serv_eff(StringUtil.strFloatToPer(house_serv_eff));// 工作(做房+客服)效率(%)

			listGoal.add(workEff);
		}

		return listGoal;
	}

	// 获取单个员工工作效率
	@Override
	public String selectUserWorkEffByLimits(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();

		String year = (String) map.remove("checkYear");
		String quarter = (String) map.remove("quarter");
		String startMonth = null;
		String endMonth = null;
		if (StringUtil.strIsNotEmpty(year) && StringUtil.strIsNotEmpty(quarter)) {
			String startTime = StringUtil.quarterFirstDay(year, quarter);
			String endTime = StringUtil.quarterLastDay(year, quarter);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			startMonth = startTime.substring(startTime.indexOf("-") + 1, 7);// 截取月份
			endMonth = endTime.substring(endTime.indexOf("-") + 1, 7);
		}

		List<Object> monthList1 = workHouseDao.selectMonthHouseEff(map);// 获取员工每个月做房效率
		List<String> monthListStr1 = perMonthEff(monthList1, startMonth, endMonth);
		jsonObject.put("list1", monthListStr1);
		List<Object> monthList = workHouseDao.selectMonthWorkEff(map);// 获取员工每个月工作效率
		List<String> monthListStr = perMonthEff(monthList, startMonth, endMonth);
		jsonObject.put("list", monthListStr);

		String staffId = (String) map.remove("staffId");// 去掉staffId
		Object[] obj = null;
		String averHouseEff = null;
		Boolean flag = true;
		Long sumDutyTime = (long) 0;
		Long sumHouseTime = (long) 0;
		List<Object> averList = workHouseDao.selectAllAverHouseEff(map);// 获取全体员工平均做房效率
		Iterator<Object> it = averList.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sumDutyTime += Long.valueOf(obj[1].toString());
			sumHouseTime += Long.valueOf(obj[2].toString());
			if (flag) {
				if (obj[0].toString().trim().equals(staffId)) {
					averHouseEff = StringUtil.divide(obj[2].toString(), obj[1].toString());// 员工平均做房效率
					flag = false;
				}
			}
		}
		if (averHouseEff != null) {
			jsonObject.put("averWorkEfficiency1", Float.valueOf(averHouseEff));
		}
		String allAverHouseEff = StringUtil.divide(sumHouseTime.toString(), sumDutyTime.toString());
		jsonObject.put("allAverWorkEfficiency1", Float.valueOf(allAverHouseEff));// 全体员工平均做房效率

		obj = null;// 先清空对象，再重新赋值
		String averWorkEff = null;
		flag = true;
		sumDutyTime = (long) 0;
		Long sumWorkTime = (long) 0;
		averList = null;
		it = null;
		averList = workHouseDao.selectAllAverHouseEff(map);// 获取全体员工平均工作效率
		it = averList.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sumDutyTime += Long.valueOf(obj[1].toString());
			sumWorkTime += Long.valueOf(obj[2].toString());
			if (flag) {
				if (obj[0].toString().trim().equals(staffId)) {
					averWorkEff = StringUtil.divide(obj[2].toString(), obj[1].toString());// 员工平均工作效率
					flag = false;
				}
			}
		}
		if (averWorkEff != null) {
			jsonObject.put("averWorkEfficiency", Float.valueOf(averWorkEff));
		}
		String allAverWorkEff = StringUtil.divide(sumWorkTime.toString(), sumDutyTime.toString());
		jsonObject.put("allAverWorkEfficiency", Float.valueOf(allAverWorkEff));// 全体员工平均工作效率

		return jsonObject.toString();
	}

	/**
	 * 去掉月份列，缺少的月份的列置为0，同时计算效率
	 * 
	 * @param list
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	private List<String> perMonthEff(List<Object> list, String startMonth, String endMonth) {
		List<String> listGoal = new ArrayList<String>();
		if (StringUtil.strIsNotEmpty(startMonth) && StringUtil.strIsNotEmpty(endMonth)) {
			Integer len = Integer.valueOf(endMonth) - Integer.valueOf(startMonth) + 1;
			Integer size = list.size();

			Object[] obj = null;
			Integer month = null;
			for (int i = 0, j = 1; i < size || j <= len; i++, j++) {
				if (i < size) {
					obj = (Object[]) list.get(i);
					month = Integer.valueOf(obj[0].toString());
					if (month == j) {
						listGoal.add(StringUtil.divide(obj[2].toString(), obj[1].toString()));
					} else {
						listGoal.add("0");
						i--;
					}
				} else {
					listGoal.add("0");
				}
			}
		}
		return listGoal;
	}

>>>>>>> e2f7892bffe266d817c60cd54f15e418c6d68cf0
}
