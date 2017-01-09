package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.WorkHouseDao;
import com.mvc.entity.CaseInfo;
import com.mvc.entity.StaffInfo;
import com.mvc.entityReport.WorkHouse;
import com.mvc.service.WorkHouseService;

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

	// 查询员工做房
	@Override
	public List<WorkHouse> selectWorkHouse() {
		List<CaseInfo> list = workHouseDao.selectWorkHouse();
//		CaseInfo caseInfo = list.get(0);
//		StaffInfo staffInfo = caseInfo.getAuthor();
//		if (staffInfo != null) {
//			String name = staffInfo.getStaff_name();
//			System.out.println(name);
//		}
		return null;
	}

}
