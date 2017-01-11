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

}
