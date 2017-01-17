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
		List<Object> monthList=workRejectDao.selectMonthWorkReject(map);//获取员工每个月做房用时
		
		return null;
	}

}
