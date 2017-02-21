package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EngineerRepairDao;
import com.mvc.entity.EngineerCaseSort;

/**
 * 工程维修相关接口实现
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Repository("engineerRepairDaoImpl")
public class EngineerRepairDaoImpl implements EngineerRepairDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	
	//获取工程维修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getEngineerRepairList(Map<String, Object> map){
		String starttime = null;//开始时间
		String endtime = null;//结束时间
		/*if((String)map.get("start_time")!=null){
			starttime=(String) map.get("start_time");
		}
		if((String)map.get("end_time")!=null){
			endtime=(String) map.get("end_time");
		}*/
		starttime="2016-11-1";
		endtime="2017-11-1";
		
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();
		sql.append(" select aa.sort_id,sort_name,father_id,parent_name,COALESCE(bb.amount,0) as amount from");
	    sql.append("(select sort_id,sort_name,parent_name,father_id from engineer_case_sort) as aa ");
		sql.append("left join ");
		sql.append("(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"+ starttime +"' and '"+ endtime +"' group by aa.sort_id");
		Query query=em.createNativeQuery(sql.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
		
	}
	/*
	 * ***********************************王慧敏报表服务类型*******************************
	 */
	@SuppressWarnings("unchecked")
	//工程维修项统计服务类型
	@Override
	public List<EngineerCaseSort> getEngineerRepairTypeList() {
		EntityManager em=emf.createEntityManager();
		String sql="select * from engineer_case_sort  where parent_name='所有分类' ";

		Query query=em.createNativeQuery(sql,EngineerCaseSort.class);
		List<EngineerCaseSort> list=query.getResultList();
		em.close();
		return list;
	}
	

}
