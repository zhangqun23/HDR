package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the linen_sort database table.
 * 
 */
@Entity
@Table(name="linen_sort")
@NamedQuery(name="LinenSort.findAll", query="SELECT l FROM LinenSort l")
public class LinenSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sort_id", unique=true, nullable=false)
	private int sortId;

	@Column(name="is_deleted", nullable=false)
	private byte isDeleted;

	@Column(name="parent_id", nullable=false)
	private int parentId;

	@Column(name="sort_name", nullable=false, length=10)
	private String sortName;

	public LinenSort() {
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