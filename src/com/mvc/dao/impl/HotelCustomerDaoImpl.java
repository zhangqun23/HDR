package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.HotelCustomerDao;

/**
 * 酒店对客服务信息统计
 * @author wanghuimin
 * @date 2017年1月10日
 */
@Repository("HotelCustomerDaoImpl")
public class HotelCustomerDaoImpl implements HotelCustomerDao{
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findHotelService(Map<String, Object> map) {
		String starttime=(String) map.get("start_time");//开始时间
		String endtime=(String) map.get("end_time");//结束时间
		
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();
		String sql0="(select ca.case_id,ca.close_time,ca.depart_id,ca.use_time,ca.service_sort,ca.time_out_flag from case_info ca left join call_info  cs on ca.call_id=cs.call_id where cs.customer_service_flag=1 ) as aa";
		String sql00="(select d.department_name,count(d.department_name) depart,coalesce(sum(c.time_out_flag),0.00) timeout,coalesce(sum(c.use_time),0.00) amount_time, "
				+ "from "+sql0+" c left join department_info d on c.depart_id=d.department_id "
						+ "where c.close_time between '"+ starttime +"' and '"+ endtime +"' group by d.department_name) as bb";
		sql.append(sql00);

		
		Query query=em.createNativeQuery(sql.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
	}
	/*
	 * ***********************************王慧敏报表1*******************************
	 */
	//查询部门对客服务工作量统计
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findDepartmentLoad(Map<String, Object> map) {
		String starttime=(String) map.get("start_time");//开始时间
		String endtime=(String) map.get("end_time");//结束时间
		String depart=(String) map.get("depart");//部门
		
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();
		String sql0="(select ca.case_id,ca.author_name,ca.close_time,ca.depart_id,ca.use_time,ca.service_sort,ca.time_out_flag "
				+ "from case_info ca left join call_info  cs on ca.call_id=cs.call_id "
				+ "where cs.customer_service_flag=1 ) as aa ";
		String sql00="(select aa.case_id,aa.author_name,aa.case_author,aa.close_time,aa.depart_id,aa.use_time,aa.service_sort,aa.time_out_flag from "+ sql0 +" left join department_info d on aa.depart_id=d.department_id where d.department_name='"+ depart +"') as ww ";
		String sql1="select ww.author_name,st.Staff_no,count(ww.author_name) amount,coalesce(sum(ww.time_out_flag),0.00) timeout,coalesce(sum(ww.use_time),0.00) amount_time from "+ sql00 +" left join staff_info st on ww.case_author=st.Staff_id where ww.close_time between "+ starttime +" and "+ endtime +" group by ww.author_name ";
		sql.append(sql1);
		
		Query query=em.createNativeQuery(sql.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
	}
	
	
	/*
	 * ***********************************王慧敏报表2*******************************
	 */
	//查询部门对客服务类型统计
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findRoomType(Map<String, Object> map) {
		String starttime=(String) map.get("start_time");//开始时间
		String endtime=(String) map.get("end_time");//结束时间
		String depart=(String) map.get("depart");//部门
		
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();
		sql.append("(select ca.case_id,ca.close_time,ca.given_time,ca.depart_id,ca.use_time,ca.service_sort,ca.time_out_flag "
				+ "from case_info ca left join call_info  cs on ca.call_id=cs.call_id "
				+ "where cs.customer_service_flag=1 ) as aa ");
		String sql0=" aa.service_sort,count(aa.service_sort) amount,aa.given_time,coalesce(sum(aa.use_time),0.00) amount_time,coalesce(sum(aa.time_out_flag),0.00) timeout from "+ sql +" left join department_info d on aa.depart_id=d.department_id where d.department_name='"+ depart +"' and aa.close_time between '"+ starttime +"' and '"+ endtime +"' group by aa.service_sort ";

		
		Query query=em.createNativeQuery(sql0.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
	}

}
