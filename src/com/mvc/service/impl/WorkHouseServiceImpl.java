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
a
	// 部门员工做房用时统计
	@Override
	public ResponseEntity<byte[]> exportWorkHouse(Map<String, Object> map, String path) {
		// List<WorkHouse> list = workHouseDao.selectWorkHouse();

		return null;
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

	// 获取单个用户做房用时
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
		jsonObject.put("averWorkTime", Float.valueOf(averWorkTime));
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

}
