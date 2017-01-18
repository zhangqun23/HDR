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
	
	// lwt:根据staffNo查询用户信息
	@Query("select s from StaffInfo s where Staff_no = :staff_no and isDeleted=0 and staff_right!=2")
	public StaffInfo findByStaffNo(@Param("staff_no") String staff_no);
	
	@Query("select count(*) from StaffInfo where Staff_no=:staff_no and isDeleted=0 and staff_right!=2")
	public Long isExist(@Param("staff_no") String staff_no);

}
