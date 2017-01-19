/**
 * 
 */
package com.mvc.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

/**
 * @author zjn
 * @date 2017年1月18日
 */
public interface ZjnService {

	// 导出房间或卫生间耗品分析图
	ResponseEntity<byte[]> exportRoomOrWashExpendPic(Map<String, String> map);

	// 导出布草用量分析图
	ResponseEntity<byte[]> exportLinenExpendPic(Map<String, String> map);
}
