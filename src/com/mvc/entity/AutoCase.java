package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auto_case database table.
 * 
 */
@Entity
@Table(name="auto_case")
@NamedQuery(name="AutoCase.findAll", query="SELECT a FROM AutoCase a")
public class AutoCase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="service_sort", unique=true, nullable=false, length=24)
	private String serviceSort;

	@Column(name="case_rank")
	private int caseRank;

	@Column(name="department_id", length=16)
	private String departmentId;

	@Column(name="given_time")
	private int givenTime;

	public AutoCase() {
	}

	public String getServiceSort() {
		return this.serviceSort;
	}

	public void setServiceSort(String serviceSort) {
		this.serviceSort = serviceSort;
	}

	public int getCaseRank() {
		return this.caseRank;
	}

	public void setCaseRank(int caseRank) {
		this.caseRank = caseRank;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public int getGivenTime() {
		return this.givenTime;
	}

	public void setGivenTime(int givenTime) {
		this.givenTime = givenTime;
	}

}