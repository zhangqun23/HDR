package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.entityReport.LinenExpend;


/**
 * 耗品统计
 * @author wq
 * @date 2017年1月13日
 */
public interface ExpendFormService {
	
	//布草统计
	List<LinenExpend> selectLinenExpend(Map<String, Object> map);

}
