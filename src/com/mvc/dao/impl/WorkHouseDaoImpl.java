package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.WorkHouseDao;
import com.mvc.entity.CaseInfo;

/**
 * 部门员工做房统计数据持久层实现
 * 
 * @author wangrui
 * @date 2016-12-08
 */
@Repository("workHouseDaoImpl")
public class WorkHouseDaoImpl implements WorkHouseDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 查询员工做房
	@SuppressWarnings("unchecked")
	@Override
	public List<CaseInfo> selectWorkHouse() {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from case_info cs where case_id='CA20140101003'");
		Query query = em.createNativeQuery(sql.toString(), CaseInfo.class);
		List<CaseInfo> list = query.getResultList();
		em.close();
		return list;
	}

}
