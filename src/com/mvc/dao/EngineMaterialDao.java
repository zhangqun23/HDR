package com.mvc.dao;

import java.util.List;
import java.util.Map;

/**
 * 工程物料管理数据持久层接口
 * 
 * @author wangrui
 * @date 2017年2月20日
 */
public interface EngineMaterialDao {

	// 查询工程物料
	List<Object> selectEngineMaterial(Map<String, Object> map);

}
