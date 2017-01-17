package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.mvc.dao.WorkRejectDao;
import com.mvc.entity.DepartmentInfo;
import com.mvc.entityReport.WorkReject;
import com.mvc.repository.DepartmentInfoRepository;
import com.mvc.service.WorkRejectService;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 部门员工做房驳回统计业务层实现
 * 
 * @author zq
 * @date 2017-1-17
 */
@Service("workRejectServiceImpl")
public class WorkRejectServiceImpl implements WorkRejectService {

	@Autowired
	WorkRejectDao workRejectDao;
	@Autowired
	DepartmentInfoRepository DepartmentInfoRepository;

	@Override
	public List<WorkReject> selectWorkRejectByLimits(Map<String, Object> map) {
		DepartmentInfo departmentInfo = DepartmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());
		List<Object> listSource = workRejectDao.selectWorkRejectByLimits(map);
		Iterator<Object> it = listSource.iterator();
		List<WorkReject> listGoal = objToWorkReject(it);
		return listGoal;
	}

	// zq员工驳回统计
	private List<WorkReject> objToWorkReject(Iterator<Object> it) {
		List<WorkReject> listGoal = new ArrayList<WorkReject>();
		Object[] obj = null;
		WorkReject workReject = null;
		int i = 0;
		while (it.hasNext()) {
			i++;
			obj = (Object[]) it.next();
			workReject = new WorkReject();
			workReject.setOrderNum(String.valueOf(i));
			workReject.setStaff_name(obj[0].toString());
			workReject.setStaff_no(obj[1].toString());
			workReject.setNum_dust(obj[2].toString());
			workReject.setReject_dust(obj[3].toString());
			workReject.setNum_night(obj[4].toString());
			workReject.setReject_night(obj[5].toString());
			workReject.setNum_leave(obj[6].toString());
			workReject.setReject_leave(obj[7].toString());
			String dust_eff = StringUtil.divide(obj[3].toString(), obj[2].toString());
			workReject.setReject_dust_eff(StringUtil.strFloatToPer(dust_eff));
			String night_eff = StringUtil.divide(obj[5].toString(), obj[4].toString());
			workReject.setReject_night_eff(StringUtil.strFloatToPer(night_eff));
			String leave_eff = StringUtil.divide(obj[7].toString(), obj[6].toString());
			workReject.setReject_leave_eff(StringUtil.strFloatToPer(leave_eff));
			listGoal.add(workReject);
		}
		return listGoal;
	}

	// zq获取员工驳回率分析
	@Override
	public String selectWorkRejectAnalyseByLimits(Map<String, Object> map) {
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
		List<Object> monthList = workRejectDao.selectMonthWorkReject(map);// 获取员工每个月做房驳回效率
		List<String> monthListStr = perMonthEff(monthList, startMonth, endMonth);
		jsonObject.put("list", monthListStr);

		String staffId = (String) map.get("staffId");
		Object[] obj = null;
		String averRejectEff = null;
		Boolean flag = true;
		Long sumWorkTime = (long) 0;
		Long sumRejectTime = (long) 0;
		List<Object> averList = workRejectDao.selectAllAverRejectEff(map);// 获取全体员工平均做房驳回效率
		Iterator<Object> it = averList.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sumWorkTime += Long.valueOf(obj[1].toString());
			sumRejectTime += Long.valueOf(obj[2].toString());
			if (flag) {
				if (obj[0].toString().trim().equals(staffId)) {
					averRejectEff = StringUtil.divide(obj[2].toString(), obj[1].toString());// 员工平均做房驳回效率
					flag = false;
				}
			}
		}
		if (averRejectEff != null) {
			jsonObject.put("averRejectEff", Float.valueOf(averRejectEff));
		}
		String allAverRejectEff = StringUtil.divide(sumRejectTime.toString(), sumWorkTime.toString());
		jsonObject.put("allAverRejectEff", Float.valueOf(allAverRejectEff));// 全体员工平均做房驳回效率
		// 获取驳回原因统计扇形图
		List<Object> reasonList = workRejectDao.selectReasonsByLimits(map);
		Iterator<Object> iter = reasonList.iterator();
		int reasonArr[] = new int[] { 0, 0, 0, 0, 0, 0 };

		while (iter.hasNext()) {
			obj = (Object[]) iter.next();
			JSONObject reasonJson = JSONObject.fromObject(obj);
			if (reasonJson.containsKey("consumables")) {
				reasonArr[0] += 1;
			}
			if (reasonJson.containsKey("barProblems")) {
				reasonArr[1] += 1;
			}
			if (reasonJson.containsKey("toiletProblems")) {
				reasonArr[2] += 1;
			}
			if (reasonJson.containsKey("towels")) {
				reasonArr[3] += 1;
			}
			if (reasonJson.containsKey("roomProblems")) {
				reasonArr[4] += 1;
			}
			if (reasonJson.containsKey("otherProblems")) {
				reasonArr[5] += 1;
			}

		}
		jsonObject.put("reasonList", reasonArr);// 全体员工平均做房驳回效率
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

}
