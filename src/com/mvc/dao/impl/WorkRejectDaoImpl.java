package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.WorkRejectDao;

/**
 * 部门员工做房驳回统计数据持久层实现
 * 
 * @author zq
 * @date 2017-1-17
 */
@Repository("workRejectDaoImpl")
public class WorkRejectDaoImpl implements WorkRejectDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectWorkRejectByLimits(Map<String, Object> map) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = workRejectSQL(map);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select a.Staff_name,a.Staff_no,coalesce(b.mochen_num,0) mochen_num,coalesce(b.mochen_reject,0) mochen_reject, ");
		sql.append(
				" coalesce(c.guoye_num,0) guoye_num,coalesce(c.guoye_reject,0) guoye_reject,coalesce(d.litui_num,0) litui_num,coalesce(d.litui_reject,0) litui_reject from  ");
		sql.append(
				"  (select s.Staff_no,s.Staff_name from case_info cs left join staff_info s on s.Staff_id=cs.case_author  ");
		sql.append("  where s.Staff_no is not null  " + sqlLimit + " group by s.Staff_no) as a   ");
		sql.append(
				"  left join (select s.Staff_no,count(1) mochen_num,sum(cs.is_back) mochen_reject from case_info cs  ");
		sql.append(
				"  left join staff_info s on s.Staff_id=cs.case_author left join call_info cl on cl.call_id=cs.call_id  ");
		sql.append("  left join service_items si on si.service_name=cl.service_sort   ");
		sql.append("  where clean_type=0 and si.parent_name='计划任务'  " + sqlLimit
				+ " group by s.Staff_no) as b on b.Staff_no=a.Staff_no   ");
		sql.append(
				"  left join (select s.Staff_no,count(1) guoye_num,sum(cs.is_back) guoye_reject from case_info cs left join staff_info s on s.Staff_id=cs.case_author  ");
		sql.append(
				"  left join call_info cl on cl.call_id=cs.call_id left join service_items si on si.service_name=cl.service_sort  ");
		sql.append("  where clean_type=1 and si.parent_name='计划任务' " + sqlLimit
				+ "  group by s.Staff_no) as c on c.Staff_no=a.Staff_no  ");
		sql.append(
				"  left join (select s.Staff_no,count(1) litui_num,sum(cs.is_back) litui_reject from case_info cs left join staff_info s on s.Staff_id=cs.case_author   ");
		sql.append(
				"  left join call_info cl on cl.call_id=cs.call_id left join service_items si on si.service_name=cl.service_sort  ");
		sql.append("  where clean_type=2 and si.parent_name='计划任务' " + sqlLimit
				+ "  group by s.Staff_no)  as d on d.Staff_no=a.Staff_no;  ");
		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	/**
	 * 驳回统计SQL条件
	 * 
	 * @param map
	 * @return
	 */
	private String workRejectSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		String deptId = (String) map.get("deptId");
		if (startTime != null) {
			sql.append(" and cs.close_time between '" + startTime + "'" + " and '" + endTime + "' ");
		}
		if (deptId != null) {
			sql.append(" and cs.depart_id='" + deptId + "' ");
		}
		return sql.toString();
	}
//获取单个人的驳回率
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectMonthWorkReject(Map<String, Object> map) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = userWorkRejectSQL(map);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select DATE_FORMAT(cs.close_time,'%m') months,count(1) caseSum,sum(case when cs.is_back > 0 then 1 else 0 end) backSum");
		sql.append(
				"  from case_info cs left join call_info cl on cl.call_id=cs.call_id left join service_items si on si.service_name=cl.service_sort");
		sql.append("  where si.parent_name='计划任务' " + sqlLimit + " group by months");
		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	// zq驳回折线图分析查询条件
	private String userWorkRejectSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		String staffId = (String) map.get("staffId");
		String cleanType = (String) map.get("cleanType");
		if (startTime != null) {
			sql.append("  and cs.close_time between '" + startTime + "'" + " and '" + endTime + "'");
		}

		if (staffId != null) {
			sql.append("  and cs.case_author= '" + staffId + "'");
		}
		if (cleanType != null) {
			sql.append("  and cs.clean_type= '" + cleanType + "'");
		}
		return sql.toString();
	}

}
