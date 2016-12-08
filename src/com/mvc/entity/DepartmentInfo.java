package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 部门信息
 * 
 */
@Entity
@Table(name = "department_info")
public class DepartmentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "department_id", unique = true, nullable = false, length = 16)
	private String departmentId;

	@Column(name = "department_name", length = 32)
	private String departmentName;

	@Column(name = "department_note", length = 144)
	private String departmentNote;

	private Integer isdeleted;

	@Column(name = "parent_id", length = 16)
	private String parentId;

	public DepartmentInfo() {
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

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

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}