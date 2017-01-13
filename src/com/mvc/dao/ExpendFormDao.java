package com.mvc.dao;

import java.util.List;
import java.util.Map;

/**
 * 酒店对客服务信息统计
 * @author wq
 * @date 2017年1月13日
 */
public interface ExpendFormDao {

	//布草统计
	List<Object> selectlinenExpend(Map<String, Object> map);

}
