package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equip_sort database table.
 * 
 */
@Entity
@Table(name="equip_sort")
@NamedQuery(name="EquipSort.findAll", query="SELECT e FROM EquipSort e")
public class EquipSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sort_id", unique=true, nullable=false)
	private int sortId;

	@Column(name="department_id", nullable=false, length=16)
	private String departmentId;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(nullable=false)
	private int isdeleted;

	@Column(name="sort_flag")
	private int sortFlag;

	@Column(name="sort_name", length=32)
	private String sortName;

	@Column(name="sort_note", length=255)
	private String sortNote;

	public EquipSort() {
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public int getSortFlag() {
		return this.sortFlag;
	}

	public void setSortFlag(int sortFlag) {
		this.sortFlag = sortFlag;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortNote() {
		return this.sortNote;
	}

	public void setSortNote(String sortNote) {
		this.sortNote = sortNote;
	}

}