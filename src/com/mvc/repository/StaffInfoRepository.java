package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entity.StaffInfo;

/**
 * 人员信息JPA
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface StaffInfoRepository extends JpaRepository<StaffInfo, Integer> {

}
