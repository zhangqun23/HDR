package com.mvc.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvc.dao.CustomerServiceDao;
import com.mvc.entity.DepartmentInfo;
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.HouseCustomerServiceLoad;
import com.mvc.entityReport.HouseCustomerServiceType;
import com.mvc.repository.DepartmentInfoRepository;
import com.mvc.service.CustomerServiceService;
import com.utils.CollectionUtil;
import com.utils.DoubleFloatUtil;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.WordHelper;

import net.sf.json.JSONObject;

/**
 * 酒店对客服务信息统计
 * 
 * @author wanghuimin
 * @date 2017年1月10日
 */
@Service("hotelCustomerServiceImpl")
public class CustomerServiceServiceImpl implements CustomerServiceService {
	@Autowired
	CustomerServiceDao hotelCustomerDao;
	@Autowired
	DepartmentInfoRepository departmentInfoRepository;

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
				endtime = jsonObject.getString("end_time");
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
	public String findHotelService(Map<String, Object> map) {
		List<Object> listSource = hotelCustomerDao.findHotelService(map);
		Iterator<Object> it = listSource.iterator();
		List<HoCustomerService> listGoal = listsourceToListGoal(it);
		String analyseResult = listsourceToListGoalAnalyse(listSource);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", listGoal);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();

	}

	private List<HoCustomerService> listsourceToListGoal(Iterator<Object> it) {
		List<HoCustomerService> listGoal = new ArrayList<HoCustomerService>();
		String serviceLoad = "0.0";// 总计服务数量
		String timeOutService = "0.0";// 总计超时服务
		String timeOutRate = "0.0";// 总计超时率
		Object[] obj;
		HoCustomerService hoCustomerService;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			hoCustomerService = new HoCustomerService();

			hoCustomerService.setDepartment(obj[0].toString());
			hoCustomerService.setServiceLoad(Float.valueOf(obj[1].toString()));
			hoCustomerService.setTimeOutService(obj[2].toString());
			hoCustomerService.setSumWorkTime(obj[3].toString());

			String overtime = StringUtil.divide(obj[2].toString(), obj[1].toString());// 超时率
			hoCustomerService.setTimeOutRate(Float.valueOf(overtime));

			String averagetime = StringUtil.divide(obj[3].toString(), obj[1].toString());// 平均用时
			hoCustomerService.setAverageWorkTime(averagetime);
			System.out.println("测试：" + averagetime);
			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[1].toString());// 总计服务数量
			timeOutService = DoubleFloatUtil.add(timeOutService, obj[2].toString());// 总计超时

			timeOutRate = DoubleFloatUtil.add(timeOutRate, overtime);// 总计超时率

