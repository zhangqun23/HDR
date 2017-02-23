package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entity.RoomSort;

/**
 * 房型JPA
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface RoomSortRepository extends JpaRepository<RoomSort, Integer> {

	// 根据sort_no获取房间类型
	@Query("select rs from RoomSort rs where sort_no=:sort_no")
	RoomSort selectNameBySortNo(@Param("sort_no") String sort_no);
}
