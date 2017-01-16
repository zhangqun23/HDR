package com.mvc.dao.impl;

import javax.persistence.EntityManagerFactory;

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

}
