package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mvc.entity.MaterialSort;

/**
 * 工程物料类别JPA
 * 
 * @author wangrui
 * @date 2017年2月23日
 */
public interface MaterialSortRepository extends JpaRepository<MaterialSort, Integer> {

	@Query("select ms from MaterialSort ms order by parent_id,sort_id ")
	List<MaterialSort> selectAllSortName();

}
