package com.mvc.repository;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entity.DepartmentInfo;

/**
 * 部门信息JPA
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public interface DepartmentInfoRepository extends JpaRepository<DepartmentInfo, Integer> {

	@Query("select d from DepartmentInfo d where department_name=:dept_name")
	DepartmentInfo selectByDeptName(@Param("dept_name") String dept_name);
<<<<<<< HEAD
	
	//whm查询部门
	@Query("select d from DepartmentInfo d where isdeleted=0 ")
	List<DepartmentInfo> selectDep();
=======
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
}
