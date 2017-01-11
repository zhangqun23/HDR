package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.WorkLoadDao;
import com.mvc.entityReport.WorkLoad;
import com.mvc.entityReport.WorkLoadLevel;
import com.mvc.service.WorkLoadService;
import com.utils.CollectionUtil;

/**
 * 工作量相关的service层接口实现
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Service("workLoadServiceImpl")
public class WorkLoadServiceImpl implements WorkLoadService {

	@Autowired
	WorkLoadDao workLoadDao;

	// 获取所有员工工作量汇总列表
	@Override
	public List<WorkLoad> getWorkLoadSummaryList(String startTime, String endTime) {

		List<Object> listSource = workLoadDao.getWorkRecordSummary(startTime, endTime);
		Iterator<Object> it = listSource.iterator();
		List<WorkLoad> listGoal = objToWorkLoad(it);

		return listGoal;
	}

	private List<WorkLoad> objToWorkLoad(Iterator<Object> it) {
		List<WorkLoad> listGoal = new ArrayList<WorkLoad>();
		Object[] obj = null;
		WorkLoad workLoad = null;

		while (it.hasNext()) {
			obj = (Object[]) it.next();
			workLoad = new WorkLoad();

			workLoad.setStaffNo(obj[0].toString());
			workLoad.setStaffName(obj[1].toString());
			workLoad.setCleanRoom(obj[2].toString());// 抹尘房总工作量
			workLoad.setCheckoutRoom(obj[3].toString());// 离退房总工作量
			workLoad.setOvernightRoom(obj[4].toString());// 过夜房总工作量

			workLoad.setActualLoad(obj[5].toString());// 实际总工作量
			workLoad.setBeyondLoad(obj[6].toString());// 超出总工作量

			listGoal.add(workLoad);
		}
		// 按员工实际工作量进行排序并编号
		sortAndWrite(listGoal, "actualLoad", true, "rank");

		Iterator<WorkLoad> itGoal = listGoal.iterator();
		int i = 0;
		workLoad = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			workLoad = (WorkLoad) itGoal.next();
			workLoad.setOrderNum(String.valueOf(i));
		}
		return listGoal;
	}

	// 获取所有员工工作量饱和度分析列表
	@Override
	public List<WorkLoadLevel> getWorkLoadLevelList(String startTime, String endTime) {
		List<WorkLoad> workLoadList = getWorkLoadSummaryList(startTime, endTime);
		List<WorkLoadLevel> listGoal = toWorkLoadList(workLoadList);
		return listGoal;
	}

	private List<WorkLoadLevel> toWorkLoadList(List<WorkLoad> it) {
		List<WorkLoadLevel> listGoal = new ArrayList<WorkLoadLevel>();
		WorkLoadLevel workLoadLevel = null;

		for (int i = 0; i < it.size(); i++) {
			// Float beyondLevel = null;
			String actualLoad = it.get(i).getActualLoad();
			String beyondLoad = it.get(i).getBeyondLoad();
			workLoadLevel = new WorkLoadLevel();

			workLoadLevel.setOrderNum(String.valueOf(i + 1));
			workLoadLevel.setStaffNo(it.get(i).getStaffNo());
			workLoadLevel.setStaffName(it.get(i).getStaffName());
			workLoadLevel.setActualLoad(actualLoad);
			workLoadLevel.setBeyondLoad(beyondLoad);

			listGoal.add(workLoadLevel);
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
	private void sortAndWrite(List<WorkLoad> list, String filedName, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, filedName, ascFlag);
		CollectionUtil<WorkLoad> collectionUtil = new CollectionUtil<WorkLoad>();
		collectionUtil.writeSort(list, writeField);
	}

}
