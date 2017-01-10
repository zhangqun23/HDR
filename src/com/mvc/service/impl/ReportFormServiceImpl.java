package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entity.RoomSort;
import com.mvc.repository.ReportFormRepository;
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
	ReportFormRepository reportFormRepository;

	@Override
	public List<RoomSort> selectRoomSort() {
		List<RoomSort> list = reportFormRepository.findAll();
		return list;
	}

}
