package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EngineerRepairDao;

/**
 * 工程维修相关接口实现
 * @author wanghuimin
 * @date 2017年2月20日
 */
@Repository("engineerRepairDaoImpl")
public class EngineerRepairDaoImpl implements EngineerRepairDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	
	//获取工程维修列表
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getEngineerRepairList(Map<String, Object> map){
		
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();
		
		
		
		
		Query query=em.createNativeQuery(sql.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
		
	}
	

}
