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

	public Integer count() {
		EntityManager em = emf.createEntityManager();
		String countSql = " select count(*) from room_sort ";
		Query query = em.createNativeQuery(countSql);
		@SuppressWarnings("unchecked")
		List<Object> totalRow = query.getResultList();
		em.close();
		return Integer.parseInt(totalRow.get(0).toString());
	}

}
