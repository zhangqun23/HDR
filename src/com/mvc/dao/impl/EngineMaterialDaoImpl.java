package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EngineMaterialDao;

/**
 * 工程物料管理数据持久层实现
 * 
 * @author wangrui
 * @date 2017年2月20日
 */
@Repository("engineMaterialDaoImpl")
public class EngineMaterialDaoImpl implements EngineMaterialDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 查询工程物料
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectEngineMaterial(Map<String, Object> map) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = engineMatSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select msf.fsort_name,mh.material_name,mh.material_type,a.take_amount,mh.material_unit,a.task_num from ");
		sql.append("(select mcr.material_id,mcr.take_amount,mcr.out_time,count(case_id) as task_num ");
		sql.append("from material_case_rela mcr where mcr.is_deleted=0 group by mcr.material_id) a ");
		sql.append("left join material_house mh on mh.material_id=a.material_id ");
		sql.append("left join material_sort_finance msf on msf.fsort_id=mh.fsort_id ");
		sql.append("where mh.Is_deleted=0 and msf.is_deleted=0 " + sqlLimit);
		sql.append(" group by mh.material_name order by msf.fsort_id");
		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	/**
	 * 员工做房SQL条件
	 * 
	 * @param map
	 * @return
	 */
	private String engineMatSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");

		if (startTime != null && endTime != null) {
			sql.append(" and a.out_time between '" + startTime + "'" + " and '" + endTime + "'");
		}

		return sql.toString();
	}

}
