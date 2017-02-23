package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entityReport.EngineMaterial;

/**
 * 工程物料JPA
 * 
 * @author wangrui
 * @date 2017年2月23日
 */
public interface EngineMaterialRepository extends JpaRepository<EngineMaterial, Integer> {

}
