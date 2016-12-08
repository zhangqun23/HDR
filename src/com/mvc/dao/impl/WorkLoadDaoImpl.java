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

import com.mchange.util.ObjectCache;
import com.mvc.dao.WorkLoadDao;
import com.mvc.entity.GoodsInfo;
import com.mvc.entity.RoomInfo;

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

	public List<RoomInfo> count() {
		EntityManager em = emf.createEntityManager();
		String countSql = " select * from room_info ";
		Query query = em.createNativeQuery(countSql, RoomInfo.class);
		@SuppressWarnings("unchecked")
		List<RoomInfo> list = query.getResultList();
		em.close();
		return list;
	}
	//huiminjun试验
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsInfo> count0() {
		EntityManager em = emf.createEntityManager();
		String countSql = " select * from goods_info ";
		Query query = em.createNativeQuery(countSql,GoodsInfo.class);
		List<GoodsInfo> list = query.getResultList();
		em.close();
		return list;
	}

}
