package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.HotelCustomerDao;
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.HouseCustomerServiceLoad;
import com.mvc.entityReport.HouseCustomerServiceType;
import com.mvc.service.HotelCustomerService;
import com.utils.CollectionUtil;
import com.utils.DoubleFloatUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 酒店对客服务信息统计
 * 
 * @author wanghuimin
 * @date 2017年1月10日
 */
@Service("hotelCustomerServiceImpl")
public class HotelCustomerServiceImpl implements HotelCustomerService {
	@Autowired
	HotelCustomerDao hotelCustomerDao;

	// 将json转换为Map
	@Override
	public Map<String, Object> JsonObjToMap(JSONObject jsonObject) {
		String starttime = null;// 开始时间
		String endtime = null;// 结束时间
		String depart = null;// 部门
		if (jsonObject.containsKey("start_time")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("start_time"))) {
				starttime = jsonObject.getString("start_time");
			}
		}
		if (jsonObject.containsKey("end_time")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("end_time"))) {
				starttime = jsonObject.getString("end_time");
			}
		}
		if (jsonObject.containsKey("depart")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("depart"))) {
				depart = jsonObject.getString("depart");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("start_time", starttime);
		map.put("end_time", endtime);
		map.put("depart", depart);
		return map;
	}

	// 查询酒店对客服务信息统计
	@Override
	public List<HoCustomerService> findHotelService(Map<String, Object> map) {
		List<Object> listSource = hotelCustomerDao.findHotelService(map);
		Iterator<Object> it = listSource.iterator();
		List<HoCustomerService> listGoal = listsourceToListGoal(it);
		return listGoal;
	}

	private List<HoCustomerService> listsourceToListGoal(Iterator<Object> it) {
		List<HoCustomerService> listGoal = new ArrayList<HoCustomerService>();
		String serviceLoad = "0.0";// 总计服务数量
		String timeOutService = "0.0";// 总计超时服务
		String timeOutRate = "0.0";// 总计超时率
		String timeOutRate0 = null;
		Object[] obj;
		HoCustomerService hoCustomerService;
		int i = 0;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			i++;
			hoCustomerService = new HoCustomerService();
			hoCustomerService.setOrderNum(i + "");
			hoCustomerService.setDepartment(obj[0].toString());
			hoCustomerService.setServiceLoad(obj[1].toString());
			hoCustomerService.setTimeOutService(obj[2].toString());
			hoCustomerService.setSumWorkTime(obj[3].toString());

			Integer integer = Integer.valueOf(obj[2].toString()) / Integer.valueOf(obj[1].toString()) * 100;// 超时率
			String overtime = integer.toString() + "%";
			hoCustomerService.setTimeOutRate(overtime);

			Integer integer0 = Integer.valueOf(obj[3].toString()) / Integer.valueOf(obj[1].toString());// 平均用时
			hoCustomerService.setTimeOutRate(integer0.toString());
			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[1].toString());// 总计服务数量
			timeOutService = DoubleFloatUtil.add(timeOutService, obj[2].toString());// 总计超时

			timeOutRate = DoubleFloatUtil.add(timeOutRate, integer.toString());// 总计超时
			timeOutRate0 = timeOutRate + "%";

			listGoal.add(hoCustomerService);
		}
		sortAndWrite(listGoal, "serviceLoad", true, "serviceLoad_rank");// 总量排名
		sortAndWrite(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名

		hoCustomerService = new HoCustomerService();
		hoCustomerService.setOrderNum("合计");
		hoCustomerService.setServiceLoad(serviceLoad);
		hoCustomerService.setTimeOutService(timeOutService);
		hoCustomerService.setTimeOutRate(timeOutRate0);

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
	private void sortAndWrite(List<HoCustomerService> list, String filedName, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, filedName, ascFlag);
		CollectionUtil<HoCustomerService> collectionUtil = new CollectionUtil<HoCustomerService>();
		collectionUtil.writeSort(list, writeField);
	}
	/*
	 * ***********************************王慧敏报表1*******************************
	 */

	// 查询部门对客服务工作量统计
	@Override
	public List<HouseCustomerServiceLoad> findDepartmentLoad(Map<String, Object> map) {
		List<Object> listSource = hotelCustomerDao.findDepartmentLoad(map);
		Iterator<Object> it = listSource.iterator();
		List<HouseCustomerServiceLoad> listGoal = listloadToListGoal(it);
		return listGoal;

	}

	private List<HouseCustomerServiceLoad> listloadToListGoal(Iterator<Object> it) {
		List<HouseCustomerServiceLoad> listGoal = new ArrayList<HouseCustomerServiceLoad>();
		String serviceLoad = "0.0";// 总计服务数量
		String timeOutService = "0.0";// 总计超时
		String timeOutRate = "0.0";// 总计超时率
		String timeOutRate0 = null;

		Object[] obj;
		HouseCustomerServiceLoad houseCustomerServiceLoad;
		int i = 0;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			i++;
			houseCustomerServiceLoad = new HouseCustomerServiceLoad();
			houseCustomerServiceLoad.setOrderNum(i + "");
			houseCustomerServiceLoad.setStaff_name(obj[0].toString());
			houseCustomerServiceLoad.setStaff_no(obj[1].toString());
			houseCustomerServiceLoad.setServiceLoad(obj[2].toString());
			houseCustomerServiceLoad.setTimeOutService(obj[3].toString());
			houseCustomerServiceLoad.setSumWorkTime(obj[4].toString());
			Integer integer0 = Integer.valueOf(obj[4].toString()) / Integer.valueOf(obj[2].toString());// 平均用时
			houseCustomerServiceLoad.setAverageWorkTime(integer0.toString());

			Integer integer = Integer.valueOf(obj[3].toString()) / Integer.valueOf(obj[2].toString()) * 100;// 超时率
			String overtime = integer.toString() + "%";
			houseCustomerServiceLoad.setTimeOutRate(overtime);

			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[2].toString());// 总计服务数量

			timeOutService = DoubleFloatUtil.add(timeOutService, obj[3].toString());// 总计超时
			timeOutRate = DoubleFloatUtil.add(timeOutRate, integer.toString());// 总计超时率
			timeOutRate0 = timeOutRate + "%";

			listGoal.add(houseCustomerServiceLoad);
		}
		sortAndWrite0(listGoal, "serviceLoad", true, "serviceLoad_rank");// 总量排名
		sortAndWrite0(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名

		houseCustomerServiceLoad = new HouseCustomerServiceLoad();
		houseCustomerServiceLoad.setOrderNum("合计");
		houseCustomerServiceLoad.setServiceLoad(serviceLoad);// 总计服务数量
		houseCustomerServiceLoad.setTimeOutService(timeOutService);// 总计超时
		houseCustomerServiceLoad.setTimeOutRate(timeOutRate0);// 总计超时率

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
	private void sortAndWrite0(List<HouseCustomerServiceLoad> list, String filedName, boolean ascFlag,
			String writeField) {
		CollectionUtil.sort(list, filedName, ascFlag);
		CollectionUtil<HouseCustomerServiceLoad> collectionUtil = new CollectionUtil<HouseCustomerServiceLoad>();
		collectionUtil.writeSort(list, writeField);
	}
	/*
	 * ***********************************王慧敏报表2*******************************
	 */

	@Override
	public List<HouseCustomerServiceType> findRoomType(Map<String, Object> map) {
		List<Object> listSource = hotelCustomerDao.findRoomType(map);
		Iterator<Object> it = listSource.iterator();
		List<HouseCustomerServiceType> listGoal = listtypeToListGoal(it);
		return listGoal;
	}

	private List<HouseCustomerServiceType> listtypeToListGoal(Iterator<Object> it) {
		List<HouseCustomerServiceType> listGoal = new ArrayList<HouseCustomerServiceType>();
		String serviceLoad = "0.0";// 总计服务数量
		String timeOutService = "0.0";// 总计超时
		String timeOutRate = "0.0";// 总计超时率
		String timeOutRate0 = null;

		Object[] obj;
		HouseCustomerServiceType houseCustomerServiceType;
		int i = 0;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			i++;
			houseCustomerServiceType = new HouseCustomerServiceType();
			houseCustomerServiceType.setOrderNum(i + "");
			houseCustomerServiceType.setServiceType(obj[0].toString());
			houseCustomerServiceType.setServiceLoad(obj[1].toString());
			houseCustomerServiceType.setGiveTime(obj[2].toString());
			houseCustomerServiceType.setTimeOutServiceLoad(obj[4].toString());
			Integer integer0 = Integer.valueOf(obj[3].toString()) / Integer.valueOf(obj[1].toString());// 平均用时
			houseCustomerServiceType.setAverageWorkTime(integer0.toString());

			Integer integer = Integer.valueOf(obj[4].toString()) / Integer.valueOf(obj[1].toString()) * 100;// 超时率
			String overtime = integer.toString() + "%";
			houseCustomerServiceType.setTimeOutRate(overtime);

			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[1].toString());// 总计服务数量
			timeOutService = DoubleFloatUtil.add(timeOutService, obj[4].toString());// 总计超时
			timeOutRate = DoubleFloatUtil.add(timeOutRate, integer.toString());// 总计超时率
			timeOutRate0 = timeOutRate + "%";

			listGoal.add(houseCustomerServiceType);
		}
		sortAndWrite1(listGoal, "serviceLoad", true, "serviceLoad_rank");// 总量排名
		sortAndWrite1(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名

		houseCustomerServiceType = new HouseCustomerServiceType();
		houseCustomerServiceType.setOrderNum("合计");
		houseCustomerServiceType.setServiceLoad(serviceLoad);// 总计服务数量
		houseCustomerServiceType.setTimeOutServiceLoad(timeOutService);// 总计超时
		houseCustomerServiceType.setTimeOutRate(timeOutRate0);// 总计超时率

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
	private void sortAndWrite1(List<HouseCustomerServiceType> list, String filedName, boolean ascFlag,
			String writeField) {
		CollectionUtil.sort(list, filedName, ascFlag);
		CollectionUtil<HouseCustomerServiceType> collectionUtil = new CollectionUtil<HouseCustomerServiceType>();
		collectionUtil.writeSort(list, writeField);
	}

}
