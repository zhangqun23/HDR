package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.CheckOrRobDao;

@Repository("checkOrRobDaoImpl")
public class CheckOrRobDaoImpl implements CheckOrRobDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@Override
	public List<Object> selectRobEfficiency(Map<String, Object> map) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = robEfficiencySQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("staff_info.staff_id,");
		sql.append("staff_info.Staff_name,");
		sql.append("staff_info.staff_no,");
		sql.append("sum(case_info.use_time) AS use_time,");
		sql.append("sum(case_info.given_time)/count(1) as given_time_avg,");
		sql.append("count(1) AS work_count,");
		sql.append("COALESCE (a.back_num, 0) AS back_num,");
		sql.append("COALESCE (b.out_time_num, 0) AS out_time_num ");
		sql.append(" sum(case_info.use_time/case_info.given_time)/count(1) as rob_effeciency_avg ");
		sql.append("FROM case_info ");
		sql.append("LEFT JOIN call_info ON case_info.call_id = call_info.call_id ");
		sql.append("LEFT JOIN staff_info ON staff_info.staff_id = case_info.case_author ");

		sql.append("LEFT JOIN (");
		sql.append("SELECT ");
		sql.append("staff_info.staff_id,");
		sql.append("count(1) AS back_num ");
		sql.append("FROM case_info ");
		sql.append("LEFT JOIN call_info ON case_info.call_id = call_info.call_id ");
		sql.append("LEFT JOIN staff_info ON staff_info.staff_id = case_info.case_author ");
		sql.append("WHERE ");
		sql.append(" case_info.is_back > 0 ");
		sql.append("AND call_info.service_sort = '抢房处理'" );
		sql.append(sqlLimit);
		sql.append(" GROUP BY case_info.case_author ");
		sql.append(") AS a ON a.staff_id = staff_info.staff_id ");

		sql.append("LEFT JOIN (");
		sql.append("SELECT ");
		sql.append("staff_info.staff_id,");
		sql.append("count(1) AS out_time_num ");
		sql.append("FROM case_info ");
		sql.append("LEFT JOIN call_info ON case_info.call_id = call_info.call_id ");
		sql.append("LEFT JOIN staff_info ON staff_info.staff_id = case_info.case_author ");
		sql.append("WHERE ");
		sql.append(" case_info.flag > 0 ");
		sql.append("AND call_info.service_sort = '抢房处理'" );
		sql.append(sqlLimit);
		sql.append(" GROUP BY case_info.case_author ");
		sql.append(") AS b ON b.staff_id = staff_info.staff_id ");

		sql.append("WHERE ");
		sql.append(" call_info.service_sort = '抢房处理'" );
		sql.append(sqlLimit);
		sql.append(" GROUP BY case_info.case_author");

		Query query = em.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	/**
	 * 抢房工作效率统计表
	 * 
	 * @param map
	 * @return
	 */
	private String robEfficiencySQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String roomType = (String) map.get("roomType");
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");

		if (roomType != null) {
			sql.append(" and case_info.sort_no='" + roomType + "' ");
		}
		if (startTime != null && endTime != null) {
			sql.append(" and case_info.open_time between '" + startTime + "'" + " and '" + endTime + "' ");
		}

		return sql.toString();
	}
}
