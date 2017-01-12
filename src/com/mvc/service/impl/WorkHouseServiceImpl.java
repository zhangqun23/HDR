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
import com.utils.CollectionUtil;
import com.utils.StringUtil;

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
	public List<WorkHouse> selectWorkHouse(Map<String, Object> map) {
		List<Object> listSource = workHouseDao.selectWorkHouse(map);
		Iterator<Object> it = listSource.iterator();
		List<WorkHouse> listGoal = objToWorkHouse(it);

		return listGoal;
	}

	// 部门员工做房用时统计
	@Override
	public ResponseEntity<byte[]> exportWorkHouse(Map<String, Object> map, String path) {
		// List<WorkHouse> list = workHouseDao.selectWorkHouse();

		return null;
	}

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

}
