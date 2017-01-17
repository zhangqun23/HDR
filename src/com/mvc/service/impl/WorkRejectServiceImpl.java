package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.mvc.dao.WorkRejectDao;
import com.mvc.entity.DepartmentInfo;
import com.mvc.entityReport.WorkReject;
import com.mvc.repository.DepartmentInfoRepository;
import com.mvc.service.WorkRejectService;

/**
 * 部门员工做房统计业务层实现
 * 
 * @author wangrui
 * @date 2016-12-08
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
		List<Object> listSource=workRejectDao.selectWorkRejectByLimits(map);
		Iterator<Object> it=listSource.iterator();
		List<WorkReject> listGoal=objToWorkReject(it);
		return listGoal;
	}
	
	private List<WorkReject> objToWorkReject(Iterator<Object> it){
		List<WorkReject> listGoal=new ArrayList<WorkReject>();
		return listGoal;
	}

}
