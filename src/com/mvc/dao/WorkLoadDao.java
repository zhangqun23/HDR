/**
 * 
 */
package com.mvc.dao;

import java.util.List;

import com.mvc.entity.RoomInfo;

/**
 * 工作量相关的dao层接口
 * 
 * @author zjn
 * @date 2016年12月7日
 */
public interface WorkLoadDao {

	 List<RoomInfo> count();

}
