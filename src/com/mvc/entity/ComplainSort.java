package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the complain_sort database table.
 * 
 */
@Entity
@Table(name="complain_sort")
@NamedQuery(name="ComplainSort.findAll", query="SELECT c FROM ComplainSort c")
public class ComplainSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="sort_flag", length=1)
	private String sortFlag;

	@Column(name="sort_id")
	private int sortId;

	@Column(name="sort_name", length=11)
	private String sortName;

	public ComplainSort() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSortFlag() {
		return this.sortFlag;
	}

	public void setSortFlag(String sortFlag) {
		this.sortFlag = sortFlag;
	}

	public int getSortId() {
		return this.sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}