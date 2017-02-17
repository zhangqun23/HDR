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
import com.utils.Pager;

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
		sql.append("COALESCE (b.out_time_num, 0) AS out_time_num, ");
		sql.append(" sum(case_info.given_time/case_info.use_time)/count(1) as rob_effeciency_avg ");
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
		sql.append("AND call_info.service_sort = '抢房处理'");
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
		sql.append("AND call_info.service_sort = '抢房处理'");
		sql.append(sqlLimit);
		sql.append(" GROUP BY case_info.case_author ");
		sql.append(") AS b ON b.staff_id = staff_info.staff_id ");

		sql.append("WHERE ");
		sql.append(" call_info.service_sort = '抢房处理'");
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
	/**
	 * 抢房工作效率统计表
	 * 
	 * @param map
	 * @return
	 */
	private String robDetailSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String roomType = (String) map.get("roomType");
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");

		if (roomType != null) {
			sql.append(" and ci.sort_no='" + roomType + "' ");
		}
		if (startTime != null && endTime != null) {
			sql.append(" and ci.open_time between '" + startTime + "'" + " and '" + endTime + "' ");
		}

		return sql.toString();
	}
	@Override
	public List<Object> selectRobDetailByPage(Map<String, Object> map, Pager pager) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = robDetailSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("room_info.room_no,");
		sql.append("ci.use_time,");
		sql.append("ci.given_time,");
		sql.append("staff_info.Staff_name,");
		sql.append("ci.is_back,");
		sql.append("check_case.use_time AS check_used_time,");
		sql.append("checkAuthor.staff_name AS checker_name ");
		sql.append("FROM ");
		sql.append("case_info ci ");
		sql.append("LEFT JOIN call_info ON call_info.call_id = ci.call_id ");
		sql.append(" INNER JOIN check_case ON check_case.case_id = ci.case_id ");
		sql.append("AND check_case.create_time = ( ");
		sql.append("SELECT ");
		sql.append("max(create_time) ");
		sql.append("FROM ");
		sql.append("check_case ");
		sql.append("WHERE ");
		sql.append("case_id = ci.case_id ");
		sql.append("LIMIT 1 ) ");
		sql.append("LEFT JOIN staff_info ON staff_info.staff_id = ci.case_author ");
		sql.append("LEFT JOIN room_info ON room_info.room_id = call_info.room_id ");
		sql.append("LEFT JOIN staff_info checkAuthor ON checkAuthor.staff_id = check_case.author_id ");
		sql.append("WHERE ");
		sql.append("ci.case_states='关闭' ");
		sql.append("AND call_info.service_sort = '抢房处理' ");
		sql.append(sqlLimit);
		sql.append(" limit ");
		sql.append(pager.getOffset() + "," + pager.getPageSize());

		Query query = em.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public int getTotalRowCountRobDetail(Map<String, Object> map) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = robDetailSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("count(1) ");
		sql.append("FROM ");
		sql.append("case_info ci ");
		sql.append("LEFT JOIN call_info ON call_info.call_id = ci.call_id ");
		sql.append(" INNER JOIN check_case ON check_case.case_id = ci.case_id ");
		sql.append("AND check_case.create_time = ( ");
		sql.append("SELECT ");
		sql.append("max(create_time) ");
		sql.append("FROM ");
		sql.append("check_case ");
		sql.append("WHERE ");
		sql.append("case_id = ci.case_id ");
		sql.append("LIMIT 1 ) ");
		sql.append("LEFT JOIN staff_info ON staff_info.staff_id = ci.case_author ");
		sql.append("LEFT JOIN room_info ON room_info.room_id = call_info.room_id ");
		sql.append("LEFT JOIN staff_info checkAuthor ON checkAuthor.staff_id = check_case.author_id ");
		sql.append("WHERE ");
		sql.append("ci.case_states='关闭' ");
		sql.append("AND call_info.service_sort = '抢房处理' ");
		sql.append(sqlLimit);

		Query query = em.createNativeQuery(sql.toString());
		int totalRow = Integer.parseInt(query.getSingleResult().toString());
		em.close();
		return totalRow;
	}

	@Override
	public List<Object> avgPerMonthsStaff(String startTime, String endTime, String staffId, String roomType) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("sum(use_time) useTime, ");
		sql.append("DATE_FORMAT(close_time, '%m') AS months, ");
		sql.append("count(1) AS caseNum ");
		sql.append("FROM ");
		sql.append("case_info ");
		sql.append("INNER JOIN call_info ON call_info.call_id = case_info.call_id ");
		sql.append("AND call_info.service_sort = '抢房处理' ");
		sql.append("WHERE ");
		sql.append("close_time BETWEEN '");
		sql.append(startTime);
		sql.append("' AND '");
		sql.append(endTime);
		sql.append("' ");
		sql.append(" AND case_author = '");
		sql.append(staffId);
		sql.append("' ");
		sql.append("AND case_info.sort_no = '");
		sql.append(roomType);
		sql.append("' ");
		sql.append("GROUP BY months ");

		Query query = em.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public String allAverWorkEfficiency(String startTime, String endTime, String roomType) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("coalesce(sum(use_time)/count(1),0) AS avgUstTime ");
		sql.append("FROM ");
		sql.append("case_info ");
		sql.append("INNER JOIN call_info ON call_info.call_id = case_info.call_id ");
		sql.append("AND call_info.service_sort = '抢房处理' ");
		sql.append("WHERE ");
		sql.append("close_time BETWEEN '");
		sql.append(startTime);
		sql.append("' AND '");
		sql.append(endTime);
		sql.append("' ");
		sql.append("AND case_info.sort_no = '");
		sql.append(roomType);
		sql.append("' ");

		Query query = em.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		String str = query.getSingleResult().toString();
		em.close();
		return str;
	}

	@Override
	public String averWorkEfficiency(String startTime, String endTime, String roomType, String staffId) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("coalesce(sum(use_time)/count(1),0) AS avgUstTime ");
		sql.append("FROM ");
		sql.append("case_info ");
		sql.append("INNER JOIN call_info ON call_info.call_id = case_info.call_id ");
		sql.append("AND call_info.service_sort = '抢房处理' ");
		sql.append("WHERE ");
		sql.append("close_time BETWEEN '");
		sql.append(startTime);
		sql.append("' AND '");
		sql.append(endTime);
		sql.append("' ");
		sql.append("AND case_info.sort_no = '");
		sql.append(roomType);
		sql.append("' ");
		sql.append("AND case_info.case_author=");
		sql.append(staffId);

		Query query = em.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		String str = query.getSingleResult().toString();
		em.close();
		return str;
	}

	@Override
	public List<Object> selectRobDetail(Map<String, Object> map) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = robDetailSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("room_info.room_no,");
		sql.append("ci.use_time,");
		sql.append("ci.given_time,");
		sql.append("staff_info.Staff_name,");
		sql.append("ci.is_back,");
		sql.append("check_case.use_time AS check_used_time,");
		sql.append("checkAuthor.staff_name AS checker_name ");
		sql.append("FROM ");
		sql.append("case_info ci ");
		sql.append("LEFT JOIN call_info ON call_info.call_id = ci.call_id ");
		sql.append(" INNER JOIN check_case ON check_case.case_id = ci.case_id ");
		sql.append("AND check_case.create_time = ( ");
		sql.append("SELECT ");
		sql.append("max(create_time) ");
		sql.append("FROM ");
		sql.append("check_case ");
		sql.append("WHERE ");
		sql.append("case_id = ci.case_id ");
		sql.append("LIMIT 1 ) ");
		sql.append("LEFT JOIN staff_info ON staff_info.staff_id = ci.case_author ");
		sql.append("LEFT JOIN room_info ON room_info.room_id = call_info.room_id ");
		sql.append("LEFT JOIN staff_info checkAuthor ON checkAuthor.staff_id = check_case.author_id ");
		sql.append("WHERE ");
		sql.append("ci.case_states='关闭' ");
		sql.append(sqlLimit);

		Query query = em.createNativeQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

}
