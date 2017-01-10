package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.WorkHouseDao;
import com.utils.Pager;

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
	public List<Object> selectWorkHouse(Map<String, Object> map, Pager pager) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = workHouseSQL(map);
		String sqlPage = pageSQL(pager);

		StringBuilder sql = new StringBuilder();
		sql.append("select a.Staff_name,a.Staff_no,coalesce(b.mochen,0),coalesce(c.guoye,0),coalesce(d.litui,0) from ");
		sql.append(
				"(select distinct(s.Staff_no),s.Staff_name from case_info c left join staff_info s on s.Staff_id=c.case_author");
		sql.append(" where 1=1 " + sqlLimit + " group by s.Staff_no) as a left join ");
		sql.append(
				"(select s.Staff_no,sum(c.use_time) mochen from case_info c left join staff_info s on s.Staff_id=c.case_author");
		sql.append(" where 1=1 " + sqlLimit + " group by s.Staff_no) as b on b.Staff_no=a.Staff_no left join ");
		sql.append(
				"(select s.Staff_no,sum(c.use_time) guoye from case_info c left join staff_info s on s.Staff_id=c.case_author");
		sql.append(" where 1=1 " + sqlLimit + " group by s.Staff_no) as c on c.Staff_no=a.Staff_no left join ");
		sql.append(
				"(select s.Staff_no,sum(c.use_time) litui from case_info c left join staff_info s on s.Staff_id=c.case_author");
		sql.append(" where 1=1 " + sqlLimit + " group by s.Staff_no) as d on d.Staff_no=a.Staff_no left join ");
		sql.append(sqlPage);
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
	private String workHouseSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		Integer roomType = (Integer) map.get("roomType");
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");

		if (roomType != null) {
			// sql.append(" and ?=" + roomType);
		}
		if (startTime != null && endTime != null) {
			sql.append(" and c.open_time between '" + startTime + "'" + " and'" + endTime + "'");
		}
		return sql.toString();
	}

	/**
	 * 分页SQL条件
	 * 
	 * @param pager
	 * @return
	 */
	private String pageSQL(Pager pager) {
		String str = "";
		Integer offset = null;
		Integer end = null;
		if (pager != null) {
			offset = pager.getOffset();
			end = pager.getPageSize();
		}
		if (offset != null && end != null) {
			str = " limit " + offset + "," + end;
		}
		return str;
	}

}
