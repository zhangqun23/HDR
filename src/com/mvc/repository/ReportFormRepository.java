package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entity.RoomSort;

/**
 * 报表公共类JPA
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface ReportFormRepository extends JpaRepository<RoomSort, Integer> {

}
