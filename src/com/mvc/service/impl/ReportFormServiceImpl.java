package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.ReportFormDao;
import com.mvc.entity.RoomSort;
import com.mvc.entity.StaffInfo;
import com.mvc.repository.RoomSortRepository;
import com.mvc.service.ReportFormService;

/**
 * 报表公共类业务层实现
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
@Service("reportFormServiceImpl")
public class ReportFormServiceImpl implements ReportFormService {

	@Autowired
	RoomSortRepository roomSortRepository;
	@Autowired
	ReportFormDao reportFormDao;

	@Override
	public List<RoomSort> selectRoomSort() {
		List<RoomSort> list = roomSortRepository.findAll();
		return list;
	}

	@Override
	public List<StaffInfo> selectStaffByDept(String deptType) {
		List<StaffInfo> list = reportFormDao.selectStaffByDept(deptType);
		return list;
	}

}
