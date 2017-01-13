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
import com.utils.Pager;

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
	
    //查询布草消耗
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectlinenExpend(Map<String, Object> map) {
		
		EntityManager em = emf.createEntityManager();
		String sqlLimit = linenExpendSQL(map);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select room_info.room_no,goods_info.Goods_Name,sum(temp_list.num) ");
		sql.append(" from room_info left join call_info on room_info.room_id=call_info.room_id left join temp_list on");
        sql.append(" call_info.call_id=temp_list.call_id left join goods_info on temp_list.goods_id=goods_info.Goods_id");
        sql.append(" left join case_info on case_info.call_id=call_info.call_id ");
		sql.append(
				" where Goods_Name is not null and goods_info.Goods_id>'408' and goods_info.Goods_id<'434' "+ sqlLimit); 
		sql.append(" group by room_info.room_id,temp_list.goods_id");
                   
		
		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	//布草统计SQL条件
	private String linenExpendSQL(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();

		String formType = (String) map.get("formType");
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");

		if (formType != null) {
			sql.append(" and case_info.clean_type='" + formType + "'");
		}
		if (startTime != null && endTime != null) {
			sql.append(" and case_info.open_time between '" + startTime + "'" + " and '" + endTime + "'");
		}
		return sql.toString();
	}
	
	/**
	 * 分页SQL条件
	 * 
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("unused")
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
