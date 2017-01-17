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
 * 部门员工做房统计数据持久层实现
 * 
 * @author wangrui
 * @date 2016-12-08
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

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	/**
	 * 驳回SQL条件
	 * 
	 * @param map
	 * @return
	 */
	private String workRejectSQL(Map<String, Object> map) {
		StringBuilder sql=new  StringBuilder();
		String startTime=(String) map.get("startTime");
		String endTime=(String)map.get("endTime");
		return "";
	}

}
