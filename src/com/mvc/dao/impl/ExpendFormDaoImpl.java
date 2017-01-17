package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.ExpendFormDao;
import com.utils.StringUtil;

/**
 * 耗品数据持久层实现
 * 
 * @author wq
 * @date 2017-1-13
 */
@Repository("expendFormDaoImpl")
public class ExpendFormDaoImpl implements ExpendFormDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 查询布草消耗
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectlinenExpend(Map<String, Object> map) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = linenExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select a.room_no,coalesce(a.num,0),coalesce(b.num1,0),coalesce(c.num3,0),coalesce(d.num4,0),coalesce(e.num5,0),coalesce(f.num6,0), ");
		sql.append(
				" coalesce(g.num7,0),coalesce(h.num8,0),coalesce(i.num9,0),coalesce(j.num10,0),coalesce(k.num11,0),coalesce(l.num12,0),coalesce(m.num13,0),coalesce(n.num14,0) ");
		sql.append(
				" from (select room_info.room_no,sum(temp_list.num) num from room_info left join call_info on call_info.room_id=room_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=423 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as a left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num1 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id= 422 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as b on b.room_no=a.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num3 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id= 421 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as c on c.room_no=b.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num4 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=424 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as d on d.room_no=c.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num5 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=425 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as e on e.room_no=d.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num6 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=426 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as f on f.room_no=e.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num7 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=428 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as g on g.room_no=f.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num8 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=408 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as h on h.room_no=g.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num9 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and goods_info.Goods_id=410 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as i on i.room_no=h.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num10 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and goods_info.Goods_id=429 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as j on j.room_no=i.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num11 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=439 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as k on k.room_no=j.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num12 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=418 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as l on l.room_no=k.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num13 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=430 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as m on m.room_no=l.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num14 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=434 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as n on n.room_no=m.room_no  ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	// 布草统计SQL条件
	private String linenExpendSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String formType = (String) map.get("formType");
		String startTime = StringUtil.dayFirstTime((String) map.get("startTime"));
		String endTime = StringUtil.dayLastTime((String) map.get("endTime"));

		if (formType != null && !formType.equals("0")) {
			sql.append(" and case_info.clean_type='" + formType + "'");
		}
		if (startTime != null && endTime != null) {
			sql.append(" and case_info.open_time between '" + startTime + "'" + " and '" + endTime + "'");
		}
		return sql.toString();
	}
	
	//布草消耗分析
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectLinenExpendAnalyse(Map<String, Object> map) {
		
		EntityManager em = emf.createEntityManager();
		String sqlLimit = expendAnalyseSQL(map);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select goods_info.Goods_Name,sum(temp_list.num) ");
		sql.append("from temp_list left join call_info on temp_list.call_id=call_info.call_id ");
		sql.append("left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
		sql.append("left join case_info on case_info.call_id=call_info.call_id ");
		sql.append("where call_info.customer_service_flag='1'and Goods_Name is not null and ");
		sql.append("goods_info.Goods_id>'408' and goods_info.Goods_id<'434' " + sqlLimit);
		sql.append("group by temp_list.goods_id ");
		
		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		System.out.println(list);
		return list;
	}
	
	// 布草统计分析SQL条件
	private String expendAnalyseSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String startTime = StringUtil.dayFirstTime((String) map.get("startTime"));
		String endTime = StringUtil.dayLastTime((String) map.get("endTime"));

		if (startTime != null && endTime != null) {
			sql.append(" and case_info.open_time between '" + startTime + "'" + " and '" + endTime + "'");
		}
		return sql.toString();
	}


	// 查询房间耗品消耗
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectroomExpend(Map<String, Object> map) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = roomExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select a.room_no,coalesce(a.num,0),coalesce(b.num1,0),coalesce(c.num3,0),coalesce(d.num4,0),coalesce(e.num5,0),coalesce(f.num6,0), ");
		sql.append(
				" coalesce(g.num7,0),coalesce(h.num8,0),coalesce(i.num9,0),coalesce(j.num10,0),coalesce(k.num11,0),coalesce(l.num12,0),coalesce(m.num13,0),coalesce(n.num14,0), ");
		sql.append(
				"coalesce(o.num15,0),coalesce(p.num16,0),coalesce(q.num17,0),coalesce(r.num18,0),coalesce(s.num19,0),coalesce(t.num20,0),coalesce(u.num21,0),coalesce(v.num22,0),coalesce(w.num23,0), ");
		sql.append(
				"coalesce(x.num24,0),coalesce(y.num25,0),coalesce(z.num26,0),coalesce(aa.num27,0),coalesce(ab.num28,0),coalesce(ac.num29,0),coalesce(ad.num30,0), ");
		sql.append(" coalesce(ae.num31,0),coalesce(af.num32,0),coalesce(ag.num33,0),coalesce(ah.num34,0)  ");
		sql.append(
				" from (select room_info.room_no,sum(temp_list.num) num from room_info left join call_info on call_info.room_id=room_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=389 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as a left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num1 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id= 551 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as b on b.room_no=a.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num3 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id= 548 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as c on c.room_no=b.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num4 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=552 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as d on d.room_no=c.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num5 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=380 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as e on e.room_no=d.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num6 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=382 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as f on f.room_no=e.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num7 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=496 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as g on g.room_no=f.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num8 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=486 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as h on h.room_no=g.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num9 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and goods_info.Goods_id=501 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as i on i.room_no=h.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num10 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and goods_info.Goods_id=455 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as j on j.room_no=i.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num11 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=550 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as k on k.room_no=j.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num12 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=444 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as l on l.room_no=k.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num13 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=492 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as m on m.room_no=l.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num14 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=547 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as n on n.room_no=m.room_no  left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num15 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=390 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as o on o.room_no=n.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num16 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=495 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as p on p.room_no=o.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num17 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=484 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as q on q.room_no=p.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num18 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=487 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as r on r.room_no=q.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num19 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=379 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as s on s.room_no=r.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num20 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=563 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as t on t.room_no=s.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num21 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=493 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as u on u.room_no=t.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num22 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=381 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as v on v.room_no=u.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num23 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=490 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as w on w.room_no=v.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num24 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=470 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as x on x.room_no=w.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num25 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=454 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as y on y.room_no=x.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num26 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=452 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as z on z.room_no=y.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num27 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=491 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as aa on aa.room_no=z.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num28 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=549 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as ab on ab.room_no=aa.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num29 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=453 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as ac on ac.room_no=ab.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num30 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=553 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as ad on ad.room_no=ac.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num31 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=502 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as ae on ae.room_no=ad.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num32 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=388 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as af on af.room_no=ae.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num33 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=383 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as ag on ag.room_no=af.room_no left join ");
		sql.append(
				" (select room_info.room_no,sum(temp_list.num) num34 from room_info left join call_info on room_info.room_id=call_info.room_id ");
		sql.append(
				" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
		sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=485 "
				+ sqlLimit);
		sql.append(" group by room_info.room_id,temp_list.goods_id) as ah on ah.room_no=ag.room_no ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	// 房间耗品统计SQL条件
	private String roomExpendSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String formType = (String) map.get("formType");
		String startTime = StringUtil.dayFirstTime((String) map.get("startTime"));
		String endTime = StringUtil.dayLastTime((String) map.get("endTime"));

		if (formType != null && !formType.equals("0")) {
			sql.append(" and case_info.clean_type='" + formType + "'");
		}
		if (startTime != null && endTime != null) {
			sql.append(" and case_info.open_time between '" + startTime + "'" + " and '" + endTime + "'");
		}
		return sql.toString();
	}

	//房间耗品消耗分析
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectRoomExpendAnalyse(Map<String, Object> map) {
		
		EntityManager em = emf.createEntityManager();
		String sqlLimit = expendAnalyseSQL(map);
			
		StringBuilder sql = new StringBuilder();
		sql.append("select goods_info.Goods_Name,sum(temp_list.num) ");
		sql.append("from temp_list left join call_info on temp_list.call_id=call_info.call_id ");
		sql.append("left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
		sql.append("left join case_info on case_info.call_id=call_info.call_id ");
		sql.append("where call_info.customer_service_flag='1'and Goods_Name is not null and ");
		sql.append("goods_info.Goods_id in (553,444,389,390,388,379,453,551,553,548,549,550,563,547,502,491,455,452,380,454,  ");
		sql.append("501,495,383,493,487,486,484,485,492,490,382,381,496,470) " + sqlLimit);
		sql.append("group by temp_list.goods_id ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		System.out.println(list);
		return list;
	}
	
	//卫生间耗品
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectwashExpend(Map<String, Object> map) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = washExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select a.room_no,coalesce(a.num,0),coalesce(b.num1,0),coalesce(c.num3,0),coalesce(d.num4,0),coalesce(e.num5,0),coalesce(f.num6,0), ");
		sql.append(" coalesce(g.num7,0),coalesce(h.num8,0),coalesce(i.num9,0),coalesce(j.num10,0),coalesce(k.num11,0),coalesce(l.num12,0),coalesce(m.num13,0), ");
		sql.append(" coalesce(n.num14,0),coalesce(o.num15,0),coalesce(p.num16,0),coalesce(q.num17,0),coalesce(r.num18,0),coalesce(s.num19,0),coalesce(t.num20,0),coalesce(u.num21,0) ");
        sql.append(" from (select room_info.room_no,sum(temp_list.num) num from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=394 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as a left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num1 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=401 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as b on b.room_no=a.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num3 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=457 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as c on c.room_no=b.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num4 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=458 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as d on d.room_no=c.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num5 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=459 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as e on e.room_no=d.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num6 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=460 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as f on f.room_no=d.room_no left join ");
        sql.append("  (select room_info.room_no,sum(temp_list.num) num7 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=481 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as g on g.room_no=f.room_no left join ");
        sql.append("  (select room_info.room_no,sum(temp_list.num) num8 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=482 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as h on h.room_no=g.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num9 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=445 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as i on i.room_no=h.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num10 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=456 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as j on j.room_no=i.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num11 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=448 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as k on k.room_no=j.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num12 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=451 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as l on l.room_no=k.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num13 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=446 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as m on m.room_no=l.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num14 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=447 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as n on n.room_no=m.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num15 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=565 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as o on o.room_no=n.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num16 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=450 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as p on p.room_no=o.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num17 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=461 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as q on q.room_no=p.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num18 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=462 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as r on r.room_no=q.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num19 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=476 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as s on s.room_no=r.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num20 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=478 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as t on t.room_no=s.room_no left join ");
        sql.append(" (select room_info.room_no,sum(temp_list.num) num21 from room_info left join call_info on call_info.room_id=room_info.room_id ");
        sql.append(" left join temp_list on call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id left join case_info on case_info.call_id=call_info.call_id  ");
        sql.append(" where call_info.customer_service_flag=1 and Goods_Name is not null and goods_info.Goods_id=473 "+ sqlLimit);
        sql.append(" group by room_info.room_id,temp_list.goods_id) as u on u.room_no=t.room_no ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}
	
	// 卫生间耗品统计SQL条件
	private String washExpendSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String formType = (String) map.get("formType");
		String startTime = StringUtil.dayFirstTime((String) map.get("startTime"));
		String endTime = StringUtil.dayLastTime((String) map.get("endTime"));

		if (formType != null && !formType.equals("0")) {
			sql.append(" and case_info.clean_type='" + formType + "'");
		}
		if (startTime != null && endTime != null) {
			sql.append(" and case_info.open_time between '" + startTime + "'" + " and '" + endTime + "'");
		}
		return sql.toString();
	}
	
	//房间耗品消耗分析
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectWashExpendAnalyse(Map<String, Object> map) {
			
		EntityManager em = emf.createEntityManager();
		String sqlLimit = expendAnalyseSQL(map);
	
		StringBuilder sql = new StringBuilder();
		sql.append("select goods_info.Goods_Name,sum(temp_list.num) ");
		sql.append("from temp_list left join call_info on temp_list.call_id=call_info.call_id ");
		sql.append("left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
		sql.append("left join case_info on case_info.call_id=call_info.call_id ");
		sql.append("where call_info.customer_service_flag='1'and Goods_Name is not null and ");
		sql.append("goods_info.Goods_id in (394,401,457,458,459,460,481,482,445,456,448,451,446,447,565) " + sqlLimit);
		sql.append("group by temp_list.goods_id ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		System.out.println(list);
		return list;
	}

}
