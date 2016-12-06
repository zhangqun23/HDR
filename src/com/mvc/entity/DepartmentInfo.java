package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the department_info database table.
 * 
 */
@Entity
@Table(name="department_info")
@NamedQuery(name="DepartmentInfo.findAll", query="SELECT d FROM DepartmentInfo d")
public class DepartmentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="department_id", unique=true, nullable=false, length=16)
	private String departmentId;

	@Column(name="department_name", length=32)
	private String departmentName;

	@Column(name="department_note", length=144)
	private String departmentNote;

	private int isdeleted;

	@Column(name="parent_id", length=16)
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

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}