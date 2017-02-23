package com.mvc.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.base.enums.CleanType;
import com.mvc.dao.WorkHouseDao;
import com.mvc.entity.DepartmentInfo;
import com.mvc.entity.RoomSort;
import com.mvc.entityReport.WorkEfficiency;
import com.mvc.entityReport.WorkHouse;
import com.mvc.repository.DepartmentInfoRepository;
import com.mvc.repository.RoomSortRepository;
import com.mvc.service.WorkHouseService;
import com.utils.CollectionUtil;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.WordHelper;

import net.sf.json.JSONObject;

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
	@Autowired
	DepartmentInfoRepository departmentInfoRepository;
	@Autowired
	RoomSortRepository roomSortRepository;

	// 查询员工做房(两次排序，效率低，注意后期优化！)
	@Override
	public String selectWorkHouse(Map<String, Object> map) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());

		List<Object> listSource = workHouseDao.selectWorkHouse(map);
		Iterator<Object> it = listSource.iterator();
		List<WorkHouse> listGoal = objToWorkHouse(it);

		WorkHouse sum = sumWorkHouse(listGoal);// 合计
		listGoal.add(sum);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", listGoal);

		listGoal.remove(listGoal.size() - 1);
		String analyseResult = anaWorkHouse(map, listGoal);
		jsonObject.put("analyseResult", analyseResult);// 报表分析
		return jsonObject.toString();
	}

	// 部门员工做房用时统计Word
	@Override
	public ResponseEntity<byte[]> exportWorkHouse(Map<String, Object> map, String path, String tempPath) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());
		String sortName = (String) map.remove("sortName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工" + sortName + "做房时间统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = workHouseDao.selectWorkHouse(map);
			Iterator<Object> it = listSource.iterator();
			List<WorkHouse> listGoal = objToWorkHouse(it);

			WorkHouse sum = sumWorkHouse(listGoal);// 合计
			listGoal.add(sum);

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("${sortName}", sortName);
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 7));
			contentMap.put("${endTime}", endTime.substring(0, 7));

			wh.export2007Word(tempPath, listMap, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

	// 部门员工做房用时统计Excel
	@Override
	public ResponseEntity<byte[]> exportWorkHouseExcel(Map<String, Object> map, String path) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());
		String sortName = (String) map.remove("sortName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			ExcelHelper<WorkHouse> ex = new ExcelHelper<WorkHouse>();
			String fileName = "客房部员工" + sortName + "做房时间统计表.xlsx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = workHouseDao.selectWorkHouse(map);
			Iterator<Object> it = listSource.iterator();
			List<WorkHouse> listGoal = objToWorkHouse(it);

			WorkHouse sum = sumWorkHouse(listGoal);// 合计
			listGoal.add(sum);

			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			String title = "客房部员工" + sortName + "做房时间统计表(" + startTime.substring(0, 7) + "至" + endTime.substring(0, 7)
					+ ")";
			String[] header = { "序号", "员工姓名", "员工编号", "抹尘房[数量,总用时,平均用时,排名]", "过夜房[数量,总用时,平均用时,排名]",
					"离退房[数量,总用时,平均用时,排名]" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, listGoal, out, "yyyy-MM-dd", -1, 0, 2);

			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteArr;
	}

	// 计算及排序
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

			String avg_time_dust = StringUtil.divide(obj[3].toString(), obj[2].toString());
			workHouse.setAvg_time_dust(Float.valueOf(avg_time_dust));// 抹尘房平均用时
			String avg_time_night = StringUtil.divide(obj[5].toString(), obj[4].toString());
			workHouse.setAvg_time_night(Float.valueOf(avg_time_night));// 过夜房平均用时
			String avg_time_leave = StringUtil.divide(obj[7].toString(), obj[6].toString());
			workHouse.setAvg_time_leave(Float.valueOf(avg_time_leave));// 离退房平均用时

			listGoal.add(workHouse);
		}
		// 分别对抹尘房、过夜房、离退房排序并编号
		sortAndWrite(listGoal, "avg_time_dust", true, "rank_dust");
		sortAndWrite(listGoal, "avg_time_night", true, "rank_night");
		sortAndWrite(listGoal, "avg_time_leave", true, "rank_leave");

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
	 * 做房用时报表分析
	 * 
	 * @param map
	 * @param list
	 * @return
	 */
	private String anaWorkHouse(Map<String, Object> map, List<WorkHouse> list) {
		StringBuilder strb = new StringBuilder();

		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		String sortNo = (String) map.get("roomType");
		strb.append("从" + startTime.substring(0, 7) + "至" + endTime.substring(0, 7));
		RoomSort roomSort = roomSortRepository.selectNameBySortNo(sortNo);
		strb.append(roomSort.getSortName() + "房型，员工做房平均用时排名前三：");

		StringBuilder strbLeave = new StringBuilder();
		int i = 0;
		for (WorkHouse workHouse : list) {// list中离退房已经有序
			if (i < 3) {
				strbLeave.append(workHouse.getStaff_name() + "(" + workHouse.getAvg_time_leave() + ")，");
			} else {
				break;
			}
			i++;
		}

		strb.append("抹尘房：" + getFirstThree(list, "avg_time_dust", true) + "；");
		strb.append("过夜房：" + getFirstThree(list, "avg_time_night", true) + "；");
		strb.append("离退房：" + strbLeave.substring(0, strbLeave.length() - 1) + "；");
		return strb.toString();
	}

	/**
	 * 返回前三条记录组成的字符串
	 * 
	 * @param list
	 * @param filedNamezg
	 *            按指定字段排序
	 * @param ascFlag
	 *            true升序,false降序
	 * @return
	 */
	private String getFirstThree(List<WorkHouse> list, String filedName, boolean ascFlag) {
		CollectionUtil.sort(list, filedName, ascFlag);
		StringBuilder subStrb = new StringBuilder();
		int i = 0;
		for (WorkHouse workHouse : list) {
			if (i < 3) {
				subStrb.append(workHouse.getStaff_name() + "(" + workHouse.getAvg_time_leave() + ")，");
			} else {
				break;
			}
			i++;
		}
		return subStrb.substring(0, subStrb.length() - 1);
	}

	/**
	 * 排序并插入序号
	 * 
	 * @param list
	 * @param filedNamezg
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

	// 获取单个员工做房用时
	@Override
	public String selectUserWorkHouseByLimits(Map<String, Object> map) {
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

		List<Object> monthList = workHouseDao.selectMonthWorkTime(map);// 获取员工每个月做房用时
		List<Integer> monthListStr = perMonth(monthList, startMonth, endMonth);
		jsonObject.put("list", monthListStr);

		String staffId = (String) map.remove("staffId");// 去掉staffId
		Object[] obj = null;
		String averWorkTime = null;
		Boolean flag = true;
		long sumNum = (long) 0;
		long sumTime = (long) 0;
		List<Object> averList = workHouseDao.selectAllAverWorkTime(map);// 获取全体员工平均做房用时
		Iterator<Object> it = averList.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sumNum += Long.parseLong(obj[1].toString());
			sumTime += Long.parseLong(obj[2].toString());
			if (flag) {
				if (obj[0].toString().trim().equals(staffId)) {
					averWorkTime = StringUtil.divide(obj[2].toString(), obj[1].toString());// 员工平均做房用时
					flag = false;
				}
			}
		}
		if (averWorkTime != null) {
			jsonObject.put("averWorkTime", Float.valueOf(averWorkTime));
		}
		String allAverWorkTime = StringUtil.divide(String.valueOf(sumTime), String.valueOf(sumNum));
		String analyseResult = getWorkHouseAnaRe(averWorkTime, allAverWorkTime);
		jsonObject.put("allAverWorkTime", Float.valueOf(allAverWorkTime));// 全体员工平均做房用时
		jsonObject.put("analyseResult", analyseResult);// 分析结果

		return jsonObject.toString();
	}

	/**
	 * 做房用时分析结果
	 * 
	 * @param averWorkTime
	 *            个人
	 * @param allAverWorkTime
	 *            全体
	 * @return
	 */
	private String getWorkHouseAnaRe(String averWorkTime, String allAverWorkTime) {
		String analyseResult = "分析结果:  ";
		Float dValue = Float.valueOf(allAverWorkTime) - Float.valueOf(averWorkTime);
		Float level = Float.valueOf(StringUtil.divide(dValue.toString(), allAverWorkTime));
		if (Math.abs(level) <= 0.05) {
			analyseResult += "良好(该员工平均做房用时与全体员工平均做房用时相差不大)";
		} else if (level > 0.05) {
			analyseResult += "优秀(该员工平均做房用时高于全体员工平均做房用时)";
		} else if (level < 0.05) {
			analyseResult += "不合格(该员工平均做房用时远低于全体员工平均做房用时)";
		}
		return analyseResult;
	}

	/**
	 * 去掉月份列，缺少的月份的列置为0
	 * 
	 * @param list
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	private List<Integer> perMonth(List<Object> list, String startMonth, String endMonth) {
		List<Integer> listGoal = new ArrayList<Integer>();
		if (StringUtil.strIsNotEmpty(startMonth) && StringUtil.strIsNotEmpty(endMonth)) {
			Integer startM = Integer.valueOf(startMonth);
			Integer endM = Integer.valueOf(endMonth);
			Integer size = list.size();

			Object[] obj = null;
			Integer month = null;
			for (int i = 0, j = startM; i < size || j <= endM; i++, j++) {
				if (i < size) {
					obj = (Object[]) list.get(i);
					month = Integer.valueOf(obj[0].toString());
					if (month == j) {
						listGoal.add(Integer.valueOf(obj[1].toString()));
					} else {
						listGoal.add(0);
						i--;
					}
				} else {
					listGoal.add(0);
				}
			}
		}
		return listGoal;
	}

	/**
	 * list求和
	 * 
	 * @param list
	 * @return
	 */
	private WorkHouse sumWorkHouse(List<WorkHouse> list) {
		WorkHouse sum = new WorkHouse();
		Iterator<WorkHouse> it = list.iterator();

		long sum_num_dust = (long) 0;// 抹尘房
		long sum_total_time_dust = (long) 0;
		float sum_avg_time_dust = 0f;
		long sum_num_night = (long) 0;// 过夜房
		long sum_total_time_night = (long) 0;
		float sum_avg_time_night = 0f;
		long sum_num_leave = (long) 0;// 离退房
		long sum_total_time_leave = (long) 0;
		float sum_avg_time_leave = 0f;

		WorkHouse workHouse = null;
		while (it.hasNext()) {
			workHouse = it.next();
			sum_num_dust += Integer.parseInt(workHouse.getNum_dust());// 抹尘房
			sum_total_time_dust += Integer.parseInt(workHouse.getTotal_time_dust());
			sum_avg_time_dust += workHouse.getAvg_time_dust();
			sum_num_night += Integer.parseInt(workHouse.getNum_night());// 过夜房
			sum_total_time_night += Integer.parseInt(workHouse.getTotal_time_night());
			sum_avg_time_night += workHouse.getAvg_time_night();
			sum_num_leave += Integer.parseInt(workHouse.getNum_leave());// 离退房
			sum_total_time_leave += Integer.parseInt(workHouse.getTotal_time_leave());
			sum_avg_time_leave += workHouse.getAvg_time_leave();
		}
		sum.setOrderNum("合计");
		sum.setNum_dust(String.valueOf(sum_num_dust));// 抹尘房
		sum.setTotal_time_dust(String.valueOf(sum_total_time_dust));
		sum.setAvg_time_dust(Float.valueOf(sum_avg_time_dust));
		sum.setNum_night(String.valueOf(sum_num_night));// 过夜房
		sum.setTotal_time_night(String.valueOf(sum_total_time_night));
		sum.setAvg_time_night(Float.valueOf(sum_avg_time_night));
		sum.setNum_leave(String.valueOf(sum_num_leave));// 离退房
		sum.setTotal_time_leave(String.valueOf(sum_total_time_leave));
		sum.setAvg_time_leave(Float.valueOf(sum_avg_time_leave));

		return sum;
	}

	// 部门员工做房用时分析导出
	@Override
	public ResponseEntity<byte[]> exportWorkHouseAna(Map<String, Object> map, String path, String tempPath,
			String picPath) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());
		String staffName = (String) map.get("staffName");
		String sortName = (String) map.get("sortName");
		String cleanType = (String) map.get("cleanType");
		String cleanTypeStr = CleanType.intToStr(Integer.valueOf(cleanType));
		String year = (String) map.get("checkYear");
		String quarter = (String) map.get("quarter");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工" + sortName + cleanTypeStr + "做房用时分析.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("${staffName}", staffName);
			contentMap.put("${roomType}", sortName);// 房间类型名称
			contentMap.put("${cleanType}", cleanTypeStr);
			if (StringUtil.strIsNotEmpty(year) && StringUtil.strIsNotEmpty(quarter)) {
				String startTime = StringUtil.quarterFirstDay(year, quarter);
				String endTime = StringUtil.quarterLastDay(year, quarter);
				startTime = startTime.substring(0, 10);// 保留到天
				endTime = endTime.substring(0, 10);
				contentMap.put("${startTime}", startTime);
				contentMap.put("${endTime}", endTime);
			}

			// 图片相关
			String svg1 = (String) map.get("chart1SVGStr");
			Map<String, Object> picMap = PictureUtil.getPicMap(picPath, svg1);
			contentMap.put("${pic1}", picMap);

			wh.export2007Word(tempPath, null, contentMap, 1, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

	/**** 员工工作效率报表 ****/

	// 查询员工工作效率(两次排序，效率低，注意后期优化！)
	@Override
	public String selectWorkEffByLimits(Map<String, Object> map) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());

		List<Object> listSource = workHouseDao.selectWorkEffByLimits(map);
		Iterator<Object> it = listSource.iterator();
		List<WorkEfficiency> listGoal = objToWorkEff(it);

		WorkEfficiency workEfficiency = sumWorkEff(listGoal);
		listGoal.add(workEfficiency);// 合计

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", listGoal);

		listGoal.remove(listGoal.size() - 1);
		String analyseResult = anaWorkEff(map, listGoal);
		jsonObject.put("analyseResult", analyseResult);// 报表分析
		return jsonObject.toString();
	}

	/**
	 * 做房用时报表分析(工作效率)
	 * 
	 * @param map
	 * @param list
	 * @return
	 */
	private String anaWorkEff(Map<String, Object> map, List<WorkEfficiency> list) {
		StringBuilder strb = new StringBuilder();

		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		strb.append("从" + startTime.substring(0, 7) + "至" + endTime.substring(0, 7));
		strb.append("客房部员工做房效率排名前三：");
		strb.append(getEffFirstThree(list, "house_eff", false) + "；");
		strb.append("工作效率排名前三：");
		strb.append(getEffFirstThree(list, "house_serv_eff", false) + "；");
		return strb.toString();
	}

	/**
	 * 返回前三条记录组成的字符串(工作效率)
	 * 
	 * @param list
	 * @param filedNamezg
	 *            按指定字段排序
	 * @param ascFlag
	 *            true升序,false降序
	 * @return
	 */
	private String getEffFirstThree(List<WorkEfficiency> list, String filedName, boolean ascFlag) {
		CollectionUtil.sort(list, filedName, ascFlag);
		StringBuilder subStrb = new StringBuilder();
		int i = 0;
		for (WorkEfficiency workEfficiency : list) {
			if (i < 3) {
				subStrb.append(workEfficiency.getStaff_name() + "("
						+ StringUtil.strfloatToPer(workEfficiency.getHouse_eff()) + ")，");
			} else {
				break;
			}
			i++;
		}
		return subStrb.substring(0, subStrb.length() - 1);
	}

	// 工作效率计算
	private List<WorkEfficiency> objToWorkEff(Iterator<Object> it) {
		List<WorkEfficiency> listGoal = new ArrayList<WorkEfficiency>();
		Object[] obj = null;
		WorkEfficiency workEff = null;
		int i = 0;
		while (it.hasNext()) {
			i++;
			obj = (Object[]) it.next();
			workEff = new WorkEfficiency();
			workEff.setOrderNum(String.valueOf(i));
			workEff.setStaff_name(obj[0].toString());
			workEff.setStaff_no(obj[1].toString());
			workEff.setWork_time(obj[2].toString());// 当班时间
			workEff.setHouse_time(obj[3].toString());// 做房时间
			workEff.setHouse_serv_time(obj[4].toString());// 做房+客服时间

			String house_eff = StringUtil.divide(obj[3].toString(), obj[2].toString());
			workEff.setHouse_eff(Float.valueOf(house_eff));// 做房效率(%)
			String house_serv_eff = StringUtil.divide(obj[4].toString(), obj[2].toString());
			workEff.setHouse_serv_eff(Float.valueOf(house_serv_eff));// 工作(做房+客服)效率(%)

			listGoal.add(workEff);
		}

		return listGoal;
	}

	// 获取单个员工工作效率
	@Override
	public String selectUserWorkEffByLimits(Map<String, Object> map) {
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

		List<Object> monthList1 = workHouseDao.selectMonthHouseEff(map);// 获取员工每个月做房效率
		List<String> monthListStr1 = perMonthEff(monthList1, startMonth, endMonth);
		jsonObject.put("list1", monthListStr1);
		List<Object> monthList = workHouseDao.selectMonthWorkEff(map);// 获取员工每个月工作效率
		List<String> monthListStr = perMonthEff(monthList, startMonth, endMonth);
		jsonObject.put("list", monthListStr);

		String staffId = (String) map.remove("staffId");// 去掉staffId
		Object[] obj = null;
		String averHouseEff = null;
		Boolean flag = true;
		long sumDutyTime = (long) 0;
		long sumHouseTime = (long) 0;
		List<Object> averList = workHouseDao.selectAllAverHouseEff(map);// 获取全体员工平均做房效率
		Iterator<Object> it = averList.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sumDutyTime += Long.parseLong(obj[1].toString());
			sumHouseTime += Long.parseLong(obj[2].toString());
			if (flag) {
				if (obj[0].toString().trim().equals(staffId)) {
					averHouseEff = StringUtil.divide(obj[2].toString(), obj[1].toString());// 员工平均做房效率
					flag = false;
				}
			}
		}
		if (averHouseEff != null) {
			jsonObject.put("averWorkEfficiency1", Float.valueOf(averHouseEff));
		}
		String allAverHouseEff = StringUtil.divide(String.valueOf(sumHouseTime), String.valueOf(sumDutyTime));
		jsonObject.put("allAverWorkEfficiency1", Float.valueOf(allAverHouseEff));// 全体员工平均做房效率

		obj = null;// 先清空对象，再重新赋值
		String averWorkEff = null;
		flag = true;
		sumDutyTime = (long) 0;
		long sumWorkTime = (long) 0;
		averList = null;
		it = null;
		averList = workHouseDao.selectAllAverHouseEff(map);// 获取全体员工平均工作效率
		it = averList.iterator();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sumDutyTime += Long.parseLong(obj[1].toString());
			sumWorkTime += Long.parseLong(obj[2].toString());
			if (flag) {
				if (obj[0].toString().trim().equals(staffId)) {
					averWorkEff = StringUtil.divide(obj[2].toString(), obj[1].toString());// 员工平均工作效率
					flag = false;
				}
			}
		}
		if (averWorkEff != null) {
			jsonObject.put("averWorkEfficiency", Float.valueOf(averWorkEff));
		}
		String allAverWorkEff = StringUtil.divide(String.valueOf(sumWorkTime), String.valueOf(sumDutyTime));
		jsonObject.put("allAverWorkEfficiency", Float.valueOf(allAverWorkEff));// 全体员工平均工作效率
		String analyseResult1 = getWorkEffAnaRe(averWorkEff, allAverWorkEff, "工作");
		jsonObject.put("analyseResult1", analyseResult1);// 工作效率分析
		String analyseResult2 = getWorkEffAnaRe(averHouseEff, allAverHouseEff, "做房");
		jsonObject.put("analyseResult2", analyseResult2);// 做房效率分析

		return jsonObject.toString();
	}

	/**
	 * 做房效率分析结果
	 * 
	 * @param averEff
	 *            个人
	 * @param allAverEff
	 *            全体
	 * @param content
	 *            做房/工作
	 * @return
	 */
	private String getWorkEffAnaRe(String averEff, String allAverEff, String content) {
		String analyseResult = "分析结果:  ";
		Float dValue = Float.valueOf(allAverEff) - Float.valueOf(averEff);
		Float level = Float.valueOf(StringUtil.divide(dValue.toString(), allAverEff));
		if (Math.abs(level) <= 0.05) {
			analyseResult += "良好(该员工平均" + content + "效率与全体员工平均" + content + "效率相差不大)";
		} else if (level > 0.05) {
			analyseResult += "优秀(该员工平均" + content + "效率高于全体员工平均" + content + "效率)";
		} else if (level < 0.05) {
			analyseResult += "不合格(该员工平均" + content + "效率远低于全体员工平均" + content + "效率)";
		}
		return analyseResult;
	}

	/**
	 * 去掉月份列，缺少的月份的列置为0，同时计算效率
	 * 
	 * @param list
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	private List<String> perMonthEff(List<Object> list, String startMonth, String endMonth) {
		List<String> listGoal = new ArrayList<String>();
		if (StringUtil.strIsNotEmpty(startMonth) && StringUtil.strIsNotEmpty(endMonth)) {
			Integer startM = Integer.valueOf(startMonth);
			Integer endM = Integer.valueOf(endMonth);
			Integer size = list.size();

			Object[] obj = null;
			Integer month = null;
			for (int i = 0, j = startM; i < size || j <= endM; i++, j++) {
				if (i < size) {
					obj = (Object[]) list.get(i);
					month = Integer.valueOf(obj[0].toString());
					if (month == j) {
						listGoal.add(StringUtil.divide(obj[2].toString(), obj[1].toString()));
					} else {
						listGoal.add("0");
						i--;
					}
				} else {
					listGoal.add("0");
				}
			}
		}
		return listGoal;
	}

	// 部门员工工作效率统计导出Word
	@Override
	public ResponseEntity<byte[]> exportWorkEffByLimits(Map<String, Object> map, String path, String tempPath) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkEfficiency> wh = new WordHelper<WorkEfficiency>();
			String fileName = "客房部员工工作效率统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = workHouseDao.selectWorkEffByLimits(map);
			Iterator<Object> it = listSource.iterator();
			List<WorkEfficiency> listGoal = objToWorkEff(it);

			WorkEfficiency sum = sumWorkEff(listGoal);// 合计
			listGoal.add(sum);

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));

			wh.export2007Word(tempPath, listMap, contentMap, 1, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

	// 部门员工工作效率统计导出Excel
	@Override
	public ResponseEntity<byte[]> exportWorkEffByLimitsExcel(Map<String, Object> map, String path) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());

		ResponseEntity<byte[]> byteArr = null;
		try {
			ExcelHelper<WorkEfficiency> ex = new ExcelHelper<WorkEfficiency>();
			String fileName = "客房部员工工作效率统计表.xlsx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = workHouseDao.selectWorkEffByLimits(map);
			Iterator<Object> it = listSource.iterator();
			List<WorkEfficiency> listGoal = objToWorkEff(it);

			WorkEfficiency sum = sumWorkEff(listGoal);// 合计
			listGoal.add(sum);

			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			String title = "客房部员工工作效率统计表(" + startTime.substring(0, 7) + "至" + endTime.substring(0, 7) + ")";
			String[] header = { "序号", "员工姓名", "员工编号", "当班时间(分钟)", "做房时间(分钟)", "做房效率", "工作时间(分钟)", "工作效率" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, listGoal, out, "yyyy-MM-dd", -1, 0, 1);
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteArr;
	}

	/**
	 * list求和(工作效率)
	 * 
	 * @param list
	 * @return
	 */
	private WorkEfficiency sumWorkEff(List<WorkEfficiency> list) {
		WorkEfficiency sum = new WorkEfficiency();
		Iterator<WorkEfficiency> it = list.iterator();

		long sum_work_time = (long) 0;// 当班时间
		long sum_house_time = (long) 0;// 做房时间
		long sum_house_serv_time = (long) 0;// 做房+客服时间

		WorkEfficiency WorkEff = null;
		while (it.hasNext()) {
			WorkEff = it.next();
			sum_work_time += Integer.parseInt(WorkEff.getWork_time());
			sum_house_time += Integer.parseInt(WorkEff.getHouse_time());
			sum_house_serv_time += Integer.parseInt(WorkEff.getHouse_serv_time());
		}
		sum.setOrderNum("合计");
		sum.setWork_time(String.valueOf(sum_work_time));
		sum.setHouse_time(String.valueOf(sum_house_time));
		sum.setHouse_serv_time(String.valueOf(sum_house_serv_time));

		return sum;
	}

	// 部门员工工作效率分析导出
	@Override
	public ResponseEntity<byte[]> exportWorkEffAna(Map<String, Object> map, String path, String tempPath,
			String picPath) {
		DepartmentInfo departmentInfo = departmentInfoRepository.selectByDeptName("客房部");// 先查询部门id
		map.put("deptId", departmentInfo.getDepartmentId());
		String staffName = (String) map.get("staffName");
		String year = (String) map.get("checkYear");
		String quarter = (String) map.get("quarter");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WorkHouse> wh = new WordHelper<WorkHouse>();
			String fileName = "客房部员工工作效率分析.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("${staffName}", staffName);
			if (StringUtil.strIsNotEmpty(year) && StringUtil.strIsNotEmpty(quarter)) {
				String startTime = StringUtil.quarterFirstDay(year, quarter);
				String endTime = StringUtil.quarterLastDay(year, quarter);
				startTime = startTime.substring(0, 10);// 保留到天
				endTime = endTime.substring(0, 10);
				contentMap.put("${startTime}", startTime);
				contentMap.put("${endTime}", endTime);
			}

			// 图片相关
			String[] svgs = new String[2];
			svgs[0] = (String) map.get("chartSVGStr");
			svgs[1] = (String) map.get("chart1SVGStr");
			Map<String, Object> picMap = null;
			for (int i = 0; i < 2; i++) {
				if (StringUtil.strIsNotEmpty(svgs[i])) {
					picMap = PictureUtil.getPicMap(picPath, svgs[i]);
					contentMap.put("${pic" + i + "}", picMap);
				}
			}

			wh.export2007Word(tempPath, null, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

}
