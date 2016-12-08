package com.mvc.entity;

import javax.persistence.*;

/**
 * 部门信息
 * 
 */
@Entity
@Table(name = "department_info")
public class DepartmentInfo {

	private String departmentId;// 部门ID，主键
	private String departmentName;// 部门名称
	private String departmentNote;// 部门说明
	private Integer isdeleted;// 删除标志位，1删除，0未删除
	private String parentId;// 所属父类ID
	public DepartmentInfo() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "department_id", unique = true, nullable = false, length = 16)
	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "department_name", length = 32)
	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "department_note", length = 144)
	public String getDepartmentNote() {
		return this.departmentNote;
	}

	public void setDepartmentNote(String departmentNote) {
		this.departmentNote = departmentNote;
	}

	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@Column(name = "parent_id", length = 16)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}