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
import com.utils.StringUtil;

/**
 * 工程维修相关接口实现
 * 
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Repository("engineerRepairDaoImpl")
public class EngineerRepairDaoImpl implements EngineerRepairDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 获取工程维修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findProjectRepairList(Map<String, Object> map) {
		String start_time = (String) map.get("start_time");
		String end_time = (String) map.get("end_time");
		String repairtype = (String) map.get("repairtype");

		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append(" select aa.sort_id,sort_name,father_id,parent_name,COALESCE(bb.amount,0) as amount from");
		sql.append("(select sort_id,sort_name,parent_name,father_id from engineer_case_sort) as aa ");
		sql.append("left join ");
		if (Integer.valueOf(repairtype) == -1) {
			sql.append(
					"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
							+ start_time + "' and '" + end_time + "' and father_id<>0  order by father_id");
		} else {
			sql.append(
					"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
							+ start_time + "' and '" + end_time + "' and father_id<>0 and father_id='" + repairtype
							+ "' order by father_id");
		}
		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	// 工程维修项统计服务类型
	@Override
	public List<EngineerCaseSort> getEngineerRepairTypeList() {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from engineer_case_sort  where parent_name='所有分类' ";

		Query query = em.createNativeQuery(sql, EngineerCaseSort.class);
		List<EngineerCaseSort> list = query.getResultList();
		em.close();
		return list;
	}

	// 获取工程维修父类名称
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProjectRepairList(Map<String, Object> map) {
		String starttime = null;// 开始时间
		String endtime = null;// 结束时间
		String repairtype = (String) map.get("repairtype");
		if ((String) map.get("start_time") != null) {
			starttime = (String) map.get("start_time");
		}
		if ((String) map.get("end_time") != null) {
			endtime = (String) map.get("end_time");
		}
		if ((String) map.get("repairtype") != null) {
			repairtype = (String) map.get("repairtype");
		}

		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append(" select parent_name from");
		sql.append("(select sort_id,sort_name,parent_name,father_id from engineer_case_sort) as aa ");
		sql.append("left join ");
		if (Integer.valueOf(repairtype) == -1) {
			sql.append(
					"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
							+ starttime + "' and '" + endtime + "' and father_id<>0  order by father_id");
		} else {
			sql.append(
					"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
							+ starttime + "' and '" + endtime + "' and father_id<>0 and father_id='" + repairtype
							+ "' order by father_id");
		}
		Query query = em.createNativeQuery(sql.toString());
		List<String> list = query.getResultList();
		em.close();
		return list;
	}

	// 获取工程维修父类名称(去重)
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProjectRepairListNo(Map<String, Object> map) {
		String starttime = null;// 开始时间
		String endtime = null;// 结束时间
		String repairtype = (String) map.get("repairtype");

		if ((String) map.get("start_time") != null) {
			starttime = (String) map.get("start_time");
		}
		if ((String) map.get("end_time") != null) {
			endtime = (String) map.get("end_time");
		}
		if ((String) map.get("repairtype") != null) {
			repairtype = (String) map.get("repairtype");
		}
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct parent_name from");
		sql.append("(select sort_id,sort_name,parent_name,father_id from engineer_case_sort) as aa ");
		sql.append("left join ");
		if (Integer.valueOf(repairtype) == -1) {
			sql.append(
					"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
							+ starttime + "' and '" + endtime + "' and father_id<>0  order by father_id");
		} else {
			sql.append(
					"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
							+ starttime + "' and '" + endtime + "' and father_id<>0 and father_id='" + repairtype
							+ "' order by father_id");
		}
		Query query = em.createNativeQuery(sql.toString());
		List<String> list = query.getResultList();
		em.close();
		return list;
	}

	/*
	 * ***********************************王慧敏报表图标*******************************
	 */
	// 导出图标
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getProjectRepairIcon(Map<String, Object> dateMap) {
		String startTime = (String) dateMap.get("start_time");
		String endTime = (String) dateMap.get("end_time");

		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		if (dateMap.containsKey("repairType")) {
			if (StringUtil.strIsNotEmpty(dateMap.get("repairType").toString())) {
				String repairType = (String) dateMap.get("repairType");
				if (repairType.equals(-1)) {
					sql.append(" select aa.sort_id,sort_name,father_id,parent_name,coalesce(sum(bb.amount),0) as AMOUNT from");
					sql.append("(select sort_id,sort_name,parent_name,father_id from engineer_case_sort) as aa ");
					sql.append("left join ");
					sql.append(
							"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
									+ startTime + "' and '" + endTime + "' and aa.father_id<>0 order by aa.father_id");
				}else{
					sql.append(" select aa.sort_id,sort_name,father_id,parent_name,COALESCE(bb.amount,0) as amount from");
					sql.append("(select sort_id,sort_name,parent_name,father_id from engineer_case_sort) as aa ");
					sql.append("left join ");
					sql.append(
							"(select sort_id,close_time,count(1) as amount from engineer_info GROUP by sort_id) as bb on aa.sort_id=bb.sort_id  where close_time between '"
									+ startTime + "' and '" + endTime + "' and aa.father_id<>0 and father_id='" + repairType
									+ "' order by aa.father_id");
				}
				}
		}
		
		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;

	}

}
