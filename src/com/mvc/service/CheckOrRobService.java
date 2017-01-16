/**
 * 
 */
package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.entityReport.RobEfficiency;

/**
 * @author 包阿儒汉
 *
 */

public interface CheckOrRobService {

	List<RobEfficiency> selectRobEfficiency(Map<String, Object> map);

}
