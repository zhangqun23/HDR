package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.EngineerRepairDao;
import com.mvc.entity.EngineerCaseSort;
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.ProjectRepair;
import com.mvc.service.EngineerRepairService;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 工程维修项统计分析
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Service("engineerRepairServiceImpl")
public class EngineerRepairServiceImpl implements EngineerRepairService {
	@Autowired
	EngineerRepairDao engineerRepairDao;

	// 将json转换为Map
	@Override
	public Map<String, Object> JsonObjToMap(JSONObject jsonObject) {
		String starttime = null;// 开始时间
		String endtime = null;// 结束时间
		String repairtype = null;// 部门 
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
		if (jsonObject.containsKey("repairtype")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("repairtype"))) {
				repairtype = jsonObject.getString("repairtype");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("start_time",starttime);
		map.put("end_time", endtime);
		map.put("repairtype", repairtype);
		return map;
	}

	//查询工程维修项统计
	@Override
	public List<ProjectRepair> findEngineerRepair(Map<String, Object> map) {
		List<Object> listSource = engineerRepairDao.getEngineerRepairList(map);
		List<String> list=engineerRepairDao.getProjectRepairList(map);
	
		List<ProjectRepair> listGoal = listsourceToListGoal(listSource,list);
		return listGoal;
	}
	private List<ProjectRepair> listsourceToListGoal(List<Object> listSource,List<String> list){
		Iterator<Object> it = listSource.iterator();
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(int j=0;j<list.size();j++){
			map.put(list.get(j), 0);
			System.out.println(list.get(j));
		}
		List<ProjectRepair> listGoal = new ArrayList<ProjectRepair>();
		Object[] objects;
		ProjectRepair projectRepair;
		int i=0;
		while(it.hasNext()){
			i++;
			objects=(Object[]) it.next();
			projectRepair=new ProjectRepair();
			
			projectRepair.setOrderNum(String.valueOf(i));
			projectRepair.setRepairParentType(objects[3].toString());//父类型
			projectRepair.setRepairType(objects[1].toString());//子类型
			projectRepair.setServiceLoad(objects[4].toString());//数量			
			
			if(map.containsKey(objects[3].toString())){
				int m=map.get(objects[3].toString());
				map.put(objects[3].toString(), m+Integer.valueOf(objects[4].toString()));	
			}	
			listGoal.add(projectRepair);	
		}
		Iterator<ProjectRepair> itGoal = listGoal.iterator();
		projectRepair = null;
		while (itGoal.hasNext()) {
			projectRepair = itGoal.next();
			projectRepair.setEngineerAmount(map.get(projectRepair.getRepairParentType()).toString());
			
		}
		
		return listGoal;
		
	}
	/*
	 * ***********************************王慧敏报表图标*******************************
	 */
	
	
	
	
	
	
	
	/*
	 * ***********************************王慧敏报表服务类型*******************************
	 */
	//工程维修项统计服务类型
	@Override
	public List<EngineerCaseSort> findEngineerRepairType() {
		List<EngineerCaseSort> list = engineerRepairDao.getEngineerRepairTypeList();
		return list;
	}

}
