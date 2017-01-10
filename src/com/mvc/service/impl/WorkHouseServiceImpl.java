package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvc.dao.WorkHouseDao;
import com.mvc.entityReport.WorkHouse;
import com.mvc.service.WorkHouseService;
import com.utils.Pager;

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
	public List<WorkHouse> selectWorkHouse(Map<String, Object> map, Pager pager) {
		List<Object> listSource = workHouseDao.selectWorkHouse(map, pager);
		Iterator<Object> it = listSource.iterator();
		List<WorkHouse> listGoal = objToWorkHouse(it);

		// List<Contract> listSum = contractDao.findContByParaNoBack(map,
		// pager);
		// Iterator<Contract> itSum = listSum.iterator();
		// NoBackContForm noBackContForm = noBackSum(itSum);
		// listGoal.add(noBackContForm);

		return listGoal;
	}

	// 部门员工做房用时统计
	@Override
	public ResponseEntity<byte[]> exportWorkHouse(Map<String, Object> map, String path) {
		// List<WorkHouse> list = workHouseDao.selectWorkHouse();

		return null;
	}

	@Override
	public Pager pagerTotalWorkHouse(Map<String, Object> map, Integer page) {

		return null;
	}

	private List<WorkHouse> objToWorkHouse(Iterator<Object> it) {
		List<WorkHouse> listGoal = new ArrayList<WorkHouse>();
		Object[] obj = null;
		WorkHouse workHouse = null;
		int i = 0;
		while (it.hasNext()) {
			i++;
			obj = (Object[]) it.next();
			workHouse = new WorkHouse();
			workHouse.setOrderNum(i);
			workHouse.setStaff_name(obj[0].toString());
			workHouse.setStaff_no(obj[1].toString());
			workHouse.setTotal_time_dust(obj[2].toString());
			workHouse.setTotal_time_night(obj[3].toString());
			workHouse.setTotal_time_leave(obj[4].toString());

		}

		return listGoal;
	}

}
