package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entity.StaffInfo;

/**
 * 人员信息JPA
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface StaffInfoRepository extends JpaRepository<StaffInfo, Integer> {

	// 根据ID查询员工信息
	@Query("select s from StaffInfo s where Staff_id = :staffId")
	public StaffInfo findById(@Param("staffId") Integer staffId);

}
