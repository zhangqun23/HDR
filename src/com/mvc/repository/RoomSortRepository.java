package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entity.RoomSort;

/**
 * 房型JPA
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface RoomSortRepository extends JpaRepository<RoomSort, Integer> {

}
