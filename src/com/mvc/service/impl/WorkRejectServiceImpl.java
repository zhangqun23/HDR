package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.mvc.dao.WorkRejectDao;
import com.mvc.service.WorkRejectService;

/**
 * 部门员工做房统计业务层实现
 * 
 * @author wangrui
 * @date 2016-12-08
 */
@Service("workRejectServiceImpl")
public class WorkRejectServiceImpl implements WorkRejectService {

	@Autowired
	WorkRejectDao workRejectDao;

}
