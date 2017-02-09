package com.mvc.dao.impl;

import java.math.BigInteger;
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

	// 布草消耗分页
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectlinenPage(Map<String, Object> map, Integer offset, Integer end,
			List<Integer> listCondition) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = linenExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select a.room_no," + getSelSQL(listCondition) + " from ");
		sql.append(getSizeSQL(listCondition, sqlLimit));
		sql.append(" limit :offset,:end");
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("offset", offset).setParameter("end", end);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	/**
	 * 获取查询条件SQL
	 * 
	 * @param list
	 * @return
	 */
	private String getSelSQL(List<Integer> list) {
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sql.append("coalesce(a" + i + ".num,0),");
		}
		return sql.toString().substring(0, sql.toString().length() - 1);
	}

	/**
	 * 获取查询布草字段SQL
	 * 
	 * @param list
	 * @param sqlLimit
	 * @return
	 */
	private String getSizeSQL(List<Integer> list, String sqlLimit) {
		StringBuilder sql = new StringBuilder();
		String sqlStr = null;
		if (list != null) {
			Integer size = list.size();
			for (int i = 0; i < size; i++) {
				if (i == 0) {
					sql.append("(select distinct temp_list.room_no from temp_list left join goods_info on goods_info.goods_id=temp_list.goods_id ");
					sql.append(" left join case_info on case_info.call_id=temp_list.call_id ");
					sql.append(" where goods_info.Display=1 and goods_info.Goods_Typeid='dt0303' " + sqlLimit + " order by temp_list.room_no asc ) as a ");
					sql.append(" left join (select call_info.room_id,sum(temp_list.num) num from call_info left join case_info ");
					sql.append(
							" on case_info.call_id=call_info.call_id left join temp_list on temp_list.call_id=call_info.call_id");
					sql.append(" left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
					sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and ");
					sql.append(" goods_info.goods_id=" + list.get(i) + sqlLimit);
					sql.append(" group by call_info.room_id) as a0 ");
					sql.append(" on a.room_no=a0.room_id left join ");
				} else {
					sql.append("(select call_info.room_id,sum(temp_list.num) num from call_info left join case_info ");
					sql.append(
							" on case_info.call_id=call_info.call_id left join temp_list on temp_list.call_id=call_info.call_id");
					sql.append(" left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
					sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and ");
					sql.append(" goods_info.goods_id=" + list.get(i) + sqlLimit);
					sql.append(" group by call_info.room_id) as a" + i);
					sql.append(" on a" + i + ".room_id=a.room_no left join ");
				}
			}
			sqlStr = sql.toString().substring(0, sql.toString().length() - 10);
		}
		return sqlStr;
	}

	// 查询布草消耗
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectlinenExpend(Map<String, Object> map, List<Integer> listCondition) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = linenExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select a.room_no," + getSelSQL(listCondition) + " from ");
		sql.append(getSizeSQL(listCondition, sqlLimit));

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

	// 布草消耗分析
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
		sql.append(" left  join goods_type on goods_type.Goods_TypeId=goods_info.Goods_Typeid");
		sql.append(" where call_info.customer_service_flag='1'and Goods_Name is not null and ");
		sql.append(" goods_info.Display=1 and goods_info.Goods_Typeid='dt0303' " + sqlLimit);
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

	// 布草分页
	@Override
	public Long countlinenTotal(Map<String, Object> map) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = linenExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select count(distinct(call_info.room_id)) from temp_list left join ");
		sql.append("call_info on temp_list.call_id=call_info.call_id ");
		sql.append("left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
		sql.append("left join case_info on case_info.call_id=call_info.call_id ");
		sql.append("where call_info.customer_service_flag='1'and Goods_Name is not null and ");
		sql.append("goods_info.Display=1 and goods_info.Goods_Typeid='dt0303'" + sqlLimit);

		Query query = em.createNativeQuery(sql.toString());
		BigInteger totalRow = (BigInteger) query.getSingleResult();// count返回值为BigInteger类型
		em.close();
		return totalRow.longValue();
	}
	
	// 房间易耗品分页
	@Override
	public Long countroomTotal(Map<String, Object> map) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = linenExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select count(distinct(call_info.room_id)) from temp_list left join ");
		sql.append("call_info on temp_list.call_id=call_info.call_id ");
		sql.append("left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
		sql.append("left join case_info on case_info.call_id=call_info.call_id ");
		sql.append("where call_info.customer_service_flag='1'and Goods_Name is not null and ");
		sql.append("goods_info.Display=1 and goods_info.Goods_Typeid='dt0301'" + sqlLimit);

		Query query = em.createNativeQuery(sql.toString());
		BigInteger totalRow = (BigInteger) query.getSingleResult();// count返回值为BigInteger类型
		em.close();
		return totalRow.longValue();
	}
	
	// 卫生间耗品分页
	@Override
	public Long countwashTotal(Map<String, Object> map) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = linenExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select count(distinct(call_info.room_id)) from temp_list left join ");
		sql.append("call_info on temp_list.call_id=call_info.call_id ");
		sql.append("left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
		sql.append("left join case_info on case_info.call_id=call_info.call_id ");
		sql.append("where call_info.customer_service_flag='1'and Goods_Name is not null and ");
		sql.append("goods_info.Display=1 and goods_info.Goods_Typeid='dt0302'" + sqlLimit);

		Query query = em.createNativeQuery(sql.toString());
		BigInteger totalRow = (BigInteger) query.getSingleResult();// count返回值为BigInteger类型
		em.close();
		return totalRow.longValue();
	}

	// 查询房间耗品消耗
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectroomExpend(Map<String, Object> map, List<Integer> listCondition) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = roomExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select a.room_no," + getSelSQL(listCondition) + " from ");
		sql.append(getroomSizeSQL(listCondition, sqlLimit));

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

	// 房间耗品消耗分析
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
		sql.append(
				"goods_info.Display=1 and goods_info.Goods_Typeid='dt0301'  " + sqlLimit);
		sql.append("group by temp_list.goods_id ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		System.out.println(list);
		return list;
	}

	// 卫生间耗品
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectwashExpend(Map<String, Object> map, List<Integer> listCondition) {

		EntityManager em = emf.createEntityManager();
		String sqlLimit = washExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select a.room_no," + getSelSQL(listCondition) + " from ");
		sql.append(getwashSizeSQL(listCondition, sqlLimit));

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

	// 卫生间耗品消耗分析
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
		sql.append("goods_info.Display=1 and goods_info.Goods_Typeid='dt0302' " + sqlLimit);
		sql.append("group by temp_list.goods_id ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	// 统计条件（布草、房间耗品、卫生间耗品）
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> selectCondition(String expendType) {

		EntityManager em = emf.createEntityManager();

		String sqlLimit = expendType;
		StringBuilder sql = new StringBuilder();
		sql.append("select goods_info.Goods_id from goods_info left join goods_type on ");
		sql.append(
				" goods_type.Goods_TypeId=goods_info.Goods_Typeid where goods_info.Display=1 and goods_type.TypeName= '"
						+ sqlLimit + "' ");
		sql.append(" order by goods_info.Goods_id;");

		Query query = em.createNativeQuery(sql.toString());
		List<Integer> list = query.getResultList();
		em.close();
		return list;
	}

	// 房间耗品统计分页
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectroomPage(Map<String, Object> map, Integer offset, Integer end,
			List<Integer> listCondition) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = roomExpendSQL(map);

		StringBuilder sql = new StringBuilder();
		sql.append("select a.room_no," + getSelSQL(listCondition) + " from ");
		sql.append(getroomSizeSQL(listCondition, sqlLimit));
		sql.append(" limit :offset,:end");
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("offset", offset).setParameter("end", end);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}
	
	/**
	 * 获取查询房间耗品字段SQL
	 * 
	 * @param list
	 * @param sqlLimit
	 * @return
	 */
	private String getroomSizeSQL(List<Integer> list, String sqlLimit) {
		StringBuilder sql = new StringBuilder();
		String sqlStr = null;
		if (list != null) {
			Integer size = list.size();
			for (int i = 0; i < size; i++) {
				if (i == 0) {
					sql.append("(select distinct temp_list.room_no from temp_list left join goods_info on goods_info.goods_id=temp_list.goods_id ");
					sql.append(" left join case_info on case_info.call_id=temp_list.call_id ");
					sql.append(" where goods_info.Display=1 and goods_info.Goods_Typeid='dt0301' " + sqlLimit + " order by temp_list.room_no asc ) as a ");
					sql.append(" left join (select call_info.room_id,sum(temp_list.num) num from call_info left join case_info ");
					sql.append(
							" on case_info.call_id=call_info.call_id left join temp_list on temp_list.call_id=call_info.call_id");
					sql.append(" left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
					sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and ");
					sql.append(" goods_info.goods_id=" + list.get(i) + sqlLimit);
					sql.append(" group by call_info.room_id) as a0 ");
					sql.append(" on a.room_no=a0.room_id left join ");
				} else {
					sql.append("(select call_info.room_id,sum(temp_list.num) num from call_info left join case_info ");
					sql.append(
							" on case_info.call_id=call_info.call_id left join temp_list on temp_list.call_id=call_info.call_id");
					sql.append(" left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
					sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and ");
					sql.append(" goods_info.goods_id=" + list.get(i) + sqlLimit);
					sql.append(" group by call_info.room_id) as a" + i);
					sql.append(" on a" + i + ".room_id=a.room_no left join ");
				}
			}
			sqlStr = sql.toString().substring(0, sql.toString().length() - 10);
		}
		return sqlStr;
	}

	//卫生间耗品分页
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectwashPage(Map<String, Object> map, Integer offset, Integer end,
			List<Integer> listCondition) {
		EntityManager em = emf.createEntityManager();
		String sqlLimit = washExpendSQL(map);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select a.room_no," + getSelSQL(listCondition) + " from ");
		sql.append(getwashSizeSQL(listCondition, sqlLimit));
		sql.append(" limit :offset,:end");
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("offset", offset).setParameter("end", end);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	/**
	 * 获取查询卫生间易耗品字段SQL
	 * 
	 * @param list
	 * @param sqlLimit
	 * @return
	*/
	private String getwashSizeSQL(List<Integer> list, String sqlLimit) {
		StringBuilder sql = new StringBuilder();
		String sqlStr = null;
		if (list != null) {
				Integer size = list.size();
				for (int i = 0; i < size; i++) {
					if (i == 0) {
						sql.append("(select distinct temp_list.room_no from temp_list left join goods_info on goods_info.goods_id=temp_list.goods_id ");
						sql.append(" left join case_info on case_info.call_id=temp_list.call_id ");
						sql.append(" where goods_info.Display=1 and goods_info.Goods_Typeid='dt0302' " + sqlLimit + " order by temp_list.room_no asc ) as a ");
						sql.append(" left join (select call_info.room_id,sum(temp_list.num) num from call_info left join case_info ");
						sql.append(
								" on case_info.call_id=call_info.call_id left join temp_list on temp_list.call_id=call_info.call_id");
						sql.append(" left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
						sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and ");
						sql.append(" goods_info.goods_id=" + list.get(i) + sqlLimit);
						sql.append(" group by call_info.room_id) as a0 ");
						sql.append(" on a.room_no=a0.room_id left join ");
					} else {
						sql.append("(select call_info.room_id,sum(temp_list.num) num from call_info left join case_info ");
						sql.append(
								" on case_info.call_id=call_info.call_id left join temp_list on temp_list.call_id=call_info.call_id");
						sql.append(" left join goods_info on temp_list.goods_id=goods_info.Goods_id ");
						sql.append(" where call_info.customer_service_flag='1' and Goods_Name is not null and ");
						sql.append(" goods_info.goods_id=" + list.get(i) + sqlLimit);
						sql.append(" group by call_info.room_id) as a" + i);
						sql.append(" on a" + i + ".room_id=a.room_no left join ");
					}
				}
				sqlStr = sql.toString().substring(0, sql.toString().length() - 10);
			}
			return sqlStr;
		}


}
