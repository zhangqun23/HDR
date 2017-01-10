package com.mvc.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.ReportFormDao;

/**
 * 报表公共类数据持久层实现
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
@Repository("reportFormDaoImpl")
public class ReportFormDaoImpl implements ReportFormDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

}