			listGoal.add(hoCustomerService);
		}

		sortAndWrite(listGoal, "serviceLoad", false, "serviceLoad_rank");// 总量排名
		sortAndWrite(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名

		// 序号
		Iterator<HoCustomerService> itGoal = listGoal.iterator();
		int i = 0;
		hoCustomerService = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			hoCustomerService = itGoal.next();
			hoCustomerService.setOrderNum(String.valueOf(i));
		}

		hoCustomerService = new HoCustomerService();
		hoCustomerService.setOrderNum("合计");
		hoCustomerService.setServiceLoad(Float.valueOf(serviceLoad));
		hoCustomerService.setTimeOutService(timeOutService);
		hoCustomerService.setTimeOutRate(Float.valueOf(timeOutRate));
		listGoal.add(hoCustomerService);
		System.out.println(listGoal);

		return listGoal;
	}

	// 酒店对客服务信息统计添加文字
	private String listsourceToListGoalAnalyse(List<Object> listSource) {
		List<HoCustomerService> listGoal = new ArrayList<HoCustomerService>();
		Iterator<Object> it = listSource.iterator();
		String serviceLoad = "0.0";// 总计服务数量
		String timeOutService = "0.0";// 总计超时服务
		String timeOutRate = "0.0";// 总计超时率
		Object[] obj;
		HoCustomerService hoCustomerService;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			hoCustomerService = new HoCustomerService();

			hoCustomerService.setDepartment(obj[0].toString());
			hoCustomerService.setServiceLoad(Float.valueOf(obj[1].toString()));
			hoCustomerService.setTimeOutService(obj[2].toString());
			hoCustomerService.setSumWorkTime(obj[3].toString());

			String overtime = StringUtil.divide(obj[2].toString(), obj[1].toString());// 超时率
			hoCustomerService.setTimeOutRate(Float.valueOf(overtime));

			String averagetime = StringUtil.divide(obj[3].toString(), obj[1].toString());// 平均用时
			hoCustomerService.setAverageWorkTime(averagetime);
			System.out.println("测试：" + averagetime);
			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[1].toString());// 总计服务数量
			timeOutService = DoubleFloatUtil.add(timeOutService, obj[2].toString());// 总计超时

			timeOutRate = DoubleFloatUtil.add(timeOutRate, overtime);// 总计超时率

			listGoal.add(hoCustomerService);
		}

		sortAndWrite(listGoal, "serviceLoad", false, "serviceLoad_rank");// 总量排名
		sortAndWrite(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名

		// 序号
		Iterator<HoCustomerService> itGoal = listGoal.iterator();
		int i = 0;
		hoCustomerService = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			hoCustomerService = itGoal.next();
			hoCustomerService.setOrderNum(String.valueOf(i));
		}

		hoCustomerService = new HoCustomerService();
		hoCustomerService.setOrderNum("合计");
		hoCustomerService.setServiceLoad(Float.valueOf(serviceLoad));
		hoCustomerService.setTimeOutService(timeOutService);
		hoCustomerService.setTimeOutRate(Float.valueOf(timeOutRate));
		listGoal.add(hoCustomerService);
		System.out.println(listGoal);

		String analyseResult = "酒店对客服务服务数量为" + serviceLoad + "，超时服务为" + timeOutService + "超时率为" + timeOutRate;

		return analyseResult;
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

	// 导出酒店对客服务信息统计表
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportCustomerService(Map<String, Object> map, String path, String modelPath) {
		ResponseEntity<byte[]> byteww = null;
		String starttime = (String) map.get("start_Time");// 开始时间
		String endtime = (String) map.get("end_Time");// 结束时间
		List<HoCustomerService> listGoal = null;
		WordHelper wh = new WordHelper();

		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析

		String analyseResult = null;
		if (StringUtil.strIsNotEmpty(starttime) && StringUtil.strIsNotEmpty(endtime)) {
			List<Object> listSource = hotelCustomerDao.findHotelService(map);
			Iterator<Object> it = listSource.iterator();
			listGoal = listsourceToListGoal(it);

			analyseResult = listsourceToListGoalAnalyse(listSource);

		}
		if (listGoal != null) {
			String fileName = "酒店对客服务信息统计表.docx";
			String path0 = FileHelper.transPath(fileName, path);// 解析后的上传路径

			// 获取列表和文本信息
			listMap.put("0", listGoal);
			contentMap.put("${starttime}", starttime);
			contentMap.put("${endtime}", endtime);
			contentMap.put("${analyseResult}", analyseResult);

			try {
				OutputStream out = new FileOutputStream(path0);// 保存路径
				wh.export2007Word(modelPath, listMap, contentMap, 1, out, -1);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byteww = FileHelper.downloadFile(fileName, path0);

		}

		return byteww;
	}

	// 导出酒店对客服务信息excel统计表
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportCustomerServiceExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteww = null;
		try {
			ExcelHelper<HoCustomerService> ex = new ExcelHelper<HoCustomerService>();
			String starttime = (String) map.get("start_Time");// 开始时间
			String endtime = (String) map.get("end_Time");// 结束时间
			String fileName = "酒店对客服务信息excel统计表(统计时间：" + starttime + "至" + endtime + ").xlsx";

			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);
			List<Object> listSource = hotelCustomerDao.findHotelService(map);
			Iterator<Object> it = listSource.iterator();
			List<HoCustomerService> listGoal = listsourceToListGoal(it);

			String title = "酒店对客服务信息excel统计表(统计时间：" + starttime + "至" + endtime + ")";

			String[] header = { "序号", "部门", "服务数量", "超时服务", "超时率", "总用时", "平均用时", "总量排名", "超时率排名" };
			ex.export2007Excel(title, header, (Collection) listGoal, out, "yyyy-MM-dd", -1, -1, -1, 0, 1);// -1表示没有合并单元格,2:隐藏了实体类最后两个字段内容
			out.close();
			byteww = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteww;
	}
	/*
	 * ***********************************王慧敏报表1*******************************
	 */

	// 查询部门对客服务工作量统计
	@Override
	public String findDepartmentLoad(Map<String, Object> map) {
		List<Object> listSource = hotelCustomerDao.findDepartmentLoad(map);
		Iterator<Object> it = listSource.iterator();
		List<HouseCustomerServiceLoad> listGoal = listloadToListGoal(it);

		Object[] objOne = (Object[]) listSource.get(0);
		String department = objOne[5].toString();

		String analyseResult = department + "员工的平均服务数量为" + listloadToListGoalAnalyse(it);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", listGoal);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();

	}

	private List<HouseCustomerServiceLoad> listloadToListGoal(Iterator<Object> it) {
		List<HouseCustomerServiceLoad> listGoal = new ArrayList<HouseCustomerServiceLoad>();
		String serviceLoad = "0.0";// 总计服务数量
		String timeOutService = "0.0";// 总计超时
		String timeOutRate = "0.0";// 总计超时率

		Object[] obj;
		HouseCustomerServiceLoad houseCustomerServiceLoad;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			houseCustomerServiceLoad = new HouseCustomerServiceLoad();
			houseCustomerServiceLoad.setStaff_name(obj[0].toString());
			houseCustomerServiceLoad.setStaff_no(obj[1].toString());
			houseCustomerServiceLoad.setServiceLoad(Float.valueOf(obj[2].toString()));
			houseCustomerServiceLoad.setTimeOutService(obj[3].toString());
			houseCustomerServiceLoad.setSumWorkTime(obj[4].toString());
			String averagetime = StringUtil.divide(obj[4].toString(), obj[2].toString());// 平均用时
			houseCustomerServiceLoad.setAverageWorkTime(averagetime);

			String overtime = StringUtil.divide(obj[3].toString(), obj[2].toString());// 超时率
			houseCustomerServiceLoad.setTimeOutRate(Float.valueOf(overtime));

			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[2].toString());// 总计服务数量

			timeOutService = DoubleFloatUtil.add(timeOutService, obj[3].toString());// 总计超时
			timeOutRate = DoubleFloatUtil.add(timeOutRate, overtime);// 总计超时率

			listGoal.add(houseCustomerServiceLoad);
		}

		sortAndWrite0(listGoal, "serviceLoad", false, "serviceLoad_rank");// 总量排名
		sortAndWrite0(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名

		// 序号
		Iterator<HouseCustomerServiceLoad> itGoal = listGoal.iterator();
		int i = 0;
		houseCustomerServiceLoad = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			houseCustomerServiceLoad = itGoal.next();
			houseCustomerServiceLoad.setOrderNum(String.valueOf(i));
		}

		houseCustomerServiceLoad = new HouseCustomerServiceLoad();
		houseCustomerServiceLoad.setOrderNum("合计");
		houseCustomerServiceLoad.setServiceLoad(Float.valueOf(serviceLoad));// 总计服务数量
		houseCustomerServiceLoad.setTimeOutService(timeOutService);// 总计超时
		houseCustomerServiceLoad.setTimeOutRate(Float.valueOf(timeOutRate));// 总计超时率
		listGoal.add(houseCustomerServiceLoad);

		return listGoal;
	}

	// 部门对客服务工作量添加文字
	private String listloadToListGoalAnalyse(Iterator<Object> it) {
		List<HouseCustomerServiceLoad> listGoal = new ArrayList<HouseCustomerServiceLoad>();
		String serviceLoad = "0.0";// 总计服务数量

		Object[] obj;
		HouseCustomerServiceLoad houseCustomerServiceLoad;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			houseCustomerServiceLoad = new HouseCustomerServiceLoad();
			houseCustomerServiceLoad.setStaff_name(obj[0].toString());
			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[2].toString());// 总计服务数量

			listGoal.add(houseCustomerServiceLoad);
		}

		// 序号
		Iterator<HouseCustomerServiceLoad> itGoal = listGoal.iterator();
		int i = 0;
		houseCustomerServiceLoad = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			houseCustomerServiceLoad = itGoal.next();
			houseCustomerServiceLoad.setOrderNum(String.valueOf(i));
		}

		String str = StringUtil.divide(serviceLoad, i + "");
		return str;
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

	// 导出部门对客服务工作量统计表
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportRoomWorkload(Map<String, Object> map, String path, String modelPath) {
		ResponseEntity<byte[]> byteww = null;
		String starttime = (String) map.get("start_Time");// 开始时间
		String endtime = (String) map.get("end_Time");// 结束时间
		List<HouseCustomerServiceLoad> listGoal = null;
		WordHelper wh = new WordHelper();

		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析

		String department = null;// 部门名称
		String analyseResult = null;
		if (StringUtil.strIsNotEmpty(starttime) && StringUtil.strIsNotEmpty(endtime)) {
			List<Object> listSource = hotelCustomerDao.findDepartmentLoad(map);
			Object[] objOne = (Object[]) listSource.get(0);
			department = objOne[5].toString();
			Iterator<Object> it = listSource.iterator();
			listGoal = listloadToListGoal(it);

			analyseResult = department + "员工的平均服务数量为" + listloadToListGoalAnalyse(it);

		}

		if (listGoal != null) {
			String fileName = department + "对客服务信息统计表.docx";
			String path0 = FileHelper.transPath(fileName, path);// 解析后的上传路径

			// 获取列表和文本信息
			listMap.put("0", listGoal);
			contentMap.put("${starttime}", starttime);
			contentMap.put("${endtime}", endtime);
			contentMap.put("${depart}", department);
			contentMap.put("${analyseResult}", analyseResult);

			try {
				OutputStream out = new FileOutputStream(path0);// 保存路径
				wh.export2007Word(modelPath, listMap, contentMap, 1, out, -1);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byteww = FileHelper.downloadFile(fileName, path0);

		}

		return byteww;
	}

	// 导出部门对客服务工作量excel统计表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportRoomWorkloadExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteww = null;
		try {
			ExcelHelper<HouseCustomerServiceLoad> ex = new ExcelHelper<HouseCustomerServiceLoad>();
			String starttime = (String) map.get("start_Time");// 开始时间
			String endtime = (String) map.get("end_Time");// 结束时间
			List<HouseCustomerServiceLoad> listGoal = null;
			String department = null;// 部门名称
			if (StringUtil.strIsNotEmpty(starttime) && StringUtil.strIsNotEmpty(endtime)) {
				List<Object> listSource = hotelCustomerDao.findDepartmentLoad(map);
				Object[] objOne = (Object[]) listSource.get(0);
				department = objOne[5].toString();
				Iterator<Object> it = listSource.iterator();
				listGoal = listloadToListGoal(it);
			}
			String fileName = department + "对客服务信息统计表(统计时间：" + starttime + "至" + endtime + ").xlsx";

			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			String title = department + "对客服务信息统计表(统计时间：" + starttime + "至" + endtime + ")";

			String[] header = { "序号", "员工姓名", "员工编号", "服务数量", "超时服务", "总用时", "平均用时", "超时率", "总量排名", "超时率排名" };
			ex.export2007Excel(title, header, (Collection) listGoal, out, "yyyy-MM-dd", -1, -1, -1, 0, 1);// -1表示没有合并单元格,2:隐藏了实体类最后两个字段内容
			out.close();
			byteww = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteww;
	}
	/*
	 * ***********************************王慧敏报表2*******************************
	 */

	// 查询部门对客服务类型统计
	@Override
	public List<HouseCustomerServiceType> findRoomType(Map<String, Object> map) {
		List<Object> listSource = hotelCustomerDao.findRoomType(map);
		Iterator<Object> it = listSource.iterator();
		List<HouseCustomerServiceType> listGoal = listtypeToListGoal(it);
		return listGoal;
	}

	@SuppressWarnings("unused")
	private List<HouseCustomerServiceType> listtypeToListGoal(Iterator<Object> it) {
		List<HouseCustomerServiceType> listGoal = new ArrayList<HouseCustomerServiceType>();
		String serviceLoad = "0.0";// 总计服务数量
		String timeOutService = "0.0";// 总计超时
		String timeOutRate = "0.0";// 总计超时率

		Object[] obj;
		HouseCustomerServiceType houseCustomerServiceType;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			houseCustomerServiceType = new HouseCustomerServiceType();
			houseCustomerServiceType.setServiceType(obj[0].toString());
			houseCustomerServiceType.setServiceLoad(Float.valueOf(obj[1].toString()));
			houseCustomerServiceType.setGiveTime(obj[2].toString());
			houseCustomerServiceType.setTimeOutServiceLoad(obj[4].toString());
			String averagertime = StringUtil.divide(obj[3].toString(), obj[1].toString());// 平均用时
			houseCustomerServiceType.setAverageWorkTime(averagertime);

			String overtime = StringUtil.divide(obj[4].toString(), obj[1].toString());// 超时率
			houseCustomerServiceType.setTimeOutRate(Float.valueOf(overtime));

			serviceLoad = DoubleFloatUtil.add(serviceLoad, obj[1].toString());// 总计服务数量
			timeOutService = DoubleFloatUtil.add(timeOutService, obj[4].toString());// 总计超时
			timeOutRate = DoubleFloatUtil.add(timeOutRate, overtime);// 总计超时率

			listGoal.add(houseCustomerServiceType);
		}

		sortAndWrite1(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名
		sortAndWrite1(listGoal, "serviceLoad", false, "serviceLoad_rank");// 总量排名

		Iterator<HouseCustomerServiceType> itGoal = listGoal.iterator();
		int i = 0;
		houseCustomerServiceType = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			houseCustomerServiceType = itGoal.next();
			houseCustomerServiceType.setOrderNum(String.valueOf(i));
		}

		houseCustomerServiceType = new HouseCustomerServiceType();
		houseCustomerServiceType.setOrderNum("合计");
		houseCustomerServiceType.setServiceLoad(Float.valueOf(serviceLoad));// 总计服务数量
		houseCustomerServiceType.setTimeOutServiceLoad(timeOutService);// 总计超时
		houseCustomerServiceType.setTimeOutRate(Float.valueOf(timeOutRate));// 总计超时率
		listGoal.add(houseCustomerServiceType);

		return listGoal;

	}

	// 得出服务类型文字分析
	@Override
	public String listtypeToListGoalWord(Map<String, Object> map) {
		List<Object> listSource = hotelCustomerDao.findRoomType(map);
		Iterator<Object> it = listSource.iterator();

		List<HouseCustomerServiceType> listGoal = new ArrayList<HouseCustomerServiceType>();

		Object[] obj;
		HouseCustomerServiceType houseCustomerServiceType;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			houseCustomerServiceType = new HouseCustomerServiceType();
			houseCustomerServiceType.setServiceType(obj[0].toString());
			houseCustomerServiceType.setServiceLoad(Float.valueOf(obj[1].toString()));
			houseCustomerServiceType.setGiveTime(obj[2].toString());
			houseCustomerServiceType.setTimeOutServiceLoad(obj[4].toString());
			String averagertime = StringUtil.divide(obj[3].toString(), obj[1].toString());// 平均用时
			houseCustomerServiceType.setAverageWorkTime(averagertime);

			String overtime = StringUtil.divide(obj[4].toString(), obj[1].toString());// 超时率
			houseCustomerServiceType.setTimeOutRate(Float.valueOf(overtime));

			listGoal.add(houseCustomerServiceType);
		}

		sortAndWrite1(listGoal, "timeOutRate", true, "timeOutRate_rank");// 超时率排名
		houseCustomerServiceType = null;
		String analyseResult0 = "分析结果:对客服务类型超时率排名前三的是：";
		Iterator<HouseCustomerServiceType> itGoal0 = listGoal.iterator();
		int j = 0;
		while (itGoal0.hasNext() && j < 3) {
			j++;
			houseCustomerServiceType = itGoal0.next();
			analyseResult0 += houseCustomerServiceType.getServiceType();
			if (j < 3) {
				analyseResult0 = analyseResult0 + ",";
			}
		}

		sortAndWrite1(listGoal, "serviceLoad", false, "serviceLoad_rank");// 总量排名
		houseCustomerServiceType = null;
		String analyseResult00 = "对客服务类型服务数量排名前三的是：";
		Iterator<HouseCustomerServiceType> itGoal00 = listGoal.iterator();
		int jj = 0;
		while (itGoal00.hasNext() && jj < 3) {
			jj++;
			houseCustomerServiceType = itGoal00.next();
			analyseResult00 += houseCustomerServiceType.getServiceType();
			if (jj < 3) {
				analyseResult00 = analyseResult00 + ",";
			}
		}
		String analyseResult = analyseResult0 + ";" + analyseResult00;

		return analyseResult;

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

	// 导出部门对客服务服务类型统计表
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<byte[]> exportRoomType(Map<String, Object> map, String path, String picPath,
			String modelPath) {
		ResponseEntity<byte[]> byteww = null;
		String starttime = (String) map.get("start_Time");// 开始时间
		String endtime = (String) map.get("end_Time");// 结束时间
		String photo = (String) map.get("photo");// 图片

		String analyseResult = listtypeToListGoalWord(map);// 图片文字

		// 添加图片
		String picName1 = null;
		if (StringUtil.strIsNotEmpty(photo)) {
			picName1 = "pic1.png";
			picPath = FileHelper.transPath(picName1, picPath);
		}
		Map<String, Object> picMap = new HashMap<String, Object>();
		picMap = PictureUtil.getHighPicMap(picName1, picPath, photo);

		List<HouseCustomerServiceType> listGoal = null;
		WordHelper wh = new WordHelper();

		Map<String, Object> contentMap = new HashMap<String, Object>();// 获取文本数据
		Map<String, Object> listMap = new HashMap<String, Object>();// 多个实体list放到Map中，在WordHelper中解析

		String department = null;// 部门名称
		if (StringUtil.strIsNotEmpty(starttime) && StringUtil.strIsNotEmpty(endtime)) {
			List<Object> listSource = hotelCustomerDao.findRoomType(map);
			Object[] objOne = (Object[]) listSource.get(0);
			department = objOne[5].toString();
			Iterator<Object> it = listSource.iterator();
			listGoal = listtypeToListGoal(it);

		}
		if (listGoal != null) {
			String fileName = department + "对客服务服务类型统计表.docx";
			String path0 = FileHelper.transPath(fileName, path);// 解析后的上传路径

			// 获取列表和文本信息
			listMap.put("0", listGoal);
			contentMap.put("${starttime}", starttime);
			contentMap.put("${endtime}", endtime);
			contentMap.put("${depart}", department);
			contentMap.put("${photo}", picMap);
			contentMap.put("${analyseResult}", analyseResult);

			try {
				OutputStream out = new FileOutputStream(path0);// 保存路径
				wh.export2007Word(modelPath, listMap, contentMap, 1, out, -1);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byteww = FileHelper.downloadFile(fileName, path0);

		}

		return byteww;
	}

	// 导出部门对客服务服务类型excel统计表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportRoomTypeExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteww = null;
		try {
			ExcelHelper<HouseCustomerServiceType> ex = new ExcelHelper<HouseCustomerServiceType>();
			String starttime = (String) map.get("start_Time");// 开始时间
			String endtime = (String) map.get("end_Time");// 结束时间
			List<HouseCustomerServiceType> listGoal = null;
			String department = null;// 部门名称
			if (StringUtil.strIsNotEmpty(starttime) && StringUtil.strIsNotEmpty(endtime)) {
				List<Object> listSource = hotelCustomerDao.findRoomType(map);
				Object[] objOne = (Object[]) listSource.get(0);
				department = objOne[5].toString();
				Iterator<Object> it = listSource.iterator();
				listGoal = listtypeToListGoal(it);

			}
			String fileName = department + "对客服务类型统计表(统计时间：" + starttime + "至" + endtime + ").xlsx";

			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			String title = department + "对客服务信息统计表(统计时间：" + starttime + "至" + endtime + ")";

			String[] header = { "序号", "服务类型", "服务数量", "给定时间", "平均用时", "超时服务", "超时率", "总量排名", "超时率排名" };
			ex.export2007Excel(title, header, (Collection) listGoal, out, "yyyy-MM-dd", -1, -1, -1, 0, 1);// -1表示没有合并单元格,2:隐藏了实体类最后两个字段内容
			out.close();
			byteww = FileHelper.downloadFile(fileName, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteww;
	}

	/*
	 * ***********************************王慧敏报表需求*******************************
	 */
	// 查询部门列表
	@Override
	public List<DepartmentInfo> findDep() {
		return departmentInfoRepository.selectDep();
	}

	// 根据部门ID筛选员工信息
	@Override
	public List<Object> findStaffByDepId(String departid) {
		return hotelCustomerDao.findStaffByDepId(departid);
	}

}
