package com.mvc.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.base.enums.CleanType;
import com.mvc.dao.WorkLoadDao;

/**
 * 工作量相关的dao层接口实现
 * 
 * @author zjn
 * @date 2016年12月7日
 */
@Repository("workLoadDaoImpl")
public class WorkLoadDaoImpl implements WorkLoadDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 获取员工打扫各类房间的数量列表
	@Override
	public List<Object> getWorkRecordSummary(String startTime, String endTime) {

		EntityManager em = emf.createEntityManager();
		StringBuilder selectSql = new StringBuilder();

		selectSql.append(
				"select si.Staff_no staff_no,si.Staff_name staff_name,coalesce(sum(clean_room_workload),0) clean_room, coalesce(sum(checkout_room_workload),0) checkout_room,coalesce(sum(overnight_room_workload),0)overnight_room,");
		selectSql.append(
				" coalesce(sum(actual_load),0)actual_load,coalesce(sum(beyond_load),0) beyond_load,coalesce(sum(rated_load),0) rated_load,count(record_id) work_days");
		selectSql.append(" from work_record wr ");
		selectSql.append(" left join  staff_info si on wr.staff_id=si.Staff_id");
		selectSql.append(" where wr.close_time between '" + startTime + "' and '" + endTime + "'");
		selectSql.append(" group by wr.staff_id ");

		Query query = em.createNativeQuery(selectSql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	// 获取全体员工实际总工作量
	@Override
	public Float getTotalActualWorkLoad(String startTime, String endTime) {
		EntityManager em = emf.createEntityManager();
		StringBuilder selectSql = new StringBuilder();

		selectSql.append("select coalesce(sum(actual_load),0) actual_load");
		selectSql.append(" from work_record ");
		selectSql.append(" where close_time between '" + startTime + "' and '" + endTime + "'");

		Query query = em.createNativeQuery(selectSql.toString());
		Double totalRow = (Double) query.getSingleResult();// count返回值为BigInteger类型
		em.close();
		return Float.valueOf(totalRow.toString());
	}

	// 获取某个员工每个月的实际总工作量、额定总工作量
	@SuppressWarnings("unchecked")
	@Override

	public List<Object> getMonthWorkLoad(String startTime, String endTime, Integer staffId) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();

		sql.append(
				"select DATE_FORMAT(wr.close_time,'%m') months,coalesce(sum(wr.actual_load),0) actual_load ,coalesce(sum(wr.rated_load),0) rated_load ");
		sql.append(" from work_record wr");
		sql.append(" where wr.close_time between '" + startTime + "' and '" + endTime + "'");
		sql.append(" and wr.staff_id='" + staffId + "'");
		sql.append(" group by months");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	// 获取员工总数
	@Override
	public Integer staffCount(String startTime, String endTime) {
		EntityManager em = emf.createEntityManager();
		StringBuilder selectSql = new StringBuilder();

		selectSql.append("select count(*) from (select count(record_id) from work_record ");
		selectSql.append(" where close_time between '" + startTime + "' and '" + endTime + "'");
		selectSql.append(" group by staff_id )as a");
		Query query = em.createNativeQuery(selectSql.toString());
		BigInteger totalRow = (BigInteger) query.getSingleResult();// count返回值为BigInteger类型
		em.close();
		return Integer.valueOf(totalRow.toString());
	}

	// 获取员工打扫的房间数信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getWorkRoomNumList(String startTime, String endTime) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		String serviceSort = "例行打扫";
		startTime += " 00:00:00";
		endTime += " 23:59:59";

		sql.append(
				"select p.staff_name ,p.staff_no,(CASE WHEN aa1.clean_room IS NULL THEN 0 ELSE aa1.clean_room END)clean_room, ");
		sql.append(
				" (CASE WHEN checkout_room IS NULL THEN 0 ELSE checkout_room END)checkout_room,(CASE WHEN overnight_room IS NULL THEN 0 ELSE overnight_room  END)overnight_room ");
		sql.append(" from (select si.Staff_name staff_name ,si.Staff_no staff_no,cf.service_sort,ci.clean_type from");
		sql.append(
				" case_info ci left join  staff_info si on ci.case_author=si.Staff_id left join call_info cf on ci.call_id =cf.call_id ");
		sql.append(" where cf.service_sort='" + serviceSort + "' and Staff_name is not null and ci.close_time between '"
				+ startTime + "' and '" + endTime + "' group by ci.case_author) as p");
		sql.append(
				" left join ( select si.Staff_no staff_no, count(case_id)clean_room from case_info ci left join  staff_info si on ci.case_author=si.Staff_id ");
		sql.append(" where ci.clean_type='" + CleanType.抹尘房.value + "' and ci.close_time between '" + startTime
				+ "' and '" + endTime + "' group by staff_no) as aa1 on p.staff_no=aa1.staff_no");
		sql.append(
				" left join ( select si.Staff_no staff_no, count(case_id)checkout_room from case_info ci left join  staff_info si on ci.case_author=si.Staff_id ");
		sql.append(" where ci.clean_type='" + CleanType.离退房.value + "' and ci.close_time between  '" + startTime
				+ "' and '" + endTime + "' group by staff_no) as aa2 on p.staff_no=aa2.staff_no");
		sql.append(
				" left join ( select si.Staff_no staff_no, count(case_id)overnight_room from case_info ci left join  staff_info si on ci.case_author=si.Staff_id");
		sql.append(" where  ci.clean_type='" + CleanType.过夜房.value + "' and ci.close_time between  '" + startTime
				+ "' and '" + endTime + "'  group by staff_no) as aa3 on p.staff_no=aa3.staff_no");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

}
