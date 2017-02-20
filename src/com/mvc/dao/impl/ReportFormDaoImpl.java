package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.ReportFormDao;
import com.mvc.entity.StaffInfo;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<StaffInfo> selectStaffByDept(String department_name) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select * from staff_info s left join staff_dep_rela sdr on sdr.Staff_id=s.staff_id left join department_info di");
		sql.append(" on di.department_id=sdr.Department_id where di.department_name=:department_name");
		Query query = em.createNativeQuery(sql.toString(), StaffInfo.class);
		query.setParameter("department_name", department_name);
		List<StaffInfo> list = query.getResultList();
		em.close();
		return list;
	}

}
