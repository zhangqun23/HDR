package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mvc.dao.EngineerRepairDao;
import com.mvc.entity.EngineerCaseSort;
import com.mvc.entityReport.HoCustomerService;
import com.mvc.entityReport.HouseCustomerServiceLoad;
import com.mvc.entityReport.ProjectRepair;
import com.mvc.service.EngineerRepairService;
import com.utils.CollectionUtil;
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
	public String findEngineerRepair(Map<String, Object> map) {
		List<Object> listSource = engineerRepairDao.getEngineerRepairList(map);
		List<String> list=engineerRepairDao.getProjectRepairList(map);//父名称可能是重复的
	
		String listGoal = listsourceToListGoal(listSource,list,map);
		return listGoal;
	}
	private String listsourceToListGoal(List<Object> listSource,List<String> list,Map<String, Object> mAp){
		String starttime = null;//开始时间
		String endtime = null;//结束时间
		if((String)mAp.get("start_time")!=null){
			starttime=(String) mAp.get("start_time");
		}
		if((String)mAp.get("end_time")!=null){
			endtime=(String) mAp.get("end_time");
		}
		Iterator<Object> it = listSource.iterator();
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(int j=0;j<list.size();j++){
			map.put(list.get(j), 0);
			System.out.println(list.get(j));
		}
		
		List<ProjectRepair> listGoal = new ArrayList<ProjectRepair>();
		Object[] objects;
		ProjectRepair projectRepair;	
		String amount = null;
	
		while(it.hasNext()){
			objects=(Object[]) it.next();
			projectRepair=new ProjectRepair();	
			projectRepair.setRepairParentType(objects[3].toString());//父类型
			projectRepair.setRepairType(objects[1].toString());//子类型
			projectRepair.setServiceLoad(objects[4].toString());//数量	
			
			amount=StringUtil.add(amount, objects[4].toString());
			
			if(map.containsKey(objects[3].toString())){
				int m=map.get(objects[3].toString());
				map.put(objects[3].toString(), m+Integer.valueOf(objects[4].toString()));	
			}	
			listGoal.add(projectRepair);	
		}
		//总计
		Iterator<ProjectRepair> itGoal = listGoal.iterator();
		projectRepair = null;
		int i=0;
		while (itGoal.hasNext()) {
			i++;
			projectRepair = itGoal.next();
			projectRepair.setOrderNum(String.valueOf(i));
			projectRepair.setEngineerAmount(map.get(projectRepair.getRepairParentType()).toString());
					
		}
		
		sortAndWriteW(listGoal, "serviceLoad", false);// 数量排名
		Iterator<ProjectRepair> itGoalRange = listGoal.iterator();
		projectRepair = null;
		int w=0;
		String analyseResult0;
		if(i<=10){
			analyseResult0="排名前"+i+"的维修项包括：" ;
			while (itGoalRange.hasNext()) {
				w++;
				projectRepair = itGoalRange.next();
				if(w<i){
					analyseResult0+=projectRepair.getRepairType()+"("+projectRepair.getServiceLoad()+")，";
				}
				else{
					analyseResult0+=projectRepair.getRepairType()+"("+projectRepair.getServiceLoad()+")。";
				}	
			}
		}
		else{
			analyseResult0="排名前十的维修项包括：" ;
			while (itGoalRange.hasNext() && w<10) {
				w++;	
				projectRepair = itGoalRange.next();
				analyseResult0+=projectRepair.getRepairType()+"("+projectRepair.getServiceLoad()+")，";
				if(w==9){
					analyseResult0+=projectRepair.getRepairType()+"("+projectRepair.getServiceLoad()+")。";
				}
				
			}
		}
			
		//字符串
		String analyseResult ="从"+starttime+"至"+endtime+"共产生报修项"+amount+"项。其中：";
		List<String> list1=engineerRepairDao.getProjectRepairListNo(mAp);//父名称可能是重复的
		for(int j=0;j<list1.size();j++){
			analyseResult+=list1.get(j)+"("+map.get(list1.get(j)+"项"+")");
		}		
		analyseResult+=analyseResult0;
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("list", listGoal);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();
		
	}
	/**
	 * 排序
	 * 
	 * @param list
	 * @param filedName
	 *            按指定字段排序
	 * @param ascFlag
	 *            true升序,false降序
	 */
	private void sortAndWriteW(List<ProjectRepair> list, String filedName, boolean ascFlag) {
		CollectionUtil.sort(list, filedName, ascFlag);
	}

	/*
	 * ***********************************王慧敏报表图标*******************************
	 */
	//工程报修图标
	@Override
	public String getProjectRepairIcon(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
	/*public List<ProjectRepair> findProjectIcon(Map<String, Object> map) {
		List<Object> listSource = engineerRepairDao.getEngineerRepairList(map);
	
		List<ProjectRepair> listGoal = listsourceToListGoalIcon(listSource);
		return listGoal;
	}*/
	
	private List<ProjectRepair> listsourceToListGoalIcon(List<Object> listSource){
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
			
				
			listGoal.add(projectRepair);	
		}
		
		
		return listGoal;
		
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
