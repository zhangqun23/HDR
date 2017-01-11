package com.mvc.service.impl;

import java.util.ArrayList;
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

	// 获取所有员工工作量汇总列表信息
	@Override
	public List<WorkLoad> getWorkLoadSummaryList(String startTime, String endTime) {
		WorkLoad workLoad = null;
		List<Object> objectList = workLoadDao.getRoomNumByPrame(startTime, endTime);

		List<WorkLoad> workLoadList = new ArrayList<WorkLoad>();
		for (int i = 0; i < objectList.size(); i++) {
			workLoad = new WorkLoad();
			Integer orderNum = i + 1;
			workLoad.setOrderNum(orderNum.toString());

			Object[] object = (Object[]) objectList.get(i);
			workLoad.setStaffName(object[0].toString());
			workLoad.setStaffNo(object[1].toString());
			if (object[2] != null && !object[2].equals(0.0))
				workLoad.setCleanRoom(object[2].toString());
			if (object[3] != null && !object[3].equals(0.0))
				workLoad.setCheckoutRoom(object[3].toString());
			if (object[4] != null && !object[4].equals(0.0))
				workLoad.setOvernightRoom(object[4].toString());
			workLoadList.add(workLoad);
		}

		// 分别对抹尘房、过夜房、离退房排序并编号
		sortAndWrite(workLoadList, "staffNo", false, "rank");

		return workLoadList;
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

	// 获取所有员工工作量饱和度分析列表
	@Override
	public List<WorkLoadLevel> getWorkLoadLevelList(String startTime, String endTime) {
		return null;
	}

}
