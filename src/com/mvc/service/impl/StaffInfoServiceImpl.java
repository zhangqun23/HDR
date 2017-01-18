package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entity.StaffInfo;
import com.mvc.repository.StaffInfoRepository;
import com.mvc.service.StaffInfoService;

@Service("staffInfoServiceImpl")
public class StaffInfoServiceImpl implements StaffInfoService {

	@Autowired
	StaffInfoRepository staffInfoRepository;

	@Override
	public Long isExist(String staffNo) {
		// TODO Auto-generated method stub
		return staffInfoRepository.isExist(staffNo);
	}

	@Override
	public StaffInfo findByStaffNo(String staffNo) {
		return staffInfoRepository.findByStaffNo(staffNo);
	}

}
