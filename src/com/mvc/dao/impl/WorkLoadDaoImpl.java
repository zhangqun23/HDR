package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
		startTime = startTime + " 00:00:00";
		endTime = endTime + " 23:59:59";

		selectSql.append(
				"select staff_no,staff_name,coalesce(sum(clean_room_workload),0) clean_room, coalesce(sum(checkout_room_workload),0) checkout_room,coalesce(sum(overnight_room_workload),0)overnight_room,coalesce(sum(actual_load),0)actual_load,coalesce(sum(beyond_load),0) beyond_load");
		selectSql.append(" from work_record ");
		selectSql.append(" where close_time between '" + startTime + "' and '" + endTime + "'");
		selectSql.append(" group by staff_id ");
		System.out.println("selectSql:" + selectSql);

		Query query = em.createNativeQuery(selectSql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	// 获取员工打扫各类房间的数量列表
	@Override
	public List<Object> getRoomNumByPrame(String startTime, String endTime) {
		EntityManager em = emf.createEntityManager();
		StringBuilder selectSql = new StringBuilder();
		String serviceSort = "  a.service_sort='例行打扫' ";
		selectSql.append(" select p.staff_name,p.staff_id,aa1.clean_num ,aa2.checkout_num ,aa3.overnight_num ");
		selectSql.append(
				" from (select b.staff_name,b.staff_id from case_info a left join staff_info b on a.case_author=b.Staff_id where ");
		selectSql.append(serviceSort);
		if (startTime != null && endTime != null) {
			selectSql.append(" and (a.close_time between '" + startTime + "' and '" + endTime + "') ");
		}
		selectSql.append(" group by  staff_id) as p ");

		selectSql.append(
				" left join (select case_author, coalesce(sum(actual_workload),0) clean_num from case_info a where a.clean_type=0 and ");
		selectSql.append(serviceSort);
		if (startTime != null && endTime != null) {
			selectSql.append(" and (a.close_time between '" + startTime + "' and '" + endTime + "') ");
		}
		selectSql.append(" group by case_author) as aa1 on p.staff_id=aa1.case_author ");

		selectSql.append(
				" left join (select case_author,coalesce(sum(actual_workload),0) checkout_num  from case_info a where a.clean_type=1 and ");
		selectSql.append(serviceSort);
		if (startTime != null && endTime != null) {
			selectSql.append(" and (a.close_time between '" + startTime + "' and '" + endTime + "') ");
		}
		selectSql.append(" group by case_author) as aa2 on p.staff_id=aa2.case_author");

		selectSql.append(
				" left join (select case_author, coalesce(sum(actual_workload),0) overnight_num from case_info a where a.clean_type=2 and ");
		selectSql.append(serviceSort);
		if (startTime != null && endTime != null) {
			selectSql.append(" and (a.close_time between '" + startTime + "' and '" + endTime + "') ");
		}
		selectSql.append(" group by case_author) as aa3 on p.staff_id=aa3.case_author ");

		System.out.println("selectSql:" + selectSql);
		Query query = em.createNativeQuery(selectSql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

}
