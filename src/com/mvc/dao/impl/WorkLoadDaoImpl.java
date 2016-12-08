/**
 * 
 */
package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.WorkLoadDao;
import com.mvc.entity.CallInfo;

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

	public List<CallInfo> count() {
		EntityManager em = emf.createEntityManager();
		String countSql = " select * from call_info ";
		Query query = em.createNativeQuery(countSql, CallInfo.class);
		@SuppressWarnings("unchecked")
		List<CallInfo> list = query.getResultList();
		em.close();
		return list;
	}

}
