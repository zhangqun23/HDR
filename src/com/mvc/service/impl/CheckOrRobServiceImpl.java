package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.CheckOrRobDao;
import com.mvc.entityReport.RobEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.service.CheckOrRobService;
import com.utils.StringUtil;

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

}
