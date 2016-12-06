package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the material_sort database table.
 * 
 */
@Entity
@Table(name="material_sort")
@NamedQuery(name="MaterialSort.findAll", query="SELECT m FROM MaterialSort m")
public class MaterialSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sort_id", unique=true, nullable=false)
	private int sortId;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="parent_id", nullable=false)
	private int parentId;

	@Column(name="sort_name", nullable=false, length=50)
	private String sortName;

	public MaterialSort() {
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}