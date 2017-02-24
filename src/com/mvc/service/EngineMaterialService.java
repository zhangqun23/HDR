package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entity.MaterialSort;

/**
 * 工程物料管理业务层接口
 * 
 * @author wangrui
 * @date 2017年2月20日
 */
public interface EngineMaterialService {

	// 查询工程物料
	String selectEngineMaterial(Map<String, Object> map);

	// 工程物料统计Word
	ResponseEntity<byte[]> exportEngineMaterial(Map<String, Object> map, String path, String tempPath);

	// 工程物料统计Excel
	ResponseEntity<byte[]> exportEngineMaterialExcel(Map<String, Object> map, String path);

	// 获取物料分类
	List<MaterialSort> selectMatSortName();

}
