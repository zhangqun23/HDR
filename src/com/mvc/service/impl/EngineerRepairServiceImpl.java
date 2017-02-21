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
		//List<String> list=engineerRepairDao.getEngineerRepairList(map);
	
		List<ProjectRepair> listGoal = listsourceToListGoal(listSource);
		return null;
	}
	private List<ProjectRepair> listsourceToListGoal(List<Object> listSource){
		Iterator<Object> it = listSource.iterator();
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
			
			Map<String, Integer> map=new HashMap<String, Integer>();
			for(int j=0;j<listSource.size();j++){
				map.put( listSource.get(j).toString(), 0);
				System.out.println(listSource.get(j).toString());
			}
			String amount;
			while(it.hasNext()){
				Object[] obj=(Object[]) it.next();
				if(projectRepair.getRepairParentType().equals(objects[3].toString())){
					amount=StringUtil.add(projectRepair.getRepairParentType(), objects[4].toString());
				}
				
			}
			
		}
		return null;
		
	}
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
